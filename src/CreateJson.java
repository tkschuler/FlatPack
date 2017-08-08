import com.google.gson.Gson;  //Needed for dealing with JSON data
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

class Furniture{ //Main object
    String furnitureName;
    List<Board> boards;
    List<Joint> joints;

    public Furniture(String furnitureName, List<Board> boards, List<Joint> joints) {
        this.furnitureName = furnitureName;
        this.boards = boards;
        this.joints = joints;
    }

    public List<double[]> searchBoards(List<Board> boards, String boardName){  //returns List of Coordinates of board with matching name
        for(Board b : boards){
            if(b.getBoardName() != null && b.getBoardName().contains(boardName)) {
                return b.getCoordinates();
            }
        }
            return null;
    }

    public List<Board> getBoards() {
        return boards;
    }

    public List<Joint> getJoints() {
        return joints;
    }

    public String getFurnitureName() {
        return furnitureName;
    }

    //determineJointType() returns either edge or Not edge. The method checks for coincidence between a connecting line
    //and every line on a board's main coordinates.  If the lines are ever coincident, than it is an edge joint.
    public void determineJointType(){
        for (Joint j: joints){
            List<double[]> b1coords = new ArrayList<>();
            List<double[]> b2coords = new ArrayList<>();

            for (Board b : boards) {
                if (j.getPlugName().equals(b.getBoardName())) {
                    b1coords = b.getCoordinates();
                }
                if (j.getReceptorName().equals(b.getBoardName())) {
                    b2coords = b.getCoordinates();
                }
            }

            boolean b1coincidence = false;
            boolean b2coincidence = false;

            for (int i = 0; i< b1coords.size(); i++){
                if (i == 0){
                    b1coincidence = checkCoincidence(b1coords.get(b1coords.size()-1), b1coords.get(i), j.plugConnectingLine[0], j.plugConnectingLine[1]);
                }
                else {
                    b1coincidence = checkCoincidence(b1coords.get(i-1), b1coords.get(i), j.plugConnectingLine[0], j.plugConnectingLine[1]);
                }

                if (b1coincidence == true)
                    break;

            }

            for (int i = 0; i< b2coords.size(); i++){
                if (i == 0){
                    b2coincidence = checkCoincidence(b2coords.get(b2coords.size()-1), b2coords.get(i), j.receptorConnectingLine[0], j.receptorConnectingLine[1]);
                }
                else {
                    b2coincidence = checkCoincidence(b2coords.get(i-1), b2coords.get(i), j.receptorConnectingLine[0], j.receptorConnectingLine[1]);
                }

                if (b2coincidence == true)
                    break;

            }

            if (b1coincidence == true && b2coincidence == true){
                j.setJointCategory("Edge");
            }
            else
                j.setJointCategory("notEdge");
        }
    }

    //checkCoincidence() checks if two lines are coincident. x & y-coordinates are needed for each start and end point.
    //First slopes are checked to see if they are equal.  If they are equal, then the connecting line is checked to make sure it is
    //equal to in length, or within the min and max points of a line on a board's main coordinates.
    public boolean checkCoincidence(double[] boardPoint1, double[] boardPoint2, double[] connectingLine1, double[] connectingLine2) {

        double angle1 = Math.abs(Math.atan((boardPoint2[1] - boardPoint1[1]) / (boardPoint2[0] - boardPoint1[0])));
        double angle2 = Math.abs(Math.atan((connectingLine2[1] - connectingLine1[1]) / (connectingLine2[0] - connectingLine1[0])));

        if (angle1 != angle2)
            return false;

        //min and max must be determined because of negative slopes.
        double maxX;
        double minX;
        double maxY;
        double minY;

        if (boardPoint1[0] > boardPoint2[0]) {
            maxX = boardPoint1[0];
            minX = boardPoint2[0];
        } else {
            maxX = boardPoint2[0];
            minX = boardPoint1[0];
        }

        if (boardPoint1[1] > boardPoint2[1]) {
            maxY = boardPoint1[1];
            minY = boardPoint2[1];
        } else {
            maxY = boardPoint2[1];
            minY = boardPoint1[1];
        }

        if (connectingLine1[0] >= minX && connectingLine1[0] <= maxX && connectingLine1[1] >= minY && connectingLine1[1] <= maxY && connectingLine2[0] >= minX && connectingLine2[0] <= maxX && connectingLine2[1] >= minY && connectingLine2[1] <= maxY)
            return true;

        return false;
    }
}

class Board{
    private String boardName;
    private double thickness;
    //Coordinates will be a list of list of coordinates, to allow addition of holes.
    //i.e. { { [0,0],[0,50],[50,50],[50,0] } , { [10,10],[10,20] } , { [10,40],[10,30] } }
    private List<double[]> mainCoordinates;
    List<List<double[]>> holes = new ArrayList<>();


    public Board(String boardName, double thickness, List<double[]> coordinates) {
        this.boardName = boardName;
        this.thickness = thickness;
        this.mainCoordinates = coordinates;
    }

    public String getBoardName() {
        return boardName;
    }

    public List<double[]> getCoordinates() {
        return mainCoordinates;
    }

    public List<List<double[]>> getHoles() {
        return holes;
    }

    public void setHoles(List<List<double[]>> newHoles) {

        if (holes == null)
            holes = newHoles;
        else {
            for (List<double[]> l : newHoles) {
                holes.add(l);
            }
        }
    }

    public void setPlugCoordinates(List<double[]> plugCoordinates){
        int counter = 0;
        //first find starting point of joing in main shape

        for (double[] p : mainCoordinates) {
            if (p[0] == plugCoordinates.get(0)[0] && p[1] == plugCoordinates.get(0)[1]) {
                mainCoordinates.remove(counter);
                break;
            }
            counter++;
        }

        //This removes duplicate points
        //plugCoordinates.remove(plugCoordinates.size()-1);
        //Next add plug coordinates to main shape and remove starting coordinate of connecting line.  (otherwise there will be a line
        //going through the base of the plugs, since SVG automatically connects the first and last points)
        for (double[] point : plugCoordinates) {
            mainCoordinates.add(counter, point);
            counter++;
        }
    }
}

class Joint{
    String plugName;
    String receptorName;
    String jointCategory;

    double[][] plugConnectingLine;
    double[][] receptorConnectingLine;

    public Joint(String plugName, String receptorName, String jointCategory, double[][] plugConnectingLine, double[][] receptorConnectingLine) {
        this.plugName = plugName;
        this.receptorName = receptorName;
        this.jointCategory = jointCategory;
        this.plugConnectingLine = plugConnectingLine;
        this.receptorConnectingLine = receptorConnectingLine;
    }

    public String getPlugName() {
        return plugName;
    }

    public String getReceptorName() {
        return receptorName;
    }

    public void setJointCategory(String jointCategory) {
        this.jointCategory = jointCategory;
    }

    public String toString(){
        String jointToString = "";
        jointToString += "Plug Name: " + plugName + "\n" + "Receptor Name: " + receptorName + "\n" + "Joint Category: " + jointCategory +"\n";
        jointToString += "Plug Connecting Line: (" + plugConnectingLine[0][0] + "," + plugConnectingLine[0][1] + "),("
                + plugConnectingLine[1][0] + "," + plugConnectingLine[1][1] + ") \n";
        jointToString += "Receptor Connecting Line: (" + receptorConnectingLine[0][0] + "," + receptorConnectingLine[0][1] + "),("
                + receptorConnectingLine[1][0] + "," + receptorConnectingLine[1][1] + ")\n";
        return jointToString;
    }
}

public class CreateJson {

    public static void main(String[] args) {
        /*
        //Manually Testing a Table with two legs
        //----------------------------------------------------------------------------------------------------------------------
        //Main shapes follow counterclockwise orientation
        //Table Top 300x700 units
        List<double[]> TTPoints = new ArrayList<>();
        TTPoints.add(new double[]{0,0});
        TTPoints.add(new double[]{0,300});
        TTPoints.add(new double[]{700,300});
        TTPoints.add(new double[]{700,0});
        Board tableTop = new Board("TableTop", 50, TTPoints);

        //----------------------------------------------------------------------------------------------------------------------
        //Leg 1
        List<double[]> Leg1Points = new ArrayList<>();
        Leg1Points.add(new double[]{0,0});
        Leg1Points.add(new double[]{0,300});
        Leg1Points.add(new double[]{200,300});
        Leg1Points.add(new double[]{200,0});
        Board leg1 = new Board("Leg1", 50, Leg1Points);

        //Leg 2
        List<double[]> Leg2Points = new ArrayList<>();
        Leg2Points.add(new double[]{0,0});
        Leg2Points.add(new double[]{0,300});
        Leg2Points.add(new double[]{200,300});
        Leg2Points.add(new double[]{200,0});
        Board leg2 = new Board("Leg2", 50, Leg2Points);

        //----------------------------------------------------------------------------------------------------------------------
        List<Board> boards = new ArrayList<>();
        boards.add(tableTop);
        boards.add(leg1);
        boards.add(leg2);

        List<Joint> joints = new ArrayList<>();
        //Plug Connecting Lines must also be in CCW direction or shape will not turn out correctly.
        Joint joint1 = new Joint("Leg1", "TableTop", null, new double[][]{{200,0},{0,0}}, new double[][]{{100,50},{100,250}});
        Joint joint2 = new Joint("Leg2", "TableTop", null, new double[][]{{200,0},{0,0}}, new double[][]{{600,50},{600,250}});
        joints.add(joint1);
        joints.add(joint2);

        Furniture tableTest = new Furniture("Table Test", boards, joints);
        exportSVG test = new exportSVG();
        test.createSVGFile(tableTest);

        //System.out.println("\n");
        System.out.println((char)27 + "[35mPRETTY JSON FILE:" + (char)27 + "[30m"); //ANSI Code to Format output Color

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting().serializeNulls();
        Gson gson2 = builder.create();
        System.out.println(gson2.toJson(tableTest));

        try {
            PrintWriter writer = new PrintWriter("/Users/tristanschuler/Desktop/FlatPack/out/files/tabletest.txt", "UTF-8");
            //writer.println("PRETTY JSON FILE:");
            writer.println(gson2.toJson(tableTest));
            writer.close();
        } catch (IOException e) {
            // do something
        }
        */



        //Manually Testing a Table with two legs
        //----------------------------------------------------------------------------------------------------------------------
        //Main shapes follow counterclockwise orientation
        //Table Top 300x700 units
        //Remember, for edge joints, connecting lines must also be included on main shape if smaller than a whole edge
        List<double[]> TTPoints = new ArrayList<>();
        TTPoints.add(new double[]{0,0});
        TTPoints.add(new double[]{0,50});
        TTPoints.add(new double[]{0,250});
        TTPoints.add(new double[]{0,300});
        TTPoints.add(new double[]{700,300});
        TTPoints.add(new double[]{700,250});
        TTPoints.add(new double[]{700,50});
        TTPoints.add(new double[]{700,0});
        Board tableTop = new Board("TableTop", 50, TTPoints);

        //----------------------------------------------------------------------------------------------------------------------
        //Leg 1
        List<double[]> Leg1Points = new ArrayList<>();
        Leg1Points.add(new double[]{0,0});
        Leg1Points.add(new double[]{0,300});
        Leg1Points.add(new double[]{200,300});
        Leg1Points.add(new double[]{200,0});
        Board leg1 = new Board("Leg1", 50, Leg1Points);

        //Leg 2
        List<double[]> Leg2Points = new ArrayList<>();
        Leg2Points.add(new double[]{0,0});
        Leg2Points.add(new double[]{0,300});
        Leg2Points.add(new double[]{200,300});
        Leg2Points.add(new double[]{200,0});
        Board leg2 = new Board("Leg2", 50, Leg2Points);

        //----------------------------------------------------------------------------------------------------------------------
        List<Board> boards = new ArrayList<>();
        boards.add(tableTop);
        boards.add(leg1);
        boards.add(leg2);

        List<Joint> joints = new ArrayList<>();
        //Plug Connecting Lines must also be in CCW direction or shape will not turn out correctly.
        Joint joint1 = new Joint("Leg1", "TableTop", null, new double[][]{{200,0},{0,0}}, new double[][]{{0,50},{0,250}});
        Joint joint2 = new Joint("Leg2", "TableTop", null, new double[][]{{200,0},{0,0}}, new double[][]{{700,250},{700,50}});
        joints.add(joint1);
        joints.add(joint2);

        Furniture tableTest = new Furniture("Table Edge Test", boards, joints);
        exportSVG test = new exportSVG();
        test.createSVGFile(tableTest);

        //System.out.println("\n");
        System.out.println((char)27 + "[35mPRETTY JSON FILE:" + (char)27 + "[30m"); //ANSI Code to Format output Color

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting().serializeNulls();
        Gson gson2 = builder.create();
        System.out.println(gson2.toJson(tableTest));

        try {
            PrintWriter writer = new PrintWriter("/Users/tristanschuler/Desktop/FlatPack/out/files/tableedgetest.txt", "UTF-8");
            //writer.println("PRETTY JSON FILE:");
            writer.println(gson2.toJson(tableTest));
            writer.close();
        } catch (IOException e) {
            // do something
        }
    }
}