package Part1;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Maze {
	int width, height;
	State start;
	State goal;
	char[][] map;
	
	public Maze(String fileName) {
		ArrayList<char[]> buffer = new ArrayList<char[]>();
		FileInputStream fis;
		BufferedReader br;
		try {
			fis = new FileInputStream(fileName);
			br = new BufferedReader(new InputStreamReader(fis));
			String line = null;
			while ((line = br.readLine()) != null) {
				buffer.add(line.toCharArray());
			}
			br.close();
			
			//initialize map
			height = buffer.size();
			width = buffer.get(0).length;
			map = new char[height][width];
			buffer.toArray(map);
			
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					if (map[i][j] == 'P') {
						start = new State(i,j,null);
					} else if(map[i][j] == '.') {
						goal = new State(i,j,null);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int width(){
		return width;
	}
	public int height(){
		return height;
	}
	public State goal(){
		return goal;
	}
	
	public boolean available(int row, int col){
		return row >= 0 && row < height && col >= 0 && col < width && map[row][col] != '%';
	}
	
	public boolean setDot(int row, int col) {
		if (available(row, col)) {
			map[row][col] = '.';
			return true;
		}
		return false;
	}
	
	public void drawPath(State s){
		while(s != null) {
			if (!setDot(s.row(), s.col())){
				System.out.println("Something is Wrong! Failed to draw dot at " + s.row() + ":" + s.col());
			}
		}
	}
	
	public void drawPath(ArrayList<State> list) {
		if (list == null) {
			return;
		}
		for (State s : list) {
			if (!setDot(s.row(), s.col())){
				System.out.println("Something is Wrong! Failed to draw dot at " + s.row() + ":" + s.col());
			}
		}
	}
	public void print(){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < map.length; i++){
			for (int j = 0; j < map[i].length; j++) {
				sb.append(map[i][j]);
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}
	
	public static void main(String[] args) {
		Maze m = new Maze("bigMaze.txt");
		m.print();
	}
}
