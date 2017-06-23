import java.util.*;

public class Human extends Player {

    protected ArrayList<Integer> rolls = new ArrayList<Integer>();

    public Human(Space [] board, Colour colour)
    {
        super(board, colour);
    }

    public String takeTurn()
    {
        ArrayList<Integer> rolls = rollDie();
        ArrayList<Integer> allRolls = new ArrayList<Integer>();
        for (int roll: rolls)
        {
            allRolls.add(roll);
        }
        int numMoves = rolls.size();
        ArrayList<Move> moves = new ArrayList<Move>();
        for (int i = 0; i< numMoves; i++)
        {
            Move move = this.getMove(rolls);
            if (run(move, board)) {
                Game.printBoard();
                moves.add(move);
                int diff = move.getEnd() - move.getStart();
                diff = Math.abs(diff);
                rolls.remove((Integer) diff);
            }
            else
            {
                break;
            }
        }
        return constructTurn(allRolls, moves);
    }

    public Move getMove(ArrayList<Integer> rolls)
    {
        System.out.println("Available rolls:");
        for (Integer roll: rolls)
        {
            System.out.println(roll);
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the start point. (Enter -1 if no possible move)");
        int start = scanner.nextInt();
        System.out.println("Please enter the end point. (Enter -1 if no possible move)");
        int end = scanner.nextInt();
        Move move = new Move(start, end, this);
        while(!move.validate(rolls, board))
        {
            System.out.println("Move invalid, try again.");
            move = getMove(rolls);
        }
        return move;
    }

    public String toString()
    {
        return "Human";
    }
}
