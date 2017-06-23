

/**
 *
 * A space has counters in it. counters = null if there are no counters on the
 * space
 */
public class Space {

    protected int numCounters = 0;
    private Colour colour = null;

    /**
     * Moves a counter into a space on the board
     */
    public void addCounter()
    {
        numCounters++;
    }
    
    /**
    * Adds multiple counters to a space on the board
    */
    public void addCounter(int num)
    {
        numCounters += num;
    }
    
    public Space(int num, Colour colour)
    {
    	this.numCounters = num;
    	this.colour = colour;
    }
    
    public void setColour(Colour colour)
    {
    	this.colour = colour;
    }

    /**
     * Moves a counter out of a space on a board
     */
    public void removeCounter()
    {
        numCounters--;
    }

    public boolean isEmpty()
    {
       return numCounters == 0;
    }

    public Colour getColour()
    {
        return colour;
    }

    public void flip()
    {
        if (colour == Colour.RED)
        {
            colour = Colour.WHITE;
        }
        else
        {
            colour = Colour.RED;
        }
    }

    /**
     * Gets the number of counters in this space on the board
     * @return the number of counters
     */
    public int getNumCounters()
    {
        return numCounters;
    }

    public String toString()
    {
        String col;
        if (getColour() != null) {
            if (getColour() == colour.RED) {
                col = "R";
            } else {
                col = "W";
            }
        }

        else
        {
            col = "";
        }
        return getNumCounters() + col;
    }

}
