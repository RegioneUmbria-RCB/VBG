using Init.SIGePro.Manager;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Core.Halley.ImpresaInUnGiorno.Suap
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
