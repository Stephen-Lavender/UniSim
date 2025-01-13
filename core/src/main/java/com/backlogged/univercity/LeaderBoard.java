package com.backlogged.univercity;

import java.util.ArrayList;
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

    String filePath = Gdx.files.internal(Constants.PLAYER_SCORES_PATH).toString();
    public HashMap<Integer, Pair<String,Integer>> playerScores = new HashMap<>(); 
    private File scoreFile;



    
    //constructer
    public LeaderBoard() {
        scoreFile = new File(this.filePath);
        ArrayList<Pair<String,Integer>> unSortedList = new ArrayList<>();

        if (!scoreFile.exists()) {
            try {
                scoreFile.createNewFile();
            }
            catch(IOException e) {
            }
        }

        try {
            Scanner scanner = new Scanner(scoreFile);

            while (scanner.hasNextLine()) {
                String Line = scanner.nextLine();
                unSortedList.add(new Pair(Line.split(":")[0], Integer.parseInt(Line.split(":")[1])));            
            }
            scanner.close();

            unSortedList.sort((p1, p2) -> p2.getScore() - p1.getScore());

            for (int i = 0; i < unSortedList.size(); i++) {
                playerScores.put(i,unSortedList.get(i));
            }


        }
        catch(FileNotFoundException e) {
        }
        
    
    }


    //add new name and score in player score file 
    public void addNewScore(String name, int score) {
        String filePath = this.filePath; 
        String playerName = name;
        int playerScore = score;

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));

            writer.write(playerName + ":" + playerScore);
            writer.newLine();
            writer.close();

        } catch (IOException e) {
            System.err.println("Error when trying to add a new score to " + Constants.PLAYER_SCORES_PATH + e.getMessage());
        }

    }

}
