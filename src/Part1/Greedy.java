package Part1;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;


public class Greedy extends Search{
	
	public Greedy(String fileName) {
		super(fileName);
		
		frontier = new PriorityQueue<State>(100, new Comparator<State>(){
			public int compare(State o1, State o2) {
				return o1.hn - o2.hn;
			}});
	}
	
	public static void main(String[] args) {
		Search greedy = new Greedy("bigMaze.txt");
		ArrayList<State> path = greedy.search();
		greedy.m.drawPath(path);
		greedy.m.print();
		System.out.println(greedy.exploredCount);
		System.out.println(path.get(0).gn);
	}

}
