import model.*;

import java.util.List;

public class Scene {

    Vector origin = new Vector(3, 3, 10);
    Vector viewPortParams = new Vector(1, 1, 1);
    MyColor background = new MyColor(0, 0, 0);
    Vector camViewcenter = new Vector(0, -1, 3);
    double camPos = 0;

    public void draw(MyCanvas canvas, List<Sphere> spheres, List<Light> lights) {
        camPos += 0.1;
        Vector camVec = getCameraVec();
        Vector src = camViewcenter.minus(camVec);
        Vector angle = RotationUtils.toAngle(src);
        Matrix rotationMatrix = RotationUtils.createRotationMatrix(angle.x, angle.y, angle.z);
        for (int x = canvas.getMinx(); x <= canvas.getMaxx(); x++) {
            for (int y = canvas.getMiny(); y <= canvas.getMaxy(); y++) {
                Vector d = canvasToViewPort(x, y);
                d = RotationUtils.rotate(d, rotationMatrix);
                MyColor color = canvas.getPixel(x, y);
                if (color == null) {
                    continue;
                }
                traceRay(camVec, d, 1.0, 1000000000, spheres, lights, color, 1);
            }
        }
    }

    public Vector getCameraVec() {
        double x = Math.sin(camPos) * 7.0 + camViewcenter.x;
        double z = Math.cos(camPos) * 7.0 + camViewcenter.z;
        double y = 4;
        return new Vector(x, y, z);
    }

    public void traceRay(Vector o, Vector d, double tMin, double tMax, List<Sphere> spheres,
                         List<Light> lights, MyColor color, int depth) {
        IntersectionResult inter = closestIntersection(o, d, tMin, tMax, spheres);
        if (inter.closestSphere == null) {
            color.fromColor(background);
        } else {
            Vector p = o.plus(d.mul(inter.closestValue));
            Vector n = p.minus(inter.closestSphere.getPosition());
            n.normalize();
            double ref = inter.closestSphere.getReflective();
            color.fromColor(inter.closestSphere.getColor());
            if (depth > 0 && ref > 0) {
                MyColor local = inter.closestSphere.getColor();
                Vector r = reflectRay(d.mul(-1), n);
                traceRay(p, r, 0.001, 100000000, spheres, lights, color, depth - 1);
                double red = local.r * (1 - ref) + color.r * ref;
                double green = local.g * (1 - ref) + color.g * ref;
                double blue = local.b * (1 - ref) + color.b * ref;
                color.set(red, green, blue);
                color.mul(computeLight(p, n, p.mul(-1.0), lights, spheres, inter.closestSphere.getSpecular()));
            } else {
                color.mul(computeLight(p, n, p.mul(-1.0), lights, spheres, inter.closestSphere.getSpecular()));
            }
        }
    }

    public double computeLight(Vector p, Vector n, Vector v, List<Light> lights, List<Sphere> spheres, double specular) {
        double i = 0.0;
        for (int li = 0; li < lights.size(); li++) {
            Light light = lights.get(li);
            if (light.getType().equals("ambient")) {
                i += light.getIntensity();
            } else {
                Vector l = Vector.create(0, 0, 0);
                double tmax = 0.0;
                if (light.getType().equals("point")) {
                    l = light.getPosition().minus(p);
                    tmax = 1.0;
                } else if (light.getType().equals("directional")) {
                    l = light.getDirection();
                    tmax = 10000000000.0;
                }
                //shadows
                IntersectionResult inter = closestIntersection(p, l, 0.00001, tmax, spheres);
                if (inter.closestSphere != null) {
                    continue;
                }
                //diffuse
                double ndl = n.dot(l);
                if (ndl > 0) {
                    i += light.getIntensity() * ndl / (n.norm() * l.norm());
                }
                //specular
                if (specular != -1) {
                    Vector r = n.mul(2.0).mul(n.dot(l)).minus(l);
                    double cos = r.dot(v) / (r.norm() * v.norm());
                    if (cos > 0) {
                        i += light.getIntensity() * Math.pow(cos, specular);
                    }
                }
            }
        }
        return i;
    }

    public IntersectionResult closestIntersection(Vector o,
                                                  Vector d,
                                                  double tMin,
                                                  double tMax,
                                                  List<Sphere> spheres) {
        double closest = Integer.MAX_VALUE;
        IntersectionResult closestResult = IntersectionResult.get(0, 0, false, null, 0.0);
        for (int si = 0; si < spheres.size(); si++) {
            Sphere s = spheres.get(si);
            IntersectionResult inter = intersectRaySphere(o, d, s);
            if (!inter.hasRoots()) {
                continue;
            }
            double t1 = inter.x1;
            double t2 = inter.x2;
            if (t1 >= tMin && t1 <= tMax && t1 < closest) {
                closest = t1;
                closestResult = inter;
            }
            if (t2 >= tMin && t2 <= tMax && t2 < closest) {
                closest = t2;
                closestResult = inter;
            }
        }
        closestResult.closestValue = closest;
        return closestResult;
    }

    public IntersectionResult intersectRaySphere(Vector o, Vector d, Sphere sphere) {
        Vector oc = o.minus(sphere.getPosition());
        double b = 2.0 * d.dot(oc);
        double a = d.dot(d);
        double c = oc.dot(oc) - sphere.getRadius() * sphere.getRadius();
        double disc = b * b - 4 * a * c;
        if (disc < 0) {
            return IntersectionResult.get(0, 0, false, sphere, 0.0);
        } else {
            double r1 = (-b + Math.sqrt(disc)) / (2.0 * a);
            double r2 = (-b - Math.sqrt(disc)) / (2.0 * a);
            return IntersectionResult.get(r1, r2, true, sphere, 0.0);
        }
    }

    Vector canvasToViewPort(int x, int y) {
        double rx = (double) x * viewPortParams.x / Constants.SC_W;
        double ry = (double) y * viewPortParams.y / Constants.SC_H;
        double rz = viewPortParams.z;
        Vector v = Vector.create(rx, ry, rz);
        return v;
    }

    Vector reflectRay(Vector r, Vector n) {
        return n.mul(2 * r.dot(n)).minus(r);
    }

}
