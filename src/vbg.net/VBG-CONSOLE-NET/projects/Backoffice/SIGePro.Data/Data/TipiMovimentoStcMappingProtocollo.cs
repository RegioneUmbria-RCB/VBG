using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Data
{
    public class TipiMovimentoStcMappingProtocollo
    {
        public string Flusso{ get; private set; }
        public string TipoDocumento { get; private set; }
        public int? OggettoMailTipo { get; private set; }
        public int? AmministrazioneMittente { get; private set; }

        public TipiMovimentoStcMappingProtocollo(string flusso, string tipoDocumento, int? oggettoMailTipo, int? amministrazioneMittente)
        {
            this.Flusso = flusso;
            this.TipoDocumento = tipoDocumento;
            this.OggettoMailTipo = oggettoMailTipo;
            this.AmministrazioneMittente = amministrazioneMittente;
        }
    }
}
