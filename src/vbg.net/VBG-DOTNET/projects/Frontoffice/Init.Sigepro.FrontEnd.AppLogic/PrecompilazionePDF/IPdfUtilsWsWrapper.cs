using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.ServizioPrecompilazionePDF;
using log4net;
using System;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.PrecompilazionePDF
{
    public interface IPdfUtilsWsWrapper
    {
        BinaryFile PrecompilaPdf(BinaryFile pdfFile, BinaryFile xmlFile);
        DatiPdfCompilabile EstraiXml(BinaryFile pdfFile);
    }
}
