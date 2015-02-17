package Part1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

public class Maze3{
	public static char[] marks = "123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	HashSet<Point> dots;
	int width, height;
	State3 start;
	char[][] map;
	public Maze3(String fileName) {
		dots = new HashSet<Point>();
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
			
			init();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void init() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (map[i][j] == 'P') {
					start = new State3(i,j,null,this, true);
				} else if(map[i][j] == '.') {
					dots.add(new Point(i,j));
				}
			}
		}
		start.remain = new HashSet<Point>(dots);
	}
	
	public Maze3(char[][] map) {
		dots = new HashSet<Point>();
		this.map = map;
		height = map.length;
		width = map[0].length;
		init();
	}
	public int width(){
		return width;
	}
	public int height(){
		return height;
	}
	
	public boolean available(int row, int col){
		return row >= 0 && row < height && col >= 0 && col < width && map[row][col] != '%';
	}
	
	public boolean setChar(int row, int col, char c) {
		if (available(row, col)) {
			map[row][col] = c;
			return true;
		}
		return false;
	}
	
	public void drawPath(ArrayList<State3> list) {
		if (list == null) {
			return;
		}
		int count = dots.size();
		for (State3 s : list) {
//			char backup = map[s.row][s.col];
//			setChar(s.row(), s.col(), '*');
//			System.out.println(s.gn);
//			print();
//			setChar(s.row(), s.col(), backup);
			if (s.onDot) {
				if (!setChar(s.row(), s.col(), marks[count--])){
					System.out.println("Something is Wrong! Failed to draw dot at " + s.row() + ":" + s.col());
				}
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
}
