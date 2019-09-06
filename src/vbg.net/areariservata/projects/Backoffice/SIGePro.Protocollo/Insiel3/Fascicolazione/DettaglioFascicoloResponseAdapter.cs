using Init.SIGePro.Protocollo.ProtocolloInsielService3;
using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;

namespace Init.SIGePro.Protocollo.Insiel3.Fascicolazione
{
    public class DettaglioFascicoloResponseAdapter
    {
        public DettaglioFascicoloResponseAdapter()
        {

        }

        public DatiProtocolloFascicolato Adatta(Dettagli response)
        {
            string classifica = "";

            if (!String.IsNullOrEmpty(response.codiceRegistro))
            {
                classifica = Regex.Replace(response.codiceRegistro.Trim(), @"\s+", ".");
            }

            return new DatiProtocolloFascicolato
            {
                AnnoFascicolo = response.anno,
                DataFascicolo = response.data.ToString("dd/MM/yyyy"),
                Fascicolato = EnumFascicolato.si,
                NumeroFascicolo = response.numero,
                Oggetto = response.oggetto,
                NoteFascicolo = response.note,
                Classifica = classifica
            };
        }
    }
}
