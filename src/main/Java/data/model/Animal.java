package data.model;

import data.repository.ArrayListToSortByStrategy;

import java.util.Scanner;

public class Animal {

    private String kind;
    private EyesColor eyesColor;
    private Boolean isWoolen;

    public String getKind() {
        return kind;
    }

    public EyesColor getEyesColor() {
        return eyesColor;
    }

    public boolean getIsWoolen() {
        return isWoolen;
    }

    public static ArrayListToSortByStrategy<Animal> loadDataFromFile (String path) {
        return null;
    }

    public static ArrayListToSortByStrategy<Animal> loadRandomData (int qty) {
        return null;
    }

    public static ArrayListToSortByStrategy<Animal> loadDataManually () {
        return null;
    }

    public static Animal createObjectManually(Scanner scanner) {
        return null;
    }

    public enum EyesColor {
        BLUE,
        GREEN,
        BROWN,
        YELLOW
    };
}

