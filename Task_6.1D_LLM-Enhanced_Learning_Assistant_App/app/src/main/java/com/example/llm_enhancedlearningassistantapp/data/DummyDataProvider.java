package com.example.llm_enhancedlearningassistantapp.data;

import com.example.llm_enhancedlearningassistantapp.models.Interest;

import java.util.ArrayList;
import java.util.List;

public class DummyDataProvider {

    public static List<Interest> getInterestList() {
        List<Interest> list = new ArrayList<>();
        list.add(new Interest("Algorithms", false));
        list.add(new Interest("Data Structures", false));
        list.add(new Interest("Web Development", false));
        list.add(new Interest("Testing", false));
        list.add(new Interest("Databases", false));
        list.add(new Interest("Mobile Development", false));
        list.add(new Interest("Cloud Computing", false));
        list.add(new Interest("Cybersecurity", false));
        return list;
    }
}