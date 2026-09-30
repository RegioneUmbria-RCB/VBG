using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.Delta.Adapters
{
    public class DeltaProtocolloOutputAdapter
    {
        public readonly DatiProtocolloRes DatiProtocollo;
        public readonly ProtocolloDeltaService.Protocollo _response;

        public DeltaProtocolloOutputAdapter(ProtocolloDeltaService.Protocollo response)
        {
            _response = response;
            DatiProtocollo = CreaDatiProtocollo();
        }

        private DatiProtocolloRes CreaDatiProtocollo()
        {
            try
            {
                var datiRes = new DatiProtocolloRes();

                datiRes.NumeroProtocollo = _response.Progressivo.ToString();
                datiRes.DataProtocollo = _response.DataProtocollo.ToString("dd/MM/yyyy");
                datiRes.AnnoProtocollo = _response.Anno.ToString();

                return datiRes;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA MAPPATURA DEI DATI DI RISPOSTA DEL WEB SERVICE DI PROTOCOLLO SULL'ADATTATORE, LA PROTOCOLLAZIONE E' ANDATA COMUNQUE A BUON FINE", ex);
            }

        }
    }
}
