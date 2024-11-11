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
    private PersonalInfo personalInfo;

    public Author() {
    }

    public Author(String firstName, String lastName, String affiliation, String email) {
        this.personalInfo = new PersonalInfo(firstName, lastName, affiliation, email);
    }

    public String getEmail(){
        return personalInfo.getEmail();
    }

    public String getFirstName(){
        return personalInfo.getFirstName();
    }

    public String getLastName(){
        return personalInfo.getLastName();
    }

    public String getAffiliation(){
        return personalInfo.getAffiliation();
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
