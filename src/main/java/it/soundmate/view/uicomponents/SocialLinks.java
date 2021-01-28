/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 27/01/21, 17:50
 * Last edited: 27/01/21, 17:50
 */

package it.soundmate.view.uicomponents;

import javafx.scene.image.Image;

import java.util.Arrays;

public enum SocialLinks {

    SPOTIFY("Spotify", new Image("soundmate/icons/spotify-icon.png")),
    YOUTUBE("YouTube", new Image("soundmate/icons/youtube-icon.png")),
    FACEBOOK("Facebook", new Image("soundmate/icons/facebook-icon.png"));

    private final String name;
    private final Image source;
    private String link;

    SocialLinks(String name, Image source) {
        this.name = name;
        this.source = source;
    }

    public static SocialLinks returnSocialLink(String social) {
        SocialLinks[] socialLinks = SocialLinks.values();
        return Arrays.stream(socialLinks)
                .filter(currentSocial -> currentSocial.toString().equals(social))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("SocialLink not found:"));
    }

    public String getName() {
        return name;
    }

    public Image getSource() {
        return source;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
