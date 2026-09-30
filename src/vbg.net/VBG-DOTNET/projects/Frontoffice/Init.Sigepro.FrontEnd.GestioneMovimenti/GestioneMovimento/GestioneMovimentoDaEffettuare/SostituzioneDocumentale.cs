using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare
{
    public enum TipoSostituzioneDocumentaleEnum
    {
        Endo,
        Intervento
    }

    public class SostituzioneDocumentale
    {
        public TipoSostituzioneDocumentaleEnum TipoDocumento { get; set; }
        public int CodiceOggettoOrigine { get; set; }
        public string NomeFileOrigine { get; set; }

        public int CodiceOggettoSostitutivo { get; set; }
        public string NomeFileSostitutivo { get; set; }
    }
}
