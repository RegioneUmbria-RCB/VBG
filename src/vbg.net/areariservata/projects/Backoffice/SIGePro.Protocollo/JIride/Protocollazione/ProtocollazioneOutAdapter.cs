using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.JIride.Protocollazione
{
    public class ProtocollazioneOutAdapter
    {
        bool _modificaNumero;
        bool _aggiungiAnno;
        ProtocolloLogs _logs;
        string _messaggioProtoOk;

        public ProtocollazioneOutAdapter(bool modificaNumero, bool aggiungiAnno, ProtocolloLogs logs, string messaggioProtoOk)
        {
            this._modificaNumero = modificaNumero;
            this._aggiungiAnno = aggiungiAnno;
            this._logs = logs;
            this._messaggioProtoOk = messaggioProtoOk;
        }

        public DatiProtocolloRes Adatta(DocumentoOutXml response)
        {
            var protoRes = new DatiProtocolloRes
            {
                IdProtocollo = $"{response.IdDocumento.ToString()}-COPIA",
                AnnoProtocollo = response.AnnoProtocollo == 0 ? "" : response.AnnoProtocollo.ToString(),
                DataProtocollo = response.DataProtocollo.HasValue ? response.DataProtocollo.Value.ToString("dd/MM/yyyy") : "",
                NumeroProtocollo = response.NumeroProtocollo == 0 ? "" : response.NumeroProtocollo.ToString()
            };

            if (_modificaNumero)
            {
                protoRes.NumeroProtocollo = protoRes.NumeroProtocollo.TrimStart(new char[] { '0' });
            }

            if (_aggiungiAnno)
            {
                protoRes.NumeroProtocollo += "/" + response.AnnoProtocollo.ToString();
            }

            if (!String.IsNullOrEmpty(response.Messaggio))
            {
                _logs.Warn(response.Messaggio);
            }

            protoRes.Warning = _logs.Warnings.WarningMessage;

            if (!String.IsNullOrEmpty(_messaggioProtoOk) && !String.IsNullOrEmpty(_logs.Warnings.WarningMessage))
            {
                protoRes.Warning = _logs.Warnings.WarningMessage.Replace(_messaggioProtoOk, "");
            }

            _logs.InfoFormat("Dati protocollo restituiti, numero: {0}, anno: {1}, data: {2}", protoRes.NumeroProtocollo, protoRes.AnnoProtocollo, protoRes.DataProtocollo);

            return protoRes;
        }

        public DatiProtocolloRes Adatta(ProtocolloOutXml response)
        {
            var protoRes = new DatiProtocolloRes
            {
                IdProtocollo = response.IdDocumento.ToString(),
                AnnoProtocollo = response.AnnoProtocollo == 0 ? "" : response.AnnoProtocollo.ToString(),
                DataProtocollo = response.DataProtocollo.HasValue ? response.DataProtocollo.Value.ToString("dd/MM/yyyy") : "",
                NumeroProtocollo = response.NumeroProtocollo == 0 ? "" : response.NumeroProtocollo.ToString()
            };

            if (_modificaNumero)
            {
                protoRes.NumeroProtocollo = protoRes.NumeroProtocollo.TrimStart(new char[] { '0' });
            }

            if (_aggiungiAnno && !String.IsNullOrEmpty(protoRes.NumeroProtocollo) && !String.IsNullOrEmpty(protoRes.AnnoProtocollo))
            {
                protoRes.NumeroProtocollo += "/" + response.AnnoProtocollo.ToString();
            }

            if (!String.IsNullOrEmpty(response.Errore))
            { 
                _logs.Warn(response.Errore);
            }

            protoRes.Warning = _logs.Warnings.WarningMessage;

            if (!String.IsNullOrEmpty(_messaggioProtoOk) && !String.IsNullOrEmpty(_logs.Warnings.WarningMessage))
            {
                protoRes.Warning = _logs.Warnings.WarningMessage.Replace(_messaggioProtoOk, "");
            }

            _logs.InfoFormat("Dati protocollo restituiti, numero: {0}, anno: {1}, data: {2}", protoRes.NumeroProtocollo, protoRes.AnnoProtocollo, protoRes.DataProtocollo);

            return protoRes;
        }

    }
}
