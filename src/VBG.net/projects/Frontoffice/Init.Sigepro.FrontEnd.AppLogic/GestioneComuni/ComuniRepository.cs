// -----------------------------------------------------------------------
// <copyright file="IComuniRepository.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneComuni
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
	using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
	using Init.Sigepro.FrontEnd.Infrastructure.Caching;
	using Init.Sigepro.FrontEnd.AppLogic.Common;
	using Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices;

	public interface IComuniRepository
	{
		DatiComuneCompatto[] FindComuneDaMatchParziale(string aliasComune, string matchComune);
		List<DatiProvinciaCompatto> FindProvinciaDaMatchParziale(string aliasComune, string matchProvincia);
		List<DatiComuneCompatto> GetComuniAssociati(string aliasComune);
		DatiComuneCompatto GetDatiComune(string aliasComune, string codiceComune);
		DatiProvinciaCompatto GetDatiProvincia(string aliasComune, string siglaProvincia);

		List<Cittadinanza> GetListaCittadinanze(string aliasComune);
		Cittadinanza GetCittadinanzaDaId(string IdComune, int idCittadinanza);

		List<DatiComuneCompatto> GetListaComuni(string aliasComune);
		List<DatiComuneCompatto> GetListaComuni(string aliasComune, string siglaProvincia);
		List<DatiProvinciaCompatto> GetListaProvincie(string aliasComune);
		string GetPecComuneAssociato(string aliasComune, string codiceComune, string software);
		DatiProvinciaCompatto GetProvinciaDaCodiceComune(string aliasComune, string codiceComune);
		bool IsCittadinanzaExtracomunitaria(string aliasComune, int idCittadinanza);

        DatiComuneCompatto GetComuneDaCodiceIstat(string p, string codiceIstat);
    }

	internal class WsComuniRepository : WsAreaRiservataRepositoryBase, IComuniRepository
	{
		const string CACHE_KEY_LISTA_COMUNI_PROVINCIA = "SESSION_KEY_LISTA_COMUNI_PROVINCIA_";
		const string CACHE_KEY_LISTA_PROVINCIE = "SESSION_KEY_LISTA_PROVINCIE";
		const string CACHE_KEY_LISTA_CITTADINANZE = "CACHE_KEY_LISTA_CITTADINANZE";
		const string CACHE_KEY_DATI_COMUNE = "CACHE_KEY_DATI_COMUNE_";
		const string CACHE_KEY_DATI_PROVINCIA = "CACHE_KEY_DATI_PROVINCIA_";
        const string CACHE_KEY_COMUNE_DA_COD_ISTAT = "COMUNE:COD:ISAT:";

		public WsComuniRepository(AreaRiservataServiceCreator sc)
			: base(sc)
		{

		}

		public DatiComuneCompatto GetDatiComune(string aliasComune, string codiceComune)
		{
			if (String.IsNullOrEmpty(codiceComune))
				return new DatiComuneCompatto();

			var cacheKey = CACHE_KEY_DATI_COMUNE + codiceComune;

			if (CacheHelper.KeyExists(cacheKey))
				return CacheHelper.GetEntry<DatiComuneCompatto>(cacheKey);

			using (var ws = _serviceCreator.CreateClient(aliasComune))
			{
				var rVal = ws.Service.GetDatiComune(ws.Token, codiceComune);

				if (rVal == null)
					return null;

				CacheHelper.AddEntry(cacheKey, rVal);

				return rVal;
			}
		}

		public List<DatiComuneCompatto> GetListaComuni(string aliasComune)
		{
            return GetListaComuni(aliasComune, String.Empty);
		}

		public List<DatiComuneCompatto> GetComuniAssociati(string aliasComune)
		{
			using (var ws = _serviceCreator.CreateClient(aliasComune))
			{
				return new List<DatiComuneCompatto>(ws.Service.GetComuniAssociati(ws.Token));
			}
		}

		public string GetPecComuneAssociato(string aliasComune, string codiceComune, string software)
		{
			using (var ws = _serviceCreator.CreateClient(aliasComune))
			{
				return ws.Service.GetPecComuneAssociato(ws.Token, codiceComune, software);
			}
		}
        
		public List<DatiComuneCompatto> GetListaComuni(string aliasComune, string siglaProvincia)
		{
			string cacheKey = CACHE_KEY_LISTA_COMUNI_PROVINCIA + siglaProvincia;

			if (!CacheHelper.KeyExists(cacheKey))
			{
				using (var ws = _serviceCreator.CreateClient(aliasComune))
				{
					var value = new List<DatiComuneCompatto>(ws.Service.GetListaComuni(ws.Token, siglaProvincia));

					CacheHelper.AddEntry(cacheKey, value);
				}
			}

			return CacheHelper.GetEntry<List<DatiComuneCompatto>>(cacheKey);
		}

		public DatiComuneCompatto[] FindComuneDaMatchParziale(string aliasComune, string matchComune)
		{
			using (var ws = _serviceCreator.CreateClient(aliasComune))
			{
				var value = ws.Service.FindComuniDaMatchParziale(ws.Token, matchComune);

				return value;
			}
		}

		public DatiProvinciaCompatto GetProvinciaDaCodiceComune(string aliasComune, string codiceComune)
		{
			if (codiceComune == null)
				return null;

			using (var ws = _serviceCreator.CreateClient(aliasComune))
			{
				return ws.Service.GetDatiProvinciaDaCodiceComune(ws.Token, codiceComune);
			}
		}

		public DatiProvinciaCompatto GetDatiProvincia(string aliasComune, string siglaProvincia)
		{
			if (siglaProvincia == null)
				return null;

			var cacheKey = CACHE_KEY_DATI_PROVINCIA + siglaProvincia;

			if (CacheHelper.KeyExists(cacheKey))
				return CacheHelper.GetEntry<DatiProvinciaCompatto>(cacheKey);


			using (var ws = _serviceCreator.CreateClient(aliasComune))
			{
				var rVal = ws.Service.GetDatiProvincia(ws.Token, siglaProvincia);

				if (rVal == null) // Provincia non trovata
					return null;

				CacheHelper.AddEntry(cacheKey, rVal);

				return rVal;
			}
		}

		public List<DatiProvinciaCompatto> GetListaProvincie(string aliasComune)
		{
			if (!CacheHelper.KeyExists(CACHE_KEY_LISTA_PROVINCIE))
			{
				using (var ws = _serviceCreator.CreateClient(aliasComune))
				{
					var value = new List<DatiProvinciaCompatto>(ws.Service.GetListaProvincie(ws.Token));

					CacheHelper.AddEntry(CACHE_KEY_LISTA_PROVINCIE, value);
				}
			}

			return CacheHelper.GetEntry<List<DatiProvinciaCompatto>>(CACHE_KEY_LISTA_PROVINCIE);
		}

		public List<DatiProvinciaCompatto> FindProvinciaDaMatchParziale(string aliasComune, string matchProvincia)
		{
			matchProvincia = matchProvincia.ToUpper();

			var listaProvince = GetListaProvincie(aliasComune);

			var res = from DatiProvinciaCompatto p in listaProvince
					  where p.Provincia.ToUpper().StartsWith(matchProvincia)
					  select p;

			return res.ToList();
		}


		public List<Cittadinanza> GetListaCittadinanze(string aliasComune)
		{

			if (!CacheHelper.KeyExists(CACHE_KEY_LISTA_CITTADINANZE))
			{

				using (var ws = _serviceCreator.CreateClient(aliasComune))
				{
					var value = new List<Cittadinanza>(ws.Service.GetCittadinanze(ws.Token));

					CacheHelper.AddEntry(CACHE_KEY_LISTA_CITTADINANZE, value);
				}
			}

			return CacheHelper.GetEntry<List<Cittadinanza>>(CACHE_KEY_LISTA_CITTADINANZE);
		}

		public Cittadinanza GetCittadinanzaDaId(string aliasComune, int idCittadinanza)
		{
			return GetListaCittadinanze(aliasComune).Where(x => x.Codice.Value == idCittadinanza)
													.FirstOrDefault();
		}

		public bool IsCittadinanzaExtracomunitaria(string aliasComune, int idCittadinanza)
		{
			var cittadinanza = GetListaCittadinanze(aliasComune).Where(x => x.Codice.Value == idCittadinanza)
																.FirstOrDefault();

			if (cittadinanza == null)
				throw new InvalidOperationException("La cittadinanza con codice " + idCittadinanza + " non è stata trovata");

			return cittadinanza.FlgPaeseComunitario.GetValueOrDefault(0) == 0;
		}


        public DatiComuneCompatto GetComuneDaCodiceIstat(string aliasComune, string codiceIstat)
        {
            string cacheKey = CACHE_KEY_COMUNE_DA_COD_ISTAT + codiceIstat;

            if (!CacheHelper.KeyExists(cacheKey))
            {
                using (var ws = _serviceCreator.CreateClient(aliasComune))
                {
                    var value = ws.Service.GetComuneDaCodiceIstat(ws.Token, codiceIstat);

                    if(value == null)
                    {
                        return null;
                    }

                    CacheHelper.AddEntry(cacheKey, value);
                }
            }

            return CacheHelper.GetEntry<DatiComuneCompatto>(cacheKey);
        }
    }
}
