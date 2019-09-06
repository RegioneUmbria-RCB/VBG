using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.VerificaSoggettiFirmatari.Errori
{
    public class DocumentoNonPresente
    {
        public readonly string NomeDocumento;

        internal DocumentoNonPresente(string nomeDocumento)
        {
            this.NomeDocumento = nomeDocumento;
        }
    }
}
