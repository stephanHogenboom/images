package io.hogenboom.familyfoto.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "person")
public class Person {
    @Id
    private UUID id;
    private  String name;

    private String familyName;
    private LocalDate birthDay;

    private String gender;

    public  Person() {

    }

    public Person(UUID id, String name, String familyName, String gender, LocalDate birthDay) {
        this.id = id;
        this.name = name;
        this.familyName = familyName;
        this.birthDay = birthDay;
        this.gender = gender;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    @ManyToMany(mappedBy = "persons")
    private Set<Image> images = new HashSet<>();

    public UUID id() {
        return id;
    }

    public String name() {
        return name;
    }

    public LocalDate birthDay() {
        return birthDay;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Person) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.birthDay, that.birthDay);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFamilyName() {
        return familyName;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthDay);
    }

    @Override
    public String toString() {
        return "Person[" +
                "id=" + id + ", " +
                "name=" + name + ", " +
                "birthDay=" + birthDay + ']';
    }

    public Set<Image> getImages() {
        return images;
    }
}
