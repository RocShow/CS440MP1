package Part1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class State3 {
	int row, col;
	int gn;
	int hn;
	boolean computeHn;
	State3 parent;
	HashSet<Point> remain;
	boolean onDot = false;
	Maze3 m;
	public State3(int row, int col, State3 parent, Maze3 m, boolean h) {
		this.row = row;
		this.col = col;
		this.parent = parent;
		this.remain = parent == null ? new HashSet<Point>() : new HashSet<Point>(parent.remain);
		this.m = m;
		if (m.map[row][col] == '.') {
			
			//System.out.print(remain.size() + "***");
			Point p = new Point(row, col);
			if (remain.contains(p)) {
				onDot = true;
				remain.remove(new Point(row, col));
			}
			//System.out.println(remain.size());
		}
		gn = parent == null ? 1 : parent.gn() + 1;
		computeHn = h;
	}
	
	public int gn(){
		return gn;
	}
	
	//heuristic
	public void setHn(HashSet<Point> dots) {
		//h version 1
		hn = 0;
		int min = Integer.MAX_VALUE;
		for (Point i : dots) {
			min = Math.min(hn, Math.abs(i.row - row) + Math.abs(i.col - col));
			int closet = Integer.MAX_VALUE;
			for (Point another : dots) {
				if (another == i) {
					continue;
				}
				closet = Math.min(closet, Math.abs(i.row - another.row) + Math.abs(i.col - another.col));
			}
			if (closet != Integer.MAX_VALUE) {
				hn += closet;
			}
		}
		hn += min;
		
		//h version 2
//		hn = 0;
//		char[][] newMap = new char[m.map.length - 2][m.map[0].length - 2];
//		for(Point p : dots) {
//			newMap[p.row - 1][p.col - 1] = '.';
//		}
//		newMap[row - 1][col - 1] = 'P';
//		//bfs
//		hn = new BFS3(newMap,row - 1,col - 1).search();
	}
	
	public int hn(){
		return hn;
	}
	
	public int gnhn(){
		return gn + hn;
	}
	
	public int row(){
		return row;
	}
	
	public int col(){
		return col;
	}
	
	public ArrayList<State3> children(Maze3 m){
		ArrayList<State3> ret = new ArrayList<State3>();
		if (m.available(row - 1,col)) {
			ret.add(new State3(row - 1, col, this, m, computeHn));
		}
		if (m.available(row, col - 1)) {
			ret.add(new State3(row, col - 1, this, m, computeHn));
		}
		if (m.available(row + 1, col)) {
			ret.add(new State3(row + 1, col, this, m, computeHn));
		}
		if (m.available(row, col + 1)) {
			ret.add(new State3(row, col + 1, this, m, computeHn));
		}
		if (computeHn) {
			for (State3 s : ret) {
				s.setHn(m.dots);
			}
		}
		
		return ret;
	}
	
	public boolean equals(Object s) {
		if (((State3)s).row != this.row || ((State3)s).col != this.col || ((State3)s).remain.size() != remain.size()){
			return false;
		}
		for(Point p : remain) {
			if (!((State3)s).remain.contains(p)) {
				return false;
			}
		}
		return true;
	}
	
	public int hashCode(){
		return (String.valueOf(row) + String.valueOf(col)).hashCode();
	}
	
	public boolean isGoal() {
		return remain.size() == 0;
	}
}
