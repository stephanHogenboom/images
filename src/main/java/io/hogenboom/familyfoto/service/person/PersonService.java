package io.hogenboom.familyfoto.service.person;

import io.hogenboom.familyfoto.entity.Person;
import io.hogenboom.familyfoto.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    PersonRepository repository;

    public void addPerson(Person person) {
        repository.save(person);
    }
}
