package project.sarah.mobile_lab_2;

/**
 * Created by sarah on 11/17/17.
 */

public class Books {

    private String bTitle;
    private String bAuthor;
    private String bContext;

    public Books(String bTitle, String bAuthor, String bContext){
        this.bTitle=bTitle;
        this.bAuthor=bAuthor;
        this.bContext=bContext;
    }

    public String getbTitle(){
        return this.bTitle;
    }

    public String getbAuthor(){
        return this.bAuthor;
    }

    public String getbContext(){
        return this.bContext;
    }
}
