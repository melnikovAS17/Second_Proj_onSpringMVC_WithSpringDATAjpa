package ru.melnikov.SecondProj.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.melnikov.SecondProj.models.Book;
import ru.melnikov.SecondProj.models.Person;
import ru.melnikov.SecondProj.services.BookService;
import ru.melnikov.SecondProj.services.PersonService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class BooksController {

    private final BookService bookService;
    private final PersonService personService;
    @Autowired
    public BooksController(BookService bookService, PersonService personService) {
        this.bookService = bookService;
        this.personService = personService;
    }

    @GetMapping("/books")
    public String showAllBooks(Model model,@RequestParam(name = "page", required = false) Integer page,
                               @RequestParam(name = "books_per_page", required = false) Integer amountBook,
                               @RequestParam(name = "sort_by_year", required = false) boolean sort) {
        if(page == null || amountBook == null)
             model.addAttribute("bookList",bookService.showAllBooks(sort));
        else
             model.addAttribute("bookPageOut",bookService.pageableOut(page,amountBook,sort));
        return "books/showAllBooks";
    }
    @GetMapping("/books/pageable")
    public String getFormForPageableOut(){
        return "books/formForPageableOut";
    }

    @GetMapping("/books/{id}")
    public String showBookInfo(@PathVariable("id") int id, Model model,
                               @ModelAttribute("person") Person person){
        model.addAttribute("book",bookService.showBook(id));
        if(bookService.checkBookBelong(id) != null)
            model.addAttribute("getPerson",bookService.checkBookBelong(id).getName());
        else
            model.addAttribute("people",personService.showAllPerson());
        return "books/showBookInfo";
    }

    @GetMapping("/books/new")
    public String getFormForCreateNewBook(Model model){
        model.addAttribute("book", new Book());
        return "books/formForNewBook";
    }

    @PostMapping("/books")
    public String createNewBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "books/formForNewBook";
        bookService.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/books/{id}/edit")
    public String getFormForEditBook(@PathVariable("id") int id, Model model){
        model.addAttribute("book",bookService.showBook(id));
        return "books/formForEditBook";
    }

    @PatchMapping("/books/{id}")
    public String editBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                           @PathVariable("id") int id){
        if (bindingResult.hasErrors()) return "books/formForEditBook";
        bookService.updateBook(id,book);
        return "redirect:/books/"+id;
    }

    @DeleteMapping("/books/{id}")
    public String deleteBook(@PathVariable("id") int id){
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    //Methods for management book's readers
    @PatchMapping("/books/{id}/assign")
    public String assignBookToPerson(@PathVariable("id") int id, @ModelAttribute("person")Person person){
        bookService.assignBook(id,person);
        return "redirect:/books/" + id;
    }
    @PatchMapping("/books/{id}/cancel")
    public String cancelBookToPerson(@PathVariable("id") int id){
        bookService.cancelBook(id);
        return "redirect:/books/" + id;
    }

    //Methods for searching
    @GetMapping("/books/search")//Этот метод нужен только для перехода на форму
    public String formForSearch(){
        return "books/formForSearch";
    }

    @PostMapping("/books/form_search")
    public String resultPage(@RequestParam(value = "title", required = false) String title, Model model){
        List<Book> searchingResult = bookService.searchBook(title);
        //Проверка: если список пустой соотв книга или книги не были найдены
        if(searchingResult.isEmpty())model.addAttribute("emptyList","Nothing was found");
        model.addAttribute("bookList",searchingResult);
        return "books/resultSearch";
    }

}
