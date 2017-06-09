public class Rectangle {
    double[] centerPoint = new double[2];
    double plugThickness;
    double sectionDistance;
    double angle;

    double[][] rectangle = new double[4][2];  //Normal Rectangle
    double[][] rectangleRotated = new double[4][2];  //Rotated Rectangle that matches slope of line

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

    public void createRectangleCorners(){
        //[x,y]
        //Top Right Corner
        rectangle[0][0] = centerPoint[0] + sectionDistance/2;
        rectangle[0][1] = centerPoint[1] + plugThickness/2;

        //Top Left Corner
        rectangle[1][0] = centerPoint[0] - sectionDistance/2;
        rectangle[1][1] = centerPoint[1] + plugThickness/2;

        //Bottom Left Corner
        rectangle[2][0] = centerPoint[0] - sectionDistance/2;
        rectangle[2][1] = centerPoint[1] - plugThickness/2;

        //Bottom Right Corner
        rectangle[3][0] = centerPoint[0] + sectionDistance/2;
        rectangle[3][1] = centerPoint[1] - plugThickness/2;

    }

    public double[][] getRectangle() {
        return rectangle;
    }

    public double[][] createRotatedRectangleCorners() {
        rectangleRotated[0][0] = centerPoint[0] + (Math.cos(angle) * (rectangle[0][0] - centerPoint[0]) - Math.sin(angle) * (rectangle[0][1] - centerPoint[1]));
        rectangleRotated[0][1] = centerPoint[1] + (Math.sin(angle) * (rectangle[0][0] - centerPoint[0]) + Math.cos(angle) * (rectangle[0][1] - centerPoint[1]));

        //Top Left Corner
        rectangleRotated[1][0] = centerPoint[0] + (Math.cos(angle) * (rectangle[1][0] - centerPoint[0]) - Math.sin(angle) * (rectangle[1][1] - centerPoint[1]));
        rectangleRotated[1][1] = centerPoint[1] + (Math.sin(angle) * (rectangle[1][0] - centerPoint[0]) + Math.cos(angle) * (rectangle[1][1] - centerPoint[1]));

        //Bottom Left Corner
        rectangleRotated[2][0] = centerPoint[0] + (Math.cos(angle) * (rectangle[2][0] - centerPoint[0]) - Math.sin(angle) * (rectangle[2][1] - centerPoint[1]));
        rectangleRotated[2][1] = centerPoint[1] + (Math.sin(angle) * (rectangle[2][0] - centerPoint[0]) + Math.cos(angle) * (rectangle[2][1] - centerPoint[1]));

        //Bottom Right Corner
        rectangleRotated[3][0] = centerPoint[0] + (Math.cos(angle) * (rectangle[3][0] - centerPoint[0]) - Math.sin(angle) * (rectangle[3][1] - centerPoint[1]));
        rectangleRotated[3][1] = centerPoint[1] + (Math.sin(angle) * (rectangle[3][0] - centerPoint[0]) + Math.cos(angle) * (rectangle[3][1] - centerPoint[1]));

        return rectangleRotated;
    }
}
