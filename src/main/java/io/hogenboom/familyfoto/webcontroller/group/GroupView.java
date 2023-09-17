package io.hogenboom.familyfoto.webcontroller.group;

import java.util.UUID;

public class GroupView {
    String name;
    UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
