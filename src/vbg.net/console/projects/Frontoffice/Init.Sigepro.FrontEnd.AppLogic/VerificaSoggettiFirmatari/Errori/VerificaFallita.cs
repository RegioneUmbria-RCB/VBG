using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.VerificaSoggettiFirmatari.Errori
{
    public class VerificaFallita
    {
        public readonly string NomeFile;
        public readonly string NomeDocumento;
        public readonly IEnumerable<SoggettoFirmatario> FirmatariRichiestiPresenti;

        internal VerificaFallita(string nomeDocumento, string nomeFile, IEnumerable<SoggettoFirmatario> firmatariRichiestiPresenti)
        {
            this.NomeFile = nomeFile;
            this.NomeDocumento = nomeDocumento;
            this.FirmatariRichiestiPresenti = firmatariRichiestiPresenti;
        }
    }
}
