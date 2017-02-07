package com.nakrupadevelopment.nakrupa.honeycomb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

/**
 * Created by NAKRUPA on 3/23/2016.
 */
public class RespondAssist extends Activity {
    public String pid;
    public String cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.respondassist);

        Intent called = getIntent();
        Bundle extras = called.getExtras();
        if (extras != null) {
            pid = called.getExtras().getString("pid");
            cid = called.getExtras().getString("cid");
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
            EditText issuedesc = (EditText) findViewById(R.id.issuedesc);
            issuedesc.setText(split[1]);
        }
    }

    public void Submit(View view) {
        EditText titleET = (EditText) findViewById(R.id.editText13);
        EditText resET = (EditText) findViewById(R.id.editText8);

        String title = String.valueOf(titleET.getText());
        String res = String.valueOf(resET.getText());

        System.out.println(title);
        System.out.println(res);

        ConnectionPost postResponse = new ConnectionPost();
        postResponse.execute("post_response", cid, pid, title, res);


        Intent submit = new Intent(RespondAssist.this, Issue.class);
        submit.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        submit.putExtra("cid", cid);
        submit.putExtra("pid", pid);
        startActivity(submit);
    }
}
