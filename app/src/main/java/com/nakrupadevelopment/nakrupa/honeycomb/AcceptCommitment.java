package com.nakrupadevelopment.nakrupa.honeycomb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

/**
 * Created by NAKRUPA on 3/23/2016.
 */
public class AcceptCommitment extends Activity {

    public String cid;
    public String pid;
    public String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acceptcommitment);

        Intent called = getIntent();
        Bundle extras = called.getExtras();
        if (extras != null) {
            cid = called.getExtras().getString("cid");
            pid = called.getExtras().getString("pid");
            uid = called.getExtras().getString("uid");
            System.out.println(uid);
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
            TextView title = (TextView) findViewById(R.id.myCommitmentsTitle);
            title.setText(split[0]);

            EditText desc = (EditText) findViewById(R.id.myCommitmentsDescription);
            desc.setText(split[1]);

            TextView sdeadline = (TextView) findViewById(R.id.myCommitmentsStartDeadline);
            sdeadline.setText(split[3]);

            TextView edeadline = (TextView) findViewById(R.id.myCommitmentsEndDeadline);
            edeadline.setText(split[4]);

            TextView points = (TextView) findViewById(R.id.myCommitmentsPoints);
            points.setText(split[2]);
        }
    }

    public void acceptCommitment(View view) {

        ConnectionRemove acceptItem = new ConnectionRemove();
        acceptItem.execute("remove_todo", cid, uid);

        Intent acceptCommitment = new Intent(AcceptCommitment.this, ViewProject.class);
        acceptCommitment.putExtra("pid", pid);
        acceptCommitment.putExtra("uid", uid);
        startActivity(acceptCommitment);
    }
}
