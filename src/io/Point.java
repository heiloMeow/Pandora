package io;

/**
 * Represent the points in the 2D array maze.
 * <li>row stands for the row the character in</li>
 * <li>column stands for the column the character in</li>
 * <li>Point stands for the last point before visiting the current point</li>
 */
class Point {
    public int row;
    public int column;
    Point lastPoint;
    Point(int row, int col, Point lastPoint) {
        this.row = row;
        this.column = col;
        this.lastPoint = lastPoint;
    }
    public int hashCode(){
        return row*13+column;
    }
}
