package Part1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Q13 {
	Maze3 m;
	HashSet<State3> explored;
	PriorityQueue<State3> frontier;
	int exploredCount = 0;
	
	public Q13(String fileName){
		m = new Maze3(fileName);
		explored = new HashSet<State3>();
		frontier = new PriorityQueue<State3>(100, new Comparator<State3>(){
			public int compare(State3 o1, State3 o2) {
				return (o1.hn + o1.gn) - (o2.hn + o2.gn);
			}});
	}
	
	public ArrayList<State3> search(){
		ArrayList<State3> ret = new ArrayList<State3>();
		if (m != null && m.start != null) {
			frontier.add(m.start);
		}
		State3 goal = null;
		while (!frontier.isEmpty()) {
			State3 cur = frontier.poll();
			if (explored.contains(cur)) {
				continue;
			}
			exploredCount++;
			//System.out.println(cur.gn);
			if (cur.isGoal()) {
				goal = cur;
				break;
			}
			explored.add(cur);
			ArrayList<State3> children = cur.children(m);
			for (State3 s : children) {
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
	
	public static void main(String[] args) {
		Q13 q = new Q13("smallSearch.txt");
		Date start = new Date();
		ArrayList<State3> path = q.search();
		Date end = new Date();
		System.out.println((end.getTime() - start.getTime())/1000);
		q.m.drawPath(path);
		System.out.println(q.exploredCount);
		if (path != null){
			System.out.println(path.get(0).gn);
		}
		q.m.print();
	}
}
