package readability;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public static void main(String[] args) {
        double sentences = 0;
        double words = 0;
        double characters = 0;
        double syllables = 0;
        double polysyllables = 0;
        String currentWord = "";
        String eos = ".+[.?!]";

        String filePath = "./" + args[0];
        File file = new File(filePath);

        try (Scanner scanner = new Scanner(file)) {
            // Counting each word and if it has punctuation
            // (., !, ?) then incrementing sentence count
            while (scanner.hasNext()) {
                currentWord = scanner.next();

                int currentWordSyllables = calculateSyllables(currentWord);
                syllables += currentWordSyllables;

                if (currentWordSyllables > 2) {
                    polysyllables++;
                }

                if (currentWord.matches(eos)) {
                    sentences++;
                }

                characters += currentWord.length();
                words++;
            }

            // checking if last input was a sentence
            // (without punctuation) since it wouldn't
            // have been caught in loop.
            if (!currentWord.matches(eos)) {
                sentences++;
            }

            printDemographics(characters, words,
                    sentences, syllables, polysyllables);

            printScoreType(characters, words,
                    sentences, syllables, polysyllables);

        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + filePath);
        }
    }

    static void printScoreType(double characters, double words, double sentences,
                               double syllables, double polysyllables) {
        String scoreType = getScoreTypeFromUser();
        print("");
        double score;
        int readingAge;

        switch (scoreType) {
            case "ARI":
                score = calculateARIScore(characters, words, sentences);
                printARIScore(score);
                readingAge = calculateReadingAge(score);
                printReadingAge(readingAge);
                break;
            case "FK":
                score = calculateFKScore(words, sentences, syllables);
                printFKScore(score);
                readingAge = calculateReadingAge(score);
                printReadingAge(readingAge);
                break;
            case "SMOG":
                score = calculateSMOGScore(polysyllables, sentences);
                printSMOGScore(score);
                readingAge = calculateReadingAge(score);
                printReadingAge(readingAge);
                break;
            case "CL":
                score = calculateCLScore(words, characters, sentences);
                printCLScore(score);
                readingAge = calculateReadingAge(score);
                printReadingAge(readingAge);
                break;
            case "all":
                double ageSum = 0;

                //ARI
                score = calculateARIScore(characters, words, sentences);
                printARIScore(score);
                readingAge = calculateReadingAge(score);
                ageSum += readingAge;
                printReadingAge(readingAge);

                // FK
                score = calculateFKScore(words, sentences, syllables);
                printFKScore(score);
                readingAge = calculateReadingAge(score);
                ageSum += readingAge;
                printReadingAge(readingAge);

                // SMOG
                score = calculateSMOGScore(polysyllables, sentences);
                printSMOGScore(score);
                readingAge = calculateReadingAge(score);
                ageSum += readingAge;
                printReadingAge(readingAge);

                //CL
                score = calculateCLScore(words, characters, sentences);
                printCLScore(score);
                readingAge = calculateReadingAge(score);
                ageSum += readingAge;
                printReadingAge(readingAge);

                double avgSum = ageSum / 4;
                System.out.println("This text should be understood in average by "
                        + df.format(avgSum)
                        + " year olds.");
                break;
            default:
                System.out.println("Not a valid choice.");
        }
    }

    static String getScoreTypeFromUser () {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");

        return scanner.next();
    }

    static int calculateSyllables(String word) {
        /*
        https://hyperskill.org/projects/39/stages/208/implement
        1. Count the number of vowels in the word.
        2. Do not count double-vowels (for example, "rain" has 2
            vowels but only 1 syllable).
        3. If the last letter in the word is 'e' do not count it
            as a vowel (for example, "side" has 1 syllable).
        4. If at the end it turns out that the word contains 0
            vowels, then consider this word as a 1-syllable one.
         */

        String simpleWord = stripFirstPunctuation(word);
        int vowels = 0;

        for (int i = 0; i < simpleWord.length(); i++) {
            boolean vowel;
            char currentLetter = simpleWord.charAt(i);
            vowel = checkIfVowel(currentLetter);

            // checking for two vowels in a row
            if (i > 0 && checkIfVowel(simpleWord.charAt(i - 1)) && vowel) {
                vowel = false;
            }

            // checking for 'e' at the end of a word
            if (i == simpleWord.length() - 1
                    && (currentLetter == 'e' || currentLetter == 'E')) {
                vowel = false;
            }

            // if letter is vowel, increase vowel count
            if (vowel) {
                vowels++;
            }
        }

        if (vowels == 0) {
            vowels = 1;
        }

        return vowels;
    }

    static String stripFirstPunctuation (String word) {
        return word.replaceFirst("[.?!,]", "");
    }

    static boolean checkIfVowel(char letter) {
        boolean isVowel;

        switch (letter) {
            case 'a':
            case 'e':
            case 'i':
            case 'o':
            case 'u':
            case 'y':
            case 'A':
            case 'E':
            case 'I':
            case 'O':
            case 'U':
            case 'Y':
                isVowel = true;
                break;
            default:
                isVowel = false;
        }

        return isVowel;
    }

    static double calculateARIScore (double characters,
                                  double words, double sentences) {
        return 4.71 * (characters / words)
                + .5 * (words / sentences)
                - 21.43;
    }

    static void printARIScore(double score) {
        System.out.print("Automated Readability Index: " + df.format(score));
    }

    static double calculateFKScore (double words,
                                    double sentences, double syllables) {
        return 0.39 * (words / sentences)
                + 11.8 * (syllables / words)
                -15.59;
    }

    static void printFKScore(double score) {
        System.out.print("Flesch–Kincaid readability tests: " + df.format(score));
    }

    static double calculateSMOGScore (double polysallables, double sentences) {
        return 1.043
                * Math.sqrt(polysallables * 30 / sentences)
                + 3.1291;
    }

    static void printSMOGScore(double score) {
        System.out.print("Simple Measure of Gobbledygook: " + df.format(score));
    }

    static double calculateCLScore (double words,
                                    double letters, double sentences) {
        double L = letters / words * 100;
        double S = sentences / words * 100;

        return 0.0588 * L
                - 0.296 * S
                - 15.8;
    }

    static void printCLScore(double score) {
        System.out.print("Coleman–Liau index: " + df.format(score));
    }

    static int calculateReadingAge (double score) {
        int finalScore = (int) Math.round(score);
        int recommendedAge;

        switch (finalScore) {
            case 1:
                recommendedAge = 6;
                break;
            case 2:
                recommendedAge = 7;
                break;
            case 3:
                recommendedAge = 9;
                break;
            case 4:
                recommendedAge = 10;
                break;
            case 5:
                recommendedAge = 11;
                break;
            case 6:
                recommendedAge = 12;
                break;
            case 7:
                recommendedAge = 13;
                break;
            case 8:
                recommendedAge = 14;
                break;
            case 9:
                recommendedAge = 15;
                break;
            case 10:
                recommendedAge = 16;
                break;
            case 11:
                recommendedAge = 17;
                break;
            case 12:
                recommendedAge = 18;
                break;
            case 13:
                recommendedAge = 24;
                break;
            case 14:
                recommendedAge = 25;
                break;
            default:
                recommendedAge = 0;
        }

        return recommendedAge;

        /*
        Score 	Age 	Grade Level
            1 	5-6 	Kindergarten
            2 	6-7 	First/Second Grade
            3 	7-9 	Third Grade
            4 	9-10 	Fourth Grade
            5 	10-11 	Fifth Grade
            6 	11-12 	Sixth Grade
            7 	12-13 	Seventh Grade
            8 	13-14 	Eighth Grade
            9 	14-15 	Ninth Grade
            10 	15-16 	Tenth Grade
            11 	16-17 	Eleventh Grade
            12 	17-18 	Twelfth grade
            13 	18-24 	College student
            14 	24+ 	Professor
        */
    }

    static void printReadingAge(int readingAge) {
        System.out.println(" (about " + Math.round(readingAge) + " year olds).");
    }

    static void printDemographics (double characters, double words, double sentences,
                              double syllables, double polysyllables) {
        print("Words: " + (int) words);
        print("Sentences: " + (int) sentences);
        print("Characters: " + (int) characters);
        print("Syllables: " + (int) syllables);
        print("Polysyllables: " + (int) polysyllables);
    }

    static void print (String value) {
        System.out.println(value);
    }
}
