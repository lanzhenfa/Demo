package com.me.demo.database;

/**
 * Create by lzf on 5/19/21
 */
public interface IDatabaseContract {

    interface IDatabaseView {
    }

    interface IDatabasePresenter {

        void createDatabase();

        void add();

        void delete();

        void update();

        void query();
    }


}
