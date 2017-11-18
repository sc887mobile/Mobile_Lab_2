package project.sarah.mobile_lab_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sarah on 11/17/17.
 */

public class BooksAdapter extends ArrayAdapter<Books> {

    public BooksAdapter(Context context, List<Books> booksList) {
        super(context, 0, booksList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.books_list_item, parent, false);
        }

        Books currentBooks = getItem(position);

        TextView authorView = (TextView) listItemView.findViewById(R.id.tv_bauthor);
        String author = currentBooks.getbAuthor();
        authorView.setText(author);

        String title = currentBooks.getbTitle();
        TextView bTitleView = (TextView) listItemView.findViewById(R.id.tv_bTitle);
        bTitleView.setText(title);

        String publishedDate = currentBooks.getbContext();
        TextView publishedDateView = (TextView)listItemView.findViewById(R.id.tv_book3);
        publishedDateView.setText(publishedDate);

        return listItemView;
    }
}
