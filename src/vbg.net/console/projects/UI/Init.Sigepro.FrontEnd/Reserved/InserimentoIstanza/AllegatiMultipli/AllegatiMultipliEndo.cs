using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.AllegatiMultipli
{
    public class AllegatiMultipliEndo : IAllegatiMultipliUploader
    {
        AllegatiEndoprocedimentiService _service;
        int _idDomanda;

        public AllegatiMultipliEndo(AllegatiEndoprocedimentiService service, int idDomanda)
        {
            this._service = service;
            this._idDomanda = idDomanda;
        }

        public void AggiungiAllegatoPrincipale(DocumentoDomanda allegatoOriginale, AppLogic.GestioneOggetti.BinaryFile file)
        {
            this._service.AggiungiAllegatoAEndo(this._idDomanda, allegatoOriginale.Id, file);
        }

        public void AggiungiAllegatoSecondario(DocumentoDomanda allegatoOriginale,int indice, AppLogic.GestioneOggetti.BinaryFile file)
        {
            var doc = GetById(allegatoOriginale.Id);
            var descrizione = allegatoOriginale.Descrizione + " (file " + indice.ToString() + ")";

            this._service.AggiungiAllegatoLibero(this._idDomanda, doc.CodiceEndoOIntervento.Value, descrizione, file, false);
        }

        public DocumentoDomanda GetById(int idAllegato)
        {
            return this._service.GetById(this._idDomanda, idAllegato);
        }
    }
}