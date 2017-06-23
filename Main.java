import java.io.*;
import java.util.ArrayList;

public class Main {

    public static PrintWriter writer = null;
    public static PrintWriter stats = null;

    public static void main(String[] args) {


        /*String fileName = "rawData.txt";
        try {
            writer = new PrintWriter(fileName);
            stats = new PrintWriter(new BufferedWriter(new FileWriter("stats.txt", true)));
        }
        catch(FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        catch (IOException e)
        {

        }*/

        int loopSize = 1;
        for (int i = 0; i< loopSize; i++) {
            Game game = new Game();
            game.play(i, loopSize);
        }


        /*writer.close();
        Statistics.generate(fileName);
        Main.stats.println();
        stats.close();*/



    }


}
