using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimentiFrontoffice
{
	public class EndoprocedimentiFrontofficeService
	{
		IEndoprocedimentiRepository _repo;

		public EndoprocedimentiFrontofficeService(IEndoprocedimentiRepository repo)
		{
			this._repo = repo;
		}

		public BaseDtoOfInt32String[] GetListaFamiglie(string aliasComune, string software)
		{
			return this._repo.GetListaFamiglieFrontoffice(aliasComune, software);
		}

		public BaseDtoOfInt32String[] GetListaEndo(string aliasComune, string software, int codiceCategoria)
		{
			return this._repo.GetListaEndoFrontoffice(aliasComune, software, codiceCategoria);
		}

		public BaseDtoOfInt32String[] GetListaCategorie(string aliasComune, string software, int codiceFamiglia)
		{
			return this._repo.GetListaCategorieFrontoffice(aliasComune, software, codiceFamiglia);
		}

		public RisultatoCaricamentoGerarchiaEndo CaricaGerarchia(string aliasComune, int id, LivelloCaricamentoGerarchia livello)
		{


			return this._repo.CaricaGerarchia(aliasComune, id, livello);
		}

		public RisultatoRicercaTestualeEndo RicercaTestuale(string alias,string software, string partial, TipoRicercaEnum tipoRicerca)
		{
			return this._repo.RicercaTestualeEndo(alias, software, partial, tipoRicerca);
		}

	}
}
