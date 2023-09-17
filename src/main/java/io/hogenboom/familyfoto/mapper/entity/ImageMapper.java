package io.hogenboom.familyfoto.mapper.entity;

import io.hogenboom.familyfoto.entity.Group;
import io.hogenboom.familyfoto.entity.Image;
import io.hogenboom.familyfoto.entity.Person;
import io.hogenboom.familyfoto.webcontroller.group.GroupView;
import io.hogenboom.familyfoto.webcontroller.image.ImageView;
import io.hogenboom.familyfoto.webcontroller.person.PersonView;

import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ImageMapper {

   public static ImageView toImageview(Image source) {
        var view = new ImageView();

        view.setId(source.getId());
        view.setNickName(source.getNickName());
        view.setImagePath(Paths.get(source.getPath()));
        view.setPersonViews(toPersonIds(source.getPersons()));
        view.setGroupIds(toGroupIds(source.getGroups()));
        view.setId(source.getId());

        return view;
    }
    private static List<UUID> toGroupIds(Set<Group> groups) {
        return groups.stream().map(Group::id).toList();
    }
    private static List<UUID> toPersonIds(Set<Person> people) {
        return people.stream().map(Person::id).toList();
    }
}
