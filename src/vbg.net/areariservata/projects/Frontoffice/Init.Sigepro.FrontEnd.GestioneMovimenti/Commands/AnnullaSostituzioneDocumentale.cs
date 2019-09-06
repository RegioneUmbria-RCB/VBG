using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.Commands
{
    public class AnnullaSostituzioneDocumentale : Command
    {
        public readonly int IdMovimento;
        public readonly int CodiceOggettoSostitutivo;

        public AnnullaSostituzioneDocumentale(int idMovimento, int codiceOggettoSostitutivo)
        {
            this.IdMovimento = idMovimento;
            this.CodiceOggettoSostitutivo = codiceOggettoSostitutivo;
        }
    }
}
