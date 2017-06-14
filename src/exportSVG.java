import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

//This class can be called to export several SVG files form the imported furniture
//item and it's corresponding boards.
public class exportSVG {
    Furniture item;

    /*public exportSVG(Furniture item) {
        this.item = item;
    }
    */

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




    public void createSVGFile(Furniture f) {
        List<Board> boards = f.getBoards();
        int counter = 1;


        for (Board b : boards) {
            try {
                PrintWriter writer = new PrintWriter("/Users/tristanschuler/Desktop/FlatPack/out/files/board-" + counter + ".txt", "UTF-8");
                writer.println("<svg height=\"600\" width=\"600\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">");
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
                // do something
            }

            counter +=1;
        }


    }
}
