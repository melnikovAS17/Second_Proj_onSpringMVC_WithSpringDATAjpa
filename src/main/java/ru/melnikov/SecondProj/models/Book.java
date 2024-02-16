package ru.melnikov.SecondProj.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title")
    @NotEmpty(message = "Field shouldn't be empty")
    private String title;
    @Column(name = "author")
    @NotEmpty(message = "Field shouldn't be empty")
    @Size(min = 2, max = 70, message = "Author's name should be between 2 and 70 character")
    @Pattern(regexp = "[A-Z]\\w+ [A-z]\\w+", message = "Author's name should be correct: Mikhail Mikhalkov")
    private String author;
    @Column(name = "year")
    @NotEmpty(message = "Field shouldn't be empty")
    @Pattern(regexp = "\\d{4}", message = "Data should be correct: 1940")
    private int yearOfCreated;
    @ManyToOne
    @JoinColumn(name = "id_person", referencedColumnName = "id")
    private Person person;
    @Column(name = "appointed_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date appointedDate;

    //Поле проверки просрочки
    @Transient
    private boolean bookIsOverdue;

    public Book(int id, String title, String author, int yearOfCreated, Person person, Date appointedDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.yearOfCreated = yearOfCreated;
        this.person = person;
        this.appointedDate = appointedDate;
    }

    public Book(){}

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", yearOfCreated=" + yearOfCreated +
                ", person=" + person +
                ", appointedDate=" + appointedDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && yearOfCreated == book.yearOfCreated && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(person, book.person) && Objects.equals(appointedDate, book.appointedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, yearOfCreated, person, appointedDate);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearOfCreated() {
        return yearOfCreated;
    }

    public void setYearOfCreated(int yearOfCreated) {
        this.yearOfCreated = yearOfCreated;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Date getAppointedDate() {
        return appointedDate;
    }

    public void setAppointedDate(Date appointedDate) {
        this.appointedDate = appointedDate;
    }

    public void setBookIsOverdue(boolean bookIsOverdue){
        this.bookIsOverdue = bookIsOverdue;
    }
    public boolean getBookIsOverdue(){
        return bookIsOverdue;
    }
}
