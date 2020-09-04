package com.gads.gadstopscorers.ui.fragments;

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
import com.gads.gadstopscorers.adapters.GadsSkillsRecyclerAdapter;
import com.gads.gadstopscorers.models.SkillsApiResponse;
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


public class SkillsFragment extends Fragment implements View.OnClickListener{
    @BindView(R.id.skillsRecyclerView) RecyclerView mSkillsRecyclerView;
    @BindView(R.id.errorSkillsTextView) TextView mErrorSkillsTextView;
    @BindView(R.id.errorSkillsImageView) ImageView mErrorSkillsImageView;
    @BindView(R.id.restartSkillsButton) Button mRestartSkillsButton;

    private GadsSkillsRecyclerAdapter mGadsSkillsRecyclerAdapter;
    private List<SkillsApiResponse> mLeadersBySkills;


    public SkillsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_skills, container, false);
        ButterKnife.bind(this, view);

        mRestartSkillsButton.setOnClickListener(this);

        if (hasConnection(Objects.requireNonNull(getContext()))){
            fetchSkillsDetails();
        }else{
            showErrorDisplay();
        }


        return view;
    }



    private void fetchSkillsDetails() {
        GadsApi client = GadsClient.getClient();
        Call<List<SkillsApiResponse>> call = client.getSkillsResults();

        call.enqueue(new Callback<List<SkillsApiResponse>>(){
            @Override
            public void onResponse(Call<List<SkillsApiResponse>> call, Response<List<SkillsApiResponse>> response) {
                if(response.isSuccessful()){
                    removeErrorDisplay();
                    mLeadersBySkills = response.body();
                    System.out.println(mLeadersBySkills);
                    mGadsSkillsRecyclerAdapter = new GadsSkillsRecyclerAdapter(getContext(), mLeadersBySkills);
                    LayoutManager layoutManager = new LinearLayoutManager(getContext());
                    mSkillsRecyclerView.setLayoutManager(layoutManager);
                    mSkillsRecyclerView.setAdapter(mGadsSkillsRecyclerAdapter);
                    mSkillsRecyclerView.setHasFixedSize(true);
                }
            }

            @Override
            public void onFailure(Call<List<SkillsApiResponse>> call, Throwable t) {
                if (t instanceof IOException){
                    showErrorDisplay();
                }else{
                    Toast.makeText(getContext(), "Sorry something went Wrong", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    private void showErrorDisplay() {
        mSkillsRecyclerView.setVisibility(View.GONE);
        mErrorSkillsImageView.setVisibility(View.VISIBLE);
        mErrorSkillsTextView.setVisibility(View.VISIBLE);
        mRestartSkillsButton.setVisibility(View.VISIBLE);
    }

    private void removeErrorDisplay() {
        mSkillsRecyclerView.setVisibility(View.VISIBLE);
        mErrorSkillsImageView.setVisibility(View.GONE);
        mErrorSkillsTextView.setVisibility(View.GONE);
        mRestartSkillsButton.setVisibility(View.GONE);
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
    public void onClick(View view) {
        if (view == mRestartSkillsButton) {
            Intent intent = Objects.requireNonNull(getActivity()).getIntent();
            getActivity().finish();
            startActivity(intent);
        }

    }
}
