package Part1;
import java.util.ArrayList;


public class Q12 {
	public static void main(String[] args) {
		//easy for greedy
		System.out.println("Easy For Greedy:");
		System.out.println("Greedy");
		Search greedy = new Greedy("easyForGreedy.txt");
		ArrayList<State> path = greedy.search();
		greedy.m.drawPath(path);
		greedy.m.print();
		System.out.println("Path Length: " + path.get(0).gn);
		System.out.println("Explored: " + greedy.exploredCount);
		
		System.out.println("AStar");
		Search astar = new AStar("easyForGreedy.txt");
		path = astar.search();
		astar.m.drawPath(path);
		astar.m.print();
		System.out.println("Path Length: " + path.get(0).gn);
		System.out.println("Explored: " + astar.exploredCount);
		
		//easy for AStar
		System.out.println("****************************************");
		System.out.println("Easy For AStar:");
		System.out.println("Greedy");
		greedy = new Greedy("easyForAStar.txt");
		path = greedy.search();
		greedy.m.drawPath(path);
		greedy.m.print();
		System.out.println("Path Length: " + path.get(0).gn);
		System.out.println("Explored: " + greedy.exploredCount);
		
		System.out.println("AStar");
		astar = new AStar("easyForAStar.txt");
		path = astar.search();
		astar.m.drawPath(path);
		astar.m.print();
		System.out.println("Path Length: " + path.get(0).gn);
		System.out.println("Explored: " + astar.exploredCount);
	}
}
