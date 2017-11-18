package project.sarah.mobile_lab_2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by sarah on 11/17/17.
 */

public class ChooseActivity extends AppCompatActivity {

    Button btn_news, btn_books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        btn_news=(Button)findViewById(R.id.btn_news);
        btn_books=(Button)findViewById(R.id.btn_books);

        btn_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseActivity.this, NewsActivity.class);
                startActivity(intent);
            }
        });

        btn_books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseActivity.this, BooksActivity.class);
                startActivity(intent);
            }
        });

    }
}
