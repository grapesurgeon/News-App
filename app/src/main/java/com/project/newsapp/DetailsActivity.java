package com.project.newsapp;

import static com.project.newsapp.Constants.EXTRA_ARTICLE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.newsapp.databinding.ActivityDetailsBinding;
import com.project.newsapp.model.Article;

public class DetailsActivity extends AppCompatActivity {

    private ActivityDetailsBinding binding;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
        binding.bookmarked.setVisibility(View.INVISIBLE);

    }
}