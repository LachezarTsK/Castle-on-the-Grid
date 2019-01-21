import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Solution {

	private static String[][] grid;
	private static int[][] distanceFromStart;

	/**
	 * Applying breadth first search to find the path with least turns from start to
	 * goal.
	 */
	private static int mininumTurns(int startX, int startY, int goalX, int goalY) {
		if (startX == goalX && startY == goalY) {
			return 0;
		}
		distanceFromStart[startX][startY] = 1;
		boolean[][] visited = new boolean[grid.length][grid.length];
		List<Vertex> queue = new LinkedList<Vertex>();
		Vertex currentMove = new Vertex(startX, startY);
		Vertex previousMove = null;
		queue.add(currentMove);

		while (!queue.isEmpty()) {
			previousMove = currentMove;
			currentMove = queue.remove(0);
			if (currentMove.getX() == goalX && currentMove.getY() == goalY) {
				break;
			}
			if (visited[currentMove.getX()][currentMove.getY()] == false) {
				visited[currentMove.getX()][currentMove.getY()] = true;
				/**
				 * Methods movesRight, movesLeft, movesDown, movesUp, search for possible moves
				 * in the respective direction.
				 * 
				 * If there is no turn, the new vertex takes the same value for distance from
				 * the start as the previous vertex. Otherwise the new value is formed by adding
				 * one to the distance from the start for the previous vertex.
				 * 
				 * With breadth first search and counting only the number of turns, when a cell
				 * is explored for the first time, through an edge from the currently visited
				 * cell, the value of the distance from the start for this cell is its minimum
				 * possible value.
				 * 
				 * Therefore, no need to initialize an array for distances from start with
				 * Integer.MAX_VALUE. The algorithm prevents exploring a cell more than once
				 * i.e. when distanceFromStart[i][j]!=0.
				 */
				movesRight(currentMove, previousMove, queue, visited);
				movesLeft(currentMove, previousMove, queue, visited);
				movesUp(currentMove, previousMove, queue, visited);
				movesDown(currentMove, previousMove, queue, visited);
			}
		}
		return distanceFromStart[goalX][goalY];
	}

	/**
	 * @param start_X_prevEdge
	 * @param start_Y_prevEdge
	 *            Coordinates of the start vertex of the previous move.
	 * @param end_X_currentEdge
	 * @param end_Y_currentEdge
	 *            Coordinates of the end vertex of the current move.
	 * 
	 * @return If the current move is a turn, return one. Otherwise, return zero.
	 */
	private static int addTurn(int start_X_prevEdge, int start_Y_prevEdge, int end_X_currentEdge,
			int end_Y_currentEdge) {
		if (start_X_prevEdge != end_X_currentEdge && start_Y_prevEdge != end_Y_currentEdge) {
			return 1;
		}
		return 0;
	}

	private static void movesRight(Vertex current, Vertex previous, List<Vertex> queue, boolean[][] visited) {

		for (int i = current.getY() + 1; i < grid.length; i++) {
			if (grid[current.getX()][i].equalsIgnoreCase("X")) {
				break;
			}
			if (visited[current.getX()][i] == false && distanceFromStart[current.getX()][i] == 0) {

				int newFromStart = addTurn(previous.getX(), previous.getY(), current.getX(), i)
						+ distanceFromStart[current.getX()][current.getY()];
				distanceFromStart[current.getX()][i] = newFromStart;
				queue.add(new Vertex(current.getX(), i));
			}
		}
	}

	private static void movesLeft(Vertex current, Vertex previous, List<Vertex> queue, boolean[][] visited) {
		for (int i = current.getY() - 1; i >= 0; i--) {
			if (grid[current.getX()][i].equalsIgnoreCase("X")) {
				break;
			}
			if (visited[current.getX()][i] == false && distanceFromStart[current.getX()][i] == 0) {

				int newFromStart = addTurn(previous.getX(), previous.getY(), current.getX(), i)
						+ distanceFromStart[current.getX()][current.getY()];
				distanceFromStart[current.getX()][i] = newFromStart;
				queue.add(new Vertex(current.getX(), i));
			}
		}
	}

	private static void movesDown(Vertex current, Vertex previous, List<Vertex> queue, boolean[][] visited) {
		for (int i = current.getX() - 1; i >= 0; i--) {
			if (grid[i][current.getY()].equalsIgnoreCase("X")) {
				break;
			}
			if (visited[i][current.getY()] == false && distanceFromStart[i][current.getY()] == 0) {

				int newFromStart = addTurn(previous.getX(), previous.getY(), i, current.getY())
						+ distanceFromStart[current.getX()][current.getY()];
				distanceFromStart[i][current.getY()] = newFromStart;
				queue.add(new Vertex(i, current.getY()));
			}
		}
	}

	private static void movesUp(Vertex current, Vertex previous, List<Vertex> queue, boolean[][] visited) {
		for (int i = current.getX() + 1; i < grid.length; i++) {
			if (grid[i][current.getY()].equalsIgnoreCase("X")) {
				break;
			}
			if (visited[i][current.getY()] == false && distanceFromStart[i][current.getY()] == 0) {

				int newFromStart = addTurn(previous.getX(), previous.getY(), i, current.getY())
						+ distanceFromStart[current.getX()][current.getY()];
				distanceFromStart[i][current.getY()] = newFromStart;
				queue.add(new Vertex(i, current.getY()));
			}
		}
	}

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		int gridSize = scanner.nextInt();
		grid = new String[gridSize][gridSize];
		distanceFromStart = new int[grid.length][grid.length];

		for (int i = 0; i < gridSize; i++) {
			grid[i] = scanner.next().split("");
		}

		int startX = scanner.nextInt();
		int startY = scanner.nextInt();
		int goalX = scanner.nextInt();
		int goalY = scanner.nextInt();
		scanner.close();

		System.out.println(mininumTurns(startX, startY, goalX, goalY));
	}
}

class Vertex {
	private int x;
	private int y;

	public Vertex(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}
}
