package com.gads.gadstopscorers.models;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeadersApiResponse implements Serializable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("hours")
    @Expose
    private Integer hours;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("badgeUrl")
    @Expose
    private String badgeUrl;
    private final static long serialVersionUID = 8648073748402305514L;

    /**
     * No args constructor for use in serialization
     *
     */
    public LeadersApiResponse() {
    }

    /**
     *
     * @param country
     * @param hours
     * @param badgeUrl
     * @param name
     */
    public LeadersApiResponse(String name, Integer hours, String country, String badgeUrl) {
        super();
        this.name = name;
        this.hours = hours;
        this.country = country;
        this.badgeUrl = badgeUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
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
