package ru.melnikov.SecondProj.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.melnikov.SecondProj.models.Book;
import ru.melnikov.SecondProj.models.Person;
import ru.melnikov.SecondProj.repositories.BookRepository;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> showAllBooks(boolean sort){
        if(sort)return bookRepository.findAll(Sort.by("yearOfCreated"));
        return bookRepository.findAll();
    }
    //Метод добавляет пагинацию и сортировку, с возможностью использовать отдельно одно от другого и вместе(пагинация + сортировка)
    //Sort.by(поле класса Book)
    public List<Book> pageableOut(Integer page, Integer amountBooks, boolean sort) {
        if(sort)
            return bookRepository.findAll(PageRequest
                    .of(page, amountBooks, Sort.by("yearOfCreated"))).getContent();
        else
            return bookRepository.findAll(PageRequest.of(page,amountBooks)).getContent();
    }

    public Book showBook(int id){
        Optional<Book> book = bookRepository.findById(id);
        return book.orElse(null);
    }
    @Transactional
    public void saveBook(Book book){
        bookRepository.save(book);
    }
    @Transactional
    public void updateBook(int id, Book bookToUpdate){
        bookToUpdate.setId(id);
        //Чтобы не терялась связь
        bookToUpdate.setPerson(bookToUpdate.getPerson());
        //метод save нужен тк книга не находится в Persistence context
        bookRepository.save(bookToUpdate);
    }
    @Transactional
    public void deleteBook(int id){
        bookRepository.deleteById(id);
    }


    //Methods for management book's readers
    @Transactional
    public void assignBook(int idBook, Person person){
        //Кастомный запрос
        //У автора :
        /*bookRepository.findById(idBook).isPresent(
                book ->{
                    book.setPerson(person);
                    book.setAppointedDate(new Date());
                }
        );*/
        bookRepository.getBookByIdAndSetPerson(idBook, person,new Date());
    }

    @Transactional
    public void cancelBook(int idBook){
        //timestamp - уст-м в null (appointed_date = null)
        //Использую свой кастомный запрос
        bookRepository.getBookByIdAndSetPerson(idBook,null,null);
    }

    public Person checkBookBelong(int idBook){
        Book book = showBook(idBook);
        return book.getPerson();
    }

    //Method for searching
    public List<Book> searchBook(String stringOfTitle){
        return bookRepository.findByTitleStartingWith(stringOfTitle);

    }
}
