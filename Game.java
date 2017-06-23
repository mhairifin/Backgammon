
import java.util.*;

/**
 * Game class has an array of 24 space objects
 */
public class Game {

    public static final int BOARD_SIZE = 24;
    public static final int BOARD_LENGTH=12;
    public static final int BOARD_COL=2;

    private static Space[] board;

    private static Space[][] board2;


    /**
     * Game loop
     * @param count
     * @param loopSize
     */
    public void play(int count, int loopSize)
    {

        board = setupBoard();
        board2=setupPrinter();
        printBoard();

        Player player1 = null;
        Player player2 = null;

        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to Backgammon! Which type of game do you want to play?");
        System.out.println("1: Human vs. Human");
        System.out.println("2: Human vs. Online Player");
        System.out.println("3: AI vs. Online Player");
        System.out.println("4: AI vs. AI");

        switch(input.nextInt())
        {
            case 1:  player1 = new Human(board, Colour.WHITE);
                player2 = new Human(board, player1.getColour().getOpp());
                break;
            case 2:  player1 = new Comp(board, Colour.WHITE);
                player2 = new Human(board, player1.getColour().getOpp());
                break;
            case 3:  player1 = new Comp(board, Colour.WHITE);
                player2 = new Harvey(board, player1.getColour().getOpp());
                break;
            case 4:  player1 = new Harvey(board, Colour.WHITE);
                player2 = new Jax(board, player1.getColour().getOpp());
                break;
        }

        Player current;
        if (player1.getColour() == Colour.RED)
        {
            current = player2;
        }
        else
        {
            current = player1;
        }

        System.out.println(current + " goes first!");
        System.out.println(player1 + " is " + player1.getColour());
        System.out.println(player2 + " is " + player2.getColour());
        System.out.println("White is going down the board (24 - 1), Red is going up the board (1 - 24).");
        

        boolean gameOver = false;
        while (!gameOver)
        {
            System.out.println(current + "'s turn");
            String turn = current.takeTurn();
            Game.printBoard();
            gameOver = checkEnd(current);
            if (current == player1)
            {
                current = player2;
            }
            else
            {
                current = player1;
            }
            if (current instanceof Comp)
            {
                ((Comp) current).pass(turn);
            }
        }
    }

    public Space[][] setupPrinter()
    {
        Space[][] board2 = new Space[BOARD_COL][BOARD_LENGTH+1];

        board2[0][0]=(EndSpace) board[0];
        board2[1][12]=(EndSpace) board[25];

        board2[0][0]=board[0];
        board2[0][1]=board[12];
        board2[0][2]=board[11];
        board2[0][3]=board[10];
        board2[0][4]=board[9];
        board2[0][5]=board[8];
        board2[0][6]=board[7];
        board2[0][7]=board[6];
        board2[0][8]=board[5];
        board2[0][9]=board[4];
        board2[0][10]=board[3];
        board2[0][11]=board[2];
        board2[0][12]=board[1];

        board2[1][0]=board[13];
        board2[1][1]=board[14];
        board2[1][2]=board[15];
        board2[1][3]=board[16];
        board2[1][4]=board[17];
        board2[1][5]=board[18];
        board2[1][6]=board[19];
        board2[1][7]=board[20];
        board2[1][8]=board[21];
        board2[1][9]=board[22];
        board2[1][10]=board[23];
        board2[1][11]=board[24];
        board2[1][12]=board[25];

        return board2;
    }

    public static void printBoard() {
    /* isn't working how I want I want it to go through the loop,
     * but instead of printing out r or w,  I want it to be added to the 2d array
     * and then toString the array and print the array out in a nice formatted way
     * how to do this??
     */



        System.out.println();
        //once the values saved, print out the toString board values???
        Arrays.toString(board2);
        System.out.println("White Home    12   11  10   9   8   7   6    5   4   3   2   1");
        System.out.println(board2[0][0] + "    " + board2[0][1] + "   " + board2[0][2] + "   " + board2[0][3]
                + "   " + board2[0][4] + "   " + board2[0][5] + "   " + board2[0][6] + "   " + board2[0][7]
                + "   " + board2[0][8] + "   " + board2[0][9] + "   " + board2[0][10] + "   " + board2[0][11] + "   " + board2[0][12] + " ");
        System.out.println();
        System.out.println("               " + board2[1][0] + "   " + board2[1][1] + "   " + board2[1][2] + "   " + board2[1][3]
                + "   " + board2[1][4] + "   " + board2[1][5] + "   " + board2[1][6] + "   " + board2[1][7]
                + "   " + board2[1][8] + "   " + board2[1][9] + "   " + board2[1][10] + "   " + board2[1][11] + "   " + board2[1][12]);
        System.out.println("              13   14  15  16  17   18  19   20  21  22  23  24   Red Home");

    }

    /**
     * Checks end of the game
     * @param player
     * @return
     */
    public boolean checkEnd(Player player)
    {
        if (player.getHome().getBearing() == 15) {
            System.out.println(player + " wins!");
            Main.writer.println(player.getColour());
            if (player instanceof Comp)
            {
                ((Comp)player).tellWin();
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    public Space[] setupBoard()
    {
        Space[] board = new Space[BOARD_SIZE+2];
        board[0] = new EndSpace(Colour.WHITE);
        board[25] = new EndSpace(Colour.RED);
    	for (int i = 1; i<= BOARD_SIZE; i++)
    	{
    		board[i] = new Space(0, null);
    	}
    	board[1].setColour(Colour.RED);
        board[1].addCounter(2);
        board[12].setColour(Colour.RED);
        board[12].addCounter(5);
        board[17].setColour(Colour.RED);
        board[17].addCounter(3);
        board[19].setColour(Colour.RED);
        board[19].addCounter(5);
        board[24].setColour(Colour.WHITE);
        board[24].addCounter(2);
        board[13].setColour(Colour.WHITE);
        board[13].addCounter(5);
        board[8].setColour(Colour.WHITE);
        board[8].addCounter(3);
        board[6].setColour(Colour.WHITE);
        board[6].addCounter(5);
        return board;
    }

}
