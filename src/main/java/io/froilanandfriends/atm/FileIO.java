package io.froilanandfriends.atm;

import java.io.BufferedReader;
import java.io.FileReader;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.StringBuilder;

/**
 * This class is used by all the manager classes to either
 * read or log their data.
 */




public class FileIO {

    /**
     * Reads file specified by arg {@Code String path} by line, and builds
     * one big String from the contents of that file.
     * <p>
     * Called within load methods in each of the Managers.
     * Each manager that needs to read or write records should now have
     * a String pathname for the file it wants to use.
     */
    public static String readRecords(String path) throws Exception {
        String line;
        BufferedReader br = new BufferedReader(new FileReader(path));
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = br.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        br.close();
        return stringBuilder.toString().trim();
    }


    /**
     *  Called by the log methods of each Manager class.
     *  Receives long string from said methods, writes it to file. X is long String, y is file path,
     *  usually specified by the PATHNAME field in each Manager.
     */
    // This is the new way to do this.  Each MANAGER that needs to set records should now have
    // a String pathname = its log file path.  When it calls setRecords, it should pass its pathname.
    public static void logRecords(String outString, String path) throws Exception {
        PrintWriter out = new PrintWriter(new FileWriter(path, false), false);
        out.write(outString.trim());
        out.close();
    }

}