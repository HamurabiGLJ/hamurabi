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
        int bushelsGrain = 2800;
        int population = 100;
        int land = 1000;
        int landValue = 19;
        int year = 1;
        int cumulativePopulation = population;
        int cumulativeStarvations = 0;

        while (year <= 10) {
            if (year == 1) {
                printBeginningSummary(population, bushelsGrain, land, landValue);
            }
            // statements go after the declarations
            // get buy and sell
            int acresToBuy = askHowManyAcresToBuy(landValue, bushelsGrain);
            int acresToSell = 0;
            if (acresToBuy == 0) {
                acresToSell = askHowManyAcresToSell(land);
            }
            land += acresToBuy - acresToSell;
            bushelsGrain -= acresToBuy * landValue;
            bushelsGrain += acresToSell * landValue;

            // grains fed to people
            int grainsFed = askHowMuchGrainToFeedPeople(bushelsGrain);
            bushelsGrain -= grainsFed;
            // acres to plant
            int acresPlanted = askHowManyAcresToPlant(land, population, bushelsGrain);
            bushelsGrain -= acresPlanted;

            // Plague
            int plagueDeaths = plagueDeaths(population);
            population -= plagueDeaths;

            // Handle starvation
            int starvationAmount = starvationDeaths(population, grainsFed);
            cumulativeStarvations += starvationAmount;
            boolean isUprising = uprising(population, starvationAmount);
            if (isUprising) {
                printUprising(year + 1, starvationAmount, population);
                return;
            }
            population -= starvationAmount;

            // Handle immigration
            int immigrationAmount = 0;
            if (starvationAmount == 0) {
                immigrationAmount += immigrants(population, land, bushelsGrain);
                population += immigrationAmount;
                cumulativePopulation += immigrationAmount;
            }

            // Handle harvest
            int grainHarvested = harvest(land);
            bushelsGrain += grainHarvested;
            int grainsPerAcre = 0;
            if (grainHarvested != 0 && acresPlanted != 0) {
                grainsPerAcre = grainHarvested / acresPlanted;
            }

            // Handle rats
            int grainsEaten = grainEatenByRats(bushelsGrain);
            bushelsGrain -= grainsEaten;

            // New land value
            landValue = newCostOfLand();

            year++;
            printSummary(year, starvationAmount, immigrationAmount, plagueDeaths, population, grainHarvested,
                    grainsPerAcre, grainsEaten, bushelsGrain, land, landValue);
        }
        finalSummary(cumulativeStarvations, cumulativePopulation, population, land);
    }

    void printBeginningSummary(int population, int grain, int land, int landPrice) {
        int grainPerAcre = rand.nextInt(7);
        int grainEatenRats = grainEatenByRats(1500 + grain);
        printSummary(1, rand.nextInt(21), 0, 0,
                population, land * grainPerAcre, grainPerAcre,
                grainEatenRats, grain, land, landPrice);
    }

    void finalSummary(int totalStarved, int cumulativePop, int lastPop, int lastLand) {
        int acresPerPerson;
        String evalMsg;
        if (lastPop == 0) {
            acresPerPerson = 0;
        } else acresPerPerson = lastLand / lastPop;

        long percentStarved  = Math.round(totalStarved / (double) cumulativePop * 100);
        int evalPoints = 0;

        evalPoints += (percentStarved < 5)
                ? 60
                : (percentStarved < 20)
                ? 30
                : (percentStarved < 30)
                ? 10
                : 0;
        evalPoints += (acresPerPerson > 20)
                ? 50
                : (acresPerPerson > 12)
                ? 30
                : (acresPerPerson > 5)
                ? 20
                : 10;

        if (evalPoints >= 100) {
            evalMsg = "AMAZING performance O great Hammurabi! Statues will be made all over and songs will be sung about your" +
                    "glorious rule for a long time to come!\n";
        } else if (evalPoints >= 80) {
            evalMsg = "Great performance. As expected of the great Hammurabi!";
        } else if (evalPoints >= 30) {
            evalMsg = "An average performance..but Hammurabi cannot be average. Are you really Hammurabi?";
        } else {
            evalMsg = "HORRIBLE! You must be an imposter!";
        }
        String summary = """
            In your 10 year term of governance:
            %d starved out of %d people who resided in your city. That's %d%% of the cumulative population!
            You started with 10 acres per person and ended with %d acres per person!
            
            Evaluation:
            %s
            """.formatted(totalStarved, cumulativePop, percentStarved, acresPerPerson, evalMsg);

        System.out.println(summary);
    }

    void printUprising(int year, int peopleStarved, int population) {
        String summary = """
                You are in year %d of your ten year rule.
                %d people have starved to death out of %d!
                There has been uprising due to your cruelty and the people have removed you from your post!
                Oh poor Hammurabi!
                GAME OVER
                """.formatted(year, peopleStarved, population);
        System.out.println(summary);
    }

    void printSummary(int year, int starvationDeath,
                      int immigrants, int plagueDeaths, int population,
                      int grainHarvested, int grainPerAcre,
                      int grainDestroyed, int grainRemaining,
                      int land, int landPrice) {
        String plagueMsg = "";
        if (plagueDeaths > 0) {
            plagueMsg = "A terrible plague befell the city! %d of our people died!%n".formatted(plagueDeaths);
        }
        String summary = """
                O Great Hammurabi!
                You are in year %d of your ten year rule.
                In the previous year %d people starved to death.
                In the previous year %d people entered the kingdom.
                %sThe population is now %d.
                We harvested %d bushels at %d bushels per acre.
                Rats destroyed %d bushels, leaving %d bushels in storage.
                The city owns %d acres of land.
                Land is currently worth %d bushels per acre.
                """.formatted(year, starvationDeath, immigrants, plagueMsg, population,
                grainHarvested, grainPerAcre, grainDestroyed, grainRemaining,
                land, landPrice);
        System.out.println(summary);
    }

    int getNumber(String message) {
        while (true) {
            System.out.println(message);
            try {
                return Math.abs(scanner.nextInt());
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


    int askHowManyAcresToSell(int acresOwned) {
        int acresToSell = getNumber("How many acres of land would you like to sell?");
        while (acresToSell > acresOwned) {
            System.out.println("O Hammurabi, but you only have " + acresOwned + " acres of land!");
            acresToSell = getNumber("How many acres of land would you like to sell?");
        }
        return acresToSell;
    }

    int askHowMuchGrainToFeedPeople(int bushels) {
        //Ask the player how much grain to feed people, and returns that number.
        // You can't feed them more grain than you have. You can feed them more than they need to survive.
        String msg = "How many bushels of grain do you want to feed your people?";
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

        String msg = "How many acres do you want to plant with grain?";
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

    int plagueDeaths(int population) {
        if (rand.nextInt(0,101) <= 15)  {
            return population / 2;
        }
        return 0;
    }

    int starvationDeaths(int population, int bushelsFedToPeople) {

//        Each person needs 20 bushels of grain to survive.
//        If you feed them more than this, they are happy, but the grain is still gone.
//        You don't get any benefit from having happy subjects.
//        Return the number of deaths from starvation (possibly zero).
        int numberOfPeopleFed = bushelsFedToPeople/20;

        if (numberOfPeopleFed < population) {
            System.out.println("O Great Hammurabi, our population has been reduced by " +
                    (population - bushelsFedToPeople/20) + " subjects due to starvation. Our population is now " +
                    (bushelsFedToPeople/20) + ".");
            return population - bushelsFedToPeople/20;
        }
        else {
            System.out.println("O Great Hammurabi, all our subjects are fed! No one died from starvation.");
            return 0;
        }
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
        int chanceRatInfestation =  rand.nextInt(0,101);

        if (chanceRatInfestation <= 40) {
            int eatenByRat = rand.nextInt(10,31);
//            System.out.println("O Great Hammurabi, there is an infestation! The rats have eaten " +
//                    (bushels*eatenByRat/100) + " bushels!");
            return (bushels*eatenByRat/100);
        }
//        System.out.println("O Great Hammurabi, the rats have not eaten any of our bushels!");
        return 0;
    }

    int newCostOfLand() {
        return rand.nextInt(17,24);
    }
}
