package model;

import java.awt.*;

public class MyColor {

    public double r;
    public double g;
    public double b;
    private MutableJavaColor javaColor = new MutableJavaColor((int) r, (int) g, (int) b);

    public MyColor(double r, double g, double b) {
        set(r, g, b);
    }

    public MyColor copy() {
        return new MyColor(r, g, b);
    }

    public Color asJavaColor() {
        return javaColor;
    }

    public void fromColor(MyColor c) {
        set(c.r, c.g, c.b);
    }

    public void set(double r, double g, double b) {
        this.r = Math.min(r, 255);
        this.g = Math.min(g, 255);
        this.b = Math.min(b, 255);
        javaColor.set((int) this.r, (int) this.g, (int) this.b, 255);
    }

    public void mul(double x) {
        set(r * x, g * x, b * x);
    }

}
