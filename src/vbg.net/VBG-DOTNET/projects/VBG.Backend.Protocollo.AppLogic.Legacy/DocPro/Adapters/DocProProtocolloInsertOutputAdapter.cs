using System;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocPro.Adapters
{
    public class DocProProtocolloInsertOutputAdapter
    {
        _ProtocollazioneResponse _response;
        public readonly DatiProtocolloResponseType DatiProtocollo;

        public DocProProtocolloInsertOutputAdapter(_ProtocollazioneResponse response)
        {
            _response = response;
            DatiProtocollo = CreaDatiProtocollo();
        }

        private DatiProtocolloResponseType CreaDatiProtocollo()
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
