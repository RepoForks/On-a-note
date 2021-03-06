package bov.vitali.onanote.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import bov.vitali.onanote.model.ModelTask;

/**
 * Created by vitali on 21.01.17.
 */
public class DBQueryManager {

    private SQLiteDatabase database;

    DBQueryManager(SQLiteDatabase database){
        this.database = database;
    }

    public ModelTask getTask(long timeStamp) {
        ModelTask modelTask = null;
        Cursor cursor = database.query(DBHelper.TASKS_TABLE, null, DBHelper.SELECTOIN_TIME_STAMP,
                new String[] {Long.toString(timeStamp)}, null, null, null);

        if (cursor.moveToFirst()) {
            String title = cursor.getString(cursor.getColumnIndex(DBHelper.TASK_TITLE_COLUMN));
            long date = cursor.getLong(cursor.getColumnIndex(DBHelper.TASK_DATE_COLUMN));
            int priority = cursor.getInt(cursor.getColumnIndex(DBHelper.TASK_PRIORITY_COLUMN));
            int status = cursor.getInt(cursor.getColumnIndex(DBHelper.TASK_STATUS_COLUMN));

            modelTask = new ModelTask(title, date, priority, status, timeStamp);
        }
        cursor.close();
        return modelTask;
    }

    public List<ModelTask> getTasks(String selection, String[] selectionArgs, String orderBy) {
        List<ModelTask> tasks = new ArrayList<>();

        Cursor cursor = database.query(DBHelper.TASKS_TABLE, null, selection, selectionArgs, null, null, orderBy);

        if(cursor.moveToFirst()){
            do{
                String title = cursor.getString(cursor.getColumnIndex(DBHelper.TASK_TITLE_COLUMN));
                long date = cursor.getLong(cursor.getColumnIndex(DBHelper.TASK_DATE_COLUMN));
                int priority = cursor.getInt(cursor.getColumnIndex(DBHelper.TASK_PRIORITY_COLUMN));
                int status = cursor.getInt(cursor.getColumnIndex(DBHelper.TASK_STATUS_COLUMN));
                long timeStamp = cursor.getLong(cursor.getColumnIndex(DBHelper.TASK_TIME_STAMP_COLUMN));

                ModelTask modelTask = new ModelTask(title, date, priority, status, timeStamp);
                tasks.add(modelTask);

            }while(cursor.moveToNext());
        }
        cursor.close();

        return tasks;
    }

}