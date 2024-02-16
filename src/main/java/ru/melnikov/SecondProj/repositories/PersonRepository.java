package ru.melnikov.SecondProj.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.melnikov.SecondProj.models.Person;
@Repository
public interface PersonRepository extends JpaRepository<Person,Integer> {
}
