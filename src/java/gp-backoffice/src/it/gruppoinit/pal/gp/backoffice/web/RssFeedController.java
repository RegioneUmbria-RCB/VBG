package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Faq;
import it.gruppoinit.pal.gp.core.domain.Info;
import it.gruppoinit.pal.gp.core.domain.News;
import it.gruppoinit.pal.gp.core.service.FaqService;
import it.gruppoinit.pal.gp.core.service.InfoService;
import it.gruppoinit.pal.gp.core.service.NewsService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;

@Controller
public class RssFeedController extends BaseController<Object> {

    private static final String FEED_TYPE = "atom_0.3";
    private static final String COULD_NOT_GENERATE_FEED_ERROR = "Non è stato possibile generare l'output rss";
    @Autowired
    private NewsService newsService;
    @Autowired
    private FaqService faqService;
    @Autowired
    private InfoService infoService;
    private static final String MIME_TYPE = "application/xml; charset=UTF-8";

    @RequestMapping
    public void news(HttpServletRequest request, HttpServletResponse response) throws IOException {

	List<News> newss = newsService.findLatest(100);
	SyndFeed feed = new SyndFeedImpl();
	feed.setFeedType(FEED_TYPE);
	feed.setTitle("Notizie dal comune");
	feed.setLink("");
	feed.setDescription("Le ultime notizie dal comune. Pubblica le novità inerenti lo Sportello Unico e l'amministrazione comunale.");
	List<SyndEntry> entries = new ArrayList<SyndEntry>();
	SyndEntry entry = null;
	SyndContent description = null;
	for (News news : newss) {
	    entry = new SyndEntryImpl();
	    entry.setTitle(news.getTitolo());
	    entry.setLink("");
	    entry.setPublishedDate(news.getData());
	    description = new SyndContentImpl();
	    description.setType("text/html");
	    description.setValue(news.getNews());
	    entry.setDescription(description);
	    entries.add(entry);
	}
	feed.setEntries(entries);
	SyndFeedOutput output = new SyndFeedOutput();
	try {
	    response.setContentType(MIME_TYPE);
	    output.output(feed, response.getWriter());
	} catch (FeedException ex) {
	    String msg = COULD_NOT_GENERATE_FEED_ERROR;
	    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, msg);
	}
    }

    @RequestMapping
    public void faq(HttpServletRequest request, HttpServletResponse response) throws IOException {

	List<String> softwareList = new ArrayList<String>();
	softwareList.add(WebConstants.SOFTWARE_TT);
	if (!ORMHelper.getSoftware().equalsIgnoreCase(WebConstants.SOFTWARE_TT)) {
	    softwareList.add(ORMHelper.getSoftware());
	}
	List<Faq> newss = faqService.findByFilter(softwareList, 0, 300, Boolean.TRUE);
	SyndFeed feed = new SyndFeedImpl();
	feed.setFeedType(FEED_TYPE);
	feed.setTitle("Cerca tra le FAQ del comune");
	feed.setLink("");
	feed.setDescription("In questa sezione sono pubblicate le risposte ai quesiti più frequenti");
	List<SyndEntry> entries = new ArrayList<SyndEntry>();
	SyndEntry entry = null;
	SyndContent description = null;
	SyndContent title = null;
	for (Faq faq : newss) {
	    if (faq.getPubblicare() != null) {
		if (faq.getPubblicare().booleanValue()) {
		    entry = new SyndEntryImpl();
		    title = new SyndContentImpl();
		    title.setType("text/html");
		    title.setValue(faq.getDomanda());
		    entry.setTitleEx(title);
		    entry.setLink("");
		    entry.setPublishedDate(faq.getData());
		    description = new SyndContentImpl();
		    description.setType("text/html");
		    description.setValue(faq.getRisposta());
		    entry.setDescription(description);
		    entries.add(entry);
		}
	    }
	}
	feed.setEntries(entries);
	SyndFeedOutput output = new SyndFeedOutput();
	try {
	    response.setContentType(MIME_TYPE);
	    output.output(feed, response.getWriter());
	} catch (FeedException ex) {
	    String msg = COULD_NOT_GENERATE_FEED_ERROR;
	    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, msg);
	}
    }

    @RequestMapping
    public void info(HttpServletRequest request, HttpServletResponse response) throws IOException {

	List<Info> newss = infoService.findAll(0, 300);
	SyndFeed feed = new SyndFeedImpl();
	feed.setFeedType(FEED_TYPE);
	feed.setTitle("Cerca tra le informative del comune");
	feed.setLink("");
	feed.setDescription("In questa sezione sono pubblicate le informative del comune");
	List<SyndEntry> entries = new ArrayList<SyndEntry>();
	SyndEntry entry = null;
	SyndContent description = null;
	for (Info faq : newss) {
	    entry = new SyndEntryImpl();
	    entry.setTitle(faq.getTitolo());
	    entry.setLink(null);
	    entry.setPublishedDate(null);
	    entry.setLink(faq.getIndirizzoweb());
	    entry.setDescription(null);
	    entries.add(entry);
	}
	feed.setEntries(entries);
	SyndFeedOutput output = new SyndFeedOutput();
	try {
	    response.setContentType(MIME_TYPE);
	    output.output(feed, response.getWriter());
	} catch (FeedException ex) {
	    String msg = COULD_NOT_GENERATE_FEED_ERROR;
	    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, msg);
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @Override
    protected void fixMergeEntityProperty(Object entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Object entity) {

    }
}
