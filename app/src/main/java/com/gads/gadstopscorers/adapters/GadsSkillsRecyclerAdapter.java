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
import com.gads.gadstopscorers.models.SkillsApiResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GadsSkillsRecyclerAdapter extends RecyclerView.Adapter<GadsSkillsRecyclerAdapter.GadsSkillsViewHolder> {
    private Context mContext;
    private List<SkillsApiResponse> mSkillsLeaders;

    public GadsSkillsRecyclerAdapter(Context context, List<SkillsApiResponse> skillsLeaders){
        mContext = context;
        mSkillsLeaders = skillsLeaders;
    }

    @NonNull
    @Override
    public GadsSkillsRecyclerAdapter.GadsSkillsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_skills_leader, parent, false);
        GadsSkillsViewHolder gadsSkillsViewHolder = new GadsSkillsViewHolder(view);
        return gadsSkillsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GadsSkillsRecyclerAdapter.GadsSkillsViewHolder holder, int position) {
        holder.bindSkillsLeader(mSkillsLeaders.get(position));
    }

    @Override
    public int getItemCount() {
        return mSkillsLeaders.size();
    }

    public class GadsSkillsViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.skillsImageView) ImageView mLeaderBySkillsImageView;
        @BindView(R.id.skillsNameHourTextView) TextView mLeaderBySkillsNameTextView;
        @BindView(R.id.skillsHoursScoreAndCountryTextView) TextView mLeaderBySkillsHoursAndCountryTextView;

        public GadsSkillsViewHolder(@NonNull View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }

        @SuppressLint("SetTextI18n")
        public void bindSkillsLeader(SkillsApiResponse mSkillLeader) {
            if (mSkillLeader.getBadgeUrl() != null){
                Picasso.get().load(mSkillLeader.getBadgeUrl()).into(mLeaderBySkillsImageView);
            }else{
                Picasso.get().load(R.drawable.imageholder).into(mLeaderBySkillsImageView);
            }

            mLeaderBySkillsNameTextView.setTypeface(null, Typeface.BOLD);
            mLeaderBySkillsNameTextView.setText(mSkillLeader.getName());

            mLeaderBySkillsHoursAndCountryTextView.setText(mSkillLeader.getScore()+" skills IQ Score, "+ mSkillLeader.getCountry());

        }
    }
}
