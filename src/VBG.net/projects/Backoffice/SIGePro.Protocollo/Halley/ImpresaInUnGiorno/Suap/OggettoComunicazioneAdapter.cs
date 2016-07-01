using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloServices;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Manager;

namespace Init.SIGePro.Protocollo.Halley.ImpresaInUnGiorno.Suap
{
    public class OggettoComunicazioneAdapter
    {
        ResolveDatiProtocollazioneService _datiProtocollazioneService;
        ProtocolloLogs _logs;

        public OggettoComunicazioneAdapter(ResolveDatiProtocollazioneService datiProtocollazioneService, ProtocolloLogs logs)
        {
            _datiProtocollazioneService = datiProtocollazioneService;
            _logs = logs;
        }


        public OggettoComunicazione Adatta()
        {
            var res = new OggettoComunicazione { Value = _datiProtocollazioneService.Istanza.LAVORI };

            var tipointervento = GetTipoIntervento();

            if (!String.IsNullOrEmpty(tipointervento))
            {
                res.tipointervento = ConvertiTipoIntervento(tipointervento);
                res.tipointerventoSpecified = true;
            }

            var oggettoComunicazione = GetOggettoTipoProcedimento();

            if (!String.IsNullOrEmpty(oggettoComunicazione))
            {
                res.tipoprocedimento = ConvertiOggettoComunicazione(oggettoComunicazione);
                res.tipoprocedimentoSpecified = true;
            }

            return res;
        }

        private OggettoComunicazioneTipoprocedimento ConvertiOggettoComunicazione(string codice)
        {
            try
            {
                return (OggettoComunicazioneTipoprocedimento)Enum.Parse(typeof(OggettoComunicazioneTipoprocedimento), codice);
            }
            catch
            {
                throw new Exception(String.Format("NON E' STATO POSSIBILE FARE IL PARSING DELL'OGGETTO COMUNICAZIONE TIPO PROCEDIMENTO CODICE {0}, IL TIPO PROCEDIMENTO NON E' PREVISTO DALLE SPECIFICHE", codice));
            }
        }

        private string GetOggettoTipoProcedimento()
        {
            var mgr = new RiTipoProcedimentoMgr(_datiProtocollazioneService.Db);
            var riTipoProc = mgr.GetRiProcedimentoByProcedura(Convert.ToInt32(_datiProtocollazioneService.Istanza.FKCODICESOGGETTO), _datiProtocollazioneService.IdComune);

            if (riTipoProc != null)
                return riTipoProc.Codice;

            return "";
        }

        private TipoIntervento ConvertiTipoIntervento(string codice)
        {
            try
            {
                return (TipoIntervento)Enum.Parse(typeof(TipoIntervento), codice);
            }
            catch
            {
                throw new Exception(String.Format("NON E' STATO POSSIBILE FARE IL PARSING DELL'INTERVENTO CODICE {0}, L'INTERVENTO NON E' PREVISTO DALLE SPECIFICHE", codice));
            }
        }

        private string GetTipoIntervento()
        {
            var mgr = new RiInterventoMgr(_datiProtocollazioneService.Db);
            var riInt = mgr.GetByIntervento(_datiProtocollazioneService.IdComune, Convert.ToInt32(_datiProtocollazioneService.CodiceInterventoProc));

            if (riInt != null)
                return riInt.Codice;

            return "";
        }
    }
}
