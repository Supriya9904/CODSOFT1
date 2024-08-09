package com.example.todo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_TASK_REQUEST = 1;
    private static final int EDIT_TASK_REQUEST = 1;
    FloatingActionButton fab;
    private RecyclerView recyclerView;
    private Adapter adapter;
    private List<modelClass> modelClassList;
    TextView title1;
    private int editPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        title1 = findViewById(R.id.toolbar_title);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        modelClassList = new ArrayList<>();
        adapter = new Adapter(modelClassList, new Adapter.OnItemClickListener() {
            @Override
            public void onEditClick(int position) {
                onEditTask(position);
            }

            @Override
            public void onDeleteClick(int position) {
                onDeleteTask(position);
            }
        });
        recyclerView.setAdapter(adapter);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AppTask.class);
                startActivityForResult(intent, ADD_TASK_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_TASK_REQUEST && resultCode == RESULT_OK && data != null) {
            String title = data.getStringExtra("taskTitle");
            String description = data.getStringExtra("taskDescription");
            int priority = data.getIntExtra("taskPriority", 0);

            //modelClassList.add(new modelClass(title, description, priority));
            if (requestCode == ADD_TASK_REQUEST) {
                modelClassList.add(new modelClass(title, description, priority));
            } else if (requestCode == EDIT_TASK_REQUEST) {
                modelClassList.set(editPosition, new modelClass(title, description, priority));
            }

            sortTasksByPriority();
            adapter.notifyDataSetChanged();
        }
//            modelClass model = new modelClass(title, priority);
//            if (position != -1) {
//                modelClassList.set(position, model);
//                adapter.notifyItemChanged(position);
//                position = -1;
//            } else {
//                modelClassList.add(model);
//                adapter.notifyItemInserted(modelClassList.size() - 1);
//            }
//        }
    }

    private void sortTasksByPriority() {
        Collections.sort(modelClassList, new Comparator<modelClass>() {
            @Override
            public int compare(modelClass t1, modelClass t2) {
                return Integer.compare(t1.getPriority(), t2.getPriority());
            }
        });
    }


        public void onEditTask( int position){
            position = position;
            modelClass model = modelClassList.get(position);
            Intent intent = new Intent(MainActivity.this, AppTask.class);
            intent.putExtra("taskTitle", model.getTask());
            intent.putExtra("taskPriority", model.getPriority());
            startActivityForResult(intent, EDIT_TASK_REQUEST);
        }

        public void onDeleteTask(int position){
            modelClassList.remove(position);
            adapter.notifyItemRemoved(position);
        }

}
