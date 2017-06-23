import java.io.*;
import java.net.*;
import java.util.*;
public class Comp extends Player{

    ServerSocket server = null;
    Socket socks = null;
    PrintWriter out = null;
    BufferedReader in = null;
    BufferedReader userIn = null;

    private final static String HELLO = "hello";
    private final static String BYE = "bye";
    private final static String NEWGAME = "newgame";
    private final static String READY = "ready";
    private final static String REJECT = "reject";
    private final static String WIN = "you-win";
    private final static String PASS = "pass";


    /**
     * Constructs an online player, establishing a socket and acting as either a clent or a server
     * @param board the game board
     * @param colour the colour of the player
     */
    public Comp(Space [] board, Colour colour)
    {
        super(board, colour);

        int portNumber = 20101 + 30000;

        try
        {
            userIn = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter the name of a server, or enter 'wait' if you want a client to make a connection.");
            String keys = userIn.readLine();
            if (keys.equals("wait")) { //This means this program is acting as server
                server = new ServerSocket(portNumber);
                this.colour = Colour.WHITE;
                socks = server.accept();
            }
            else
            {
                this.colour = Colour.RED; //program acts as client
                socks = new Socket(keys, portNumber);
            }
            out = new PrintWriter(socks.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socks.getInputStream()));
            if (!establishGame(this.colour == Colour.WHITE)) //Use protocols to establish game with another program
            {
                quit(BYE); //end connection if game fails to establish
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Begins a game using protocols
     * @param server If this player is the server or not
     * @return whether or not the game successfully got made
     * @throws IOException
     */
    public boolean establishGame(boolean server) throws IOException
    {
        if (server) {
            String input = in.readLine();
            if (input.equals(HELLO)) {
                out.println(HELLO);
            } else {
                return false;
            }
            input = in.readLine();
            if (input.equals(NEWGAME)) {
                out.println(READY);
            } else {
                return false;
            }
        }
        else
        {
            out.println(HELLO);
            String input = in.readLine();
            if (input.equals(HELLO)) {
                out.println(NEWGAME);
            }
            else
            {
                return false;
            }
            input = in.readLine();
            if (!input.equals(READY)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Shuts everything down
     */
    public void close()
    {
        try
        {
            if (in != null)
            {
                in.close();
            }
            if (userIn != null)
            {
                userIn.close();
            }
            if (out != null)
            {
                out.close();
            }
            if (socks != null)
            {
                socks.close();
            }
            if (server != null)
            {
                server.close();
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Returns a blank move, called when there is no possible moves, must be written
     * to override method in superclass
     * @param rolls the possible dice rolls
     * @return No possible move
     */
    public Move getMove(ArrayList<Integer> rolls)
    {
        return new Move(-1,-1, this);
    }

    /**
     * Tells the online player it has won
     */
    public void tellWin()
    {
        out.println(WIN + ";" + BYE);
    }

    /**
     * Gets the moves, validates them, then runs them on the board and prints the
     * board
     * @return String format of the turn, null in this case as this is for
     * players to pass their moves to an online player
     */
    public String takeTurn()
    {
        try
        {
            String turn = in.readLine();
            quit(turn);
            String[] turnPieces = turn.split(":");
            ArrayList<Integer> rolls = getRolls(turnPieces[0]);
            ArrayList<Move> moves = extractMoves(turnPieces[1]);
            for (Move move: moves)
            {
                if (!move.validate(rolls, board)) //If invalid, quit
                {
                    System.out.println(move);
                    quit(BYE);
                }
                System.out.println(move);
                if (run(move, board))
                {
                    int diff = move.getEnd() - move.getStart();
                    diff = Math.abs(diff);
                    rolls.remove((Integer) diff); //removes roll that has been used from roll list
                }
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        Game.printBoard();
        return null;
    }

    /**
     * Quits if time to quit
     * @param maybye the string that might be bye, that would idnicate a quit
     */
    public void quit(String maybye)
    {
        if (BYE.equals(maybye))
        {
            out.println(BYE);
            close();
            System.exit(0);
        }
        else if (maybye.equals(WIN) || maybye.equals(WIN + ";" + BYE))
        {
            System.out.println("Winner!");
            out.println(BYE);
            close();
            System.exit(0);
        }
    }

    /**
     * Gets rolls from the players String
     * @param stringRoll
     * @return
     */
    public ArrayList<Integer> getRolls(String stringRoll)
    {
        String [] rolls = stringRoll.split("-");

        int roll1 = Integer.parseInt(rolls[0]);
        int roll2 = Integer.parseInt(rolls[1]);

        System.out.println("A " + roll1 + " and a " + roll2 + " are rolled.");

        ArrayList<Integer> intRolls = new ArrayList<Integer>();

        intRolls.add(roll1);
        intRolls.add(roll2);

        if (roll1 == roll2)
        {
            for (int i = 0; i<2; i++)
            {
                intRolls.add(roll1);
            }
        }
        return intRolls;
    }

    /**
     * Gets the moves from the online players String
     * @param stringMoves
     * @return
     */
    public ArrayList<Move> extractMoves(String stringMoves)
    {
        ArrayList<Move> moves = new ArrayList<Move>();
        String [] sepStringMoves = stringMoves.split(",");
        for (int i = 0; i< sepStringMoves.length; i++)
        {
            String brackRemove = sepStringMoves[i].replace("(", "");
            brackRemove = brackRemove.replace(")", "");
            brackRemove = brackRemove.replace(";", "");

            String [] nums = brackRemove.split("\\|");

            moves.add(new Move(Integer.parseInt(nums[0]), Integer.parseInt(nums[1]), this));
        }
        return moves;
    }


    /**
     * Passes the other players move to the online player
     * @param turn Other players turn, formatted correctly
     */
    public void pass(String turn)
    {
        out.println(turn);
    }

    public String toString()
    {
        return "Online Player";
    }
}
