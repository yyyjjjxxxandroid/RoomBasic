package com.example.roombasic;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao//Database access object 访问数据库的一个接口增删改查都要在里面声明
public interface WordDao {
    @Insert
    void insertWords(Word... words);
    @Update
    void updateWord(Word... words);
    @Delete
    void deleteWords(Word... words);
    @Query("DELETE FROM WORD")
    void deleteAllWord();
    @Query("SELECT * FROM WORD ORDER BY ID DESC")
    LiveData<List<Word>> getAllWordsLive();
    @Query("SELECT * FROM WORD WHERE english_word Like :patten ORDER BY ID DESC")
    LiveData<List<Word>>findWordsWithPatten(String patten);
}
