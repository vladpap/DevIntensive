package com.softdesign.devintensive.data.network.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vladimirpapin on 14.07.16.
 */
public class UserListRes {
    @SerializedName("success")
    @Expose
    protected boolean success;
    @SerializedName("data")
    @Expose
    protected List<Datum> data = new ArrayList<Datum>();

    public List<Datum> getData() {
        return data;
    }

    public class Datum {

        @SerializedName("_id")
        @Expose
        protected String id;
        @SerializedName("first_name")
        @Expose
        protected String firstName;
        @SerializedName("second_name")
        @Expose
        protected String secondName;
        @SerializedName("__v")
        @Expose
        protected int v;
        @SerializedName("repositories")
        @Expose
        protected UserModelRes.Repositories repositories;
        @SerializedName("profileValues")
        @Expose
        protected UserModelRes.ProfileValues profileValues;
        @SerializedName("publicInfo")
        @Expose
        protected UserModelRes.PublicInfo publicInfo;
        @SerializedName("specialization")
        @Expose
        protected String specialization;
        @SerializedName("u pdated")
        @Expose
        protected String updated;

        public String getFullName() {
            return firstName + " " + secondName;
        }

        public UserModelRes.Repositories getRepositories() {
            return repositories;
        }

        public UserModelRes.ProfileValues getProfileValues() {
            return profileValues;
        }

        public UserModelRes.PublicInfo getPublicInfo() {
            return publicInfo;
        }
    }

    /*
    public class Repositories {

        @SerializedName("repo")
        @Expose
        protected List<Repo> repo = new ArrayList<Repo>();
        @SerializedName("updated")
        @Expose
        protected String updated;


    }

    public class Repo {

        @SerializedName("_id")
        @Expose
        protected String id;
        @SerializedName("git")
        @Expose
        protected String git;
        @SerializedName("title")
        @Expose
        protected String title;

    }

    public class ProfileValues {

        @SerializedName("homeTask")
        @Expose
        protected int homeTask;
        @SerializedName("projects")
        @Expose
        protected int projects;
        @SerializedName("linesCode")
        @Expose
        protected int linesCode;
        @SerializedName("rait")
        @Expose
        protected int rait;
        @SerializedName("updated")
        @Expose
        protected String updated;

    }

    public class PublicInfo {

        @SerializedName("bio")
        @Expose
        protected String bio;
        @SerializedName("avatar")
        @Expose
        protected String avatar;
        @SerializedName("photo")
        @Expose
        protected String photo;
        @SerializedName("updated")
        @Expose
        protected String updated;

    }
*/

}
