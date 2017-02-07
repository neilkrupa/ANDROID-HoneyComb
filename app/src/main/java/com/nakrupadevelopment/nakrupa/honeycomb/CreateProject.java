package com.nakrupadevelopment.nakrupa.honeycomb;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by NAKRUPA on 2/11/2016.
 */
public class CreateProject extends Activity{


    public ArrayAdapter theAdapter;
    public ListView teamMembersList;
    public ArrayList<String> teamMembers = new ArrayList<String>();
    public ListView commitmentsList;
    public ArrayList<String> commitments = new ArrayList<String>();
    public ArrayList<String> commitmentsIndex = new ArrayList<String>();


    public String pid;
    public String title;
    public String description;
    public String deadline;
    public String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createproject);

        Intent called = getIntent();
        Bundle extras = called.getExtras();
        if (extras != null) {
            if(called.getStringExtra("pid") != null){
                pid = called.getStringExtra("pid");
            }

            if(called.getStringExtra("uid") != null){
                uid = called.getStringExtra("uid");
            }
        }

        if(pid != null){

            ConnectionRetrieve getPUsers = new ConnectionRetrieve();
            getPUsers.execute("get_project_commitments", pid);
            String response = null;
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
                    commitments.add(split[i]);
                    commitmentsIndex.add(split[i+1]);
                }
            }

            ConnectionRetrieve getCommitments = new ConnectionRetrieve();
            getCommitments.execute("get_project_users", pid);
            String commitmentsresponse = null;
            try {
                commitmentsresponse = getCommitments.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            if(commitmentsresponse != null){
                String[] split = commitmentsresponse.split(",");
                for(int i=0; i<split.length-1; i=i+2){
                    teamMembers.add(split[i]);
                }
            }

            ConnectionRetrieve getUsersTask = new ConnectionRetrieve();
            getUsersTask.execute("get_project", pid);
            response = null;
            try {
                response = getUsersTask.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            if(response != null){
                EditText titleView = (EditText) findViewById(R.id.newProjectName);
                EditText descriptionView = (EditText) findViewById(R.id.newProjectDescription);
                EditText deadlineView = (EditText) findViewById(R.id.editText10);
                String[] split = response.split(",");
                titleView.setText(split[0]);
                descriptionView.setText(split[1]);
                deadlineView.setText(split[2]);
                uid = split[5];
            }
        }


        if(teamMembers != null){
            theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, teamMembers);
            teamMembersList = (ListView) findViewById(R.id.newProjectTeamMemberList);
            theAdapter.notifyDataSetChanged();
            teamMembersList.setAdapter(theAdapter);
        }

        if(commitments != null){
            theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, commitments);
            commitmentsList = (ListView) findViewById(R.id.newProjectCommitmentList);
            theAdapter.notifyDataSetChanged();
            commitmentsList.setAdapter(theAdapter);
        }

    }

    public void newProjectAddTeamMember(View view) {

        EditText titleView = (EditText) findViewById(R.id.newProjectName);
        EditText descriptionView = (EditText) findViewById(R.id.newProjectDescription);
        EditText deadlineView = (EditText) findViewById(R.id.editText10);
        title = String.valueOf(titleView.getText());
        description = String.valueOf(descriptionView.getText());
        deadline = String.valueOf(deadlineView.getText());


        if(title.equals("") || description.equals("") || deadline.equals("")){
            Toast.makeText(CreateProject.this, "Please enter project information first.", Toast.LENGTH_SHORT).show();
        }else{
            ConnectionPost addProject = new ConnectionPost();
            addProject.execute("post_project", uid, title, description, deadline);

            try {
                pid = addProject.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            ConnectionPost addLeader = new ConnectionPost();
            addLeader.execute("post_puser", uid, pid);

            Intent intent = new Intent(CreateProject.this, AddTeamMember.class);
            intent.putExtra("pid", pid);
            intent.putExtra("uid", uid);
            startActivity(intent);
        }

    }

    public void newProjectAddCommitment(View view) {

        if(teamMembers.isEmpty() == false){

            Intent intent = new Intent(CreateProject.this, CreateCommitment.class);
            intent.putExtra("pid", pid);
            startActivity(intent);

        }else{
            Toast.makeText(CreateProject.this, "You must first add team members.", Toast.LENGTH_SHORT).show();
        }

    }


    public void addProject(View view) {
        EditText titleView = (EditText) findViewById(R.id.newProjectName);
        EditText descriptionView = (EditText) findViewById(R.id.newProjectDescription);
        EditText deadlineView = (EditText) findViewById(R.id.editText10);
        title = String.valueOf(titleView.getText());
        description = String.valueOf(descriptionView.getText());
        deadline = String.valueOf(deadlineView.getText());


        if(title.equals("") || description.equals("") || deadline.equals("")){
            Toast.makeText(CreateProject.this, "Please enter project information first.", Toast.LENGTH_SHORT).show();
        }else{

            if(pid == null){
                ConnectionPost addProject = new ConnectionPost();
                addProject.execute("post_project", uid, title, description, deadline);

                try {
                    pid = addProject.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                ConnectionPost addLeader = new ConnectionPost();
                addLeader.execute("post_puser", uid, pid);
            }

            Intent intent = new Intent(CreateProject.this, ProjectList.class);
            intent.putExtra("uid", uid);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}
