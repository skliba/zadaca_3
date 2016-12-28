package com.skliba.parsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public abstract class Parser {

    public void readFile(String filename) {
        BufferedReader bufferedReader;

        try {
            bufferedReader = new BufferedReader(new FileReader(new File(filename)));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                if (!line.isEmpty()) {
                    parseData(line);
                }
            }
            parseComplete();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    abstract void parseData(String line);
    abstract void parseComplete();
}
