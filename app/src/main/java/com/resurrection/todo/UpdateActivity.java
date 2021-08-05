package com.resurrection.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {

    EditText title;
    EditText description;
    Button cancel;
    Button save;
    long todoid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        getSupportActionBar().setTitle("Update Todo");

        title = findViewById(R.id.editTextTitleUpdate);
        description = findViewById(R.id.editTextDescriptionUpdate);
        cancel = findViewById(R.id.buttonCancelUpdate);
        save = findViewById(R.id.buttonSaveUpdate);

        getData();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Nothing Updated", Toast.LENGTH_LONG).show();
                finish();

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTodo();
            }
        });
    }

    public void updateTodo()
    {
        String titlelast = title.getText().toString();
        String descriptionlast = description.getText().toString();

        Intent intent = new Intent();
        intent.putExtra("titlelast", titlelast);
        intent.putExtra("descriptionlast", descriptionlast);
        if(todoid != -1)
        {
            intent.putExtra("todoid", todoid);
            setResult(RESULT_OK, intent);
            finish();
        }

    }

    public void getData()
    {
        Intent i = getIntent();
        todoid = i.getLongExtra("id", -1);
        String todotitle = i.getStringExtra("title");
        String tododescription = i.getStringExtra("description");

        title.setText(todotitle);
        description.setText(tododescription);
    }
}
