package com.example.roombasic;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

//singleton
@Database(entities = {Word.class},version = 2, exportSchema = false)
//exportSchema布尔值，这个参数决定了是否导出数据库架构。如果设置为true，Room会在构建项目时生成描述数据库架构的JSON文件。
// 这个文件对于理解和维护数据库结构、以及在数据库升级时自动迁移非常有用
public abstract class WordDatabase extends RoomDatabase {
   private static WordDatabase INSTANCE;
   static synchronized WordDatabase getDatabase(Context context){
       if (INSTANCE==null){
           //数据库实例通常需要在整个应用生命周期中保持有效，使用应用上下文可以确保数据库的生命周期不依赖于任何特定组件的生命周期
           // 避免了因为某个Activity或Fragment销毁而导致数据库连接丢失的问题。
           INSTANCE= Room.databaseBuilder(context.getApplicationContext(), WordDatabase.class,"word_database")
                   .addMigrations(MIGRATION_1_2)
                   .build();
       }
     return INSTANCE;
   };
    public abstract WordDao getWordDao();
   static final Migration MIGRATION_1_2=new Migration(1,2) {
       @Override
       public void migrate(@NonNull SupportSQLiteDatabase database) {
        database.execSQL("ALTER TABLE word ADD COLUMN chinese_invisible INTEGER NOT NULL DEFAULT 0");

       }
   };
}
