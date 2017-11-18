package project.sarah.mobile_lab_2;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    Button btn_signin, btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv= (TextView)findViewById(R.id.welcome_message);
        tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/billabong.ttf"));

        btn_signin=(Button)findViewById(R.id.btn_signin);
        btn_signup=(Button)findViewById(R.id.btn_signup);

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(MainActivity.this, LoginActivity.class);
                finish();
                startActivity(intent1);
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(MainActivity.this, SignupActivity.class);
                finish();
                startActivity(intent1);
            }
        });

    }
}
