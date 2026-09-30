using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;

namespace VBG.Backend.Protocollo.AppLogic.Core.SidUmbria.Protocollazione
{
    public class ResponseAdapter
    {
        public static DatiProtocolloResponseType Adatta(identificatore identificatore, string idProtocollo, ProtocolloLogs logs)
        {
            if (identificatore == null)
            {
                logs.Warn("IL SISTEMA DI PROTOCOLLO NON HA RESTITUITO GLI ESTREMI, LA RICHIESTA E' STATA MESSA IN CODA");

                return new DatiProtocolloResponseType
                {
                    AnnoProtocollo = "",
                    DataProtocollo = "",
                    NumeroProtocollo = ProtocolloSidUmbriaConstants.SeparatoreCodaNumeroProtocollo,
                    IdProtocollo = idProtocollo,
                    Warning = logs.Warnings.WarningMessage
                };
            }

            var data = DateTime.Parse(identificatore.data);

            var res = new DatiProtocolloResponseType
            {
                AnnoProtocollo = identificatore.anno.ToString(),
                DataProtocollo = data.ToString("dd/MM/yyyy"),
                NumeroProtocollo = identificatore.numero.ToString(),
                IdProtocollo = idProtocollo,
                Warning = logs.Warnings.WarningMessage
            };

            return res;
        }
    }
}
