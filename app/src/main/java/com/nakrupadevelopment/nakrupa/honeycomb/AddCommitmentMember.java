package com.nakrupadevelopment.nakrupa.honeycomb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by NAKRUPA on 2/11/2016.
 */
public class AddCommitmentMember extends Activity {

    public String pid;
    public String cid;
    public String edited;

    public ArrayAdapter theAdapter;
    public ArrayList<String> users = new ArrayList<String>();
    public ArrayList<String> usersIndex = new ArrayList<String>();
    public ArrayList<String> commitmentUsers = new ArrayList<String>();
    public ArrayList<String> commitmentUsersIndex = new ArrayList<String>();
    public ListView TeamMemberList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addteammember);

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

        ConnectionRetrieve getPUsers = new ConnectionRetrieve();
        getPUsers.execute("get_project_users", pid);
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
                users.add(split[i]);
                usersIndex.add(split[i+1]);
            }
        }


        if(users != null){
            theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, users);
            TeamMemberList = (ListView) findViewById(R.id.listView2);
            theAdapter.notifyDataSetChanged();
            TeamMemberList.setAdapter(theAdapter);
        }

        TeamMemberList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                commitmentUsers.add(users.get(position));
                commitmentUsersIndex.add(usersIndex.get(position));
            }
        });

        TeamMemberList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int pos, long id) {
                Intent intent = new Intent(AddCommitmentMember.this, ViewUser.class);
                intent.putExtra("username", users.get(pos));
                startActivity(intent);
                return true;
            }
        });
    }


    public void addMembers(View view) {

        for(int i=0; i<commitmentUsers.size(); i++){
            ConnectionPost addMembers = new ConnectionPost();
            addMembers.execute("post_commitment_member", commitmentUsersIndex.get(i), cid, pid);
        }

        Intent addCommitmentMember = new Intent(AddCommitmentMember.this, CreateCommitment.class);
        addCommitmentMember.putExtra("cid", cid);
        addCommitmentMember.putExtra("pid", pid);
        addCommitmentMember.putExtra("edit", edited);
        startActivity(addCommitmentMember);
    }
}
