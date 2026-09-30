using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Kibernetes.V1
{
    public class LeggiProtocolloV1Service : ILeggiProtocolloService
    {

        public DatiProtocolloLettoResponseType LeggiProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo, DateTime? dataProtocollo)
        {
            return new DatiProtocolloLettoResponseType
            {
                AnnoProtocollo = annoProtocollo,
                NumeroProtocollo = numeroProtocollo,
                DataProtocollo = dataProtocollo.HasValue ? dataProtocollo.Value.ToString("dd/MM/yyyy") : ""
            };
        }
    }
}
