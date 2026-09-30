using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Insiel3.Services;

namespace Init.SIGePro.Protocollo.Insiel3.Protocollazione.MittentiDestinatari
{
    public class ProtocollazioneFactory
    {
        public static IProtocollazione Create(string flusso, IDatiProtocollo datiProto, ProtocolloService srv, TipoGestioneAnagraficaEnum.TipoGestione tipoGestioneAnagrafe)
        {
            IProtocollazione rVal = null;
            
            if (flusso == ProtocolloConstants.COD_ARRIVO)
                rVal = new ProtocollazioneArrivo(datiProto, srv, tipoGestioneAnagrafe);
            else if (flusso == ProtocolloConstants.COD_PARTENZA)
                rVal = new ProtocollazionePartenza(datiProto, srv, tipoGestioneAnagrafe);
            else
                throw new Exception(String.Format("FLUSSO {0} NON SUPPORTATO", flusso));

            return rVal;
        }
    }
}
