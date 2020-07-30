package com.example.allinone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Set;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> course_name;
    ArrayAdapter<String> ad;
    ArrayList<String> course_url;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try{
            course_name=retrieve_name();
            course_url=retrieve_url();
        } catch (Exception e) {
            course_name=new ArrayList<String>();
            course_url=new ArrayList<String>();
        }
        ad=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,course_name);
        lv=(ListView)findViewById(R.id.listview);
        lv.setAdapter(ad);
        registerForContextMenu(lv);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Uri t=Uri.parse(course_url.get(i));
                Intent intent=new Intent(Intent.ACTION_VIEW,t);
                try{
                    startActivity(intent);
                } catch (Exception e) {
                    toast();
                }
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.delete_item,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        course_name.remove(info.position);
        course_url.remove(info.position);
        ad.notifyDataSetChanged();
        save();
        saveurl();
        return super.onContextItemSelected(item);
    }

    private void toast() {
        Toast.makeText(this,"The link saved under this name is wrong/not working", Toast.LENGTH_SHORT).show();
    }

    private ArrayList<String> retrieve_url() {
        SharedPreferences p=getSharedPreferences("hell",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = p.getString("curl", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        ArrayList<String> g =gson.fromJson(json, type);
        if(g.isEmpty()){
            g=new ArrayList<String>();
        }
        return g;
    }

    private ArrayList<String> retrieve_name() {
        SharedPreferences p=getSharedPreferences("hell",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = p.getString("cn", "");
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        ArrayList<String> g =gson.fromJson(json, type);
        if(g.isEmpty()){
            g=new ArrayList<String>();
        }
        return g;
    }

    public void Add_course(View view) {
        Intent i=new Intent(this,AddCourseActivity.class);
        startActivity(i);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        if(addcourse.name==null||addcourse.name==""||addcourse.url==null||addcourse.url==""){}
        else{
            course_name.add(addcourse.name);
            course_url.add(addcourse.url);
            addcourse.name=null;
            addcourse.url=null;
            ad.notifyDataSetChanged();
            save();
            saveurl();
        }
    }

    private void save() {
        SharedPreferences p = getSharedPreferences("hell", MODE_PRIVATE);
        SharedPreferences.Editor editor = p.edit();
        Gson gson = new Gson();
        String json = gson.toJson(course_name);
        editor.putString("cn", json);
        editor.commit();

    }
    private void saveurl(){
        SharedPreferences p = getSharedPreferences("hell", MODE_PRIVATE);
        SharedPreferences.Editor editor = p.edit();
        Gson gson = new Gson();
        String json = gson.toJson(course_url);
        editor.putString("curl", json);
        editor.commit();
    }
}