import model.Light;
import model.SceneObjects;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class Starter extends JPanel {

    MyCanvas canvas = new MyCanvas(Constants.SC_W, Constants.SC_H);
    Scene scene = new Scene();
    double direction = 1;

    private void moveLight() {
        Light light = SceneObjects.lights.get(1);
        light.getPosition().x += 0.05 * direction;
        if (light.getPosition().x > 20) {
            direction = -1;
        }
        if (light.getPosition().x < -20) {
            direction = 1;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        scene.draw(canvas, SceneObjects.spheres, SceneObjects.lights);
        for (int x = 0; x < Constants.SC_W; x++) {
            for (int y = 0; y < Constants.SC_H; y++) {
                Color c = canvas.getColor(x, y);

                g2d.setColor(c);
                g2d.fillRect((int)(x * Constants.scale), (int) (y * Constants.scale),
                        (int)(Constants.scale) + 1, (int)(Constants.scale) + 1);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("Scene");
        Starter game = new Starter();
        game.setDoubleBuffered(true);
        frame.add(game);
        frame.setSize((int) (Constants.SC_W * Constants.scale + 1), (int) (Constants.SC_H * Constants.scale + 1));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        while (true) {
            game.moveLight();
            game.repaint();
            Thread.sleep(100);
        }
    }
}
