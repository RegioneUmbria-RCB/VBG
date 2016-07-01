using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.DocEr.ProtocollazioneRegistrazione.Protocollazione
{
    public class ProtocollazioneResponseAdapter
    {
        esito _response;
        ProtocolloLogs _logs;

        public ProtocollazioneResponseAdapter(esito response, ProtocolloLogs logs)
        {
            _response = response;
            _logs = logs;
        }

        public DatiProtocolloRes Adatta(long idUnitaDocumentale)
        {
            try
            {
                var protoRes = new DatiProtocolloRes();

                protoRes.IdProtocollo = idUnitaDocumentale.ToString();

                protoRes.NumeroProtocollo = _response.dati_protocollo[0].NUM_PG;
                
                var outputData = new DateTime();

                bool parse = DateTime.TryParse(_response.dati_protocollo[0].DATA_PG, out outputData);
                if (!parse)
                    throw new System.Exception(String.Format("ERRORE GENERATO DURANTE LA CONVERSIONE DEI DATI DELLA DATA, valore data: {0}, numero protocollo: {1}", _response.dati_protocollo[0].DATA_PG, _response.dati_protocollo[0].NUM_PG));

                protoRes.DataProtocollo = outputData.ToString("dd/MM/yyyy");
                protoRes.AnnoProtocollo = _response.dati_protocollo[0].ANNO_PG;

                _logs.InfoFormat("Dati protocollo restituiti, numero: {0}, anno: {1}, data: {2}, id unità documentale: {3}", protoRes.NumeroProtocollo, protoRes.AnnoProtocollo, protoRes.DataProtocollo, idUnitaDocumentale.ToString());

                protoRes.Warning = _logs.Warnings.WarningMessage;

                return protoRes;
            }
            catch (Exception ex)
            {
                throw new System.Exception(String.Format("ERRORE GENERATO DURANTE LA MAPPATURA DEI DATI RESTITUITI DAL WEB SERVICE, IL PROTOCOLLO POTREBBE ESSERE STATO INSERITO COMUNQUE CORRETTAMENTE, ERRORE: {0}", ex.Message));
            }
        }
    }
}
