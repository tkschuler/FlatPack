import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;

class Furniture{
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
}

class Board{
    private String boardName;
    private double thickness;
    //Coordinates will be a list of list of coordinates, to allow addition of holes.
    //i.e. { { [0,0],[0,50],[50,50],[50,0] } , { [10,10],[10,20] } , { [10,40],[10,30] } }
    private List<double[]> mainCoordinates;
    private List<List<double[]>> holes;


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

    public void setHoles(List<List<double[]>> holes) {
        this.holes = holes;
    }
}

class Joint{
    String plugName;
    String receptorName;
    String jointType;

    double[][] plugConnectingLine;
    double[][] receptorConnectingLine;

    public Joint(String plugName, String receptorName, String jointType, double[][] plugConnectingLine, double[][] receptorConnectingLine) {
        this.plugName = plugName;
        this.receptorName = receptorName;
        this.jointType = jointType;
        this.plugConnectingLine = plugConnectingLine;
        this.receptorConnectingLine = receptorConnectingLine;
    }
}


public class CreateJson {

    public static void main(String[] args) {


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
        Joint joint1 = new Joint("Square1", "Square2", null, new double[][]{{0,0},{50,50}}, new double[][]{{0,0},{50,50}});
        joints.add(joint1);

        Furniture rightAngleTest = new Furniture("Right Angle Test", boards, joints);





        Gson gson = new Gson();
        String jsonString = gson.toJson(rightAngleTest);
        System.out.println(jsonString);

        System.out.println("\n");

        System.out.println((char)27 + "[35mPRETTY JSON FILE:" + (char)27 + "[30m"); //ANSI Code to Format output Color

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting().serializeNulls();
        Gson gson2 = builder.create();
        System.out.println(gson2.toJson(rightAngleTest));


    }
}
