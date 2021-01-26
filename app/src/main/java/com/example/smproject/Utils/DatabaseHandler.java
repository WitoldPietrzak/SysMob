package com.example.smproject.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.smproject.User;
import com.example.smproject.tasks.ProgressiveTask;
import com.example.smproject.tasks.SingleTask;
import com.example.smproject.tasks.StepTask;
import com.example.smproject.tasks.Task;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SMDB";
    private static final String TABLE_1_NAME = "Users";
    private static final String TABLE_2_NAME = "Tasks";
    private static final String[] TABLE_1_FIELDS = {"userName", "userData"};
    private static final String[] TABLE_2_FIELDS = {"taskID", "taskData"};

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {



        String CREATION_TABLE_1 = "CREATE TABLE IF NOT EXISTS " +
                "Users" +
                "(" +
                "userName TEXT PRIMARY KEY," +
                "userData BLOB NOT NULL" +
                "); ";

        String CREATION_TABLE_2 = "CREATE TABLE IF NOT EXISTS " +
                "Tasks" +
                "(" +
                "taskID TEXT PRIMARY KEY," +
                "taskData BLOB NOT NULL" +
                "); ";

        db.execSQL(CREATION_TABLE_1);
        db.execSQL(CREATION_TABLE_2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_1_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_2_NAME);

    }


    public void addUser(User user) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_1_FIELDS[0], user.getUserName());
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(user);
            values.put(TABLE_1_FIELDS[1], baos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        database.insert(TABLE_1_NAME, null, values);
        database.close();

    }

    public void addTask(Task task) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_2_FIELDS[0], task.getTaskID().toString());
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(task);
            values.put(TABLE_2_FIELDS[1], baos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        database.insert(TABLE_2_NAME, null, values);
        database.close();

    }


    public User getUser(String userName) {
        SQLiteDatabase database = this.getReadableDatabase();
        String[] conditions = {userName};
        Cursor cursor = database.query(TABLE_1_NAME, TABLE_1_FIELDS, TABLE_1_FIELDS[0] + " = ?", conditions, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            try {
                ByteArrayInputStream in = new ByteArrayInputStream(cursor.getBlob(1));
                ObjectInputStream is = new ObjectInputStream(in);
                database.close();
                cursor.close();
                return (User) is.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        database.close();
        return null;
    }

    public Task getTask(UUID taskID) {
        SQLiteDatabase database = this.getReadableDatabase();
        String[] conditions = {taskID.toString()};
        Cursor cursor = database.query(TABLE_2_NAME, TABLE_2_FIELDS, TABLE_2_FIELDS[0] + " = ?", conditions, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            try {
                ByteArrayInputStream in = new ByteArrayInputStream(cursor.getBlob(1));
                ObjectInputStream is = new ObjectInputStream(in);
                database.close();
                cursor.close();
                return (Task) is.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        database.close();
        return null;
    }

    public void updateUser(User user) {
        SQLiteDatabase database = this.getWritableDatabase();
        String[] conditions = {user.getUserName()};
        ContentValues values = new ContentValues();
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(user);
            values.put(TABLE_1_FIELDS[1], baos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        database.update(TABLE_1_NAME, values, TABLE_1_FIELDS[0] + " = ?", conditions);
        database.close();

    }

    public void updateTask(Task task) {
        SQLiteDatabase database = this.getWritableDatabase();
        String[] conditions = {task.getTaskID().toString()};
        ContentValues values = new ContentValues();
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(task);
            values.put(TABLE_2_FIELDS[1], baos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        database.update(TABLE_2_NAME, values, TABLE_2_FIELDS[0] + " = ?", conditions);
        database.close();
    }

    public void deleteUser(User user) {
        SQLiteDatabase database = this.getWritableDatabase();
        String[] conditions = {user.getUserName()};
        database.delete(TABLE_1_NAME, TABLE_1_FIELDS[0] + "= ?", conditions);
        database.close();
    }

    public void deleteTask(Task task) {
        SQLiteDatabase database = this.getWritableDatabase();
        String[] conditions = {String.valueOf(task.getTaskID().toString())};
        database.delete(TABLE_2_NAME, TABLE_2_FIELDS[0] + "= ?", conditions);
        database.close();
    }


    public List<Task> getAllTasks() {
        SQLiteDatabase database = this.getReadableDatabase();
        List<Task> tasks = new ArrayList<Task>();
        Cursor cursor = database.query(TABLE_2_NAME, TABLE_2_FIELDS, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                try {
                    ByteArrayInputStream in = new ByteArrayInputStream(cursor.getBlob(1));
                    ObjectInputStream is = new ObjectInputStream(in);
                    database.close();
                    cursor.close();
                    tasks.add((Task) is.readObject());
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                cursor.move(1);
            }
            cursor.close();
            database.close();
            return tasks;
        } else {
            return null;
        }
    }

}
