package com.nakrupadevelopment.nakrupa.honeycomb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by NAKRUPA on 3/23/2016.
 */
public class SkillList extends Activity {
    public String uid;
    public String username;
    public String password;
    public ListView skillList;
    public ArrayAdapter theAdapter;
    public ArrayList<String> list = new ArrayList<String>();

    public ArrayList<String> newSkills = new ArrayList<String>();
    public ArrayList<String> skillRating = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skilllist);

        Intent called = getIntent();
        Bundle extras = called.getExtras();
        if (extras != null) {
            username = called.getExtras().getString("username");
            uid = called.getExtras().getString("uid");
            password = called.getExtras().getString("password");

            if(called.getExtras().getStringArrayList("ns") != null){
                newSkills = called.getExtras().getStringArrayList("ns");
            }

            if(called.getExtras().getStringArrayList("sr") != null){
                skillRating = called.getExtras().getStringArrayList("sr");
            }
        }

        theAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        skillList = (ListView) findViewById(R.id.listView);

        String response = null;
        ConnectionRetrieve getUsersTask = new ConnectionRetrieve();
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
        if(newSkills != null){
            for(int i=0; i<newSkills.size(); i++){
                list.add(newSkills.get(i) + "        " + skillRating.get(i) + "/5");
            }
        }


        theAdapter.notifyDataSetChanged();
        skillList.setAdapter(theAdapter);
    }

    public void AddSkill(View view) {
        Intent addSkill = new Intent(SkillList.this, AddSkill.class);
        addSkill.putExtra("username", username);
        addSkill.putExtra("uid", uid);
        addSkill.putExtra("password", password);
        addSkill.putExtra("ns", newSkills);
        addSkill.putExtra("sr", skillRating);
        startActivity(addSkill);
    }

    public void Submit(View view) {
        EditText passCheck = (EditText) findViewById(R.id.editText11);
        String check = String.valueOf(passCheck.getText());

        if(password.equals(check)){

            //Add all new skills.

            Intent settings = new Intent(SkillList.this, Settings.class);

            //Add all required addons.

            startActivity(settings);
        }else{
            Toast.makeText(SkillList.this, "Password incorrect", Toast.LENGTH_SHORT).show();
        }
    }
}
