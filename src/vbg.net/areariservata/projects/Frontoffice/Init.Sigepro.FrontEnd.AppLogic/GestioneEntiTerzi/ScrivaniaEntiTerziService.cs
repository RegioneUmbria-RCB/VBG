using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneEntiTerzi
{
    public class ScrivaniaEntiTerziService : IScrivaniaEntiTerziService
    {
        private readonly IConfigurazione<ParametriScrivaniaEntiTerzi> _configurazione;
        private readonly IScrivaniaEntiTerziWsProxy _proxy;

        internal ScrivaniaEntiTerziService(IConfigurazione<ParametriScrivaniaEntiTerzi> configurazione, IScrivaniaEntiTerziWsProxy proxy)
        {
            _configurazione = configurazione;
            _proxy = proxy;
        }

        public IEnumerable<ETPratica> GetPraticheDiCompetenza(ETCodiceAnagrafe codiceAnagrafe, ETFiltriRicerca filtri)
        {
            return _proxy.GetListaPratiche(codiceAnagrafe, filtri);
        }

        public bool ModuloAttivo(string software)
        {
            return _configurazione.Parametri.VerticalizzazioneAttiva && _configurazione.Parametri.CodiceSoftwareGestione.ToUpperInvariant() == software.ToUpperInvariant();
        }

        public bool UtentePuoAccedere(ETCodiceAnagrafe codiceAnagrafe)
        {
            var amministrazione = _proxy.GetAmministrazioneCollegataAdAnagrafica(codiceAnagrafe);

            return amministrazione != null;
        }

        public ETAmministrazioneCollegata GetDatiAmministrazioneCollegata(ETCodiceAnagrafe codiceAnagrafe)
        {
            return _proxy.GetAmministrazioneCollegataAdAnagrafica(codiceAnagrafe);
        }

        public IEnumerable<ETSoftwareConPratiche> GetListaSoftwareConPratiche(ETCodiceAnagrafe codiceAnagrafe)
        {
            return this._proxy.GetListaSoftwareConPratiche(codiceAnagrafe.Value);
        }

        public bool PraticaElaborata(ETCodiceIstanza codiceIStanza, ETCodiceAnagrafe codiceAnagrafe)
        {
            return this._proxy.PraticaElaborata(codiceIStanza, codiceAnagrafe);
        }

        public void MarcaPraticaComeElaborata(ETCodiceIstanza codiceIStanza, ETCodiceAnagrafe codiceAnagrafe)
        {
            this._proxy.MarcaPraticaComeElaborata(codiceIStanza, codiceAnagrafe);
        }

        public void MarcaPraticaComeNonElaborata(ETCodiceIstanza codiceIStanza, ETCodiceAnagrafe codiceAnagrafe)
        {
            this._proxy.MarcaPraticaComeNonElaborata(codiceIStanza, codiceAnagrafe);
        }

        public bool PuoEffettuareMovimenti(ETCodiceAnagrafe codiceAnagrafe)
        {
            return this._proxy.PuoEffettuareMovimenti(codiceAnagrafe);
        }
    }
}
