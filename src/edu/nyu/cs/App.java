package edu.nyu.cs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * A program to analyze the use of verbal tics in any transcribed text.
 * Complete the functions to perform the tasks indicated in the comments.
 * Refer to the README.md file for additional requirements and helpful hints.
 */
public class App {

  // use this "global"-ish Scanner variable when getting keyboard input from the user within any function; this avoids common problems using several different Scanners within different functions
  public static Scanner scn = new Scanner(System.in);

  /**
   * The main function is automatically called first in a Java program.
   * This function contains the main logic of the program that makes use of all the other functions to solve the problem.
   * This main function MUST make use of the other functions to perform the tasks those functions are designed for, i.e.:
   * - you must use the getFilepathFromUser() to get the file path to the text file that the user wants to analyze
   * - you must use the getContentsOfFile() function whenever you need to get the contents of the text file
   * - you must use the getTicsFromUser() function whenever you need to get the set of tics the user wants to analyze in the text
   * - you must use the countOccurrences() function whenever you want to count the number of occurrences of a given tic within the text
   * - you must use the calculatePercentage() function whenever you want to calculate the percentage of all tics in the text that a given tic consumes
   * - you must use the calculateTicDensity() function to calculate the proportion of all words in the text that are tic words
   * 
   * @param args An array of any command-line arguments.
   */
  public static void main(String[] args) throws Exception {
    
    // complete this function according to the instructions
    System.out.println("What file would you like to open?");
    String filePath = getFilepathFromUser();
    String fullText = getContentsOfFile(filePath);
    System.out.println("What words would you like to search for?");
    String[] tics = getTicsFromUser();
    System.out.println("\n...............................Analyzing text.................................\n");
    int count = 0;
    for (int i=0; i<tics.length; i++) {
      int temp = countOccurrences(tics[i], fullText);
      count += temp;
    }
    double density = calculateTicDensity(tics, fullText);
    System.out.println("Total number of tics: "+count);
    System.out.println("Density of tics: "+density);
    System.out.println("\n...............................Tic breakdown..................................\n");
    for (int j=0; j<tics.length; j++) {
      System.out.println(String.format("%10s", tics[j].toLowerCase())+"/ "+ String.format("$19s", countOccurrences(tics[j], fullText)+" occurrences") + "/ "+calculatePercentage(countOccurrences(tics[j], fullText), count)+"% of all tics");
    }
  }

  /**
   * getFilepathFromUser method
   * Asks the user to enter the path to the text file they want to analyze.
   * Hint:
   *  - use the "global"-ish Scanner variable scn to get the response from the user, rather than creating a new Scanner variable within this function.
   *  - do not close the "global"-ish Scanner so that you can use it in other functions
   * @return The file path that the user enters, e.g. "data/trump_speech_010621.txt"
   */
  public static String getFilepathFromUser() {

    // complete the getFilepathFromUser function according to the instructions above
    String fileName = scn.nextLine();
    return fileName;
  }


  /**
   * getContentsOfFile method
   * Opens the specified file and returns the text therein.
   * If the file can't be opened, print out the message, "Oh no... can't find the file!"
   * @param filename The path to a text file containing a speech transcript with verbal tics in it.
   * @return The full text in the file as a String.
   */
  public static String getContentsOfFile(String filepath) {

    // the code in this function is given to you as a gift... don't change it.

    String fullText = "";
    // opening up a file may fail if the file is not there, so use try/catch to protect against such errors
    try {
      // try to open the file and extract its contents
      Scanner scn = new Scanner(new File(filepath));
      while (scn.hasNextLine()) {
        String line = scn.nextLine();
        fullText += line + "\n"; // nextLine() removes line breaks, so add them back in
      }
      scn.close(); // be nice and close the Scanner
    }
    catch (FileNotFoundException e) {
      // in case we fail to open the file, output a friendly message.
      System.out.println("Oh no... can't find the file!");
    }
    return fullText; // return the full text
  }
  
 /**
   * getTicsFromUser method
   * Asks the user to enter a comma-separated list of tics, e.g. "uh,like, um, you know,so", and returns an array containing those tics, e.g. { "uh", "like", "um", "you know", "so" }.
   * Hint:
   *  - use the "global"-ish Scanner variable scn to get the response from the user, rather than creating a new Scanner variable ithin this function.
   *  - do not close the "global"-ish Scanner so that you can use it in other functions
   * @return A String array containing each of the tics to analyze, with any leading or trailing whitespace removed from each tic.
   */
  public static String[] getTicsFromUser() {
    // write the getTicsFromUser function according to the instructions
    String ticString = scn.nextLine();
    String[] tics = ticString.split("\\s*,\\s*");
    return tics;
  }

 /**
   * countOccurrences method
   * Counts how many times a given string (the needle) occurs within another string (the haystack), ignoring case.
   * @param needle The String to search for.
   * @param haystack The String within which to search.
   * @return THe number of occurrences of the "needle" String within the "haystack" String, ignoring case.
   */
  public static int countOccurrences(String needle, String haystack) {
    // write the countOccurrences function according to the instructions
    haystack = haystack.toLowerCase();
    needle = needle.toLowerCase();
    // use the same method as the second test to create array and iterate through it
    String[] haystackAry = haystack.split("[ \n\t.,?!]");
    int count = 0;
    for (int i=0; i<haystackAry.length; i++) {
      if (haystackAry[i].equals(needle)) {
        count += 1;
      }
    }
    return count;
  }
  /**
   * calculatePercentage method
   * Calculates the equivalent percentage from the proportion of one number to another number.
   * @param num1 The number to be converted to a percentage.  i.e. the numerator in the ratio of num1 to num2.
   * @param num2 The overall number out of which the num1 number is taken.  i.e. the denominator in the ratio of num1 to num2.
   * @return The percentage that rum1 represents out of the total of num2, rounded to the nearest integer.
   */
  public static int calculatePercentage(int num1, int num2) {
    int rounded = (int) Math.round(100*num1/num2);
    return rounded;
  }
    // write the calculatePercentage function according to the instructions above


  /**
   * calculateTicDensity method
   * Calculates the "density" of tics in the text.  In other words, the proportion of tic words to the total number of words in a text.
   * Hint:
   *  - assume that words in the text are separated from one another by any of the following characters: space ( ), line break (\n), tab (\t), period (.), comma (,), question mark (?), or exclamation mark (!)
   *  - all Strings have a .split() method which can split by any of a collection of characters given as an argument;  This function returns an array of the remaining text that was separated by any of those characters
   *  - e.g. "foo-bar;baz.bum".split("[-;.]+") will result in an array with { "foo", "bar", "baz", and "bum" } as the values.
   * @param tics An array of tic words to analyze.
   * @param fullText The full text.
   * @return The proportion of the number of tic words present in the text to the total number of words in the text, as a double.
   */
  public static double calculateTicDensity(String[] tics, String fullText) {
    String[] fullStrings = fullText.split("[\\s\n\t.,?!]+");
    int count = 0;
    for (int i=0; i<fullStrings.length; i++) {
      for (int j=0; j<tics.length; j++) {
        if (tics[j].toLowerCase().equals(fullStrings[i].toLowerCase())) {
          count += 1;
        }
      }
    }
    double proportion = (double) count/fullStrings.length;
    String proportionStr = String.format("%.2f", proportion);
    proportion = Double.parseDouble(proportionStr);
    return proportion;
  }
    // write the calculateTicDensity function according to the instructions above

    

} // end of class