package gr.aueb.edtmgr.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "authors")
public class Author {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Embedded
    private PersonalInfo personalInfo = new PersonalInfo();

    public Author() {
    }

    public Author(String firstName, String lastName, String affiliation, String email) {
        this.personalInfo = new PersonalInfo(firstName, lastName, affiliation, email);
    }

    public Integer getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author that = (Author) o;
        return getFirstName().equals(that.getFirstName()) && getLastName().equals(that.getLastName())
                && getAffiliation().equals(that.getAffiliation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getAffiliation());
    }
}
