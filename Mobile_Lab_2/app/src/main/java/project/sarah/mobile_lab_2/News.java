package project.sarah.mobile_lab_2;

/**
 * Created by sarah on 11/16/17.
 */

public class News {

    private String mDate;
    private String mTitle;
    private String mContext;
    private String mUrl;

    public News(String mDate, String mTitle, String mContext, String mUrl){
        this.mDate=mDate;
        this.mTitle=mTitle;
        this.mContext=mContext;
        this.mUrl=mUrl;
    }

    public String getmDate(){
        return this.mDate;
    }

    public String getmTitle(){
        return this.mTitle;
    }

    public String getmContext(){
        return this.mContext;
    }

    public String getmUrl(){
        return this.mUrl;
    }
}
