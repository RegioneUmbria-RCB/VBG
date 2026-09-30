using Init.SIGePro.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Interfaces
{
    internal interface IAllegatiService
    {
        void AggiungiAllegati(string idProtocollo, string numeroProtocollo, DateTime? dataProtocollo, IEnumerable<ProtocolloAllegati> allegati);
        AllegatoResponseType LeggiAllegato();
        AllegatoResponseType LeggiAllegatoDaLeggiProtocollo();
        DatiProtocolloResponseType MettiAllaFirma(DatiProtocolloIn pProt);
    }
}
