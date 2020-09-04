package com.gads.gadstopscorers.models;


import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SkillsApiResponse implements Serializable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("badgeUrl")
    @Expose
    private String badgeUrl;
    private final static long serialVersionUID = 7883931033199288665L;

    /**
     * No args constructor for use in serialization
     *
     */
    public SkillsApiResponse() {
    }

    /**
     *
     * @param score
     * @param country
     * @param badgeUrl
     * @param name
     */
    public SkillsApiResponse(String name, Integer score, String country, String badgeUrl) {
        super();
        this.name = name;
        this.score = score;
        this.country = country;
        this.badgeUrl = badgeUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBadgeUrl() {
        return badgeUrl;
    }

    public void setBadgeUrl(String badgeUrl) {
        this.badgeUrl = badgeUrl;
    }

}