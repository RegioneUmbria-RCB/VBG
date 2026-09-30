using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using log4net;
using Init.SIGePro.Manager;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Services
{
    public class ParametriInterventoProtocolloProtocolloService : IParametriInterventoProtocolloService
    {
        ILog _log = LogManager.GetLogger(typeof(ParametriInterventoProtocolloProtocolloService));
        ResolveDatiProtocollazioneService _datiProtocolloService;

        private const string OGGETTO_DEFAULT_PROTOCOLLO = "Protocollo dell'istanza n. ";
        private const string OGGETTO_DEFAULT_FASCICOLO = "Fascicolo dell'istanza n. ";

        #region IParametriInterventoProtocolloService Members

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

        public ParametriInterventoProtocolloProtocolloService(int? codiceMailTipo)
        {
            CodiceTestoTipo = codiceMailTipo;
            OggettoDefault = OGGETTO_DEFAULT_PROTOCOLLO;
        }

        public ParametriInterventoProtocolloProtocolloService(ResolveDatiProtocollazioneService datiProtocolloService, bool isFascicolo)
        {
            _datiProtocolloService = datiProtocolloService;

            if (isFascicolo)
            {
                SetCodiceTestoTipoFascicolo();
                OggettoDefault = OGGETTO_DEFAULT_FASCICOLO;
            }
            else
            {
                SetCodiceTestoTipo();
                OggettoDefault = OGGETTO_DEFAULT_PROTOCOLLO;
            }
        }

        private void SetCodiceTestoTipo()
        {
            try
            {
                _log.DebugFormat("Funzionalità SetCodiceTestoTipo protocollazione, codice intervento: {0}", _datiProtocolloService.CodiceInterventoProc.GetValueOrDefault(-1));

                if (!_datiProtocolloService.CodiceInterventoProc.HasValue)
                    throw new Exception("CODICE INTERVENTO TIPO NON VALORIZZATO");

                var alberoMgr = new AlberoProcMgr(_datiProtocolloService.Db);
                var res = alberoMgr.GetTestoTipoProtocolloFromAlberoProcProtocollo(_datiProtocolloService.CodiceInterventoProc.Value, _datiProtocolloService.IdComune, _datiProtocolloService.Software, _datiProtocolloService.CodiceComune);
                
                if (!String.IsNullOrEmpty(res))
                    CodiceTestoTipo = Convert.ToInt32(res);

                _log.DebugFormat("Fine funzionalità SetCodiceTestoTipo protocollazione, codice testo tipo restituito: {0}, codice intervento: {1}", CodiceTestoTipo, _datiProtocolloService.CodiceInterventoProc.Value);
            }
            catch (Exception ex)
            {
                throw new Exception("SI E' VERIFICATO UN ERRORE DURANTE IL RECUPERO DEL CODICE DELL'INTERVENTO DAL PROTOCOLLO", ex);
            }
        }

        private void SetCodiceTestoTipoFascicolo()
        {
            try
            {
                _log.DebugFormat("Funzionalità SetCodiceTestoTipoFascicolo, codice intervento: {0}", _datiProtocolloService.CodiceInterventoProc.GetValueOrDefault(-1));

                if (!_datiProtocolloService.CodiceInterventoProc.HasValue)
                    throw new Exception("CODICE INTERVENTO TIPO NON VALORIZZATO");

                var alberoMgr = new AlberoProcMgr(_datiProtocolloService.Db);
                var res = alberoMgr.GetTestoTipoFascicoloFromAlberoProcProtocollo(_datiProtocolloService.CodiceInterventoProc.Value, _datiProtocolloService.IdComune, _datiProtocolloService.Software, _datiProtocolloService.CodiceComune);

                if (!String.IsNullOrEmpty(res))
                    CodiceTestoTipo = Convert.ToInt32(res);

                _log.DebugFormat("Fine funzionalità SetCodiceTestoTipoFascicolo , codice testo tipo restituito: {0}, codice intervento: {1}", CodiceTestoTipo, _datiProtocolloService.CodiceInterventoProc.Value);
            }
            catch (Exception ex)
            {
                throw new Exception("SI E' VERIFICATO UN ERRORE DURANTE IL RECUPERO DEL CODICE DELL'INTERVENTO DAL PROTOCOLLO", ex);
            }
        }

        #endregion

    }
}
