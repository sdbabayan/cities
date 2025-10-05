package model;

import domain.interfaces.IntValueReturnable;

public class TestIntObject implements IntValueReturnable {
    private final int value;
    private final String name;

    public TestIntObject(int value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public int getIntValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestIntObject that = (TestIntObject) o;
        return value == that.value && name.equals(that.name);
    }

    @Override
    public String toString() {
        return name + "(" + value + ")";
    }
}
