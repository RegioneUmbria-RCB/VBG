using Init.SIGePro.Manager;
using PersonalLib2.Data;
using ProtocolloInsielMercatoService2;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.InsielMercato2.LeggiProtocollo
{
    public class AllegatiResponseAdapter
    {
        public static AllegatoResponseType[] Adatta(protocolDetail response, DataBase db)
        {
            return response.documentList.Select(x => new AllegatoResponseType
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
