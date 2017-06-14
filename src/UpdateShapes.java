import java.util.ArrayList;
import java.util.List;

//Right now this Class only handles creating squaretooth joints, for both plugs and receptors, depending on the String Type
//that is passed in. The class will return updated joint coordinates;
class UpdateShapes {

    private String jointType;
    private int numberOfTeeth;
    private double plugThickness;  //This is the plugs board thickness, determines width of receptor holes
    public double[][] connectingLine;

    //Updated coordinates  (Should I make this Array or List?)
    double distance = 0;
    double jointSpacing = 0;
    double angle; //in radians
    double sectionDistance;

    double X1, X2, Y1, Y2;

    public UpdateShapes(String jointType, int numberOfTeeth, double plugThickness, double[][] connectingLine) {
        this.jointType = jointType;
        this.numberOfTeeth = numberOfTeeth;
        this.plugThickness = plugThickness;
        this.connectingLine = connectingLine;

        //Helper Coordinates from the connectingLine
        X1 = connectingLine[0][0];
        Y1 = connectingLine[0][1];
        X2 = connectingLine[1][0];
        Y2 = connectingLine[1][1];

        if (X2 == X1)
            angle = 0;
        else
            angle = Math.atan((Y2 - Y1) / (X2 - X1)); //+ 1.5708;
    }


    public void squareToothSpacing() {
        //Distance formula

        distance = Math.sqrt(Math.pow((X2 - X1), 2) + Math.pow((Y2 - Y1), 2));

        //Each Squaretooth joint has [(n*2)-1] sections to the joint
        double jointSpacing = distance / ((numberOfTeeth * 2) - 1);
    }


    //this method returns a rectangle in the correct orientation around a center point
    public void angleOfConnectingLine() {
        if (X2 == X1)
            angle = 0;
        else
            angle = Math.atan((Y2 - Y1) / (X2 - X1));
    }

    public List<List<double[]>> makeReceptorHoles() {
        squareToothSpacing();

        //array of rectangle centers for the teeth
        double[][] centerPoints = new double[numberOfTeeth][2];
        /*
        Let the ratio of distances, t=dt/dt=dt/d
        Then the point (xt,yt)=(((1−t)x0+tx1),((1−t)y0+ty1))
        */

        //This code determines the center point of where holes need to be for Receptors
        /*

        First the the number of teeth and their midpoints are calculate. For a joint with 3 teeth
        there would be three holes where the grey blocks are.  Grey blocks represent a tooth on a plug joint. ex:

        Plug:
         1  2  3  4  5
        ▇▇▇___▇▇▇___▇▇▇

        Holes:
        ▓▓▓	  ▓▓▓	▓▓▓


        One the Midpoints of the teeth are calculated, the 4 points of the rectangle are calculated by first plotting
        a rectangle with the correct dimensions, then rotating the points by the angle from the slope

         */
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

        //return centerPoints;

        List<double[]> rectangle = new ArrayList();
        List<List<double[]>> rectangles = new ArrayList<>();

        //[[0,0],[0,0],[0,0],[0,0]]

        for (int i = 0; i < centerPoints.length; i++) {

            Rectangle rect = new Rectangle(centerPoints[i], plugThickness, sectionDistance, angle);
            rect.createRectangleCorners();
            rectangle = rect.createRotatedRectangleCorners();

            rectangles.add(rectangle);
        }

        return rectangles;
    }

    public static void main(String[] args) {
        double[][] connectingLine = new double[][]{{1, 2}, {5, 7}};
        UpdateShapes joint1 = new UpdateShapes("hi", 3, 30.0, connectingLine);
        List<List<double[]>> returnedRectangles = joint1.makeReceptorHoles();
        System.out.println("WAZZZZZUP");



    }
}
// double[][] connectingLine = new double[][]{{1,2},{5,7}}Line = new double[][]{{1,2},{5,7}}