import java.util.Scanner;
public class TrackTeamAnalyzer {
   static final int RUNNER_NOT_FOUND = -1;  //to indicate the runner is not found

   /**
    * Calls displayFastest(runnerId, runnerTime, numbersEntered) to display which
    * runner had the fastest run time. Calls findRunner(runnerIdSearch, runnerId,
    * numbersEntered) to allow user to search runner ID numbers until user stops. 
    * 
    * @param args the command line arguments
    */
   public static void main(String[] args) {
      Scanner scnr = new Scanner(System.in);
      final int MAXIMUM_RUNNERS = 12; //maximum runners, coach can change at later date
      final int STOPPING_ID = 9999; //user to enter after entering all runners IDs and times
      
      int i = 0;
      int numbersEntered; //total number of runners
      int runnerIdSearch = 0; //user entered number to search runner IDs
      int runnerIndex;
      double totalTime = 0.0; //all the runner times added up
      double averageTime = 0.0; //average time for the group of runners
      boolean stopIdReached = false; //when the user has entered 9999
      
      int[] runnerId = new int[MAXIMUM_RUNNERS];
      double [] runnerTime = new double [MAXIMUM_RUNNERS];
      
      do {
         System.out.println("Enter id number of runner (or 9999 to stop):");
         runnerId[i] = scnr.nextInt();
         if (runnerId[i] == 9999) {
            stopIdReached = true;
            runnerId[i] = 0;
         }
         else {
            System.out.println("Enter 400 meter run time for runner with id "
                  + "number " + runnerId[i] + ":");
            runnerTime[i] = scnr.nextDouble();
            ++i;
            if (i == MAXIMUM_RUNNERS) {
               System.out.println("Maximum of " + MAXIMUM_RUNNERS + " runners "
                     + "can be stored. You have reached the limit.");
               stopIdReached = true;
            }
         }
      } while (stopIdReached == false);
      
      numbersEntered = i;
      
      System.out.println();
      System.out.println("You entered the following " + numbersEntered + " runner id "
            + "numbers and run times:");
      for (i = 0; i < numbersEntered; ++i) {
         System.out.printf("%2d" , runnerId[i]);
         System.out.printf("  %2.2f\n" , runnerTime[i]);
         totalTime += runnerTime[i];
      }
      
      averageTime = totalTime / numbersEntered;
      
      System.out.println();
      System.out.print("Average 400 meter run time is ");
      System.out.printf("%2.2f seconds\n", averageTime);
      
      displayFastest(runnerId, runnerTime, numbersEntered);
      
      while (runnerIdSearch != STOPPING_ID) {
         System.out.println();
         System.out.println("Enter id number of one runner to find (or 9999 to "
               + "stop):");
         runnerIdSearch = scnr.nextInt();
         runnerIndex = findRunner(runnerIdSearch, runnerId, numbersEntered);
         
         if (runnerIdSearch != STOPPING_ID) {
            if (runnerIndex != RUNNER_NOT_FOUND) {
               System.out.print("Runner " + runnerId[runnerIndex] + " has a 400"
                  + " meter run time of ");
               System.out.printf("%2.2f seconds\n" , runnerTime[runnerIndex]);
            }
            else {
               System.out.println("No runner with id number " + runnerIdSearch);
            }
         }
      }     
   }
   
   /**
    * Method to display the runner(s) who had the fastest run time for the 400
    * meter run.
    * 
    * @param runnerId - array of all runners IDs
    * @param runnerTime - array of runner times in seconds for 400 meter run
    * @param numberOfRunners - the number of runners whose data was entered
    */
   public static void displayFastest (int [] runnerId, double [] runnerTime, 
         int numberOfRunners) {
      int i;
      double fastestTime;
      
      fastestTime = runnerTime[0];
      for (i = 0; i < numberOfRunners; ++i) {
         if (runnerTime[i] <= fastestTime) {
            fastestTime = runnerTime[i];
         }
      }
      
      System.out.print("Fastest 400 meter run time is ");
      System.out.printf("%2.2f seconds\n", fastestTime);
      
      System.out.println("   Id(s) of runners with fastest run time:");
      for (i = 0; i < numberOfRunners; ++i) {
         if (runnerTime[i] == fastestTime) {
            System.out.println("   " + runnerId[i]);
         }
      } 
   }
   
   /**
    * Method to allow the user to search runner IDs. If the user inputs a number
    * that is not found, the program will return the constant RUNNER_NOT_FOUND, 
    * otherwise the program will return which index number that runner is assigned
    * to for ID number and run time.
    * 
    * @param runnerIdNeeded - the personal ID of the runner
    * @param runnerId - array of all runner IDs
    * @param numberOfRunners - the number of runners whose data was entered
    * @return indexOfRunner - index number of the needed runner ID
    */
   public static int findRunner (int runnerIdNeeded, int [] runnerId, 
         int numberOfRunners) {
      int indexOfRunner = RUNNER_NOT_FOUND;
      int i = 0;
      boolean idFound = false;
      
      do {
         if (runnerId[i] == runnerIdNeeded) {
            indexOfRunner = i;
            idFound = true;
         }
         else {
            ++i;
         }
      } while ((idFound == false) && (i < numberOfRunners));
      
      return indexOfRunner;
   }   
}
