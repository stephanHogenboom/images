package io.hogenboom.familyfoto.webcontroller.person;

import io.hogenboom.familyfoto.mapper.entity.PersonMapper;
import io.hogenboom.familyfoto.repository.PersonRepository;
import io.hogenboom.familyfoto.service.person.PersonService;
import io.hogenboom.familyfoto.webcontroller.MissingInformationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/manage-persons/")
public class ManagePersonController {

    @Autowired
    PersonRepository personRepository;
    @Autowired
    PersonService personService;

    @GetMapping("new")
    public ModelAndView createNewPerson() {
        var modelAndView = new ModelAndView("person/new-person");

        modelAndView.addObject("newPerson", new PersonView());

        return modelAndView;
    }
    @GetMapping("edit/{id}")
    public ModelAndView editPerson(@PathVariable("id") UUID id) {
        var person = personRepository.findById(id).orElseThrow();

        var personView = PersonMapper.toView(person);

        var mav = new ModelAndView("person/edit-person");
        mav.addObject("person", personView);

        return mav;
    }

    @PostMapping("edit/{id}")
    public ModelAndView saveEditedPerson(@PathVariable("id") UUID id, @ModelAttribute("person") PersonView personView) {
        var person = personRepository.findById(id).orElseThrow();

        person.setGender(personView.gender);
        person.setName(personView.name);
        person.setFamilyName(personView.familyName);
        person.setBirthDay(personView.birthDay);

        personRepository.save(person);

        return showPersonMainPage();
    }

    private ModelAndView showPersonMainPage() {
        var persons = personRepository.findAll(Pageable.ofSize(100));

        var mav = new ModelAndView("person/view-persons");
        mav.addObject("persons", persons.stream().toList());

        return mav;
    }

    @PostMapping("new")
    public ModelAndView addPerson(@ModelAttribute("newPerson") PersonView personView) {
        if (personView == null) {
            throw new MissingInformationException("newPerson");
        }
        if (personView.name == null) {
            throw new MissingInformationException("name");
        }
        if (personView.familyName == null) {
            throw new MissingInformationException("familyName");
        }
        if (personView.gender == null) {
            throw new MissingInformationException("gender");
        }
        if (personView.birthDay == null) {
            throw new MissingInformationException("birthDay");
        }

        var person = PersonMapper.viewToPerson(personView);
        personService.addPerson(person);

        return showPersonMainPage();
    }
}
