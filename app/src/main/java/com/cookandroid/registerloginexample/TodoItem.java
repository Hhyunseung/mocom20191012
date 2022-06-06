package com.cookandroid.registerloginexample;

public class TodoItem
{
    private int id;
    private String title;
    private String content;
    private String spe;
    private String note;
    private String writeDate;

    public TodoItem()
    {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSpe() { return spe; }

    public void setSpe(String spe) { this.spe = spe; }

    public String getNote() { return note; }

    public void setNote(String note) { this.note = note; }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }
}

