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
        Integer grain = 100;
        Integer population = 4;
        Integer land = 400;
        Integer landValue = 19;

//        Each person needs at least 20 bushels of grain per year to survive
//        Each person can farm at most 10 acres of land
//        It takes 2 bushels of grain to farm an acre of land
//        The market price for land fluctuates yearly

//        100 people
//        2800 bushels of grain in storage
//        1000 acres of land
//        Land value is 19 bushels/acre

        // statements go after the declarations

        askHowManyAcresToPlant(land, population, grain);
    }

    int getNumber(String message) {
        while (true) {
            System.out.print(message);
            try {
                return scanner.nextInt();
            }
            catch (InputMismatchException e) {
                System.out.println("\"" + scanner.next() + "\" isn't a number!");
            }
        }
    }
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

    int askHowManyAcresToSell(int acresOwned) {return 0;}

    int askHowMuchGrainToFeedPeople(int bushels) {
        //Ask the player how much grain to feed people, and returns that number.
        // You can't feed them more grain than you have. You can feed them more than they need to survive.
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
//        Ask the player how many acres to plant with grain, and returns that number.
//        You must have enough acres, enough grain, and enough people to do the planting.
//        Any grain left over goes into storage for next year.

        //        Integer grain = 2800;
        //        Integer population = 100;
        //        Integer land = 1000;
        //        Integer landValue = 19;

        String msg = "How many acres do you want to plant with grain?\n";
        int input = getNumber(msg);

        while (true) {
            if (input > acresOwned) {
                String newMsg = String.format("O Great Hammurabi, surely you jest! We'd need %d acres but we only have %d!",
                        input, acresOwned);
                System.out.println(newMsg);
                input = getNumber(msg);
                continue;
            }
            if (input/10 > population) {
                String newMsg = String.format("O Great Hammurabi, surely you jest! We'd need %d people but we only have %d!",
                        input/10 , population);
                System.out.println(newMsg);
                input = getNumber(msg);
                continue;
            }
            if (input > bushels/2) {
                String newMsg = String.format("O Great Hammurabi, surely you jest! We'd need %d bushels but we only have %d!",
                        input*2, bushels);
                System.out.println(newMsg);
                input = getNumber(msg);
                continue;
            }
            return input;
        }
    }

    int plagueDeaths(int population) {return 0;}

    int starvationDeaths(int population, int bushelsFedToPeople) {return 0;}

    boolean uprising(int population, int howManyPeopleStarved) {return false;}

    int immigrants(int population, int acresOwned, int grainInStorage) {return 0;}

    int harvest(int acres, int bushelsUsedAsSeed) {return 0;}

    int grainEatenByRats(int bushels) {return 0;}

    int newCostOfLand() {return 0;}
}
