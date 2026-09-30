// -----------------------------------------------------------------------
// <copyright file="SalvataggioCachedStrategy.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda
{
    using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
    using Init.Sigepro.FrontEnd.AppLogic.Common;
    using Init.Sigepro.FrontEnd.AppLogic.Configurazione;
    using Init.Sigepro.FrontEnd.AppLogic.GestioneCodeMessaggi.DomandeInBozza;
    using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
    using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda.EliminazioneDomanda;
    using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda.Repositories;
    using VBG.Shared.Infrastructure.Caching;
    using log4net;
    using System.Threading.Tasks;

    /// <summary>
    /// Effettua il salvataggio della domanda dell'utente e mantiene una cache della domanda corrente in sessione
    /// </summary>
    public class SalvataggioCachedStrategy : ISalvataggioDomandaStrategy
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(SalvataggioCachedStrategy));
        private readonly CacheDomandeOnline _cacheDomande;
        private readonly SalvataggioDirettoStrategy _logicaSalvataggioStandard;
        private readonly IAliasSoftwareResolver _aliasSoftwareResolver;

        public SalvataggioCachedStrategy(IAliasSoftwareResolver aliasSoftwareResolver, IAuthenticationDataResolver authenticationDataResolver,
            IDatiDomandaFoRepository datiDomandaFoRepository, IApplicationCache webCache,
            IAppConfigurationReader appConfigurationReader, IEventiDomandeInBozzaService eventiDomandeInBozzaService, IEliminazioneBozzaDomandaService eliminazioneBozzaDomandaService)
        {
            this._aliasSoftwareResolver = aliasSoftwareResolver;
            this._logicaSalvataggioStandard = new SalvataggioDirettoStrategy(aliasSoftwareResolver, authenticationDataResolver, datiDomandaFoRepository, eventiDomandeInBozzaService, eliminazioneBozzaDomandaService);
            this._cacheDomande = new CacheDomandeOnline(new PuliziaBasataSuNumeroEtaStrategy(webCache, appConfigurationReader));
        }


        #region ILogicaSalvataggioDomanda Members

        public DomandaOnline GetById(int idPresentazione)
        {
            return this._cacheDomande.GetOrAdd(this._aliasSoftwareResolver.AliasComune, idPresentazione, () =>
            {
                this._log.DebugFormat("Cache miss per la domanda con idDomanda={0}, verrà effettuato il caricamento completo dei dati", idPresentazione);

                return this._logicaSalvataggioStandard.GetById(idPresentazione);
            });

            /*
            var domanda = this._cacheDomande.Get(this._aliasSoftwareResolver.AliasComune, idPresentazione);

            if (domanda == null)
            {
                lock (typeof(SalvataggioCachedStrategy))
                {
                    domanda = this._cacheDomande.Get(this._aliasSoftwareResolver.AliasComune, idPresentazione);

                    if (domanda == null)
                    {
                        this._log.DebugFormat("Cache miss per la domanda con idDomanda={0}, verrà effettuato il caricamento completo dei dati", idPresentazione);

                        domanda = this._logicaSalvataggioStandard.GetById(idPresentazione);

                        this._cacheDomande.Add(domanda);
                    }
                }
            }

            domanda.ReadInterface.Invalidate();

            return domanda;
            */
        }

        public Task<DomandaOnline> GetByIdAsync(int idPresentazione)
        {
            return this._cacheDomande.GetOrAddAsync(this._aliasSoftwareResolver.AliasComune, idPresentazione, async () =>
            {
                this._log.DebugFormat("Cache miss per la domanda con idDomanda={0}, verrà effettuato il caricamento completo dei dati", idPresentazione);

                return await this._logicaSalvataggioStandard.GetByIdAsync(idPresentazione);
            });
        }


        public void Salva(DomandaOnline domanda, bool aggiornaDataultimaModifica = true)
        {
            if (domanda.Flags.Presentata)
                this._cacheDomande.Remove(domanda.DataKey.IdComune, domanda.DataKey.IdPresentazione);

            this._logicaSalvataggioStandard.Salva(domanda, aggiornaDataultimaModifica);

            domanda.ReadInterface.Invalidate();
        }

        public async ValueTask SalvaAsync(DomandaOnline domanda, bool aggiornaDataultimaModifica = true)
        {
            if (domanda.Flags.Presentata)
                this._cacheDomande.Remove(domanda.DataKey.IdComune, domanda.DataKey.IdPresentazione);

            await this._logicaSalvataggioStandard.SalvaAsync(domanda, aggiornaDataultimaModifica);

            domanda.ReadInterface.Invalidate();
        }

        public void Elimina(DomandaOnline domanda)
        {
            this._cacheDomande.Remove(domanda.DataKey.IdComune, domanda.DataKey.IdPresentazione);

            this._logicaSalvataggioStandard.Elimina(domanda);
        }


        public byte[] GetAsXml(int idDomanda)
        {
            var domanda = this.GetById(idDomanda);

            return this._logicaSalvataggioStandard.GetAsXml(domanda);
        }

        public void ImpostaIdIstanzaOrigine(int idDomanda, int idDomandaOrigine)
        {
            this._logicaSalvataggioStandard.ImpostaIdIstanzaOrigine(idDomanda, idDomandaOrigine);
        }



        #endregion
    }
}
