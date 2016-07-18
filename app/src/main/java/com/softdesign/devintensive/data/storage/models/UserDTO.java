package com.softdesign.devintensive.data.storage.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.softdesign.devintensive.data.network.res.UserListRes;
import com.softdesign.devintensive.data.network.res.UserModelRes;

import java.util.ArrayList;
import java.util.List;

public class UserDTO implements Parcelable {

    private String mPhoto;
    private String mFullName;
    private String mRaiting;
    private String mCodeLine;
    private String mProject;
    private String mBio;
    private List<String> mRepositories;

    public UserDTO(UserListRes.Datum userData) {
        List<String> repoList = new ArrayList<>();

        mPhoto = userData.getPublicInfo().getPhoto();
        mFullName = userData.getFullName();
        mRaiting = String.valueOf(userData.getProfileValues().getRaiting());
        mCodeLine = String.valueOf(userData.getProfileValues().getLinesCode());
        mProject = String.valueOf(userData.getProfileValues().getProjects());
        mBio = userData.getPublicInfo().getBio();
        for (UserModelRes.Repo getlink : userData.getRepositories().getRepo()) {
            repoList.add(getlink.getGit());
        }
        mRepositories = repoList;
    }

    protected UserDTO(Parcel in) {
        mPhoto = in.readString();
        mFullName = in.readString();
        mRaiting = in.readString();
        mCodeLine = in.readString();
        mProject = in.readString();
        mBio = in.readString();
        if (in.readByte() == 0x01) {
            mRepositories = new ArrayList<String>();
            in.readList(mRepositories, String.class.getClassLoader());
        } else {
            mRepositories = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mPhoto);
        dest.writeString(mFullName);
        dest.writeString(mRaiting);
        dest.writeString(mCodeLine);
        dest.writeString(mProject);
        dest.writeString(mBio);
        if (mRepositories == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mRepositories);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<UserDTO> CREATOR = new Parcelable.Creator<UserDTO>() {
        @Override
        public UserDTO createFromParcel(Parcel in) {
            return new UserDTO(in);
        }

        @Override
        public UserDTO[] newArray(int size) {
            return new UserDTO[size];
        }
    };

    public String getPhoto() {
        return mPhoto;
    }

    public String getFullName() {
        return mFullName;
    }

    public String getRaiting() {
        return mRaiting;
    }

    public String getCodeLine() {
        return mCodeLine;
    }

    public String getProject() {
        return mProject;
    }

    public String getBio() {
        return mBio;
    }

    public List<String> getRepositories() {
        return mRepositories;
    }
}
