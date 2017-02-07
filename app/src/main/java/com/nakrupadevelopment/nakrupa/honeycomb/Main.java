package com.nakrupadevelopment.nakrupa.honeycomb;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import java.lang.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Main extends Activity {

    public String username;
    public String uid;
    public ListView liveList;
    public ArrayAdapter liveAdapter;
    public ArrayList<String> live = new ArrayList<String>();
    private ImageButton more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Intent called = getIntent();
        Bundle extras = called.getExtras();
        if (extras != null) {
            username = called.getExtras().getString("username");
            uid = called.getExtras().getString("uid");
        }

        liveAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, live);
        liveList = (ListView) findViewById(R.id.livefeed);

        ConnectionRetrieve getUsersTask = new ConnectionRetrieve();
        getUsersTask.execute("get_live_feed", uid);
        String response = null;
        try {
            response = getUsersTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("RESPONSE: " + response);

        if(response != null){
            String[] split = response.split(",");
            for(int i=split.length-1; i>=0; i=i-1){
                live.add(split[i]);
            }
        }

        liveAdapter.notifyDataSetChanged();
        liveList.setAdapter(liveAdapter);


        more = (ImageButton) findViewById(R.id.moreButton);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(Main.this, more);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.moremenu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("Settings")) {
                            Intent intent = new Intent(Main.this, Settings.class);
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
        Intent intent = new Intent(Main.this, Main.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("username", username);
        intent.putExtra("uid", uid);
        startActivity(intent);
    }

    public void showProjects(View view) {
        Intent intent = new Intent(Main.this, ProjectList.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("username", username);
        intent.putExtra("uid", uid);
        startActivity(intent);
    }

    public void showProfile(View view) {
        Intent intent = new Intent(Main.this, ViewProfile.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("username", username);
        intent.putExtra("uid", uid);
        startActivity(intent);
    }

    public void showNotifications(View view) {
        Intent intent = new Intent(Main.this, ViewNotifications.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("username", username);
        intent.putExtra("uid", uid);
        startActivity(intent);
    }
}
