
using Init.SIGePro.Manager.Authentication;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Interfaces
{
    internal interface IAccettazioneProtocolliService
    {
        EseguiAccettazioneResponseType EseguiAccettazione(AuthenticationInfo auth, string idProtocollo, string annoProtocollo, string numeroProtocollo);
        DatiProtocolloEsitatoResponseType IsEsitato(AuthenticationInfo auth, string annoProtocollo, string numeroProtocollo);
        DatiProtocolloEsitatoResponseType IsEsitato(AuthenticationInfo auth, string idProtocollo);
    }
}
