using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.ProtocolloFoliumService;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.Folium.Protocollazione
{
    public class FoliumProtocollazioneOutputAdapter
    {
        public readonly DatiProtocolloRes DatiProtocolloResponse;
        ProtocolloLogs _logs;

        public FoliumProtocollazioneOutputAdapter(DocumentoProtocollato response, ProtocolloLogs logs)
        {
            _logs = logs;
            DatiProtocolloResponse = CreaDatiProtocollo(response);
        }

        private DatiProtocolloRes CreaDatiProtocollo(DocumentoProtocollato response)
        {
            try
            {
                var datiRes = new DatiProtocolloRes();
                datiRes.AnnoProtocollo = response.dataProtocollo.Value.ToString("yyyy");
                datiRes.DataProtocollo = response.dataProtocollo.Value.ToString("dd/MM/yyyy");
                datiRes.IdProtocollo = response.id.HasValue ? response.id.Value.ToString() : "";
                datiRes.NumeroProtocollo = response.numeroProtocollo;
                datiRes.Warning = _logs.Warnings.WarningMessage;

                return datiRes;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA MAPPATURA DEI DATI DI RISPOSTA RICEVUTI DAL WEB SERVICE DOPO LA PROTOCOLLAZIONE, IL PROTOCOLLO E' STATO INSERITO NEL SISTEMA DI PROTOCOLLO, MA LA NUMERAZIONE NON E' STATA SALVATA SUL BACKOFFICE", ex);
            }
        }
    }
}
