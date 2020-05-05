package fr.ulille.iut.agile.client_android;

public class Calculator {

    private Calculator() {
        throw new IllegalStateException("Do not use this constructor");
    }

    public static int add(int left, int right) {
        return left + right;
    }
}
