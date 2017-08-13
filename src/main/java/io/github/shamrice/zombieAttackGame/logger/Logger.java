package io.github.shamrice.zombieAttackGame.logger;

/**
 * Created by Erik on 8/13/2017.
 */
public abstract class Logger {

    public abstract void logInfo(String info);

    public abstract void logDebug(String message);

    public abstract void logError(String error);

    public abstract void logException(String message, Exception exception);
}
