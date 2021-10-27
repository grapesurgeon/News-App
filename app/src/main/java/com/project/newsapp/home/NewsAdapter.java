package com.project.newsapp.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.newsapp.R;
import com.project.newsapp.model.Article;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<Article> articles;

    private ClickListener listener;

    public NewsAdapter(List<Article> articles) {
        this.articles = articles;
    }

    public void setClickListener(ClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.iv.setImageBitmap(); //TODO get from url
        holder.title.setText(articles.get(position).getTitle());
        holder.time.setText(getTime(articles.get(position).getPublishedAt()));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public Article getItem(int position){
        return articles.get(position);
    }

    public void setItems(List<Article> articles){
        this.articles = articles;
        notifyDataSetChanged();
    }

    public void removeItem(int position){
        articles.remove(position);
        notifyDataSetChanged();
    }

    private String getTime(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date now = new Date();
        try {
            Date published = sdf.parse(time);

            long mlDiff = Math.abs(published.getTime() - now.getTime());
            long diffDay = TimeUnit.DAYS.convert(mlDiff, TimeUnit.MILLISECONDS);
            long diffHour = TimeUnit.HOURS.convert(mlDiff, TimeUnit.MILLISECONDS);
            long diffMin = TimeUnit.MINUTES.convert(mlDiff, TimeUnit.MILLISECONDS);

            if(diffDay > 0) return diffDay + " days ago";
            else if(diffHour > 0) return diffHour + " hours ago";
            else if(diffMin > 0) return diffMin + " minutes ago";

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "Just now";
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView iv;

        ImageView ivDelete;

        TextView title;

        TextView time;

        public ViewHolder(@NonNull View v) {
            super(v);
            iv = v.findViewById(R.id.iv);
            ivDelete = v.findViewById(R.id.iv_delete);
            title = v.findViewById(R.id.tv_title);
            time = v.findViewById(R.id.tv_time);

            v.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    listener.onItemClicked(articles.get(position));
                }
            });

            ivDelete.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    listener.onDeleteClicked(articles.get(position));
                }
            });
        }
    }

    interface ClickListener{
        void onItemClicked(Article article);
        void onDeleteClicked(Article article);
    }
}
