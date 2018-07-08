package by.app.admin.busassist.ConstClass;

/**
 * Created by Admin on 07.05.2018.
 */

public class Capital {

    public long id_start_capital;
    public Float sum_start_capital;

    public Capital(){

    }

    public Capital(Float sum_start_capital){
        this.sum_start_capital = sum_start_capital;
    }

    public long getId_start_capital() {
        return id_start_capital;
    }

    public void setId_start_capital(long id_start_capital) {
        this.id_start_capital = id_start_capital;
    }

    public Float getSum_start_capital() {
        return sum_start_capital;
    }

    public void setSum_start_capital(Float sum_start_capital) {
        this.sum_start_capital = sum_start_capital;
    }
}
