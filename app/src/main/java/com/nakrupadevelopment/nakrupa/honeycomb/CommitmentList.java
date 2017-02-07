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
public class CommitmentList extends Activity {
    public String uid;
    public String pid;
    public String lid;
    public ListView commitmentList;
    public ArrayAdapter theAdapter;
    public ArrayList<String> commitments = new ArrayList<String>();
    public ArrayList<String> cidIndex = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commitmentlist);

        Intent called = getIntent();
        Bundle extras = called.getExtras();
        if (extras != null) {
            uid = called.getExtras().getString("uid");
            pid = called.getExtras().getString("pid");
            lid = called.getExtras().getString("lid");
        }

        System.out.println("-----PID: " + pid);


        theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, commitments);
        commitmentList = (ListView) findViewById(R.id.myCommitmentsList);

        ConnectionRetrieve getUsersTask = new ConnectionRetrieve();
        getUsersTask.execute("get_commitment_list", uid, pid);
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
            for(int i = 0; i<split.length-1; i=i+2){
                commitments.add(split[i]);
                cidIndex.add(split[i+1]);
            }
        }

        System.out.println(response);

        theAdapter.notifyDataSetChanged();
        commitmentList.setAdapter(theAdapter);

        commitmentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(CommitmentList.this, ViewCommitment.class);
                intent.putExtra("cid", cidIndex.get(position));
                intent.putExtra("lid", lid);
                intent.putExtra("uid", uid);
                intent.putExtra("pid", pid);
                startActivity(intent);
            }
        });
    }

}
