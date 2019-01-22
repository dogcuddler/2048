package Basics;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import Basics.Game;

public class board {


	//Repeats userMove until the board is full
		public static void runGame (int[][] board) {
			
			int count = 0;
			Scanner sc = new Scanner(System.in);
			while (count == 0) {
				if (playable(board)) {
					String scan = sc.nextLine();
					if (scan.contains("w")){
						upMove(board);
					}
					else if (scan.contains("a")) {
						leftMove(board);
					}
					else if (scan.contains("s")) {
						downMove(board);
					}
					else if (scan.contains("d")) {
						rightMove(board);
					}
					else {
						System.out.println("Use only a lowercase w,a,s, or d");
					}
				}
				else {
				System.out.println("Game Over");
				System.out.println("Your final board was: ");
				printBoard(board);
				count += 1;
				sc.close();
				}
			}
		}

	// keeps track of score
	public static int score = 0;

	// returns and prints board
	public static int[][] setup() {
		int[][] board = new int[][] {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
		
		newBlock(board);
		return board;
	}
	
	
	// prints board in a visible way to user, includes spacing to line up blocks. Prints score
	public static void printBoard(int[][] board) {
		System.out.println("Score: " + score);
		int[][] newBoard = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
		int newRow = 0;
		int newColumn = 0;
		for (int x = 0; x < 16; x += 1) {
			newBoard[newRow][newColumn] = board[newColumn][newRow];
			newColumn += 1;
			if (newColumn == 4) {
				newColumn -= 4;
				newRow += 1;
			}
		}
		
		int n = 0;
		for (int[] row : board) {
			for (int unit : row) {
				
				System.out.print(spacing(unit,highNum(newBoard[n])));
				n += 1;
				if (n == 4) {
					n -= 4;					
				}
			}
			System.out.println("");
			
		}
		System.out.println("");
	}
	
	
	// returns string containing unit and spaces based on the size of int digits
	public static String spacing (int unit, int digits) {
		String brackets = "";
		int spacing = digits - digit(unit);
		if (spacing == -1) {
			brackets = "oops";
		}
		if (spacing == 0) {
			brackets = "[" + unit + "]";
		}
		else if (spacing == 1) {
			brackets = "[" + unit + " ]";
		}
		else if (spacing == 2) {
			brackets = "[ " + unit + " ]";
		}
		else if (spacing == 3) {
			brackets = "[ " + unit + "  ]";
		}
		else if (spacing == 4) {
			brackets = "[  " + unit + "  ]";
		}
		else if (spacing == 5) {
			brackets = "[  " + unit + "   ]";
		}
		return brackets;
	}
	
	
	// returns the # of digits in a number
	public static int digit (int unit) {
		int value = 1;
			while (unit > 9) {
				value += 1;
				unit /= 10;
			}
			return value;
	}
	
	
	// returns the # digits of the highest number in a row
	public static int highNum (int[] row) {
		int highest = 0;
		for (int unit : row) {
			if (unit > highest) {
				highest = unit;
			}
		}
		return digit(highest);
	}
	
	
	// returns # of empty slots
	public static int denominator(int[][] board) {
		int denom = 0;
		for (int[] row : board) {
			for (int block : row) {
				if (block == 0) {
					denom += 1;
				}
			}
		}
		return (denom);
	}
	
	
	// returns a new block value, 2 or 4
	public static int newValue() {
		Random rand = new Random();
		
		int n = rand.nextInt(10) + 1;
		
		if (n == 1) {
			return(4);
		}
		else {
			return(2);
		}
	}
	
	
	// puts a new block in a random location
	public static void newBlock(int[][] board) {
		Random rand = new Random();
		
		int value = newValue();
		int denom = denominator(board);
		int newSpot = rand.nextInt(denom) + 1;
		int counter = 1;
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		
		for (int[] row : board) { 
			for (int unit : row) {
				map.put(counter, unit);
				list.add(counter);
				counter += 1;
			}
		}
			
		ArrayList<Integer> options = new ArrayList<Integer>();
		for (int unit : list) {
			if (map.get(unit) == 0) {
				options.add(unit);
			}
		}
		int location = options.get(newSpot - 1);
		int column = (location - 1) % 4;
		int line = (location - 1) / 4;
		(board[line][column]) = value;
		
}
	
	
	// returns true if row is occupied
	public static boolean rowOccupied(int[] row) {
		int counter = 0;
		for (int unit : row) {
			if (unit != 0){
				counter += 1;
			}
		}
		if (counter != 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	//shifts board to the left
	public static void left (int[][] board) {
		for (int[] row : board) {
			if (rowOccupied(row)) {
				int zero = row[0];
				int one = row[1];
				int two = row[2];
				int three = row[3];
				if (zero == 0) {
					if (one == 0) {
						if (two == 0) {
							row[0] = row[3];
							row[3] = 0;
						}
						// if zero and one are empty and two is occupied
						else {
							if (three == 0) {
								row[0] = row[2];
								row[2] = 0;
							}
							else {
								if (three == two) {
									row[0] = row[2]*2;
									row[2] = 0;
									row[3] = 0;
									score += row[0];
								}
								else {
									row[0] = row[2];
									row[1] = row[3];
									row[2] = 0;
									row[3] = 0;
								}
							}
						}
					}
					// for if zero is empty and one is occupied
					else {
						if (two == 0) {
							if (three == 0) {
								row[0] = row[1];
								row[1] = 0;
							}
							else {
								if (one == three) {
									row[0] = row[1]*2;
									row[1] = 0;
									row[3] = 0;
									score += row[0];
								}
								else {
									row[0] = row[1];
									row[1] = row[3];
									row[3] = 0;
								}
							}
						}
						else if(three == 0) {
							if (one == two) {
								row[0] = row[1]*2;
								row[1] = 0;
								row[2] = 0;
								score += row[0];
							}
							else {
								row[0] = row[1];
								row[1] = row[2];
								row[2] = 0;
							}
						}
						// for if zero is empty and 1,2,3 are occupied
						else {
							if (one == two) {
								row[0] = row[1]*2;
								row[1] = row[3];
								row[2] = 0;
								row[3] = 0;
								score += row[0];
							}
							else if (two == three) {
								row[0] = row[1];
								row[1] = row[2]*2;
								row[2] = 0;
								row[3] = 0;
								score += row[1];
							}
							else {
								row[0] = row[1];
								row[1] = row[2];
								row[2] = row[3];
								row[3] = 0;
							}
						}
					}
				}
				// for if zero is occupied
				else {
					if (one == 0) {
						if (two == 0) {
							if (three == zero) {
								row[0] = row[3]*2;
								row[3] = 0;
								score += row[0];
							}
							else if (three != 0) {
								row[1] = row[3];
								row[3] = 0;
							}
						}
						// for if zero and two are occupied, one is empty
						else {
							if (three == 0) {
								if (zero == two) {
									row[0] = row[2]*2;
									row[2] = 0;
									score += row[0];
								}
								else {
									row[1] = row[2];
									row[2] = 0;
								}
							}
							// for if 0,2,3 are occupied, 1 is empty
							else {
								if(zero == two) {
									row[0] = row[2]*2;
									row[1] = row[3];
									row[3] = 0;
									row[2] = 0;
									score += row[0];
								}
								else if(two == three) {
									row[1] = row[2]*2;
									row[2] = 0;
									row[3] = 0;
									score += row[1];
								}
								else {
									row[1] = row[2];
									row[2] = row[3];
									row[3] = 0;
								}
							}
						}
					}
					// for if zero and one are occupied
					else {
						if (two == 0) {
							if (three == 0) {
								if (zero == one) {
									row[0] *= 2;
									row[1] = 0;
									score += row[0];
								}
							}
							// for if 0,1,3 are occupied, 2 is empty
							else {
								if (zero == one) {
									row[0] = row[1]*2;
									row[1] = row[3];
									row[3] = 0;
									score += row[0];
								}
								else if (one == three) {
									row[1] = row[3]*2;
									row[3] = 0;
									score += row[1];
								}
								else {
									row[2] = row[3];
									row[3] = 0;
								}
							}
						}
						// for if 0,1,2 are occupied
						else {
							if (three == 0) {
								if (zero == one) {
									row[0] *= 2;
									row[1] = row[2];
									row[2] = 0;
									score += row[0];
								}
								else if (one == two) {
									row[1] *= 2;
									row[2] = 0;
									score += row[1];
								}
							}
							else {
								if (zero == one) {
									if (two == three) {
										row[0] *= 2;
										row[1] = row[2]*2;
										row[2] = 0;
										row[3] = 0;
										score += row[0] + row[1];
										
									}
									else {
										row[0] *= 2;
										row[1] = row[2];
										row[2] = row[3];
										row[3] = 0;
										score += row[0];
									}
								}
								else if (one == two) {
									row[1] *= 2;
									row[2] = row[3];
									row[3] = 0;
									score += row[1];
								}
								else if (two == three) {
									row[2] *= 2;
									row[3] = 0;
									score += row[2];
								}
							}
						}
					}
				}
			}
		}
	}
	
	
	//if able, executes left, places new block and prints
	public static void leftMove (int[][] board) {
		if (move(board)) {
			left(board);
			newBlock(board);
		}
		printBoard(board);
	}
	
	
	//shifts board to the right
	public static void right (int[][] board) {
		int[][] newBoard = rightBoard(board);
		left(newBoard);
		int row = 0;
		for (int[] rows : newBoard) {
			int block = 3;
			for (int unit : rows) {
				board[row][block] = unit;
				block -= 1;
			}
			row += 1;
		}
	}
	
	
	//if able, executes right, places new block and prints
	public static void rightMove (int[][] board) {
		int [][] newBoard = rightBoard(board);
		if (move(newBoard)) {
			right(board);
			newBlock(board);
		}
		printBoard(board);
	}
	
	
	//shifts board up
	public static void up (int[][] board) {
		int[][] newBoard = upBoard(board);
		left(newBoard);
		int newColumn = 0;
		for (int[] row : newBoard) {
			int newRow = 0;
			for (int unit : row) {
				board[newRow][newColumn] = unit;
				newRow += 1;
				newRow %= 4;
			}
			newColumn += 1;
		}
	}
	
	
	//if able, executes up, places new block and prints
	public static void upMove(int[][] board) {
		int[][] newBoard = upBoard(board);
		if (move(newBoard)) {
			up(board);
			newBlock(board);
		}
		printBoard(board);
	}
	
	
	//shifts board down
	public static void down(int[][] board) {
		int[][] newBoard = downBoard(board);
		left(newBoard);
		int newColumn = 3;
		for (int[] row : newBoard) {
			int newRow = 3;
			for (int unit : row) {
				board[newRow][newColumn] = unit;
				newRow -= 1;
			}
			newColumn -= 1;
		}
	}
	
	
	//if able, executes down, places new block and prints
	public static void downMove (int[][] board) {
		int[][] newBoard = downBoard(board);
		if(move(newBoard)) {
			down(board);
		newBlock(board);
		}		
		printBoard(board);
	}
	
	
	//return up board ready for leftMove to be executed
	public static int[][] upBoard(int[][] board) {
		int[][] upBoard = new int[][] {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
		int upColumn = 0;
		for (int[] row : board) {
			int newRow = 0;
			for (int unit : row) {
				upBoard[newRow][upColumn] = unit;
				newRow += 1;
				newRow %= 4;
			}
			upColumn += 1;
		}	
		return upBoard;
	}
	
	
	//returns right board ready for leftMove to be executed
	public static int[][] rightBoard(int[][] board){
		int[][] rightBoard = new int[][] {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
		int row = 0;
		for (int[] rows : board) {
			int block = 3;
			for (int unit : rows) {
				rightBoard[row][block] = unit;
				block -= 1;
			}
			row += 1;
		}
		return rightBoard;
	}
	
	
	//returns down board ready for leftMove to be executed
	public static int[][] downBoard(int[][] board){
		int[][] downBoard = new int[][] {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
		int downColumn = 3;
		for (int[] row : board) {
			int newRow = 3;
			for (int unit : row) {
				downBoard[newRow][downColumn] = unit;
				newRow -= 1;
			}
			downColumn -= 1;
		}
		return downBoard;
	}
	
	
	//sees if move is necessary
	public static boolean move (int[][] board) {
		int counter = 0;
		for (int[] row : board) {
			if (rowOccupied(row)) {
				if (row[0] == 0) {
					counter += 1;
				}
				else if (row[1] == 0) {
					if (row[2] != 0 || row[3] != 0) {
						counter += 1;
					}
				}
				else if (row[0] == row[1]) {
					counter += 1;
				}
				else if (row[2] == 0) {
					if (row[3] != 0) {
						counter += 1;
					}
				}
				else if (row[1] == row[2] || row[2] == row[3]) {
					counter += 1;
				}
			}
		}
		if (counter != 0) {
			return true;
		}
		return false;
	}
	
	
	//sees if there is a possible move on the board
	public static boolean playable (int[][] board) {
		if (move(board) || move(rightBoard(board)) || move(upBoard(board)) || move(downBoard(board))){
			return true;
		}
		return false;
	}
	
	private static int[][] x = setup();
	
	class Adapter extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			if (!playable(x)) {
				return;
			}

			int keyCode = e.getKeyCode();

			if (keyCode == 'p' || keyCode == 'P') {
				printBoard(x);
			}

			

			switch (keyCode) {
			case KeyEvent.VK_A:
				leftMove(x);
				break;
			case KeyEvent.VK_D:
				rightMove(x);
				break;
			case KeyEvent.VK_S:
				downMove(x);
				break;
			case KeyEvent.VK_W:
				upMove(x);
				break;
			
			}
		}
	}
		
		
}

