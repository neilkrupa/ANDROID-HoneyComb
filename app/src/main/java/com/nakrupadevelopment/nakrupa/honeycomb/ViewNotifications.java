package com.nakrupadevelopment.nakrupa.honeycomb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.Connection;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by NAKRUPA on 3/23/2016.
 */
public class ViewNotifications extends Activity {
    private ImageButton more;
    public String uid;
    public String username;
    public ArrayAdapter theAdapter;
    public ListView notificationList;
    public ArrayList<String> notifications = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewnotifications);
        Intent called = getIntent();
        Bundle extras = called.getExtras();
        if (extras != null) {
            uid = called.getExtras().getString("uid");
            username = called.getExtras().getString("username");
        }


        ConnectionRetrieve getNotif = new ConnectionRetrieve();
        getNotif.execute("get_notifications", uid);
        String response = "";
        response = null;
        try {
            response = getNotif.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(response != null){
            String[] split = response.split(",");
            for(int i=split.length-1; i>=0; i=i-1){
                notifications.add(split[i]);
            }
        }


        if(notifications != null){
            theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, notifications);
            notificationList = (ListView) findViewById(R.id.notLists);
            theAdapter.notifyDataSetChanged();
            notificationList.setAdapter(theAdapter);
        }


        more = (ImageButton) findViewById(R.id.moreButton);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(ViewNotifications.this, more);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.moremenu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("Settings")) {
                            Intent intent = new Intent(ViewNotifications.this, Settings.class);
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

        //ON CLICK RESET NOTIFICATIONS
        ConnectionRemove resetNotif = new ConnectionRemove();
        resetNotif.execute("remove_new_notif", uid);
    }

    public void showLive(View view) {
        Intent intent = new Intent(ViewNotifications.this, Main.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("username", username);
        intent.putExtra("uid", uid);
        startActivity(intent);
    }

    public void showProjects(View view) {
        Intent intent = new Intent(ViewNotifications.this, ProjectList.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("username", username);
        intent.putExtra("uid", uid);
        startActivity(intent);
    }

    public void showProfile(View view) {
        Intent intent = new Intent(ViewNotifications.this, ViewProfile.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("username", username);
        intent.putExtra("uid", uid);
        startActivity(intent);
    }

    public void showNotifications(View view) {
        Intent intent = new Intent(ViewNotifications.this, ViewNotifications.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("username", username);
        intent.putExtra("uid", uid);
        startActivity(intent);
    }
}
