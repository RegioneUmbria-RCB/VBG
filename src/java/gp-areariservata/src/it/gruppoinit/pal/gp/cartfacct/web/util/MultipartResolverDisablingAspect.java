package it.gruppoinit.pal.gp.cartfacct.web.util;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartResolver;

/**
 * Disables the spring multipart resolver for specific client requests and
 * therefore keeps the request intact so that controllers can process it in
 * whatever way they wish. This behaviour is triggered by a particular GET
 * parameter in the client request so it is configurable.
 * @author lion
 * @see MultipartResolver
 */
@Aspect
public class MultipartResolverDisablingAspect {
    
    /**
     * GET parameter which, if present in request, enables advice.
     */
    private static final String DISABLING_HTTP_REQUEST_PARAMETER_KEY = "disableMR";
    
    private static boolean disablingParameterExists(final HttpServletRequest request) {
        Assert.notNull(request);
        return request.getParameter(DISABLING_HTTP_REQUEST_PARAMETER_KEY) != null;
    }
    
    /**
     * If above GET parameter exists in request then prompt the spring multipart
     * resolver to always tell spring that request is not of type multipart.
     * Spring then does not process the request any further.
     * @param pjp
     * @param request
     * @return
     * @throws Throwable
     */
    @Around("isMultipartOperation() && args(request)")
    public Object disableIsMultipartOperation(final ProceedingJoinPoint pjp, final HttpServletRequest request)
            throws Throwable {
        
        Assert.notNull(pjp);
        Assert.notNull(request);
        
        if (disablingParameterExists(request)) {
            return Boolean.FALSE;
        }
        
        return pjp.proceed();
    }
    
    /**
     * Applies to any implementation of {@linkplain MultipartResolver}
     */
    @SuppressWarnings("unused")
    @Pointcut("execution(public boolean org.springframework.web.multipart.MultipartResolver.isMultipart(javax.servlet.http.HttpServletRequest))")
    private void isMultipartOperation() {}
    
}