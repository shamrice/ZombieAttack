package io.github.shamrice.zombieAttackGame.logger;

import io.github.shamrice.zombieAttackGame.logger.types.Logger;

/**
 * Created by Erik on 8/14/2017.
 */
public class Log {

    private Log instance = new Log();
    private static Logger logger;
    private static LogLevel logLevel;

    public static void setLogger(Logger loggerToUse, LogLevel logLevelToUse) {
        logger = loggerToUse;
        logLevel = logLevelToUse;
    }

    public static void logDebug(String text) {
        if (logLevel.ordinal() >= LogLevel.DEBUG.ordinal()) {
            logger.logDebug(text);
        }
    }

    public static void logInfo(String text) {
        if (logLevel.ordinal() >= LogLevel.INFO.ordinal())
        logger.logInfo(text);
    }

    public static void logError(String text) {
        if (logLevel.ordinal() >= LogLevel.ERROR.ordinal()) {
            logger.logError(text);
        }
    }

    public static void logException(String message, Exception exc) {
        if (logLevel.ordinal() >= LogLevel.EXCEPTION.ordinal()) {
            logger.logException(message, exc);
        }
    }
}
