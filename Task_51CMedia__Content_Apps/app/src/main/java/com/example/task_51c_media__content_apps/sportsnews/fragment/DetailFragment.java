package com.example.task_51c_media__content_apps.sportsnews.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_51c_media__content_apps.R;
import com.example.task_51c_media__content_apps.sportsnews.adapter.RelatedStoriesAdapter;
import com.example.task_51c_media__content_apps.sportsnews.data.BookmarkManager;
import com.example.task_51c_media__content_apps.sportsnews.data.DummyNewsData;
import com.example.task_51c_media__content_apps.sportsnews.model.NewsItem;

import java.util.ArrayList;
import java.util.List;

public class DetailFragment extends Fragment {

    public static DetailFragment newInstance(int imageResId, String title,
                                             String description, String category) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt("image", imageResId);
        args.putString("title", title);
        args.putString("description", description);
        args.putString("category", category);
        fragment.setArguments(args);
        return fragment;
    }

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        ImageView ivImage = view.findViewById(R.id.ivDetailImage);
        TextView tvTitle = view.findViewById(R.id.tvDetailTitle);
        TextView tvDescription = view.findViewById(R.id.tvDetailDescription);
        Button btnBookmark = view.findViewById(R.id.btnBookmark);
        RecyclerView rvRelated = view.findViewById(R.id.rvRelatedStories);

        Bundle args = getArguments();
        if (args == null) return view;

        int image = args.getInt("image");
        String title = args.getString("title");
        String description = args.getString("description");
        String category = args.getString("category");

        ivImage.setImageResource(image);
        tvTitle.setText(title);
        tvDescription.setText(description);

        List<NewsItem> relatedStories = new ArrayList<>();
        for (NewsItem item : DummyNewsData.getLatestNews()) {
            if (item.getCategory().equalsIgnoreCase(category)
                    && !item.getTitle().equalsIgnoreCase(title)) {
                relatedStories.add(item);
            }
        }

        rvRelated.setLayoutManager(new LinearLayoutManager(getContext()));
        rvRelated.setAdapter(new RelatedStoriesAdapter(relatedStories, item -> {
            DetailFragment nextFragment = DetailFragment.newInstance(
                    item.getImageResId(),
                    item.getTitle(),
                    item.getDescription(),
                    item.getCategory()
            );

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, nextFragment)
                    .addToBackStack(null)
                    .commit();
        }));

        btnBookmark.setOnClickListener(v -> {
            BookmarkManager.saveBookmark(requireContext(),
                    new NewsItem(image, title, description, category));

            Toast.makeText(getContext(), "Story bookmarked", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}