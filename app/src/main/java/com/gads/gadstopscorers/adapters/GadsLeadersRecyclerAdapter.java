package com.gads.gadstopscorers.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gads.gadstopscorers.R;
import com.gads.gadstopscorers.models.LeadersApiResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GadsLeadersRecyclerAdapter extends RecyclerView.Adapter<GadsLeadersRecyclerAdapter.GadsLeadersViewHolder> {
    private Context mContext;
    private List<LeadersApiResponse> mHourLeaders;

    public GadsLeadersRecyclerAdapter(Context context, List<LeadersApiResponse> hourLeaders){
        mContext = context;
        mHourLeaders = hourLeaders;
    }

    @NonNull
    @Override
    public GadsLeadersRecyclerAdapter.GadsLeadersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_hour_leader, parent, false);
        GadsLeadersViewHolder gadsLeadersViewHolder = new GadsLeadersViewHolder(view);
        return gadsLeadersViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GadsLeadersRecyclerAdapter.GadsLeadersViewHolder holder, int position) {
        holder.bingLeadersByHours(mHourLeaders.get(position));
    }

    @Override
    public int getItemCount() {
        return mHourLeaders.size();
    }

    public class GadsLeadersViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.leadersNameHourTextView) TextView mLeaderByHourNameTextView;
        @BindView(R.id.leadersUserImageView) ImageView mLeaderByHourImageView;
        @BindView(R.id.leadersHoursScoreAndCountryTextView) TextView mLeaderByHourScoreAndCountryTextView;


        public GadsLeadersViewHolder(@NonNull View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }

        @SuppressLint("SetTextI18n")
        public void bingLeadersByHours(LeadersApiResponse mLeaderByHour) {
            if (mLeaderByHour.getBadgeUrl() != null){
                Picasso.get().load(mLeaderByHour.getBadgeUrl()).into(mLeaderByHourImageView);
            }else {
                Picasso.get().load(R.drawable.imageholder).into(mLeaderByHourImageView);
            }

            mLeaderByHourNameTextView.setTypeface(null, Typeface.BOLD);
            mLeaderByHourNameTextView.setText(mLeaderByHour.getName().trim());

            mLeaderByHourScoreAndCountryTextView.setText(mLeaderByHour.getHours().toString()+" learning hours, "+mLeaderByHour.getCountry());

        }
    }
}
