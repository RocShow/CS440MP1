package Part1;
import java.util.ArrayList;


public class Q11 {
	public static void main(String[] args) {
		String[] files = {"bigMaze.txt","mediumMaze.txt","smallMaze.txt"};
		for (int i = 0; i < files.length; i++) {
			System.out.println("File:" + files[i]);
			//dfs
			System.out.println("DFS:");
			Search dfs = new DFS(files[i]);
			ArrayList<State> path = dfs.search();
			dfs.m.drawPath(path);
			dfs.m.print();
			System.out.println(path.get(0).gn);
			System.out.println(dfs.exploredCount);
			
			//bfs
			System.out.println("BFS:");
			Search bfs = new BFS(files[i]);
			path = bfs.search();
			bfs.m.drawPath(path);
			bfs.m.print();
			System.out.println(path.get(0).gn);
			System.out.println(bfs.exploredCount);
			
			//greedy
			System.out.println("Greddy:");
			Search greedy = new Greedy(files[i]);
			path = greedy.search();
			greedy.m.drawPath(path);
			greedy.m.print();
			System.out.println(path.get(0).gn);
			System.out.println(greedy.exploredCount);
			
			//Astar
			System.out.println("AStar:");
			Search astar = new AStar(files[i]);
			path = astar.search();
			astar.m.drawPath(path);
			astar.m.print();
			System.out.println(path.get(0).gn);
			System.out.println(astar.exploredCount);
			
			System.out.println("*****************************************");
		}
		
	
	}
	
}