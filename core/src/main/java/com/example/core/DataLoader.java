package com.example.core;

public interface DataLoader {
    void loadData(DataLoadedListener listener); //Async
    boolean isDataLoaded();
}
