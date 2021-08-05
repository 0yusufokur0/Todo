package com.resurrection.todo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TodoRepository {

    private TodoDao todoDao;
    private LiveData<List<Todo>> todo;

    public TodoRepository(Application application) {
        TodoDatabase database = TodoDatabase.getInstance(application);
        todoDao = database.todoDao();
        todo = todoDao.getAllTodo();
    }

    public void insert(Todo todo)
    {
        new InsertTodoAsyncTask(todoDao).execute(todo);
    }

    public void update(Todo todo)
    {
        new UpdateTodoAsyncTask(todoDao).execute(todo);
    }

    public void delete(Todo todo)
    {
        new DeleteTodoAsyncTask(todoDao).execute(todo);
    }

    public LiveData<List<Todo>> getAllTodo()
    {
        return todo;
    }

    private static class InsertTodoAsyncTask extends AsyncTask<Todo, Void, Void> {
        private TodoDao todoDao;

        private InsertTodoAsyncTask(TodoDao todoDao)
        {
            this.todoDao = todoDao;
        }
        @Override
        protected Void doInBackground(Todo... todos) {
            todoDao.Insert(todos[0]);
            return null;
        }
    }

    private static class UpdateTodoAsyncTask extends AsyncTask<Todo, Void, Void> {
        private TodoDao todoDao;

        private UpdateTodoAsyncTask(TodoDao todoDao)
        {
            this.todoDao = todoDao;
        }
        @Override
        protected Void doInBackground(Todo... todos) {
            todoDao.Update(todos[0]);
            return null;
        }
    }

    private static class DeleteTodoAsyncTask extends AsyncTask<Todo, Void, Void> {
        private TodoDao todoDao;

        private DeleteTodoAsyncTask(TodoDao todoDao)
        {
            this.todoDao = todoDao;
        }
        @Override
        protected Void doInBackground(Todo... todos) {
            todoDao.Delete(todos[0]);
            return null;
        }
    }



}
