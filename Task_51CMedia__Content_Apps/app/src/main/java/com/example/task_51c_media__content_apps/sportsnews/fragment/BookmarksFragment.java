package com.example.task_51c_media__content_apps.sportsnews.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_51c_media__content_apps.R;
import com.example.task_51c_media__content_apps.sportsnews.adapter.LatestNewsAdapter;
import com.example.task_51c_media__content_apps.sportsnews.data.BookmarkManager;
import com.example.task_51c_media__content_apps.sportsnews.model.NewsItem;

import java.util.List;

public class BookmarksFragment extends Fragment {

    public BookmarksFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bookmarks, container, false);

        RecyclerView rvBookmarks = view.findViewById(R.id.rvBookmarks);
        TextView tvEmpty = view.findViewById(R.id.tvEmptyBookmarks);

        List<NewsItem> bookmarks = BookmarkManager.getBookmarks(requireContext());

        if (bookmarks.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            tvEmpty.setVisibility(View.GONE);
        }

        rvBookmarks.setLayoutManager(new LinearLayoutManager(getContext()));
        rvBookmarks.setAdapter(new LatestNewsAdapter(bookmarks, item -> {
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
        }));

        return view;
    }
}