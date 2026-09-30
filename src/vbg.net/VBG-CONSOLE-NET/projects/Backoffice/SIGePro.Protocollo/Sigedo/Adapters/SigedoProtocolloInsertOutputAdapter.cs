using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Sigedo.Proxies;
using Init.SIGePro.Protocollo.Sigedo.Proxies.Protocollazione;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.Sigedo.Adapters
{
    public class SigedoProtocolloInsertOutputAdapter
    {
        ProtocollazioneRet _response;

        public SigedoProtocolloInsertOutputAdapter(ProtocollazioneRet response)
        {
            _response = response;
        }

        public DatiProtocolloRes Adatta()
        {
            try
            {
                var datiRes = new DatiProtocolloRes();

                datiRes.NumeroProtocollo = _response.lngNumPG.ToString();
                datiRes.DataProtocollo = _response.strDataPG;
                datiRes.AnnoProtocollo = _response.lngAnnoPG.ToString();

                return datiRes;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA MAPPATURA DEI DATI DI RISPOSTA DEL WEB SERVICE DI PROTOCOLLO SULL'ADATTATORE, LA PROTOCOLLAZIONE E' PROBABILMENTE ANDATA COMUNQUE A BUON FINE", ex);
            }
        }
    }
}
