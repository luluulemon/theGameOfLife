package vtp2022.revision.workshop;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {   
        if (args.length == 0){System.out.println("Please provide path for start game file");}

        else{
        String startGameFilePath = args[0];
        game newGame = new game();
        List<String> data = newGame.readFileMain(startGameFilePath);
        System.out.println("game is of size " + newGame.sizeX + ","+ newGame.sizeY);
        System.out.println("game starts at " + newGame.startX + "," + newGame.startY);

        newGame.gameCycles(data, 5);    }
    }
}
