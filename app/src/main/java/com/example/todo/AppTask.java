package com.example.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AppTask extends AppCompatActivity {
    private EditText editText2,editText;
    private TextView textView;
    private RadioGroup radioGroup;
    private Button btnAddTask;

    private String taskTitle;
    private String taskDescription;
    private int taskPriority;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_app_task);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editText=findViewById(R.id.editText);
        editText2=findViewById(R.id.editText2);
        textView=findViewById(R.id.textView);
        radioGroup=findViewById(R.id.radioGroup);
        btnAddTask=findViewById(R.id.btnAddTask);

        Intent intent = getIntent();
        if (intent.hasExtra("taskTitle")) {
            taskTitle = intent.getStringExtra("taskTitle");
            taskDescription = intent.getStringExtra("taskDescription");
            taskPriority = intent.getIntExtra("taskPriority", 3);

            editText.setText(taskTitle);
            editText2.setText(taskDescription);
            if (taskPriority == 1) {
                radioGroup.check(R.id.high);
            } else if (taskPriority == 2) {
                radioGroup.check(R.id.medium);
            } else {
                radioGroup.check(R.id.low);
            }
            btnAddTask.setText("Update Task");
        }



        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editText.getText().toString();
                String description = editText2.getText().toString();
                int priority = getSelectedPriority();

                Intent resultIntent = new Intent();
                resultIntent.putExtra("taskTitle", title);
                //resultIntent.putExtra("taskDescription", description);
                resultIntent.putExtra("taskPriority", priority);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    private int getSelectedPriority() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.high) {
            return 1;
        } else if (selectedId == R.id.medium) {
            return 2;
        } else {
            return 3;
        }
    }


}









