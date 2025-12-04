package com.example.a122ad.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.a122ad.model.Note;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 数据库帮助类，用于创建和管理笔记数据库
 */
public class NoteDbHelper extends SQLiteOpenHelper {
    // 数据库名称
    private static final String DATABASE_NAME = "notes.db";
    // 数据库版本
    private static final int DATABASE_VERSION = 2;
    // 笔记表名
    public static final String TABLE_NOTES = "notes";
    // 列名
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_CREATED_AT = "created_at";
    public static final String COLUMN_UPDATED_AT = "updated_at";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_IMAGE_PATH = "image_path";
    // 日期格式
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 创建表的SQL语句
    private static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NOTES + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TITLE + " TEXT NOT NULL, " +
            COLUMN_CONTENT + " TEXT, " +
            COLUMN_CREATED_AT + " TEXT NOT NULL, " +
            COLUMN_UPDATED_AT + " TEXT NOT NULL, " +
            COLUMN_CATEGORY + " TEXT DEFAULT '默认分类', " +
            COLUMN_IMAGE_PATH + " TEXT)";

    public NoteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            // 创建笔记表
            db.execSQL(SQL_CREATE_TABLE);
        } catch (Exception e) {
            e.printStackTrace();
            // 数据库创建失败，记录错误
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            if (oldVersion < 2) {
                db.execSQL("ALTER TABLE " + TABLE_NOTES + " ADD COLUMN " + COLUMN_IMAGE_PATH + " TEXT");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 数据库升级失败，记录错误
        }
    }

    /**
     * 添加新笔记
     */
    public long addNote(Note note) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            if (db == null) {
                return -1;
            }
            
            ContentValues values = new ContentValues();
            values.put(COLUMN_TITLE, note.getTitle() != null ? note.getTitle() : "");
            values.put(COLUMN_CONTENT, note.getContent() != null ? note.getContent() : "");
            values.put(COLUMN_CREATED_AT, dateFormat.format(note.getCreatedAt() != null ? note.getCreatedAt() : new Date()));
            values.put(COLUMN_UPDATED_AT, dateFormat.format(note.getUpdatedAt() != null ? note.getUpdatedAt() : new Date()));
            values.put(COLUMN_CATEGORY, note.getCategory() != null ? note.getCategory() : "默认分类");
            values.put(COLUMN_IMAGE_PATH, note.getImagePath());

            // 插入新行并返回ID
            long id = db.insert(TABLE_NOTES, null, values);
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    /**
     * 获取所有笔记
     */
    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        
        try {
            db = this.getReadableDatabase();
            if (db == null) {
                return notes;
            }
            
            String selectQuery = "SELECT * FROM " + TABLE_NOTES + " ORDER BY " + COLUMN_UPDATED_AT + " DESC";
            cursor = db.rawQuery(selectQuery, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    try {
                        Note note = new Note();
                        note.setId(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                        
                        // 安全获取字符串值
                        int titleIndex = cursor.getColumnIndex(COLUMN_TITLE);
                        if (titleIndex >= 0 && !cursor.isNull(titleIndex)) {
                            note.setTitle(cursor.getString(titleIndex));
                        }
                        
                        int contentIndex = cursor.getColumnIndex(COLUMN_CONTENT);
                        if (contentIndex >= 0 && !cursor.isNull(contentIndex)) {
                            note.setContent(cursor.getString(contentIndex));
                        }
                        
                        int categoryIndex = cursor.getColumnIndex(COLUMN_CATEGORY);
                        if (categoryIndex >= 0 && !cursor.isNull(categoryIndex)) {
                            note.setCategory(cursor.getString(categoryIndex));
                        } else {
                            note.setCategory("默认分类");
                        }

                        int imagePathIndex = cursor.getColumnIndex(COLUMN_IMAGE_PATH);
                        if (imagePathIndex >= 0 && !cursor.isNull(imagePathIndex)) {
                            note.setImagePath(cursor.getString(imagePathIndex));
                        }
                        
                        // 安全处理日期
                        int createdAtIndex = cursor.getColumnIndex(COLUMN_CREATED_AT);
                        if (createdAtIndex >= 0 && !cursor.isNull(createdAtIndex)) {
                            try {
                                note.setCreatedAt(dateFormat.parse(cursor.getString(createdAtIndex)));
                            } catch (ParseException e) {
                                note.setCreatedAt(new Date());
                            }
                        } else {
                            note.setCreatedAt(new Date());
                        }
                        
                        int updatedAtIndex = cursor.getColumnIndex(COLUMN_UPDATED_AT);
                        if (updatedAtIndex >= 0 && !cursor.isNull(updatedAtIndex)) {
                            try {
                                note.setUpdatedAt(dateFormat.parse(cursor.getString(updatedAtIndex)));
                            } catch (ParseException e) {
                                note.setUpdatedAt(new Date());
                            }
                        } else {
                            note.setUpdatedAt(new Date());
                        }
                        
                        notes.add(note);
                    } catch (Exception e) {
                        // 单个笔记解析失败，跳过并继续处理下一个
                        e.printStackTrace();
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return notes;
    }

    /**
     * 根据ID获取笔记
     */
    public Note getNoteById(long id) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        Note note = null;
        
        try {
            db = this.getReadableDatabase();
            if (db == null) {
                return null;
            }
            
            cursor = db.query(TABLE_NOTES,
                    null, // 所有列
                    COLUMN_ID + "=?",
                    new String[]{String.valueOf(id)},
                    null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                note = new Note();
                note.setId(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                
                // 安全获取字符串值
                int titleIndex = cursor.getColumnIndex(COLUMN_TITLE);
                if (titleIndex >= 0 && !cursor.isNull(titleIndex)) {
                    note.setTitle(cursor.getString(titleIndex));
                }
                
                int contentIndex = cursor.getColumnIndex(COLUMN_CONTENT);
                if (contentIndex >= 0 && !cursor.isNull(contentIndex)) {
                    note.setContent(cursor.getString(contentIndex));
                }
                
                int categoryIndex = cursor.getColumnIndex(COLUMN_CATEGORY);
                if (categoryIndex >= 0 && !cursor.isNull(categoryIndex)) {
                    note.setCategory(cursor.getString(categoryIndex));
                } else {
                    note.setCategory("默认分类");
                }

                int imagePathIndex = cursor.getColumnIndex(COLUMN_IMAGE_PATH);
                if (imagePathIndex >= 0 && !cursor.isNull(imagePathIndex)) {
                    note.setImagePath(cursor.getString(imagePathIndex));
                }
                
                // 安全处理日期
                int createdAtIndex = cursor.getColumnIndex(COLUMN_CREATED_AT);
                if (createdAtIndex >= 0 && !cursor.isNull(createdAtIndex)) {
                    try {
                        note.setCreatedAt(dateFormat.parse(cursor.getString(createdAtIndex)));
                    } catch (ParseException e) {
                        note.setCreatedAt(new Date());
                    }
                } else {
                    note.setCreatedAt(new Date());
                }
                
                int updatedAtIndex = cursor.getColumnIndex(COLUMN_UPDATED_AT);
                if (updatedAtIndex >= 0 && !cursor.isNull(updatedAtIndex)) {
                    try {
                        note.setUpdatedAt(dateFormat.parse(cursor.getString(updatedAtIndex)));
                    } catch (ParseException e) {
                        note.setUpdatedAt(new Date());
                    }
                } else {
                    note.setUpdatedAt(new Date());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return note;
    }

    /**
     * 更新笔记
     */
    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, note.getTitle());
        values.put(COLUMN_CONTENT, note.getContent());
        values.put(COLUMN_UPDATED_AT, dateFormat.format(new Date()));
        values.put(COLUMN_CATEGORY, note.getCategory());
        values.put(COLUMN_IMAGE_PATH, note.getImagePath());

        // 更新指定ID的笔记
        int rowsAffected = db.update(TABLE_NOTES, values, COLUMN_ID + "=?",
                new String[]{String.valueOf(note.getId())});
        db.close();
        return rowsAffected;
    }

    /**
     * 删除笔记
     */
    public void deleteNote(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    /**
     * 搜索笔记
     */
    public List<Note> searchNotes(String query) {
        List<Note> notes = new ArrayList<>();
        String searchQuery = "SELECT * FROM " + TABLE_NOTES + 
                " WHERE " + COLUMN_TITLE + " LIKE ? OR " + COLUMN_CONTENT + " LIKE ?" +
                " ORDER BY " + COLUMN_UPDATED_AT + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(searchQuery, new String[]{"%" + query + "%", "%" + query + "%"});

        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                note.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
                note.setContent(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT)));
                try {
                    note.setCreatedAt(dateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CREATED_AT))));
                    note.setUpdatedAt(dateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UPDATED_AT))));
                } catch (ParseException e) {
                    e.printStackTrace();
                    note.setCreatedAt(new Date());
                    note.setUpdatedAt(new Date());
                }
                note.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)));
                notes.add(note);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return notes;
    }

    /**
     * 根据分类获取笔记
     */
    public List<Note> getNotesByCategory(String category) {
        List<Note> notes = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NOTES + 
                " WHERE " + COLUMN_CATEGORY + " = ? " +
                " ORDER BY " + COLUMN_UPDATED_AT + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{category});

        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                note.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
                note.setContent(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT)));
                try {
                    note.setCreatedAt(dateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CREATED_AT))));
                    note.setUpdatedAt(dateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UPDATED_AT))));
                } catch (ParseException e) {
                    e.printStackTrace();
                    note.setCreatedAt(new Date());
                    note.setUpdatedAt(new Date());
                }
                note.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)));
                notes.add(note);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return notes;
    }

    /**
     * 获取所有分类
     */
    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        String selectQuery = "SELECT DISTINCT " + COLUMN_CATEGORY + " FROM " + TABLE_NOTES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                categories.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return categories;
    }
}