using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.ProtocolloSidUmbriaService;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;

namespace Init.SIGePro.Protocollo.SidUmbria.Protocollazione
{
    public class ProtocollazioneFlussoPartenza : IProtocollazioneFlusso
    {
        IDatiProtocollo _datiProto;
        string _destinatarioCC;

        public ProtocollazioneFlussoPartenza(IDatiProtocollo datiProto, string destinatarioCC)
        {
            _datiProto = datiProto;
            _destinatarioCC = destinatarioCC;
        }

        public string Flusso
        {
            get { return ProtocolloConstants.COD_PARTENZA_DOCAREA; }
        }

        public corrispondente[] GetCorrispondenti()
        {
            var anagrafiche = _datiProto.AnagraficheProtocollo.Select(x => x.ToCorrispondenteAnagraficaPartenza(_destinatarioCC));
            var amministrazioni = _datiProto.AmministrazioniProtocollo.Select(x => x.ToCorrispondenteAmministrazionePartenza(_destinatarioCC));

            var corrispondente = anagrafiche.Union(amministrazioni);

            return corrispondente.ToArray();

        }
    }
}
