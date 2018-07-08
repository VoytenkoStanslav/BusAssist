package by.app.admin.busassist.ConstClass;

/**
 * Created by Admin on 03.04.2018.
 */

public class Schedule {

    private long id;
    private Integer date_time;
    private String task;
    private String check_time;
    private String notific;
    private Integer notific_time;

    public Schedule() {
    }

    public Schedule(Integer date_time, String task, String check_time, String notific, Integer notific_time) {
        this.date_time = date_time;
        this.task = task;
        this.check_time = check_time;
        this.notific = notific;
        this.notific_time = notific_time;
    }

    public long getId() {
        return id;
    }

    public Integer getDate_Time(){
        return date_time;
    }

    public String getCheck_time(){
        return check_time;
    }

    public String getTask(){
        return task;
    }

    public String getNotific() {
        return notific;
    }

    public Integer getNotific_time() {
        return notific_time;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDate_Time(Integer date_time) {
        this.date_time = date_time;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setCheck_time(String check_time){
        this.check_time = check_time;
    }

    public void setNotific(String notific) {
        this.notific = notific;
    }

    public void setNotific_time(Integer notific_time) {
        this.notific_time = notific_time;
    }

}
