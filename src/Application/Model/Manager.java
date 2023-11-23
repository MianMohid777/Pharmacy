package Application.Model;

public class Manager extends Role{

    public void permissions()
    {
        super.log = !log;
    }
}
