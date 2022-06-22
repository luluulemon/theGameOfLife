package vtp2022.revision.workshop;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

public class game {
    int sizeX;
    int sizeY;
    int startX;
    int startY;

    
    public game(){      // readFile method will set the values
        sizeX = 1;
        sizeY = 1;
        startX = 0;
        startY = 0;     
    }


    public void gameCycles(List<String> data, int cycles){
        List<String> survivorOld = data;
        List<String> survivorNew;
        int numCycles = 0;

        System.out.println("Starting game cyles...");
        display(data);

        while (numCycles<cycles)
        {   survivorNew = gameOfLife(survivorOld);
            numCycles++;
            System.out.println("\nDisplaying cycle "+ numCycles);
            display(survivorNew);
            survivorOld = survivorNew;
        }
    }


    public List<String> gameOfLife(List<String> data){      // find neighbours and list survivors
        List<String> survivors = new LinkedList<>();

        for (int i =0; i < sizeY; i++) //i refers to row (Y)
        {    for(int j=0; j< sizeX; j++) // j refers to column (X)
            {   List<String> neighbours = new LinkedList<>();   
                boolean isAlive = false;
                
                // check the eight neighbours that are on grid
                if(i-1>0 && j-1>0)
                { if (data.contains(Integer.toString(j-1)+Integer.toString(i-1)) )
                            neighbours.add(Integer.toString(j-1)+Integer.toString(i-1));   }
                if(i-1>0)
                { if (data.contains(Integer.toString(j)+Integer.toString(i-1)) )
                            neighbours.add(Integer.toString(j)+Integer.toString(i-1));     }
                if(i-1>0 && j+1<sizeX)
                { if (data.contains(Integer.toString(j+1)+Integer.toString(i-1)) )
                            neighbours.add(Integer.toString(j+1)+Integer.toString(i-1));    }
                if(j-1>0)
                { if (data.contains(Integer.toString(j-1)+Integer.toString(i)) )
                            neighbours.add(Integer.toString(j-1)+Integer.toString(i));    }
                if(j-1>0 && i+1<sizeY)
                { if (data.contains(Integer.toString(j-1)+Integer.toString(i+1)) )
                            neighbours.add(Integer.toString(j-1)+Integer.toString(i+1));    }
                if(i+1<sizeY)
                { if (data.contains(Integer.toString(j)+Integer.toString(i+1)) )
                            neighbours.add(Integer.toString(j)+Integer.toString(i+1));    }
                if(i+1<sizeY && j+1<sizeX)
                { if (data.contains(Integer.toString(j+1)+Integer.toString(i+1)) )
                            neighbours.add(Integer.toString(j+1)+Integer.toString(i+1));    }
                if(j+1<sizeX)
                { if (data.contains(Integer.toString(j+1)+Integer.toString(i)) )
                            neighbours.add(Integer.toString(j+1)+Integer.toString(i));    }
                //System.out.println("point " + Integer.toString(j) + Integer.toString(i) + " has " + neighbours.size());
                
                // add survivors to list
                if(data.contains(Integer.toString(j) + Integer.toString(i))){ isAlive = true; }
                if((neighbours.size()==2 || neighbours.size()==3) && isAlive)
                    {   survivors.add(Integer.toString(j) + Integer.toString(i));}
                if(isAlive==false && neighbours.size()==3)
                    {   survivors.add(Integer.toString(j) + Integer.toString(i));}
            }
        }
        return survivors;
    }


    public void readFileGrid(String startGame){
        
        try(
        Reader reader = new FileReader(startGame);
        BufferedReader br = new BufferedReader(reader);     
        ){  
            String line;
            String cmd;
            while (null != (line=br.readLine()) )
            {    cmd = line.split(" ")[0];
                if(cmd.equals( "GRID"))
                {   sizeX = Integer.parseInt(line.split(" ")[1]);
                    sizeY = Integer.parseInt(line.split(" ")[2]);
                }
            }      
        }
        catch(FileNotFoundException e)
        { e.printStackTrace();}
        catch(IOException e)
        { e.printStackTrace(); }
    }


    public void readFileStart(String startGame){
        
        try(
        Reader reader = new FileReader(startGame);
        BufferedReader br = new BufferedReader(reader);     )
        {  
            String line;
            String cmd;
            while (null != (line=br.readLine()) )
            {    cmd = line.split(" ")[0];
                if(cmd.equals( "START"))
                {   startX = Integer.parseInt(line.split(" ")[1]);
                    startY = Integer.parseInt(line.split(" ")[2]);
                }
            }      
        }
        catch(FileNotFoundException e)
        { e.printStackTrace();}
        catch(IOException e)
        { e.printStackTrace(); }
    }


    public List<String> readFileMain(String startGame) {
        readFileGrid(startGame);
        readFileStart(startGame);
        
        List<String> data = new LinkedList<>();

        try(
        Reader reader = new FileReader(startGame);
        BufferedReader br = new BufferedReader(reader);     )
        {  
            String line;
            String cmd;
            while (null != (line=br.readLine()) )
            {    cmd = line.split(" ")[0];
                if(cmd.equals( "DATA"))
                {   for (Integer lineNum=startY; lineNum < sizeY; lineNum++)
                    {   String dataLine = br.readLine();
                        if (dataLine == null) break;
                        for (int chars=0; chars< dataLine.length(); chars++)
                        {   if( dataLine.charAt(chars) == '*')  
                           { data.add(Integer.toString(chars+startX) + lineNum.toString());   } 
                        }
                    }
                }
            }      
        }
        catch(FileNotFoundException e)
        { e.printStackTrace();}
        catch(IOException e)
        { e.printStackTrace(); }

        return data;      
    }

    
    public void display(List<String> data)
    {
        // initialize boolean array of false
        boolean[][] dataDisplay = new boolean[sizeX][sizeY];

        // make survivor values true and make display "X"
        for(String datas: data)
        {   int x = Integer.parseInt( datas)/10;
            int y = Integer.parseInt(datas) % 10;
            dataDisplay[y][x] = true;                }

        System.out.print("\t ");    // insert tab space
        for(int i=0; i<sizeX; i++)
            {   System.out.print(" " + i + "  ");   }   // printing out header
        System.out.print("\n");
        for(int i=0; i<sizeY; i++)               // main loop for each position
            {System.out.print(i + "\t| ");
            for(int j=0; j<sizeX; j++)
                {   if(dataDisplay[i][j]==true)
                    {   System.out.print("X");  }
                    else { System.out.print(" "); }

                    System.out.print(" | ");
                }
                System.out.print("\n");
            }
     
    }

}
