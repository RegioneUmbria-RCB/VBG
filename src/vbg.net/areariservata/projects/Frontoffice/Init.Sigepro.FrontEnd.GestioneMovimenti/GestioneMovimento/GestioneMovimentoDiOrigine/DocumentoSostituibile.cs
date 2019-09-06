using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine
{
    public enum OrigineDocumentoSostituibileEnum
    {
        Intervento,
        Endoprocedimento
    }

    public class DocumentoSostituibile
    {
        public int? CodiceOggetto { get; private set; }
        public string Descrizione { get; private set; }
        public int IdDocumento { get; private set; }
        public string NomeFile { get; private set; }
        public OrigineDocumentoSostituibileEnum Origine { get; private set; }

        public DocumentoSostituibile(OrigineDocumentoSostituibileEnum origine, int idDocumento, string descrizione, int? codiceOggetto, string nomeFile)
        {
            this.CodiceOggetto = codiceOggetto;
            this.Descrizione = descrizione;
            this.IdDocumento = idDocumento;
            this.NomeFile = nomeFile;
            this.Origine = origine;
        }

        
    }
}
