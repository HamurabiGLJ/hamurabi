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
        Integer grain = 2800;
        Integer population = 100;
        Integer land = 1000;
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
         grainEatenByRats(100);
//        starvationDeaths(5,100);
//        askHowManyAcresToPlant(land, population, grain);
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

    int starvationDeaths(int population, int bushelsFedToPeople) {
//        Each person needs 20 bushels of grain to survive.
//        If you feed them more than this, they are happy, but the grain is still gone.
//        You don't get any benefit from having happy subjects.
//        Return the number of deaths from starvation (possibly zero).
        int numberOfPeopleFed = bushelsFedToPeople/20;

        if (numberOfPeopleFed < population) { //100 fed divide by 20 means 5, if 5 < pop. 100, 95 ppl died.
            System.out.println("O Great Hammurabi, our population has been reduced by " +
                    (population - bushelsFedToPeople/20) + " subjects due to starvation. Our population is now " +
                    (bushelsFedToPeople/20) + ".");
            return population - bushelsFedToPeople/20;
        }
        else
            System.out.println("O Great Hammurabi, all our subjects are fed! No one died from starvation.");
            return 0;
    }

    boolean uprising(int population, int howManyPeopleStarved) {return false;}

    int immigrants(int population, int acresOwned, int grainInStorage) {return 0;}

    int harvest(int acres, int bushelsUsedAsSeed) {return 0;}

    int grainEatenByRats(int bushels) {
        //here is a 40% chance that you will have a rat`` infestation.
        // When this happens, rats will eat somewhere between 10% and 30% of your grain.
        // Return the amount of grain eaten by rats (possibly zero).
        int chanceRatInfestation =  rand.nextInt(0,101);
        System.out.println(chanceRatInfestation);
        if (chanceRatInfestation <= 40) {
            int eatenByRat = rand.nextInt(10,31);
            System.out.println(eatenByRat);
            System.out.println("O Great Hammurabi, there is an infestation! The rats have eaten " + (bushels*eatenByRat/100));
            return (bushels*eatenByRat/100);
        }
        System.out.println("O Great Hammurabi, the rats have not eaten any of our bushels!");
        return 0;
    }

    int newCostOfLand() {return 0;}
}
