using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using log4net;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;

namespace Init.SIGePro.Protocollo.ProtocolloServices
{
    public class ParametriInterventoProtocolloFascicolazioneService : IParametriInterventoProtocolloService
    {
        #region IParametriInterventoProtocolloService Members

        ILog _log = LogManager.GetLogger(typeof(ParametriInterventoProtocolloFascicolazioneService));
        ResolveDatiProtocollazioneService _datiProtocollazioneService;

        const string OGGETTO_DEFAULT_FASCICOLO = "Fascicolo dell'istanza numero ";

        public int? CodiceTestoTipo
        {
            get;
            set;
        }

        public string OggettoDefault
        {
            get;
            set;
        }

        public ParametriInterventoProtocolloFascicolazioneService(string codiceInterventoProc, ResolveDatiProtocollazioneService datiProtocollazioneService)
        {
            _datiProtocollazioneService = datiProtocollazioneService;
            SetCodiceTestoTipo(codiceInterventoProc);
            OggettoDefault = OGGETTO_DEFAULT_FASCICOLO;
        }

        private void SetCodiceTestoTipo(string codiceInterventoProc)
        {
            try
            {
                _log.DebugFormat("Funzionalità SetCodiceTestoTipo fascicolazione, codice intervento: {0}", codiceInterventoProc);
                
                int result;
                var parsing = Int32.TryParse(codiceInterventoProc, out result);

                if (!parsing)
                    throw new Exception("CODICE INTERVENTO TIPO NON VALORIZZATO");

                var alberoMgr = new AlberoProcMgr(_datiProtocollazioneService.Db);
                var res = alberoMgr.GetTestoTipoFascicoloFromAlberoProcProtocollo(result, _datiProtocollazioneService.IdComune, _datiProtocollazioneService.Software, _datiProtocollazioneService.CodiceComune);

                if (!String.IsNullOrEmpty(res))
                    CodiceTestoTipo = Convert.ToInt32(res);


                _log.DebugFormat("Fine funzionalità SetCodiceTestoTipo fascicolazione, codice testo tipo restituito: {0}, codice intervento: {1}", CodiceTestoTipo, codiceInterventoProc);
            }
            catch (Exception ex)
            {
                throw new Exception("SI E' VERIFICATO UN ERRORE DURANTE IL RECUPERO DEL CODICE DELL'INTERVENTO DALLA FASCICOLAZIONE", ex);
            }
        }

        #endregion
    }
}
