package com.example.allinone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AddCourseActivity extends AppCompatActivity {
    EditText name;
    EditText url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_course);

    }

    public void submit(View view) {
        name=findViewById(R.id.name);
        url=findViewById(R.id.url);
        if(TextUtils.isEmpty(name.getText())){
            name.setError("Course name required");
        }
        else{
            if(TextUtils.isEmpty(url.getText())){
                url.setError("Course content URL is required");
            }
            else{
                addcourse.name=name.getText().toString();
                addcourse.url=url.getText().toString();
                //addcourse ad=new addcourse(name.getText().toString(),url.getText().toString());
                //Toast.makeText(this,"yes "+name.getText().toString(),Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}