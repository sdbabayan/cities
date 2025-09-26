package examples;

import java.io.*;
import java.util.*;

public class Animal {
    public enum colours {BROWN, GREEN, BLUE, GRAY};

    final private String kind;
    final private boolean isWoolen;
    final private colours eyesColour;

    private Animal(Builder builder){
        this.kind = builder.kind;
        this.eyesColour = builder.eyesColour;
        this.isWoolen = builder.isWoolen;
    }

    public String getKind() { return kind; }
    public boolean getIsWoolen() { return isWoolen; }
    public colours getEyesColour() { return eyesColour; }

    public static class Builder {
        private String kind;
        private boolean isWoolen;
        private colours eyesColour;

        public Builder kind(String kind){
            this.kind = kind;
            return this;
        }
        public Builder isWoolen (boolean isWoolen){
            this.isWoolen = isWoolen;
            return this;
        }
        public Builder eyesColour(colours eyesColour){
            this.eyesColour = eyesColour;
            return this;
        }
        public Animal build() { return new Animal(this); }
    }

    public String toString() {
        return String.format("Вид животного: %s; цвет глаз: %s; наличие шерсти: %s", kind, eyesColour, isWoolen);
    }
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Animal)) return false;
        Animal animal = (Animal) o;
        return isWoolen == animal.isWoolen && Objects.equals(kind, animal.kind) && eyesColour == animal.eyesColour;
    }
    public int hashCode() {
        return Objects.hash(kind, eyesColour, isWoolen);
    }
}

class Test {
    public static void main(String[] args){
        Animal test = new Animal.Builder().kind("Dog").eyesColour(Animal.colours.BLUE).isWoolen(true).build();
        System.out.println(test);
    }
}

