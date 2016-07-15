package com.softdesign.devintensive.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.data.network.res.UserListRes;
import com.softdesign.devintensive.ui.views.AspectRatioImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by vladimirpapin on 14.07.16.
 */
public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    private Context mContext;
    private ArrayList<UserListRes.Datum> mUsers;
    private UserViewHolder.CustomClickListener mCustomClickListener;

    public UsersAdapter(ArrayList<UserListRes.Datum> users, UserViewHolder.CustomClickListener customClickListener) {
        mUsers = users;
        this.mCustomClickListener = customClickListener;
    }

    @Override
    public UsersAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View converView = LayoutInflater.from(mContext).inflate(R.layout.item_user_list, parent, false);

        return new UserViewHolder(converView, mCustomClickListener);
    }

    @Override
    public void onBindViewHolder(UsersAdapter.UserViewHolder holder, int position) {

        UserListRes.Datum user = mUsers.get(position);
        Picasso.with(mContext)
                .load(user.getPublicInfo().getPhoto())
                .placeholder(mContext.getResources().getDrawable(R.drawable.user_bg))
                .error(mContext.getResources().getDrawable(R.drawable.user_bg))
                .resize(730, 415)
                .centerCrop()
                .into(holder.userPhoto);

        holder.mFullName.setText(user.getFullName());
        holder.mRaitin.setText(String.valueOf(user.getProfileValues().getRaiting()));
        holder.mCodeLines.setText(String.valueOf(user.getProfileValues().getLinesCode()));
        holder.mProjects.setText(String.valueOf(user.getProfileValues().getProjects()));
        String bio = user.getPublicInfo().getBio();
        if (bio == null || bio.isEmpty()) {
            holder.mBio.setVisibility(View.GONE);
        } else {
            holder.mBio.setVisibility(View.VISIBLE);
            holder.mBio.setText(bio);
        }

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected AspectRatioImageView userPhoto;
        protected TextView mFullName, mRaitin, mCodeLines, mProjects, mBio;
        protected Button mButton;
          CustomClickListener mListener;

        public UserViewHolder(View itemView, CustomClickListener customClickListener) {
            super(itemView);
            this.mListener = customClickListener;

            userPhoto = (AspectRatioImageView) itemView.findViewById(R.id.users_photo);
            mRaitin = (TextView) itemView.findViewById(R.id.raiting_txt_user);
            mCodeLines = (TextView) itemView.findViewById(R.id.code_line_txt_user);
            mProjects = (TextView) itemView.findViewById(R.id.project_txt_user);
            mBio  = (TextView) itemView.findViewById(R.id.bio_txt_user);
            mFullName = (TextView) itemView.findViewById(R.id.user_full_name_txt);
            mButton = (Button) itemView.findViewById(R.id.more_info_btn);

            mButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onClickUserMore(getAdapterPosition());
            }
        }

        public interface CustomClickListener {
            void onClickUserMore(int position);
        }
    }
}
