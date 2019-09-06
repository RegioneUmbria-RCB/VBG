// -----------------------------------------------------------------------
// <copyright file="ComuniService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneComuni
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.Common;
	using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;


	public interface IComuniService
	{
		DatiProvinciaCompatto GetProvinciaDaCodiceComune(string codiceComune);
		string GetPecComuneAssociato(string software, string codiceComune);
		DatiComuneCompatto GetDatiComune(string codiceComune);
		IEnumerable<DatiComuneCompatto> FindComuneDaMatchParziale(string aliasComune, string matchComune);
		IEnumerable<DatiProvinciaCompatto> FindProvinciaDaMatchParziale(string aliasComune, string matchProvincia);
		DatiProvinciaCompatto GetDatiProvincia( string siglaProvincia);
		IEnumerable<DatiComuneCompatto> GetComuniAssociati();
		DatiComuneCompatto FindComuneDaNomeComune(string matchComune);
	}

	public class ComuniService : IComuniService
	{
		IComuniRepository _comuniRepository;
		IAliasResolver _aliasSoftwareResolver;

		public ComuniService(IAliasResolver aliasResolver, IComuniRepository comuniRepository)
		{
			this._comuniRepository = comuniRepository;
			this._aliasSoftwareResolver = aliasResolver;
		}



		#region IComuniService Members

		public DatiProvinciaCompatto GetProvinciaDaCodiceComune(string codiceComune)
		{
			return this._comuniRepository.GetProvinciaDaCodiceComune(this._aliasSoftwareResolver.AliasComune, codiceComune);
		}

		public string GetPecComuneAssociato(string software, string codiceComune)
		{
			return this._comuniRepository.GetPecComuneAssociato(this._aliasSoftwareResolver.AliasComune, codiceComune, software);
		}

		public DatiComuneCompatto GetDatiComune(string codiceComune)
		{
			return this._comuniRepository.GetDatiComune(this._aliasSoftwareResolver.AliasComune, codiceComune);
		}

		public IEnumerable<DatiComuneCompatto> FindComuneDaMatchParziale(string aliasComune, string matchComune)
		{
			return this._comuniRepository.FindComuneDaMatchParziale(aliasComune, matchComune);
		}

		public IEnumerable<DatiProvinciaCompatto> FindProvinciaDaMatchParziale(string aliasComune, string matchProvincia)
		{
			return this._comuniRepository.FindProvinciaDaMatchParziale(aliasComune, matchProvincia);
		}

		public DatiProvinciaCompatto GetDatiProvincia( string siglaProvincia)
		{
			return this._comuniRepository.GetDatiProvincia(this._aliasSoftwareResolver.AliasComune, siglaProvincia);
		}

		public IEnumerable<DatiComuneCompatto> GetComuniAssociati()
		{
			return this._comuniRepository.GetComuniAssociati(this._aliasSoftwareResolver.AliasComune);
		}

		#endregion


		public DatiComuneCompatto FindComuneDaNomeComune(string matchComune)
		{
			var results = this.FindComuneDaMatchParziale(this._aliasSoftwareResolver.AliasComune, matchComune);

			if (results == null || results.Count() == 0)
				return null;

			return results.Where(x => x.Comune.ToUpperInvariant() == matchComune.ToUpperInvariant()).FirstOrDefault();
		}
	}
}
