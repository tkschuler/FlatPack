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

    public List<List<double[]>> updateReceptors() {
        squareToothSpacing();
        Receptor r = new Receptor(numberOfTeeth, sectionDistance, distance, plugThickness, angle, X1, X2, Y1, Y2);
        List<List<double[]>> rectangles = r.makeReceptorHoles();

        return rectangles;
    }

    public static void main(String[] args) {
        double[][] connectingLine = new double[][]{{1, 2}, {5, 7}};
        UpdateShapes joint1 = new UpdateShapes("hi", 3, 30.0, connectingLine);
        List<List<double[]>> returnedRectangles = joint1.updateReceptors();
        System.out.println("WAZZZZZUP");



    }
}
// double[][] connectingLine = new double[][]{{1,2},{5,7}}Line = new double[][]{{1,2},{5,7}}