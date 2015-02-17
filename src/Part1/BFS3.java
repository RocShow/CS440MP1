package Part1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class BFS3 {
	
	char[][] map;
	int px, py;
	
	public BFS3(char map[][], int px, int py) {
		this.map = map;
		this.px = px;
		this.py = py;
	}
	
	public int search(){
		Maze3 m = new Maze3(map);
		State3 start = new State3(px,py,null,m,false);
		LinkedList<State3> queue = new LinkedList<State3>();
		HashSet<State3> explored = new HashSet<State3>();
		queue.add(start);
		while (!queue.isEmpty()) {
			State3 cur = queue.poll();
			explored.add(cur);
			if (cur.isGoal()) {
				return cur.gn;
			}
			ArrayList<State3> children = cur.children(m);
			for (State3 child : children) {
				if (explored.contains(child)) {
					continue;
				}
				int dupIndex = queue.indexOf(child);
				if (dupIndex != -1 && queue.get(dupIndex).gn > child.gn) {
					queue.remove(dupIndex);
				} else {
					continue;
				}
				queue.add(child);
			}
		}
		System.out.println("impossible!");
		return -1;
	}
}
