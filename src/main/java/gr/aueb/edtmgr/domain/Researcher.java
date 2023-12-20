package gr.aueb.edtmgr.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("RESEARCHER")
public class Researcher extends User {

    public Researcher() {
        super();
    }

    public Researcher(String firstName, String lastName, String affiliation, String email) {
        super(firstName, lastName, affiliation, email);
    }

    public void setFirstName(String firstName){
        this.personalInfo.setFirstName(firstName);
    }

    public String getFirstName(){
        return this.personalInfo.getFirstName();
    }

    public String getLastName(){
        return this.personalInfo.getLastName();
    }

    public String getAffiliation(){
        return this.personalInfo.getAffiliation();
    }

    public String getEmail(){
        return this.personalInfo.getEmail();
    }

    public void setLastName(String lastName){
        this.personalInfo.setLastName(lastName);
    }

    public void setAffiliation(String affiliation){
        this.personalInfo.setAffiliation(affiliation);
    }
    public void setEmail(String email){
        this.personalInfo.setEmail(email);
    }
    public void setId(Integer id){
        this.id = id;
    }


}
