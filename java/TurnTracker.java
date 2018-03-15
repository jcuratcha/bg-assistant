import java.io.*;
import java.util.*;

public class TurnTracker {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String filename;
        int players = -1;

        // get document name
        filename = new Time().getDateTimeFileFormat();
        System.out.println("Default filename of output file will be \"" + filename + " {subtitle}\"");
        System.out.print("Add subtitle (leave blank if no)? ");
        if (input.nextLine().trim().equals("") == false) {
            filename += " " + input.nextLine().trim();
        }
        filename += ".csv";
        
        // get number of players
        while (players < 0) {
            System.out.print("Number of players (default = 3): ");
            String next = input.nextLine();
            if (isInteger(next) == true) {
                players = Integer.parseInt(next);
            } else if (next.trim().equals("") == true) {
                players = 3;
            }
        }

        System.out.println("\nYou will save output in \"" + filename + "\"" + " and the number of players is " + players + ". ");
        System.out.println("\nInstructions:");
        System.out.println("  - Press 'Enter' advance after every turn.");
        System.out.println("      > Timer will increment to next player's turn immediately.");
        System.out.println("  - To quit recording turns and save results to file, enter 'q'.");
        System.out.println("      > Game will quit immediately and results will be printed to file.");
        System.out.println("  - To pause the turn timer, enter 'p'");
        System.out.println("      > The current turn will end, and the next turn will begin once timer is resumed.");
        System.out.println("\nWhen ready to begin, press 'Enter'");
        input.nextLine();

        int turnCounter = 0;
        int playerCounter = 0;
        String command = "";
        ArrayList<String> results = new ArrayList<String>();

        // add headers to csv file
        results.add("turn,player,start,end,turn");

        do {
            turnCounter++;
            if (playerCounter == players) {
                playerCounter = 1;   
            } else {
                playerCounter++;
            }
            
            Time startTime = new Time();
            System.out.print(turnCounter + ": P" + playerCounter + " start: " + startTime.getTime() + "  ");
            
            // wait until user hits enter again
            command = input.nextLine().trim();
            Time endTime = new Time();

            // format results into csv line and add to arraylist
            String newLine = Integer.toString(turnCounter) + ",";
            newLine += Integer.toString(playerCounter) + ",";
            newLine += startTime.getTime() + ",";
            newLine += endTime.getTime() + ",";
            newLine += endTime.getDifference(startTime);
            results.add(newLine);

            // check if user enter p to pause the timer
            if (command.equals("p") == true) {
                System.out.println("P" + playerCounter + " end: " + endTime.getTime() + "  ");
                System.out.println("PAUSED.");
                System.out.print("When ready to continue with next turn, press 'Enter'");
                command = input.nextLine().trim();
            }
        } while (command.equals("q") == false);

        // try to save results to file, if error then dump results to terminal so it can be copied directly
        System.out.println("\nSaving results to " + filename);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filename), true));

            for(int i=0; i<results.size(); i++) {
                writer.write(results.get(i)+"\n");
            }

            writer.close();
            
        } catch (Exception e) {
            System.out.println();
            e.printStackTrace();
            System.out.println("\nError while trying to save file.  Dumping csv file to terminal.");
            for(int i=0; i<results.size(); i++) {
                System.out.println(results.get(i));
            }
        }

        System.out.println("\nEnd of processing.");
    }

    public static boolean isInteger( String input ) {
        try {
            Integer.parseInt( input );
            return true;
        }
        catch( Exception e ) {
            return false;
        }
    }
}