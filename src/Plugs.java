import java.util.ArrayList;
import java.util.List;

public class Plugs {

    //double squareToothSpacing;
    int numberOfTeeth;
    double sectionDistance;
    double distance;
    double plugThickness;
    double angle;
    double X1, X2, Y1, Y2;
    String extrusionDirection;

    public Plugs(int numberOfTeeth, double sectionDistance, double distance, double plugThickness, double angle, double x1, double x2, double y1, double y2, String extrusionDirection) {
        this.numberOfTeeth = numberOfTeeth;
        this.sectionDistance = sectionDistance;
        this.distance = distance;
        this.plugThickness = plugThickness;
        this.angle = angle;
        this.extrusionDirection = extrusionDirection;
        X1 = x1;
        X2 = x2;
        Y1 = y1;
        Y2 = y2;
    }

    //Counterclockwise orientation.  Everything to the right of a line path is outside of the shape.  Holes SHOULD
    //follow a clockwise orientation.

        /*
    This method determines the center points of teeth based off of the number of teeth given by the user
    and the distance between the two points of a Joint connecting Line.  These points can be rotated anyway in
    the XY Coordinate plane.
    */

    //Same as Receptors Class
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

    //View Documentation from Receptors Class on how this function compliments making receptors.
    public List<List<double[]>> makePlugsForReceptors(){
        double[][] centerPoints = makeCenterPoints();

        List<double[]> rectangle = new ArrayList();
        List<List<double[]>> rectangles = new ArrayList<>();

        for (int i = 0; i < centerPoints.length; i++) {

            Rectangle rect = new Rectangle(centerPoints[i], plugThickness, sectionDistance, angle, extrusionDirection);
            rect.createPlugShiftedRectangle(extrusionDirection); //There is also a Receptors version
            rectangle = rect.createRotatedRectangleCorners();

            rectangles.add(rectangle);
        }

        return rectangles;

    }

}