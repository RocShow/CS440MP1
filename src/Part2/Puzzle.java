package Part2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Stack;

public class Puzzle {
	private HashSet<State> explored;
	private PriorityQueue<State> frontier;
	State start, end;
	int exploredCount;
	
	public Puzzle() {
		start = new State();
		frontier = new PriorityQueue<State>(100, new Comparator<State>(){
			public int compare(State o1, State o2) {
				return o1.cost + o1.h - o2.cost - o2.h;
			}
			
		});
		exploredCount = 0;
		explored = new HashSet<State>();
	}
	
	public Puzzle(int[][] board) {
		this();
		start = new State(board);
	}
	
	public State search(){
		frontier.add(start);
		while (!frontier.isEmpty()) {
			State cur = frontier.poll();
			if (explored.contains(cur)) {
				continue;
			}
			exploredCount++;
			explored.add(cur);
			if (cur.goalTest()) {
				end = cur;
				return cur;
			}
			ArrayList<State> children = cur.children();
			for (State s : children) {
				if (!explored.contains(s)) {
					frontier.add(s);
				}
			}
		}
		return null;
	}
	
	public void printSolution(){
		if (end == null) {
			System.out.println("No Solution");
		}
		Stack<State> stack = new Stack<State>();
		while (end != null) {
			stack.push(end);
			end = end.parent;
		}
		while (!stack.isEmpty()) {
			stack.pop().print();
		}
	}
}
