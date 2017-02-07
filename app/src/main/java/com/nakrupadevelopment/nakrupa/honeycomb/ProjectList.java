package com.nakrupadevelopment.nakrupa.honeycomb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by NAKRUPA on 3/23/2016.
 */
public class ProjectList extends Activity {
    public ArrayList<String> listings = new ArrayList<String>();
    public ArrayList<String> pid = new ArrayList<String>();
    public String uid;
    public String username;
    public ListView projectList;
    public ArrayAdapter theAdapter;
    private ImageButton more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.projectslist);
        Intent called = getIntent();
        Bundle extras = called.getExtras();
        if (extras != null) {
            uid = called.getExtras().getString("uid");
            username = called.getExtras().getString("username");
        }


        ConnectionRetrieve getProjectList = new ConnectionRetrieve();
        getProjectList.execute("get_project_list", uid);
        listings.clear();
        String response = null;
        try {
            response = getProjectList.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(response != null){
            String[] split = response.split(",");
            for(int i = 0; i<split.length-1; i=i+2){
                listings.add(split[i]);
                pid.add(split[i+1]);
            }
        }

        theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listings);
        projectList = (ListView) findViewById(R.id.projectList);
        theAdapter.notifyDataSetChanged();
        projectList.setAdapter(theAdapter);

        projectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ProjectList.this, ViewProject.class);
                intent.putExtra("pid", pid.get(position));
                intent.putExtra("uid", uid);
                startActivity(intent);
            }
        });

        more = (ImageButton) findViewById(R.id.moreButton);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(ProjectList.this, more);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.moremenu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("Settings")) {
                            Intent intent = new Intent(ProjectList.this, Settings.class);
                            intent.putExtra("username", username);
                            intent.putExtra("uid", uid);
                            startActivity(intent);
                        }
                        return true;
                    }
                });

                popup.show();
            }
        });

        //GET NOTIFICATIONS AND CHANGE ICON
        ConnectionRetrieve getNewNotifications = new ConnectionRetrieve();
        getNewNotifications.execute("get_new_notif", uid);
        String notifRes = null;
        try {
            notifRes = getNewNotifications.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(notifRes.equals("1")){
            ImageButton notifIcon = (ImageButton) findViewById(R.id.notifications);
            notifIcon.setBackgroundResource(R.drawable.notificationsnew);
        }
    }


    public void showLive(View view) {
        Intent intent = new Intent(ProjectList.this, Main.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("username", username);
        intent.putExtra("uid", uid);
        startActivity(intent);
    }

    public void showProjects(View view) {
        Intent intent = new Intent(ProjectList.this, ProjectList.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("username", username);
        intent.putExtra("uid", uid);
        startActivity(intent);
    }

    public void showProfile(View view) {
        Intent intent = new Intent(ProjectList.this, ViewProfile.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("username", username);
        intent.putExtra("uid", uid);
        startActivity(intent);
    }

    public void showNotifications(View view) {
        Intent intent = new Intent(ProjectList.this, ViewNotifications.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("username", username);
        intent.putExtra("uid", uid);
        startActivity(intent);
    }

    public void newProject(View view) {
        Intent intent = new Intent(ProjectList.this, CreateProject.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("uid", uid);
        startActivity(intent);
    }
}
