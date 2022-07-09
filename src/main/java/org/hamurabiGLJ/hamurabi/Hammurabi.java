package org.hamurabiGLJ.hamurabi;

import java.util.*;

public class Hammurabi {
    Random rand = new Random();  // this is an instance variable
    Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) { // required in every Java program
        new Hammurabi().playGame();
    }

    void playGame() {
        // declare local variables here: grain, population, etc.
        Integer bushelsGrain = 2800;
        Integer population = 100;
        Integer land = 1000;
        Integer landValue = 19;

        // statements go after the declarations
        // get buy and sell
        land += askHowManyAcresToBuy(landValue, bushelsGrain) - askHowManyAcresToSell(land);

        // grains fed to people
        int grainsFed = askHowMuchGrainToFeedPeople(population, bushelsGrain);
        bushelsGrain -= grainsFed;

        int acresPlanted = askHowManyAcresToPlant(land, population, bushelsGrain);
        bushelsGrain -= acresPlanted;

        population -= plagueDeaths(population);

        int starvationAmount = starvationDeaths(population, grainsFed);
        // TODO: handle uprising
        boolean isUprising = uprising(population, starvationAmount);
        population -= starvationAmount;

        if (starvationAmount == 0) {
            population += immigrants(population, land, bushelsGrain);
        }
        bushelsGrain += harvest(land, acresPlanted);

        bushelsGrain -= grainEatenByRats(bushelsGrain);

        landValue = newCostOfLand();
    }

    void printSummary() {
        System.out.println("");
    }

    int getNumber(String message) {
        while (true) {
            System.out.println(message);
            try {
                return scanner.nextInt();
            }
            catch (InputMismatchException e) {
                System.out.println("\"" + scanner.next() + "\" isn't a number!");
            }
        }
    }
    int askHowManyAcresToBuy(int price, int grainStock) {
        int maxAcres = grainStock / price;
        String msg = "Great Harambe, how many acres of land would you like to buy?";
        int input = getNumber(msg);
        while (input > maxAcres) {
            String exceededMsg = String.format("O Great Hammurabi, surely you jest! We'd need %d bushels but we only have %d!",
                    input * price, grainStock);
            System.out.println(exceededMsg);
            input = getNumber(msg);
        }
        return input;
    }
    //other methods go here
    int askHowManyAcresToSell(int acresOwned) {return 0;}

    int askHowMuchGrainToFeedPeople(int population, int grainStock) {return 0;}

    int askHowManyAcresToPlant(int acresOwned, int population, int bushels) {return 0;};

    int plagueDeaths(int population) {return 0;}

    int starvationDeaths(int population, int bushelsFedToPeople) {return 0;}

    boolean uprising(int population, int howManyPeopleStarved) {return false;}

    int immigrants(int population, int acresOwned, int grainInStorage) {return 0;}

    int harvest(int acres, int bushelsUsedAsSeed) {return 0;}

    int grainEatenByRats(int bushels) {return 0;}

    int newCostOfLand() {return 0;}
}
