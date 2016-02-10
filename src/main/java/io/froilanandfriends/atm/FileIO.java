package io.froilanandfriends.atm;

import java.io.BufferedReader;
import java.io.FileReader;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.StringBuilder;

public class FileIO {

    /*** FILE READING - Reads file by line, rebuilds as String, sends off ***/
    // This is the new way to do this.  Each MANAGER that needs to set records should now have
    // a String pathname = its log file path.  When it calls setRecords, it should pass its pathname.
    public static String readRecords(String path) throws Exception {
        String line;
        BufferedReader br = new BufferedReader(new FileReader(path));
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = br.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        br.close();
        return stringBuilder.toString();
    }
    /****  END OF FILE READING  ****/

    /*** FILE WRITING -  Receives long string from Manager, writes it to file - x is long String, y is file path ***/
    // This is the new way to do this.  Each MANAGER that needs to set records should now have
    // a String pathname = its log file path.  When it calls setRecords, it should pass its pathname.
    public static void logRecords(String outString, String path) throws Exception {
        PrintWriter out = new PrintWriter(new FileWriter(path, false), false);
        out.write(outString.trim());
        out.close();
    }
    /**** END OF FILE WRITING  ****/
}