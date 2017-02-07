package com.nakrupadevelopment.nakrupa.honeycomb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by NAKRUPA on 3/23/2016.
 */
public class Settings extends Activity {
    public String uid;
    public String username;
    public ListView skillList;
    public ArrayAdapter theAdapter;
    private String password;
    public ArrayList<String> list = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        Intent called = getIntent();
        Bundle extras = called.getExtras();
        if (extras != null) {
            username = called.getExtras().getString("username");
            uid = called.getExtras().getString("uid");
        }

        ConnectionRetrieve getUsersTask = new ConnectionRetrieve();
        getUsersTask.execute("get_settings", uid);
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
            System.out.println(response);
            EditText uname = (EditText) findViewById(R.id.editText2);
            EditText email = (EditText) findViewById(R.id.editText5);
            uname.setText(split[0]);
            email.setText(split[2]);
            password=split[1];
        }

        theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        skillList = (ListView) findViewById(R.id.listView4);

        getUsersTask = new ConnectionRetrieve();
        getUsersTask.execute("get_skill_list", uid);
        response = null;
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
                list.add(split[i] + "        " + split[i+1] + "/5");
            }
        }

        theAdapter.notifyDataSetChanged();
        skillList.setAdapter(theAdapter);
    }

    public void EditSkillList(View view) {
        Intent editSkillList = new Intent(Settings.this, SkillList.class);
        editSkillList.putExtra("uid", uid);
        editSkillList.putExtra("password", password);
        editSkillList.putExtra("username", username);
        //editSkillList CODE -------------------------------------------------------------------------
        startActivity(editSkillList);
    }

    public void Submit(View view) {
        EditText pwd = (EditText) findViewById(R.id.editText12);
        String testpassword = String.valueOf(pwd.getText());

        EditText npwd = (EditText) findViewById(R.id.editText3);
        String newPassword = String.valueOf(npwd.getText());
        EditText rtpwd = (EditText) findViewById(R.id.editText4);
        String retypePassword = String.valueOf(rtpwd.getText());
        EditText nemail = (EditText) findViewById(R.id.editText5);
        String newEmail = String.valueOf(nemail.getText());


        if(password.equals(testpassword)){
            if(newPassword.equals(retypePassword)){
                if(newPassword.equals("")){
                    Toast.makeText(Settings.this, "Please enter a valid new password", Toast.LENGTH_SHORT).show();
                }else{
                    ConnectionPost updateSettings = new ConnectionPost();
                    updateSettings.execute("post_settings", newPassword, newEmail, uid);

                    System.out.println(newPassword);
                    System.out.println(newEmail);
                    System.out.println(uid);

                    Intent submit = new Intent(Settings.this, ViewProfile.class);
                    submit.putExtra("username", username);
                    submit.putExtra("uid", uid);
                    startActivity(submit);
                }
            }
        }else{
            Toast.makeText(Settings.this, "Password incorrect", Toast.LENGTH_SHORT).show();
        }


    }
}
