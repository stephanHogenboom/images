package io.hogenboom.familyfoto.webcontroller.person;

import io.hogenboom.familyfoto.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/view-persons")
public class ViewPersonsController {
    @Autowired
    PersonRepository personRepository;

    @GetMapping("")
    public ModelAndView managePersonPage() {
        var persons = personRepository.findAll(Pageable.ofSize(100));

        var mav = new ModelAndView("person/view-persons");
        mav.addObject("persons", persons.stream().toList());

        return mav;
    }
}
