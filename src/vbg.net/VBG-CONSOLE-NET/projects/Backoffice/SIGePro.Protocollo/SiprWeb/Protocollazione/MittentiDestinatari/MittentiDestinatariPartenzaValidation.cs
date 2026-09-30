using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.SiprWeb.Protocollazione.MittentiDestinatari
{
    public class MittentiDestinatariPartenzaValidation : BaseMittentiDestinatari
    {
        ProtocolloLogs _logs;

        public MittentiDestinatariPartenzaValidation(IDatiProtocollo datiProto, ProtocolloLogs logs) : base(datiProto)
        {
            _logs = logs;
        }

        public void ValidaDestinatari()
        {
            try
            {
                if (DatiProto.AnagraficheProtocollo.Count + DatiProto.AmministrazioniProtocollo.Count == 0)
                    throw new Exception("NON SONO PRESENTI DESTINATARI");

                var lista = DatiProto.AmministrazioniInterne.Select(x => String.Concat(x.PROT_UO, " ", x.AMMINISTRAZIONE)).ToArray();

                if (lista.Length > 0)
                    _logs.WarnFormat("NEI DESTINATARI SONO PRESENTI DELLE AMMINISTRAZIONI INTERNE: {0}", String.Join(", ", lista));
            }
            catch (Exception ex)
            {
                throw new Exception("VALIDAZIONE DEI DATI RIGUARDANTI I DESTINATARI NON SUPERATA", ex);
            }
        }
    }
}
