package it.openutils.log4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Log4jConfigurationServlet extends HttpServlet {

    private static final long serialVersionUID = 64182L;
    private static final String[] LEVELS = { Level.OFF.toString(), Level.FATAL.toString(), Level.ERROR.toString(), Level.WARN.toString(),
	    Level.INFO.toString(), Level.DEBUG.toString(), Level.TRACE.toString(), Level.ALL.toString() };
    private boolean isFragment;

    @SuppressWarnings("rawtypes")
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	String className = request.getParameter("class");
	String level = request.getParameter("level");
	if ((className != null) && (level != null)) {
	    setClass(className, level);
	}
	String sortByLevelParam = request.getParameter("sortbylevel");
	boolean sortByLevel = ("true".equalsIgnoreCase(sortByLevelParam)) || ("yes".equalsIgnoreCase(sortByLevelParam));
	List loggers = getSortedLoggers(sortByLevel);
	int loggerNum = 0;
	PrintWriter out = response.getWriter();
	if (!this.isFragment) {
	    response.setContentType("text/html");
	    printHeader(out, request);
	}
	out.println("<a href=\"" + request.getRequestURI() + "\">Refresh</a>");
	out.println("<table class=\"log4jtable\">");
	out.println("<thead><tr>");
	out.println("<th title=\"Logger name\">");
	out.println("<a href=\"?sortbylevel=false\">Class</a>");
	out.println("</th>");
	out.println("<th title=\"Is logging level inherited from parent?\" style=\"text-align:right\" >*</th>");
	out.println("<th title=\"Logger level\">");
	out.println("<a href=\"?sortbylevel=true\">Level</a>");
	out.println("</th>");
	out.println("</tr></thead>");
	out.println("<tbody>");
	displayLogger(out, Logger.getRootLogger(), loggerNum++, request);
	Iterator iterator = loggers.iterator();
	while (iterator.hasNext()) {
	    displayLogger(out, (Logger) iterator.next(), loggerNum++, request);
	}
	out.println("</tbody>");
	out.println("</table>");
	out.println("<a href=\"\">Refresh</a>");
	if (!this.isFragment) {
	    out.println("</body></html>");
	    out.flush();
	    out.close();
	}
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	doGet(request, response);
    }

    private void displayLogger(PrintWriter out, Logger logger, int row, HttpServletRequest request) {

	String color = null;
	String loggerName = logger.getName().equals("") ? "Root" : logger.getName();
	color = row % 2 == 1 ? "even" : "odd";
	out.println("<tr class=\"" + color + "\">");
	out.println("<td>");
	out.println(loggerName);
	out.println("</td>");
	out.println("<td style=\"text-align:right\">");
	if (logger.getLevel() == null) {
	    out.println("*");
	}
	out.println("</td>");
	out.println("<td>");
	out.println("<form action=\"\" method=\"post\">");
	printLevelSelector(out, logger.getEffectiveLevel().toString());
	out.println("<input type=\"hidden\" name=\"class\" value=\"" + loggerName + "\">");
	out.print("<input type=\"submit\" name=\"Set\" value=\"Set \">");
	out.println("</form>");
	out.println("</td>");
	out.println("</tr>");
    }

    private synchronized String setClass(String className, String level) {

	Logger logger = null;
	try {
	    logger = "Root".equalsIgnoreCase(className) ? Logger.getRootLogger() : Logger.getLogger(className);
	    logger.setLevel(Level.toLevel(level));
	} catch (Throwable e) {
	    System.out.println("ERROR Setting LOG4J Logger:" + e);
	}
	return "Message Set For " + (logger.getName().equals("") ? "Root" : logger.getName());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private List<Logger> getSortedLoggers(boolean sortByLevel) {

	Enumeration enm = LogManager.getCurrentLoggers();
	List list = new ArrayList();
	while (enm.hasMoreElements()) {
	    list.add(enm.nextElement());
	}
	Collections.sort(list, new LoggerComparator(sortByLevel));
	return list;
    }

    private void printHeader(PrintWriter out, HttpServletRequest request) {

	out.println("<html><head><title>Log4J Control Console</title>");
	out.println("<style type=\"text/css\">");
	out.println("body{ background-color:#fff; }");
	out.println("body, td, th, select, input{ font-family:Verdana, Geneva, Arial, sans-serif; font-size: 8pt;}");
	out.println("select, input{ border: 1px solid #ccc;}");
	out.println("table.log4jtable, table.log4jtable td { border-collapse:collapse; border: 1px solid #ccc; ");
	out.println("white-space: nowrap; text-align: left; }");
	out.println("form { margin:0; padding:0; }");
	out.println("table.log4jtable thead tr th{ background-color: #5991A6; padding: 2px; }");
	out.println("table.log4jtable tr.even { background-color: #eee; }");
	out.println("table.log4jtable tr.odd { background-color: #fff; }");
	out.println("</style>");
	out.println("</head>");
	out.println("<body>");
	out.println("<h3>Log4J Control Console</h3>");
    }

    private void printLevelSelector(PrintWriter out, String currentLevel) {

	out.println("<select id=\"level\" name=\"level\">");
	for (int j = 0; j < LEVELS.length; j++) {
	    out.print("<option");
	    if (LEVELS[j].equals(currentLevel)) {
		out.print(" selected=\"selected\"");
	    }
	    out.print(">");
	    out.print(LEVELS[j]);
	    out.println("</option>");
	}
	out.println("</select>");
    }

    public void init(ServletConfig config) throws ServletException {

	String fragmentParam = config.getInitParameter("fragment");
	this.isFragment = (("true".equalsIgnoreCase(fragmentParam)) || ("yes".equalsIgnoreCase(fragmentParam)));
	super.init(config);
    }

    private class LoggerComparator implements Comparator<Logger> {

	private boolean sortByLevel;

	public LoggerComparator(boolean sortByLevel) {

	    this.sortByLevel = sortByLevel;
	}

	public int compare(Logger logger1, Logger logger2) {

	    if (!this.sortByLevel) {
		return logger1.getName().compareTo(logger2.getName());
	    }
	    return logger1.getEffectiveLevel().toInt() - logger2.getEffectiveLevel().toInt();
	}

	public boolean equals(Object object) {

	    if (!(object instanceof LoggerComparator)) {
		return false;
	    }
	    return this.sortByLevel == ((LoggerComparator) object).sortByLevel;
	}

	public int hashCode() {

	    return super.hashCode();
	}
    }
}
