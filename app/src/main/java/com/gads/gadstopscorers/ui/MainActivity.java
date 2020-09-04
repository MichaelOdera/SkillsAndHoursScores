package com.gads.gadstopscorers.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.gads.gadstopscorers.R;
import com.gads.gadstopscorers.adapters.GadsViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.viewPager) ViewPager mViewPager;
    @BindView(R.id.tab_layout) TabLayout mTabLayout;
    private GadsViewPagerAdapter mGadsViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mGadsViewPagerAdapter = new GadsViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        mViewPager.setAdapter(mGadsViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.submitButton);

        item.getActionView().findViewById(R.id.submitActionView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent submitIntent = new Intent(MainActivity.this, SubmissionActivity.class);
                startActivity(submitIntent);
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


}
