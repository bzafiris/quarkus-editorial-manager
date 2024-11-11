package gr.aueb.edtmgr.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id;

    @Embedded
    protected PersonalInfo personalInfo;

    public User() {
    }

    public User(String firstName, String lastName, String affiliation, String email) {
        this.personalInfo = new PersonalInfo(firstName, lastName, affiliation, email);
    }

    public Integer getId() {
        return id;
    }

    public String getEmail(){
        return this.personalInfo.getEmail();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
