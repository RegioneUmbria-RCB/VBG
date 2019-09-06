using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.DTO.Endoprocedimenti;
using Init.SIGePro.Manager.DTO;
using Init.SIGePro.Manager;
using Init.Utils;
using System.Threading.Tasks;
using PersonalLib2.Data;
using Init.SIGePro.Manager.Logic.GestioneEndoprocedimenti;
using Init.SIGePro.Manager.Logic.AidaSmart.GestioneEndoprocedimenti;
using Init.SIGePro.Manager.Logic.AidaSmart;

namespace Sigepro.net.WebServices.WsAreaRiservata.Classes
{
	public partial class AreaRiservataServiceBase
	{
		[WebMethod]
		public List<BaseDto<int, string>> GetEndoprocedimentiPropostiDaCodiceIntervento(string token, int codiceIntervento)
		{
			return new EndoprocedimentiService().GetEndoprocedimentiPropostiDaCodiceIntervento(token, codiceIntervento);
		}

		[WebMethod]
		public EndoprocedimentoDto GetEndoprocedimentoById(string token, int id, AmbitoRicerca ambitoRicercaDocumenti)
		{
			return new EndoprocedimentiService().GetEndoprocedimentoById(token, id, ambitoRicercaDocumenti);
		}

		[WebMethod]
		public ListaEndoDaIdInterventoDto GetListaEndoDaIdIntervento(string token, int codiceIntervento)
		{
			return new EndoprocedimentiService().GetListaEndoDaIdIntervento(token, codiceIntervento);
		}

		[WebMethod]
		public List<EndoprocedimentoDto> GetEndoprocedimentiList(string token, List<int> listaId)
		{
			return new EndoprocedimentiService().GetEndoprocedimentiList(token, listaId);
		}


		[WebMethod]
		public List<TipiTitoloDto> GetTipiTitoloEndoDaCodiceInventario(string token, int codiceInventario)
		{
			var authInfo = CheckToken(token);

			var list = new InventarioProcTipiTitoloMgr(authInfo.CreateDatabase()).GetTipiTitoloDaCodiceInventario(authInfo.IdComune, codiceInventario);

			return list.Select(x => x.ToTipiTitoloDto()).ToList();
		}


		[WebMethod]
		public List<SerializableKeyValuePair<int, List<TipiTitoloDto>>> GetTipiTitoloEndoDaListaCodiciInventario(string token, List<int> listaCodiciInventario)
		{
			var authInfo = CheckToken(token);

			var rVal = new List<SerializableKeyValuePair<int, List<TipiTitoloDto>>>();

			foreach (var codiceInventario in listaCodiciInventario)
			{
				var listaTitoli = new InventarioProcTipiTitoloMgr(authInfo.CreateDatabase()).GetTipiTitoloDaCodiceInventario(authInfo.IdComune, codiceInventario);

				if (listaTitoli.Count == 0)
					continue;

				rVal.Add(new SerializableKeyValuePair<int, List<TipiTitoloDto>>
				{
					Key = codiceInventario,
					Value = listaTitoli.Select(x => x.ToTipiTitoloDto()).ToList()
				});
			}

			return rVal;
		}

		[WebMethod]
		public List<BaseDto<int, string>> GetFamiglieEndoFrontoffice(string token, string software)
		{
			var authInfo = CheckToken(token);

			return new InventarioProcedimentiMgr(authInfo.CreateDatabase()).GetFamiglieEndoFrontoffice(authInfo.IdComune, software);
		}

		[WebMethod]
		public List<BaseDto<int, string>> GetCategorieEndoFrontoffice(string token, string software, int codiceFamiglia)
		{
			var authInfo = CheckToken(token);

			return new InventarioProcedimentiMgr(authInfo.CreateDatabase()).GetCategorieEndoFrontoffice(authInfo.IdComune, software, codiceFamiglia);
		}

		[WebMethod]
		public List<BaseDto<int, string>> GetListaEndoFrontoffice(string token, string software, int codiceCategoria)
		{
			var authInfo = CheckToken(token);

			return new InventarioProcedimentiMgr(authInfo.CreateDatabase()).GetEndoFrontoffice(authInfo.IdComune, software, codiceCategoria);
		}

		public enum LivelloCaricamentoGerarchia
		{
			Famiglia, Categoria, Endo
		}

		public class RisultatoCaricamentoGerarchiaEndo
		{
			public int Famiglia { get; set; }
			public int Categoria { get; set; }
			public int Endo { get; set; }

			public RisultatoCaricamentoGerarchiaEndo()
			{
				Famiglia = -1;
				Categoria = -1;
				Endo = -1;
			}
		}

		[WebMethod]
		public RisultatoCaricamentoGerarchiaEndo GetGerarchiaEndo(string token, int valore, LivelloCaricamentoGerarchia livelloRicerca)
		{
			var authInfo = CheckToken(token);

			using(var db = authInfo.CreateDatabase())
			{
				if (livelloRicerca == LivelloCaricamentoGerarchia.Endo)
				{
					var endo = new InventarioProcedimentiMgr(db).GetById(authInfo.IdComune, valore);

					if (!endo.Codicetipo.HasValue)
						return new RisultatoCaricamentoGerarchiaEndo { Endo = valore };

					var tipo = new TipiEndoMgr(db).GetById(endo.Codicetipo.Value, authInfo.IdComune);

					if (!tipo.Codicefamigliaendo.HasValue)
						return new RisultatoCaricamentoGerarchiaEndo { Categoria = endo.Codicetipo.Value, Endo = valore };

					return new RisultatoCaricamentoGerarchiaEndo { Famiglia = tipo.Codicefamigliaendo.Value, Categoria = endo.Codicetipo.Value, Endo = valore };
				}

				if (livelloRicerca == LivelloCaricamentoGerarchia.Categoria)
				{
					var tipo = new TipiEndoMgr(db).GetById(valore, authInfo.IdComune);

					if (!tipo.Codicefamigliaendo.HasValue)
						return new RisultatoCaricamentoGerarchiaEndo { Categoria = valore };

					return new RisultatoCaricamentoGerarchiaEndo { Famiglia = tipo.Codicefamigliaendo.Value, Categoria = valore, Endo = -1 };
				}

				return new RisultatoCaricamentoGerarchiaEndo { Famiglia = valore };
			}
		}

		[WebMethod]
		public TipiTitoloDto GetTipoTitoloById(string token, int codiceTipoTitolo)
		{
			var authInfo = CheckToken(token);

			return new InventarioProcTipiTitoloMgr(authInfo.CreateDatabase()).GetById(authInfo.IdComune, codiceTipoTitolo).ToTipiTitoloDto();
		}



		[WebMethod]
		public RisultatoRicercaTestualeEndo RicercaTestualeEndo(string token, string software, string partial, InventarioProcedimentiMgr.TipoRicercaEnum tipoRicerca)
		{
			var authInfo = CheckToken(token);

			return new RicercaTestualeEndo(authInfo, software).TrovaEndo(partial, tipoRicerca);
		}

		[WebMethod]
		public EndoprocedimentoIncompatibileDto[] GetEndoprocedimentiIncompatibili(string token, int[] listaIdEndoAttivati)
		{
			var authInfo = CheckToken(token);

			using (var db = authInfo.CreateDatabase())
			{
				var incomp = new InventarioProcedimentiIncompMgr(db, authInfo.IdComune).GetListByListaCodiciEndo(listaIdEndoAttivati);

				return incomp.Select(x => new EndoprocedimentoIncompatibileDto
					{
						CodiceEndoprocedimento = Convert.ToInt32(x.CODICEINVENTARIO),
						CodiceEndoprocedimentoIncompatibile = Convert.ToInt32(x.CODICEINCOMPATIBILE)
					}).ToArray();
			}
		}

        [WebMethod]
        public NaturaBaseEndoprocedimentoDto GetNaturaBaseDaidEndoprocedimento(string token, int idEndoprocedimento)
        {
            var authInfo = CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var natura = new InventarioProcedimentiMgr(db).GetNaturaBaseDaIdEndoprocedimento(authInfo.IdComune, idEndoprocedimento);

                return new NaturaBaseEndoprocedimentoDto(natura);
            }
            
        }

        [WebMethod]
        public EndoprocedimentiConsole GetEndoprocedimentiConsoleDaIdIntervento(string token, int idIntervento, string codiceComune, bool utenteTester)
        {
            var authInfo = CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var console = new ConsoleService(db, authInfo.Alias);
                var service = new EndoprocedimentiConsoleService(db, console);

                return service.GetByIdIntervento(idIntervento, codiceComune, utenteTester);
            }
        }
    }
}
