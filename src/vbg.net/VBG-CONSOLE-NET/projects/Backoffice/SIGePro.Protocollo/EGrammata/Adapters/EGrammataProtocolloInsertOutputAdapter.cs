using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.EGrammata.Segnatura.ProtoOutput;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.EGrammata.Adapters
{
    public class EGrammataProtocolloInsertOutputAdapter
    {
        Output_NuovaUD _response;
        ProtocolloLogs _logs;
        public readonly DatiProtocolloRes DatiProtocollo;


        public EGrammataProtocolloInsertOutputAdapter(Output_NuovaUD response, ProtocolloLogs logs)
        {
            _response = response;
            _logs = logs;
            DatiProtocollo = CreaDatiProtocollo();    
        }

        private DatiProtocolloRes CreaDatiProtocollo()
        {
            try
            {
                var datiRes = new DatiProtocolloRes();

                if (_response == null)
                    throw new Exception("E' STATA RESTITUITA UNA RISPOSTA NULL DAL WEB SERVICE DI PROTOCOLLAZIONE");

                if (_response.EstremiRegistrazione == null || _response.EstremiRegistrazione.Length == 0)
                    throw new Exception("LA RISPOSTA DEL WEB SERVICE PRESENTA GLI ESTREMI REGISTRAZIONE NON VALORIZZATI O NULL");

                if (_response.DataEOraRegistrazione == null)
                    throw new Exception("LA RISPOSTA DEL WEB SERVICE PRESENTA LA DATA DI REGISTRAZIONE NON VALORIZZATA O NULL");

                _logs.InfoFormat("Protocollo restituito numero: {0}, data: {1}, anno: {2}, id: {3}", _response.EstremiRegistrazione[0].Numero, _response.DataEOraRegistrazione.ToString("dd/MM/yyyy"), _response.EstremiRegistrazione[0].Anno, _response.IdUD);

                datiRes.IdProtocollo = _response.IdUD;
                datiRes.AnnoProtocollo = _response.EstremiRegistrazione[0].Anno;
                datiRes.DataProtocollo = _response.DataEOraRegistrazione.ToString("dd/MM/yyyy");
                datiRes.NumeroProtocollo = _response.EstremiRegistrazione[0].Numero;

                return datiRes;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA MAPPATURA DEI DATI RESTITUITI DAL WEB SERVICE, IL PROTOCOLLO POTREBBE ESSERE STATO INSERITO CORRETTAMENTE MA POTREBBE NON ESSERE PRESENTE SUL BACKOFFICE, IN QUESTO CASO AGGIORNARE L'ISTANZA CON I DATI DEL PROTOCOLLO MANUALMENTE", ex);
            }
        }
    }
}
