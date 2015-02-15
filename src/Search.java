import java.util.ArrayList;
import java.util.HashSet;


public abstract class Search {
	Maze m;
	HashSet<State> explored;
	
	public Search(String fileName){
		m = new Maze(fileName);
		explored = new HashSet<State>();
	}
	
	public abstract ArrayList<State> search();
	
	public boolean goalTest(State s) {
		return s.equals(m.goal());
	}
	
	public void drawPath(State s){
		while(s != null) {
			if (!m.setDot(s.row(), s.col())){
				System.out.println("Something is Wrong! Failed to draw dot at " + s.row() + ":" + s.col());
			}
		}
	}
	
	public void drawPath(ArrayList<State> list) {
		for (State s : list) {
			if (!m.setDot(s.row(), s.col())){
				System.out.println("Something is Wrong! Failed to draw dot at " + s.row() + ":" + s.col());
			}
		}
	}
}
