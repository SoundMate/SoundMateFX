package it.soundmate.view.uicomponents;

import javafx.scene.image.Image;

public enum InstrumentGraphics {
    GUITAR("Guitar", new Image("soundmate/icons/instruments/electric-guitar.png")),
    DRUM("Drums", new Image("soundmate/icons/instruments/drum-set.png")),
    BASS("Bass", new Image("soundmate/icons/instruments/bass.png")),
    KEYBOARD("Keyboard", new Image("soundmate/icons/instruments/organ.png")),
    MIC("Microphone", new Image("soundmate/icons/instruments/microphone.png"));

    private final String name;
    private final Image source;

    InstrumentGraphics(String name, Image source) {
        this.name = name;
        this.source = source;
    }

    public Image getSource() {
        return source;
    }

    public String getName() {
        return name;
    }
}
