package com.nakrupadevelopment.nakrupa.honeycomb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

/**
 * Created by NAKRUPA on 1/20/2016.
 */
public class Register extends Activity{
    private EditText usernameET;
    private EditText emailET;
    private EditText passwordET;
    private EditText repasswordET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        emailET = (EditText) findViewById(R.id.emailET);
        usernameET = (EditText) findViewById(R.id.usernameET);
        passwordET = (EditText) findViewById(R.id.passwordET);
        repasswordET = (EditText) findViewById(R.id.reenterPasswordET);
    }

    public void registerScreenRegButtonClick(View view) {

        String username = String.valueOf(usernameET.getText());
        String email = String.valueOf(emailET.getText());
        String password = String.valueOf(passwordET.getText());
        String repassword = String.valueOf(repasswordET.getText());

        if(username.equals("") || email.equals("")){
            Toast.makeText(Register.this, "Please fill out the whole form.", Toast.LENGTH_SHORT).show();
        }else {

            if (password.equals(repassword)) {

                ConnectionValidate backgroundTask = new ConnectionValidate();
                backgroundTask.execute("validate_registration", username);
                String result = "";

                try {
                    result = backgroundTask.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                if (result.equals("0")) {
                    Toast.makeText(Register.this, "Username already in use.", Toast.LENGTH_SHORT).show();
                } else if (result.equals("1")) {

                    ConnectionPost createUserTask = new ConnectionPost();
                    createUserTask.execute("post_user", username, password, email);
                    String createUserResult = "";

                    try {
                        createUserResult = backgroundTask.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    if (createUserResult.equals("1")) {
                        Toast.makeText(Register.this, "Registration Successful.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Register.this, "Registration Failed.", Toast.LENGTH_SHORT).show();
                    }

                    Intent submit = new Intent(Register.this, Login.class);
                    startActivity(submit);
                }

            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Passwords do not match.", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }
}
