using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.AllegatiMultipli
{
    public class AllegatiMultipliIntervento : IAllegatiMultipliUploader
    {
        AllegatiInterventoService _service;
        int _idDomanda;

        public AllegatiMultipliIntervento(AllegatiInterventoService service, int idDomanda)
        {
            this._service = service;
            this._idDomanda = idDomanda;
        }

        public void AggiungiAllegatoPrincipale(DocumentoDomanda allegatoOriginale, AppLogic.GestioneOggetti.BinaryFile file)
        {
            this._service.Salva(this._idDomanda, allegatoOriginale.Id, file);
        }

        public void AggiungiAllegatoSecondario(DocumentoDomanda allegatoOriginale, int indice, AppLogic.GestioneOggetti.BinaryFile file)
        {
            var descrizione = allegatoOriginale.Descrizione + " (file " + indice.ToString() + ")";

            this._service.AggiungiAllegatoLibero(this._idDomanda, descrizione, file, allegatoOriginale.Categoria.Codice, allegatoOriginale.Categoria.Descrizione, false);
        }

        public DocumentoDomanda GetById(int idAllegato)
        {
            return this._service.GetById(this._idDomanda, idAllegato);
        }
    }
}