package io.hogenboom.familyfoto.webcontroller.image;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

public class ImageView {
    UUID id;
    String nickName;
    List<UUID> groupIds;
    List<UUID> personIds;
    Path imagePath;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    public void setGroupIds(List<UUID> groupIds) {
        this.groupIds = groupIds;
    }

    public List<UUID> getGroupIds() {
        return groupIds;
    }

    public List<UUID> getPersonIds() {
        return personIds;
    }

    public void setPersonIds(List<UUID> uuids) {
        this.personIds = uuids;
    }

    public Path getImagePath() {
        return imagePath;
    }

    public void setImagePath(Path imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "ImageView{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", groupIds=" + groupIds +
                ", personIds=" + personIds +
                ", imagePath=" + imagePath +
                '}';
    }
}
