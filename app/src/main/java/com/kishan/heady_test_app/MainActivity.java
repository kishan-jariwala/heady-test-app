package com.kishan.heady_test_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.kishan.heady_test_app.repository.CategoryListRepository;
import com.kishan.heady_test_app.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }

        getSupportFragmentManager().addOnBackStackChangedListener(onBackStackChangedListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportFragmentManager().removeOnBackStackChangedListener(onBackStackChangedListener);
    }

    private FragmentManager.OnBackStackChangedListener onBackStackChangedListener = () -> {
        BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        fragment.updateTitle();
    };
}
