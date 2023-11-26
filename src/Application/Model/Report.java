package Application.Model;

import java.util.HashMap;
import java.util.Vector;

public abstract class Report {

    protected HashMap<String, Vector<Object>> reportData;


    abstract public void display();


    public HashMap<String, Vector<Object>> getReportData() {
        return reportData;
    }

    public void setReportData(HashMap<String, Vector<Object>> reportData) {
        this.reportData = reportData;
    }
}
