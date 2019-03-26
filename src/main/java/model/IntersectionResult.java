package model;

public class IntersectionResult {

    final public static IntersectionResult[] intersectPool = new IntersectionResult[1000];
    public static int poolPos = 0;

    static {
        for (int i = 0; i < intersectPool.length; i++) {
            intersectPool[i] = new IntersectionResult(0, 0, false, null, 0);
        }
    }

    public double x1;
    public double x2;
    public boolean roots;
    public Sphere closestSphere;
    public double closestValue;

    private IntersectionResult(double x1, double x2, boolean roots,
                               Sphere closest, double closestValue) {
        this.x1 = x1;
        this.x2 = x2;
        this.roots = roots;
        this.closestSphere = closest;
        this.closestValue = closestValue;
    }

    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }

    public boolean hasRoots() {
        return roots;
    }

    public static IntersectionResult get(double x1, double x2, boolean roots,
                                         Sphere closest, double closestValue) {
        poolPos = (poolPos + 1) % intersectPool.length;
        IntersectionResult res = intersectPool[poolPos];
        res.x1 = x1;
        res.x2 = x2;
        res.roots = roots;
        res.closestSphere = closest;
        res.closestValue = closestValue;
        return res;
    }

}
