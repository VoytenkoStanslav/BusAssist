package by.app.admin.busassist.ConstClass;

/**
 * Created by Admin on 14.04.2018.
 */

public class BalancePlus {

    private long id_plus;
    private Integer date_plus;
    private String category_plus;
    private Integer category_pos_plus;
    private Float amount_plus;
    private String commit_plus;

    public BalancePlus() {
    }

    public BalancePlus(Integer date_plus, String category_plus, Integer category_pos_plus,
                       Float amount_plus, String commit_plus) {
        this.date_plus = date_plus;
        this.category_plus = category_plus;
        this.category_pos_plus = category_pos_plus;
        this.amount_plus = amount_plus;
        this.commit_plus = commit_plus;
    }

    public long getId_plus(){
        return id_plus;
    }

    public void setId_plus(long id_plus) {
        this.id_plus = id_plus;
    }

    public Integer getDate_plus() {
        return date_plus;
    }

    public void setDate_plus(Integer date_plus) {
        this.date_plus = date_plus;
    }

    public String getCategory_plus() {
        return category_plus;
    }

    public void setCategory_plus(String category_plus) {
        this.category_plus = category_plus;
    }

    public Integer getCategory_pos_plus() {
        return category_pos_plus;
    }

    public void setCategory_pos_plus(Integer category_pos_plus) {
        this.category_pos_plus = category_pos_plus;
    }

    public Float getAmount_plus() {
        return amount_plus;
    }

    public void setAmount_plus(Float amount_plus) {
        this.amount_plus = amount_plus;
    }

    public String getCommit_plus() {
        return commit_plus;
    }

    public void setCommit_plus(String commit_plus) {
        this.commit_plus = commit_plus;
    }
}
