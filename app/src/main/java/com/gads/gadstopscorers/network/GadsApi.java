package com.gads.gadstopscorers.network;

import com.gads.gadstopscorers.models.LeadersApiResponse;
import com.gads.gadstopscorers.models.SkillsApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GadsApi {
    @GET("api/hours")
    Call<List<LeadersApiResponse>> getLeadersResults();

    @GET("api/skilliq")
    Call<List<SkillsApiResponse>> getSkillsResults();
}
