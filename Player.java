
import java.util.*;

public abstract class Player {

    protected Space[] board;
    protected Colour colour;

    public Player(Space [] board, Colour colour)
    {
        this.board = board;
        this.colour = colour;
    }

    public abstract Move getMove(ArrayList<Integer> rolls);
    
    public Colour getColour()
    {
    	return colour;
    }

    public String takeTurn()
    {
        ArrayList<Integer> rolls = rollDie();
        ArrayList<Move> moves = new ArrayList<Move>();
        ArrayList<Integer> allRolls = new ArrayList<Integer>();
        for (int roll: rolls)
        {
            allRolls.add(roll);
        }
    	int numMoves = rolls.size();
        for (int i = 0; i< numMoves; i++)
        {
            Move move = this.getMove(rolls);
            moves.add(move);
            System.out.println(move);
            if (run(move, board)) {
                int diff = move.getEnd() - move.getStart();
                diff = Math.abs(diff);
                rolls.remove((Integer) diff);
            }
            else
            {
                break;
            }
        }
        Game.printBoard();
        return constructTurn(allRolls, moves);
    }
    
    public boolean rightWay(int start, int end)
    {
    	if (colour == Colour.RED)
    	{
    		return end>start;
    	}
    	if (colour == Colour.WHITE)
    	{
    		return start>end;
    	}
    	return false;
    }

    public boolean run(Move move, Space[] board)
    {
        if (move.getStart() == -1 && move.getEnd()==-1)
        {
            return false;
        }
        else
        {
            Type type = move.getType();
            Space start = board[move.getStart()];
            Space end = board[move.getEnd()];

            start.removeCounter();
            if (start instanceof EndSpace)
            {
                ((EndSpace)start).removeCaptured();
            }
            if (start.getNumCounters() == 0 && !(start instanceof EndSpace))
            {
                start.setColour(null);
            }

            if (type == Type.NORMAL)
            {
                if (end.getNumCounters() == 0)
                {
                    end.setColour(colour);
                }
                end.addCounter();
            }
            else if (type != null)
            {
                if (type == Type.CAPTURE)
                {
                    end.flip();
                }
                if (board[0].getColour() == colour)
                {
                    ((EndSpace)board[0]).addCounter(type);
                }
                else if (board[25].getColour() == colour)
                {
                    ((EndSpace)board[25]).addCounter(type);
                }
            }
            return true;
        }
    }

    public ArrayList<Move> getPossMoves(int roll, Space[] board)
    {
        ArrayList<Move> moves = new ArrayList<Move>();
        int adj;
        int begin;
        int end;
        if (colour == Colour.WHITE)
        {
            adj = -1; //Adjusted roll ensures roll is made in correct direction for colour
            begin = board.length-1;
            end = 0;
        }
        else
        {
            adj = 1;
            end = board.length-1;
            begin = 0;
        }
        int adjRoll = roll*adj;
        int i = begin;
        while (i != end+adj)
        {
            Move move = new Move(i, i+adjRoll, this);
            ArrayList<Integer> rolls = new ArrayList<Integer>();
            rolls.add(roll);
            if (move.validate(rolls, board))
            {
                moves.add(move);
            }
            i+= adj;
        }
        return moves;
    }

    public ArrayList<Integer> rollDie()
    {
        ArrayList<Integer> rolls = new ArrayList<Integer>();
        int die1 = roll();
        int die2 = roll();

        System.out.println("A " + die1 + " and a " + die2 + " are rolled.");

        rolls.add(die1);
        rolls.add(die2);

        if (die1 == die2)
        {
            rolls.add(die1);
            rolls.add(die1);
        }

        return rolls;
    }

    public int roll()
    {
        return (int)(6.0*Math.random())+1;
    }
    
    public boolean bearoffAllowed()
    {
        if (colour == Colour.WHITE) {
            int numCounters = 0;
            int end = 6;

            if (board[0].getColour() == colour) {
                numCounters += ((EndSpace) board[0]).getBearing();
            }
            for (int i = 1; i <= end; i++) {
                if (board[i].getColour() == colour) {
                    numCounters += board[i].getNumCounters();
                }
            }
            return numCounters == 15;
        }
        else
        {
            int numCounters = 0;
            int end = 25;

            if (board[25].getColour() == colour) {
                numCounters += ((EndSpace) board[25]).getBearing();
            }
            for (int i = 19; i < end; i++) {
                if (board[i].getColour() == colour) {
                    numCounters += board[i].getNumCounters();
                }
            }
            return numCounters == 15;
        }
    }

    public boolean hasCaptured()
    {
            return getOppHome().getCaptured() > 0;
    }

    public EndSpace getOppHome()
    {
        return ((EndSpace)board[colour.getOpp().getHome()]);
    }

    public String constructTurn(ArrayList<Integer> rolls, ArrayList<Move> moves)
    {
        StringBuilder turn = new StringBuilder();
        turn.append(rolls.get(0));
        turn.append("-");
        turn.append(rolls.get(1));
        turn.append(":");
        for (Move move: moves)
        {
            turn.append("(");
            turn.append(move.getStart());
            turn.append("|");
            turn.append(move.getEnd());
            turn.append("),");
        }
        turn.deleteCharAt(turn.lastIndexOf(","));
        turn.append(";");
        return turn.toString();
    }

    public EndSpace getHome()
    {
        return ((EndSpace)board[colour.getHome()]);
    }

    public abstract String toString();
}
