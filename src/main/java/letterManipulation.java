import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.simple.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.BasicConfigurator; // Used to remove the warns
import org.apache.lucene.util.ArrayUtil;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

public class letterManipulation {
    public static String[] words;
    public static boolean type = false;
    public static int failPerc;


    public static void main(String[] args) throws FileNotFoundException {

        BasicConfigurator.configure();
        char generatedChar = letterGen();
        //System.out.println(generatedChar);

        setFile();
        Scanner input = new Scanner(System.in);  // Create a Scanner object
        System.out.println("This is a program aimed to showcase computational creativity.");
        System.out.println("It has four main features, insert, remove, replace and do nothing.");
        System.out.println("There is an additional choice between demanding words of the same type.");
        System.out.println("");

        System.out.println("Do you want to apply words of the same type rule? (verbs for verbs etc.) reply y or n");
        String yOrn = input.nextLine();
        yOrn = yOrn.toLowerCase();
        if (yOrn.equals("y")) {
            type = true;
        } else if (yOrn.equals("n")) {
            type = false;
        } else {
            System.out.println("You have entered incorrect parameters.");
            System.exit(0);
        }
        System.out.println("What would you like the 'do nothing' percentage to be? reply 10,20,30.");
        System.out.println("20 meaning theres a 20% chance the program discards the found words.");

        String failString = input.nextLine();
        try {
            failPerc = Integer.parseInt(failString);
        } catch (Exception e) {
            System.out.println("You have entered the number in an invalid format.");
            System.exit(0);
        }

        System.out.println("Enter text to be manipulated:");

        String sentence = input.nextLine();
        input.close();


//        System.out.println(Arrays.toString(senSplit) + " sentence splitted ");
//        generatedChar = 'o';
        String[] senSplit = sentence.split("\\s+");
        doManipulation(generatedChar, senSplit);

    }

    public static void doManipulation(char rChar1, String[] inString1) {
        char rChar = rChar1;
        String[] inString = inString1;



        //Replacing
        for (int i = 0; i < inString.length; i++) {

            ArrayList<String> foundWords = new ArrayList<String>();

            if(inString[i].length() > 1){

            //Replacing
            for (int y = 0; y < inString[i].length(); y++) {
                StringBuilder temp = new StringBuilder(inString[i]);

                if (Character.isLetter(temp.charAt(y))) {
                    temp.setCharAt(y, rChar);

                    if (checkWord(temp.toString())) {
                        if (!(foundWords.contains(temp.toString()))) {
                            if (type == false) {
                                foundWords.add(temp.toString());
                            } else {
                                boolean bool = typeCheck(inString[i], temp.toString());
                                if (bool == true) {
                                    foundWords.add(temp.toString());
                                }
                            }

                        }
                    }
                }
            }


            //Remove
            //System.out.println("REMOVE");

            //Remove
            for (int y = 0; y < inString[i].length(); y++) {
                StringBuilder temp = new StringBuilder(inString[i]);
                if (temp.charAt(y) == rChar && Character.isLetter(temp.charAt(y))) {
                    temp.deleteCharAt(y);

                    if (checkWord(temp.toString())) {

                        if (!(foundWords.contains(temp.toString()))) {
                            if (type == false) {
                                foundWords.add(temp.toString());
                            } else {
                                boolean bool = typeCheck(inString[i], temp.toString());
                                if (bool == true) {
                                    foundWords.add(temp.toString());
                                }
                            }
                        }
                    }
                }
            }

            // Inserting
            for (int y = 0; y < inString[i].length() + 1; y++) {
                StringBuilder temp = new StringBuilder(inString[i]);
                temp.insert(y, rChar);

                if (checkWord(temp.toString())) {
                    if (!(foundWords.contains(temp.toString()))) {
                        if (type == false) {
                            foundWords.add(temp.toString());
                        } else {
                            boolean bool = typeCheck(inString[i], temp.toString());
                            if (bool == true) {
                                foundWords.add(temp.toString());
                            }
                        }
                    }
                }

            }
        }

//                System.out.println(Arrays.toString(foundWordsArr));

            String[] foundWordsArr = foundWords.toArray(new String[0]);
            //DO NOTHING
            if (foundWordsArr.length > 0) {
                Random r = new Random();
                double interg = (r.nextDouble() * 100);
                if (interg > failPerc) {
                    Random r2 = new Random();
                    int rInt = r2.nextInt(foundWordsArr.length);
                    inString[i] = foundWordsArr[rInt];
                    //System.out.println("Hit");
                } else {
                    // do nothing
                    //System.out.println("MISS");

                }
            }
//                        System.out.println("Hit");
//                        System.out.println(foundWordsArr[rInt]);

//                        System.out.println("MISS");


//                System.out.println(Arrays.toString(inString));

        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < inString.length; i++) {
            sb.append(inString[i] + " ");
        }
        System.out.println("The letter used for this variation was: " + rChar);
        System.out.println("The generated sentence was: ");

        String finalString = sb.toString();
        System.out.print(finalString);
//            if(Arrays.asList(words).contains(temp.toString())){
//                System.out.println("Found the string");
//            }

//            if(ArrayUtils.contains(words, temp.toString())){
//                System.out.println("Found the string");
//            }

//            if ((Arrays.binarySearch(words, temp.toString())) >= 0) {
//                System.out.println("Found Apple");
//            }


    }

    public static boolean typeCheck(String originalWord, String newWord) {
        Sentence orWord = new Sentence(originalWord);
        List<String> tags = orWord.posTags();
        Sentence neWord = new Sentence(newWord);
        List<String> tags2 = neWord.posTags();
//        System.out.println(originalWord + " " + newWord);
//        System.out.println(tags + " " + tags2);
        boolean bool = tags.equals(tags2);
        return (bool);
    }


//        System.out.println(originalWord + " " + newWord);
//        System.out.println(tags + " " + tags2);
//        System.out.println("Checking equals.");


    public static boolean checkWord(String tempWord) {
        String aWord = tempWord;
        boolean found = false;
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(aWord)) {
                found = true;
            }
        }
        return (found);

    }

    public static void setFile() throws FileNotFoundException {
        Scanner inFile = new Scanner(new File("wordsReduced.txt"));
        int size = 0;
        while (inFile.hasNextLine()) {
            inFile.nextLine();
            size++;
        }
        String[] words2 = new String[size];

        Scanner inFile2 = new Scanner(new File("wordsReduced.txt"));
        for (int i = 0; i < size; i++) {
            words2[i] = inFile2.nextLine().toLowerCase();
        }
        inFile.close();
        inFile2.close();
        words = words2;

    }

    public static char letterGen() {
        Random r = new Random();
        double interg = (r.nextDouble() * 100);
        //System.out.println(interg);


        char chosen = '\0';
        char letters[] = {'e', 't', 'a', 'o', 'i', 'n', 's', 'r', 'h', 'd', 'l', 'u', 'c', 'm', 'f', 'y', 'w', 'g', 'p', 'b', 'v', 'k', 'x', 'q', 'j', 'z'};
        double freq[] = {12.02, 21.12, 29.24, 36.92, 44.23, 51.18, 57.46, 63.48, 69.4, 73.72, 77.7, 80.58, 83.29, 85.9, 88.2, 90.31, 92.4, 94.43, 96.25, 97.74, 98.85, 99.54, 99.71, 99.82, 99.92, 101};


        for (int i = 0; i < freq.length; i++) {
            if (i == 0 && interg <= freq[i + 1]) {
                chosen = letters[i];
            } else if (i == 25 && interg > freq[i - 1]) {
                chosen = letters[i];
            } else if (i != 0 && i != 26 && freq[i - 1] <= interg && interg < freq[i + 1]) {
                chosen = letters[i];
            }
        }

        return (chosen);
    }

}
