using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.ProtocolloSidUmbriaService;

namespace Init.SIGePro.Protocollo.SidUmbria.Protocollazione
{
    public class ProtocollazioneFlussoArrivo : IProtocollazioneFlusso
    {
        IDatiProtocollo _datiProto;

        public ProtocollazioneFlussoArrivo(IDatiProtocollo datiProto)
        {
            _datiProto = datiProto;
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_ARRIVO_DOCAREA; }
        }

        public corrispondente[] GetCorrispondenti()
        {
            if (_datiProto.AnagraficheProtocollo.Count > 0)
                return new corrispondente[] { _datiProto.AnagraficheProtocollo.First().ToCorrispondenteAnagraficaArrivo() };
            else if (_datiProto.AmministrazioniProtocollo.Count > 0)
                return new corrispondente[] { _datiProto.AmministrazioniEsterne.First().ToCorrispondenteAmministrazioneArrivo() };
            else
                throw new Exception("NON SONO PRESENTI MITTENTI O MITTENTI NON VALIDI");
        }
    }
}
