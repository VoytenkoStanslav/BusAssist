package by.app.admin.busassist.ConstClass;

/**
 * Created by Admin on 11.05.2018.
 */

public class Notific {

    public long id_notific;
    public String name_notific;

    public Notific(){

    }

    public Notific(String name_notific){

        this.name_notific = name_notific;

    }

    public long getId_notific() {
        return id_notific;
    }

    public void setId_notific(long id_notific) {
        this.id_notific = id_notific;
    }

    public String getName_notific() {
        return name_notific;
    }

    public void setName_notific(String name_notific) {
        this.name_notific = name_notific;
    }
}
