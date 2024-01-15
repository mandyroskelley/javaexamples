import java.util.Scanner;
import java.util.InputMismatchException;
public class VoterStatistics {
   public static final int STATE_MAX_CHARS = 14;  //maximum characters for state name
   public static final int PERCENT_POP_ELIGIBLE = 76; // to account for the 24% of the
                                                      //population under voting age
   private String stateName; // name of state for voter statistics
   private int population; // state population
   private int numRepublicans; // number of registered Republican voters
   private int numDemocrats;  // number of registered Democrat voters
   private int numOther;  // number of registered other voters (not Republican or Democrat)
   
   /**
    * Constructor to set the default value for state name, population, number of
    * Republican voters, number of Democrat voters and number of other voters. 
    */
   public VoterStatistics() {
      stateName = "unknown";
      population = 0;
      numRepublicans = 0;
      numDemocrats = 0;
      numOther = 0;
}
   /**
    * Method to retrieve the state name.
    * 
    * @return stateName - name of the state
    */
   public String getStateName() {
     return stateName;
   }
   /**
    * Method to retrieve the population of the state
    * 
    * @return population - population of the state
    */
   public int getPopulation() {
     return population;
   }
   /**
    * Method to retrieve the number of Republican voters.
    * 
    * @return numRepublicans - the number of Republican voters
    */
   public int getNumRepublicans() {
     return numRepublicans;
   }
   /**
    * Method to retrieve the number of Democrat voters.
    * 
    * @return numDemocrats - the number of Democrat voters
    */
   public int getNumDemocrats() {
     return numDemocrats;
   }
   /**
    * Method to retrieve the number of other voters.
    * 
    * @return numOther - the number of other voters
    */
   public int getNumOther() {
     return numOther;
   }
   /**
    * Method to set the name of the state. This method will also correct the 
    * casing of the user's input so that the first letter of each word of the state
    * is capitalized.
    */
   public void setStateName(String stateName) {
      int whiteSpace = 0; // to count how many times there is white space in input
      int whiteSpaceIndex = 0; // if there is white space, what the index of it is
      char firstLetter; //to locate first letter to change to upper case
      char upperCase; // to locate letter after white space to change to upper case
      String twoWordName; // to use when there is white space, to correct case the letters
      
      if (stateName.length() > STATE_MAX_CHARS) {
         throw new IllegalArgumentException ("ERROR: State name cannot have more"
               + " than 14 characters in it");
      }
      
      try {
         for (int i = 0; i < stateName.length(); ++i) {
            if (!(stateName.charAt(i) >= 'A' && stateName.charAt(i) <= 'Z') &&
                  !(stateName.charAt(i) >= 'a' && stateName.charAt(i) <= 'z') &&
                  (stateName.charAt(i) != ' ')) {
               throw new IllegalArgumentException ("ERROR: Invalid character '" 
                     + stateName.charAt(i) + "' is not allowed!");
            }
            
            if (stateName.charAt(i) == ' ') {
               whiteSpaceIndex = i + 1;
               ++whiteSpace;
               if (whiteSpace > 1) {
                  throw new IllegalArgumentException ("ERROR: No more than one "
                     + "space is allowed!"); 
               }
            } 
         }
         
         stateName = stateName.toLowerCase();
         upperCase = Character.toUpperCase(stateName.charAt(whiteSpaceIndex));
         twoWordName = upperCase + stateName.substring(whiteSpaceIndex + 1, stateName.length());
         stateName = stateName.substring(0, whiteSpaceIndex) + twoWordName;
         firstLetter = Character.toUpperCase(stateName.charAt(0));
         stateName = stateName.substring(1, stateName.length());
         stateName = firstLetter + stateName;
         this.stateName = stateName; 
      }
      
      catch (IllegalArgumentException iaeException) {
         System.out.println(iaeException.getMessage());
         System.out.println("State's name cannot be set to \"" + stateName +  "\"");
         System.out.println();
      }
   }
   /**
    * Method to set the number for the population of the state.
    */
   public void setPopulation(int population) {
      this.population = population;
   }
   /**
    * Method to set the number for the number of Republican voters.
    */
   public void setNumRepublicans(int numRepublicans) {
      this.numRepublicans = numRepublicans;
   }
   /**
    * Method to set the number for the number of Democrat voters.
    */
   public void setNumDemocrats(int numDemocrats) {
      this.numDemocrats = numDemocrats;
   }
   /**
    * Method to set the number for the number of other voters.
    */
   public void setNumOther(int numOther) {
      this.numOther = numOther;
   }
   /**
    * Method to read user input of number of registrations for the specific
    * political party and give error codes based on specific errors of user
    * input. If user inputs a valid number, method returns that number for 
    * the number of registrations. Method accounts for two exceptions regarding
    * incorrect user input and Input Mismatch Exception.
    * 
    * @param keyboard - scanner object for user to input data
    * @param party - political party, three options to choose from (Republican,
    * Democrat, and other)
    * @return numRegistrations - number of people registered per specified
    * political party
    */
   public int readPartyRegistrations(Scanner keyboard, String party ) {
      int numRegistrations = -1; //number of voters registered per party
      
      while (numRegistrations == -1) {
         try {System.out.println("Enter number of " + party + " registrations:");
            numRegistrations = keyboard.nextInt();

            if (numRegistrations < 0) {
               numRegistrations = -1;
               throw new Exception ("ERROR: Cannot be negative");
            }
            if (numRegistrations > getPopulation()) {
               numRegistrations = -1;
               throw new Exception("ERROR: Cannot be more than " + getStateName() +
                     " population of " + getPopulation());
            }
         }
         catch (InputMismatchException imeException) {
            keyboard.nextLine();
            System.out.println("ERROR: Must be an integer value");
         }
         catch (Exception numException) {
            keyboard.nextLine();
            System.out.println(numException.getMessage());
         }
      }
      return numRegistrations;
   }
   /**
    * Method to assign number of registered voters to each specific party - three
    * party options (Republican, Democrat, and other).
    * 
    * @param keyboard - scanner object for user to input data
    */
   public void updateRegistrations(Scanner keyboard) {
      String party; //name of political party - Republican, Democrat or other
      int numRegistrations; //number of voters registered per party
      
      party = "Republican";
      numRegistrations = readPartyRegistrations(keyboard, party);
      setNumRepublicans(numRegistrations);
      
      party = "Democrat";
      numRegistrations = readPartyRegistrations(keyboard, party);
      setNumDemocrats(numRegistrations);
      
      party = "other";
      numRegistrations = readPartyRegistrations(keyboard, party);
      setNumOther(numRegistrations);
   }
   /**
    * Method to calculate the percentage of registered voters compared to the
    * number of eligible voters in the state's population. Method accounts for
    * Runtime Exception.
    * 
    * @return percentRegistered - integer to represent the percent of eligible voters
    * who are registered
    */
   public int calcPercentRegistered() {
      int percentRegistered; //percentage of eligible voters who are registered to vote
      int votingPopulation; // 76% of the total population, to account for minors
      
      votingPopulation = getPopulation();
      votingPopulation = (votingPopulation * PERCENT_POP_ELIGIBLE) / 100;
      percentRegistered = getNumRepublicans() +getNumDemocrats() + getNumOther();
      
      if (percentRegistered > votingPopulation) {
         throw new RuntimeException ("DATA ERROR: " + getStateName() + 
               " registrations exceed number of eligible voters");
      }
      percentRegistered = (percentRegistered * 100) / votingPopulation;
      
      return percentRegistered;
   }
 }
