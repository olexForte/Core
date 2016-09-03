package utils;

import java.util.Random;

public class DataGenerator {
	
	public static int randInt(int min, int max) {
		Random r = new Random();
		int i1 = r.nextInt(max - min + 1) + min;
	    return i1;
	}

	public static String intNumber(int numOfDigits) {
		String genNum = "9";
		for (int i = 0; i < numOfDigits - 1; i++) {
			genNum = genNum + new Random().nextInt(9);
		}
		return genNum;
	}

	public static String firstName() {
		String firstName = null;
		String[] arr = { "Buck", "Lemuel", "Blaine", "Armand", "Rocco", "Enrique", "Rick", "Stanford", "Gerardo",
				"Leonel", "Waylon", "Collin", "Hubert", "Walter", "Palmer", "Heriberto", "Fabian", "Isreal", "Dean",
				"August", "Kasey", "Nigel", "Sang", "Jamel", "Randal", "Zackary", "Rob", "Jae", "Kerry", "Seth",
				"Oswaldo", "Ezekiel", "Ken", "Luke", "Hunter", "Lou", "Duane", "Fredrick", "Errol", "Grant", "Garth",
				"Riley", "Elmer", "Asa", "Ralph", "Josef", "Joaquin", "Francisco", "Ben", "Barrett" };
		firstName = arr[new Random().nextInt(arr.length)];
		return firstName;
	}

	public static String lastName() {
		String lastName = null;
		String[] arr = { "Richardson", "Shaffer", "Kent", "Morris", "Cameron", "Rosario", "Rich", "Pace", "Sloan",
				"Reyes", "Gordon", "Schmitt", "Kirby", "Costa", "Washington", "Simmons", "Kirk", "Dominguez", "Osborn",
				"Carey", "Fox", "Kaufman", "Preston", "Small", "Shepard", "Donaldson", "Manning", "Dixon", "Harris",
				"Higgins", "Nielsen", "Phillips", "Herring", "Howard", "Mclaughlin", "Hoover", "Mccann", "Suarez",
				"Barrera", "Rivers", "Mayer", "Schaefer", "English", "Price", "Barnett", "Keith", "Pitts", "Garcia",
				"Paul", "Marsh" };
		lastName = arr[new Random().nextInt(arr.length)];
		return lastName;
	}

	public static String address() {
		String address = null;
		String[] arr = { "North Berkenshire Lawn", "Dancers End Place", "West Heathwick Mews", "South Germone Passage",
				"West Crispsparkle Green", "Northwest White Pine", "Mulherin Grove", "Northwest Cassena Alley",
				"Peek Boulevard", "Shore Mall", "Northwest Pheasant Lake", "Ivel Quadrant", "Hilder Lane",
				"Southwest Turon Passage", "Southwest Jacinth Passage", "South Boynton Pathway", "Southwest Ross Drive",
				"Fish Arcade", "Valory Avenue", "East Levin Pathway", "Colt Run North", "Addiscombe Court Quadrant",
				"West Wallace Terrace", "Lexow Trace", "Adelphi Boulevard West", "Duppas Hill Spur", "Bearhurst Mews",
				"Mc Gregor Causeway South", "North Portley Viaduct", "Woodpath Loop", "Lordswell Causeway North",
				"East Mulders Oval", "Southwest Johnson Fold", "East Azee Lawn", "South Bank Field",
				"Southwest Buckswood Road", "Percypenny Gardens", "Sierra Mills", "Tall Timber Mall", "Baylis Road",
				"South Carieville Grade", "Old Albany Post", "East Mossbray Garth", "North Roy Frerichs Promenade",
				"West Ironmonger Townline", "West Evers Court", "Matiasevich Promenade", "Roseway Trace Southeast",
				"Camp Meade South", "Southwest Lancers Mews" };
		address = arr[new Random().nextInt(arr.length)] + " " + new Random().nextInt(999);
		return address;
	}

}
