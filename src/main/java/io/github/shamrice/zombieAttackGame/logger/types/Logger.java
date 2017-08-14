package io.github.shamrice.zombieAttackGame.logger.types;

/**
 * Created by Erik on 8/13/2017.
 */
public interface Logger {

    void logInfo(String info);

    void logDebug(String message);

    void logError(String error);

    void logException(String message, Exception exception);
}
