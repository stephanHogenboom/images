package io.hogenboom.familyfoto.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
@Entity
@Table(name = "named_group")
public class Group {
    @Id
    private UUID id;
    private String name;

    @ManyToMany(mappedBy = "groups")
    private Set<Image> images = new HashSet<>();

    public Group(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public Group() {

    }

    public UUID id() {
        return id;
    }

    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Group) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Group[" +
                "id=" + id + ", " +
                "name=" + name + ']';
    }

}
