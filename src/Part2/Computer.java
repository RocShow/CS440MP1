package Part2;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;

public class Computer implements Runnable{
	int start, end;
	ArrayList<int[]> list;
	ArrayList<String> ret;
	Lock lock;
	
	public Computer(ArrayList<int[]> list, ArrayList<String> ret, Lock lock, int start, int end) {
		this.list = list;
		this.start = start;
		this.end = end;
		this.ret = ret;
		this.lock = lock;
	}
	
	public void run(){
		for (int index = start; index <= end; index++) {
			int[] array = list.get(index);
			//System.out.println(index + "/" + list.size());
			int[][] board = new int[3][3];
			int k = 0;
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					board[i][j] = array[k++];
				}
			}
			int[][] backup = State.cloneData(board);
			Puzzle p = new Puzzle(board);
			//p.start.print();
			State s = p.search();
			if (s != null) {
				//System.out.println(s.cost);
				StringBuilder sb = new StringBuilder();
				sb.append("{");
				for (int i = 0; i < 3; i++) {
					sb.append("{");
					for (int j = 0; j < 3; j++) {
						sb.append(backup[i][j]);
						if (j != 2) {
							sb.append(",");
						}
					}
					sb.append("}");
					if (i != 2) {
						sb.append(",");
					}
				}
				sb.append("},");
				
				lock.lock();
				sb.append(" " + ret.size());
				sb.append(" " + s.cost);
				sb.append(" " + p.exploredCount);
				//System.out.println("Generate cost " + s.cost);
				System.out.println(sb.toString());
				
				ret.add(sb.toString());
				lock.unlock();
			}
		}
	}
}
