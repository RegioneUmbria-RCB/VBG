package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.report.model.ReportBase;
import it.gruppoinit.pal.gp.core.report.model.ReportIstanze;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ReportService {

    public void doReport(Map map, HttpServletRequest request, HttpServletResponse response);

    /**
     * MMetodo che richiama la creazione di report di tabelle di base
     * 
     * @param reportBase
     */
    public void doReportDiBase(ReportBase reportBase, HttpServletRequest request, HttpServletResponse response);

    /**
     * Metodo che richiama la creazione di report specifici per un istanza
     * 
     * @param reportIstanze
     */
    public void doReportAndStatisticheIstanze(ReportIstanze reportIstanze, HttpServletRequest request, HttpServletResponse response);

    public String exportReportGenerico(Esportazioni esportazioni, String emailResponsabile, String codiceComune);
}
