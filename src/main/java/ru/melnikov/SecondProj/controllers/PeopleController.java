package ru.melnikov.SecondProj.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.melnikov.SecondProj.models.Book;
import ru.melnikov.SecondProj.models.Person;
import ru.melnikov.SecondProj.services.PersonService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PeopleController {

    private final PersonService personService;

    @Autowired
    public PeopleController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/people")
    public String showAllPerson(Model model){
        model.addAttribute("personList",personService.showAllPerson());
        return "people/showAllPeople";
    }

    @GetMapping("/people/{id}")
    public String showPersonInfo(@PathVariable("id") int id, Model model){
        model.addAttribute("person",personService.showPerson(id));
        if(personService.bookListCheck(id).isEmpty())
            model.addAttribute("emptyList","The reader did not take any book");
        else
            model.addAttribute("bookList", personService.bookListCheck(id));
        return "people/showPersonInfo";
    }
    @GetMapping("/people/new")
    public String getFormForCreateNewPerson(Model model){
        model.addAttribute("person",new Person());
        return "people/formForNewPerson";
    }

    @PostMapping("/people")
    public String createNewPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        if(bindingResult.hasErrors())return "people/formForNewPerson";
        personService.savePerson(person);
        return "redirect:/people";
    }

    @GetMapping("/people/{id}/edit")
    public String getFormForEditPerson(@PathVariable("id") int id, Model model){
        model.addAttribute("person",personService.showPerson(id));
        return "people/formForEditPerson";
    }

    @PatchMapping("/people/{id}")
    public String editPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                             @PathVariable("id") int id){
        if(bindingResult.hasErrors()) return "people/formForEditPerson";
        personService.updatePerson(id,person);
        return "redirect:/people/" + id;
    }

    @DeleteMapping("/people/{id}")
    public String deletePerson(@PathVariable("id") int id){
        personService.deletePerson(id);
        return "redirect:/people";
    }

}

