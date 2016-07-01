using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using System.Web.Script.Services;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.AmbitoRicercaIntervento;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimentiFrontoffice;

namespace Init.Sigepro.FrontEnd.Public.WebServices
{
	/// <summary>
	/// Summary description for AlberoEndoJsService
	/// </summary>
	[WebService(Namespace = "http://tempuri.org/")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[System.ComponentModel.ToolboxItem(false)]
	// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
	[ScriptService]
	public class AlberoEndoJsService : System.Web.Services.WebService
	{
		private static class Constants
		{
			public const string PrefissoCodiceFamiglia = "F";
			public const string PrefissoCodiceCategoria = "C";

			public const string PrefissoDescrizioneFamiglia = "FAM";
			public const string PrefissoDescrizioneCategoria = "CAT";
			public const string PrefissoDescrizioneEndo = "PROC";
		}

		[Inject]
		public EndoprocedimentiFrontofficeService _endoService { get; set; }

		public AlberoEndoJsService()
		{
			FoKernelContainer.Inject(this);
		}

		[WebMethod(EnableSession = true)]
		[ScriptMethod()]
		public object[] GetNodiFiglio(string aliasComune, string software, string idPadre, int idAteco, bool areaRiservata, bool utenteTester)
		{
			IAmbitoRicercaIntervento ambitoRicerca = areaRiservata ? (IAmbitoRicercaIntervento)new AmbitoRicercaAreaRiservata(utenteTester) : 
																	 (IAmbitoRicercaIntervento)new AmbitoRicercaFrontofficePubblico();

			if (idPadre == "-1")
			{
				return GetListaFamiglie(aliasComune, software);
			}

			if (idPadre[0].ToString() == Constants.PrefissoCodiceFamiglia)
			{
				return GetListaCategorie(aliasComune, software, idPadre.Substring(1));
			}

			if (idPadre[0].ToString() == Constants.PrefissoCodiceCategoria)
			{
				return GetListaEndo(aliasComune, software, idPadre.Substring(1));
			}

			throw new Exception("Tipo di ricerca endo non supportato " + idPadre);
		}

		public object[] GetListaFamiglie(string aliasComune, string software)
		{
			return _endoService
						.GetListaFamiglie(aliasComune, software)
						.Select(x => new { 
							Codice = Constants.PrefissoCodiceFamiglia + x.Codice.ToString(),
							Descrizione = x.Descrizione,
							HaNodiFiglio = true,
							HaNote = false							
						})
						.ToArray();
		}

		public object[] GetListaCategorie(string aliasComune, string software, string codiceFamiglia)
		{
			return _endoService
						.GetListaCategorie(aliasComune, software, Convert.ToInt32(codiceFamiglia))
						.Select(x => new
						{
							Codice = Constants.PrefissoCodiceCategoria + x.Codice.ToString(),
							Descrizione = x.Descrizione,
							HaNodiFiglio = true,
							HaNote = false
						})
						.ToArray();
		}

		public object[] GetListaEndo(string aliasComune, string software, string codiceCategoria)
		{
			return _endoService
						.GetListaEndo(aliasComune, software, Convert.ToInt32(codiceCategoria))
						.Select(x => new
						{
							Codice = x.Codice.ToString(),
							Descrizione = x.Descrizione,
							HaNodiFiglio = false,
							HaNote = false
						})
						.ToArray();
		}

		
		[WebMethod(EnableSession = true)]
		[ScriptMethod()]
		public BaseDtoOfStringString[] RicercaTestuale(string aliasComune, string software, string matchParziale, int matchCount, string tipoRicerca, bool areaRiservata, bool utenteTester)
		{
			if (matchParziale.Length < 2)
				return new BaseDtoOfStringString[0];

			var tipoRicercaEnum = (TipoRicercaEnum)Enum.Parse(typeof(TipoRicercaEnum), tipoRicerca);

			var result = _endoService.RicercaTestuale(aliasComune, software, matchParziale, tipoRicercaEnum);

			var map = new Func<BaseDtoOfStringString,string,string,BaseDtoOfStringString>(( dto, prefissoCodice,prefissoDescrizione )=>{
				return new BaseDtoOfStringString 
				{ 
					Codice = String.Format( "{0}{1}", prefissoCodice, dto.Codice),
					Descrizione = String.Format( "{0} - {1}" , prefissoDescrizione, dto.Descrizione)
				};
			});
			
			var famiglie	= result.Famiglie.Select(x => map( x , Constants.PrefissoCodiceFamiglia, Constants.PrefissoDescrizioneFamiglia));
			var categorie	= result.Categorie.Select(x => map(x, Constants.PrefissoCodiceCategoria, Constants.PrefissoDescrizioneCategoria));
			var endo		= result.Endoprocedimenti.Select(x => map(x, String.Empty, Constants.PrefissoDescrizioneEndo));

			return famiglie.Union(categorie).Union(endo).ToArray();
		}

		
		
		[WebMethod(EnableSession = true)]
		[ScriptMethod]
		public List<string> CaricaGerarchia(string aliasComune, string id)
		{
			var valore = -1;
			var livello = LivelloCaricamentoGerarchia.Endo;

			switch (id[0].ToString())
			{
				case Constants.PrefissoCodiceCategoria:
					{
						valore = Convert.ToInt32(id.Substring(1));
						livello = LivelloCaricamentoGerarchia.Categoria;
						break;
					}
				case Constants.PrefissoCodiceFamiglia:
					{
						valore = Convert.ToInt32(id.Substring(1));
						livello = LivelloCaricamentoGerarchia.Famiglia;
						break;
					}

				default:
					valore = Convert.ToInt32(id);
					break;
			}

			var result = _endoService.CaricaGerarchia(aliasComune, valore, livello );

			if (result == null)
				return null;

			switch (livello)
			{
				case LivelloCaricamentoGerarchia.Famiglia:
					return new List<string>
					{
						Constants.PrefissoCodiceFamiglia + result.Famiglia.ToString()
					};

				case LivelloCaricamentoGerarchia.Categoria:
					return new List<string>
					{
						Constants.PrefissoCodiceCategoria + result.Categoria.ToString(),
						Constants.PrefissoCodiceFamiglia + result.Famiglia.ToString()
					};

			}

			return new List<string>{
						result.Endo.ToString(),
						Constants.PrefissoCodiceCategoria + result.Categoria.ToString(),
						Constants.PrefissoCodiceFamiglia + result.Famiglia.ToString()				
					};
		}
	}
}
