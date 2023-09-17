package io.hogenboom.familyfoto.mapper.entity;

import io.hogenboom.familyfoto.entity.Group;
import io.hogenboom.familyfoto.webcontroller.group.GroupView;

import java.util.UUID;

public class GroupMapper {
    public static Group viewToGroup(GroupView source) {
        var group = new Group();

        group.setId(UUID.randomUUID());
        group.setName(source.getName());

        return group;
    }

    public static GroupView toView(Group source) {
        var groupView = new GroupView();

        groupView.setName(source.name());
        groupView.setId(source.id());

        return groupView;
    }
}
