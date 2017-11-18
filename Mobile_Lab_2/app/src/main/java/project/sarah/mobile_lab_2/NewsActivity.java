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
 * Created by sarah on 11/16/17.
 */

public class NewsActivity extends AppCompatActivity {

    private static final String LOG_TAG = NewsActivity.class.getName();

    private static final String USGS_REQUEST_URL1 =
            "https://content.guardianapis.com/search?q=";
    private static final String USGS_REQUEST_URL2 =
            "&order-by=newest&api-key=28f284d1-9f76-482c-ac96-ef0f0fd76cba";
    private NewsAdapter mAdapter;
    private String topic_string;
    private EditText ed_topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        ed_topic = (EditText)findViewById(R.id.ed_topic);

        Button btn_submit=(Button)findViewById(R.id.btn_sendquery);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topic_string = ed_topic.getText().toString();
                final String temp_string=USGS_REQUEST_URL1+topic_string+USGS_REQUEST_URL2;
                NewsAsyncTask task = new NewsAsyncTask();
                task.execute(temp_string);
            }
        });

        ListView newsListView = (ListView) findViewById(R.id.list);
        mAdapter = new NewsAdapter(this, new ArrayList<News>());
        newsListView.setAdapter(mAdapter);
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                News currentNews = mAdapter.getItem(position);

                Uri newsUri = Uri.parse(currentNews.getmUrl());

                Intent intent = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(intent);
            }
        });
    }

        private class NewsAsyncTask extends AsyncTask<String, Void, List<News>> {

            @Override
            protected List<News> doInBackground(String... urls) {
                if (urls.length < 1 || urls[0] == null) {
                    return null;
                }

                List<News> result = QueryUtils.fetchNewsData2(USGS_REQUEST_URL1+topic_string+USGS_REQUEST_URL2);
                return result;
            }

            @Override
            protected void onPostExecute(List<News> data) {
                mAdapter.clear();
                if (data != null && !data.isEmpty()) {
                    mAdapter.addAll(data);
                }
            }
        }
    }

