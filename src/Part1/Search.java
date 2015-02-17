package Part1;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;


public abstract class Search {
	Maze m;
	HashSet<State> explored;
	PriorityQueue<State> frontier;
	int exploredCount = 0;
	
	public Search(String fileName){
		m = new Maze(fileName);
		explored = new HashSet<State>();
	}
	
//	public void setFrontier(PriorityQueue<State> frontier) {
//		this.frontier = frontier;
//	}
	
	public ArrayList<State> search(){
		if (frontier == null) {
			System.out.println("Frontier has not been initialized.");
			return null;
		}
		ArrayList<State> ret = new ArrayList<State>();
		if (m != null && m.start != null) {
			frontier.add(m.start);
		}
		State goal = null;
		while (!frontier.isEmpty()) {
			State cur = frontier.poll();
			if (explored.contains(cur)) {
				continue;
			}
			exploredCount++;
			if (goalTest(cur)) {
				goal = cur;
				break;
			}
			explored.add(cur);
			ArrayList<State> children = cur.children(m);
			for (State s : children) {
				if (explored.contains(s)) {
					continue;
				}
				frontier.add(s);
			}
		}
		if (goal == null) {
			return null;
		}
		while (goal != null) {
			ret.add(goal);
			goal = goal.parent;
		}
		return ret;
	};
	
	public boolean goalTest(State s) {
		return s.equals(m.goal());
	}
	
	
}
