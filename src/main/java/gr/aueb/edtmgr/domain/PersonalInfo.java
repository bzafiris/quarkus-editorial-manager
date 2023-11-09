package gr.aueb.edtmgr.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class PersonalInfo {

    public PersonalInfo() {
    }

    public PersonalInfo(String firstName, String lastName, String affiliation, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.affiliation = affiliation;
        this.email = email;
    }

    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Column(name = "affiliation", length = 200, nullable = false)
    private String affiliation;

    @Column(name = "email", length = 50, nullable = false)
    private String email;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonalInfo that = (PersonalInfo) o;
        return firstName.equals(that.firstName) && lastName.equals(that.lastName) && affiliation.equals(that.affiliation) && email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, affiliation, email);
    }
}
