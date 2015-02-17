package Part1;
import java.util.ArrayList;
import java.util.LinkedList;


public class BFS extends Search{
	private LinkedList<State> frontier;
	
	public BFS(String fileName) {
		super(fileName);
		frontier = new LinkedList<State>();
	}

	@Override
	public ArrayList<State> search() {
		ArrayList<State> ret = new ArrayList<State>();
		if (m != null && m.start != null) {
			frontier.add(m.start);
		}
		State goal = null;
		while (!frontier.isEmpty()) {
			State cur = frontier.poll();
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
				int dupIndex = frontier.indexOf(s);
				if (dupIndex != -1) {
					State dup = frontier.get(dupIndex);
					if (dup.gn > s.gn) {
						frontier.remove(dupIndex);
					} else {
						continue;
					}
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
	}
	
	public static void main(String[] args) {
		Search bfs = new BFS("bigMaze.txt");
		ArrayList<State> path = bfs.search();
		bfs.m.drawPath(path);
		bfs.m.print();
		System.out.println(bfs.exploredCount);
		System.out.println(path.get(0).gn);
	}
}
