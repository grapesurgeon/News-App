package com.project.newsapp.home;

import static com.project.newsapp.Constants.API_KEY;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

    private SearchView sv;

    private TabLayout tabLayout;

    private RecyclerView rv;

    private CircularProgressIndicator progressBar;

    private FrameLayout flError;

    private NewsAdapter adapter;

    private NewsVM newsVM;

    private static final String[] categories = {"Business", "Health", "Food", "Entertainment", "Style", "Travel", "Sports" };

    public ListFragment() {
    }

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);
        errorBinding = binding.flContent;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        initView();
        initRv();
        initTab();
        initSearchView();
//        observeData(categories[0]);
        queryData(categories[tabLayout.getSelectedTabPosition()]);
        Log.d(TAG, "onViewCreated: " + tabLayout.getSelectedTabPosition());
    }

    private void initViewModel(){
        newsVM = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getActivity().getApplication())).get(NewsVM.class);
    }

    private void initView(){
        sv = binding.sv;
        tabLayout = binding.tabs;
        progressBar = binding.progressBar;
        flError = binding.flError;
        errorBinding.btnRetry.setOnClickListener(v -> queryData(categories[tabLayout.getSelectedTabPosition()]));
    }

    private void initRv(){
        rv = binding.rv;
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.hasFixedSize();
        adapter = new NewsAdapter(new ArrayList<>());
        rv.setAdapter(adapter);
        adapter.setClickListener(new NewsAdapter.ClickListener() {
            @Override
            public void onItemClicked(Article article) {
                //TODO go to detail activity
                Log.d(TAG, "onItemClicked: ");
                Intent i = new Intent(getActivity(), DetailsActivity.class);
                startActivity(i);
            }

            @Override
            public void onDeleteClicked(Article article) {
                //TODO delete article
                Log.d(TAG, "onDeleteClicked: ");
            }
        });
    }

    private void initTab(){
        for(String str : categories){
            tabLayout.addTab(tabLayout.newTab().setText(str));
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(TAG, "onTabSelected: " + tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initSearchView(){
        SearchManager sm = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        sv.setSearchableInfo(sm.getSearchableInfo(getActivity().getComponentName()));
        sv.setIconifiedByDefault(true);
        sv.setMaxWidth(Integer.MAX_VALUE);
        sv.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
//                presenter.search(s.toLowerCase());
                sv.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
//                presenter.search(s.toLowerCase());
                return false;
            }
        });
    }

    private void observeData(String category){
        newsVM.getNews(category).removeObservers(getViewLifecycleOwner());
        Observer<List<Article>> observer = articles -> adapter.setItems(articles);
        newsVM.getNews(category).observe(getViewLifecycleOwner(), observer);
//        newsVM.getNews(category).observe(getViewLifecycleOwner(), new Observer<List<Article>>() {
//            @Override
//            public void onChanged(List<Article> articles) {
//                adapter.setItems(articles);
//            }
//        });
    }

    private void queryData(String query) {
        showLoadingScreen();

        String sort = "publishedAt"; // popularity, relevancy, popularity
        observeData(query);
        newsVM.getRetrofitInstance()
                .getNewsApi()
                .getNews(query, sort, API_KEY)
                .enqueue(new Callback<NewsResponse>() {
                    @Override
                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                        List<Article> articles = NewsResponseMapper.transform(response.body(), query);
                        if(articles.isEmpty()){
                            showError();
                        } else{
                            newsVM.insertAll(articles);
                            showNews();
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) {

                    }
                });
    }

    private void showLoadingScreen(){
        flError.setVisibility(View.GONE);
        rv.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void showNews(){
        flError.setVisibility(View.GONE);
        rv.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    private void showError(){
        flError.setVisibility(View.VISIBLE);
        rv.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

}