package cz.cvut.kbss.ear.eshop.model;

import javax.persistence.*;


@Entity
public class User extends AbstractEntity{
    @Basic(optional = false)
    @Column(nullable = false)
    private String firstName;
    @Basic(optional = false)
    @Column(nullable = false)
    private String lastName;
    @Basic(optional = false)
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "owner")
    private Cart cart;

    public User(){}

    public User(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
