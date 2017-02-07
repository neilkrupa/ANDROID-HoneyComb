package com.nakrupadevelopment.nakrupa.honeycomb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by NAKRUPA on 3/23/2016.
 */
public class ViewUser extends Activity {
    private String uid;
    public String username;
    public ListView skillList;
    public ArrayAdapter theAdapter;
    public ArrayList<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewuser);

        Intent called = getIntent();
        Bundle extras = called.getExtras();
        if (extras != null) {
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
            uid = split[5];
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
    }
}
