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
public class AssistForum extends Activity {
    private String pid;
    public ArrayAdapter theAdapter;
    public ListView assistList;
    public ArrayList<String> assist = new ArrayList<String>();
    public ArrayList<String> cidIndex = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assistforum);


        Intent called = getIntent();
        Bundle extras = called.getExtras();
        if (extras != null) {
            pid = called.getExtras().getString("pid");
        }

        ConnectionRetrieve getUsersTask = new ConnectionRetrieve();
        getUsersTask.execute("get_af", pid);
        String response = null;
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
                assist.add(split[i]);
                cidIndex.add(split[i+1]);
            }
        }

        if(assist != null){
            assistList = (ListView) findViewById(R.id.listView3);
            theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, assist);
            theAdapter.notifyDataSetChanged();
            assistList.setAdapter(theAdapter);
        }

        assistList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(AssistForum.this, Issue.class);
                intent.putExtra("cid", cidIndex.get(position));
                intent.putExtra("pid", pid);
                startActivity(intent);

            }
        });
    }
}
