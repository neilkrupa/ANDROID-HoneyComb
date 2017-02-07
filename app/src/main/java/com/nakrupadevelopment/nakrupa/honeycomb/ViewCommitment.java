package com.nakrupadevelopment.nakrupa.honeycomb;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by NAKRUPA on 2/11/2016.
 */
public class ViewCommitment extends Activity implements OnItemSelectedListener {
    private String cid;
    private String lid;
    private String uid;
    private String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcommitment);

        Intent called = getIntent();
        Bundle extras = called.getExtras();
        if (extras != null) {
            cid = called.getExtras().getString("cid");
            lid = called.getExtras().getString("lid");
            uid = called.getExtras().getString("uid");
            pid = called.getExtras().getString("pid");
        }

        System.out.println("-----CID: " + cid);
        System.out.println("-----LID: " + lid);
        System.out.println("-----UID: " + uid);
        System.out.println("-----PID: " + pid);

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

        Spinner spinner = (Spinner) findViewById(R.id.myCommitmentsSpinner);
        spinner.setOnItemSelectedListener(this);
        List<String> selection = new ArrayList<String>();
        selection.add("Request Refactor");
        selection.add("Post Started");
        selection.add("Post to Assist Forum");
        selection.add("Post Completion");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, selection);
        spinner.setAdapter(adapter);

    }

    public String item;
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void myCommitmentSubmit(View view) {
        if(item.equals("Request Refactor")){

            Toast.makeText(ViewCommitment.this, "Refactor Requested.", Toast.LENGTH_SHORT).show();

            ConnectionPost refactor = new ConnectionPost();
            refactor.execute("refactor_req", cid, lid, pid);

            Intent intent = new Intent(ViewCommitment.this, CommitmentList.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("uid", uid);
            intent.putExtra("lid", lid);
            intent.putExtra("pid", pid);
            startActivity(intent);


        } else if (item.equals("Post Started")){
            Toast.makeText(ViewCommitment.this, "Project leader notified of started commitment.", Toast.LENGTH_SHORT).show();

            ConnectionPost refactor = new ConnectionPost();
            refactor.execute("post_start", cid, lid, pid);

            Intent intent = new Intent(ViewCommitment.this, CommitmentList.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("uid", uid);
            intent.putExtra("lid", lid);
            intent.putExtra("pid", pid);
            startActivity(intent);


        } else if(item.equals("Post to Assist Forum")){

            Intent intent = new Intent(ViewCommitment.this, PostToAssist.class);
            intent.putExtra("uid", uid);
            intent.putExtra("cid", cid);
            intent.putExtra("pid", pid);
            startActivity(intent);

        } else if(item.equals("Post Completion")){

            Toast.makeText(ViewCommitment.this, "Completion Completed.", Toast.LENGTH_SHORT).show();
            ConnectionPost refactor = new ConnectionPost();
            refactor.execute("post_completion", cid, pid, uid);

            Intent intent = new Intent(ViewCommitment.this, CommitmentList.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("uid", uid);
            intent.putExtra("lid", lid);
            intent.putExtra("pid", pid);
            startActivity(intent);
        }
    }
}
