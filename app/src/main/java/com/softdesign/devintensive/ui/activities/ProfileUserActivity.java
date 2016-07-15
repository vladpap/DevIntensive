package com.softdesign.devintensive.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.data.storage.models.UserDTO;
import com.softdesign.devintensive.ui.adapters.RepositoriesAdapter;
import com.softdesign.devintensive.utils.ConstantManager;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProfileUserActivity extends BaseActivity {

    private Toolbar mToolbar;
    private ImageView mProfileImage;
    private EditText mUserBio;
    private TextView mUserRaiting, mUserCodeLines, mUserProject;
    private CollapsingToolbarLayout mCollapsingToolbar;

    private ListView mRepoListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_user);
        mProfileImage = (ImageView) findViewById(R.id.user_photo_img_user);
        mUserBio = (EditText) findViewById(R.id.about_user);
        mUserRaiting = (TextView) findViewById(R.id.count_rating_user);
        mUserCodeLines = (TextView) findViewById(R.id.count_code_line_user);
        mUserProject = (TextView) findViewById(R.id.count_projects_user);
        mRepoListView = (ListView) findViewById(R.id.repositories_list);
        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout_user);

        setupToolbar();
        initProfileData();
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    private void initProfileData() {
        UserDTO userDTO = getIntent().getParcelableExtra(ConstantManager.PARCELABLE_KEY);
        final List<String> repositoiesList = userDTO.getRepositories();
        final RepositoriesAdapter repositoriesAdapter = new RepositoriesAdapter(this, repositoiesList);
        mRepoListView.setAdapter(repositoriesAdapter);

        mRepoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String repoStr = repositoiesList.get(position);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(repoStr.contains("http") ? repoStr : ("http://" + repoStr)));
                startActivity(browserIntent);
            }
        });

        mCollapsingToolbar.setTitle(userDTO.getFullName());
        mUserRaiting.setText(userDTO.getRaiting());
        mUserCodeLines.setText(userDTO.getCodeLine());
        mUserProject.setText(userDTO.getProject());
        mUserBio.setText(userDTO.getBio());

        Picasso.with(this)
                .load(userDTO.getPhoto())
                .placeholder(this.getResources().getDrawable(R.drawable.user_bg))
                .error(this.getResources().getDrawable(R.drawable.user_bg))
                .into(mProfileImage);
    }
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    */
}
