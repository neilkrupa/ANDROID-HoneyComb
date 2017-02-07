package com.nakrupadevelopment.nakrupa.honeycomb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by NAKRUPA on 2/13/2016.
 */
public class PerformanceBreakdown extends Activity {
    private Button more;
    public String uid;
    public String pid;
    public ArrayAdapter theAdapter;
    public ListView userList;
    public ArrayList<String> addedUsers = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.performancebreakdown);

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
            uid = split[5];
        }

        ConnectionRetrieve getLeader = new ConnectionRetrieve();
        getLeader.execute("get_leader", uid);
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
            for(int i=0; i<split.length; i++){
                addedUsers.add(split[i]);
            }
        }


        if(addedUsers != null){
            theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, addedUsers);
            userList = (ListView) findViewById(R.id.userList);
            theAdapter.notifyDataSetChanged();
            userList.setAdapter(theAdapter);
        }

        ConnectionRetrieve getPbd = new ConnectionRetrieve();
        getPbd.execute("get_performance_breakdown", pid);
        response = null;
        try {
            response = getPbd.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(response != null){
            String[] split = response.split(",");

            TextView total = (TextView) findViewById(R.id.textView26);
            TextView active = (TextView) findViewById(R.id.textView27);
            TextView completed = (TextView) findViewById(R.id.textView28);
            TextView percentage = (TextView) findViewById(R.id.textView30);

            active.setText("Active Commitments: " + split[0]);
            completed.setText("Completed Commitments: " + split[1]);

            double activeInt = Double.parseDouble(split[0]);
            double completedInt = Double.parseDouble(split[1]);

            double product = activeInt + completedInt;
            int totalNo = (int) Math.round(product);

            String totalString = Integer.toString(totalNo);
            total.setText("Total Number of Commitments: " + totalString);

            double percentageInt = (completedInt/product)*100;
            int percentNo = (int) Math.round(percentageInt);
            String percentageString = Integer.toString(percentNo);
            percentage.setText(percentageString + "%");

        }
    }

}
