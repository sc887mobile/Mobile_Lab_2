package project.sarah.mobile_lab_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

/**
 * Created by sarah on 11/16/17.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, List<News> newslist) {
        super(context, 0, newslist);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }

        News currentNews = getItem(position);

        TextView dateView = (TextView) listItemView.findViewById(R.id.tv_date);
        String date = currentNews.getmDate();
        dateView.setText(date);

        String title = currentNews.getmTitle();
        TextView titleView = (TextView) listItemView.findViewById(R.id.tv_title);
        titleView.setText(title);

        String context = currentNews.getmContext();
        TextView contextView = (TextView)listItemView.findViewById(R.id.tv_context);

        return listItemView;
    }
}
