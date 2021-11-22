package iftm.pedro.aproject.entities.utils;

public enum Status {

    ORDERED ("PEDIDO REALIZADO", "COR"),
    PACKAGED ("ENCOMENDA FEITA", "COR"),
    APPROVED ("ENCOMENDA APROVADA", "COR"),
    DELIVERED ("ENCOMENDA ENTREGUE", "COR");

    private final String name;
    private final String color;

    Status(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
}
