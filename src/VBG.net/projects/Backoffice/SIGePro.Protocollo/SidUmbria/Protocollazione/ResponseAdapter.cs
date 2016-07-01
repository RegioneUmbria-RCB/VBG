using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.ProtocolloSidUmbriaService;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.SidUmbria.Protocollazione
{
    public class ResponseAdapter
    {
        public static DatiProtocolloRes Adatta(identificatore identificatore, string idProtocollo, ProtocolloLogs logs)
        {
            if (identificatore == null)
            {
                logs.Warn("IL SISTEMA DI PROTOCOLLO NON HA RESTITUITO GLI ESTREMI DI PROTOCOLLO, I DATI SARANNO RESTITUITI IN UN SECONDO MOMENTO");

                return new DatiProtocolloRes
                {
                    AnnoProtocollo = identificatore.anno.ToString(),
                    DataProtocollo = DateTime.Now.ToString("dd/MM/yyyy"),
                    NumeroProtocollo = " - ",
                    IdProtocollo = idProtocollo,
                    Warning = logs.Warnings.WarningMessage
                };
            }

            var data = DateTime.Parse(identificatore.data);

            var res = new DatiProtocolloRes
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
