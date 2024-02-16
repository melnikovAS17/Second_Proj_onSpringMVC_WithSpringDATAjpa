package ru.melnikov.SecondProj.models;

import org.hibernate.annotations.Fetch;
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
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    @NotEmpty(message = "Field shouldn't be empty")
    @Size(min = 2, message = "The name must be more than 1 letter.")
    private String name;
    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @NotEmpty(message = "Field shouldn't be empty")
    @Pattern(regexp = "^(0[1-9]|1\\d|2\\d|3[01])\\.(0[1-9]|1[0-2])\\.\\d{4}$", message = "Date of birth should be correct: dd.mm.year 12.12.2012")
    private Date dateOfBirth;
    @Column(name = "email")
    @NotEmpty(message = "Field shouldn't be empty")
    @Email(message = "Email should be correct: aaa@gmail. ")
    private String email;
    @OneToMany(mappedBy = "person")
    private List<Book> bookList;

    public Person(int id, String name, Date dateOfBirth, String email, List<Book> bookList) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.bookList = bookList;
    }

    public Person(){}

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", email='" + email + '\'' +
                ", bookList=" + bookList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && Objects.equals(name, person.name) && Objects.equals(dateOfBirth, person.dateOfBirth) && Objects.equals(email, person.email) && Objects.equals(bookList, person.bookList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dateOfBirth, email, bookList);
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}
