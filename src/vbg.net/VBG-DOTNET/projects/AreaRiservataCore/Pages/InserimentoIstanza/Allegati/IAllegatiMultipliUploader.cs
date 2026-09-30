using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti;

namespace AreaRiservataCore.Pages.InserimentoIstanza.Allegati
{
    public interface IAllegatiMultipliUploader
    {
        void AggiungiAllegatoPrincipale(DocumentoDomanda allegatoOriginale, int codiceOggetto);
        void AggiungiAllegatoSecondario(DocumentoDomanda allegatoOriginale, int indice, int codiceOggetto);
        DocumentoDomanda GetById(int idAllegato);
    }
}
