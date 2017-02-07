package com.nakrupadevelopment.nakrupa.honeycomb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by NAKRUPA on 2/11/2016.
 */
public class ViewProject extends Activity {
    private Button more;
    public String lid;
    public String username;
    public String uid;
    public String pid;
    public ArrayAdapter theAdapter;
    public ListView userList;
    public ArrayList<String> addedUsers = new ArrayList<String>();

    //ADD ANOTHER EXTRA FOR PROJECT LEADER --------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewproject);

        TextView name = (TextView) findViewById(R.id.openProject);
        TextView leader = (TextView) findViewById(R.id.openProjectLeader);
        EditText desc = (EditText) findViewById(R.id.openProjectDescription);
        TextView deadline = (TextView) findViewById(R.id.openProjectDeadlineDate);

        Intent called = getIntent();
        Bundle extras = called.getExtras();
        if (extras != null) {
            pid = called.getExtras().getString("pid");
            uid = called.getExtras().getString("uid");
        }

        ConnectionRetrieve getUsersTask = new ConnectionRetrieve();
        getUsersTask.execute("get_project", pid);
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
            name.setText("Project Name: " + split[0]);
            desc.setText(split[1]);
            deadline.setText(split[2]);
            lid = split[5];
        }


        ConnectionRetrieve getLeader = new ConnectionRetrieve();
        getLeader.execute("get_leader", lid);
        response = null;
        try {
            response = getLeader.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(response != null){
            leader.setText("Project Leader: " + response);
            username = response;
        }

        ConnectionRetrieve getPUsers = new ConnectionRetrieve();
        getPUsers.execute("get_project_users", pid);
        response = null;
        try {
            response = getPUsers.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(response != null){
            String[] split = response.split(",");
            for(int i=0; i<split.length-1; i=i+2){
                addedUsers.add(split[i]);
            }
        }


        if(addedUsers != null){
            theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, addedUsers);
            userList = (ListView) findViewById(R.id.userList);
            theAdapter.notifyDataSetChanged();
            userList.setAdapter(theAdapter);
        }

        more = (Button) findViewById(R.id.moreButton);
        if(uid.equals(lid)){
            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Creating the instance of PopupMenu
                    PopupMenu popup = new PopupMenu(ViewProject.this, more);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater()
                            .inflate(R.menu.leader_dropdown, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            if(item.getTitle().equals("My Commitments")){

                                Intent intent = new Intent(ViewProject.this, CommitmentList.class);
                                intent.putExtra("uid", uid);
                                intent.putExtra("lid", lid);
                                intent.putExtra("pid", pid);
                                startActivity(intent);

                            }else if(item.getTitle().equals("To Do List")){

                                Intent intent = new Intent(ViewProject.this, ToDoList.class);
                                intent.putExtra("uid", uid);
                                intent.putExtra("pid", pid);
                                startActivity(intent);

                            }else if(item.getTitle().equals("Assist Forum")){

                                Intent intent = new Intent(ViewProject.this, AssistForum.class);
                                intent.putExtra("uid", uid);
                                intent.putExtra("pid", pid);
                                startActivity(intent);

                            }else if(item.getTitle().equals("Performance Breakdown")){

                                Intent intent = new Intent(ViewProject.this, PerformanceBreakdown.class);
                                intent.putExtra("uid", uid);
                                intent.putExtra("pid", pid);
                                startActivity(intent);
                            }else if(item.getTitle().equals("Edit/Remove Project")){

                                Intent intent = new Intent(ViewProject.this, EditRemoveProject.class);
                                intent.putExtra("uid", uid);
                                intent.putExtra("username", username);
                                intent.putExtra("pid", pid);
                                startActivity(intent);
                            }
                            return true;
                        }
                    });

                    popup.show();
                }
            });
        }else{
            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Creating the instance of PopupMenu
                    PopupMenu popup = new PopupMenu(ViewProject.this, more);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater()
                            .inflate(R.menu.dropdown_menu, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            if(item.getTitle().equals("My Commitments")){

                                Intent intent = new Intent(ViewProject.this, CommitmentList.class);
                                intent.putExtra("uid", uid);
                                intent.putExtra("lid", lid);
                                intent.putExtra("pid", pid);
                                startActivity(intent);

                            }else if(item.getTitle().equals("To Do List")){

                                Intent intent = new Intent(ViewProject.this, ToDoList.class);
                                intent.putExtra("uid", uid);
                                intent.putExtra("pid", pid);
                                startActivity(intent);

                            }else if(item.getTitle().equals("Assist Forum")){

                                Intent intent = new Intent(ViewProject.this, AssistForum.class);
                                intent.putExtra("uid", uid);
                                intent.putExtra("pid", pid);
                                startActivity(intent);

                            }else if(item.getTitle().equals("Performance Breakdown")){

                                Intent intent = new Intent(ViewProject.this, PerformanceBreakdown.class);
                                intent.putExtra("uid", uid);
                                intent.putExtra("pid", pid);
                                startActivity(intent);
                            }
                            return true;
                        }
                    });

                    popup.show();
                }
            });
        }

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ViewProject.this, ViewUser.class);
                intent.putExtra("username", addedUsers.get(position));
                startActivity(intent);
            }
        });
    }
}
