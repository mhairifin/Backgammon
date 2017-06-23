import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Statistics {

    /**
     * Generates stats of which colour won how many games, based on a txt file generated during the programs run
     * @param txtfile
     */
    public static void generate(String txtfile)
    {
        int whiteCount = 0;
        int redCount = 0;
        BufferedReader reader = null;
        try
        {
            reader = new BufferedReader(new FileReader(txtfile));
            while (reader.ready())
            {
                String line = reader.readLine();
                if (line.equals("RED"))
                {
                    redCount++;
                }
                else
                {
                    whiteCount++;
                }
            }
        }
        catch (IOException e)
        {

        }
        Main.stats.println("Red: " + redCount + " White: " + whiteCount);
    }
}
