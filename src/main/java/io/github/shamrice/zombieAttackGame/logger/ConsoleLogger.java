package io.github.shamrice.zombieAttackGame.logger;

import java.util.Date;

/**
 * Created by Erik on 8/13/2017.
 */
public class ConsoleLogger extends Logger {

    public ConsoleLogger() {
        super();
    }

    public void logInfo(String info) {
        System.out.println(new Date().toString() + " : [INFO]      : " + info);
    }
    public void logDebug(String message) {
        System.out.println(new Date().toString() + " : [DEBUG]     : " +  message);
    }

    public void logError(String error) {
        System.out.println(new Date().toString() + " : [ERROR]     : " + error);
    }

    public void logException(String message, Exception exception) {
        System.out.println(new Date().toString() + " : [EXCEPTION] : " +  message);
        exception.printStackTrace();
    }
}
