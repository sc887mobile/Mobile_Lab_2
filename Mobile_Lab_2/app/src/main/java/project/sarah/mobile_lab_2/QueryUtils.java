package project.sarah.mobile_lab_2;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarah on 11/16/17.
 */

public class QueryUtils {
    /** Tag for the log messages */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    public static List<News> fetchNewsData2(String requestUrl) {
        List<News> news = new ArrayList<>();
        URL url = null;
        String jsonResponse = "";

        try {
            url=new URL(requestUrl);
            URLConnection urlConnection=url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            StringBuilder stringBuilder= new StringBuilder();
            InputStream inputStream=urlConnection.getInputStream();
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader in = new BufferedReader(inputStreamReader);

            String line =in.readLine();
            while(line!=null){
                stringBuilder.append(line);
                line=in.readLine();
            }

            jsonResponse=stringBuilder.toString();

            JSONObject reader = new JSONObject(jsonResponse);
            JSONObject response = reader.getJSONObject("response");
            JSONArray results=response.getJSONArray("results");
            for(int i=0;i<results.length();i++){
                JSONObject temp=results.getJSONObject(i);
                String mDate = temp.getString("webPublicationDate");
                String mTitle = temp.getString("webTitle");
                String mUrl = temp.getString("webUrl");
                String mConxtext=temp.getString("apiUrl");

                News nw = new News(mDate,mTitle,mConxtext,mUrl);
                news.add(nw);
            }

            in.close();            return news;

        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception:  "+e);
        }
        return news;
    }

    public static List<Books> fetchBooksData2(String requestUrl) {
        List<Books> books = new ArrayList<>();
        URL url = null;
        String jsonResponse = "";

        try {
            url=new URL(requestUrl);
            HttpURLConnection httpConnection=(HttpURLConnection)url.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setReadTimeout(10000);
            httpConnection.setConnectTimeout(15000);
            httpConnection.connect();

            int responseCode = httpConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                StringBuilder stringBuilder = new StringBuilder();
                InputStream inputStream = httpConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader in = new BufferedReader(inputStreamReader);

                String line = in.readLine();
                while (line != null) {
                    stringBuilder.append(line);
                    line = in.readLine();
                }
                in.close();

                jsonResponse = stringBuilder.toString();


                JSONObject reader = new JSONObject(jsonResponse);
                JSONArray items=reader.getJSONArray("items");
                for(int i=0;i<items.length();i++){
                    JSONObject temp=items.getJSONObject(i);
                    JSONObject volumeInfo = temp.getJSONObject("volumeInfo");
                    JSONArray author = volumeInfo.getJSONArray("authors");
                    String bAuthor = author.get(0).toString();
                    String bTitle = volumeInfo.getString("title");
                    String bpublishedDate = volumeInfo.getString("publishedDate");

                    System.out.println("AAA "+bAuthor+", "+bTitle+", "+bpublishedDate);
                    Books bk = new Books(bTitle,bAuthor,bpublishedDate);
                    books.add(bk);
                }

                return books;
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception:  "+e);
        }
        return books;
    }


}
