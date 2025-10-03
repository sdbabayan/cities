package data.model;

import data.repository.ArrayListToSortByStrategy;

import java.util.Scanner;

public class Person {
    private Sex sex;
    private int age;
    private String surname;

    public Sex getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public String getSurname() {
        return surname;
    }

    public static ArrayListToSortByStrategy<Person> loadDataFromFile (String path) {
        return null;
    }

    public static ArrayListToSortByStrategy<Person> loadRandomData (int qty) {
        return null;
    }

    public static ArrayListToSortByStrategy<Person> loadDataManually () {
        return null;
    }

    public static Person createObjectManually(Scanner scanner) {
        return null;
    }

    public enum Sex {
      М,
      Ж
    };
}
