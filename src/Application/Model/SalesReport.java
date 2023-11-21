package Application.Model;

public class SalesReport extends Report{
    @Override
    public void display() {
        System.out.println(super.getContents());
    }
}
