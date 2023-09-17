package io.hogenboom.familyfoto.rest;

import io.hogenboom.familyfoto.mapper.entity.PersonMapper;
import io.hogenboom.familyfoto.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/persons")
public class PersonController {
    @Autowired
    PersonRepository personRepository;

    @PostMapping("")
    public void addPerson(@RequestBody AddPersonRequest request) {
        var person = PersonMapper.viewToPerson(request);
        personRepository.save(person);
    }
}
