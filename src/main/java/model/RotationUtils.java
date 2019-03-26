package model;

public class RotationUtils {

    public static Vector rotate(Vector source, Matrix rotationMatrix) {
        return rotationMatrix.dot(Matrix.fromVec(source)).toVec();
    }

    public static Matrix createRotationMatrix(double xAngle, double yAngle, double zAngle) {
        Matrix r = Matrix.xRotationMatrix(xAngle);
        Matrix p = Matrix.yRotationMatrix(yAngle);
        Matrix y = Matrix.zRotationMatrix(zAngle);
        Matrix result = y.dot(p.dot(r));
        return result;
    }

    public static Vector toAngle(Vector source) {
        Vector v = source.normalize();
        double pitch = Math.asin(-v.y);
        double yaw = Math.atan2(v.x, v.z);
        Vector result = new Vector(0, 0, 0);
        result.x = pitch;
        result.y = yaw;
        return result;
    }


}
