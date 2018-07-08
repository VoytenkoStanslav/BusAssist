package by.app.admin.busassist.ConstClass;

/**
 * Created by Admin on 14.04.2018.
 */

public class BalanceMinus {

    private long id_minus;
    private Integer date_minus;
    private String category_minus;
    private Integer category_pos_minus;
    private Float amount_minus;
    private String commit_minus;

    public BalanceMinus() {
    }

    public BalanceMinus(Integer date_minus, String category_minus, Integer category_pos_minus,
                       Float amount_minus, String commit_minus) {
        this.date_minus = date_minus;
        this.category_minus = category_minus;
        this.category_pos_minus = category_pos_minus;
        this.amount_minus = amount_minus;
        this.commit_minus = commit_minus;
    }

    public long getId_minus(){
        return id_minus;
    }

    public void setId_minus(long id_minus) {
        this.id_minus = id_minus;
    }

    public Integer getDate_minus() {
        return date_minus;
    }

    public void setDate_minus(Integer date_minus) {
        this.date_minus = date_minus;
    }

    public String getCategory_minus() {
        return category_minus;
    }

    public void setCategory_minus(String category_minus) {
        this.category_minus = category_minus;
    }

    public Integer getCategory_pos_minus() {
        return category_pos_minus;
    }

    public void setCategory_pos_minus(Integer category_pos_minus) {
        this.category_pos_minus = category_pos_minus;
    }

    public Float getAmount_minus() {
        return amount_minus;
    }

    public void setAmount_minus(Float amount_minus) {
        this.amount_minus = amount_minus;
    }

    public String getCommit_minus() {
        return commit_minus;
    }

    public void setCommit_minus(String commit_minus) {
        this.commit_minus = commit_minus;
    }

}
