package com.nakrupadevelopment.nakrupa.honeycomb;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


/**
 * Created by NAKRUPA on 2/11/2016.
 */
public class EditRemoveCommitment extends Activity {
    public ArrayList<String> usersAssigned = new ArrayList<String>();
    public String cid;
    public ArrayAdapter theAdapter;
    public ListView teamMembersList;

    public String pid;
    public String username;
    public String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editremovecommitment);

        Intent called = getIntent();
        Bundle extras = called.getExtras();
        if (extras != null) {
            if(called.getStringExtra("cid") != null){
                cid = called.getStringExtra("cid");
            }
            if(called.getStringExtra("uid") != null){
                uid = called.getStringExtra("uid");
            }
            if(called.getStringExtra("username") != null){
                username = called.getStringExtra("username");
            }
            if(called.getStringExtra("pid") != null){
                pid = called.getStringExtra("pid");
            }
        }

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

            TextView sdeadline = (TextView) findViewById(R.id.newCommitmentStartDeadline);
            sdeadline.setText(split[3]);

            TextView edeadline = (TextView) findViewById(R.id.newCommitmentEndDeadline);
            edeadline.setText(split[4]);

            TextView points = (TextView) findViewById(R.id.newCommitmentPoints);
            points.setText(split[2]);
        }

        ConnectionRetrieve getCommitments = new ConnectionRetrieve();
        getCommitments.execute("get_commitment_members", cid);
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
                usersAssigned.add(split[i]);
            }
        }

        if(usersAssigned != null){
            theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, usersAssigned);
            teamMembersList = (ListView) findViewById(R.id.newCommitmentUserList);
            theAdapter.notifyDataSetChanged();
            teamMembersList.setAdapter(theAdapter);
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

    public void editCommitment(View view) {
        EditText commitmentTitle = (EditText) findViewById(R.id.newCommitmentTitle);
        String title = String.valueOf(commitmentTitle.getText());

        EditText commitmentDescription = (EditText) findViewById(R.id.newCommitmentDescription);
        String description = String.valueOf(commitmentDescription.getText());

        TextView commitmentPoints = (TextView) findViewById(R.id.newCommitmentPoints);
        String pointsString = String.valueOf(commitmentPoints.getText());

        EditText commitmentEndDeadline = (EditText) findViewById(R.id.newCommitmentEndDeadline);
        String endDeadline = String.valueOf(commitmentEndDeadline.getText());

        EditText commitmentStartDeadline = (EditText) findViewById(R.id.newCommitmentStartDeadline);
        String startDeadline = String.valueOf(commitmentStartDeadline.getText());

        ConnectionPost updateCommitment = new ConnectionPost();
        updateCommitment.execute("update_commitment", cid, title, description, pointsString, endDeadline, startDeadline, pid);

        Intent intent = new Intent(EditRemoveCommitment.this, EditRemoveProject.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("pid", pid);
        intent.putExtra("uid", uid);
        intent.putExtra("username", username);
        startActivity(intent);

    }

    public void removeCommitment(View view) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        ConnectionRemove removeProject = new ConnectionRemove();
                        removeProject.execute("remove_commitment", cid);

                        Intent intent = new Intent(EditRemoveCommitment.this, EditRemoveProject.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("pid", pid);
                        intent.putExtra("uid", uid);
                        intent.putExtra("username", username);
                        startActivity(intent);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("Are you sure you want to remove this commitment?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }
}
