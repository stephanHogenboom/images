package io.hogenboom.familyfoto.mapper.entity;

import io.hogenboom.familyfoto.entity.Person;
import io.hogenboom.familyfoto.rest.AddPersonRequest;
import io.hogenboom.familyfoto.webcontroller.person.PersonView;

import java.util.UUID;

public class PersonMapper {

    public static Person viewToPerson(AddPersonRequest source) {
        var person = new Person();

        person.setId(UUID.randomUUID());
        person.setName(source.name());
        person.setFamilyName(source.familyName());
        person.setBirthDay(source.birthday());
        person.setGender(source.gender());

        return person;
    }

    public static Person viewToPerson(PersonView source) {
        var person = new Person();

        person.setId(UUID.randomUUID());
        person.setName(source.getName());
        person.setFamilyName(source.getFamilyName());
        person.setBirthDay(source.getBirthDay());
        person.setGender(source.getGender());

        return person;
    }

   public static PersonView toView(Person source) {
        var personView = new PersonView();

        personView.setBirthDay(source.getBirthDay());
        personView.setGender(source.getGender());
        personView.setName(source.getName());
        personView.setFamilyName(source.getFamilyName());
        personView.setId(source.getId());

        return personView;
    }
}
