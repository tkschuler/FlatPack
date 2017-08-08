import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

//This class can be called to export several SVG files form the imported furniture
//item and it's corresponding boards.

//Main coordinates are blue.
public class exportSVG {
    public String mainShapeLine(List<double[]> coords) {
        String line = "\t <path d=\"M";
        for (double[] point : coords) {
            line += point[0] + " " + point[1] + " ";

            if (coords.indexOf(point) != coords.size() - 1) {
                line += "L";
            }
        }
        line += "Z\" fill=\"none\" stroke=\"blue\" stroke-width=\"1\"/>";
        return line;
    }

    //helper method for receptor boards with holes. Holes are green
    public String holesLine(List<List<double[]>> holes) {
        String line = "";
        for (List<double[]> hole : holes) {
            line += "\t <path d=\"M";
            for (double[] point : hole) {
                line += point[0] + " " + point[1] + " ";

                if (hole.indexOf(point) != hole.size() - 1) {
                    line += "L";
                }
            }
            line += "Z\" fill=\"none\" stroke=\"green\" stroke-width=\"1\"/>" + "\n";
        }
        return line;
    }

    //Exports a string that is saved as a .svg file.  SVG files can be viewed in a browser and displayed as an image.
    public void createSVGFile(Furniture f) {
        List<Board> boards = f.getBoards();
        int counter = 1;
        for (Board b : boards) {
            try {
                PrintWriter writer = new PrintWriter("/Users/tristanschuler/Desktop/FlatPack/out/files/test_" + b.getBoardName() + ".svg", "UTF-8");
                writer.println("<svg height=\"1000\" width=\"1000\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">");
                List<double[]> mainCoordinates = b.getCoordinates();
                List<List<double[]>> holes = b.getHoles();

                String line = mainShapeLine(mainCoordinates);
                writer.println(line);

                if (holes != null) {
                    String line2 = holesLine(holes);
                    writer.println(line2);
                }

                writer.println("</svg>");
                writer.close();
            } catch (IOException e) {
            }
            counter +=1;
        }
    }

    //Same as previous method, but with a different naming scheme.
    public void createUpdatedSVGFile(Furniture f) {
        List<Board> boards = f.getBoards();
        int counter = 1;

        for (Board b : boards) {
            try {
                PrintWriter writer = new PrintWriter("/Users/tristanschuler/Desktop/FlatPack/out/files/updated_" + b.getBoardName() + ".svg", "UTF-8");
                writer.println("<svg height=\"1000\" width=\"1000\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"-100 -100 1000 1000\">");
                //In the future, view boxes should probably be changed to be the min and max values of a particular shape.  All other boards will probably
                //rely on the shape that requires the largest viewbox.
                List<double[]> mainCoordinates = b.getCoordinates();
                List<List<double[]>> holes = b.getHoles();

                String line = mainShapeLine(mainCoordinates);
                writer.println(line);

                if (holes != null) {
                    String line2 = holesLine(holes);
                    writer.println(line2);
                }

                writer.println("</svg>");
                writer.close();
            } catch (IOException e) {
            }
            counter +=1;
        }
    }
}
