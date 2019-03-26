package model;

public class Matrix {

    private double[][] mat;

    public Matrix(int rows, int cols) {
        mat = new double[rows][cols];
    }

    public Matrix(double[][] mat) {
        this.mat = mat;
    }

    Matrix dot(Matrix other) {
        assert (this.getCols() == other.getRows());
        Matrix result = new Matrix(this.getRows(), other.getCols());
        for (int r = 0; r < result.getRows(); r++) {
            for (int c = 0; c < result.getCols(); c++) {
                for (int k = 0; k < this.getCols(); k++) {
                    result.mat[r][c] += this.mat[r][k] * other.mat[k][c];
                }
            }
        }
        return result;
    }

    Matrix transpose() {
        Matrix result = new Matrix(this.getCols(), this.getRows());
        for (int r = 0; r < result.getRows(); r++) {
            for (int c = 0; c < result.getCols(); c++) {
                result.mat[r][c] = this.mat[c][r];
            }
        }
        return result;
    }

    public int getRows() {
        return mat.length;
    }

    public int getCols() {
        return mat[0].length;
    }

    public Vector toVec() {
        if (mat.length == 1 && mat[0].length == 3) {
            return new Vector(mat[0][0], mat[0][1], mat[0][2]);
        } else if (mat.length == 3 && mat[0].length == 1) {
            return new Vector(mat[0][0], mat[1][0], mat[2][0]);
        }
        throw new RuntimeException("dims doesn't match");
    }

    public static Matrix xRotationMatrix(double angle) {
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        double[][] m = {
                {1, 0, 0},
                {0, cos, -sin},
                {0, sin, cos}
        };
        return new Matrix(m);
    }

    public static Matrix yRotationMatrix(double angle) {
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        double[][] m = {
                {cos, 0, sin},
                {0, 1, 0},
                {-sin, 0, cos}
        };
        return new Matrix(m);
    }

    public static Matrix zRotationMatrix(double angle) {
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        double[][] m = {
                {cos, -sin, 0},
                {sin, cos, 0},
                {0, 0, 1}
        };
        return new Matrix(m);
    }

    public static Matrix fromVec(Vector v) {
        Matrix m = new Matrix(3, 1);
        m.mat[0][0] = v.x;
        m.mat[1][0] = v.y;
        m.mat[2][0] = v.z;
        return m;
    }


}
