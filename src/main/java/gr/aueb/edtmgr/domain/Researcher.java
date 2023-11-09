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

    protected void setId(Integer id){
        this.id = id;
    }


}
