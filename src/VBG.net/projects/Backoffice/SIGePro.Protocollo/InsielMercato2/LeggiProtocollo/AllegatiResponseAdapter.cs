using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.ProtocolloInsielMercatoService2;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using System.IO;

namespace Init.SIGePro.Protocollo.InsielMercato2.LeggiProtocollo
{
    public class AllegatiResponseAdapter
    {
        public static AllOut[] Adatta(protocolDetail response, DataBase db)
        {
            return response.documentList.Select(x => new AllOut
            {
                IDBase = x.name,
                Serial = x.name,
                Commento = Path.GetFileNameWithoutExtension(x.name),
                ContentType = new OggettiMgr(db).GetContentType(x.name),
                TipoFile = Path.GetExtension(x.name).Replace(".", "")
            }).ToArray();
        }
    }
}
