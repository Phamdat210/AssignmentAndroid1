package com.example.hp.assignment;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hp.assignment.database.DatabaseHelper;
import com.example.hp.assignment.model.Lop;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dialogAdd(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View dialog = LayoutInflater.from(this).inflate(R.layout.dialog_add_class,null);

        builder.setView(dialog);

        final EditText edMaLop;
        final EditText edTenLop;
        Button btnDel;
        Button btnLuu;
        Button btnUpdate;

        edMaLop = (EditText) dialog.findViewById(R.id.edMaLop);
        edTenLop = (EditText) dialog.findViewById(R.id.edTenLop);
        btnDel = (Button) dialog.findViewById(R.id.btnDel);
        btnLuu = (Button) dialog.findViewById(R.id.btnLuu);
        btnUpdate = dialog.findViewById(R.id.btnUpdate);

        try {
            Intent intent = getIntent();
            if (intent != null) {
                Bundle bundle = intent.getBundleExtra("bundle");
                edMaLop.setText(bundle.getString("id"));
                edTenLop.setText(bundle.getString("name"));
            }
        } catch (Exception ex){
        }

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper = new DatabaseHelper(getApplicationContext());
                Lop lop = new Lop(edMaLop.getText().toString(),
                        edTenLop.getText().toString());
                if (databaseHelper.insertClass(lop)>0){
                    Toast.makeText(getApplicationContext(),"Thêm thành công",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this,ListCLass.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),"Thêm thất bại",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper = new DatabaseHelper(MainActivity.this);
                Lop lop = new Lop(edMaLop.getText().toString(),
                        edTenLop.getText().toString());
                if (databaseHelper.updateClass(lop)>0){
                    Toast.makeText(getApplicationContext(),"Cập nhật thành công",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),"Cập nhật thất bại",Toast.LENGTH_LONG).show();
                }
            }
        });

        builder.create().show();

    }

    public void listClass(View view) {
        Intent intent = new Intent(MainActivity.this,ListCLass.class);
        startActivity(intent);
    }
}
