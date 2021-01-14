package it.soundmate.model;

import java.util.Arrays;

public enum Genre {

    ROCK, POP, BLUES, JAZZ, METAL, HIPHOP, ELECTRONIC, INDIE, FOLK, FUNKY, FUSION;

    public static Genre returnGenre(String genre){
        Genre[] genres = Genre.values();
        return Arrays.stream(genres)
                .filter(currentGenre -> currentGenre.toString().equals(genre))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Genre not found:"));
    }

}
