package project.sarah.mobile_lab_2;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarah on 11/17/17.
 */

public class BooksActivity extends AppCompatActivity {

    private static final String LOG_TAG = BooksActivity.class.getName();

    private static final String USGS_REQUEST_URL1 = "https://www.googleapis.com/books/v1/volumes?q=";
    private BooksAdapter mAdapter;
    private String book_name;
    private EditText ed_books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        ed_books = (EditText)findViewById(R.id.ed_books);

        Button btn_submit=(Button)findViewById(R.id.btn_booksubmit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                book_name = ed_books.getText().toString();
                final String temp_string=USGS_REQUEST_URL1+book_name;
                BooksActivity.BooksAsyncTask task = new BooksActivity.BooksAsyncTask();
                task.execute(temp_string);
            }
        });

        ListView booksListView = (ListView) findViewById(R.id.list_books);
        mAdapter = new BooksAdapter(this, new ArrayList<Books>());
        booksListView.setAdapter(mAdapter);
        booksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Books currentBooks = mAdapter.getItem(position);
            }
        });
    }

    private class BooksAsyncTask extends AsyncTask<String, Void, List<Books>> {

        @Override
        protected List<Books> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<Books> result = QueryUtils.fetchBooksData2(USGS_REQUEST_URL1+book_name);
            return result;
        }

        @Override
        protected void onPostExecute(List<Books> data) {
            mAdapter.clear();
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }
        }
    }

}
