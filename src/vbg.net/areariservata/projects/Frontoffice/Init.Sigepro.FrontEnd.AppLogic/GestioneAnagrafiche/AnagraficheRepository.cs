// -----------------------------------------------------------------------
// <copyright file="AnagraficheRepository.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche
{
    using AutoMapper;
    using CuttingEdge.Conditions;
    using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
    using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
    using Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices;
    using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
    using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;
    using Init.Sigepro.FrontEnd.Infrastructure.Caching;
    using log4net;
    using System;

	public interface IAnagraficheRepository
	{
        Anagrafe GetByUserId(string aliasComune, string userId, TipoPersonaEnum tipoPersona);
		void ModificaDatianagrafici(string idComune, AnagraficaUtente anagrafe);
		void ModificaPassword(string idComune, int codiceAnagrafe, string vecchiaPassword, string nuovaPassword, string confermaNuovaPassword);
		// void NuovaRegistrazione(string aliasComune, Anagrafe anagrafe);
		Anagrafe RicercaAnagrafica(string aliasComune, TipoPersonaEnum tipoPersona, string codiceFiscale);
        CreazioneAnagraficaResult CreaAnagrafica(string aliasComune, RichiestaCreazioneAnagraficaDto richiesta);
	}

	internal class WsAnagraficheRepository : WsAreaRiservataRepositoryBase, IAnagraficheRepository
	{
		const string GET_BY_CODICEFISCALE_CACHE_KEY = "GET_BY_CODICEFISCALE_CACHE_KEY_{0}_{1}";
		const string GET_BY_ID_CACHE_KEY = "GET_BY_ID_CACHE_KEY_";

		ILog _log = LogManager.GetLogger(typeof(WsAnagraficheRepository));

		CreazioneAnagrafeServiceCreator _creazioneAnagrafeServiceCreator;
		RicercaAnagraficheServiceCreator _ricercaAnagraficheServiceCreator;

		public WsAnagraficheRepository(CreazioneAnagrafeServiceCreator creazioneAnagrafeServiceCreator, AreaRiservataServiceCreator sc, RicercaAnagraficheServiceCreator ricercaAnagraficheServiceCreator)
			: base(sc)
		{
			Condition.Requires(creazioneAnagrafeServiceCreator, "serviceCreator").IsNotNull();

			_creazioneAnagrafeServiceCreator = creazioneAnagrafeServiceCreator;
			_ricercaAnagraficheServiceCreator = ricercaAnagraficheServiceCreator;
		}

		public void ModificaPassword(string idComune, int codiceAnagrafe, string vecchiaPassword, string nuovaPassword, string confermaNuovaPassword)
		{
			if (nuovaPassword != confermaNuovaPassword)
				throw new InvalidOperationException("La password di conferma e la nuova password non coincidono");

			if (String.IsNullOrEmpty(nuovaPassword))
				throw new InvalidOperationException("La nuova password non può essere vuota");

			try
			{
				using (var ws = _serviceCreator.CreateClient(idComune))
				{
					ws.Service.ModificaPasswordAnagrafica(ws.Token, codiceAnagrafe, vecchiaPassword, nuovaPassword);
				}
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore durante la modifica della password per l'anagrafica {0} nell'idcomune {1}: {2}", codiceAnagrafe, idComune, ex.ToString());

				throw new InvalidOperationException("Errore durante la modifica della password. La password non è stata modificata.");
			}
		}

		public void ModificaDatianagrafici(string idComune, AnagraficaUtente anagrafe)
		{
			try
			{
				using (var ws = _serviceCreator.CreateClient(idComune))
				{
					ws.Service.RichiediModificaDatiAnagrafica(ws.Token, anagrafe.ToWsAnagrafe());
				}
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore durante la modifica della password per l'anagrafica {0} , idComune={1}: {2}", anagrafe.Codiceanagrafe, idComune, ex.ToString());

				throw;
			}
		}

		public Anagrafe RicercaAnagrafica(string aliasComune, TipoPersonaEnum tipoPersona, string codiceFiscale)
		{
			var sessionKey = String.Format(GET_BY_CODICEFISCALE_CACHE_KEY, tipoPersona == TipoPersonaEnum.Fisica ? "F" : "G", codiceFiscale);

			if (SessionHelper.KeyExists(sessionKey))
				return SessionHelper.GetEntry<AreaRiservataService.Anagrafe>(sessionKey);

			var anag = RicercaAnagraficaInternal(aliasComune, tipoPersona, codiceFiscale);

			if (anag != null)
				SessionHelper.AddEntry(sessionKey, anag);

			return anag;
		}

		private Anagrafe RicercaAnagraficaInternal(string aliasComune, TipoPersonaEnum tipoPersona, string codiceFiscale)
		{
			using (var ws = this._ricercaAnagraficheServiceCreator.CreateClient(tipoPersona))
			{
				codiceFiscale = codiceFiscale.ToUpper();

				if (tipoPersona == TipoPersonaEnum.Fisica)
				{
					return Mapper.Map<Anagrafe>( ws.Service.getPersonaFisica( ws.Token, codiceFiscale));
				}

				return Mapper.Map<Anagrafe>(ws.Service.getPersonaGiuridica(ws.Token, codiceFiscale));
			}
		}

        //public void NuovaRegistrazione(string aliasComune, Anagrafe anagrafe)
        //{
        //    using (var ws = _serviceCreator.CreateClient(aliasComune))
        //    {
        //        ws.Service.RichiediNuovaRegistrazione(ws.Token, anagrafe);
        //    }
        //}

        public Anagrafe GetByUserId(string aliasComune, string userId, TipoPersonaEnum tipoPersona)
		{
			var cacheKey = String.Format("{0}:{1}:{2}:{3}", GET_BY_ID_CACHE_KEY, aliasComune , userId, tipoPersona);

			if (!SessionHelper.KeyExists(cacheKey))
			{
				try
				{
					using (var ws = _serviceCreator.CreateClient(aliasComune))
					{
						var value = ws.Service.GetAnagrafeByUserId(ws.Token, userId, tipoPersona == TipoPersonaEnum.Fisica ? TipoPersona.PersonaFisica: TipoPersona.PersonaGiuridica);

						if (value == null)
							return null;

						return SessionHelper.AddEntry(cacheKey, value);
					}
				}
				catch (Exception ex)
				{
					_log.ErrorFormat("Errore durante la lettura dell'anagrafica {0} nell'idcomune {1}: {2}", userId, aliasComune, ex.ToString());

					throw;
				}
			}

			return SessionHelper.GetEntry<Anagrafe>(cacheKey);
		}

        public CreazioneAnagraficaResult CreaAnagrafica(string aliasComune, RichiestaCreazioneAnagraficaDto richiesta)
		{
			using (var ws = _creazioneAnagrafeServiceCreator.CreateClient(aliasComune))
			{

				var anagrafeRequest = new Init.Sigepro.FrontEnd.AppLogic.CreazioneAnagrafeService.InserimentoAnagrafeRequest
				{
					token = ws.Token,
                    datiAnagrafici = richiesta.GetAnagrafeType(),
                    tipoInserimento = richiesta.AuthType,
                    tipoInserimentoSpecified = true,
                    xmlDatiAnagrafici = richiesta.GetXmlAnagrafica()
				};

                _log.DebugFormat("Creazione di una nuova anagrafica nel comune {0}:\r\n{1}", aliasComune, richiesta.GetXmlAnagrafica());

				var response = ws.Service.InserimentoAnagrafe(anagrafeRequest);

				_log.DebugFormat("Creazione nuova anagrafica terminata, alias comune {0}:\r\n {1}", aliasComune, response.ToXmlString());

                if (response.errori == null)
                {
                    return CreazioneAnagraficaResult.Success;
                }

                return CreazioneAnagraficaResult.Failed(response.errori.numeroErrore, response.errori.descrizione);

            }
        }

	}
}
