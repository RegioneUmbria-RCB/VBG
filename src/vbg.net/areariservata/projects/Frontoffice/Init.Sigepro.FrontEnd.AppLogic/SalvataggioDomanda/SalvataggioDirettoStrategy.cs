// -----------------------------------------------------------------------
// <copyright file="SalvataggioDirettoStrategy.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
	using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
	using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
	using Init.Sigepro.FrontEnd.AppLogic.Common;
	using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
	using log4net;
	
	using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
	using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;

	/// <summary>
	/// Effettua il caricamento ed il salvataggio della domanda direttamente senza passare per nessun metodo di caching
	/// </summary>
	internal class SalvataggioDirettoStrategy : ISalvataggioDomandaStrategy
	{
		ILog _log = LogManager.GetLogger(typeof(SalvataggioDirettoStrategy));

		IDatiDomandaFoRepository	_datiDomandaFoRepository;
		IAliasSoftwareResolver		_aliasSoftwareResolver;
		IAuthenticationDataResolver _authenticationDataResolver;

		IEventDispatcher _eventDispatcher;

		public SalvataggioDirettoStrategy(IAliasSoftwareResolver aliasSoftwareResolver, IAuthenticationDataResolver authenticationDataResolver, IDatiDomandaFoRepository datiDomandaFoRepository, IEventDispatcher eventDispatcher)
		{
			_aliasSoftwareResolver = aliasSoftwareResolver;
			_datiDomandaFoRepository = datiDomandaFoRepository;
			_authenticationDataResolver = authenticationDataResolver;
			_eventDispatcher = eventDispatcher;
		}

		#region ILogicaSalvataggioDomanda Members

		public DomandaOnline GetById(int idPresentazione)
		{
			var alias		= _aliasSoftwareResolver.AliasComune;
			var software	= _aliasSoftwareResolver.Software;

			var datiDomanda = _datiDomandaFoRepository.LeggiDatiDomanda(alias, idPresentazione);

			PresentazioneIstanzaDataKey dataKey = null;
			PresentazioneIstanzaDbV2 dataSet = null;
			bool domandaPresentata = false;


			if (datiDomanda != null)
			{
				domandaPresentata = datiDomanda.FlgPresentata.GetValueOrDefault(0) == 1;
				dataKey = PresentazioneIstanzaDataKey.New(alias, software, datiDomanda.Richiedente.CODICEFISCALE, idPresentazione);
				dataSet = _datiDomandaFoRepository.LeggiDataSetDomanda(alias, idPresentazione);
			}
			else
			{
				var codiceFiscale	= _authenticationDataResolver.DatiAutenticazione.DatiUtente.Codicefiscale;

				domandaPresentata = false;
				dataKey = PresentazioneIstanzaDataKey.New(alias, software, codiceFiscale, idPresentazione);
				dataSet = new PresentazioneIstanzaDbV2();
			}

			return new DomandaOnline(dataKey, dataSet, false);
		}

		public void Elimina(DomandaOnline domanda)
		{
			_datiDomandaFoRepository.Elimina(domanda.DataKey.IdComune, domanda.DataKey.IdPresentazione);
		}


		public void Salva(DomandaOnline domanda)
		{
			if (domanda.DataKey.IdPresentazione <= 0)
				throw new Exception("La domanda che si vuole salvare ha id " + domanda.DataKey.IdPresentazione + " non è possibile salvare una domanda con un id < 0");

			//_eventDispatcher.DispatchEvents(domanda);

			_datiDomandaFoRepository.Salva(domanda);
		}

        public byte[] GetAsXml(int idDomanda)
        {
            var domanda = GetById(idDomanda);

            return GetAsXml(domanda);
        }

        public byte[] GetAsXml(DomandaOnline domanda)
        {
            return this._datiDomandaFoRepository.ConvertToXml(domanda);
        }

        public void ImpostaIdIstanzaOrigine(int idDomanda, int idDomandaOrigine)
        {
            this._datiDomandaFoRepository.ImpostaIdIstanzaOrigine(idDomanda, idDomandaOrigine);
        }

        #endregion

    }
}
