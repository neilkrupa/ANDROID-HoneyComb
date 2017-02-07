package com.nakrupadevelopment.nakrupa.honeycomb;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
 * Created by NAKRUPA on 3/23/2016.
 */
public class EditRemoveProject extends Activity {

    public ArrayAdapter theAdapter;
    public ListView teamMembersList;
    public ArrayList<String> teamMembers = new ArrayList<String>();
    public ListView commitmentsList;
    public ArrayList<String> commitments = new ArrayList<String>();
    public ArrayList<String> commitmentsIndex = new ArrayList<String>();


    public String pid;
    public String username;
    public String title;
    public String description;
    public String deadline;
    public String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editremoveproject);

        Intent called = getIntent();
        Bundle extras = called.getExtras();
        if (extras != null) {
            if(called.getStringExtra("pid") != null){
                pid = called.getStringExtra("pid");
            }
            if(called.getStringExtra("uid") != null){
                uid = called.getStringExtra("uid");
            }
            if(called.getStringExtra("username") != null){
                username = called.getStringExtra("username");
            }
        }

        System.out.println("PID: " + pid);

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
                EditText deadlineView = (EditText) findViewById(R.id.deadline);
                String[] split = response.split(",");
                titleView.setText(split[0]);
                descriptionView.setText(split[1]);
                deadlineView.setText(split[2]);
                uid = split[5];
            }

            ListView commitmentListView = (ListView) findViewById(R.id.newProjectCommitmentList);
            commitmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(EditRemoveProject.this, EditRemoveCommitment.class);
                    intent.putExtra("cid", commitmentsIndex.get(position));
                    intent.putExtra("uid", uid);
                    intent.putExtra("username", username);
                    intent.putExtra("pid", pid);
                    startActivity(intent);
                }
            });
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
        //ADD MEMBER CODE-----------------------------------------------------------------------------

        Intent addTeamMember = new Intent(EditRemoveProject.this, AddTeamMember.class);
        addTeamMember.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(addTeamMember);
    }

    public void newProjectAddCommitment(View view) {
        if(teamMembers.isEmpty() == false){

            Intent intent = new Intent(EditRemoveProject.this, CreateCommitment.class);
            intent.putExtra("pid", pid);
            intent.putExtra("edit", "edit");
            startActivity(intent);

        }else{
            Toast.makeText(EditRemoveProject.this, "You must first add team members.", Toast.LENGTH_SHORT).show();
        }
    }

    public void removeProject(View view) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        ConnectionRemove removeProject = new ConnectionRemove();
                        removeProject.execute("remove_project", pid);

                        Intent intent = new Intent(EditRemoveProject.this, ProjectList.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("username", username);
                        intent.putExtra("uid", uid);
                        startActivity(intent);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("Are you sure you want to remove this project?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    public void saveProject(View view) {

        EditText nameET = (EditText) findViewById(R.id.newProjectName);
        EditText descET = (EditText) findViewById(R.id.newProjectDescription);
        EditText deadET = (EditText) findViewById(R.id.deadline);

        title = String.valueOf(nameET.getText());
        description = String.valueOf(descET.getText());
        deadline = String.valueOf(deadET.getText());


        ConnectionPost getCommitments = new ConnectionPost();
        getCommitments.execute("update_project", pid, title, description, deadline);

        Intent intent = new Intent(EditRemoveProject.this, ViewProject.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("uid", uid);
        intent.putExtra("pid", pid);
        startActivity(intent);
    }

}
