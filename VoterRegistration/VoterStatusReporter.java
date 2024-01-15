/**
 * Program to evaluate registered voters within a certain state (specified by user)
 * and compare to other states. This program will have multiple error checking.
 * There will be three political parties to check for (Republican, Democrat, and
 * other). User provides state name, state population, and number of registered
 * voters for the three specified parties. Program will calculate the percent of 
 * eligible voters who are registered and rate that compared to other states.
 * 
 */
import java.util.Scanner;
import java.util.InputMismatchException;
public class VoterStatusReporter {
   
   public enum RatingType {POOR, AVERAGE, ABOVE_AVERAGE, HIGH
   }

   /**
    * Calls setStateName() to set the user input to the name of the state,calls
    * setPopulation() to assign a number to the state population,  calls 
    * calcPercentRegistered() to calculate the percentage of eligible voters who
    * are registered to vote, calls deteremineRegistrationStatus() to evaluate
    * the percentage of registered voters, compared to other states. Method 
    * accounts for Illegal Argument Exception, Input Mismatch Exception, Arithmetic
    * Exception, and Runtime Exception.
    * 
    * @param args the command line arguments
    */
   public static void main(String[] args) {
      Scanner keyboard = new Scanner(System.in);
      
      String stateName;  // name of the state, given by user
      int statePop;  // population of the state, given by user
      boolean needPopulation = true; // to determine whether input for population 
                                     //is still needed
      VoterStatistics newVoter = new VoterStatistics();
      
      do {
         try {
         System.out.println("Enter the state name:");
         stateName = keyboard.nextLine();
         newVoter.setStateName(stateName);
         }
         catch (IllegalArgumentException iaeException) {
            System.out.println(iaeException.getMessage());
            System.out.println("State's name not set");
            System.out.println();
         }
         
      } while (newVoter.getStateName() == "unknown");
      
      do {
         try {
            System.out.println("Enter " + newVoter.getStateName() + " state population:");
            statePop = keyboard.nextInt();
            newVoter.setPopulation(statePop);
            System.out.println("Integer correctly entered for population");
            needPopulation = false;
         }

         catch (InputMismatchException imeException) {
            keyboard.nextLine();
            System.out.println("ERROR: Invalid non-integer value entered");
         }
      } while (needPopulation);
      
      newVoter.updateRegistrations(keyboard);
      
      System.out.println();
      
      try {
      System.out.println("In " + newVoter.getStateName() + ", " + 
            newVoter.calcPercentRegistered() + "% of the eligible voters are "
                  + "registered to vote"); 
      System.out.println(newVoter.getStateName() + " voter registration is " + 
            determineRegistrationStatus(newVoter.calcPercentRegistered()) + 
            " compared to other states");
      }
      catch (ArithmeticException aeException) {
         System.out.println("ERROR: " + aeException.getMessage());
         System.out.println("Program cannot continue -- Exiting");
      }
      catch (RuntimeException rtException) {
         System.out.println(rtException.getMessage());
      }
   }
   
   /**
    * Method to compare the state's voter registration with other states and determine
    * whether it is poor, average, above average or high.
    * 
    * @param percentEligible - integer representing percentage of eligible people
    * who are registered to vote
    * @return efficiency - the rating type associated with the percentage of
    * eligible people who are registered to vote, in comparison to the population
    */
   public static RatingType determineRegistrationStatus(int percentEligible) {
      RatingType efficiency;
      
      if (percentEligible < 55) {
         efficiency = RatingType.POOR; 
      }
      else if (percentEligible < 64) {
         efficiency = RatingType.AVERAGE; 
      }
      else if (percentEligible < 70) {
         efficiency = RatingType.ABOVE_AVERAGE; 
      }
      else {
         efficiency = RatingType.HIGH;
      }
      
   return efficiency;
  }
   
}
