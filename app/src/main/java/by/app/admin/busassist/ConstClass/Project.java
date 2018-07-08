package by.app.admin.busassist.ConstClass;

/**
 * Created by Admin on 02.05.2018.
 */

public class Project {

    public long id_project;
    public String name_project;
    public String type_project;
    public Integer type_pos_project;
    public String company_project;
    public String responsible_for_project;
    public Integer date_finish_project;


    public Project(){

    }

    public Project(String type_project, Integer type_pos_project, String name_project, String company_project,
                   String responsible_for_project, Integer date_finish_project){

        this.type_project = type_project;
        this.type_pos_project = type_pos_project;
        this.name_project = name_project;
        this.company_project = company_project;
        this.responsible_for_project = responsible_for_project;
        this.date_finish_project = date_finish_project;

    }

    public long getId_project() {
        return id_project;
    }

    public void setId_project(long id_project) {
        this.id_project = id_project;
    }

    public String getType_project() {
        return type_project;
    }

    public void setType_project(String type_project) {
        this.type_project = type_project;
    }

    public Integer getType_pos_project() {
        return type_pos_project;
    }

    public void setType_pos_project(Integer type_pos_project) {
        this.type_pos_project = type_pos_project;
    }

    public String getName_project() {
        return name_project;
    }

    public void setName_project(String name_project) {
        this.name_project = name_project;
    }

    public String getCompany_project() {
        return company_project;
    }

    public void setCompany_project(String company_project) {
        this.company_project = company_project;
    }

    public String getResponsible_for_project() {
        return responsible_for_project;
    }

    public void setResponsible_for_project(String responsible_for_project) {
        this.responsible_for_project = responsible_for_project;
    }

    public Integer getDate_finish_project() {
        return date_finish_project;
    }

    public void setDate_finish_project(Integer date_finish_project) {
        this.date_finish_project = date_finish_project;
    }
}
