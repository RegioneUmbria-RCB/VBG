using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Manager.Logic.RicercheAnagrafiche;
using Init.SIGePro.Data;
using Terni.AnagrafeCiviliaService;
using System.ServiceModel;
using Init.SIGePro.Manager;
using log4net;

namespace Terni
{
	public class AnagrafeSearcher : AnagrafeSearcherBase
	{
		ILog _log = LogManager.GetLogger(typeof(AnagrafeSearcher));

		private static class Constants
		{
			public const string NomeAnagrafeSearcherSigepro = "DEFAULTANAGRAFESEARCHER";
			public const string WebServiceBindingName = "defaultHttpBinding";


			internal static class ChiaviVerticalizzazioni
			{
				public const string WebServiceUrl = "WS_URL";
				public const string Username = "USERNAME";
				public const string Password = "PASSWORD";
				public const string Ente = "ENTE";
				public const string UsaAnagrafeSigeproSeAnagraficaNonTrovata = "USA_ANAGRAFE_SIGEPRO";
			}
		}


		private string _webServiceUrl = String.Empty;
		private string _username = String.Empty;
		private string _password = String.Empty;
		private string _ente = String.Empty;

		private Init.SIGePro.Manager.Logic.RicercheAnagrafiche.AnagrafeSearcher _sigeproAnagrafeSearcher;


		public AnagrafeSearcher()
			: base("TERNI")
		{
		}

		public override void Init()
		{
			this._webServiceUrl = Configuration[Constants.ChiaviVerticalizzazioni.WebServiceUrl].ToString();
			this._username = Configuration[Constants.ChiaviVerticalizzazioni.Username].ToString();
			this._password = Configuration[Constants.ChiaviVerticalizzazioni.Password].ToString();
			this._ente = Configuration[Constants.ChiaviVerticalizzazioni.Ente].ToString();

			if (Configuration[Constants.ChiaviVerticalizzazioni.UsaAnagrafeSigeproSeAnagraficaNonTrovata] == "1")
			{
				_sigeproAnagrafeSearcher = new Init.SIGePro.Manager.Logic.RicercheAnagrafiche.AnagrafeSearcher(Constants.NomeAnagrafeSearcherSigepro);
				_sigeproAnagrafeSearcher.InitParams(this.IdComune, this.Alias, this.SigeproDb);
			}
		}

		public override Init.SIGePro.Data.Anagrafe ByCodiceFiscaleImp(string codiceFiscale)
		{
			return this.ByCodiceFiscaleImp(global::Init.SIGePro.Manager.Logic.RicercheAnagrafiche.TipoPersona.PersonaFisica, codiceFiscale);
		}

		public override Init.SIGePro.Data.Anagrafe ByCodiceFiscaleImp(Init.SIGePro.Manager.Logic.RicercheAnagrafiche.TipoPersona tipoPersona, string codiceFiscale)
		{
			using (var ws = CreateClient())
			{
				_log.DebugFormat("Invocazione di ByCodiceFiscaleImp con codiceFiscale={0} e tipo persona={1}", codiceFiscale, tipoPersona);

				var tipoPersonaCivilia = tipoPersona == global::Init.SIGePro.Manager.Logic.RicercheAnagrafiche.TipoPersona.PersonaFisica ? 
										 Terni.AnagrafeCiviliaService.TipoPersona.PersonaFisica : 
										 Terni.AnagrafeCiviliaService.TipoPersona.PersonaGiuridica;

				AnagrafeCiviliaService.Anagrafe anagrafeCivilia = null;

				try
				{
					anagrafeCivilia = ws.ByCodiceFiscaleETipoPersona(this._username,
																		   this._password,
																		   this._ente,
																		   tipoPersonaCivilia,
																		   codiceFiscale);
				}
				catch (Exception ex)
				{
					// il web service solleva un'eccezione se l'anagrafica non è stata trovata
					_log.ErrorFormat("Eccezione sollevata dal web service di Civilia: {0}", ex.Message);
				}


				if (anagrafeCivilia != null)
				{
					anagrafeCivilia.TIPOANAGRAFE = tipoPersona == global::Init.SIGePro.Manager.Logic.RicercheAnagrafiche.TipoPersona.PersonaFisica ? "F" : "G";
				}

				if (anagrafeCivilia == null && _sigeproAnagrafeSearcher != null)
				{
					_log.Debug("La ricerca non ha restituito risultati, verrà effettuata una ricerca tra le anagrafiche di sigepro");

					var anagrafeSigepro = _sigeproAnagrafeSearcher.ByCodiceFiscaleImp(codiceFiscale);

					if (anagrafeSigepro != null)
					{
						_log.Debug("L'anagrafica è stata trovata in Sigepro");

						return anagrafeSigepro;
					}

					_log.Debug("L'anagrafica non è stata trovata in Sigepro");
				}


				return Adatta(anagrafeCivilia);
			}
		}

		public override Init.SIGePro.Data.Anagrafe ByPartitaIvaImp(string partitaIva)
		{
			using (var ws = CreateClient())
			{
				_log.DebugFormat("Invocazione di ByPartitaIvaImp con partitaIva={0}", partitaIva);

				var anagrafeCivilia = ws.ByPartitaIva( this._username,
														this._password,
														this._ente,
														partitaIva );

				if (anagrafeCivilia == null && _sigeproAnagrafeSearcher != null)
				{
					_log.Debug("La ricerca non ha restituito risultati, verrà effettuata una ricerca tra le anagrafiche di sigepro");

					var anagrafeSigepro = _sigeproAnagrafeSearcher.ByPartitaIvaImp(partitaIva);

					if (anagrafeSigepro != null)
					{
						_log.Debug("L'anagrafica è stata trovata in Sigepro");

						return anagrafeSigepro;
					}

					_log.Debug("L'anagrafica non è stata trovata in Sigepro");
				}

				return Adatta(anagrafeCivilia);
			}
		}

		public override List<Init.SIGePro.Data.Anagrafe> ByNomeCognomeImp(string nome, string cognome)
		{
			throw new NotImplementedException();
		}

		private WsAnagrafePortTypeClient CreateClient()
		{
			var binding = new BasicHttpBinding(Constants.WebServiceBindingName);
			var endpoint = new EndpointAddress(this._webServiceUrl);

			var client = new WsAnagrafePortTypeClient(binding, endpoint);

			return client;
		}

		private Init.SIGePro.Data.Anagrafe Adatta(Terni.AnagrafeCiviliaService.Anagrafe anagrafeCivilia)
		{
			if (anagrafeCivilia == null)
				return null;

			if (!String.IsNullOrEmpty(anagrafeCivilia.COMUNERESIDENZA))
				anagrafeCivilia.COMUNERESIDENZA = CodiceComuneDaCodiceIstat(anagrafeCivilia.COMUNERESIDENZA);

			if (!String.IsNullOrEmpty(anagrafeCivilia.CODCOMNASCITA))
				anagrafeCivilia.CODCOMNASCITA = CodiceComuneDaCodiceIstat(anagrafeCivilia.CODCOMNASCITA);

			var rVal  = new Init.SIGePro.Data.Anagrafe
			{
				NOMINATIVO = anagrafeCivilia.NOMINATIVO,
				NOME = anagrafeCivilia.NOME,
				INDIRIZZO = anagrafeCivilia.INDIRIZZO,
				CAP = anagrafeCivilia.CAP,
				CODICEFISCALE = anagrafeCivilia.CODICEFISCALE,
				PARTITAIVA = anagrafeCivilia.PARTITAIVA,
				DATANASCITA = anagrafeCivilia.DATANASCITA,
				SESSO = anagrafeCivilia.SESSO,
				COMUNERESIDENZA = anagrafeCivilia.COMUNERESIDENZA,
				CODCOMNASCITA = anagrafeCivilia.CODCOMNASCITA,
				TIPOANAGRAFE = anagrafeCivilia.TIPOANAGRAFE
			};

			return rVal;

		}

		private string CodiceComuneDaCodiceIstat(string codiceIstat)
		{
			var comuniMgr = new ComuniMgr(SigeproDb);

			var comune = comuniMgr.GetByCodiceIstat(codiceIstat);

			if (comune == null)
			{
				_log.ErrorFormat("Impossibile trovare il codice istat " + codiceIstat + " letto durante la richiesta anagrafica");
				return string.Empty;
			}

			return comune.CODICECOMUNE;
		}
	}
}
