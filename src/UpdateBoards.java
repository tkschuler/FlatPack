import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.PrintWriter;
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
        plugThickness = 50.0;

        //----------------------------------------------------------------------------------------------------------------------
        //Board Square 1 Information for Testing
        List<double[]> board1points = new ArrayList<>();
        board1points.add(new double[]{250,500});
        board1points.add(new double[]{500,67});
        board1points.add(new double[]{0,67});
        Board square1 = new Board("Square1", 30, board1points);

        //----------------------------------------------------------------------------------------------------------------------
        //Board Square 2 Information for Testing
        List<double[]> board2points = new ArrayList<>();
        board2points.add(new double[]{250,67});
        board2points.add(new double[]{500,500});
        board2points.add(new double[]{0,500});
        Board square2 = new Board("Square2", 30, board2points);

        //----------------------------------------------------------------------------------------------------------------------
        List<Board> boards = new ArrayList<>();
        boards.add(square1);
        boards.add(square2);

        List<Joint> joints = new ArrayList<>();
        Joint joint1 = new Joint("Square1", "Square2", null, new double[][]{{250,500},{500,67}}, new double[][]{{250,200},{400,400}});
        //Joint joint1 = new Joint("Square1", "Square2", null, new double[][]{{0,0},{500,500}}, new double[][]{{0,500},{500,500}});
        //Joint joint1 = new Joint("Square1", "Square2", null, new double[][]{{0,0},{500,500}}, new double[][]{{500,500},{250,67}});
        //Joint joint1 = new Joint("Square1", "Square2", null, new double[][]{{0,0},{500,500}}, new double[][]{{250,67},{0,500}});
        joints.add(joint1);

        Furniture rightAngleTest = new Furniture("Right Angle Test", boards, joints);
        //----------------------------------------------------------------------------------------------------------------------
        //----------------------------------------------------------------------------------------------------------------------
        //Determine holes for receptor
        List<List<double[]>> holes = new ArrayList<>();
        for (int i = 0; i < rightAngleTest.joints.size(); i++) {
            Joint j = rightAngleTest.joints.get(i);
            System.out.println(j);

            //Extract infromation from Joint to create new shapes
            //Joint Type, NumberOfTeeth, and PlugThickness will all be user input
            //First Receptors holes
            UpdateShapes updatedJointShapes = new UpdateShapes(jointType, numberOfTeeth, plugThickness, j.receptorConnectingLine);
            holes = updatedJointShapes.updateReceptors();
        }

        List<double[]> plugs = new ArrayList<>();

        for (int i = 0; i < rightAngleTest.joints.size(); i++) {
            Joint j = rightAngleTest.joints.get(i);
            System.out.println(j);

            //Extract infromation from Joint to create new shapes
            //Joint Type, NumberOfTeeth, and PlugThickness will all be user input
            //First Receptors holes
            UpdateShapes updatedJointShapes = new UpdateShapes(jointType, numberOfTeeth, plugThickness, j.plugConnectingLine);
            plugs = updatedJointShapes.updatePlugs();
        }


        //Update the boards from the above determined holes and plugs.
        //-------------------------------------------------------------------
        //Update Receptors
        for (Board b : rightAngleTest.boards){
            if (b.getBoardName().equals(joint1.receptorName))
            b.setHoles(holes);
        }

        //Update Plugs
        for (Board b : rightAngleTest.boards){
            if (b.getBoardName().equals(joint1.plugName)) {;
                b.setPlugCoordinates(plugs);
            }
        }
        //-------------------------------------------------------------------

        //System.out.println(rightAngleTest.boards.get(1).getHoles());

        System.out.println((char)27 + "[35mPRETTY JSON FILE:" + (char)27 + "[30m"); //ANSI Code to Format output Color

        exportSVG test = new exportSVG();
        test.createSVGFile(rightAngleTest);


        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting().serializeNulls();
        Gson gson2 = builder.create();
        System.out.println(gson2.toJson(rightAngleTest));

        try {
            PrintWriter writer = new PrintWriter("/Users/tristanschuler/Desktop/FlatPack/out/files/gson_test.txt", "UTF-8");
            writer.println("PRETTY JSON FILE:");
            writer.println(gson2.toJson(rightAngleTest));
            writer.close();
        } catch (IOException e) {
            // do something
        }
    }
}