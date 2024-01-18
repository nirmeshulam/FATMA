package ex1;
//public static void main(String[] args) {

//	// while True
//	// ==== check if user wants new game
//	// 1. start game question: 
//	// get input (can we input a str?)
//	// valid check: is a number? is 0/1?
//	// if 0 - exit
//	// (else) - continue to game 
//	// ===== play single game
//	
//
//}
//private static void PlaySingleGame() {
//	// ==== String[][] board = board_init
//	// while True
//	// 1. === end_string = UserTurn( String[][] board)
//	// check for end possibility from end_string
//	// 2. === ComputerTurn( String[][] board)
//	
//}
//
//private static String UserTurn( String[][] board) {
//	// TODO check tie
//	// ==== get input
//	// ==== input validation: is "STOP" or pattern xx-xx (1<=x<=8)
//	// ==== is move valid:
//	// 1. is start position with user soldier
//	// 2. is end position free (=="*")
//	// 3. check if queen or regular soldier
//	// ==== regular user possibilities:
//	// - is move directly in immidiate diagonal (regular move)
//	// - is move two place diagonal with pc soldier in the middle (eat move)
//	// -- remove soldier
//	// - else: NOT VALID- try again
//	// ==== queen possibilities:
//	// - same as regular soldier but with possibility to move back
//	
//	// 4. ===== multiEat(board, soldier/queen place)
//	// check if we can eat more (4 directions)
//	// - automatically eat more if possible (while True)
//	// TODO check if automate or manual multi-eat
//
//	// 5. ==== end of turn check:
//	// is win
//	// is queen
//	
//	
//}
//private static String ComputerTurn( String[][] board) {
//	// 1. ==== is_eat = checkEat( String[][] board) 
//	// do this randomly
//	// inside you have multiEat 
//	// 2. ==== is_move = checkMove( String[][] board) 
//	//  do this randomly
//	//    2.a 30-70 random.
//	// 3. else: TIE

import java.util.*;

public class ex1 {
	static Scanner sc = new Scanner(System.in);

	//
	public static void main(String[] args) {
		// the first menu. checking if the user wants to play.
		boolean UserWant = true;
		while (UserWant == true) {
			System.out.println("Welcome to Fatma Checkers. To start the game press 1, to exit press 0:");
			int user_choice = sc.nextInt();
			if (user_choice == 1)
				PlaySingleGame();
			else if (user_choice == 0) {
				UserWant = false;
				System.out.println("Goodbye!");
			} else
				System.out.println("The input is not valid, To start the game press 1, to exit press 0:");

		}
	//	System.out.println(testIsUserEatingValid4());
	}

	// starting the first board.
	public static String[][] BoardInit() {
		String[][] board = new String[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((i + j) % 2 == 1) {
					if (i < 3)
						board[i][j] = "W";
					else if (i > 4)
						board[i][j] = "R";
					else
						board[i][j] = "*";
				} else {
					board[i][j] = "-";
				}
			}
		}
		return board;
	}

	// print board.
	public static void PrintBoard(String[][] board) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				System.out.print(board[i][j] + "	");
			}
			System.out.println();
		}
		System.out.println();
	}

	//
	public static void PlaySingleGame() {
		// Initial the board
		String[][] board = BoardInit();
		// user turn
		while (true) {
			boolean IsGameOverUserSide = UserTurn(board);
			if (IsGameOverUserSide) {
				break;
			}
			PrintBoard(board);
			boolean IsGameOverComputerSide = ComputerTurn(board);
			PrintBoard(board);
			if (IsGameOverComputerSide) {
				break;
			}
		}
	}

	//
	public static boolean UserTurn(String[][] board) {
		// return is_game_over

		// ===== get input
		String user_input;
		while (true) {
			PrintBoard(board);
			System.out.println("It's your turn, please enter your move:");
			user_input = sc.next();
			if (user_input.equals("STOP")) {
				System.out.println("Sorry, computer has won :( ");
				return true;
			}
			// to fix if the pattern is ok but the move isnt.
			if (IsValidPatternInput(user_input))
				break;
			System.out.println("The input is not valid, please enter your move again.");

		}

		int[][] translated_input = TranslateUserInput(user_input);

		// check what is the soldier - "R" or "Q-R"
		// if "R"
		if (board[translated_input[1][0]][translated_input[1][1]] == "R") {
			// if IsREatingValid
			if (IsUserEatingValid(translated_input, board)) {
				Eating(translated_input, board);
				// MultiEat(translated_input, board);
				int countW = 0;
				// check if there's W's soldiers on the board
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						// if yes - count
						if (board[i][j] == "W" || board[i][j] == "Q-W") {
							countW++;
						}
					}
				}
				if (countW == 0) {
					System.out.println("Congratulations, user has won :)");
					return true;
				}

			} else if (IsMoveValidRSoldier(translated_input, board)) {
				// else if IsRMoveValid
				board = Move(translated_input, board);
			} else {
				System.out.println("This move is invalid, please enter a new move.");
			}
			// else input wrong
		}

		// if "Q-R"
		if (board[translated_input[1][0]][translated_input[1][1]] == "Q-R") {
			// if IsREatingValid
			if (IsQueenCanEat(translated_input, board)) {
				Eating(translated_input, board);
				int countW = 0;
				// check if there's W's soldiers on the board
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						// if yes - count
						if (board[i][j] == "W" || board[i][j] == "Q-W") {
							countW++;
						}
					}
				}
				if (countW == 0) {
					System.out.println("Congratulations, user has won :)");
					return true;
				}

			} else if (IsMoveValidR_Queen(translated_input, board)) {
				// else if IsRMoveValid
				board = Move(translated_input, board);
			} else {
				System.out.println("The input is not valid, please enter your move again.");
			}
			// else input wrong
		}
		// if IsQueenCanEat
		// else if IsRMoveValid
		// else input wrong

		return false;

	}

	//
	public static boolean IsValidPatternInput(String user_input) {
		if (user_input.length() != 5)
			return false;
		if (IsInRangeDigit(user_input.charAt(0)) && IsInRangeDigit(user_input.charAt(1))
				&& IsInRangeDigit(user_input.charAt(3)) && IsInRangeDigit(user_input.charAt(4))
				&& (user_input.charAt(2) == '-'))
			return true;
		else
			return false;
	}

	//
	public static boolean IsInRangeDigit(char c) {
		if (('1' <= c) && (c <= '8')) {
			return true;
		}
		return false;
	}

	// translate the user input from String to int and if the user write "11-22" he
	// means "70-61"
	public static int[][] TranslateUserInput(String user_input) {
		int[][] transleted_input = new int[2][2];
		transleted_input[0][0] = 8 - Character.getNumericValue(user_input.charAt(0));
		transleted_input[0][1] = Character.getNumericValue(user_input.charAt(1)) - 1;
		transleted_input[1][0] = 8 - Character.getNumericValue(user_input.charAt(3));
		transleted_input[1][1] = Character.getNumericValue(user_input.charAt(4)) - 1;
		return transleted_input;
	}

	//
	public static boolean IsMoveValidRSoldier(int[][] transleted_input, String[][] board) {
		if (// start is R
		(board[transleted_input[1][0]][transleted_input[1][1]] == "R")
				// end is *
				&& (board[transleted_input[0][0]][transleted_input[0][1]] == "*")
				// y is one step up
				&& (Math.abs(transleted_input[1][1] - transleted_input[0][1]) == 1)
				// x is one step left OR right
				&& (/*
					 * (transleted_input[0][0] - transleted_input[1][0] == -1) ||
					 */ (transleted_input[0][0] - transleted_input[1][0] == -1))) {
			return true;
		}

		return false;
	}

	// check if the queen can move
	public static boolean IsMoveValidR_Queen(int[][] transleted_input, String[][] board) {
		if (// start is Q-W
		(board[transleted_input[1][0]][transleted_input[1][1]] == "Q-W")
				// end is *
				&& (board[transleted_input[0][0]][transleted_input[0][1]] == "*")
				// y is one step up OR down
				&& ((transleted_input[0][1] - transleted_input[1][1] == -1)
						|| (transleted_input[0][1] - transleted_input[1][1] == 1))
				// x is one step left OR right
				&& ((transleted_input[0][0] - transleted_input[1][0] == -1)
						|| (transleted_input[0][0] - transleted_input[1][0] == 1))) {
			return true;
		}

		if (// start is Q-R
		(board[transleted_input[1][0]][transleted_input[1][1]] == "Q-R")
				// end is *
				&& (board[transleted_input[0][0]][transleted_input[0][1]] == "*")
				// y is one step up OR down
				&& ((transleted_input[0][1] - transleted_input[1][1] == -1)
						|| (transleted_input[0][1] - transleted_input[1][1] == 1))
				// x is one step left OR right
				&& ((transleted_input[0][0] - transleted_input[1][0] == -1)
						|| (transleted_input[0][0] - transleted_input[1][0] == 1))) {
			return true;
		}

		return false;
	}

	public static boolean IsQueenCanEat(int[][] transleted_input, String[][] board) {
		// check on Q-R
		if ((board[transleted_input[1][0]][transleted_input[1][1]] == "Q-R")) {
			// end is '*'
			if ((board[transleted_input[0][0]][transleted_input[0][1]] == "*")
					// is there 'W' between the 'Q-R' and the '*'
					&& ((board[(transleted_input[0][0] + transleted_input[1][0])
							/ 2][(transleted_input[0][1] + transleted_input[1][1]) / 2] == "W")
							// || (board[transleted_input[0][0] + 1][transleted_input[0][1] - 1] == "W")
							// || (board[transleted_input[0][0] - 1][transleted_input[0][1] - 1] == "W")
							// || (board[transleted_input[0][0] - 1][transleted_input[0][1] + 1] == "W")
							// is there 'Q-W' between the 'Q-R' and the '*'
							|| (board[(transleted_input[0][0] + transleted_input[1][0])
									/ 2][(transleted_input[0][1] + transleted_input[1][1]) / 2] == "Q-W"))
					/*
					 * || (board[transleted_input[0][0] + 1][transleted_input[0][1] + 1] == "Q-W")
					 * || (board[transleted_input[0][0] + 1][transleted_input[0][1] - 1] == "Q-W")
					 * || (board[transleted_input[0][0] - 1][transleted_input[0][1] - 1] == "Q-W")
					 * || (board[transleted_input[0][0] - 1][transleted_input[0][1] + 1] == "Q-W")
					 */
					// x is two step up
					&& (transleted_input[0][0] - transleted_input[1][0] == 2)
					|| (transleted_input[0][0] - transleted_input[1][0] == -2)
							// y is two step left OR right
							&& ((transleted_input[0][1] - transleted_input[1][1] == -2)
									|| (transleted_input[0][1] - transleted_input[1][1] == 2))) {
				return true;
			}
		}
		// check on Q-W
		if (board[transleted_input[1][0]][transleted_input[1][1]] == "Q-W") {
			// end is '*'
			if ((board[transleted_input[0][0]][transleted_input[0][1]] == "*")
					// is there 'W' between the 'Q-R' and the '*'
					&& ((board[(transleted_input[0][0] + transleted_input[1][0])
							/ 2][(transleted_input[0][1] + transleted_input[1][1]) / 2] == "R")
							|| (board[(transleted_input[0][0] + transleted_input[1][0])
									/ 2][(transleted_input[0][1] + transleted_input[1][1]) / 2] == "Q-R"))
					/*
					 * (board[transleted_input[0][0] + 1][transleted_input[0][1] + 1] == "R") ||
					 * (board[transleted_input[0][0] + 1][transleted_input[0][1] - 1] == "R") ||
					 * (board[transleted_input[0][0] - 1][transleted_input[0][1] - 1] == "R") ||
					 * (board[transleted_input[0][0] - 1][transleted_input[0][1] + 1] == "R") // is
					 * there 'Q-W' between the 'Q-R' and the '*' || (board[transleted_input[0][0] +
					 * 1][transleted_input[0][1] + 1] == "Q-R") || (board[transleted_input[0][0] +
					 * 1][transleted_input[0][1] - 1] == "Q-R") || (board[transleted_input[0][0] -
					 * 1][transleted_input[0][1] - 1] == "Q-R") || (board[transleted_input[0][0] -
					 * 1][transleted_input[0][1] + 1] == "Q-R")
					 */
					// x is two step up
					&& (transleted_input[0][0] - transleted_input[1][0] == 2)
					|| (transleted_input[0][0] - transleted_input[1][0] == -2)
							// y is two step left OR right
							&& ((transleted_input[0][1] - transleted_input[1][1] == -2)
									|| (transleted_input[0][1] - transleted_input[1][1] == 2))) {
				return true;
			}
		}
		return false;
	}

	// function that work after the first eat of the turn that check if can eat more
	/*
	 * public static void MultiEat(int[][] transleted_input, String[][] board) { //
	 * computer turn // START OF STOPPING CONDITION if
	 * ((board[transleted_input[1][0]][transleted_input[1][1]] == "R" ||
	 * board[transleted_input[1][0]][transleted_input[1][1]] == "Q-R") &&
	 * (IsUserEatingValid(transleted_input, board) ||
	 * IsQueenCanEat(transleted_input, board))) { Eating(transleted_input, board);
	 * return; } else { if ((board[transleted_input[1][0]][transleted_input[1][1]]
	 * == "W" || board[transleted_input[1][0]][transleted_input[1][1]] == "Q-W") &&
	 * (IsComputerEatingValid(transleted_input[1][0], transleted_input[1][1],
	 * board)[0][0] != -1 || IsQueenCanEat(transleted_input, board))) {
	 * Eating(transleted_input, board); return; } } // END OF STOPPING CONDITION
	 * 
	 * // Update coordinates - move the end coordinate to be the start coordinate
	 * transleted_input[1][0] = transleted_input[0][0]; transleted_input[1][1] =
	 * transleted_input[0][1];
	 * 
	 * // make an array for each option Left/Right and Down/Up int[][] leftDown = {
	 * { transleted_input[1][0] + 1, transleted_input[1][1] - 1 }, {
	 * transleted_input[1][0], transleted_input[1][1] } }; int[][] leftUp = { {
	 * transleted_input[1][0] - 1, transleted_input[1][1] - 1 }, {
	 * transleted_input[1][0], transleted_input[1][1] } }; int[][] rightDown = { {
	 * transleted_input[1][0] + 1, transleted_input[1][1] + 1 }, {
	 * transleted_input[1][0], transleted_input[1][1] } }; int[][] rightUp = { {
	 * transleted_input[1][0] - 1, transleted_input[1][1] + 1 }, {
	 * transleted_input[1][0], transleted_input[1][1] } };
	 * 
	 * // check the options - call to the function MultiEat(leftDown, board);
	 * MultiEat(leftUp, board); MultiEat(rightDown, board); MultiEat(rightUp,
	 * board); return;
	 * 
	 * // check if the computer can eat more // check to the right side // if yes -
	 * check to the left side if he can eat // if not - eat to the right side // if
	 * yes - choice 30-70 where to eat // if not - check to the left side if he can
	 * eat // if yes - eat to the left side // if not - stop the turn
	 * 
	 * // user turn // check if the user can eat more // if he can to the right or
	 * to the left give the user another turn // if not - stop the turn }
	 */

	// eating function
	public static void Eating(int[][] transleted_input, String[][] board) {
		// save the value of the starting location
		String value = board[transleted_input[1][0]][transleted_input[1][1]];
		// put the value of the starting location in the ending location
		board[transleted_input[0][0]][transleted_input[0][1]] = value;
		// change the starting location to '*'
		board[transleted_input[1][0]][transleted_input[1][1]] = "*";
		// change the middle location to '*'
		board[(transleted_input[0][0] + transleted_input[1][0]) / 2][(transleted_input[0][1] + transleted_input[1][1])/ 2] = "*";

		// Update coordinates - move the end coordinate to be the start coordinate
		transleted_input[1][0] = transleted_input[0][0];
		transleted_input[1][1] = transleted_input[0][1];
		// make an array for each option Left/Right and Down/Up
		int[][] leftDown = { { transleted_input[1][0] + 2, transleted_input[1][1] - 2 },
				{ transleted_input[1][0], transleted_input[1][1] } };
		int[][] leftUp = { { transleted_input[1][0] - 2, transleted_input[1][1] - 2 },
				{ transleted_input[1][0], transleted_input[1][1] } };
		int[][] rightDown = { { transleted_input[1][0] + 2, transleted_input[1][1] + 2 },
				{ transleted_input[1][0], transleted_input[1][1] } };
		int[][] rightUp = { { transleted_input[1][0] - 2, transleted_input[1][1] + 2 },
				{ transleted_input[1][0], transleted_input[1][1] } };

		if (board[transleted_input[1][0]][transleted_input[1][1]] == "R"
				|| board[transleted_input[1][0]][transleted_input[1][1]] == "Q-R") {
			if (IsUserEatingValid(leftDown, board) || IsQueenCanEat(leftDown, board) || IsUserEatingValid(leftUp, board)
					|| IsQueenCanEat(leftUp, board) || IsUserEatingValid(rightDown, board)
					|| IsQueenCanEat(rightDown, board) || IsUserEatingValid(rightUp, board)
					|| IsQueenCanEat(rightUp, board)) {
				PrintBoard(board);
				System.out.println("write the new destinition coordinate to eat the same pawn");
				String user_input = sc.next();
				transleted_input[0][0] = 8 - Character.getNumericValue(user_input.charAt(0));
				transleted_input[0][1] = Character.getNumericValue(user_input.charAt(1)) - 1;
				Eating(transleted_input, board);
			} else if (board[transleted_input[1][0]][transleted_input[1][1]] == "W"
					|| board[transleted_input[1][0]][transleted_input[1][1]] == "Q-W") {
				if (IsComputerEatingValid(leftDown[1][0], leftDown[1][1], board)[0][0] != -1
						|| IsQueenCanEat(leftDown, board)) {
					transleted_input[0][0] = FinishPlaceComputer(leftDown, board)[0][0];
					transleted_input[0][1] = FinishPlaceComputer(leftDown, board)[0][1];
					Eating(transleted_input, board);

				} else if (IsComputerEatingValid(leftUp[1][0], leftUp[1][1], board)[0][0] != -1
						|| IsQueenCanEat(leftUp, board)) {
					transleted_input[0][0] = FinishPlaceComputer(leftUp, board)[0][0];
					transleted_input[0][1] = FinishPlaceComputer(leftUp, board)[0][1];
					Eating(transleted_input, board);
				} else if (IsComputerEatingValid(rightDown[1][0], rightDown[1][1], board)[0][0] != -1
						|| IsQueenCanEat(rightDown, board)) {
					transleted_input[0][0] = FinishPlaceComputer(rightDown, board)[0][0];
					transleted_input[0][1] = FinishPlaceComputer(rightDown, board)[0][1];
					Eating(transleted_input, board);
				} else if (IsComputerEatingValid(rightUp[1][0], rightUp[1][1], board)[0][0] != -1
						|| IsQueenCanEat(rightUp, board)) {
					transleted_input[0][0] = FinishPlaceComputer(rightUp, board)[0][0];
					transleted_input[0][1] = FinishPlaceComputer(rightUp, board)[0][1];
					Eating(transleted_input, board);
				}
			} else {

				return;
			}
		}
	}

	public static String[][] Move(int[][] transleted_input, String[][] board) {
		// save the value of the starting location
		String value = board[transleted_input[1][0]][transleted_input[1][1]];
		// put the value of the starting location in the ending location
		board[transleted_input[0][0]][transleted_input[0][1]] = value;
		// change the starting location to '*'
		board[transleted_input[1][0]][transleted_input[1][1]] = "*";
		return board;
	}

	/*public static boolean testIsUserEatingValid4() {
	    // Test case with a valid eating move on the left side of the board
	    String[][] board = { 
	    		{ "-", "W", "-", "W", "-", "*", "-", "W" }, 
	    		{ "W", "-", "W", "-", "W", "-", "W", "-" },
	            { "-", "R", "-", "R", "-", "*", "-", "W" }, 
	            { "*", "-", "R", "-", "*", "-", "W", "-" },
	            { "-", "R", "-", "*", "-", "*", "-", "R" }, 
	            { "R", "-", "*", "-", "R", "-", "*", "-" },
	            { "-", "R", "-", "R", "-", "R", "-", "R" }, 
	            { "R", "-", "R", "-", "R", "-", "R", "-" } };
	    int[][] transleted_input = {
	    		{ 0, 5 },
	    		{ 2, 3 } }; // Valid eating move on the left side
	    return IsUserEatingValid(transleted_input, board) == true;
	}*/
	
	// check if the user can eat
	public static boolean IsUserEatingValid(int[][] transleted_input, String[][] board) {
		if (transleted_input[1][0] != 0 && transleted_input[1][0] != 1 && transleted_input[1][1] != 0
				&& transleted_input[1][1] != 1) {
			if (// start is 'R'
			(board[transleted_input[1][0]][transleted_input[1][1]] == "R")
					// end is '*'
					&& (board[transleted_input[0][0]][transleted_input[0][1]] == "*")
					// is there 'W' between the 'R' and the '*'
					&& ((board[(transleted_input[0][0] + transleted_input[1][0])/ 2]
							[(transleted_input[0][1] + transleted_input[1][1]) / 2] == "W"))
					// x is two step up
					&& (Math.abs(transleted_input[0][0] - transleted_input[1][0]) == 2)
					// y is two step left OR right
					&& (Math.abs(transleted_input[0][1] - transleted_input[1][1]) == 2)) {
				return true;
			}
		}
		if (transleted_input[1][0] != 0 && transleted_input[1][0] != 1 && transleted_input[1][1] != 6
				&& transleted_input[1][1] != 7) {
			if (// start is 'R'
			(board[transleted_input[1][0]][transleted_input[1][1]] == "R")
					// end is '*'
					&& (board[transleted_input[0][0]][transleted_input[0][1]] == "*")
					// is there 'W' between the 'R' and the '*'
					&& ((board[(transleted_input[0][0] + transleted_input[1][0])
							/ 2][(transleted_input[0][1] + transleted_input[1][1]) / 2] == "W"))
					// x is two step up
					&& (Math.abs(transleted_input[0][0] - transleted_input[1][0]) == 2)
					// y is two step left OR right
					&& (Math.abs(transleted_input[0][1] - transleted_input[1][1]) == 2)) {
				return true;
			}
		}

		return false;
	}

	public static int[][] IsComputerEatingValid(int Wx, int Wy, String[][] board) {
		int[][] rArr = new int[1][2];
		if (// start is 'W'
		(board[Wx][Wy] == "W")) {
			if (ComputerCanEatLeft(Wx, Wy, board) || ComputerCanEatRight(Wx, Wy, board)) {
				rArr[0][0] = Wx;
				rArr[0][1] = Wy;
			} else {
				rArr[0][0] = -1;
				rArr[0][1] = -1;
			}

		} else if (// start is 'Q-W'
		(board[Wx][Wy] == "Q-W")) {
			if (ComputerCanEatLeftQueen(Wx, Wy, board) || ComputerCanEatRightQueen(Wx, Wy, board)) {
				rArr[0][0] = Wx;
				rArr[0][1] = Wy;
			} else {
				rArr[0][0] = -1;
				rArr[0][1] = -1;
			}

		}
		return rArr;
	}

	// check if the computer (not queen) can do a move
	public static int[][] IsComputerCanMove(int Wx, int Wy, String[][] board) {
		int[][] rArr = new int[1][2];
		if (// start is 'W'
		(board[Wx][Wy] == "W")) {
			if (ComputerCanMoveLeft(Wx, Wy, board) || ComputerCanMoveRight(Wx, Wy, board)) {
				rArr[0][0] = Wx;
				rArr[0][1] = Wy;
			} else {
				rArr[0][0] = -1;
				rArr[0][1] = -1;
			}

		}
		if (// start is 'Q-W'
		(board[Wx][Wy] == "Q-W")) {
			if (ComputerCanMoveLeftQueen(Wx, Wy, board) || ComputerCanMoveRightQueen(Wx, Wy, board)) {
				rArr[0][0] = Wx;
				rArr[0][1] = Wy;
			} else {
				rArr[0][0] = -1;
				rArr[0][1] = -1;
			}

		}
		return rArr;
	}

	public static boolean ComputerCanMoveLeft(int Wx, int Wy, String[][] board) {
		if (Wy == 0) {
			return false;
		}
		if (Wx < 7) {
			if (// end is '*'
			board[Wx + 1][Wy - 1] == "*") {
				return true;
			} else {
				return false;
			}
		}

		return false;
	}

	public static boolean ComputerCanMoveRight(int Wx, int Wy, String[][] board) {
		if (Wy == 7) {
			return false;
		}
		if (Wx < 7) {
			if (// end is '*'
			board[Wx + 1][Wy + 1] == "*") {
				return true;
			}
		}
		return false;
	}

	public static boolean ComputerCanMoveLeftQueen(int Wx, int Wy, String[][] board) {
		if (Wy != 0) {
			if (Wx == 0) {

				if (// end is '*'
				board[Wx + 1][Wy - 1] == "*") {
					return true;
				}
			} else if (Wx == 7) {

				if (// end is '*'
				board[Wx - 1][Wy - 1] == "*") {
					return true;
				}
			} else {
				if (// end is '*'
				board[Wx - 1][Wy - 1] == "*" || board[Wx + 1][Wy - 1] == "*") {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean ComputerCanMoveRightQueen(int Wx, int Wy, String[][] board) {
		if (Wy != 7) {
			if (Wx == 0) {

				if (// end is '*'
				board[Wx + 1][Wy + 1] == "*") {
					return true;
				}
			} else if (Wx == 7) {

				if (// end is '*'
				board[Wx - 1][Wy + 1] == "*") {
					return true;
				}
			} else {
				if (// end is '*'
				board[Wx - 1][Wy + 1] == "*" || board[Wx + 1][Wy + 1] == "*") {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean ComputerCanEatLeftQueen(int Wx, int Wy, String[][] board) {
		if (Wy == 6 || Wy == 7) {
			return false;
		}

		if (Wx == 0 || Wx == 1) {
			if ((board[Wx + 2][Wy + 2] == "*") && (board[Wx + 1][Wy + 1] == "R" || board[Wx + 1][Wy + 1] == "Q-R")) {

				return true;
			}
		}

		if (Wx == 6 || Wx == 7) {
			if ((board[Wx - 2][Wy + 2] == "*") && (board[Wx - 1][Wy + 1] == "R" || board[Wx - 1][Wy + 1] == "Q-R")) {

				return true;
			}
		}

		else if ( // end is '*'
		((board[Wx + 2][Wy + 2] == "*")
				// is there 'R'/'Q-R' between the 'Q-W' and the '*'
				&& ((board[Wx + 1][Wy + 1] == "R") || (board[Wx + 1][Wy + 1] == "Q-R"))) ||
		// end is '*'
				((board[Wx - 2][Wy + 2] == "*")
						// is there 'R'/'Q-R' between the 'Q-W' and the '*'
						&& ((board[Wx - 1][Wy + 1] == "R") || (board[Wx - 1][Wy + 1] == "Q-R")))) {
			return true;
		}
		return false;
	}

	public static boolean ComputerCanEatRightQueen(int Wx, int Wy, String[][] board) {
		if (Wy == 1 || Wy == 0) {
			return false;
		}

		if (Wx == 0 || Wx == 1) {
			if ((board[Wx + 2][Wy - 2] == "*") && (board[Wx + 1][Wy - 1] == "R" || board[Wx + 1][Wy - 1] == "Q-R")) {
				return true;
			}
		}

		if (Wx == 6 || Wx == 7) {
			if ((board[Wx - 2][Wy - 2] == "*") && (board[Wx - 1][Wy - 1] == "R" || board[Wx - 1][Wy - 1] == "Q-R")) {
				return true;
			}
		}

		else if ( // end is '*'
		((board[Wx + 2][Wy - 2] == "*")
				// is there 'R'/'Q-R' between the 'Q-W' and the '*'
				&& ((board[Wx + 1][Wy - 1] == "R") || (board[Wx + 1][Wy - 1] == "Q-R"))) ||
		// end is '*'
				((board[Wx - 2][Wy - 2] == "*")
						// is there 'R'/'Q-R' between the 'Q-W' and the '*'
						&& ((board[Wx - 1][Wy - 1] == "R") || (board[Wx - 1][Wy - 1] == "Q-R")))) {
			return true;
		}
		return false;
	}

	public static boolean ComputerCanEatLeft(int Wx, int Wy, String[][] board) {
		if (Wy == 1 || Wy == 0 || Wx == 6 || Wx == 7) {
			return false;
		} else {

			if (// end is '*'
			(board[Wx + 2][Wy - 2] == "*")
					// is there 'R' between the 'W' and the '*'
					&& ((board[Wx + 1][Wy - 1] == "R") || (board[Wx + 1][Wy - 1] == "Q-R"))) {
				return true;
			}
		}
		return false;
	}

	public static boolean ComputerCanEatRight(int Wx, int Wy, String[][] board) {
		if (Wy == 6 || Wy == 7 || Wx == 6 || Wx == 7) {
			return false;
		} else {

			if (// end is '*'
			(board[Wx + 2][Wy + 2] == "*")
					// is there 'R' between the 'W' and the '*'
					&& ((board[Wx + 1][Wy + 1] == "R") || (board[Wx + 1][Wy + 1] == "Q-R"))) {
				return true;
			}
		}
		return false;
	}

	// Computer Turn
	public static boolean ComputerTurn(String[][] board) {
		int arow = 0;
		int Wx, Wy;
		Random random = new Random();

		// count how many W there is on the board
		int count = CountW(board);
		arow = count;
		// open a countX2 array that includes the Ws coordinates
		int[][] allWxy = AddWcoordinate(board, count);

		if (allWxy.length == 0) {
			System.out.println("Congratulations, user has won :)");
			return true;
		}
		int[][] tempAllWxy = new int[count][2];

		// copy the array for backup
		CopyTheArray(count, allWxy, tempAllWxy);

		/////// Check If There Is Someone That Can Eat:///////

		// random a number between 0-(count-1)
		int randomNumberInRange = random.nextInt(arow);

		do {
			if (arow != 0) {
				// randomize a number between 0->(count-1)
				randomNumberInRange = random.nextInt(arow);
				arow--;
			} else {
				break;
			}
			// check if this location can eat
			// if yes -
			if (IsComputerEatingValid(tempAllWxy[randomNumberInRange][0], tempAllWxy[randomNumberInRange][1],
					board)[0][0] != -1) {
				Wx = tempAllWxy[randomNumberInRange][0];
				Wy = tempAllWxy[randomNumberInRange][1];
				tempAllWxy = new int[1][2];
				tempAllWxy[0][0] = Wx;
				tempAllWxy[0][1] = Wy;
				break;
			}
			// if not
			else {
				// remove the location because we already checked it -
				// make a new array without the bad location
				tempAllWxy = RemoveRowFromArray(tempAllWxy, randomNumberInRange);

			}
		} while (tempAllWxy.length > 0);

		if (tempAllWxy.length == 1) {
			// make 2X2 array thats include the starting and the ending location
			int[][] computerLocations = new int[2][2];
			// put the starting location coordinate to the bottom cells
			computerLocations[1][0] = tempAllWxy[0][0];
			computerLocations[1][1] = tempAllWxy[0][1];
			// find me in random the ending location
			int[][] finishLoc = FinishPlaceComputer(tempAllWxy, board);
			// put the endinging location coordinate to the top cells
			computerLocations[0][0] = finishLoc[0][0];
			computerLocations[0][1] = finishLoc[0][1];
			// eat one
			Eating(computerLocations, board);
			// eat until you can't
			// MultiEat(computerLocations, board);
			int countR = 0;
			// check if there's R's soldiers on the board
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					// if yes - count
					if (board[i][j] == "R" || board[i][j] == "Q-R") {
						countR++;
					}
				}
			}
			if (countR == 0) {
				System.out.println("Sorry, computer has won :(");
				return true;
			}

			// Do the check again - multieat
			if (finishLoc[0][0] == 7) {
				ChangeTheSoldierToQueen(finishLoc[0][0], finishLoc[0][1], board);
			}
			return false;
		}
		// the computer can't eat
		else {
			tempAllWxy = new int[count][2];

			// copy the array for backup
			CopyTheArray(count, allWxy, tempAllWxy);
			arow = count;

			do {
				if (arow != 0) {
					// randomize a number between 0->(count-1)
					randomNumberInRange = random.nextInt(arow);
					arow--;
				} else {
					break;
				}
				// check if this location can move
				// if yes -
				if (IsComputerCanMove(allWxy[randomNumberInRange][0], allWxy[randomNumberInRange][1],
						board)[0][0] != -1) {
					Wx = allWxy[randomNumberInRange][0];
					Wy = allWxy[randomNumberInRange][1];
					tempAllWxy = new int[1][2];
					tempAllWxy[0][0] = Wx;
					tempAllWxy[0][1] = Wy;
					break;
				}
				// if not
				else {
					// remove the location because we already checked it -
					// make a new array without the bad location
					tempAllWxy = RemoveRowFromArray(tempAllWxy, randomNumberInRange);
				}
			} while (tempAllWxy.length > 0);

			if (tempAllWxy.length == 1) {
				// make 2X2 array thats include the starting and the ending location
				int[][] computerLocations = new int[2][2];
				// put the starting location coordinate to the bottom cells
				computerLocations[1][0] = tempAllWxy[0][0];
				computerLocations[1][1] = tempAllWxy[0][1];
				// find me in random the ending location
				int[][] finishLoc = FinishPlaceComputer(tempAllWxy, board);
				// put the endinging location coordinate to the top cells
				computerLocations[0][0] = finishLoc[0][0];
				computerLocations[0][1] = finishLoc[0][1];
				Move(computerLocations, board);
				// check if the computer soldier can become a queen
				if (finishLoc[0][0] == 7) {
					ChangeTheSoldierToQueen(finishLoc[0][0], finishLoc[0][1], board);
				}
				return false;
			}
			// else - If can't move its a tie
			else if (tempAllWxy.length == 0) {
				Tie(true);
				return true;
			}
		}
		return false;
	}

	public static void ChangeTheSoldierToQueen(int i, int j, String[][] board) {
		if (board[i][j] == "W" || board[i][j] == "Q-W") {
			board[i][j] = "Q-W";
		}
		if (board[i][j] == "R" || board[i][j] == "Q-R") {
			board[i][j] = "Q-R";
		}
	}
	
	//check if its a tie
	public static boolean Tie(boolean F) {
		if (F == true) {
			System.out.println("Well, unfortunately it’s a Tie…");
			return true;
		} else
			return false;
	}

	public static int[][] FinishPlaceComputer(int[][] tempAllWxy, String[][] board) {
		int[][] finishLoc = new int[1][2];
		Random r = new Random();
		int random = r.nextInt(10) + 1;
		if (IsComputerEatingValid(tempAllWxy[0][0], tempAllWxy[0][1], board)[0][0] != -1) {
			if (ComputerCanEatLeft(tempAllWxy[0][0], tempAllWxy[0][1], board)
					&& ComputerCanEatRight(tempAllWxy[0][0], tempAllWxy[0][1], board)) {
				if (random <= 3) {
					// eat to the right
					finishLoc[0][0] = tempAllWxy[0][0] + 2;
					finishLoc[0][1] = tempAllWxy[0][1] + 2;
				} else if (random >= 4) {
					// eat to the left
					finishLoc[0][0] = tempAllWxy[0][0] + 2;
					finishLoc[0][1] = tempAllWxy[0][1] - 2;
				}

			} else if (ComputerCanEatRight(tempAllWxy[0][0], tempAllWxy[0][1], board)) {
				// eat to the right
				finishLoc[0][0] = tempAllWxy[0][0] + 2;
				finishLoc[0][1] = tempAllWxy[0][1] + 2;
			} else {
				// eat to the left
				finishLoc[0][0] = tempAllWxy[0][0] + 2;
				finishLoc[0][1] = tempAllWxy[0][1] - 2;
			}

		} else if (IsComputerCanMove(tempAllWxy[0][0], tempAllWxy[0][1], board)[0][0] != -1) {
			if (ComputerCanMoveLeft(tempAllWxy[0][0], tempAllWxy[0][1], board)
					&& ComputerCanMoveRight(tempAllWxy[0][0], tempAllWxy[0][1], board)) {
				if (random <= 3) {
					// move to the right
					finishLoc[0][0] = tempAllWxy[0][0] + 1;
					finishLoc[0][1] = tempAllWxy[0][1] + 1;
				} else if (random >= 4) {
					// move to the left
					finishLoc[0][0] = tempAllWxy[0][0] + 1;
					finishLoc[0][1] = tempAllWxy[0][1] - 1;
				}

			} else if (ComputerCanMoveRight(tempAllWxy[0][0], tempAllWxy[0][1], board)) {
				// move to the right
				finishLoc[0][0] = tempAllWxy[0][0] + 1;
				finishLoc[0][1] = tempAllWxy[0][1] + 1;
			} else {
				// move to the left
				finishLoc[0][0] = tempAllWxy[0][0] + 1;
				finishLoc[0][1] = tempAllWxy[0][1] - 1;
			}

		}

		return finishLoc;

	}

	public static int[][] RemoveRowFromArray(int[][] tempAllWxy, int randomNumberInRange) {
		int[][] temp2AllWxy = new int[tempAllWxy.length - 1][2];

		// check 3 cases:
		// 1. if the random number is not at the peers
		if (randomNumberInRange != 0 && randomNumberInRange != tempAllWxy.length - 1) {
			for (int k = 0; k < randomNumberInRange; k++) {
				for (int p = 0; p < 2; p++) {
					temp2AllWxy[k][p] = tempAllWxy[k][p];
				}
			}
			for (int k = randomNumberInRange + 1; k < tempAllWxy.length; k++) {
				for (int p = 0; p < 2; p++) {
					temp2AllWxy[k - 1][p] = tempAllWxy[k][p];
				}
			}
		}
		// 2. if the random number is first
		if (randomNumberInRange == 0) {
			for (int k = 1; k < tempAllWxy.length; k++) {
				for (int p = 0; p < 2; p++) {
					temp2AllWxy[k - 1][p] = tempAllWxy[k][p];
				}
			}
		}
		// 3. if the random number is the last
		if (randomNumberInRange == tempAllWxy.length - 1) {
			for (int k = 0; k < tempAllWxy.length - 1; k++) {
				for (int p = 0; p < 2; p++) {
					temp2AllWxy[k][p] = tempAllWxy[k][p];
				}
			}
		}
		return temp2AllWxy;
	}

	public static int[][] AddWcoordinate(String[][] board, int count) {
		int[][] allWxy = new int[count][2];

		int arow = 0;
		for (int k = 0; k < 8; k++) {
			for (int p = 0; p < 8; p++) {
				if (board[k][p] == "W" || board[k][p] == "Q-W") {
					allWxy[arow][0] = k;
					allWxy[arow][1] = p;
					arow++;
				}
			}
		}
		return allWxy;
	}

	public static int CountW(String[][] board) {
		int count = 0;
		for (int k = 0; k < 8; k++) {
			for (int p = 0; p < 8; p++) {
				if (board[k][p] == "W")
					count++;
			}
		}
		return count;
	}

	public static void CopyTheArray(int row, int[][] arrOrigin, int[][] tempArr) {
		for (int k = 0; k < row; k++) {
			for (int p = 0; p < 2; p++) {
				tempArr[k][p] = arrOrigin[k][p];
			}
		}
	}

}
