package com.nakrupadevelopment.nakrupa.honeycomb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import javax.sql.StatementEvent;

/**
 * Created by NAKRUPA on 4/8/2016.
 */
public class ViewResponse extends Activity {

    public String description;
    public String restitle;
    public String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewresponse);

        Intent called = getIntent();
        Bundle extras = called.getExtras();
        if (extras != null) {
            description = called.getExtras().getString("description");
            restitle = called.getExtras().getString("restitle");
            response = called.getExtras().getString("response");
        }

        EditText descriptionET = (EditText) findViewById(R.id.issuedesc);
        EditText restitleET = (EditText) findViewById(R.id.editText13);
        EditText responseET = (EditText) findViewById(R.id.editText8);

        descriptionET.setText(description);
        restitleET.setText(restitle);
        responseET.setText(response);
    }
}
