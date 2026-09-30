using log4net;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using VBG.Backend.SIT.Verticalizzazioni;


namespace VBG.Backend.SIT.AppLogic.Manager
{

    internal class ConcreteSitFactory
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(ConcreteSitFactory));
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly SitRegistry _sitRegistry = new SitRegistry();

        private readonly string _idComune;
        private readonly string _idComuneAlias;
        private readonly string _software;
        private readonly Lazy<ISitApi> _istanzaClasseSit;

        public ConcreteSitFactory(string idComune, string idComuneAlias, string software, IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            if (string.IsNullOrEmpty(idComune))
            {
                throw new ArgumentException($"'{nameof(idComune)}' cannot be null or empty.", nameof(idComune));
            }

            if (string.IsNullOrEmpty(idComuneAlias))
            {
                throw new ArgumentException($"'{nameof(idComuneAlias)}' cannot be null or empty.", nameof(idComuneAlias));
            }

            if (string.IsNullOrEmpty(software))
            {
                throw new ArgumentException($"'{nameof(software)}' cannot be null or empty.", nameof(software));
            }

            if (verticalizzazioniFactory is null)
            {
                throw new ArgumentException($"'{nameof(verticalizzazioniFactory)}' cannot be null or empty.", nameof(verticalizzazioniFactory));
            }

            this._idComune = idComune;
            this._idComuneAlias = idComuneAlias;
            this._software = software;
            this._istanzaClasseSit = new Lazy<ISitApi>(() => this.Create(this.GetTipoSitAttivo()));
            this._verticalizzazioniFactory = verticalizzazioniFactory;
        }

        public ISitApi GetSitAttivo() => this._istanzaClasseSit.Value;

        private ISitApi Create(string tipoSit)
        {
            this._log.DebugFormat("Caricamento via reflection del sitconnector {0}", tipoSit);

            if (String.IsNullOrEmpty(tipoSit))
            {
                this._log.Error("Nell'installazione corrente non sono presenti sit attivi");
                throw new InvalidOperationException("La verticalizzazione SIT_ATTIVO non è attiva. Software " + this._software + "\r\n");
            }

            var sitConnector = this._sitRegistry.GetSitInstance(tipoSit);

            sitConnector.InizializzaParametriSigepro(this._idComune, this._idComuneAlias, this._software);

            sitConnector.SetupVerticalizzazione(this._verticalizzazioniFactory);

            return sitConnector;
        }

        private string GetTipoSitAttivo()
        {
            try
            {
                var verticalizzazioneSit = this._verticalizzazioniFactory.Create<VerticalizzazioneSitAttivo>(this._idComuneAlias, this._software);

                this._log.DebugFormat("GetTipoSitAttivo, verticalizzazione caricata con successo, stato attiva: {0}, tipo sit: {1}", verticalizzazioneSit.Attiva, verticalizzazioneSit.Tiposit);

                return (verticalizzazioneSit.Attiva) ? verticalizzazioneSit.Tiposit : string.Empty;
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore in GetTipoSitAttivo: {0}", ex.ToString());

                throw;
            }
        }
    }
}
