package it.gruppoinit.stc.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class ContentLengthTrackingWorkaroundFilter implements Filter {

    private static final String CONTENT_LENGTH = "Content-Length";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

	// No initialization required.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

	if (response instanceof HttpServletResponse) {
	    HttpServletResponse http = (HttpServletResponse) response;
	    chain.doFilter(request, new ContentLengthAwareResponseWrapper(http));
	} else {
	    chain.doFilter(request, response);
	}
    }

    @Override
    public void destroy() {

	// No resources to release.
    }

    private static final class ContentLengthAwareResponseWrapper extends HttpServletResponseWrapper {

	ContentLengthAwareResponseWrapper(HttpServletResponse response) {

	    super(response);
	}

	@Override
	public void setHeader(String name, String value) {

	    if (isContentLength(name)) {
		setContentLengthFromString(value, false);
	    } else {
		super.setHeader(name, value);
	    }
	}

	@Override
	public void addHeader(String name, String value) {

	    if (isContentLength(name)) {
		setContentLengthFromString(value, true);
	    } else {
		super.addHeader(name, value);
	    }
	}

	@Override
	public void setIntHeader(String name, int value) {

	    if (isContentLength(name)) {
		super.setContentLength(value);
	    } else {
		super.setIntHeader(name, value);
	    }
	}

	@Override
	public void addIntHeader(String name, int value) {

	    if (isContentLength(name)) {
		super.addHeader(CONTENT_LENGTH, Integer.toString(value));
	    } else {
		super.addIntHeader(name, value);
	    }
	}

	@Override
	public void setContentLength(int len) {

	    if (len >= 0 && len <= Integer.MAX_VALUE) {
		super.setContentLength((int) len);
	    } else {
		// Spring Security 4.2 traccia addHeader("Content-Length", ...).
		super.addHeader(CONTENT_LENGTH, Long.toString(len));
	    }
	}

	private void setContentLengthFromString(String value, boolean addMode) {

	    if (value == null) {
		if (addMode) {
		    super.addHeader(CONTENT_LENGTH, null);
		} else {
		    super.setHeader(CONTENT_LENGTH, null);
		}
		return;
	    }
	    try {
		long len = Long.parseLong(value);
		if (len >= 0 && len <= Integer.MAX_VALUE) {
		    super.setContentLength((int) len);
		} else {
		    super.addHeader(CONTENT_LENGTH, value);
		}
	    } catch (NumberFormatException ex) {
		if (addMode) {
		    super.addHeader(CONTENT_LENGTH, value);
		} else {
		    super.setHeader(CONTENT_LENGTH, value);
		}
	    }
	}

	private boolean isContentLength(String name) {

	    return CONTENT_LENGTH.equalsIgnoreCase(name);
	}
    }
}
