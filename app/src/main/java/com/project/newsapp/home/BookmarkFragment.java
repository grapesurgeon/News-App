package com.project.newsapp.home;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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
import android.widget.FrameLayout;
import android.widget.SearchView;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.project.newsapp.DetailsActivity;
import com.project.newsapp.R;
import com.project.newsapp.databinding.FragmentBookmarkBinding;
import com.project.newsapp.model.Article;
import com.project.newsapp.viewmodel.NewsVM;

import java.util.ArrayList;
import java.util.List;

public class BookmarkFragment extends Fragment {

    private FragmentBookmarkBinding binding;

    private SearchView sv;

    private RecyclerView rv;

    private CircularProgressIndicator progressBar;

    private FrameLayout flEmpty;

    private NewsAdapter adapter;

    private NewsVM newsVM;

    public BookmarkFragment() {
        // Required empty public constructor
    }

    public static BookmarkFragment newInstance() {
        return new BookmarkFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBookmarkBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        initView();
        initRv();
        initSearchView();
        showLoadingScreen();
        initData();
    }

    private void initViewModel(){
        newsVM = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getActivity().getApplication())).get(NewsVM.class);
    }

    private void initView(){
        sv = binding.sv;
        rv = binding.rv;
        progressBar = binding.progressBar;
        flEmpty = binding.flEmpty;
    }

    private void initRv(){
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.hasFixedSize();
        adapter = new NewsAdapter(new ArrayList<>());
        rv.setAdapter(adapter);
        adapter.setClickListener(new NewsAdapter.ClickListener() {
            @Override
            public void onItemClicked(Article article) {
                goToDetails(article);
            }

            @Override
            public void onDeleteClicked(Article article) {

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

    private void initData(){
        observeData();
    }

    private void observeData(){
        Observer<List<Article>> observer = articles -> {
            adapter.setItems(articles);
            if(articles.isEmpty()) showLayoutEmpty();
            else showNews();
        };
        newsVM.getNews().observe(getViewLifecycleOwner(), observer);
    }

    private void goToDetails(Article article){
        Intent i = new Intent(getActivity(), DetailsActivity.class);
        //TODO add extras
        startActivity(i);
    }

    private void showLoadingScreen(){
        rv.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void showNews(){
        rv.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    private void showLayoutEmpty(){
        rv.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        flEmpty.setVisibility(View.VISIBLE);
    }
}