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
    static boolean hiddenJoint = false;

    Furniture item;

    public UpdateBoards(Furniture item) {
        this.item = item;
    }

    public void UpdateBoards() throws IOException {
        DeserializeJSON d = new DeserializeJSON();
        d.deserializeJSON();
        Furniture item = d.deserializeJSON();

        //testing for now
        //numberOfTeeth = 3;
        //jointType = "sawtooth";
        //plugThickness = 50.0;

        List<List<double[]>> holes = new ArrayList<>();
        for (int i = 0; i < item.joints.size(); i++) {
            Joint j = item.joints.get(i);
            System.out.println(j);

            //Extract infromation from Joint to create new shapes
            //Joint Type, NumberOfTeeth, and PlugThickness will all be user input
            //First Receptors holes
            UpdateShapes updatedJointShapes = new UpdateShapes(jointType, numberOfTeeth, plugThickness, j.receptorConnectingLine);
            holes = updatedJointShapes.updateReceptors();

            //searches for correct board updates receptors
            for (Board b : item.boards) {
                if (b.getBoardName().equals(j.receptorName))
                    b.setHoles(holes);
            }
        }

        List<double[]> plugs = new ArrayList<>();
        for (int i = 0; i < item.joints.size(); i++) {
            Joint j = item.joints.get(i);
            System.out.println(j);

            //Extract infromation from Joint to create new shapes
            //Joint Type, NumberOfTeeth, and PlugThickness will all be user input
            UpdateShapes updatedJointShapes = new UpdateShapes(jointType, numberOfTeeth, plugThickness, j.plugConnectingLine);
            plugs = updatedJointShapes.updatePlugs();

            //searches for correct board updates plugs
            for (Board b : item.boards) {
                if (b.getBoardName().equals(j.plugName)) {
                    ;
                    b.setPlugCoordinates(plugs);
                }
            }
        }

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting().serializeNulls();
        Gson gson = builder.create();

        try {
            PrintWriter writer = new PrintWriter("/Users/tristanschuler/Desktop/FlatPack/out/files/updatedJSON.txt", "UTF-8");
            //writer.println("PRETTY JSON FILE:");
            writer.println(gson.toJson(item));
            writer.close();
        } catch (IOException e) {
            // do something
        }

        exportSVG test = new exportSVG();
        test.createUpdatedSVGFile(item);
    }



    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public static void main(String[] args) throws IOException {
        DeserializeJSON d = new DeserializeJSON();
        Furniture item = d.deserializeJSON();
        item.determineJointType();


        System.out.println("Please individually select specs for each joint in \"" + item.getFurnitureName() + "\"");

        //testing for now
        //numberOfTeeth = 3;
        //jointType = "squaretooth";
        //plugThickness = 50.0;


/*
        //Check if Edge joint is possible
        if (jointType.equals("Edge Squaretooth")) {
            for (int n = 0; n < item.joints.size(); n++) {
                Joint j = item.joints.get(n);
                for (Board b : item.boards) {
                    if (b.getBoardName().equals(j.receptorName)) {
            }


        }
        */


        ///Users/tristanschuler/Desktop/FlatPack/out/files/tableedgetest.txt
        ///Users/tristanschuler/Desktop/FlatPack/out/files/tabletest.txt

        for (int i = 0; i < item.joints.size(); i++) {
            Joint j = item.joints.get(i);
            System.out.println(j.toString() + "\n");



            //Get joint specifications from the user for each individual joint.
            Scanner s = new Scanner(System.in);
            System.out.println("What type of joint will this furniture item use? (Squaretooth, Edge_Squaretooth)");
            jointType = s.next();
            //s.close();

            Scanner s2 = new Scanner(System.in);
            System.out.println("How many teeth for the joints?");
            numberOfTeeth = s2.nextInt();
            //s2.close();

            Scanner s1 = new Scanner(System.in);
            System.out.println("What is the board thickness? [Enter in 0.0 format]");
            plugThickness = s1.nextDouble();
            //s1.close();


            if (jointType.equals("Squaretooth")) {
                List<List<double[]>> holes;
                //Extract information from Joint to create new shapes
                //Joint Type, NumberOfTeeth, and PlugThickness will all be user input
                //First Receptors holes
                UpdateShapes updatedJointShapesReceptors = new UpdateShapes(jointType, numberOfTeeth, plugThickness, j.receptorConnectingLine);
                holes = updatedJointShapesReceptors.updateReceptors();

                //searches for correct board updates receptors
                for (Board b : item.boards) {
                    if (b.getBoardName().equals(j.receptorName)) {
                        b.setHoles(holes);
                        break;
                    }
                }

                List<double[]> plugs;
                UpdateShapes updatedJointShapesPlugs = new UpdateShapes(jointType, numberOfTeeth, plugThickness, j.plugConnectingLine);
                plugs = updatedJointShapesPlugs.updatePlugs();

                //searches for correct board & updates plugs
                for (Board b : item.boards) {
                    if (b.getBoardName().equals(j.plugName)) {
                        b.setPlugCoordinates(plugs);
                        break;
                    }
                }
            }
            else if (jointType.equals("Edge_Squaretooth")){
                System.out.println("You chose Edge Squaretooth");
            }

            else
                System.out.println("That is not a joint type.");

        }


        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting().serializeNulls();
        Gson gson = builder.create();

        try {
            PrintWriter writer = new PrintWriter("/Users/tristanschuler/Desktop/FlatPack/out/files/updatedJSON.txt", "UTF-8");
            //writer.println("PRETTY JSON FILE:");
            writer.println(gson.toJson(item));
            writer.close();
        } catch (IOException e) {
            // do something
        }

        exportSVG test = new exportSVG();
        test.createUpdatedSVGFile(item);
    }
}