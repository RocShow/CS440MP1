package Part1;

public class Point {
	int row, col;
	public Point(int x, int y){
		row = x;
		col = y;
	}
	
	public boolean equals(Object o) {
		Point p = (Point)o;
		return p.row == row && p.col == col;
	}
	
	public int hashCode(){
		return ("" + row + col).hashCode();
	}
}
