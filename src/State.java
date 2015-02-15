import java.util.ArrayList;

public class State {
	int row, col;
	int gn;
	int hn;
	State parent;
	public State(int row, int col, State parent) {
		this.row = row;
		this.col = col;
		this.parent = parent;
		gn = parent == null ? 0 : parent.gn() + 1;
	}
	
	public int gn(){
		return gn;
	}
	
	//heuristic
	public void setHn(int gRow, int gCol) {
		hn = Math.abs(gRow - row) + Math.abs(gCol - col);
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
	
	public ArrayList<State> children(Maze m){
		ArrayList<State> ret = new ArrayList<State>();
		if (m.available(row - 1,col)) {
			ret.add(new State(row - 1, col, this));
		}
		if (m.available(row, col - 1)) {
			ret.add(new State(row, col - 1, this));
		}
		if (m.available(row + 1, col)) {
			ret.add(new State(row + 1, col, this));
		}
		if (m.available(row, col + 1)) {
			ret.add(new State(row, col + 1, this));
		}
		for (State s : ret) {
			s.setHn(m.goal.row, m.goal.col);
		}
		return ret;
	}
	
	public boolean equals(Object s) {
		return ((State)s).row == this.row && ((State)s).col == this.col;
	}
	
	public int hashCode(){
		return (String.valueOf(row) + String.valueOf(col)).hashCode();
	}
}
