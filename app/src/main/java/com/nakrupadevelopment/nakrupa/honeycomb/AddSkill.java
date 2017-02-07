package com.nakrupadevelopment.nakrupa.honeycomb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by NAKRUPA on 3/23/2016.
 */
public class AddSkill extends Activity {
    public ArrayList<String> newSkills = new ArrayList<String>();
    public ArrayList<String> skillRating = new ArrayList<String>();
    public String uid;
    public String username;
    public String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addskill);

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
    }

    public void pointsUp(View view) {
        TextView pointScoreTv = (TextView) findViewById(R.id.textView5);
        String pointScore = pointScoreTv.getText().toString();
        int pSaI = Integer.parseInt(pointScore);
        pSaI++;
        pointScore = Integer.toString(pSaI);
        pointScoreTv.setText(pointScore);
    }

    public void pointsDown(View view) {
        TextView pointScoreTv = (TextView) findViewById(R.id.textView5);
        String pointScore = pointScoreTv.getText().toString();
        int pSaI = Integer.parseInt(pointScore);
        if(pSaI != 0){
            pSaI--;
        }
        pointScore = Integer.toString(pSaI);
        pointScoreTv.setText(pointScore);
    }

    public void addSkill(View view) {
        EditText skillName = (EditText) findViewById(R.id.editText6);
        TextView skillRat = (TextView) findViewById(R.id.textView5);
        String sn = String.valueOf(skillName.getText());
        String rs = String.valueOf(skillRat.getText());

        newSkills.add(sn);
        skillRating.add(rs);


        Intent addSkill = new Intent(AddSkill.this, SkillList.class);
        addSkill.putExtra("username", username);
        addSkill.putExtra("uid", uid);
        addSkill.putExtra("password", password);
        addSkill.putExtra("ns", newSkills);
        addSkill.putExtra("sr", skillRating);
        startActivity(addSkill);
    }
}
