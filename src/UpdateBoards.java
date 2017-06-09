import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UpdateBoards {

    static String jointType;
    static int numberOfTeeth;
    static double plugThickness;

    Furniture item;

    public UpdateBoards(Furniture item) {
        this.item = item;
    }

    public static void main(String[] args) {
        /*
        System.out.println("Please type in what kind of joints you would like (i.e. Squaretooth).");
        Scanner s = new Scanner(System.in);
        jointType = s.nextLine();

        System.out.println("How many teeth?");
        numberOfTeeth = s.nextInt();

        System.out.println("\nJoint Type: " + jointType);
        System.out.println("Number of teeth: " + numberOfTeeth);
        */

        numberOfTeeth = 3;
        jointType = "sawtooth";
        plugThickness = 1.0;


        //----------------------------------------------------------------------------------------------------------------------
        //Board Square 1 Information for Testing
        List<double[]> board1points = new ArrayList<>();
        board1points.add(new double[]{0,0});
        board1points.add(new double[]{0,50});
        board1points.add(new double[]{50,50});
        board1points.add(new double[]{50,0});
        Board square1 = new Board("Square1", 1, board1points);

        //----------------------------------------------------------------------------------------------------------------------
        //Board Square 2 Information for Testing
        List<double[]> board2points = new ArrayList<>();
        board2points.add(new double[]{0,0});
        board2points.add(new double[]{0,50});
        board2points.add(new double[]{50,50});
        board2points.add(new double[]{50,0});
        Board square2 = new Board("Square2", 1, board2points);

        //----------------------------------------------------------------------------------------------------------------------
        List<Board> boards = new ArrayList<>();
        boards.add(square1);
        boards.add(square2);

        List<Joint> joints = new ArrayList<>();
        Joint joint1 = new Joint("Square1", "Square2", null, new double[][]{{0,0},{50,50}}, new double[][]{{10,10},{25,25}});
        joints.add(joint1);

        Furniture rightAngleTest = new Furniture("Right Angle Test", boards, joints);
        //----------------------------------------------------------------------------------------------------------------------
        //----------------------------------------------------------------------------------------------------------------------
        List<double[][]> holes = new ArrayList<double[][]>();

        for (int i = 0; i < rightAngleTest.joints.size(); i++) {
            Joint j = rightAngleTest.joints.get(i);
            System.out.println(j);

            //Extract infromation from Joint to create new shapes
            //Joint Type, NumberOfTeeth, and PlugThickness will all be user input
            //First Receptor holes
            UpdateShapes updatedJointShapes = new UpdateShapes(jointType, numberOfTeeth, plugThickness, j.receptorConnectingLine);
            holes = updatedJointShapes.makeReceptorHoles();

        }

        //Update Receptor
        for (Board b : rightAngleTest.boards){
            if (b.getBoardName().equals(joint1.receptorName))
            b.setHoles(holes);
        }

        System.out.println(rightAngleTest.boards.get(1).getHoles());

        System.out.println((char)27 + "[35mPRETTY JSON FILE:" + (char)27 + "[30m"); //ANSI Code to Format output Color

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting().serializeNulls();
        Gson gson2 = builder.create();
        System.out.println(gson2.toJson(rightAngleTest));
    }
}
