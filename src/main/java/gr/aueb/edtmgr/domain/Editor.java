package gr.aueb.edtmgr.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("EDITOR")
public class Editor extends User {

    public Editor() {
    }

    public Editor(String firstName, String lastName, String affiliation, String email) {
        super(firstName, lastName, affiliation, email);
    }
}
