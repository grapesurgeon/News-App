package com.project.newsapp;

import static com.project.newsapp.Constants.EXTRA_ARTICLE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.newsapp.databinding.ActivityDetailsBinding;
import com.project.newsapp.home.NewsAdapter;
import com.project.newsapp.model.Article;
import com.project.newsapp.viewmodel.LoginVM;
import com.project.newsapp.viewmodel.NewsVM;

public class DetailsActivity extends AppCompatActivity {

    private ActivityDetailsBinding binding;
    private ImageView iv;
    private NewsVM newsVM;
    private NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        newsVM = new ViewModelProvider(this).get(NewsVM.class);

        Intent i = getIntent();
        Article article = (Article) i.getSerializableExtra(EXTRA_ARTICLE);

        iv = binding.imageView;
        Glide.with(iv.getContext())
                .load(article.getUrlToImage())
                .into(iv);

        binding.tvTitle.setText(article.getTitle());
        binding.tvContent.setText(article.getContent());
        binding.tvCategory.setText(article.getCategory());
        binding.tvPublishedAt.setText(article.getPublishedAt());
        readMoreLink(article);
        isBookmarked(article);
        shareLink(article);
    }

    private void shareLink(Article article) {
        binding.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
                i.putExtra(Intent.EXTRA_TEXT, "NewsApp : " + article.getUrl());
                startActivity(Intent.createChooser(i, "Share URL"));
            }
        });
    }

    private void readMoreLink(Article article) {
        binding.readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(article.getUrl()));
                startActivity(i);
            }
        });
    }

    private void isBookmarked(Article article) {
        if (article.getBookmark()){
            binding.notBookmarked.setVisibility(View.GONE);
            binding.bookmarked.setVisibility(View.VISIBLE);
            binding.bookmarked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newsVM.bookmark(article.getUrl());
                    binding.bookmarked.setVisibility(View.GONE);
                    binding.notBookmarked.setVisibility(View.VISIBLE);
                }
            });
        } else{
            binding.bookmarked.setVisibility(View.GONE);
            binding.notBookmarked.setVisibility(View.VISIBLE);
            binding.notBookmarked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.notBookmarked.setVisibility(View.GONE);
                    binding.bookmarked.setVisibility(View.VISIBLE);
                    newsVM.bookmark(article.getUrl());
                }
            });
        }
    }
}