package vtp2022.revision.workshop;

//import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        game newGame = new game();
        List<String> data = newGame.readFileMain("glider.gol");

        newGame.display(data);
        System.out.println("game is of size " + newGame.sizeX + ","+ newGame.sizeY);
        System.out.println("game starts at " + newGame.startX + "," + newGame.startY);

        newGame.gameCycles(data, 2);
    }
}
