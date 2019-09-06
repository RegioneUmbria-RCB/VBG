using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloDatagraphService;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.Datagraph.LeggiProtocollo
{
    public class LeggiProtocolloResponseAdapter
    {
        ProtocolloLogs _logs;

        public LeggiProtocolloResponseAdapter()
        {
            
        }

        public DatiProtocolloLetto Adatta(Segnatura response)
        {
            var intestazione = response.Intestazione;
            var mittDest = MittentiDestinatariResponseFactory.Create(intestazione.Identificatore.Flusso, intestazione.Mittente, intestazione.Destinatario);

            var allegatiAdapter = new AllegatiAdapter();
            var allegati = allegatiAdapter.Adatta(response.Descrizione);

            string tipoDocumento = "";
            var parametriTipoDocumento = response.ApplicativoProtocollo.Parametro.Where(x => x.nome == "FORMATODOC" && !String.IsNullOrEmpty(x.valore));

            if (parametriTipoDocumento != null && parametriTipoDocumento.Count() == 1)
            {
                tipoDocumento = parametriTipoDocumento.First().valore;
            }

            DateTime data;
            var isDate = DateTime.TryParse(intestazione.Identificatore.DataRegistrazione, out data);
            if (!isDate)
            {
                throw new Exception("DATA PROTOCOLLO FORMATTATA NON CORRETTAMENTE");
            }

            return new DatiProtocolloLetto
            {
                NumeroProtocollo = intestazione.Identificatore.NumeroRegistrazione,
                DataProtocollo = intestazione.Identificatore.DataRegistrazione,
                AnnoProtocollo = data.Year.ToString(),
                Classifica = intestazione.Classifica.CodiceTitolario,
                Classifica_Descrizione = intestazione.Classifica.CodiceTitolario,
                MittentiDestinatari = mittDest.GetMittenteDestinatario(),
                InCaricoA = mittDest.InCaricoA,
                InCaricoA_Descrizione = mittDest.InCaricoADescrizione,
                Origine = mittDest.Flusso,
                Oggetto = intestazione.Oggetto,
                Allegati = allegati.ToArray(),
                TipoDocumento = tipoDocumento,
                TipoDocumento_Descrizione = tipoDocumento
            };
        }
    }
}
