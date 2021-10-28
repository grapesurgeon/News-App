package com.project.newsapp.home;

import static com.project.newsapp.Constants.API_KEY;
import static com.project.newsapp.Constants.DB_FILLED;
import static com.project.newsapp.Constants.DB_PREFERENCE;
import static com.project.newsapp.Constants.EXTRA_ARTICLE;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.SearchView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.tabs.TabLayout;
import com.project.newsapp.DetailsActivity;
import com.project.newsapp.LoginActivity;
import com.project.newsapp.R;
import com.project.newsapp.databinding.FragmentListBinding;
import com.project.newsapp.databinding.LayoutErrorBinding;
import com.project.newsapp.model.Article;
import com.project.newsapp.model.NewsResponse;
import com.project.newsapp.util.NewsResponseMapper;
import com.project.newsapp.viewmodel.NewsVM;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFragment extends Fragment {
    public static final String TAG = "asdf";

    private FragmentListBinding binding;

    private LayoutErrorBinding errorBinding;

    private SharedPreferences preferences;

    private SearchView sv;

    private TabLayout tabLayout;

    private RecyclerView rv;

    private CircularProgressIndicator progressBar;

    private FrameLayout flError;

    private NewsAdapter adapter;

    private NewsVM newsVM;

    private static final String[] categories = {"Business", "Health", "Food", "Entertainment", "Style", "Travel", "Sports"};

    public ListFragment() {
    }

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);
        errorBinding = binding.flContent;
        return binding.getRoot();
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem item = menu.findItem(R.id.clear);
        if(item != null) item.setVisible(false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        preferences = getContext().getSharedPreferences(DB_PREFERENCE, Context.MODE_PRIVATE);

        initViewModel();
        initView();
        initRv();
        initTab();
        initSearchView();
        showLoadingScreen();
        initData();
    }

    private void initViewModel() {
        newsVM = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(NewsVM.class);
    }

    private void initView() {
        sv = binding.sv;
        tabLayout = binding.tabs;
        rv = binding.rv;
        progressBar = binding.progressBar;
        flError = binding.flError;
        errorBinding.btnRetry.setOnClickListener(v -> queryData(categories[tabLayout.getSelectedTabPosition()]));
    }

    private void initRv() {
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.hasFixedSize();
        adapter = new NewsAdapter(new ArrayList<>());
        rv.setAdapter(adapter);
        adapter.setClickListener(new NewsAdapter.ClickListener() {
            @Override
            public void onItemClicked(Article article) {
                Log.d(TAG, "onItemClicked: ");
                goToDetails(article);
            }

            @Override
            public void onDeleteClicked(Article article) {
                Log.d(TAG, "onDeleteClicked: ");
                newsVM.delete(article);
            }
        });
    }

    private void initTab() {
        for (String str : categories) {
            tabLayout.addTab(tabLayout.newTab().setText(str));
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                queryData(categories[tabLayout.getSelectedTabPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initSearchView() {
        SearchManager sm = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        sv.setSearchableInfo(sm.getSearchableInfo(getActivity().getComponentName()));
        sv.setIconifiedByDefault(true);
        sv.setMaxWidth(Integer.MAX_VALUE);
        sv.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                observeSearchData(categories[tabLayout.getSelectedTabPosition()], s);
                sv.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(TextUtils.isEmpty(s)) observeData(categories[tabLayout.getSelectedTabPosition()]);
                return false;
            }
        });
    }

    private void initData() {
        if (preferences.getBoolean(DB_FILLED, false)) { // have data
            Log.d(TAG, "initData: have data");
            observeData(categories[tabLayout.getSelectedTabPosition()]);
        } else { // no data
            Log.d(TAG, "initData: no data");
            queryData(categories[tabLayout.getSelectedTabPosition()]);
            preferences.edit().putBoolean(DB_FILLED, true).apply();
        }

    }

    private void observeData(String category) {
        removeObservers(category, null);
        Observer<List<Article>> observer = articles -> {
            adapter.setItems(articles);
            showNews();
        };
        newsVM.getNews(category).observe(getViewLifecycleOwner(), observer);
    }

    private void observeSearchData(String category, String s){
        removeObservers(category, s);
        Observer<List<Article>> observer = articles -> {
            adapter.setItems(articles);
            showNews();
        };
        newsVM.getNews(category, s).observe(getViewLifecycleOwner(), observer);
    }

    private void removeObservers(String category, String s){
        newsVM.getNews(category).removeObservers(getViewLifecycleOwner());
        newsVM.getNews(category, s).removeObservers(getViewLifecycleOwner());
    }

    private void queryData(String query) {
        showLoadingScreen();

        String sort = "publishedAt"; // popularity, relevancy, popularity
        observeData(query);
        newsVM.getRetrofitInstance()
                .getNewsApi()
                .getNews(query, sort, API_KEY, 20)
                .enqueue(new Callback<NewsResponse>() {
                    @Override
                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                        List<Article> articles = NewsResponseMapper.transform(response.body(), query);
                        if (articles.isEmpty()) showError();
                        else newsVM.insertAll(articles);

                    }

                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) {
                        showError();
                        preferences.edit().remove(DB_FILLED).apply();
                    }
                });
    }

    private void goToDetails(Article article) {
        Intent i = new Intent(getActivity(), DetailsActivity.class);
        i.putExtra(EXTRA_ARTICLE, article);
        startActivity(i);
    }

    private void showLoadingScreen() {
        flError.setVisibility(View.GONE);
        rv.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void showNews() {
        flError.setVisibility(View.GONE);
        rv.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    private void showError() {
        flError.setVisibility(View.VISIBLE);
        rv.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

}