package ch.hearc.ig.tools;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {
    private static final Logger logger = createLogger();

    private Log() {
        // Private constructor to prevent instantiation
    }

    private static Logger createLogger() {
        Logger logger = Logger.getLogger("ApplicationLog");
        logger.setLevel(Level.ALL);
        for (Handler handler : logger.getHandlers()) {
            handler.setLevel(Level.ALL);
        }
        return logger;
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void warn(String message) {
        logger.warning(message);
    }

    public static void error(String message, Throwable throwable) {
        logger.log(Level.SEVERE, message, throwable);
    }

    public static void debug(String message) {
        logger.fine(message);
    }
}
