package com.gads.gadstopscorers.ui.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import com.gads.gadstopscorers.R;
import com.gads.gadstopscorers.adapters.GadsLeadersRecyclerAdapter;
import com.gads.gadstopscorers.models.LeadersApiResponse;
import com.gads.gadstopscorers.network.GadsApi;
import com.gads.gadstopscorers.network.GadsClient;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaderFragment extends Fragment implements View.OnClickListener{
    @BindView(R.id.leadersRecyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.errorLeadersTextView) TextView mErrorLeadersTextView;
    @BindView(R.id.errorLeadersImageView) ImageView mErrorLeadersImageView;
    @BindView(R.id.restartLeadersButton) Button mRestartLeadersButton;

    private List<LeadersApiResponse> mLeadersByHour;
    private GadsLeadersRecyclerAdapter mGadsLeadersRecyclerAdapter;

    private Dialog mSplashDialog;



    public LeaderFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSplashDialog = new Dialog(Objects.requireNonNull(getContext()),android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        showSplashScreen();
    }

    private void showSplashScreen() {
        mSplashDialog.setContentView(R.layout.frame_help);
        mSplashDialog.show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_leader, container, false);
        ButterKnife.bind(this, view);
        mRestartLeadersButton.setOnClickListener(this);

        if (hasConnection(Objects.requireNonNull(getContext()))){
            fetchLeadersDetails();
        }else{
            showErrorDisplay();
        }

        return  view;
    }


    private void fetchLeadersDetails() {
        GadsApi client = GadsClient.getClient();
        Call<List<LeadersApiResponse>> call = client.getLeadersResults();

        call.enqueue(new Callback<List<LeadersApiResponse>>() {
            @Override
            public void onResponse(Call<List<LeadersApiResponse>> call, Response<List<LeadersApiResponse>> response) {
                if (response.isSuccessful()){
                    removeErrorDisplay();
                    mSplashDialog.dismiss();
                    mLeadersByHour = response.body();
                    mGadsLeadersRecyclerAdapter = new GadsLeadersRecyclerAdapter(getContext(), mLeadersByHour);
                    LayoutManager layoutManager = new LinearLayoutManager(getContext());
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setAdapter(mGadsLeadersRecyclerAdapter);
                    mRecyclerView.setHasFixedSize(true);
                }
            }

            @Override
            public void onFailure(Call<List<LeadersApiResponse>> call, Throwable t) {
                if (t instanceof IOException){
                    Toast.makeText(getActivity(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                    mSplashDialog.dismiss();
                    showErrorDisplay();
                }else{
                    Toast.makeText(getActivity(), "Sorry something went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void showErrorDisplay() {
        mSplashDialog.dismiss();
        mErrorLeadersImageView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mErrorLeadersTextView.setVisibility(View.VISIBLE);
        mRestartLeadersButton.setVisibility(View.VISIBLE);
    }

    private void removeErrorDisplay() {
        mErrorLeadersTextView.setVisibility(View.GONE);
        mErrorLeadersImageView.setVisibility(View.GONE);
        mRestartLeadersButton.setVisibility(View.GONE);
    }

    private boolean hasConnection(Context context){
        ConnectivityManager mConnectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifiNetwork=mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mWifiNetwork != null && mWifiNetwork.isConnected()){
            return true;
        }
        NetworkInfo mMobileNetwork=mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mMobileNetwork != null && mMobileNetwork.isConnected()){
            return true;
        }
        NetworkInfo mActiveNetwork = mConnectivityManager.getActiveNetworkInfo();
        if (mActiveNetwork != null && mActiveNetwork.isConnected()){
            return true;
        }
        return false;

    }

    @Override
    public void onStop(){
        super.onStop();
        if (mSplashDialog.isShowing() || mSplashDialog != null){
            mSplashDialog.dismiss();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == mRestartLeadersButton){
            Intent intent = Objects.requireNonNull(getActivity()).getIntent();
            getActivity().finish();
            startActivity(intent);
        }

    }
}
