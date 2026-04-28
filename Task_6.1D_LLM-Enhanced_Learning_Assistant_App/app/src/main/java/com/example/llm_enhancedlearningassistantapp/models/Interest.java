package com.example.llm_enhancedlearningassistantapp.models;

public class Interest {
    private final String name;
    private boolean selected;

    public Interest(String name, boolean selected) {
        this.name = name;
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}