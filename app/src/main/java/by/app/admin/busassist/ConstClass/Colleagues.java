package by.app.admin.busassist.ConstClass;

/**
 * Created by Admin on 23.04.2018.
 */

public class Colleagues {

    public long id_colleag;
    public String image_uri;
    public String name_colleag;
    public String number_colleag;
    public String skype_colleag;
    public String commit_colleag;


    public Colleagues() {

    }

    public Colleagues(String image_uri, String name_colleag, String number_colleag,
                      String skype_colleag, String commit_colleag){
        this.image_uri = image_uri;
        this.name_colleag = name_colleag;
        this.number_colleag = number_colleag;
        this.skype_colleag = skype_colleag;
        this.commit_colleag = commit_colleag;
    }

    public long getId_colleag() {
        return id_colleag;
    }

    public void setId_colleag(long id_colleag) {
        this.id_colleag = id_colleag;
    }

    public String getImage_uri() {
        return image_uri;
    }

    public void setImage_uri(String image_uri) {
        this.image_uri = image_uri;
    }

    public String getName_colleag() {
        return name_colleag;
    }

    public void setName_colleag(String name_colleag) {
        this.name_colleag = name_colleag;
    }

    public String getNumber_colleag() {
        return number_colleag;
    }

    public void setNumber_colleag(String number_colleag) {
        this.number_colleag = number_colleag;
    }

    public String getSkype_colleag() {
        return skype_colleag;
    }

    public void setSkype_colleag(String skype_colleag) {
        this.skype_colleag = skype_colleag;
    }

    public String getCommit_colleag() {
        return commit_colleag;
    }

    public void setCommit_colleag(String commit_colleag) {
        this.commit_colleag = commit_colleag;
    }
}
