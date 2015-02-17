package Part1;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;


public class AStar extends Search{

	public AStar(String fileName) {
		super(fileName);
		
		frontier = new PriorityQueue<State>(100, new Comparator<State>(){
			public int compare(State o1, State o2) {
				return (o1.hn + o1.gn) - (o2.hn + o2.gn);
			}});
	}
	
	public static void main(String[] args) {
		Search astart = new AStar("bigMaze.txt");
		ArrayList<State> path = astart.search();
		astart.m.drawPath(path);
		astart.m.print();
		System.out.println(astart.exploredCount);
		System.out.println(path.get(0).gn);
	}
}
