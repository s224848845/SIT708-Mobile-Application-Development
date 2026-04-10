package com.example.task_51c_media__content_apps.sportsnews.data;

import com.example.task_51c_media__content_apps.R;
import com.example.task_51c_media__content_apps.sportsnews.model.NewsItem;

import java.util.ArrayList;
import java.util.List;

public class DummyNewsData {

    public static List<NewsItem> getFeaturedMatches() {
        List<NewsItem> list = new ArrayList<>();
        list.add(new NewsItem(R.drawable.sports1, "Football Final Tonight",
                "Two top teams face off in the season final.", "Football"));
        list.add(new NewsItem(R.drawable.sports2, "Cricket Series Decider",
                "The final ODI will decide the series winner.", "Cricket"));
        list.add(new NewsItem(R.drawable.sports1, "Basketball Playoff Clash",
                "A high-intensity playoff battle is expected.", "Basketball"));
        return list;
    }

    public static List<NewsItem> getLatestNews() {
        List<NewsItem> list = new ArrayList<>();
        list.add(new NewsItem(R.drawable.sports3, "Football Transfer News",
                "A star striker may move next season.", "Football"));
        list.add(new NewsItem(R.drawable.sports4, "Cricket Captain Interview",
                "The captain shared the team’s strategy.", "Cricket"));
        list.add(new NewsItem(R.drawable.sports5, "Basketball Injury Update",
                "The key guard has returned to training.", "Basketball"));
        list.add(new NewsItem(R.drawable.sports6, "Football Training Report",
                "The squad looked sharp in training.", "Football"));
        list.add(new NewsItem(R.drawable.sports1, "Cricket Bowling Analysis",
                "Spinners are expected to dominate.", "Cricket"));
        return list;
    }
}