using VBG.Frontend.AppLogic.WsAnagraficheService;
using Init.Sigepro.FrontEnd.AppLogic.WsEndoFrontoffice;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.Frontoffice
{
	public class EndoprocedimentiFrontofficeService
	{
		private readonly IEndoprocedimentiFrontofficeRepository _repo;

		public EndoprocedimentiFrontofficeService(IEndoprocedimentiFrontofficeRepository repo)
		{
			this._repo = repo;
		}

		public FamigliaEndoFrontoffice[] GetListaFamiglieFrontoffice(string aliasComune, string software)
		{
			return this._repo.GetListaFamiglieFrontoffice(aliasComune, software);
		}

		public EndoBreveFrontoffice[] GetListaEndoFrontoffice(string aliasComune, string software, int codiceCategoria)
		{
			return this._repo.GetListaEndoFrontoffice(aliasComune, software, codiceCategoria);
		}

		public CategoriaEndoFrontoffice[] GetListaCategorieFrontoffice(string aliasComune, string software, int codiceFamiglia)
		{
			return this._repo.GetListaCategorieFrontoffice(aliasComune, software, codiceFamiglia);
		}

		public RisultatoCaricamentoGerarchiaEndo CaricaGerarchiaFrontoffice(string aliasComune, int id, LivelloCaricamentoGerarchia livello)
		{


			return this._repo.CaricaGerarchiaFrontoffice(aliasComune, id, livello);
		}

		public RisultatoRicercaTestualeEndo RicercaTestualeFrontoffice(string alias, string software, string partial, TipoRicercaEnum tipoRicerca)
		{
			return this._repo.RicercaTestualeEndoFrontoffice(alias, software, partial, tipoRicerca);
		}

	}
}
