package cs309.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "description")
    private String description;

//    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "current_city")
    private String currentCity;

    //TODO tstack 2/4/16 implement saving of any further location information
//    private Long cityXCoordinate;
//    private Long cityYCoordinate;
    @OneToMany(mappedBy = "userInvited")
    @JsonIgnore
    private List<EventInvite> events;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<ConnectionGroup> connectionGroups = new ArrayList<ConnectionGroup>();

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public List<EventInvite> getEvents() {
        return events;
    }

    public void setEvents(List<EventInvite> events) {
        this.events = events;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ConnectionGroup> getConnectionGroups() {
        return connectionGroups;
    }

    public void setConnectionGroups(List<ConnectionGroup> connectionGroups) {
        this.connectionGroups = connectionGroups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return new EqualsBuilder()
                .append(firstName, user.firstName)
                .append(lastName, user.lastName)
                .append(email, user.email)
                .append(description, user.description)
                .append(currentCity, user.currentCity)
                .append(events, user.events)
                .append(connectionGroups, user.connectionGroups)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(firstName)
                .append(lastName)
                .append(email)
                .append(description)
                .append(currentCity)
                .append(events)
                .append(connectionGroups)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                ", currentCity='" + currentCity + '\'' +
                ", events=" + events +
                ", connectionGroups=" + connectionGroups +
                '}';
    }
}