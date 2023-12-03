package Application.Model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public abstract class Report {

    private LocalDate start;
    private LocalDate end;
    protected HashMap<String, Vector<Object>> reportData;


    abstract public void display() throws IOException;


    public HashMap<String, Vector<Object>> getReportData() {
        return reportData;
    }

    public void setReportData(HashMap<String, Vector<Object>> reportData) {
        this.reportData = reportData;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }


}
