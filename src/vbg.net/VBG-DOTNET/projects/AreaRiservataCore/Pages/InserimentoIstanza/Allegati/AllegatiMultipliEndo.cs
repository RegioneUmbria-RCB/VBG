using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.Endoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti;

namespace AreaRiservataCore.Pages.InserimentoIstanza.Allegati
{
    public class AllegatiMultipliEndo : IAllegatiMultipliUploader
    {
        private readonly IAllegatiEndoprocedimentiService _service;
        private readonly int _idDomanda;

        public AllegatiMultipliEndo(IAllegatiEndoprocedimentiService service, int idDomanda)
        {
            this._service = service;
            this._idDomanda = idDomanda;
        }

        public void AggiungiAllegatoPrincipale(DocumentoDomanda allegatoOriginale, int codiceOggetto)
        {
            this._service.AggiungiAllegatoAEndo(this._idDomanda, allegatoOriginale.Id, codiceOggetto);
        }

        public void AggiungiAllegatoSecondario(DocumentoDomanda allegatoOriginale, int indice, int codiceOggetto)
        {
            var doc = this.GetById(allegatoOriginale.Id);
            var descrizione = allegatoOriginale.Descrizione + " (file " + indice.ToString() + ")";

            this._service.AggiungiAllegatoLibero(this._idDomanda, doc.CodiceEndoOIntervento.Value, descrizione, codiceOggetto);
        }

        public DocumentoDomanda GetById(int idAllegato)
        {
            return this._service.GetById(this._idDomanda, idAllegato);
        }
    }
}
