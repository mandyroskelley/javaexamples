/**
 * Program to create a game of Chutes and Ladders, using a file that will contain
 * the layout of the game board. Players have option of a random mode for game
 * play. This program will handle some exceptions and will end when a winner is
 * declared or when the maximum number of terms is reached, whichever is first.
 * This program uses object class Player.java. Program is modified to allow user
 * to choose number of players and program will display a visual of where each
 * player is and how close they are to the finish.
 *
 * @author Amanda Roskelley
 * @version 2, assn 13
 */
import java.util.Random;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
public class ChutesAndLadders {
   
   /**
    * Calls createGameBoard(gameFile) to create the game board, calls
    * writeLogFile(gameFile, gameBoard) to create a new file containing the
    * square locations of the chutes and of the ladders, calls setName() to set
    * the players first name, calls getName() to retrieve the player's name, calls
    * oneTurnMove() to allow each player to have a turn during the game, calls
    * getLocation() to retrieve the square location of the specific player in the
    * game.
    * 
    * @param args the command line arguments
    */
   public static void main(String[] args) {
      final int HIGHEST_ROLL = 6; // the maximum number on a standard die
      final int MAXIMUM_TURNS = 100; // game ends if 100 turns in reached without 
                                       //someone winning
      
      Scanner keyboard = new Scanner(System.in);
      int [] gameBoard = null; // array for the gameboard
      Player [] gamePlayers = null;
      int randomPlay; // number to allow user to decide if they want to play random play
      int turnNumber = 1; // the number representing which turn the players are on
      int dieRoll; // number associated with a six-sided die
      int numberOfPlayers;
      String gameFile = null; // name of the game file
      String logFileName; // name of the game log file
      String name = null; // first name of the player
      boolean playingRandom; // to determine if the players are playing random or not
      boolean gamePlaying = true; // to decide if the game is still playing or if it is over
      
      Random randGen;
      
      
      try {
         System.out.println("Enter gameboard data input filename:");
         gameFile = keyboard.nextLine();
         gameBoard =  createGameboard(gameFile);
         
         logFileName = writeLogFile(gameFile, gameBoard);
         if (logFileName != "null") {
            System.out.println("Log file " + logFileName  + 
                  " successfully created.");
         }
         else {
            throw new Exception ("Program will continue without a log file");
         }
         
         System.out.println();
         System.out.println("How many people will be playing?");
         numberOfPlayers = keyboard.nextInt();
         keyboard.nextLine();
         
         gamePlayers  = new Player[numberOfPlayers];
         
         for (int i = 0; i < gamePlayers.length; ++i) {
            System.out.println("First name of player " + (i+1) + "?");
            name = keyboard.nextLine();
            gamePlayers[i] = new Player();
            gamePlayers[i].setName(name);
         }
         
         System.out.println();
         System.out.println("Chutes and Ladders game for players ");
         for (int i = 0; i < gamePlayers.length; ++i) {
            System.out.println("Player "+ (i + 1) + ": " + gamePlayers[i].getName());
         }
         
         
         System.out.println();
         System.out.println("Enter a specific integer for testing (or 0 for random play):");
         randomPlay = keyboard.nextInt();
         if (randomPlay == 0) {
            randGen = new Random();
            System.out.println("Interactive random play chosen!");
            playingRandom = true;
            keyboard.nextLine();
         }
         else {
            randGen = new Random();
            randGen.setSeed(randomPlay);
            System.out.println("Controlled play for testing chosen.");
            playingRandom = false;
         }
         
         while (gamePlaying) {
            System.out.println();
            System.out.println("TURN "+ turnNumber + ":");

            for (int i = 0; i < numberOfPlayers; ++i) {
               if (gamePlaying) {
                  if (playingRandom) {
                    System.out.println(gamePlayers[i].getName().toUpperCase() +
                              " - press ENTER to ROLL");
                        keyboard.nextLine();
                        dieRoll = randGen.nextInt(HIGHEST_ROLL) + 1;
                        gamePlayers[i].oneTurnMove(dieRoll, gameBoard);
                        if (gamePlayers[i].getLocation() == gameBoard.length - 1) {
                           System.out.println();
                           System.out.println(gamePlayers[i].getName().toUpperCase()
                                 + " WON!");
                           gamePlaying = false;
                        }
                     }         
                  else {

                     dieRoll = randGen.nextInt(HIGHEST_ROLL) + 1;
                     gamePlayers[i].oneTurnMove(dieRoll, gameBoard);
                     if (gamePlayers[i].getLocation() == gameBoard.length - 1) {
                        System.out.println();
                        System.out.println(gamePlayers[i].getName().toUpperCase() + " WON!");
                        gamePlaying = false;
                     }

                  }   
               }
            }   
               
            if (turnNumber == MAXIMUM_TURNS) {
               System.out.println();
               System.out.println("NOBODY CAN WIN.  Program exiting.");
               gamePlaying = false;
            }
            
            System.out.println();
            System.out.println("Player location status:");
            for (int i = 0; i < gamePlayers.length; ++i ) {
               for (int j = 1; j < gameBoard.length; ++j) {
                  if (gamePlayers[i].getLocation() == j) {
                     System.out.print((i + 1)); 
                  }
                  else if (j % 2 == 0) {
                     System.out.print("o");
                  }
                  else {
                     System.out.print("O");
                  }
               }
               System.out.println();
            }   
            
            ++turnNumber;
         }
         
      }
      catch (IOException ioeException) {
         System.out.println(ioeException.getMessage());
         System.out.println("Cannot play without gameboard, so program exiting");
      }
      
      catch (Exception exception) {
         System.out.println(exception.getMessage());
      }
      
   }
   /**
    * Method to read file and create array for game board. Method will also display
    * number of game board squares, number of chute squares and number of ladder
    * squares.
    * 
    * @param gameFile - file containing data on game board squares
    * @ return gameBoard - array of game board squares
    */
   public static int[] createGameboard(String gameFile) throws IOException {
      int[] gameBoard = null;
      int gameBoardSize;
      int gameSpace;
      int spaceNum;
      int chuteSquares = 0;
      int ladderSquares = 0;
      
      FileInputStream inFile = new FileInputStream(gameFile);
      Scanner readFile = new Scanner(inFile);
      
      gameBoardSize = readFile.nextInt();
      gameBoard = new int[gameBoardSize];
      
      while (readFile.hasNext()) {
         spaceNum = readFile.nextInt();
         gameSpace = readFile.nextInt();
         gameBoard[spaceNum] = gameSpace;
         if (gameSpace > 0) {
            ++ladderSquares;
         }
         else {
            ++chuteSquares;
         }
      }  
      
      inFile.close();
      
      System.out.println("Gameboard setup with " + (gameBoardSize - 1) + " squares");
      System.out.println("  " + chuteSquares + " squares have a chute");
      System.out.println("  " + ladderSquares + " squares have a ladder");
      System.out.println();
      
      return gameBoard;
   }
   /**
    * Method to create a new file, the log file, to have the data of all the
    * square locations of the chutes and the ladders.
    * 
    * @param fileName - file containing data on game board squares
    * @param gameBoard - array of game board squares
    * @return logFileName - name of log file associated with file for game
    */
   public static String writeLogFile(String fileName, int[] gameBoard){
      FileOutputStream outFile = null;
      PrintWriter printFile = null;
      String logFileName = null;
      int periodFinder;
      
      try {
         periodFinder = fileName.indexOf('.');
         logFileName = fileName.substring(0, periodFinder) + "_log.txt" ;
         
         outFile = new FileOutputStream(logFileName);
         printFile = new PrintWriter(outFile);
         for (int i = 0; i < gameBoard.length; ++i) {
            if (gameBoard[i] > 0) {
               printFile.println("Ladder at square " + i);
            }
            else if (gameBoard[i] < 0) {
               printFile.println("Chute at square " + i); 
            }
         }
         printFile.close();
      }
      
      catch (IOException ioeException) {
         logFileName = null;
         System.out.println("ERROR! Cannot create log file.");
      }
      
       return logFileName;
   }
   
}
