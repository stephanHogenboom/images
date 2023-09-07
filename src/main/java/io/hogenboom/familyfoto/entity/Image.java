package io.hogenboom.familyfoto.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = " image")
public final class Image {
    @Id
    private UUID id;
    private String name;
    private String nickName;

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                ", path='" + path + '\'' +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    private String path;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    private int year;
    private int month;
    private int day;

    public Image() {

    }
    public Image(UUID id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }

    public UUID id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String path() {
        return path;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Image) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, path);
    }

}
