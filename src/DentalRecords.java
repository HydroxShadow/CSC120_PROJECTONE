import java.util.Scanner;
import java.util.InputMismatchException;

/**
 *
 * This class is a program written for the Floridian Tooth Records that prompts the amount
 * of people in the family and their tooth info to then be able to print the information, extract the tooth,
 * do a root canal, or leave the program entirely at the will of the user.
 *
 * @author Kevin Wang
 *
 */

public class DentalRecords {

    static final int MAX_PEOPLE = 6;
    static final int MAX_TEETH = 8;

    private static final Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the Floridian Tooth Records");
        System.out.println("--------------------------------------");

        // Array to store tooth information for the family members
        String[] familyNames = new String[MAX_PEOPLE];
        char[][][] familyTeeth = new char[MAX_PEOPLE][2][MAX_TEETH];

        int numPeople = getValidNumberOfFamilyMembers(keyboard);
        int memberIndex = 0;

        for (memberIndex = 0; memberIndex < numPeople; memberIndex++) {
            System.out.print("Please enter the name for family member " + (memberIndex + 1) + ": ");
            familyNames[memberIndex] = keyboard.next();
            System.out.println("Please enter the uppers for " + familyNames[memberIndex] + ": ");
            familyTeeth[memberIndex][0] = getValidTeethString(keyboard);
            System.out.println("Please enter the lowers for " + familyNames[memberIndex] + ": ");
            familyTeeth[memberIndex][1] = getValidTeethString(keyboard);
        }//End of memberIndex for loop


        // Interprets input for 'P' 'E' 'R' and 'X' and executes the appropriate methods
        boolean exit = false;
        while (!exit) {
            System.out.println("\n(P)rint, (E)xtract, (R)oot, e(X)it:");
            char option = keyboard.next().charAt(0);
            switch (Character.toUpperCase(option)) {
                case 'P':
                    printTeethRecord(familyNames, familyTeeth, numPeople);
                    break;
                case 'E':
                    extractTooth(keyboard, familyNames, familyTeeth, numPeople);
                    break;
                case 'R':
                    calculateRootCanalIndices(familyTeeth, numPeople);
                    break;
                case 'X':
                    exit = true;
                    System.out.println("Exiting the Floridian Tooth Records :-)");
                    break;
                default:
                    System.out.println("Invalid option, try again.");
            }//End of switch statement
        }//End of while loop

        keyboard.close();
    }//End of main

    /**
     * This method returns the number of people in the family; if the number is invalid, ask for that value again
     *
     * @param scanner This input (Scanner) asks the user for a value to be the number of people in the family
     * @return numPeople Returns the number of people in the family
     */

    public static int getValidNumberOfFamilyMembers(Scanner scanner) {
        int numPeople = 0;
        while (numPeople < 1 || numPeople > MAX_PEOPLE) {
            System.out.print("Please enter number of people in the family (1-6): ");
            try {
                numPeople = scanner.nextInt();
                if (numPeople < 1 || numPeople > MAX_PEOPLE) {
                    System.out.println("Invalid number of people, try again.");
                } // End of try statement
            } catch (InputMismatchException exception) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // clear invalid input
            } // End of catch statement
        } // End of while loop
        return numPeople;
    } // End of getValidNumberOfFamilyMemebers method


    /**
     * This method prompts tooth type (Incisors [I], Bicuspids [B], or Missing [M])
     * Input is then checked to see if the number of teeth exceeds MAX_TEETH or if it's an invalid tooth type
     * If invalid, it prints out the error and prompts the user again
     *
     * @param scanner Prompts the user for tooth information (I, B, or M)
     * @return Stores the teeth information to array back in main method or return invalid if erroneous input is given
     */

    public static char[] getValidTeethString(Scanner scanner) {
        String teeth;
        while (true) {
            teeth = scanner.next();
            teeth = teeth.toUpperCase();
            if (teeth.length() > MAX_TEETH) {
                System.out.println("Too many teeth, try again.");
            } else if (teeth.matches("[IBM]*")) {
                return teeth.toCharArray();
            } else {
                System.out.println("Invalid teeth types, try again.");
            } // End of if-else statemnt
        } // End of while statement
    } // End of getValidTeethString method

    // Get a valid string of teeth types (I, B, M)


    /**
     * This method will print the records for upper and lowers based on information provided from the user
     *
     * @param familyNames Draws family name information from stored array
     * @param familyTeeth 3D Array that draws from 1D. Corresponding family member, 2. Upper/Lower(0,1), 3. Tooth type
     * @param numPeople Number of family members from the familyNames array
     */

    public static void printTeethRecord(String[] familyNames, char[][][] familyTeeth, int numPeople) {
        int personIndex = 0; // Rename i and move i outside the for loop
        int teethFamilial = 0;

        for (personIndex = 0; personIndex < numPeople; personIndex++) {
            System.out.println(familyNames[personIndex]);
            System.out.print("  Uppers: ");
            for (teethFamilial = 0; teethFamilial < familyTeeth[personIndex][0].length; teethFamilial++) {
                System.out.print((teethFamilial + 1) + ":" + familyTeeth[personIndex][0][teethFamilial] + " ");
            } // End of for loop
            System.out.println();
            System.out.print("  Lowers: ");
            for (teethFamilial = 0; teethFamilial < familyTeeth[personIndex][1].length; teethFamilial++) {
                System.out.print((teethFamilial + 1) + ":" + familyTeeth[personIndex][1][teethFamilial] + " ");
            } // End of for loop
            System.out.println();
        } // End of for loop
    } //End of printTeethRecord method

    /**
     * This method extracts the tooth
     *
     * @param scanner Prompts input for familyMember (Goes back to array) and the Tooth layer
     * @param familyNames Stores family name details
     * @param familyTeeth 3D Array that draws from 1D. Corresponding family member, 2. Upper/Lower(0,1), 3. Tooth type
     * @param numPeople Number of family members from the familyNames array
     */

    public static void extractTooth(Scanner scanner, String[] familyNames, char[][][] familyTeeth, int numPeople) {
        System.out.print("Which family member: ");

        String familyMember = scanner.next().toUpperCase();

        int familyIndex = -1;
        int exactIndex = 0;
        for (exactIndex = 0; exactIndex < numPeople; exactIndex++) {
            if (familyNames[exactIndex].toUpperCase().equals(familyMember)) {
                familyIndex = exactIndex;
                break;
            } // End of if statement
        } // End of for loop

        while (familyIndex == -1) {
            System.out.println("Invalid family member, try again.");
            familyMember = scanner.next().toUpperCase();
            for (exactIndex = 0; exactIndex < numPeople; exactIndex++) {
                if (familyNames[exactIndex].toUpperCase().equals(familyMember)) {
                    familyIndex = exactIndex;
                    break;
                } // End of if statement
            } // End of for loop
        } // End of while loop

        System.out.print("Which tooth layer (U)pper or (L)ower: ");
        char layer = scanner.next().toUpperCase().charAt(0);
        int layerIndex = (layer == 'U') ? 0 : (layer == 'L') ? 1 : -1;


        while (layerIndex == -1) {
            System.out.println("Invalid layer, try again.");
            layer = scanner.next().toUpperCase().charAt(0);
            layerIndex = (layer == 'U') ? 0 : (layer == 'L') ? 1 : -1;
        } // End of if statement

        int toothNumber;
        boolean validTooth = false;
        while (!validTooth) {
            do {
                System.out.print("Which tooth number (1-" + MAX_TEETH + "): ");
                toothNumber = scanner.nextInt();
                if (toothNumber < 1 || toothNumber > MAX_TEETH) {
                    System.out.println("Invalid tooth number, try again.");
                }
            } while (toothNumber < 1 || toothNumber > MAX_TEETH);

            if (familyTeeth[familyIndex][layerIndex][toothNumber - 1] == 'M') {
                System.out.println("Missing tooth, try again.");

            } else {
                validTooth = true;
                familyTeeth[familyIndex][layerIndex][toothNumber - 1] = 'M';
                System.out.println("Tooth extracted successfully.");
            } // End of else statement
        } // End of while loop
    }// End of do loop

    /**
     * This method counts the number of the different types of teeth and then calculates location of root canal
     * @param familyTeeth 3D Array that draws from 1D. Corresponding family member, 2. Upper/Lower(0,1), 3. Tooth type
     * @param numPeople Number of family members from the familyNames array
     */

    public static void calculateRootCanalIndices(char[][][] familyTeeth, int numPeople) {
        int numIncissors = 0, numBicuspids = 0, numMissing = 0;
        // Count I, B, M types of teeth
        int extractionIndex = 0;
        int layerIndex = 0;
        for (extractionIndex = 0; extractionIndex < numPeople; extractionIndex++) {
            for (layerIndex = 0; layerIndex < 2; layerIndex++) {
                for (char tooth : familyTeeth[extractionIndex][layerIndex]) {
                    switch (tooth) {
                        case 'I': numIncissors++;
                            break;
                        case 'B': numBicuspids++;
                            break;
                        case 'M': numMissing++;
                            break;
                    } // End of switch statement
                } // End of for loop
            } // End of for loop
        } // End of for loop

        // Solve the root canal function: Ix^2 + Bx - M = 0
        double discriminant = numBicuspids * numBicuspids - 4 * numIncissors * -numMissing;
        if (discriminant >= 0) {
            double root1 = (-numBicuspids + Math.sqrt(discriminant)) / (2 * numIncissors);
            double root2 = (-numBicuspids - Math.sqrt(discriminant)) / (2 * numIncissors);
            System.out.printf("One root canal at %.2f\n", root1);
            System.out.printf("Another root canal at %.2f\n", root2);
        } else {
            System.out.println("No real roots for the equation.");
        } // End of if statement
    } // End of calculateRootCanalIndices method
}//End of BaseCode class
