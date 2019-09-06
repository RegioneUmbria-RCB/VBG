// -----------------------------------------------------------------------
// <copyright file="DatiContribuenteDtoExtensions.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Tares
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Bari.TARES.DTOs;
	using Init.Utils;
	using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;


	internal static class DatiContribuenteDtoExtensions
	{
		internal static string ToXmlString(this DatiContribuenteDto datiContribuente)
		{
			return StreamUtils.SerializeClass(datiContribuente);
		}

		internal static DatiComuneCompatto GetDatiComuneNascita(this DatiContribuenteDto datiContribuente, IComuniService comuniService)
		{
			var nomeComune = datiContribuente.DatiAnagraficiContribuente.ComuneNascita;

			if (String.IsNullOrEmpty(nomeComune))
				nomeComune = datiContribuente.DatiAnagraficiContribuente.ComuneEsteroNascita;

			if (String.IsNullOrEmpty(nomeComune))
				return null;

			return datiContribuente.GetDatiComune(comuniService, nomeComune);
		}

		private static DatiComuneCompatto GetDatiComune(this DatiContribuenteDto datiContribuente, IComuniService comuniService, string nomeComune)
		{
			return comuniService.FindComuneDaNomeComune(nomeComune);
		}

		internal static DatiComuneCompatto GetDatiComuneResidenza(this DatiContribuenteDto datiContribuente, IComuniService comuniService)
		{
			var nomeComune = datiContribuente.DatiResidenzaContribuente.Comune;

			return datiContribuente.GetDatiComune(comuniService, nomeComune);
		}
	}
}
