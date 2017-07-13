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
    double toothDistance;

    double[] direction;
    //These are the perpindicular vectors for determining where plug points go.
    double[] normalOut = new double[2];
    double[] normalIn = new double[2];

    public Plugs(int numberOfTeeth, double distance, double plugThickness, double angle, double x1, double x2, double y1, double y2, double[] direction) {
        this.numberOfTeeth = numberOfTeeth;
        this.sectionDistance = sectionDistance;
        this.distance = distance;
        this.plugThickness = plugThickness;
        this.angle = angle;
        this.direction = direction;
        X1 = x1;
        X2 = x2;
        Y1 = y1;
        Y2 = y2;

        //IN:  (-dy,dx)       CounterClockwise 90 degrees
        //OUT: (dy,-dx)       Clockwise 90 degrees
        normalIn[0] = this.direction[1]*-1;
        normalIn[1] = this.direction[0];

        normalOut[0]= this.direction[1];
        normalOut[1]= this.direction[0]*-1;
    }

    //Counterclockwise orientation.  Everything to the right of a line path is outside of the shape.  Holes SHOULD
    //follow a clockwise orientation.

        /*
    This method determines the center points of teeth based off of the number of teeth given by the user
    and the distance between the two points of a Joint connecting Line.  These points can be rotated anyway in
    the XY Coordinate plane.
    */

    //Same as Receptors Class

    //View Documentation from Receptors Class on how this function compliments making receptors.
    public List<double[]> makeTeeth(){
        List<double[]> plugCoordinates = new ArrayList<>();
        double[] point = new double[]{X1,Y1};

        plugCoordinates.add(new double[]{X1,Y1});
        toothDistance = distance/((numberOfTeeth*2)-1);


        for (int i=0; i<numberOfTeeth; i++) { //Loop for teeth
            for (int j=0; j<3; j++){          //Loop for one tooth (This doesn't have to be a loop)
                if (j == 0) {
                    double[] tempPoint = new double[2];

                    tempPoint[0] = point[0] + normalIn[0] * plugThickness;
                    tempPoint[1] = point[1] + normalIn[1] * plugThickness;

                    plugCoordinates.add(tempPoint);

                    point[0] = tempPoint[0];
                    point[1] = tempPoint[1];
                }
                if (j == 1) {
                    double[] tempPoint = new double[2];

                    tempPoint[0] = point[0] + direction[0] * toothDistance;
                    tempPoint[1] = point[1] + direction[1] * toothDistance;

                    plugCoordinates.add(tempPoint);

                    point[0] = tempPoint[0];
                    point[1] = tempPoint[1];

                }
                if (j == 2) {
                    double[] tempPoint = new double[2];

                    tempPoint[0] = point[0] + normalOut[0] * plugThickness;
                    tempPoint[1] = point[1] + normalOut[1] * plugThickness;

                    plugCoordinates.add(tempPoint);

                    point[0] = tempPoint[0];
                    point[1] = tempPoint[1];

                }
            }
            //Move over plug distance if not final point
            if (i != numberOfTeeth-1){
                double[] tempPoint = new double[2];

                tempPoint[0] = point[0] + direction[0] * toothDistance;
                tempPoint[1] = point[1] + direction[1] * toothDistance;

                plugCoordinates.add(tempPoint);

                point[0] = tempPoint[0];
                point[1] = tempPoint[1];
            }
        }

        return plugCoordinates;

    }
}