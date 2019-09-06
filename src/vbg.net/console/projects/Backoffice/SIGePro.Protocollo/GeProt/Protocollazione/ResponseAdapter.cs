using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.GeProt.Protocollazione
{
    public class ResponseAdapter
    {
        string[] _response;

        public ResponseAdapter(string[] response)
        {
            _response = response;
        }

        public DatiProtocolloRes Adatta(ProtocolloLogs logs)
        {
            var returnValue = new DatiProtocolloRes();

            if (!String.IsNullOrEmpty(_response[4]))
            {
                string data = _response[4];
                returnValue.DataProtocollo = data; //Come viene formattata la stringa da GeProt
                returnValue.AnnoProtocollo = returnValue.DataProtocollo.Split(new Char[] { '/' })[2]; //Come viene formattata la stringa da GeProt
            }

            if (!String.IsNullOrEmpty(_response[3]))
            {
                returnValue.NumeroProtocollo = Convert.ToInt32(_response[3]).ToString();

                /*if (modificaNumero)
                    returnValue.NumeroProtocollo = returnValue.NumeroProtocollo.TrimStart(new char[] { '0' });

                if (modificaNumero)
                    returnValue.NumeroProtocollo += "/" + returnValue.AnnoProtocollo;*/
            }

            if (logs.Warnings != null && !String.IsNullOrEmpty(logs.Warnings.WarningMessage))
                returnValue.Warning = logs.Warnings.WarningMessage;

            return returnValue;
        }
    }
}
