namespace Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti
{
	using System;
	using System.Collections.Generic;
	using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
	using Init.Sigepro.FrontEnd.AppLogic.Common;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.Incompatibilita;
	using Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices;
	using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
	using Init.Sigepro.FrontEnd.Infrastructure.Caching;

	public interface IEndoprocedimentiRepository
	{
		//ListaEndoDaIdInterventoDto WhereInterventoIs(string alias, int codiceIntervento);

		EndoprocedimentoDto GetById(string alias, int id, AmbitoRicerca ambitoRicercaDocumenti);

		Dictionary<int, List<TipiTitoloDto>> TipiTitoloWhereCodiceInventarioIn(string alias, List<int> codiciInventario);

		BaseDtoOfInt32String[] GetListaFamiglieFrontoffice(string alias, string software);

		BaseDtoOfInt32String[] GetListaCategorieFrontoffice(string alias, string software, int codiceFamiglia);

		BaseDtoOfInt32String[] GetListaEndoFrontoffice(string alias, string software, int codiceCategoria);

		TipiTitoloDto GetTipoTitoloById(string alias, int codiceTipoTitolo);

		RisultatoCaricamentoGerarchiaEndo CaricaGerarchia(string alias, int id, LivelloCaricamentoGerarchia livello);

		RisultatoRicercaTestualeEndo RicercaTestualeEndo(string alias, string software, string partial, TipoRicercaEnum tipoRicerca);

        EndoprocedimentiConsole GetEndoprocedimentiConsoleDaIdIntervento(int idIntervento, string codiceComune);
    }

	internal class WsEndoprocedimentiRepository : WsAreaRiservataRepositoryBase, IEndoprocedimentiRepository, IEndoprocedimentiIncompatibiliRepository
	{
		private static class Constants
		{
			public const string EndoDaIdInterventoSessionFmtStr = "ENDO_DA_ID_INTERVENTO_SESSION_ID_{0}";
			public const string TipoTitoloCacheKey = "TipoTitoloCacheKey:{0}:{1}";
		}

		private IAliasResolver _aliasResolver;
        private readonly IAuthenticationDataResolver _authenticationDataResolver;

        public WsEndoprocedimentiRepository(AreaRiservataServiceCreator sc, IAliasResolver aliasResolver, IAuthenticationDataResolver authenticationDataResolver)
			: base(sc)
		{
			this._aliasResolver = aliasResolver;
            this._authenticationDataResolver = authenticationDataResolver;
        }

		public List<KeyValuePair<int, string>> EndoPropostiWhereInterventoIs(string alias, int idIntervento)
		{
			using (var ws = _serviceCreator.CreateClient(alias))
			{
				try
				{
					var list = ws.Service.GetEndoprocedimentiPropostiDaCodiceIntervento(ws.Token, idIntervento);

					var endoProposti = new List<KeyValuePair<int, string>>();

					for (int i = 0; i < list.Length; i++)
					{
						var el = list[i];

						endoProposti.Add(new KeyValuePair<int, string>(el.Codice, el.Descrizione));
					}

					return endoProposti;
				}
				catch (Exception)
				{
					ws.Service.Abort();
					throw;
				}
			}
		}

		public EndoprocedimentoDto GetById(string alias, int id, AmbitoRicerca ambitoRicercaDocumenti)
		{
			using (var ws = _serviceCreator.CreateClient(alias))
			{
				try
				{
					var endo = ws.Service.GetEndoprocedimentoById(ws.Token, id, ambitoRicercaDocumenti);

					endo.Adempimenti = string.IsNullOrEmpty(endo.Adempimenti) ? string.Empty : endo.Adempimenti.Replace("\n", "<br />");
					endo.Amministrazione = string.IsNullOrEmpty(endo.Amministrazione) ? string.Empty : endo.Amministrazione.Replace("\n", "<br />");
					endo.Applicazione = string.IsNullOrEmpty(endo.Applicazione) ? string.Empty : endo.Applicazione.Replace("\n", "<br />");
					endo.DatiGenerali = string.IsNullOrEmpty(endo.DatiGenerali) ? string.Empty : endo.DatiGenerali.Replace("\n", "<br />");
					endo.Descrizione = string.IsNullOrEmpty(endo.Descrizione) ? string.Empty : endo.Descrizione.Replace("\n", "<br />");
					endo.NormativaNazionale = string.IsNullOrEmpty(endo.NormativaNazionale) ? string.Empty : endo.NormativaNazionale.Replace("\n", "<br />");
					endo.NormativaRegionale = string.IsNullOrEmpty(endo.NormativaRegionale) ? string.Empty : endo.NormativaRegionale.Replace("\n", "<br />");
					endo.NormativaUE = string.IsNullOrEmpty(endo.NormativaUE) ? string.Empty : endo.NormativaUE.Replace("\n", "<br />");
					endo.Note = string.IsNullOrEmpty(endo.Note) ? string.Empty : endo.Note.Replace("\n", "<br />");
					endo.Regolamenti = string.IsNullOrEmpty(endo.Regolamenti) ? string.Empty : endo.Regolamenti.Replace("\n", "<br />");
					endo.Tempificazione = string.IsNullOrEmpty(endo.Tempificazione) ? string.Empty : endo.Tempificazione.Replace("\n", "<br />");
					endo.Tipologia = string.IsNullOrEmpty(endo.Tipologia) ? string.Empty : endo.Tipologia.Replace("\n", "<br />");

					return endo;
				}
				catch (Exception)
				{
					ws.Service.Abort();
					throw;
				}
			}
		}

		//public ListaEndoDaIdInterventoDto WhereInterventoIs(string alias, int codIntervento)
		//{
		//	var sessionKey = string.Format(Constants.EndoDaIdInterventoSessionFmtStr, codIntervento);

		//	if (SessionHelper.KeyExists(sessionKey))
		//	{
		//		return SessionHelper.GetEntry<ListaEndoDaIdInterventoDto>(sessionKey);
		//	}

		//	using (var ws = _serviceCreator.CreateClient(alias))
		//	{
		//		try
		//		{
		//			var res = ws.Service.GetListaEndoDaIdIntervento(ws.Token, codIntervento);

		//			return SessionHelper.AddEntry(sessionKey, res);
		//		}
		//		catch (Exception)
		//		{
		//			ws.Service.Abort();
		//			throw;
		//		}
		//	}
		//}

		public Dictionary<int, List<TipiTitoloDto>> TipiTitoloWhereCodiceInventarioIn(string alias, List<int> codiciInventario)
		{
			using (var ws = _serviceCreator.CreateClient(alias))
			{
				try
				{
					var ret = ws.Service.GetTipiTitoloEndoDaListaCodiciInventario(ws.Token, codiciInventario.ToArray());

					var tipiTitolo = new Dictionary<int, List<TipiTitoloDto>>();

					foreach (var el in ret)
					{
						tipiTitolo.Add(el.Key, new List<TipiTitoloDto>(el.Value));
					}

					return tipiTitolo;
				}
				catch (Exception)
				{
					ws.Service.Abort();
					throw;
				}
			}
		}

		public BaseDtoOfInt32String[] GetListaFamiglieFrontoffice(string alias, string software)
		{
			using (var ws = _serviceCreator.CreateClient(alias))
			{
				try
				{
					return ws.Service.GetFamiglieEndoFrontoffice(ws.Token, software);
				}
				catch (Exception)
				{
					ws.Service.Abort();
					throw;
				}
			}
		}

		public BaseDtoOfInt32String[] GetListaCategorieFrontoffice(string alias, string software, int codiceFamiglia)
		{
			using (var ws = _serviceCreator.CreateClient(alias))
			{
				try
				{
					return ws.Service.GetCategorieEndoFrontoffice(ws.Token, software, codiceFamiglia);
				}
				catch (Exception)
				{
					ws.Service.Abort();
					throw;
				}
			}
		}

		public BaseDtoOfInt32String[] GetListaEndoFrontoffice(string alias, string software, int codiceCategoria)
		{
			using (var ws = _serviceCreator.CreateClient(alias))
			{
				try
				{
					return ws.Service.GetListaEndoFrontoffice(ws.Token, software, codiceCategoria);
				}
				catch (Exception)
				{
					ws.Service.Abort();
					throw;
				}
			}
		}

		private string TipoTitoloCacheKey(string idComune, int codiceTipoTitolo)
		{
			return String.Format(Constants.TipoTitoloCacheKey, idComune, codiceTipoTitolo);
		}

		public TipiTitoloDto GetTipoTitoloById(string alias, int codiceTipoTitolo)
		{
			var cacheKey = this.TipoTitoloCacheKey(alias, codiceTipoTitolo);

			if (CacheHelper.KeyExists(cacheKey))
			{
				return CacheHelper.GetEntry<TipiTitoloDto>(cacheKey);
			}

			using (var ws = _serviceCreator.CreateClient(alias))
			{
				try
				{
					var tipoTitolo = ws.Service.GetTipoTitoloById(ws.Token, codiceTipoTitolo);

                    if (tipoTitolo == null)
                    {
                        return null;
                    }

					return CacheHelper.AddEntry(cacheKey, tipoTitolo);
				}
				catch (Exception)
				{
					ws.Service.Abort();
					throw;
				}
			}
		}

		public RisultatoCaricamentoGerarchiaEndo CaricaGerarchia(string alias, int id, LivelloCaricamentoGerarchia livello)
		{
			using (var ws = _serviceCreator.CreateClient(alias))
			{
				try
				{
					return ws.Service.GetGerarchiaEndo(ws.Token, id, livello);
				}
				catch (Exception)
				{
					ws.Service.Abort();
					throw;
				}
			}
		}

		public RisultatoRicercaTestualeEndo RicercaTestualeEndo(string alias, string software, string partial, TipoRicercaEnum tipoRicerca)
		{
			using (var ws = _serviceCreator.CreateClient(alias))
			{
				try
				{
					return ws.Service.RicercaTestualeEndo(ws.Token, software, partial, tipoRicerca);
				}
				catch (Exception)
				{
					ws.Service.Abort();

					throw;
				}
			}
		}

		public IEnumerable<EndoprocedimentoIncompatibileDto> GetEndoprocedimentiIncompatibili(int[] listaIdEndoAttivati)
		{
			using (var ws = _serviceCreator.CreateClient(this._aliasResolver.AliasComune))
			{
				try
				{
					return ws.Service.GetEndoprocedimentiIncompatibili(ws.Token, listaIdEndoAttivati);
				}
				catch (Exception)
				{
					ws.Service.Abort();

					throw;
				}
			}
		}

        public string GetNaturaBaseDaidEndoprocedimento(int codiceInventario)
        {
            using (var ws = _serviceCreator.CreateClient(this._aliasResolver.AliasComune))
            {
                try
                {
                    var naturaDto = ws.Service.GetNaturaBaseDaidEndoprocedimento(ws.Token, codiceInventario);

                    return naturaDto == null ? String.Empty : naturaDto.Valore;
                }
                catch (Exception)
                {
                    ws.Service.Abort();

                    throw;
                }
            }
        }

        public EndoprocedimentiConsole GetEndoprocedimentiConsoleDaIdIntervento(int idIntervento, string codiceComune)
        {
            using (var ws = _serviceCreator.CreateClient(this._aliasResolver.AliasComune))
            {
                try
                {
                    var utenteTester = this._authenticationDataResolver.DatiAutenticazione.DatiUtente.UtenteTester;
                    var endo = ws.Service.GetEndoprocedimentiConsoleDaIdIntervento(ws.Token, idIntervento, codiceComune, utenteTester);

                    return endo;
                }
                catch (Exception)
                {
                    ws.Service.Abort();

                    throw;
                }
            }
        }
    }
}
