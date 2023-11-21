package Application.Model;

public class InventoryReport extends Report{
    @Override
    public void display() {
        System.out.println(super.getContents());
    }
}
