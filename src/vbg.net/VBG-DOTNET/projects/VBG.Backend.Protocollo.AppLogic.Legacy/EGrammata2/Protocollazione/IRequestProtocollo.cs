using VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata2.Fascicolazione;
using VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata2.Protocollazione.Segnatura.Request;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata2.Protocollazione
{
    public interface IRequestProtocollo
    {
        DatiTipoProtIN Flusso { get; }
        string DataArrivo { get; }
        UOProv UOProvenienza { get; }
        UoAss UODestinatario { get; }
        Firm[] GetMittentiEsterni();
        EsibDest[] GetDestinatariEsterni();
        Classificazione Titolario { get; }
        IFascicolazione Fascicolo { get; }
        CopieArrIn[] GetCopieArrIn();
        AllegaArrIn[] GetAllegati();
    }
}
