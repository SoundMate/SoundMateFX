package it.soundmate.model;

import java.util.Arrays;

public enum Genres {

    ROCK, POP, BLUES, JAZZ, METAL, HIPHOP, ELECTRONIC, INDIE, FOLK, FUNKY, FUSION;

    public static Genres returnGenre(String genre){
        Genres[] genres = Genres.values();
        return Arrays.stream(genres)
                .filter(currentGenre -> currentGenre.toString().equals(genre))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Genre not found:"));
    }

}
