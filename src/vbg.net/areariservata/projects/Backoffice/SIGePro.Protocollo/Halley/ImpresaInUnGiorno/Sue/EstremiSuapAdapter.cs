using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloServices;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Manager;

namespace Init.SIGePro.Protocollo.Halley.ImpresaInUnGiorno.Sue
{
    public class EstremiSuapAdapter
    {
        ResolveDatiProtocollazioneService _datiProtocollazioneService;
        ProtocolloLogs _logs;

        public EstremiSuapAdapter(ResolveDatiProtocollazioneService datiProtocollazioneService, ProtocolloLogs logs)
        {
            _datiProtocollazioneService = datiProtocollazioneService;
            _logs = logs;
        }

        public EstremiSuap Adatta()
        {
            var comAssMgr = new ComuniAssociatiMgr(_datiProtocollazioneService.Db);
            var comAss = comAssMgr.GetById(_datiProtocollazioneService.CodiceComune, _datiProtocollazioneService.IdComune);

            if (comAss == null || String.IsNullOrEmpty(comAss.CodiceamministrazioneIpa))
                throw new Exception("CODICE AMMINISTRAZIONE IPA NON VALORIZZATO");

            var comAssSoftwMgr = new ComuniAssociatiSoftwareMgr(_datiProtocollazioneService.Db);
            var comAssSoft = comAssSoftwMgr.GetByCodiceComuneSoftwareIdComune(_datiProtocollazioneService.IdComune, _datiProtocollazioneService.Software, _datiProtocollazioneService.CodiceComune);

            if (comAssSoft == null || String.IsNullOrEmpty(comAssSoft.CodiceAoo))
                throw new Exception("CODICE AOO NON VALORIZZATO");

            if (String.IsNullOrEmpty(comAssSoft.CodiceAccreditamento))
                throw new Exception("IDENTIFICATIVO NON VALORIZZATO");

            return new EstremiSuap { codiceamministrazione = comAss.CodiceamministrazioneIpa, codiceaoo = comAssSoft.CodiceAoo, identificativosuap = comAssSoft.CodiceAccreditamento };
        }
    }
}
