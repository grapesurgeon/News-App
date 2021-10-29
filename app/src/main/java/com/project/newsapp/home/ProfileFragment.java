package com.project.newsapp.home;

import static com.project.newsapp.Constants.EXTRA_ARTICLE;
import static com.project.newsapp.Constants.EXTRA_EMAIL;
import static com.project.newsapp.Constants.EXTRA_NAME;
import static com.project.newsapp.Constants.PROFILE_PREFERENCE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.ContactsContract;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.project.newsapp.LoginActivity;
import com.project.newsapp.R;
import com.project.newsapp.SessionManagerUtil;
import com.project.newsapp.databinding.ActivityDetailsBinding;
import com.project.newsapp.databinding.FragmentBookmarkBinding;
import com.project.newsapp.databinding.FragmentProfileBinding;
import com.project.newsapp.model.Article;
import com.project.newsapp.model.LoginResponse;
import com.project.newsapp.viewmodel.NewsVM;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private LoginResponse loginResponse;

    private SharedPreferences preferences;

    private String name;

    private String email;

    private NewsVM newsVM;

    public ProfileFragment() {

    }

   public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        preferences = getContext().getSharedPreferences(PROFILE_PREFERENCE, Context.MODE_PRIVATE);
        newsVM = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(NewsVM.class);
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
        initData();
        binding.textProfileName.setText(name);
        binding.textEmail.setText(email);
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutDialog();
            }
        });
        binding.editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditProfileNameDialog();
            }
        });
    }

    private void initData(){
        Bundle args = getArguments();
        name = args.getString(EXTRA_NAME);
        email = args.getString(EXTRA_EMAIL);

        if(!TextUtils.isEmpty(preferences.getString(EXTRA_NAME, null))){
            name = preferences.getString(EXTRA_NAME, null);
        }
    }

    private void showEditProfileNameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Edit Name");

        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                binding.textProfileName.setText(input.getText());
                //Value after edit not permanent
                preferences.edit().putString(EXTRA_NAME, input.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem item = menu.findItem(R.id.clear);
        if (item != null) item.setVisible(false);
    }


    private void showLogoutDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("Log Out")
                .setMessage("Do you want to Log Out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        newsVM.deleteAll();
                        SessionManagerUtil.getInstance().endUserSession(getActivity().getApplicationContext());
                        startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
                        getActivity().finish();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}