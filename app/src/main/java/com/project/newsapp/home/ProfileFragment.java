package com.project.newsapp.home;

import static com.project.newsapp.Constants.EXTRA_ARTICLE;
import static com.project.newsapp.Constants.EXTRA_EMAIL;
import static com.project.newsapp.Constants.EXTRA_NAME;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.project.newsapp.R;
import com.project.newsapp.databinding.ActivityDetailsBinding;
import com.project.newsapp.databinding.FragmentBookmarkBinding;
import com.project.newsapp.databinding.FragmentProfileBinding;
import com.project.newsapp.model.Article;
import com.project.newsapp.model.LoginResponse;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    public ProfileFragment() {

    }

   public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem item = menu.findItem(R.id.clear);
        if(item != null) item.setVisible(false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("asdf", "onViewCreated: ");
        Bundle i = getArguments();
        binding.textProfileName.setText(i.getString(EXTRA_NAME));
        binding.textEmail.setText(i.getString(EXTRA_EMAIL));
    }
}