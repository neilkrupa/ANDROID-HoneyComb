package com.nakrupadevelopment.nakrupa.honeycomb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by NAKRUPA on 3/23/2016.
 */
public class Issue extends Activity {

    private String cid;
    private String pid;
    public String description;
    public ArrayAdapter theAdapter;
    public ListView responseList;
    public ArrayList<String> responseArr = new ArrayList<String>();
    public ArrayList<String> responseArrDesc = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.issue);

        Intent called = getIntent();
        Bundle extras = called.getExtras();
        if (extras != null) {
            cid = called.getExtras().getString("cid");
            pid = called.getExtras().getString("pid");
        }

        ConnectionRetrieve getIssue = new ConnectionRetrieve();
        getIssue.execute("get_issue", cid);
        String response = "";
        try {
            response = getIssue.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        if(response != null){
            String[] split = response.split(",");
            EditText issuecodeview = (EditText) findViewById(R.id.issuecode);
            EditText issuedescription = (EditText) findViewById(R.id.issuedesc);
            issuecodeview.setText(split[0]);
            issuedescription.setText(split[1]);
            description = split[1];
        }

        System.out.println("CID:::::::::::" + cid);
        ConnectionRetrieve getUsersTask = new ConnectionRetrieve();
        getUsersTask.execute("get_issue_responses", cid);
        String res = "";
        try {
            res = getUsersTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        responseArr.add("No responses.");

        if(res != null){
            responseArr.clear();
            String[] split = res.split(",");
            for(int i = 0; i<split.length; i=i+2){
               responseArr.add(split[i]);
                responseArrDesc.add(split[i+1]);
            }
            System.out.println(responseArr);
        }

        theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, responseArr);
        responseList = (ListView) findViewById(R.id.listView5);
        theAdapter.notifyDataSetChanged();
        responseList.setAdapter(theAdapter);

        responseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(Issue.this, ViewResponse.class);
                intent.putExtra("description", description);
                intent.putExtra("restitle", responseArr.get(position));
                intent.putExtra("response", responseArrDesc.get(position));
                startActivity(intent);
            }
        });
    }

    public void uploadPhoto(View view) {
    }


    public void submitResponse(View view) {
        Intent submitResponse = new Intent(Issue.this, RespondAssist.class);
        submitResponse.putExtra("cid", cid);
        submitResponse.putExtra("pid", pid);
        startActivity(submitResponse);
    }
}
