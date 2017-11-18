package project.sarah.mobile_lab_2;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by sarah on 11/2/17.
 */

public class SignupActivity extends AppCompatActivity {

    TextView tv, tv2;
    EditText ed_fn, ed_ln, ed_email, ed_un, ed_pw, ed_dob, ed_addr;
    String st_fn, st_ln, st_email, st_un, st_pw, st_dob, st_addr;
    Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        tv=(TextView)findViewById(R.id.tv_main);
        tv2=(TextView)findViewById(R.id.tv_submain);
        tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/billabong.ttf"));
        tv2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/billabong.ttf"));

        btn_submit=(Button)findViewById(R.id.btn_submit);
        btn_submit.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/billabong.ttf"));
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInfo();
                movePage();
            }
        });
    }

    public void getInfo(){
        ed_fn=(EditText)findViewById(R.id.ed_fname);
        ed_ln=(EditText)findViewById(R.id.ed_lname);
        ed_email=(EditText)findViewById(R.id.ed_email);
        ed_un=(EditText)findViewById(R.id.ed_uname);
        ed_pw=(EditText)findViewById(R.id.ed_pw);
        ed_dob=(EditText)findViewById(R.id.ed_dob);
        ed_addr=(EditText)findViewById(R.id.ed_address);

        st_fn=ed_fn.getText().toString();
        st_ln=ed_ln.getText().toString();
        st_email=ed_email.getText().toString();
        st_un=ed_un.getText().toString();
        st_pw=ed_pw.getText().toString();
        st_dob=ed_dob.getText().toString();
        st_addr=ed_addr.getText().toString();
    }

    public void movePage(){

        Intent intent1=new Intent(SignupActivity.this, ChooseActivity.class);
        intent1.putExtra("uname", st_un);
        finish();
        startActivity(intent1);
    }
}
