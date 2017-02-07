package com.nakrupadevelopment.nakrupa.honeycomb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * Created by NAKRUPA on 2/11/2016.
 */
public class CreateCommitment extends Activity{


    public String pid;
    public String cid;
    public String name;
    public String desc;
    public String cpoints;
    public String csdeadline;
    public String cfeadline;

    public ArrayAdapter theAdapter;
    public ListView commitmentUsersView;
    public ArrayList<String> commitmentUsers = new ArrayList<String>();
    public ArrayList<String> commitmentUsersIndex = new ArrayList<String>();
    public boolean todo = false;

    public String edited = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createcommitment);

        Intent called = getIntent();
        Bundle extras = called.getExtras();
        if (extras != null) {
            if(called.getStringExtra("pid") != null){
                pid = called.getStringExtra("pid");
            }
            if(called.getStringExtra("cid") != null){
                cid = called.getStringExtra("cid");
            }
            if(called.getStringExtra("edit") != null){
                edited = called.getStringExtra("edit");
            }
        }

        if(cid != null){
            ConnectionRetrieve retrieve = new ConnectionRetrieve();
            retrieve.execute("get_commitment", cid);
            String response = null;
            try {
                response = retrieve.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            if(response != null){
                String[] split = response.split(",");
                TextView title = (TextView) findViewById(R.id.newCommitmentTitle);
                title.setText(split[0]);

                EditText desc = (EditText) findViewById(R.id.newCommitmentDescription);
                desc.setText(split[1]);

                TextView sdeadline = (TextView) findViewById(R.id.newCommitmentPoints);
                sdeadline.setText(split[2]);

                TextView edeadline = (TextView) findViewById(R.id.editText);
                edeadline.setText(split[3]);

                TextView points = (TextView) findViewById(R.id.editText9);
                points.setText(split[4]);
            }

            ConnectionRetrieve getCmembers = new ConnectionRetrieve();
            getCmembers.execute("get_commitment_members", cid);
            response = null;
            try {
                response = getCmembers.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            if(response != null){
                String[] split = response.split(",");
                for(int i=0; i<split.length-1; i=i+2){
                    commitmentUsers.add(split[i]);
                }
            }

            if(commitmentUsers != null){
                theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, commitmentUsers);
                commitmentUsersView = (ListView) findViewById(R.id.newCommitmentUserList);
                theAdapter.notifyDataSetChanged();
                commitmentUsersView.setAdapter(theAdapter);
                Button disable = (Button) findViewById(R.id.newCommitmentAddUsersButton);
                Button todobut = (Button) findViewById(R.id.button);
                disable.setEnabled(false);
                todobut.setEnabled(false);
            }

        }
    }

    public void adjustPointsAdd(View view) {
        TextView pointScoreTv = (TextView) findViewById(R.id.newCommitmentPoints);
        String pointScore = pointScoreTv.getText().toString();
        int pSaI = Integer.parseInt(pointScore);
        pSaI++;
        pointScore = Integer.toString(pSaI);
        pointScoreTv.setText(pointScore);
    }

    public void adjustPointsRem(View view) {
        TextView pointScoreTv = (TextView) findViewById(R.id.newCommitmentPoints);
        String pointScore = pointScoreTv.getText().toString();
        int pSaI = Integer.parseInt(pointScore);
        if(pSaI != 0){
            pSaI--;
        }
        pointScore = Integer.toString(pSaI);
        pointScoreTv.setText(pointScore);
    }

    public void addCommitmentUser(View view){
        EditText title = (EditText) findViewById(R.id.newCommitmentTitle);
        EditText description = (EditText) findViewById(R.id.newCommitmentDescription);
        TextView points = (TextView) findViewById(R.id.newCommitmentPoints);
        EditText sdeadline = (EditText) findViewById(R.id.editText);
        EditText edeadline = (EditText) findViewById(R.id.editText9);

        name = String.valueOf(title.getText());
        desc = String.valueOf(description.getText());
        cpoints = String.valueOf(points.getText());
        csdeadline = String.valueOf(sdeadline.getText());
        cfeadline = String.valueOf(edeadline.getText());

        if(name.equals("") | desc.equals("") | cpoints.equals("") | csdeadline.equals("") | cfeadline.equals("")){
            Toast.makeText(CreateCommitment.this, "Please enter commitment information first.", Toast.LENGTH_SHORT).show();
        }else {

            ConnectionPost addCommitment = new ConnectionPost();
            addCommitment.execute("post_commitment", pid, name, desc, cpoints, csdeadline, cfeadline);

            try {
                cid = addCommitment.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


            Intent intent = new Intent(CreateCommitment.this, AddCommitmentMember.class);
            intent.putExtra("edit", edited);
            intent.putExtra("pid", pid);
            intent.putExtra("cid", cid);
            startActivity(intent);
        }
    }

    public void createCommitment(View view){
        System.out.println("CIDCIDCIDICDICIDICIDIC: " + cid);
        if(todo == true){
            ConnectionPost postTodo = new ConnectionPost();
            postTodo.execute("post_todo", pid, name, cid);
        }

        if(edited.equals("edit")){
            Intent intent = new Intent(CreateCommitment.this, EditRemoveProject.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("pid", pid);
            startActivity(intent);
        }else{
            Intent intent = new Intent(CreateCommitment.this, CreateProject.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("pid", pid);
            startActivity(intent);
        }
    }

    public void attTodolist(View view) {

        EditText title = (EditText) findViewById(R.id.newCommitmentTitle);
        EditText description = (EditText) findViewById(R.id.newCommitmentDescription);
        TextView points = (TextView) findViewById(R.id.newCommitmentPoints);
        EditText sdeadline = (EditText) findViewById(R.id.editText);
        EditText edeadline = (EditText) findViewById(R.id.editText9);

        name = String.valueOf(title.getText());
        desc = String.valueOf(description.getText());
        cpoints = String.valueOf(points.getText());
        csdeadline = String.valueOf(sdeadline.getText());
        cfeadline = String.valueOf(edeadline.getText());

        if(name.equals("") | desc.equals("") | cpoints.equals("") | csdeadline.equals("") | cfeadline.equals("")){
            Toast.makeText(CreateCommitment.this, "Please enter commitment information first.", Toast.LENGTH_SHORT).show();
        }else {
            todo = true;
            Button disable = (Button) findViewById(R.id.newCommitmentAddUsersButton);
            Button todobut = (Button) findViewById(R.id.button);
            disable.setEnabled(false);
            todobut.setEnabled(false);
            commitmentUsers.add("Added to To-Do List");
            theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, commitmentUsers);
            commitmentUsersView = (ListView) findViewById(R.id.newCommitmentUserList);
            theAdapter.notifyDataSetChanged();
            commitmentUsersView.setAdapter(theAdapter);


            ConnectionPost addCommitment = new ConnectionPost();
            addCommitment.execute("post_commitment", pid, name, desc, cpoints, csdeadline, cfeadline);

            String result = null;
            try {
                result = addCommitment.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            if (result != null) {
                String[] split = result.split(",");
                cid = split[split.length - 1];
            }
            System.out.println(cid);
        }
    }
}
