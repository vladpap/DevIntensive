package com.softdesign.devintensive.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.data.managers.DataManager;
import com.softdesign.devintensive.data.network.res.UserListRes;
import com.softdesign.devintensive.data.storage.models.UserDTO;
import com.softdesign.devintensive.ui.adapters.UsersAdapter;
import com.softdesign.devintensive.utils.CircleTransform;
import com.softdesign.devintensive.utils.ConstantManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListActivity extends BaseActivity implements SearchView.OnQueryTextListener {

    private static final String TAG = ConstantManager.TAG_PREFIX + " UserListActivity";
    private CoordinatorLayout mCoordinatorLayout;
    private Toolbar mToolbar;
    private DrawerLayout mNavigationDrawer;
    private RecyclerView mRecyclerView;
    private SearchView mSearchView;
    private MenuItem searchMenuItem;
    private String mSearchText = "";
    private boolean mSearchTextSubmit = false;

    private DataManager mDataManager;
    private UsersAdapter mUsersAdapter;
    private ArrayList<UserListRes.Datum> mUsers;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        searchMenuItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) searchMenuItem.getActionView();

        mSearchView.setOnQueryTextListener(this);
//        return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        this.setTitle(getString(R.string.team));
        setContentView(R.layout.activity_user_list);

        mDataManager = DataManager.getInstance();
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_coordinator_content_users);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_users);
        mNavigationDrawer = (DrawerLayout) findViewById(R.id.navigation_drawer_users);
        mRecyclerView = (RecyclerView) findViewById(R.id.user_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        setupToolbar();
        setupDrawer();
        loadUsers();
    }

    private void showSnackBar(String message) {
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mNavigationDrawer.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }


    private void loadUsers() {
        showProgress();

        Call<UserListRes> call = mDataManager.getUserList();

        call.enqueue(new Callback<UserListRes>() {
            @Override
            public void onResponse(Call<UserListRes> call, Response<UserListRes> response) {

                try {
                    if (mSearchText.isEmpty()) {
                        mUsers = (ArrayList<UserListRes.Datum>) response.body().getData();
                    } else {
                        ArrayList<UserListRes.Datum> usersTemp;
                        mUsers.clear();
                        usersTemp = (ArrayList<UserListRes.Datum>) response.body().getData();
                        for (UserListRes.Datum datum : usersTemp) {
                            if (datum.getFullName().toUpperCase().contains(mSearchText.toUpperCase())) {
                                mUsers.add(datum);
                            }
                        }
                    }

                    mUsersAdapter = new UsersAdapter(mUsers, new UsersAdapter.UserViewHolder.CustomClickListener() {
                        @Override
                        public void onClickUserMore(int position) {
                            UserDTO userDTO = new UserDTO(mUsers.get(position));

                            Intent profileIntent = new Intent(UserListActivity.this, ProfileUserActivity.class);
                            profileIntent.putExtra(ConstantManager.PARCELABLE_KEY, userDTO);
                            startActivity(profileIntent);
                        }
                    });
                    mRecyclerView.setAdapter(mUsersAdapter);
                } catch (NullPointerException e) {
                    Log.e(TAG, e.toString());
                    showSnackBar("Что не так");
                }
                hideProgress();
                showSnackBar("Found " + mUsers.size() + " developers");
            }

            @Override
            public void onFailure(Call<UserListRes> call, Throwable t) {
                // TODO: 14.07.16 Обработка ошибок
            }
        });
    }

    private void setupDrawer() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view_users);

        ImageView navImgView = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.user_photo_drawer_img_nav);
        Picasso.with(this)
                .load(mDataManager.getPreferenceManager().loadAvatarPhoto())
                .resize(400,400)
                .centerCrop()
                .transform(new CircleTransform())
                .into(navImgView);

        TextView name, email;

        name = (TextView) navigationView.getHeaderView(0).findViewById(R.id.user_name_text_nav);
        email = (TextView) navigationView.getHeaderView(0).findViewById(R.id.user_email_text_nav);

        name.setText(mDataManager.getPreferenceManager().loadUserName());
        List<String> userData = mDataManager.getPreferenceManager().loadUserProfileData();
        email.setText(DataManager.getInstance().getPreferenceManager().getFieldFromKey(ConstantManager.USER_MAIL_KEY));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getTitle().toString().equals("Мой профиль")) {
                    finish();
                }
                item.setChecked(true);
                mNavigationDrawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });

    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mSearchTextSubmit = true;
        loadUsers();
        mSearchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mSearchText = newText;
        if (mSearchTextSubmit && newText.isEmpty()) {
            mSearchTextSubmit = false;
            loadUsers();
        }
        return true;
    }
}
