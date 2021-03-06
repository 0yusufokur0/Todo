package com.resurrection.todo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoDao {

    @Insert
    void Insert(Todo todo);

    @Update
    void Update(Todo todo);

    @Delete
    void Delete(Todo todo);

    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    LiveData<List<Todo>> getAllTodo();


}
