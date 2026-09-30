package it.alveo.ricalcoloaree.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {

    private static final Logger log = LoggerFactory.getLogger(LogUtil.class);

    public static void info(Object o, String methodName, String info) {

	log.info(o.getClass() + " - " + methodName + " - " + info);
    }

    public static void error(Object o, String methodName, String error, Throwable e) {

	log.error(o.getClass() + " - " + methodName + " - " + error, e);
    }

    public static void debug(Object o, String methodName, String debug) {

	log.debug(o.getClass() + " - " + methodName + " - " + debug);
    }
}
