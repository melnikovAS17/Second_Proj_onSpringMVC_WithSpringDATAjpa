package ru.melnikov.SecondProj.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.melnikov.SecondProj.models.Book;
import ru.melnikov.SecondProj.models.Person;
import ru.melnikov.SecondProj.repositories.PersonRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepository personRepository;
    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> showAllPerson(){
        return personRepository.findAll();
    }
    public Person showPerson(int id){
        // Метод getOne() возвращает лишь первичный атрибут ключа - id, чтобы получить
        Optional<Person> person = personRepository.findById(id);
        return person.orElse(null);
    }
    @Transactional
    public void savePerson(Person person){
        personRepository.save(person);
    }
    @Transactional
    public void updatePerson(int id, Person personToUpdate){
       personToUpdate.setId(id);
       personRepository.save(personToUpdate);
    }
    @Transactional
    public void deletePerson(int id){
        personRepository.deleteById(id);
    }

    //Method check on BookList - present or not
    //Взял ли читатель книги или ещё нет
    public List<Book> bookListCheck(int id){
        Hibernate.initialize(showPerson(id).getBookList());
        Date currentDate = new Date();
        //Проверка на просрочку
        showPerson(id).getBookList().forEach(b-> {
            if((currentDate.getTime() - b.getAppointedDate().getTime()) > 864000000){
                b.setBookIsOverdue(true);
            }else {
                b.setBookIsOverdue(false);
            }
        });
        return showPerson(id).getBookList();
    }

}
