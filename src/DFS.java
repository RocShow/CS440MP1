import java.util.ArrayList;
import java.util.Stack;

public class DFS extends Search{
	private Stack<State> frontier;
	
	public DFS(String fileName) {
		super(fileName);
		frontier = new Stack<State>();
	}

	@Override
	public ArrayList<State> search() {
		ArrayList<State> ret = new ArrayList<State>();
		if (m != null && m.start != null) {
			frontier.push(m.start);
		}
		State goal = null;
		while (!frontier.isEmpty()) {
			State cur = frontier.pop();
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
				int dupIndex = frontier.indexOf(s);
				if (dupIndex != -1) {
					State dup = frontier.get(dupIndex);
					if (dup.gn > s.gn) {
						frontier.remove(dupIndex);
					} else {
						continue;
					}
				}
				frontier.push(s);
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
	}
	
	public static void main(String[] args) {
		Search dfs = new DFS("bigMaze.txt");
		ArrayList<State> path = dfs.search();
		dfs.drawPath(path);
		dfs.m.print();
	}
}
