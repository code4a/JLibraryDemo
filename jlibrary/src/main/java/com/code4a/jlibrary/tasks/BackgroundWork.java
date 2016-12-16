package com.code4a.jlibrary.tasks;

public interface BackgroundWork<T> {
    T doInBackground() throws Exception;
}
