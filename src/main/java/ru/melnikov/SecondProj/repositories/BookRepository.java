package ru.melnikov.SecondProj.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.melnikov.SecondProj.models.Book;
import ru.melnikov.SecondProj.models.Person;

import java.util.Date;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
    List<Book> findByTitleStartingWith(String title);
    @Modifying
    @Query("update Book b set b.person = :person, b.appointedDate = :timestamp where b.id = :id")
    void getBookByIdAndSetPerson(@Param("id") int id, @Param("person") Person person, @Param("timestamp")Date date);

}
