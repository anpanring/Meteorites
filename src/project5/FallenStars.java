package project5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class is the actual program.
 * It both parses the CSV file for Meteorite data and allows the user to input values to get
 * data back. For example, user can enter "year YEAR" and program will print list of Meteorite
 * objects that share the specified year.
 * The program is executed with the filename of a document containing data on the meteorites as
 * the single command line argument.
 *
 * @author Jack
 */
public class FallenStars {

    /**
     * The main() method of the program.
     * @param args: a data file provided on the command line when the program is started.
     * File should contain data on meteorites, including names, id, masses, year, and location.
     */
    public static void main(String[] args) {
        //checks if args is provided
        if (args.length == 0) {
            System.err.println("Usage Error: the program expects file name as an argument.");
            System.exit(1);
        }

        //checks if file exists
        File metFile = new File(args[0]);
        if (!metFile.exists()) {
            System.err.println("Error: the file " + metFile.getAbsolutePath() + " does not exist.\n");
            System.exit(1);
        }

        //checks if file is readable
        if (!metFile.canRead()) {
            System.err.println("Error: the file " + metFile.getAbsolutePath() +
                    " cannot be opened for reading.\n");
            System.exit(1);
        }

        //open file for reading
        Scanner meteors = null;

        //checks to see if file can be opened
        try {
            meteors = new Scanner(metFile);
        } catch (FileNotFoundException e) {
            System.err.println("Error: the file "+metFile.getAbsolutePath()+
                    " cannot be opened for reading.\n");
            System.exit(1);
        }

        //parsing through data and adding to MeteoriteData
        MeteoriteData meteorList = new MeteoriteData();
        String line = null;
        ArrayList<String> meteorData = new ArrayList<String>();
        String mName = null;
        int mId = 0;
        int mMass = 0;
        int mYear = 0;
        Location mLoc = null;
        Meteorite current = null;
        meteors.nextLine();
        while (meteors.hasNextLine()) {
            //getting data from array created by splitCSVLine
            try {
                line = meteors.nextLine();
                meteorData = splitCSVLine(line);
                mName = meteorData.get(0);
                mId = Integer.parseInt(meteorData.get(1));
                mMass = Integer.parseInt(meteorData.get(4));
                mYear = Integer.parseInt(meteorData.get(6).substring(6, 10));
                mLoc = new Location(Double.parseDouble(meteorData.get(7)), Double.parseDouble(meteorData.get(8)));
            }
            catch (Exception ex) {
                //caused by an incomplete or miss-formatted line in the input file
                continue;
            }
            //setting new values for Meteorite object
            try {
                current = new Meteorite(mName.trim(), mId);
                current.setMass(mMass);
                current.setLocation(mLoc);
                current.setYear(mYear);
                meteorList.add(current);
            }
            catch (IllegalArgumentException ex ) {
                //ignore this exception and skip to the next line
                continue;
            }
        }

        //Interaction with user
        System.out.println("Search the database by using one of the following queries.\n");
        System.out.println("To search for meteorite nearest to a given geo-location, enter\n" +
                "   location LATITUDE LONGITUDE");
        System.out.println("To search for meteorites that fell in a given year, enter\n" +
                "   year YEAR");
        System.out.println("To search for meteorites with weights MASS +/- 10 grams, enter\n" +
                "   mass MASS");
        System.out.println("To finish the program, enter\n" +
                "   quit");

        //initializing Scanner for user input
        Scanner userInput  = new Scanner(System.in);
        String userValue = "";

        //actual loop for user interaction
        do {
            System.out.println("Enter your search query:\n");
            //get value from the user
            userValue = userInput.next();
            if (!userValue.equalsIgnoreCase("quit")) {
                if(userValue.equals("location")){
                    try{
                        System.out.println(meteorList.getByLocation(new Location(
                                userInput.nextDouble(), userInput.nextDouble())));
                    } catch(NoSuchElementException e){
                        System.out.println("This is not a valid location. Try again.\n");
                        userInput.nextLine();
                    }
                }
                else if(userValue.equals("year")){
                    try{
                        int tempYear = userInput.nextInt();
                        if(meteorList.getByYear(tempYear) == null){
                            System.out.println("No matches found. Try again.\n"); //!!!!!!!!!
                        }
                        else{
                            System.out.println(meteorList.getByYear(tempYear));;
                        }
                    } catch(Exception e){
                        System.out.println("This is not a valid year. Try again.\n");
                        userInput.nextLine();
                    }
                }
                else if(userValue.equals("mass")){
                    try{
                        int tempMass = userInput.nextInt();
                        if(meteorList.getByMass(tempMass, 10) == null){
                            System.out.println("No matches found. Try again.\n");
                        }
                        else System.out.println(meteorList.getByMass(tempMass, 10));
                    } catch(Exception e){
                        System.out.println("This is not a valid mass. Try again.\n");
                        userInput.nextLine();
                    }
                }
                else {
                    System.out.println("This is not a valid query. Try again.\n");
                    userInput.nextLine();
                }
            }
        } while (!userValue.equalsIgnoreCase("quit"));

        userInput.close();
    }

    /**
     * Splits the given line of a CSV file according to commas and double quotes
     * (double quotes are used to surround multi-word entries so that they may contain commas)
     * @author Joanna Klukowska
     * @param textLine  a line of text from a CSV file to be parsed
     * @return an ArrayList object containing all individual entries found on that line;
     *  any missing entries are indicated as empty strings; null is returned if the textline
     *  passed to this function is null itself.
     */
    public static ArrayList<String> splitCSVLine(String textLine){

        if (textLine == null ) return null;

        ArrayList<String> entries = new ArrayList<String>();
        int lineLength = textLine.length();
        StringBuffer nextWord = new StringBuffer();
        char nextChar;
        boolean insideQuotes = false;
        boolean insideEntry= false;

        // iterate over all characters in the textLine
        for (int i = 0; i < lineLength; i++) {
            nextChar = textLine.charAt(i);

            // handle smart quotes as well as regular quotes
            if (nextChar == '"' || nextChar == '\u201C' || nextChar =='\u201D') {

                // change insideQuotes flag when nextChar is a quote
                if (insideQuotes) {
                    insideQuotes = false;
                    insideEntry = false;
                }
                else {
                    insideQuotes = true;
                    insideEntry = true;
                }
            }
            else if (Character.isWhitespace(nextChar)) {
                if ( insideQuotes || insideEntry ) {
                    // add it to the current entry
                    nextWord.append( nextChar );
                }
                else { // skip all spaces between entries
                    continue;
                }
            }
            else if ( nextChar == ',') {
                if (insideQuotes){ // comma inside an entry
                    nextWord.append(nextChar);
                }
                else { // end of entry found
                    insideEntry = false;
                    entries.add(nextWord.toString());
                    nextWord = new StringBuffer();
                }
            }
            else {
                // add all other characters to the nextWord
                nextWord.append(nextChar);
                insideEntry = true;
            }

        }
        // add the last word ( assuming not empty )
        // trim the white space before adding to the list
        if (!nextWord.toString().equals("")) {
            entries.add(nextWord.toString().trim());
        }

        return entries;
    }
}