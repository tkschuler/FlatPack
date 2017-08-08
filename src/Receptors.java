import java.util.ArrayList;
import java.util.List;

/**
 * Created by tristanschuler on 6/14/17.
 */
public class Receptors {

    //double squareToothSpacing;
    int numberOfTeeth;
    double sectionDistance;
    double distance;
    double plugThickness;
    double angle;
    double X1, X2, Y1, Y2;

    public Receptors(int numberOfTeeth, double distance, double plugThickness, double angle, double x1, double x2, double y1, double y2) {
        this.numberOfTeeth = numberOfTeeth;
        this.distance = distance;
        this.plugThickness = plugThickness;
        this.angle = angle;
        X1 = x1;
        X2 = x2;
        Y1 = y1;
        Y2 = y2;
    }

    /*
    This method determines the center points of teeth based off of the number of teeth given by the user
    and the distance between the two points of a Joint connecting Line.  These points can be rotated anyway in
    the XY Coordinate plane.
    */

    //This is the same as plug class
    public double[][] makeCenterPoints(){
        double[][] centerPoints = new double[numberOfTeeth][2];
        double numberOfSectionPoints = 2 * ((numberOfTeeth * 2) - 1);  //section points is double number of sections
        sectionDistance = distance / numberOfSectionPoints;
        double dt = sectionDistance;
        int n = 0;
        int currentSectionPoint = 1;
        while (dt < distance) {
            double t = dt / distance;
            double xPrime = (1 - t) * X1 + t * X2;
            double yPrime = (1 - t) * Y1 + t * Y2;
            centerPoints[n][0] = xPrime;
            centerPoints[n][1] = yPrime;
            n++;
            currentSectionPoint += 4;
            dt = sectionDistance * currentSectionPoint;
        }

        return centerPoints;
    }

    public List<List<double[]>> makeReceptorHoles() {

        /*
        Let the ratio of distances, t=dt/dt=dt/d
        Then the point (xt,yt)=(((1−t)x0+tx1),((1−t)y0+ty1))
        */

        //This code determines the center point of where holes need to be for Receptors
        /*

        First the the number of teeth and their midpoints are calculate. For a joint with 3 teeth
        there would be three holes where the grey blocks are.  Grey blocks represent a tooth on a plug joint. ex:

        Plugs:
         1  2  3  4  5
        ▇▇▇___▇▇▇___▇▇▇

        Holes:
        ▓▓▓	  ▓▓▓	▓▓▓


        One the Midpoints of the teeth are calculated, the 4 points of the rectangle are calculated by first plotting
        a rectangle with the correct dimensions, then rotating the points by the angle from the slope

         */
        double[][] centerPoints = makeCenterPoints();

        List<double[]> rectangle = new ArrayList();
        List<List<double[]>> rectangles = new ArrayList<>();

        for (int i = 0; i < centerPoints.length; i++) {

            Rectangle rect = new Rectangle(centerPoints[i], plugThickness, sectionDistance, angle);
            rect.createReceptorRectangleCorners(); //There is also a Plugs version
            rectangle = rect.createRotatedRectangleCorners();

            rectangles.add(rectangle);
        }
        return rectangles;
    }
}