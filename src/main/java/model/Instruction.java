package model;

public enum Instruction {

    G("Gauche"),
    D("Droite"),
    A("Avance");

    Instruction(String name) {
        this.name = name;
    }

    private final String name;

    public String getName() {
        return name;
    }
}
