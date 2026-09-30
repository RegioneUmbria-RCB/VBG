using VBG.Backend.Protocollo.AppLogic.Core.ApSystems.Allegati;
using System.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.ApSystems.Protocollazione
{
    public interface IProtocollazioneApSystems
    {
        DatiProtocolloResponseType Protocolla(DataSet request, ProtocollazioneServiceWrapper wrapper);
        void InserisciAllegato(string codiceProtocollo, string numeroProtocollo, string dataProtocollo, byte[] oggetto, string nomeFile, string codiceAllegato, AllegatiServiceWrapper allegatiSrv);
        string Flusso { get; }
        void SetMittenti(VBG.Backend.Protocollo.AppLogic.Core.ApSystems.Protocollazione.DatiProtocollo.protocolli.mittenteDataTable dt);
        void SetDestinatari(VBG.Backend.Protocollo.AppLogic.Core.ApSystems.Protocollazione.DatiProtocollo.protocolli.destinatarioDataTable dt);
    }
}
