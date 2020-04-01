package com.kishan.heady_test_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    private String title;

    protected void setTitle(String title) {
        this.title = title;
        updateTitle();
    }

    public void updateTitle() {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(title);
    }
}
