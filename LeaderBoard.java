package com.backlogged.univercity;

import java.util.HashMap;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

import javax.sound.midi.SysexMessage;

import com.badlogic.gdx.Gdx;


public class LeaderBoard {
    private class Pair<T, U> {
        
        private String name;
        private int score;

        public Pair(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String name() {
            return this.name;
        }
        
        public int getScore() {
            return this.score;
        }

        public String toString() {
            return this.name  + ": " + this.score;
        }
        
    }

    String filePath = Gdx.files.internal("PlayerScores.txt").toString();
    HashMap<Integer, Pair<String,Integer>> playerScores = new HashMap<>();
    File scoreFile;




    //constructer
    public LeaderBoard() {
        System.out.println("run leaderboard");
        scoreFile = new File(this.filePath);
        if (!scoreFile.exists()) {
            try {
                System.out.println("created file");
                scoreFile.createNewFile();
            }
            catch(IOException e) {
            }
        }
        try {
            Scanner scanner = new Scanner(scoreFile);
            System.out.println("running scanner");
            int i = 0;
            while (scanner.hasNextLine()) {
                System.out.println("running scanner loop");
                String Line = scanner.nextLine();

                System.out.println(Line);

                //playerScores.put(i, new Pair(Line.split(" ")[0], Integer.parseInt(Line.split(" ")[1])));  
                i += 1;            
                    
            }
            scanner.close();
        }


            
            
        catch(FileNotFoundException e) {
        }

    
    }


    public void addNewScore(String name, int score) {
        String filePath = this.filePath; // Replace with the path to your score file
        String playerName = name;
        int playerScore = score;

        try {
            // Open the file in append mode
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));

            // Write the new player's name and score
            writer.write(playerName + " " + playerScore);
            writer.newLine(); // Add a new line

            // Close the writer
            writer.close();

            System.out.println("Successfully added: " + playerName + " " + playerScore);
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }

    }
}
