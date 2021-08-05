package com.resurrection.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TodoViewModel todoViewModel;
    private TodoAdapter todoAdapter = new TodoAdapter();
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        init();

        getAndSetItems();

        itemGestures();



        todoAdapter.setOnItemClickListener(new TodoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Todo todo) {
                Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                intent.putExtra("id", todo.getId());
                intent.putExtra("title", todo.getTitle());
                intent.putExtra("description", todo.getDescription());
                startActivityForResult(intent, 2);
                System.out.println("set on clik teki id "+todo.getId());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.new_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.top_menu:
                Intent intent = new Intent(MainActivity.this, AddTodo.class);
                startActivityForResult(intent, 1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK)
        {
            String title = data.getStringExtra("todoTitle");
            String description = data.getStringExtra("todoDescription");

            Todo todo = new Todo(idCreater(),title, description);
            todoViewModel.insert(todo);


            Toast.makeText(getApplicationContext(), "Todo Saved", Toast.LENGTH_LONG).show();

        }

        else if(requestCode == 2 && resultCode == RESULT_OK)
        {
            String title = data.getStringExtra("titlelast");
            String description = data.getStringExtra("descriptionlast");
            long id = data.getLongExtra("todoid", -1);

            Todo todo = new Todo(id,title, description);

            todoViewModel.update(todo);
        }

    }




    private void init() {
        recyclerView = findViewById(R.id.recyclerview);

    }


    private void getAndSetItems(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(todoAdapter);

        todoViewModel = ViewModelProviders.of(this).get(TodoViewModel.class);
        todoViewModel.getAllTodo().observe(this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> todos) {
                //update Recyclerview
                todoAdapter.setTodo(todos);

            }
        });
    }


    private void itemGestures(){
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT| ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                todoViewModel.delete(todoAdapter.getTodo(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);
    }



    long idCreater() {
        Date nowDateTime = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyykkmmss");
        String date = dateFormat.format(nowDateTime);
        System.out.println(date);
        return Long.valueOf(date);
    }



}
