package Application.Model;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.*;

public class Category implements Component {

    private String code;
    private String name;
    private String desc;

    private HashMap<String,Component> componentsMap;

    public Category() {
        code = "";
        name = "";
        desc = "";
        componentsMap = new HashMap<>();
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public HashMap<String, Component> getComponentsMap() {
        return componentsMap;
    }

    public void setComponentsMap(HashMap<String, Component> componentsMap) {
        this.componentsMap = componentsMap;
    }

    public String getCode() {
        return code;
    }

    @Override
    public void display() {

        Set<String> keys = componentsMap.keySet();

        for(String s : keys)
        {
            componentsMap.get(s).display();
        }


    }

    @Override
    public String findCode()
    {
        return String.valueOf(this.name.hashCode());
    }

    public void add(Component c){
        componentsMap.put(c.findCode(),c);
    }

   public void remove(Component c)
   {
       componentsMap.remove(c.findCode());
   }


}
