package com.nakrupadevelopment.nakrupa.honeycomb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by NAKRUPA on 2/13/2016.
 */
public class ToDoList extends Activity {

    public String pid;
    public String uid;
    public ArrayAdapter theAdapter;
    public ListView todoList;
    public ArrayList<String> todo = new ArrayList<String>();
    public ArrayList<String> cidIndex = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolist);

        Intent called = getIntent();
        Bundle extras = called.getExtras();
        if (extras != null) {
            pid = called.getExtras().getString("pid");
            uid = called.getExtras().getString("uid");
        }

        theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todo);
        todoList = (ListView) findViewById(R.id.todolistView);

        ConnectionRetrieve getUsersTask = new ConnectionRetrieve();
        getUsersTask.execute("get_todo_list", pid);
        String response = "";
        try {
            response = getUsersTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(response != null){
            String[] split = response.split(",");
            for(int i = 0; i<split.length-1; i=i+2){
                todo.add(split[i]);
                cidIndex.add(split[i+1]);
            }
        }

        System.out.println(response);

        theAdapter.notifyDataSetChanged();
        todoList.setAdapter(theAdapter);

        todoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ToDoList.this, AcceptCommitment.class);
                intent.putExtra("cid", cidIndex.get(position));
                intent.putExtra("pid", pid);
                intent.putExtra("uid", uid);
                startActivity(intent);
            }
        });
    }
}
