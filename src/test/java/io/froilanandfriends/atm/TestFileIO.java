package io.froilanandfriends.atm;

import org.junit.Test;
import java.io.BufferedReader;
import java.io.FileReader;

import static org.junit.Assert.*;

public class TestFileIO {


    @Test
    public void testFileIO_setRecords_stringReturn() throws Exception {
        assertTrue(FileIO.readRecords("testLog.csv") instanceof String);
    }

    //Test if string returned by .setRecords() matches expected return value.
    @Test
    public void testFileIO_setRecords_stringOutput() throws Exception {

        String testString = "Object,Attribute1,Attribute2,Attribute3,Attribute4,Attribute5,Attribute6" +"\n" +
                "Object-2,Attribute1-2,Attribute2-2,Attribute3-2,Attribute4-2,Attribute5-2,Attribute6-2";
        assertTrue(FileIO.readRecords("testLog.csv").equals(testString));
    }

    @Test
    public void testFileIO_logRecords_logOutput() throws Exception {
        //Feed logger a test string.
        String testLogCSV = "Object,Attribute1,Attribute2,Attribute3,Attribute4,Attribute5,Attribute6" +"\n" +
                "Object-2,Attribute1-2,Attribute2-2,Attribute3-2,Attribute4-2,Attribute5-2,Attribute6-2";
        FileIO.logRecords(testLogCSV,"testLog.csv");

        //Read the logged out file
        String line;
        BufferedReader br = new BufferedReader(new FileReader("testLog.csv"));
        StringBuilder stringBuilder = new StringBuilder();

        while ((line = br.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        br.close();

        //Check that logger logged test string properly.
        String expectedResult =  "Object,Attribute1,Attribute2,Attribute3,Attribute4,Attribute5,Attribute6" +"\n" +
                "Object-2,Attribute1-2,Attribute2-2,Attribute3-2,Attribute4-2,Attribute5-2,Attribute6-2";
        assertTrue(expectedResult.equals(stringBuilder.toString().trim()));


    }

}
