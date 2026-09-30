using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloServices;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Manager;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.Halley.ImpresaInUnGiorno.Suap
{
    public class AdempimentoSuapAdapter
    {
        ResolveDatiProtocollazioneService _datiProtocollazioneService;
        ProtocolloLogs _logs;
        List<ProtocolloAllegati> _allegati;

        public AdempimentoSuapAdapter(ResolveDatiProtocollazioneService datiProtocollazioneService, ProtocolloLogs logs, List<ProtocolloAllegati> allegati)
        {
            _datiProtocollazioneService = datiProtocollazioneService;
            _logs = logs;
            _allegati = allegati;
        }

        public AdempimentoSUAP[] Adatta()
        {

            var alberoProcMgr = new AlberoProcMgr(_datiProtocollazioneService.Db);
            var descrizione = alberoProcMgr.GetDescrizioneCompletaDaIdIntervento(_datiProtocollazioneService.CodiceInterventoProc.Value, _datiProtocollazioneService.IdComune, _datiProtocollazioneService.Software);

            var distintaModelloAttivita = new ModelloAttivitàAdapter(_datiProtocollazioneService, _logs, _allegati.First());

            var adempimento = new AdempimentoSUAP { nome = descrizione, distintamodelloattivita = distintaModelloAttivita.Adatta() };
            
            if(_allegati.Count > 1)
            {
                var allegatoGenerico = new AllegatoGenericoAdapter(_datiProtocollazioneService, _logs, _allegati.Skip(1));
                adempimento.documentoallegato = allegatoGenerico.Adatta();
            }

            return new AdempimentoSUAP[] { adempimento };
        }
    }
}
