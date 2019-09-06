// -----------------------------------------------------------------------
// <copyright file="SalvataggioCachedStrategy.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda
{
	using Init.Sigepro.FrontEnd.AppLogic.Common;
	
	using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
	using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
	using log4net;
	using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;

	/// <summary>
	/// Effettua il salvataggio della domanda dell'utente e mantiene una cache della domanda corrente in sessione
	/// </summary>
	internal class SalvataggioCachedStrategy : ISalvataggioDomandaStrategy
	{
		ILog _log = LogManager.GetLogger(typeof(SalvataggioCachedStrategy));

		CacheDomandeOnline _cacheDomande = new CacheDomandeOnline(new PuliziaBasataSuNumeroEtaStrategy());

		SalvataggioDirettoStrategy _logicaSalvataggioStandard;
		IAliasSoftwareResolver _aliasSoftwareResolver;

		public SalvataggioCachedStrategy(IAliasSoftwareResolver aliasSoftwareResolver, IAuthenticationDataResolver authenticationDataResolver, IDatiDomandaFoRepository datiDomandaFoRepository, IEventDispatcher eventDispatcher)
		{
			_aliasSoftwareResolver = aliasSoftwareResolver;
			_logicaSalvataggioStandard = new SalvataggioDirettoStrategy(aliasSoftwareResolver, authenticationDataResolver, datiDomandaFoRepository, eventDispatcher);
		}


		#region ILogicaSalvataggioDomanda Members

		public DomandaOnline GetById(int idPresentazione)
		{
			var domanda = _cacheDomande.Get(_aliasSoftwareResolver.AliasComune, idPresentazione);

			if (domanda == null)
			{
				lock (typeof(SalvataggioCachedStrategy))
				{
					domanda = _cacheDomande.Get(_aliasSoftwareResolver.AliasComune, idPresentazione);

					if (domanda == null)
					{
						_log.DebugFormat("Cache miss per la domanda con idDomanda={0}, verrà effettuato il caricamento completo dei dati", idPresentazione);

						domanda = _logicaSalvataggioStandard.GetById(idPresentazione);

						_cacheDomande.Add(domanda);
					}
				}
			}

			domanda.ReadInterface.Invalidate();

			return domanda;
		}


		public void Salva(DomandaOnline domanda)
		{
			if(domanda.Flags.Presentata)
				_cacheDomande.Remove(domanda.DataKey.IdComune, domanda.DataKey.IdPresentazione);

			_logicaSalvataggioStandard.Salva(domanda);
		}

		public void Elimina(DomandaOnline domanda)
		{
			_cacheDomande.Remove(domanda.DataKey.IdComune, domanda.DataKey.IdPresentazione);

			_logicaSalvataggioStandard.Elimina(domanda);
		}

		
        public byte[] GetAsXml(int idDomanda)
        {
            var domanda = GetById(idDomanda);

            return this._logicaSalvataggioStandard.GetAsXml(domanda);
        }

        public void ImpostaIdIstanzaOrigine(int idDomanda, int idDomandaOrigine)
        {
            this._logicaSalvataggioStandard.ImpostaIdIstanzaOrigine(idDomanda, idDomandaOrigine);
        }

        #endregion
    }
}
