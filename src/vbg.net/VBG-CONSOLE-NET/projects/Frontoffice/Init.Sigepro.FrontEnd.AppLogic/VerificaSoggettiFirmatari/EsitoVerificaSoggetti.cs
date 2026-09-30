using Init.Sigepro.FrontEnd.AppLogic.VerificaSoggettiFirmatari.Errori;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.VerificaSoggettiFirmatari
{
    public class EsitoVerificaSoggetti
    {
        List<SoggettiNonTrovati> _soggettiNonTrovati = new List<SoggettiNonTrovati>();
        List<DocumentoNonPresente> _documentiNonPresenti = new List<DocumentoNonPresente>();
        List<VerificaFallita> _verificheFallite = new List<VerificaFallita>();

        internal void AggiungiErroreSoggettiNonTrovati(string nomeDocumento, IEnumerable<TipoSoggettoFirmatario> firmatariRichiesti)
        {
            this._soggettiNonTrovati.Add(new SoggettiNonTrovati(nomeDocumento, firmatariRichiesti.Select(x => x.Descrizione)));
        }

        internal void AggiungiErroreDocumentoNonPresente(string nomeDocumento)
        {
            this._documentiNonPresenti.Add(new DocumentoNonPresente(nomeDocumento));
        }

        internal void AggiungiErroreVerificaFallita(string nomeDocumento, string nomeFile, IEnumerable<SoggettoFirmatario> firmatariRichiestiPresenti)
        {
            this._verificheFallite.Add(new VerificaFallita(nomeDocumento, nomeFile, firmatariRichiestiPresenti));
        }

        public IEnumerable<SoggettiNonTrovati> SoggettiNonTrovati
        {
            get { return this._soggettiNonTrovati; }
        }

        public IEnumerable<DocumentoNonPresente> DocumentiNonPresenti
        {
            get { return this._documentiNonPresenti; }
        }

        public IEnumerable<VerificaFallita> VerificheFallite
        {
            get { return this._verificheFallite; }
        }

        public bool VerificaRiuscita
        {
            get
            {
                return this._soggettiNonTrovati.Count == 0 &&
                       this._documentiNonPresenti.Count == 0 &&
                       this._verificheFallite.Count == 0;
            }
        }
    }
}
