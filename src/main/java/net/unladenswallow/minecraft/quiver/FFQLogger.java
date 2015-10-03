package net.unladenswallow.minecraft.quiver;

import java.io.File;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.relauncher.Side;

/**
 * Custom Logger for ModEmeraldMaterial.  Copied and modified from FMLRelaunchLog.
 * @author gus
 *
 */
public class FFQLogger {
    /**
     * Our special logger for logging issues to. We copy various assets from the
     * Minecraft logger to achieve a similar appearance.
     */
    public static FFQLogger log = new FFQLogger();

    static File minecraftHome;
    private static boolean configured;

    private Logger myLog;

    static Side side;

    private FFQLogger()
    {
    }

    /**
     * Configure the FML logger and inject tracing printstreams.
     */
    private static void configureLogging()
    {
        log.myLog = LogManager.getLogger("FFQ");
//        ThreadContext.put("side", side.name().toLowerCase(Locale.ENGLISH));
        configured = true;
    }

    public static void log(String targetLog, Level level, String format, Object... data)
    {
        LogManager.getLogger(targetLog).log(level, String.format(format, data));
    }

    public static void log(Level level, String format, Object... data)
    {
        if (!configured)
        {
            configureLogging();
        }
        log.myLog.log(level, String.format(format, data));
    }

    public static void log(String targetLog, Level level, Throwable ex, String format, Object... data)
    {
        LogManager.getLogger(targetLog).log(level, String.format(format, data), ex);
    }

    public static void log(Level level, Throwable ex, String format, Object... data)
    {
        if (!configured)
        {
            configureLogging();
        }
        log.myLog.log(level, String.format(format, data), ex);
    }

    public static void severe(String format, Object... data)
    {
        log(Level.ERROR, format, data);
    }

    public static void warning(String format, Object... data)
    {
        log(Level.WARN, format, data);
    }

    public static void info(String format, Object... data)
    {
        log(Level.INFO, format, data);
    }

    public static void fine(String format, Object... data)
    {
        log(Level.DEBUG, format, data);
    }

    public static void finer(String format, Object... data)
    {
        log(Level.TRACE, format, data);
    }

    public Logger getLogger()
    {
        return myLog;
    }

}
