package com.naresh.interviewassignment.Repo.local_db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.naresh.interviewassignment.Repo.local_db.dao.HomeDao;
import com.naresh.interviewassignment.ui.main_screen.model.HomeModel;

@Database(entities = {HomeModel.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract HomeDao homeDao();
}