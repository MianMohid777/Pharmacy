package Application.Model;

public abstract class Report {

    protected String contents;

    abstract public void display();

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
