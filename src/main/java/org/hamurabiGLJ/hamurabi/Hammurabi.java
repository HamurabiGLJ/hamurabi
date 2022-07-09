package org.hamurabiGLJ.hamurabi;

import java.util.*;

public class Hammurabi {
    Random rand = new Random();  // this is an instance variable
    Scanner scanner = new Scanner(System.in);
    private int land;


    public static void main(String[] args) { // required in every Java program
        new Hammurabi().playGame();
    }

    void playGame() {
        // declare local variables here: grain, population, etc.
        Integer grain = 2800;
        Integer population = 100;
        Integer land = 1000;
        Integer landValue = 19;

        // statements go after the declarations
    }

    int getNumber(String message) {
        while (true) {
            System.out.print(message);
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\"" + scanner.next() + "\" isn't a number!");
            }
        }
    }
    // Jiayong
    int askHowManyAcresToBuy(int price, int bushels) {
        int maxAcres = bushels / price;
        String msg = "How many acres of land would you like to buy?";
        int input = getNumber(msg);
        while (input > maxAcres) {
            String newMsg = String.format("O Great Hammurabi, surely you jest! We'd need %d bushels but we only have %d!",
                    input * price, bushels);
            System.out.println(newMsg);
            input = getNumber(msg);
        }
        return input;
    }
    // Linda
    int askHowManyAcresToSell(int acresOwned) {
        int acresToSell = getNumber("How many acres of land would you like to sell?\n");
        while (acresToSell > acresOwned) {
            System.out.println("O Hammurabi, but you only have " + land + " acres of land!\n");
            acresToSell = getNumber("How many acres of land would you like to sell?\n");
        }
        return acresToSell;
    }
    // Gychu
    int askHowMuchGrainToFeedPeople(int bushels) {
        String msg = "How many bushels of grain do you want to feed your people?\n";
        int input = getNumber(msg);
        while (input > bushels) {
            String newMsg = String.format("O Great Hammurabi, surely you jest! We'd need %d bushels but we only have %d!",
                    input, bushels);
            System.out.println(newMsg);
            input = getNumber(msg);
        }
        return input;
    }

    int askHowManyAcresToPlant(int acresOwned, int population, int bushels) {
        String msg = "How many acres do you wish to plant?\n";
        int input = getNumber(msg);

        while (true) {
            if (input > acresOwned) {
                String newMsg = String.format("O Great Hammurabi, surely you jest! We'd need %d acres but we only have %!", input, acresOwned);
                System.out.println(newMsg);
                input = getNumber(msg);
                continue;
            }
            if (input / 10 > population) {
                String newMsg = String.format("O Great Hammurabi, surely you jest! We'd need %d people but we only have %d!", input/10, population);
                System.out.println(newMsg);
                input = getNumber(msg);
                continue;
            }
            if (input > bushels / 2) {
                String newMsg = String.format("O Great Hammurabi, surely you jest! We'd need %d bushels but we only have %d!", input*2, bushels);
                System.out.println(newMsg);
                input = getNumber(msg);
                continue;
            }
            return input;
        }
    }

    int plagueDeaths(int population) {
        if (rand.nextInt(101) <= 15)  {
            return population / 2;
        }
        return 0;
    }

    int starvationDeaths(int population, int bushelsFedToPeople) {
        return 0;
    }

    boolean uprising(int population, int howManyPeopleStarved) {
        return (((double)howManyPeopleStarved / population) >= 0.45);
    }

    int immigrants(int population, int acresOwned, int grainInStorage) {
        return (20 * acresOwned + grainInStorage) / (100 * population) + 1;
    }

    int harvest(int acres) {
        int yield = rand.nextInt(1,7);
        return acres * yield;
    }

    int grainEatenByRats(int bushels) {
        return 0;
    }

    int newCostOfLand() {
        return rand.nextInt(17,24);
    }
}
