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

    public void UpdateBoards() throws IOException{
        DeserializeJSON d = new DeserializeJSON();
        d.deserializeJSON();
        Furniture item = d.deserializeJSON();

        //testing for now
        numberOfTeeth = 3;
        jointType = "sawtooth";
        plugThickness = 50.0;

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
            for (Board b : item.boards){
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
            for (Board b : item.boards){
                if (b.getBoardName().equals(j.plugName)) {;
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
    //-------------------------------------------------------------------------------------------------------------------

    public static void main(String[] args) throws IOException{
        DeserializeJSON d = new DeserializeJSON();
        Furniture item = d.deserializeJSON();

        //testing for now
        numberOfTeeth = 3;
        jointType = "sawtooth";
        plugThickness = 50.0;

        ///Users/tristanschuler/Desktop/FlatPack/out/files/tabletest.txt
        for (int i = 0; i < item.joints.size(); i++) {
            List<List<double[]>> holes;
            Joint j = item.joints.get(i);
            System.out.println(j);

            //Extract information from Joint to create new shapes
            //Joint Type, NumberOfTeeth, and PlugThickness will all be user input
            //First Receptors holes
            UpdateShapes updatedJointShapes = new UpdateShapes(jointType, numberOfTeeth, plugThickness, j.receptorConnectingLine);
            holes = updatedJointShapes.updateReceptors();

            //searches for correct board updates receptors
            for (Board b : item.boards){
                if (b.getBoardName().equals(j.receptorName)) {
                    b.setHoles(holes);
                    break;
                }
            }
        }

        for (int i = 0; i < item.joints.size(); i++) {
            List<double[]> plugs;
            Joint j = item.joints.get(i);
            System.out.println(j);

            //Extract infromation from Joint to create new shapes
            //Joint Type, NumberOfTeeth, and PlugThickness will all be user input
            UpdateShapes updatedJointShapes = new UpdateShapes(jointType, numberOfTeeth, plugThickness, j.plugConnectingLine);
            plugs = updatedJointShapes.updatePlugs();

            //searches for correct board updates plugs
            for (Board b : item.boards){
                if (b.getBoardName().equals(j.plugName)) {
                    b.setPlugCoordinates(plugs);
                    break;
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
}