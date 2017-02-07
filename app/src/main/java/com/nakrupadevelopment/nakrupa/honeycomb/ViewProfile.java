package com.nakrupadevelopment.nakrupa.honeycomb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by NAKRUPA on 3/23/2016.
 */
public class ViewProfile extends Activity {
    private ImageButton more;
    public String uid;
    public String username;
    public ListView skillList;
    public ArrayAdapter theAdapter;
    public ArrayList<String> list = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewprofile);
        Intent called = getIntent();
        Bundle extras = called.getExtras();
        if (extras != null) {
            uid = called.getExtras().getString("uid");
            username = called.getExtras().getString("username");
        }

        ConnectionRetrieve getUsersTask = new ConnectionRetrieve();
        getUsersTask.execute("get_profile", username);
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
            System.out.println(response);
            TextView uname = (TextView) findViewById(R.id.profileUsername);
            TextView email = (TextView) findViewById(R.id.profileEmail);
            TextView points = (TextView) findViewById(R.id.profilePointScore);
            TextView commitments = (TextView) findViewById(R.id.profileCommitments);
            TextView projects = (TextView) findViewById(R.id.profileProjects);
            uname.setText(split[0]);
            email.setText(split[1]);
            points.setText(split[2]);
            commitments.setText(split[3]);
            projects.setText(split[4]);
        }


        theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        skillList = (ListView) findViewById(R.id.skillList);

        getUsersTask = new ConnectionRetrieve();
        getUsersTask.execute("get_skill_list", uid);
        response = null;
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
                list.add(split[i] + "        " + split[i+1] + "/5");
            }
        }

        theAdapter.notifyDataSetChanged();
        skillList.setAdapter(theAdapter);

        more = (ImageButton) findViewById(R.id.moreButton);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(ViewProfile.this, more);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.moremenu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("Settings")) {
                            Intent intent = new Intent(ViewProfile.this, Settings.class);

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
        Intent intent = new Intent(ViewProfile.this, Main.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("username", username);
        intent.putExtra("uid", uid);
        startActivity(intent);
    }

    public void showProjects(View view) {
        Intent intent = new Intent(ViewProfile.this, ProjectList.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("username", username);
        intent.putExtra("uid", uid);
        startActivity(intent);
    }

    public void showProfile(View view) {
        Intent intent = new Intent(ViewProfile.this, ViewProfile.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("username", username);
        intent.putExtra("uid", uid);
        startActivity(intent);
    }

    public void showNotifications(View view) {
        Intent intent = new Intent(ViewProfile.this, ViewNotifications.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("username", username);
        intent.putExtra("uid", uid);
        startActivity(intent);
    }
}
