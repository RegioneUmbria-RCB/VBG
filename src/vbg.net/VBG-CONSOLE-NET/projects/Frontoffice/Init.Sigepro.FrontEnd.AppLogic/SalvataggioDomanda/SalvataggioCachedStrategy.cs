// -----------------------------------------------------------------------
// <copyright file="SalvataggioCachedStrategy.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using log4net;

namespace Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda
{
    /// <summary>
    /// Effettua il salvataggio della domanda dell'utente e mantiene una cache della domanda corrente in sessione
    /// </summary>
    internal class SalvataggioCachedStrategy : ISalvataggioDomandaStrategy
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(SalvataggioCachedStrategy));
        private readonly CacheDomandeOnline _cacheDomande = new CacheDomandeOnline(new PuliziaBasataSuNumeroEtaStrategy());
        private readonly SalvataggioDirettoStrategy _logicaSalvataggioStandard;
        private readonly IAliasSoftwareResolver _aliasSoftwareResolver;

        public SalvataggioCachedStrategy(IAliasSoftwareResolver aliasSoftwareResolver, IAuthenticationDataResolver authenticationDataResolver, IDatiDomandaFoRepository datiDomandaFoRepository)
        {
            this._aliasSoftwareResolver = aliasSoftwareResolver;
            this._logicaSalvataggioStandard = new SalvataggioDirettoStrategy(aliasSoftwareResolver, authenticationDataResolver, datiDomandaFoRepository);
        }


        #region ILogicaSalvataggioDomanda Members

        public DomandaOnline GetById(int idPresentazione)
        {
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
        }


        public void Salva(DomandaOnline domanda)
        {
            if (domanda.Flags.Presentata)
                this._cacheDomande.Remove(domanda.DataKey.IdComune, domanda.DataKey.IdPresentazione);

            this._logicaSalvataggioStandard.Salva(domanda);
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
