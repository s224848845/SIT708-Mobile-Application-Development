package com.example.task_51c_media__content_apps.sportsnews.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_51c_media__content_apps.R;
import com.example.task_51c_media__content_apps.sportsnews.adapter.FeaturedAdapter;
import com.example.task_51c_media__content_apps.sportsnews.adapter.LatestNewsAdapter;
import com.example.task_51c_media__content_apps.sportsnews.data.DummyNewsData;
import com.example.task_51c_media__content_apps.sportsnews.model.NewsItem;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private List<NewsItem> latestNews;
    private List<NewsItem> filteredNews;
    private LatestNewsAdapter latestNewsAdapter;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView rvFeatured = view.findViewById(R.id.rvFeatured);
        RecyclerView rvLatest = view.findViewById(R.id.rvLatest);
        EditText etSearch = view.findViewById(R.id.etSearchCategory);
        Button btnBookmarks = view.findViewById(R.id.btnBookmarks);

        List<NewsItem> featuredMatches = DummyNewsData.getFeaturedMatches();
        latestNews = DummyNewsData.getLatestNews();
        filteredNews = new ArrayList<>(latestNews);

        FeaturedAdapter featuredAdapter =
                new FeaturedAdapter(featuredMatches, this::openDetailFragment);

        latestNewsAdapter =
                new LatestNewsAdapter(filteredNews, this::openDetailFragment);

        rvFeatured.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvFeatured.setAdapter(featuredAdapter);

        rvLatest.setLayoutManager(new LinearLayoutManager(getContext()));
        rvLatest.setAdapter(latestNewsAdapter);

        // Filter by category or title
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterNews(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnBookmarks.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, new BookmarksFragment())
                        .addToBackStack(null)
                        .commit()
        );

        return view;
    }

    private void filterNews(String text) {
        filteredNews.clear();

        for (NewsItem item : latestNews) {
            if (item.getCategory().toLowerCase().contains(text.toLowerCase())
                    || item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredNews.add(item);
            }
        }

        latestNewsAdapter.notifyDataSetChanged();
    }

    private void openDetailFragment(NewsItem item) {
        DetailFragment fragment = DetailFragment.newInstance(
                item.getImageResId(),
                item.getTitle(),
                item.getDescription(),
                item.getCategory()
        );

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit();
    }
}