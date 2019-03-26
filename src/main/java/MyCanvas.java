import model.MyColor;

import java.awt.*;

public class MyCanvas {

    private final int minx;
    private final int maxx;
    private final int miny;
    private final int maxy;
    private int width;
    private int height;
    private MyColor[][] image;

    public MyCanvas(int width, int height) {
        assert (width % 2 == 0);
        assert (height % 2 == 0);
        this.width = width;
        this.height = height;
        this.image = new MyColor[height][width];
        this.minx = -width / 2;
        this.maxx = width / 2;
        this.miny = -height / 2;
        this.maxy = height / 2;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                image[i][j] = new MyColor(0, 0, 0);
            }
        }
    }

    public void putPixel(int x, int y, MyColor color) {
        x += width / 2;
        y += height / 2;
        y = height - y;
        if (x >= width || y >= height || x < 0 || y < 0) {
            return;
        }
        image[y][x].fromColor(color);
    }

    public MyColor getPixel(int x, int y) {
        x += width / 2;
        y += height / 2;
        y = height - y;
        if (x >= width || y >= height || x < 0 || y < 0) {
            return null;
        }
        return image[y][x];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMinx() {
        return minx;
    }

    public int getMaxx() {
        return maxx;
    }

    public int getMiny() {
        return miny;
    }

    public int getMaxy() {
        return maxy;
    }

    public Color getColor(int x, int y) {
        return image[y][x].asJavaColor();
    }

}
