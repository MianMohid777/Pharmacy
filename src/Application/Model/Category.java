package Application.Model;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.time.LocalDateTime;
import java.util.*;

public class Category implements Component {

    private String code;
    private String name;
    private String desc;

    private LocalDateTime timeStamp;

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

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
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
        String code = name.substring(0,2);
        code = code + name.hashCode();
        this.code = code;
        return code;
    }

    public void add(Component c){
        componentsMap.put(c.findCode(),c);
    }

   public void remove(Component c)
   {
       componentsMap.remove(c.findCode());
   }


}
