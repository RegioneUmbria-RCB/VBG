// -----------------------------------------------------------------------
// <copyright file="DatiCatastaliNewTypeToDatiCatastaliDenunciaTares.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.DenunceTARES.DTOs.Converters
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Bari.TARES.ServicesProxies;
	using Init.Sigepro.FrontEnd.Infrastructure.Mapping;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class DatiCatastaliNewTypeToDatiCatastaliDenunciaTares: IMapTo<datiCatastaliNewType, DatiCatastaliDenunciaTares>
	{
		public DatiCatastaliDenunciaTares Map(datiCatastaliNewType d)
		{
			if (d == null) return null;

			var sezione = d.sezione;
			var foglio = d.foglio;
			var particella = d.particella;
			var subalterno = d.subalterno;

			return new DatiCatastaliDenunciaTares(sezione, foglio, particella, subalterno);
		}
	}
}
