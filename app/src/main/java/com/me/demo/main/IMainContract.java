package com.me.demo.main;

import java.util.LinkedList;

/**
 * Create by lzf on 2021-03-30
 */
public interface IMainContract {

    interface IMainView {
        void updateMainRecycler(LinkedList<String> items);
    }
}
