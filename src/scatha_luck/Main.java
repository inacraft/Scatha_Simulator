package scatha_luck;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.Scanner;

public class Main {

	// Static Color for print
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static void main(String[] args) throws InterruptedException {
		Scanner myScanner = new Scanner(System.in);
		String mfinput = "";
		String plinput = "";
		String lootinginput = "";
		String nbPetinput = "";
		String delayKillsinput = "";
		String showKillsinput = "";
		Random r = new Random();	
		DecimalFormat formatterDecimal = new DecimalFormat("##.#####");

		System.out.println("Hellow ! Welcome to the hell of Scatha farming !");
		System.out.println("Here, you can just try your luck to see if you are lucky today to obtain your Scatha !");
		System.out.println("Please enter your actual magic find (take in account the magic find gain with bestiary) :");

		// Check if there a argument for magic find input else ask the user his magic find
		if(args.length >= 1) {
			mfinput = args[0];
			System.out.println(mfinput);
		}else {
			mfinput = myScanner.nextLine();
		}

		System.out.println("Now, enter your actual pet luck :");

		// Check if there a argument for pet luck input else ask the user his pet luck
		if(args.length >= 2) {
			plinput = args[1];
			System.out.println(plinput);
		}else {
			plinput = myScanner.nextLine();
		}

		System.out.println("Enter your level of looting of your weapon (0-5) :");

		// Check if there a argument for looting level input else ask the user his looting level
		if(args.length >= 3) {
			lootinginput = args[2];
			System.out.println(lootinginput);
		}else {
			lootinginput = myScanner.nextLine();
		}

		System.out.println("Enter the number of pets drops you want to simulate :");

		// Check if there a argument for number of pets drops input else ask the user the number of pets drops he wants
		if(args.length >= 4) {
			nbPetinput = args[3];
			System.out.println(nbPetinput);
		}else {
			nbPetinput = myScanner.nextLine();
		}

		System.out.println("Enter the delay between worms kills [in milliseconds] (0 = Instant results | 100 = Recommended ) :");

		// Check if there a argument for delay worms kills input else ask the user what delay in milliseconds he wants
		if(args.length >= 5) {
			delayKillsinput = args[4];
			System.out.println(delayKillsinput);
		}else {
			delayKillsinput = myScanner.nextLine();
		}

		System.out.println("Finally, do you want to show worms kills ? [Y/N] (Can be laggy if you want like 1 million pets drops) :");

		// Check if there a argument for delay worms kills input else ask the user what delay in milliseconds he wants
		if(args.length >= 6) {
			showKillsinput = args[5];
			System.out.println(showKillsinput);
		}else {
			showKillsinput = myScanner.nextLine();
		}

		myScanner.close();

		int mf = 0;
		int pl = 0;
		int looting = 0;
		int nbPetDrop = 0;
		int delayKills = 0;

		// Try to turn user input into int
		try {
			mf = Integer.parseInt(mfinput);
			pl = Integer.parseInt(plinput);
			looting = Integer.parseInt(lootinginput);
			nbPetDrop = Integer.parseInt(nbPetinput); 
			delayKills = Integer.parseInt(delayKillsinput);
			showKillsinput = showKillsinput.toLowerCase();
		}catch (Exception e) {
			System.out.println("Wrong arguments !");
			System.exit(1);
		}

		// Looting level is 15% every level
		if(looting > 5 || looting < 0) {
			System.out.println("Wrong level of looting !");
			System.exit(1);
		}else{
			looting = looting*15;
		}

		// START OF MATHS
		double scathaRareBaseDrop = 0.24;
		double scathaEpicBaseDrop = 0.12;
		double scathaLegBaseDrop = 0.04;

		double luckCalcul = (double) (mf+pl)/100;

		double newBaseDropRareWithoutLooting = scathaRareBaseDrop*(1+(luckCalcul));
		double newBaseDropEpicWithoutLooting = scathaEpicBaseDrop*(1+(luckCalcul));
		double newBaseDropLegWithoutLooting = scathaLegBaseDrop*(1+(luckCalcul));

		double lootingPercentage = (double) looting/100;

		double newBaseDropRare = newBaseDropRareWithoutLooting+(lootingPercentage*newBaseDropRareWithoutLooting);
		double newBaseDropEpic = newBaseDropEpicWithoutLooting+(lootingPercentage*newBaseDropEpicWithoutLooting);
		double newBaseDropLeg = newBaseDropLegWithoutLooting+(lootingPercentage*newBaseDropLegWithoutLooting);

		int luckyNumberRare = (int) (100/newBaseDropRare);
		int luckyNumberEpic = (int) (100/newBaseDropEpic);
		int luckyNumberLeg = (int) (100/newBaseDropLeg);
		// END OF MATHS

		System.out.println("\nYou have 1/" + luckyNumberRare + " for dropping a Rare Scatha. (" + formatterDecimal.format(newBaseDropRare) + "%)");
		System.out.println("You have 1/" + luckyNumberEpic + " for dropping a Epic Scatha. (" + formatterDecimal.format(newBaseDropEpic) + "%)");
		System.out.println("You have 1/" + luckyNumberLeg + " for dropping a Legendary Scatha. (" + formatterDecimal.format(newBaseDropLeg) + "%)\n");

		Thread.sleep(500);

		int nbOfScathaKills = 0;
		int nbOfWormsKills = 0;
		int nbOfScathaKillsTotal = 0;
		int nbOfWormsKillsTotal = 0;

		int randInt = 0;
		int randWorms = 0;

		int nbPetRare = 0;
		int nbPetEpic = 0;
		int nbPetLeg = 0;

		boolean nbOfPetAchieved = false;

		// int nbOfTry = 0;

		// Constant drop pet until it reach the number of pets drops the user want
		while(!nbOfPetAchieved /* || nbOfTry != 2 */) {
			// Constant simulate kills of worms and Scatha until he drop 1 pet. Check with modulo for making one drop (1/[number]) with 3 different loot and doing at least 1 Scatha kill before drop.
			while((randInt%luckyNumberLeg != 0 && randInt%luckyNumberEpic != 0 && randInt%luckyNumberRare != 0) || nbOfScathaKills == 0) {
				randWorms = r.nextInt(5) + 1;
				Thread.sleep(delayKills);
				// 4/5 to get a Worm (80%)
				if(randWorms >= 1 && randWorms <= 4) {
					nbOfWormsKills++;
					if(showKillsinput.equals("y")) {
						if(nbOfScathaKills == 1) {
							System.out.println("You killed " + ANSI_WHITE + nbOfWormsKills + ANSI_RESET + " Worm !");
						}else {
							System.out.println("You killed " + ANSI_WHITE + nbOfWormsKills + ANSI_RESET + " Worms !");
						}
					}
					// 1/5 chances to get a Scatha (20%)
				}else if(randWorms == 5) {
					// Randomize a number to simulate if user drop a pet or not
					randInt = r.nextInt((luckyNumberLeg*luckyNumberEpic*luckyNumberRare) + 1);
					nbOfScathaKills++;
					if(showKillsinput.equals("y")) {
						if(nbOfScathaKills == 1) {
							System.out.println("You killed " + ANSI_RED + nbOfScathaKills + ANSI_RESET + " Scatha !");
						}else {
							System.out.println("You killed " + ANSI_RED + nbOfScathaKills + ANSI_RESET + " Scathas !");
						}	
					}
				}
			}

			if(delayKills > 0) {
				Thread.sleep(500);
			}

			// Check before if user drop a legendary because Rare drop can be also modulo of the number of Legendary drop
			if(randInt%luckyNumberLeg == 0) {
				nbPetLeg++;
				System.out.println("\n" + ANSI_YELLOW + "PET DROP! " + ANSI_YELLOW + "Scatha " + ANSI_CYAN + "(" + mf + "% ★ Magic Find)\n" + ANSI_RESET);
			}else if(randInt%luckyNumberEpic == 0) {
				nbPetEpic++;
				System.out.println("\n" + ANSI_YELLOW + "PET DROP! " + ANSI_PURPLE + "Scatha " + ANSI_CYAN + "(" + mf + "% ★ Magic Find)\n" + ANSI_RESET);
			}else if(randInt%luckyNumberRare == 0){
				nbPetRare++;
				System.out.println("\n" + ANSI_YELLOW + "PET DROP! " + ANSI_BLUE + "Scatha " + ANSI_CYAN + "(" + mf + "% ★ Magic Find)\n" + ANSI_RESET);
			}

			float percentageScatha = (float) nbOfScathaKills/(nbOfScathaKills+nbOfWormsKills);	

			System.out.println("Number of Worms killed : " + nbOfWormsKills);
			System.out.println("Number of Scathas killed : " + nbOfScathaKills);
			System.out.println("Percentage of Scathas : " + new DecimalFormat("#.##").format(percentageScatha*100) + "%\n");

			// Reset drop for next pet drop and add all stats for this drop to global stats
			randInt = 0;
			nbOfScathaKillsTotal += nbOfScathaKills;
			nbOfWormsKillsTotal += nbOfWormsKills; 

			// The number of pets drops you want to simulate.
			nbOfPetAchieved = (nbPetRare+nbPetEpic+nbPetLeg) >= nbPetDrop;

			// Drop pet till you reach a pet drop more than 362 kills Scathas and more than 202 kills Scathas in a row.
			/*if(nbOfTry == 0 && nbOfScathaKills >= 362) {
				nbOfTry++;
			}else if(nbOfTry == 1 && nbOfScathaKills >= 202) {
				nbOfTry++;
			}else {
				nbOfTry = 0;
			}*/

			// Reset all kills for next pet drop.
			nbOfScathaKills = 0;
			nbOfWormsKills = 0;
		}

		// Show global stats if number of pets drops is more than 1.
		if((nbPetRare+nbPetEpic+nbPetLeg) > 1) {
			float percentageScathaTotal = (float) nbOfScathaKillsTotal/(nbOfScathaKillsTotal+nbOfWormsKillsTotal);
			int chanceLegendary = 0;
			int chanceEpic = 0;
			int chanceRare = 0;
			int chanceTotals = 0;
			
			if(nbPetLeg != 0) {
				chanceLegendary = (int) Math.round((double) nbOfScathaKillsTotal/nbPetLeg);
			}
			if(nbPetEpic != 0) {
				chanceEpic = (int) Math.round((double)nbOfScathaKillsTotal/nbPetEpic);
			}
			if(nbPetRare != 0) {
				chanceRare = (int) Math.round((double)nbOfScathaKillsTotal/nbPetRare);
			}
			if((nbPetRare+nbPetEpic+nbPetLeg) != 0) {
				chanceTotals = (int) Math.round((double)nbOfScathaKillsTotal/(nbPetRare+nbPetEpic+nbPetLeg));
			}

			System.out.println("\nNumber of Worms total killed : " + nbOfWormsKillsTotal);
			System.out.println("Number of Scathas total killed : " + nbOfScathaKillsTotal);
			System.out.println("Percentage of Scathas total : " + new DecimalFormat("#.##").format(percentageScathaTotal*100) + "%");
			System.out.println("\nNumber of Legendary Scatha Pet : " + nbPetLeg + " (1/" + chanceLegendary + ")");
			System.out.println("Number of Epic Scatha Pet : " + nbPetEpic + " (1/" + chanceEpic + ")");
			System.out.println("Number of Rare Scatha Pet : " + nbPetRare + " (1/" + chanceRare + ")");
			System.out.println("Number of total Scatha Pet : " + (nbPetRare+nbPetEpic+nbPetLeg) + " (1/" + chanceTotals + ")");
		}
	}
}
