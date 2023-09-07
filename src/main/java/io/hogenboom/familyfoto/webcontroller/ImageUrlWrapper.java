package io.hogenboom.familyfoto.webcontroller;

import io.hogenboom.familyfoto.entity.Image;

public class ImageUrlWrapper {
    public Image image;
    public String imageUrl;
    public String specificImageUrl;
    public String patchNickNameFunction;

    public ImageUrlWrapper(Image image) {
        this.image = image;
        this.imageUrl = "/images/%s".formatted(image.id());
        this.specificImageUrl = "view-images/%s".formatted(image.id());
        this.patchNickNameFunction = "patchNickname(%s)".formatted(image.id());
    }
}
