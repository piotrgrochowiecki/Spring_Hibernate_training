package com.piotrgrochowiecki.bookstore.controller;

import com.piotrgrochowiecki.bookstore.dao.PersonDao;
import com.piotrgrochowiecki.bookstore.dao.PersonDetailsDao;
import com.piotrgrochowiecki.bookstore.model.Person;
import com.piotrgrochowiecki.bookstore.model.PersonDetails;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@AllArgsConstructor
@RequestMapping("/person")
public class PersonController {

    private PersonDao personDao;
    private PersonDetailsDao personDetailsDao;

    @GetMapping("add/{login}/{password}/{email}/{firstName}/{lastName}/{streetNumber}/{street}/{city}")
    @ResponseBody
    public String add(@PathVariable String login,
                      @PathVariable String password,
                      @PathVariable String email,
                      @PathVariable String firstName,
                      @PathVariable String lastName,
                      @PathVariable String streetNumber,
                      @PathVariable String street,
                      @PathVariable String city){
        Person person = new Person();
        person.setLogin(login);
        person.setPassword(password);
        person.setEmail(email);

        PersonDetails personDetails = new PersonDetails();
        personDetails.setFirstName(firstName);
        personDetails.setLastName(lastName);
        personDetails.setStreetNumber(streetNumber);
        personDetails.setStreet(street);
        personDetails.setCity(city);
        personDetailsDao.save(personDetails);

        person.setPersonDetails(personDetails);

        personDao.save(person);

        return "Id of added person is " + person.getId();
    }

    @GetMapping("/show/{id}")
    @ResponseBody
    public String show(@PathVariable long id) {
        return personDao.findById(id).toString();
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public String delete(@PathVariable long id) {
        Person person = personDao.findById(id);
        personDao.delete(person);
        return "Person with id " + id + " has been deleted";
    }

    @GetMapping("updatePassword/{id}/{password}")
    @ResponseBody
    public String updateTitle(@PathVariable long id, @PathVariable String password) {
        Person person = personDao.findById(id);
        person.setPassword(password);
        personDao.update(person);
        return "Password of person with id " + id + " has been updated";
    }
}