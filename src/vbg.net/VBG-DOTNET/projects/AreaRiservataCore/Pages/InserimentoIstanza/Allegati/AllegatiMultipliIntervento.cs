using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.Intervento;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti;

namespace AreaRiservataCore.Pages.InserimentoIstanza.Allegati
{
    public class AllegatiMultipliIntervento : IAllegatiMultipliUploader
    {
        private readonly AllegatiInterventoService _service;
        private readonly int _idDomanda;

        public AllegatiMultipliIntervento(AllegatiInterventoService service, int idDomanda)
        {
            this._service = service;
            this._idDomanda = idDomanda;
        }

        public void AggiungiAllegatoPrincipale(DocumentoDomanda allegatoOriginale, int codiceOggetto)
        {
            this._service.Salva(this._idDomanda, allegatoOriginale.Id, codiceOggetto);
        }

        public void AggiungiAllegatoSecondario(DocumentoDomanda allegatoOriginale, int indice, int codiceOggetto)
        {
            var descrizione = allegatoOriginale.Descrizione + " (file " + indice.ToString() + ")";

            this._service.AggiungiAllegatoLibero(this._idDomanda, descrizione, codiceOggetto, allegatoOriginale.Categoria.Codice, allegatoOriginale.Categoria.Descrizione);
        }

        public DocumentoDomanda GetById(int idAllegato)
        {
            return this._service.GetById(this._idDomanda, idAllegato);
        }
    }
}
