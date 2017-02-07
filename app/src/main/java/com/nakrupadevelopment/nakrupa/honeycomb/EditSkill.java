package com.nakrupadevelopment.nakrupa.honeycomb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by NAKRUPA on 3/23/2016.
 */
public class EditSkill extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editskill);
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

    public void submitChanges(View view) {
        Intent submitChange = new Intent(EditSkill.this, SkillList.class);
        //addTeamMember CODE --------------------------------------------------------------------
        startActivity(submitChange);
    }
}
