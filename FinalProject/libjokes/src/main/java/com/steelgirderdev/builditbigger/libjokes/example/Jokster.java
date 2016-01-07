package com.steelgirderdev.builditbigger.libjokes.example;

import java.util.Random;

/**
 * Simple random Joke generator.
 * TODO: Replace with real funny implementation
 */
public class Jokster {
    public String getJoke(){
        Random rand = new Random();
        int min = 0;
        int max = 4;
        int randomNum = rand.nextInt((max - min) + 1) + min;

        String ret = "";
        //Source of jokes: http://www.journaldev.com/240/my-25-favorite-programming-quotes-that-are-funny-too
        switch (randomNum) {
            case 0: ret = "The best thing about a boolean is even if you are wrong, you are only off by a bit. (Anonymous)"; break;
            case 1: ret = "Without requirements or design, programming is the art of adding bugs to an empty text file. (Louis Srygley)"; break;
            case 2: ret = "Before software can be reusable it first has to be usable. (Ralph Johnson)"; break;
            case 3: ret = "The best method for accelerating a computer is the one that boosts it by 9.8 m/s2. (Anonymous)"; break;
            case 4: ret = "I think Microsoft named .Net so it wouldn't show up in a Unix directory listing. (Oktal)"; break;
            default: ret = "Funny!";
        }
        System.out.print(randomNum + " " + ret);
        return ret;
    }

}
