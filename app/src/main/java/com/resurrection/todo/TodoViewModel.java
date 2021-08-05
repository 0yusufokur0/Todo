package com.resurrection.todo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {

    private TodoRepository repository;
    private LiveData<List<Todo>> todo;

    public TodoViewModel(@NonNull Application application) {
        super(application);

        repository = new TodoRepository(application);
        todo = repository.getAllTodo();

    }

    public void insert(Todo todo)
    {
        repository.insert(todo);
    }

    public void update(Todo todo)
    {
        repository.update(todo);
    }

    public void delete(Todo todo)
    {
        repository.delete(todo);
    }

    public LiveData<List<Todo>> getAllTodo()
    {
        return todo;
    }


}
