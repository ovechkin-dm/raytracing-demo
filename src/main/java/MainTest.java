import model.RotationUtils;
import model.Vector;

public class MainTest {

    public static void main(String[] args) {
        Vector angle = RotationUtils.toAngle(new Vector(1, 1, 0));
        System.out.println(angle);
    }

}
