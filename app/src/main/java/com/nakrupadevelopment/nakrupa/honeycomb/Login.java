package com.nakrupadevelopment.nakrupa.honeycomb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by NAKRUPA on 1/20/2016.
 */
public class Login extends Activity{

    List<String> usernames = new ArrayList<String>();
    List<String> passwords = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        EditText usernameLoginET = (EditText) findViewById(R.id.usernameETlogin);
        EditText passwordLoginET = (EditText) findViewById(R.id.passwordETlogin);
    }

    public void registerButtonClick(View view) {
        Intent intent = new Intent(Login.this,Register.class);
        startActivity(intent);
    }


    public void loginButtonClick(View view) {


        EditText usernameLoginET = (EditText) findViewById(R.id.usernameETlogin);
        EditText passwordLoginET = (EditText) findViewById(R.id.passwordETlogin);

        String usernameLoginString = String.valueOf(usernameLoginET.getText());
        String passwordLoginString = String.valueOf(passwordLoginET.getText());

        ConnectionValidate backgroundTask = new ConnectionValidate();
        backgroundTask.execute("validate_login", usernameLoginString, passwordLoginString);
        String passORfail = "";

        try {
            passORfail = backgroundTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        String[] split = {"Error"};

        if(passORfail != null){
            split = passORfail.split(",");
        }

        if(split[0].equals("1")){

            Intent login = new Intent(Login.this,Main.class);
            login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            login.putExtra("username", usernameLoginString);
            login.putExtra("uid", split[1]);
            startActivity(login);
            Toast.makeText(Login.this, "Welcome " + usernameLoginString, Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(Login.this, "Login Details Incorrect", Toast.LENGTH_SHORT).show();
        }
    }
}
