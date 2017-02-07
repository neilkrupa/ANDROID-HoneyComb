package com.nakrupadevelopment.nakrupa.honeycomb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static java.lang.Thread.sleep;

/**
 * Created by NAKRUPA on 2/11/2016.
 */
public class AddTeamMember extends Activity {
    public ListView userList;
    public ArrayAdapter theAdapter;
    public ArrayList<String> users = new ArrayList<String>();
    public ArrayList<String> usersIndex = new ArrayList<String>();
    public ArrayList<String> addedUsers = new ArrayList<String>();
    public ArrayList<String> addedUsersIndex = new ArrayList<String>();

    public String pid;
    public String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addteammember);

        Intent called = getIntent();
        Bundle extras = called.getExtras();
        if (extras != null) {
            if(called.getStringExtra("pid") != null){
                pid = called.getStringExtra("pid");
                System.out.println(pid);
            }
            if(called.getStringExtra("uid") != null){
                uid = called.getStringExtra("uid");
            }
        }

        theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, users);
        userList = (ListView) findViewById(R.id.listView2);

        ConnectionRetrieve getUsersTask = new ConnectionRetrieve();
        getUsersTask.execute("get_users");
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
                users.add(split[i+1]);
                usersIndex.add(split[i]);
            }
        }

        for (int j = 0; j<usersIndex.size(); j++) {
            if(usersIndex.get(j).equals(uid) == true){
                usersIndex.remove(j);
                users.remove(j);
            }
        }

        theAdapter.notifyDataSetChanged();
        userList.setAdapter(theAdapter);

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                addedUsers.add(users.get(position));
                addedUsersIndex.add(usersIndex.get(position));
            }
        });

        userList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int pos, long id) {
                Intent intent = new Intent(AddTeamMember.this, ViewUser.class);
                System.out.println(users.get(pos));
                intent.putExtra("username", users.get(pos));
                startActivity(intent);
                return true;
            }
        });
    }

    public void addMembers(View view) {

        for(int i=0; i<addedUsers.size(); i++){
            ConnectionPost addMembers = new ConnectionPost();
            addMembers.execute("post_puser", addedUsersIndex.get(i), pid);
        }

        Intent addTeamMember = new Intent(AddTeamMember.this, CreateProject.class);
        addTeamMember.putExtra("pid", pid);
        addTeamMember.putExtra("uid", uid);
        startActivity(addTeamMember);
    }
}
