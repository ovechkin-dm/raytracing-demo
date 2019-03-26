package model;

public class Sphere {

    private final double reflective;
    Vector position;
    double radius;
    MyColor color;
    double specular;

    public Sphere(Vector position, double radius, MyColor color, double specular, double reflective) {
        this.position = position;
        this.radius = radius;
        this.color = color;
        this.specular = specular;
        this.reflective = reflective;
    }

    public Vector getPosition() {
        return position;
    }

    public double getRadius() {
        return radius;
    }

    public MyColor getColor() {
        return color;
    }

    public double getSpecular() {
        return specular;
    }

    public double getReflective() {
        return reflective;
    }
}
