package com.nakrupadevelopment.nakrupa.honeycomb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by NAKRUPA on 3/23/2016.
 */
public class PostToAssist extends Activity {
    public String cid;
    public String uid;
    public String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posttoassist);

        Intent called = getIntent();
        Bundle extras = called.getExtras();
        if (extras != null) {
            cid = called.getExtras().getString("cid");
            uid = called.getExtras().getString("uid");
            pid = called.getExtras().getString("pid");
        }
    }


    public void Submit(View view) {

        EditText codeET = (EditText) findViewById(R.id.editText7);
        EditText descET = (EditText) findViewById(R.id.issuedesc);

        String code = String.valueOf(codeET.getText());
        String desc = String.valueOf(descET.getText());

        ConnectionPost postAssist = new ConnectionPost();
        postAssist.execute("post_assist", cid, pid, desc, code);

        Intent submit = new Intent(PostToAssist.this, AssistForum.class);
        submit.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        submit.putExtra("pid", pid);
        startActivity(submit);
    }
}
