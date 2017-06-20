import java.util.*;

//This class creates a list of Rectangle points that is called by UpdateShapes.

public class Rectangle {
    double[] centerPoint = new double[2];
    double plugThickness;
    double sectionDistance;
    double angle;

    List<double[]> rectangle = new ArrayList();  //Normal Rectangle
    List<double[]> rectangleRotated = new ArrayList();  //Rotated Rectangle that matches slope of line

    public Rectangle(double[] centerPoint, double plugThickness, double sectionDistance) {
        this.centerPoint = centerPoint;
        this.plugThickness = plugThickness;
        this.sectionDistance = sectionDistance;
    }

    public Rectangle(double[] centerPoint, double plugThickness, double sectionDistance, double angle) {
        this.centerPoint = centerPoint;
        this.plugThickness = plugThickness;
        this.sectionDistance = sectionDistance;
        this.angle = angle;
    }

    //Plug Thickness is given initially in Y direction, rectangle spacing is given in X Direction.
    //An angle can then be passed into any of the RotatedRectangle Functions to update the correct orientation of the points.

    public void createReceptorRectangleCorners(){

        double[] point = new double[2];
        //[x,y]
        //Top Right Corner
        point[0] = centerPoint[0] + sectionDistance;
        point[1] = centerPoint[1] + plugThickness;
        rectangle.add(new double[]{centerPoint[0] + sectionDistance,centerPoint[1] + plugThickness/2});

        //Bottom Right Corner
        point[0] = centerPoint[0] + sectionDistance;
        point[1] = centerPoint[1] - plugThickness;
        rectangle.add(new double[]{centerPoint[0] + sectionDistance, centerPoint[1] - plugThickness/2});

        //Bottom Left Corner
        point[0] = centerPoint[0] - sectionDistance;
        point[1] = centerPoint[1] - plugThickness;
        rectangle.add(new double[]{centerPoint[0] - sectionDistance, centerPoint[1] - plugThickness/2});

        //Top Left Corner
        point[0] = centerPoint[0] - sectionDistance;
        point[1] = centerPoint[1] + plugThickness;
        rectangle.add(new double[]{centerPoint[0] - sectionDistance, centerPoint[1] + plugThickness/2});
    }

    public void createPlugRectangleCorners(){

        double[] point = new double[2];
        //[x,y]
        //Top Right Corner
        point[0] = centerPoint[0] + sectionDistance;
        point[1] = centerPoint[1] + plugThickness;
        rectangle.add(new double[]{centerPoint[0] + sectionDistance,centerPoint[1] + plugThickness});

        //Bottom Right Corner
        point[0] = centerPoint[0] + sectionDistance;
        point[1] = centerPoint[1] - plugThickness;
        rectangle.add(new double[]{centerPoint[0] + sectionDistance, centerPoint[1]});

        //Bottom Left Corner
        point[0] = centerPoint[0] - sectionDistance;
        point[1] = centerPoint[1] - plugThickness;
        rectangle.add(new double[]{centerPoint[0] - sectionDistance, centerPoint[1]});

        //Top Left Corner
        point[0] = centerPoint[0] - sectionDistance;
        point[1] = centerPoint[1] + plugThickness;
        rectangle.add(new double[]{centerPoint[0] - sectionDistance, centerPoint[1] + plugThickness});
    }

    public List<double[]> getRectangle() {
        return rectangle;
    }

    public List<double[]> createRotatedRectangleCorners() {
        //Top Right Corner

        double[] point = new double[2];
        point[0] = centerPoint[0] + (Math.cos(angle) * (rectangle.get(0)[0] - centerPoint[0]) - Math.sin(angle) * (rectangle.get(0)[1] - centerPoint[1]));
        point[1] = centerPoint[1] + (Math.sin(angle) * (rectangle.get(0)[0] - centerPoint[0]) + Math.cos(angle) * (rectangle.get(0)[1] - centerPoint[1]));
        rectangleRotated.add(point);

        //Bottom Right Corner
        double[] point3 = new double[2];
        point3[0] = centerPoint[0] + (Math.cos(angle) * (rectangle.get(3)[0] - centerPoint[0]) - Math.sin(angle) * (rectangle.get(3)[1] - centerPoint[1]));
        point3[1] = centerPoint[1] + (Math.sin(angle) * (rectangle.get(3)[0] - centerPoint[0]) + Math.cos(angle) * (rectangle.get(3)[1] - centerPoint[1]));
        rectangleRotated.add(point3);

        //Bottom Left Corner
        double[] point2 = new double[2];
        point2[0] = centerPoint[0] + (Math.cos(angle) * (rectangle.get(2)[0] - centerPoint[0]) - Math.sin(angle) * (rectangle.get(2)[1] - centerPoint[1]));
        point2[1] = centerPoint[1] + (Math.sin(angle) * (rectangle.get(2)[0] - centerPoint[0]) + Math.cos(angle) * (rectangle.get(2)[1] - centerPoint[1]));
        rectangleRotated.add(point2);

        //Top Left Corner
        double[] point1 = new double[2];
        point1[0] = centerPoint[0] + (Math.cos(angle) * (rectangle.get(1)[0] - centerPoint[0]) - Math.sin(angle) * (rectangle.get(1)[1] - centerPoint[1]));
        point1[1] = centerPoint[1] + (Math.sin(angle) * (rectangle.get(1)[0] - centerPoint[0]) + Math.cos(angle) * (rectangle.get(1)[1] - centerPoint[1]));
        rectangleRotated.add(point1);
        return rectangleRotated;
    }
}
