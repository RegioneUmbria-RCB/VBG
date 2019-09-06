using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneMovimentiFrontoffice
{
    public enum TipoSostituzioneDocumentaleEnum
    {
        NessunaSostituzione = 0,
        DocumentiNonValidi = 1,
        DocumentiNonValidiENonVerificati = 2
    }

    public class FlagsMovimento
    {
        public TipoSostituzioneDocumentaleEnum TipoSostituzioneDocumentale;
        public bool RichiedeFirmaDigitale;
    }
}
