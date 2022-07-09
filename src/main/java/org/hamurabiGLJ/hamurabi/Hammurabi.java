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
            bushelsGrain -= acresToBuy * landValue + (acresToSell * landValue);

            // grains fed to people
            int grainsFed = askHowMuchGrainToFeedPeople(population, bushelsGrain);
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
            int grainHarvested = harvest(land, acresPlanted);
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
            evalMsg = "AMAZING performance O great Hamurabi! Statues will be made all over and songs will be sung about your" +
                    "glorious rule for a long time to come!\n";
        } else if (evalPoints >= 80) {
            evalMsg = "Great performance. As expected of the great Hamurabi!";
        } else if (evalPoints >= 30) {
            evalMsg = "An average performance..but Hamurabi cannot be average. Are you really Hamurabi?";
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
                Oh poor Hamurabi!
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
    int askHowManyAcresToBuy(int price, int grainStock) {
        int maxAcres = grainStock / price;
        String msg = "Great Harambe, how many acres of land would you like to buy?";
        int input = getNumber(msg);
        while (input > maxAcres) {
            String exceededMsg = String.format("O Great Hammurabi, surely you jest! We'd need %d bushels but we only have %d!%n",
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

    int newCostOfLand() {return 1;}
}
