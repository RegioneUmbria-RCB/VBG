using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti;
using Init.Sigepro.FrontEnd.AppLogic.STC.Adapter;

namespace Init.Sigepro.FrontEnd.AppLogic.PrecompilazionePDF
{
    public interface IPdfUtilsService
	{
		BinaryFile PrecompilaPdf(int codiceOggettoPdf, int idDomandaOnline);
		BinaryFile PrecompilaPdf(int codiceOggettoPdf, int idDomandaOnline, IIstanzaStcAdapter adapter);

        DatiPdfCompilabile EstraiDatiPdf(DocumentoDomanda allegato);
		DatiPdfCompilabile EstraiDatiPdf(int codiceOggetto);
		DatiPdfCompilabile EstraiDatiPdf(BinaryFile oggetto);
	}

}
