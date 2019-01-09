package com.example.phosphor.idecided;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.phosphor.idecided.Fragments.LeaderCommentsFragment;
import com.example.phosphor.idecided.Fragments.LeaderManifestoFragment;
import com.example.phosphor.idecided.Fragments.LeaderProfileFragment;
import com.example.phosphor.idecided.SharedPrefs.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class LeaderDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_details);

        ViewPager viewPager = findViewById(R.id.leader_detail_viewpager);
        setViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.leader_detail_tablayout);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setViewPager (ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment("PROFILE", new LeaderProfileFragment());
        adapter.addFragment("MANIFESTO", new LeaderManifestoFragment());
        adapter.addFragment("COMMENTS", new LeaderCommentsFragment());
        viewPager.setAdapter(adapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        List<String> fragmentTitle = new ArrayList<>();
        List<Fragment> fragmentList = new ArrayList<>();


        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        void addFragment(String title, Fragment fragment) {
            fragmentTitle.add(title);
            fragmentList.add(fragment);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuLogout:
                signOut();
                break;

            case R.id.menuSettings:
                Toast.makeText(this, "You clicked settings", Toast.LENGTH_LONG).show();
        }
        return true;
    }
}
