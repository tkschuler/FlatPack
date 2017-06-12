import java.util.List;

//This class can be called to export several SVG files form the imported furniture
//item and it's corresponding boards.
public class exportSVG {
    Furniture item;

    public exportSVG(Furniture item) {
        this.item = item;
    }

    public void createSVGFile(Furniture f){
        List<Board> boards = f.getBoards();

        for (Board b : boards) {
            List<double[]> mainCoordinates = b.getCoordinates();
            List<double[][]> holes = b.getHoles();
        }

    }
}
