package com.example.daatabase;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText txt_id, txt_name, txt_marks;
    Button btn_insertData, btn_ViewData, btn_update, btn_Delete;
    Database_Helper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        txt_id = findViewById(R.id.vID);
        txt_name = findViewById(R.id.vName);
        txt_marks = findViewById(R.id.vMarks);
        btn_insertData = findViewById(R.id.btn_insert);
        btn_ViewData = findViewById(R.id.btn_view);
        btn_update = findViewById(R.id.btn_update); // Correct ID assignment for update button
        btn_Delete = findViewById(R.id.btn_delete);

        myDB = new Database_Helper(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_insertData.setOnClickListener(v -> insertData());
        btn_ViewData.setOnClickListener(v -> viewData());
        btn_update.setOnClickListener(v -> updateData());
        btn_Delete.setOnClickListener(v -> deleteData());
    }

    private void insertData() {
        String name = txt_name.getText().toString();
        int marks;
        try {
            marks = Integer.parseInt(txt_marks.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid marks input", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isInserted = myDB.insertData(name, marks);
        if (isInserted) {
            Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
        }
    }

    private void viewData() {
        Cursor cursor = myDB.getAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder buffer = new StringBuilder();
        while (cursor.moveToNext()) {
            buffer.append("ID: ").append(cursor.getString(0)).append("\n");
            buffer.append("Name: ").append(cursor.getString(1)).append("\n");
            buffer.append("Marks: ").append(cursor.getString(2)).append("\n\n");
        }

        Toast.makeText(this, buffer.toString(), Toast.LENGTH_LONG).show();
    }

    private void updateData() {
        String id = txt_id.getText().toString();
        String name = txt_name.getText().toString();
        int marks;
        try {
            marks = Integer.parseInt(txt_marks.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid marks input", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isUpdated = myDB.updateData(id, name, marks);
        if (isUpdated) {
            Toast.makeText(this, "Data Updated Sucess", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Data Update Failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteData() {
        String id = txt_id.getText().toString();
        Integer deletedRows = myDB.deleteData(id);

        if (deletedRows > 0) {
            Toast.makeText(this, "Data Deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Data Not Deleted", Toast.LENGTH_SHORT).show();
        }
    }
}
