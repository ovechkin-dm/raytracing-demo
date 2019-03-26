package model;

import java.util.ArrayList;
import java.util.List;

public class SceneObjects {

    public static List<Sphere> spheres = new ArrayList<Sphere>();

    public static List<Light> lights = new ArrayList<Light>();

    static {
        Sphere s1 = new Sphere(new Vector(0, -1, 3), 1, new MyColor(255, 0, 0), 50, 0.2);
        Sphere s2 = new Sphere(new Vector(2, 0, 4), 1, new MyColor(0, 0, 255), 100, 0.3);
        Sphere s3 = new Sphere(new Vector(-2, 0, 4), 1, new MyColor(0, 255, 0), 200, 0.4);
        Sphere s4 = new Sphere(new Vector(0, -5001, 0), 5000, new MyColor(255, 255, 0), 1000, 0.5);
        spheres.add(s1);
        spheres.add(s2);
        spheres.add(s3);
        spheres.add(s4);
    }

    static {
        Light ambient = new Light("ambient", 0.2, new Vector(0, 0, 0), new Vector(0, 0, 0));
        Light point = new Light("point", 0.9, new Vector(-2, 2, 0), new Vector(0, 0, 0));
        Light directional = new Light("directional", 0.2, new Vector(0, 0, 0), new Vector(1, 4, 4));
        lights.add(ambient);
        lights.add(point);
        lights.add(directional);
    }

}
