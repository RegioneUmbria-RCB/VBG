using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ItCity.LeggiProtocollo
{
    public class LeggiProtocolloRequestInfo
    {
        public readonly int Numero;
        public readonly int Anno;
        public readonly string Sigla;

        public LeggiProtocolloRequestInfo(string numeroProtocollo, string annoProtocollo, string sigla)
        {
            this.Sigla = sigla;

            int numero;
            var isParsableNumero = Int32.TryParse(numeroProtocollo, out numero);
            if (!isParsableNumero)
            {
                throw new Exception($"IL NUMERO DI PROTOCOLLO {numeroProtocollo}, NON HA UN FORMATO VALIDO, NON E' UN NUMERO");
            }

            this.Numero = numero;

            int anno;
            var isParsableAnno = Int32.TryParse(annoProtocollo, out anno);
            if (!isParsableAnno)
            {
                throw new Exception($"L'ANNO DI PROTOCOLLO {annoProtocollo} NON HA UN FORMATO VALIDO, NON E' UN NUMERO");
            }

            this.Anno = anno;
        }
    }
}
