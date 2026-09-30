using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.Commands
{
    public enum OrigineDocumentoSostituzioneDocumentale
    {
        Endoprocedimento,
        Intervento
    }

    public class SostituisciDocumento: Command
    {
        public readonly int IdMovimento;
        public readonly OrigineDocumentoSostituzioneDocumentale OrigineDocumento;
        public readonly int CodiceOggettoOriginale;
        public readonly string NomeFileOriginale;
        public readonly int CodiceOggettoSostitutivo;
        public readonly string NomeFileSostitutivo;

        public SostituisciDocumento(int movimento, OrigineDocumentoSostituzioneDocumentale origine, int codiceOggettoOriginale, string nomeFileOriginale, int codiceOggettoSostitutivo, string nomeFileSostitutivo)
        {
            this.IdMovimento = movimento;
            this.OrigineDocumento = origine;
            this.CodiceOggettoOriginale = codiceOggettoOriginale;
            this.NomeFileOriginale = nomeFileOriginale;
            this.CodiceOggettoSostitutivo = codiceOggettoSostitutivo;
            this.NomeFileSostitutivo = nomeFileSostitutivo;
        }
    }
}
