using Init.SIGePro.Protocollo.ApSystems.Allegati;
using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ApSystems.Protocollazione
{
    public interface IProtocollazione
    {
        DatiProtocolloRes Protocolla(DataSet request, ProtocollazioneServiceWrapper wrapper);
        void InserisciAllegato(string codiceProtocollo, string numeroProtocollo, string dataProtocollo, byte[] oggetto, string nomeFile, string codiceAllegato, AllegatiServiceWrapper allegatiSrv);
        string Flusso { get; }
        void SetMittenti(DatiProtocollo.protocolli.mittenteDataTable dt);
        void SetDestinatari(DatiProtocollo.protocolli.destinatarioDataTable dt);
    }
}
