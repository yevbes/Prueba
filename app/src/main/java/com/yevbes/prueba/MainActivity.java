package com.yevbes.prueba;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yevbes.prueba.fragments.PostListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            Fragment fragment = new PostListFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainer,fragment)
                    .commit();

        }
    }
}
