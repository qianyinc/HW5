package edu.cmu.mis.iccfb.model;
import edu.cmu.mis.iccfb.model.Author;


public class Quote {

    private Long id;
    private String text;
    private String source;
    private Long authorId;
    private Author author;


    public Quote() {}

    public Quote(String text, String source, Author author) {
        this.text = text;
        this.source = source;
        this.author = author;
    }

    @Override
    public String toString() {

        return String.format("Quote[id=%d, text='%s', by='%s']", this.id, this.text, this.author.getName());
    }


    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }



    public Long getId() {
        return id;
    }
    
    
}