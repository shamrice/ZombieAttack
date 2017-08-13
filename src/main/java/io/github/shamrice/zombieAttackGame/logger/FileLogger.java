package io.github.shamrice.zombieAttackGame.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Erik on 8/13/2017.
 */
public class FileLogger extends Logger {

    private String fileName;

    public FileLogger(String fileName) {
        super();

        this.fileName = fileName;
    }

    public void logInfo(String info) {
        writeToFile(new Date().toString() + " : [INFO]      : " +  info);
    }
    public void logDebug(String message) {
        writeToFile(new Date().toString() + " : [DEBUG]     : " + message);
    }

    public void logError(String error) {
        writeToFile(new Date().toString() + " : [ERROR]     : " + error);
    }

    public void logException(String message, Exception exception) {
        writeToFile(new Date().toString() + " : [EXCEPTION] : " + message);
        for (StackTraceElement element : exception.getStackTrace()) {
            writeToFile(element.toString());
        }
    }

    private void writeToFile(String stringToWrite) {
        BufferedWriter writer = null;

        try {
            File logFile = new File(fileName);
            writer = new BufferedWriter(new FileWriter(logFile, true));
            writer.write(stringToWrite + "\r\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception ex2) {
                ex2.printStackTrace();
            }
        }
    }
}
