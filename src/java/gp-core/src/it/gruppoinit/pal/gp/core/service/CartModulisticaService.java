package it.gruppoinit.pal.gp.core.service;

import it.eng.suap.xengine.model.modulistica.FileType;
import it.eng.suap.xengine.model.modulistica.ModuloType;
import it.eng.suap.xengine.model.service.xcommon.ModulisticaContentType;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.autocompiler.AutocompilerConfig;
import it.gruppoinit.pal.gp.core.domain.cart.AllegatoDaFirmare;
import it.gruppoinit.pal.gp.core.domain.cart.DatiDomandaCart;
import it.gruppoinit.pal.gp.core.domain.cart.ErroreValidazione;
import it.gruppoinit.pal.gp.core.domain.cart.ModuloRendering;
import it.gruppoinit.pal.gp.core.domain.helper.CartModuloHelper;

import java.io.File;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;

public interface CartModulisticaService {

    public List<ModuloRendering> renderModulistica(DatiDomandaCart inputData, Map<Object, Object> contextData);

    public void renderModuloToFile(ModuloType modulo, DatiDomandaCart inputData, Map<Object, Object> contextData, File outputFile) throws Exception;

    public List<ErroreValidazione> validazioneQuadro(DatiDomandaCart inputData, String idModulo, String idQuadro);

    public List<ErroreValidazione> validazioneDomanda(DatiDomandaCart inputData);

    public AllegatoDaFirmare validaFirmaDigitale(FileType campoFile, FileItem fileItem, int rowIndex);

    //public Oggetti validaFirmaDigitale(Integer codiceOggetto);
    public AllegatoDaFirmare validaFirmaDigitale(Oggetti oggetto);

    public byte[] convertHtmlToPdFile(byte[] htmlContent) throws RemoteException;

    //public String[] getValoreCampoDefault(DatiDomandaCart inputData, String idModulo, String idSemantico, int rowIndex);
    public void impostaValoriDefault(String refModulo, DatiDomandaCart datiDomanda);

    public byte[] renderSchedaDinamicaPdf(String htmlContent, Map<Object, Object> context, File pdfOutput, int documentWidth) throws Exception;

    /**
     * Restituisce la modulistica STANDARD 00 per la compilazione di domande CART da parte degli operatori del BO,
     * per consentire la notifica ad enti terzi secondo le specifiche del CARTn anche di domande non pervenute dall'infrastruttura CART.
     * Il modulo restiutito viene recuperato dal file modulo_standard_00.xml opportunamente predisposto. 
     * 
     * @return
     */
    public ModulisticaContentType getModulisticaStandard00() throws Exception;

    public DatiDomandaCart leggiDatiQuadro(HttpServletRequest req, String refModulo, String idQuadro, DatiDomandaCart dati);

    public CartModuloHelper getModuloHelperInstance(ModuloType modulo, DatiDomandaCart inputData, AutocompilerConfig autocompilerConfig);
}
