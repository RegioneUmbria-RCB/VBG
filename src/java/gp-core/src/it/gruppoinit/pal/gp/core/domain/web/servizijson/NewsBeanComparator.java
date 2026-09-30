package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class NewsBeanComparator implements Comparator<NewsBean> {

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public int compare(NewsBean o1, NewsBean o2) {

	if (o1 == null && o2 == null) {
	    return 0;
	}
	if (o1 != null && o2 == null) {
	    return -1;
	}
	if (o1 == null && o2 != null) {
	    return 1;
	}
	String data1 = StringUtils.defaultIfEmpty(o1.getData(), "31/12/0000");
	String data2 = StringUtils.defaultIfEmpty(o2.getData(), "31/12/0000");
	int result = 0;
	try {
	    Date d1 = sdf.parse(data1);
	    Date d2 = sdf.parse(data2);
	    result = d2.compareTo(d1);
	} catch (ParseException e) {
	}
	if (result == 0) {
	    result = StringUtils.defaultString(o1.getTitolo()).compareTo(o2.getTitolo());
	}
	return result;
    }
}
