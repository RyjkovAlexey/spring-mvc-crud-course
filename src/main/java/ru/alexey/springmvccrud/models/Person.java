package ru.alexey.springmvccrud.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Column(name = "name")
    private String name;

    @Min(value = 0, message = "Age should be greater than 0")
    @Column(name = "age")
    private int age;

    @NotEmpty(message = "Email should be empty")
    @Email(message = "Email should be valid")
    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "owner")
    private List<Item> items;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOfBirth;

    @Column(name="created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Pattern(
            regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{6}",
            message = "Your address should be in this: Country, City, Postal Code (6 digits)"
    )
    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    private Mood mood;

    public Person() {

    }

    public Person(String name, int age, String email, String address) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && age == person.age && Objects.equals(name, person.name) && Objects.equals(email, person.email) && Objects.equals(items, person.items) && Objects.equals(dateOfBirth, person.dateOfBirth) && Objects.equals(createdAt, person.createdAt) && Objects.equals(address, person.address) && mood == person.mood;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, email, items, dateOfBirth, createdAt, address, mood);
    }
}
