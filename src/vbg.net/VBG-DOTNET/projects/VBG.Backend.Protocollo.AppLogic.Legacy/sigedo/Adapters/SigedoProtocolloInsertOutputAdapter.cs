using System;
using VBG.Backend.Protocollo.AppLogic.Legacy.Sigedo.Proxies.Protocollazione;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Sigedo.Adapters
{
    public class SigedoProtocolloInsertOutputAdapter
    {
        ProtocollazioneRet _response;

        public SigedoProtocolloInsertOutputAdapter(ProtocollazioneRet response)
        {
            _response = response;
        }

        public DatiProtocolloResponseType Adatta()
        {
            try
            {
                var datiRes = new DatiProtocolloResponseType();

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
