package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.service.exception.PraticheRestException;
import it.gruppoinit.pal.gp.core.service.helper.DownloadPraticaZipHelper;
import it.gruppoinit.pal.gp.core.service.helper.PraticheRestBeanResult;
import it.gruppoinit.pal.gp.core.service.helper.RiferimentiPraticaSTCRestBean;

import java.io.InputStream;
import java.util.Date;

public interface ApiProducerService {

    public RiferimentiPraticaSTCRestBean creaPraticaDaZip(InputStream zipFile, String numeroProtocollo, Date dataProtocollo);

    public PraticheRestBeanResult findPraticheByCF(String cf, String software, Integer offset, Integer limit) throws PraticheRestException;

    @DeletableCacheElements
    public void resetObjectCached();

    public DownloadPraticaZipHelper scaricaZipPratica(String uuid, boolean includiDocumentiDeiMovimenti) throws Exception;
}
