using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.EGrammata2.Protocollazione.Segnatura.Request;
using Init.SIGePro.Protocollo.EGrammata2.Fascicolazione;

namespace Init.SIGePro.Protocollo.EGrammata2.Protocollazione
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
