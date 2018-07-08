package by.app.admin.busassist.ConstClass;

/**
 * Created by Admin on 25.04.2018.
 */

public class Client {

    public long id_client;
    public String image_path_client;
    public String type_client;
    public Integer type_pos_client;
    public String company_client;
    public String name_client;
    public String number_client;
    public String skype_client;
    public String commit_client;
    public String project_client;

    public Client(){

    }

    public Client(String image_path_client, String type_client, Integer type_pos_client,
                  String company_client, String name_client, String number_client,
                  String skype_client, String commit_client, String project_client){
        this.image_path_client = image_path_client;
        this.type_client = type_client;
        this.type_pos_client = type_pos_client;
        this.company_client = company_client;
        this.name_client = name_client;
        this.number_client = number_client;
        this.skype_client = skype_client;
        this.commit_client = commit_client;
        this.project_client = project_client;
    }

    public long getId_client() {
        return id_client;
    }

    public void setId_client(long id_client) {
        this.id_client = id_client;
    }

    public String getImage_path_client() {
        return image_path_client;
    }

    public void setImage_path_client(String image_path_client) {
        this.image_path_client = image_path_client;
    }

    public String getType_client() {
        return type_client;
    }

    public void setType_client(String type_client) {
        this.type_client = type_client;
    }

    public Integer getType_pos_client() {
        return type_pos_client;
    }

    public void setType_pos_client(Integer type_pos_client) {
        this.type_pos_client = type_pos_client;
    }

    public void setCompany_client(String company_client) {
        this.company_client = company_client;
    }

    public String getCompany_client() {
        return company_client;
    }

    public String getName_client() {
        return name_client;
    }

    public void setName_client(String name_client) {
        this.name_client = name_client;
    }

    public String getNumber_client() {
        return number_client;
    }

    public void setNumber_client(String number_client) {
        this.number_client = number_client;
    }

    public String getSkype_client() {
        return skype_client;
    }

    public void setSkype_client(String skype_client) {
        this.skype_client = skype_client;
    }

    public String getCommit_client() {
        return commit_client;
    }

    public void setCommit_client(String commit_client) {
        this.commit_client = commit_client;
    }

    public String getProject_client() {
        return project_client;
    }

    public void setProject_client(String project_client) {
        this.project_client = project_client;
    }
}
