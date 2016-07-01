// -----------------------------------------------------------------------
// <copyright file="DatiRappresentanteTypeToRappresentanteDenunciaTares.cs" company="">
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
	using Init.Sigepro.FrontEnd.Bari.Core.SharedDTOs;
	using Init.Sigepro.FrontEnd.Bari.Core;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class DatiRappresentanteTypeToRappresentanteDenunciaTares: IMapTo<datiRappresentanteType,RappresentanteDenunciaTares>
	{
		IResolveComuneDaCodiceIstat _comuneResolver;

		public DatiRappresentanteTypeToRappresentanteDenunciaTares(IResolveComuneDaCodiceIstat comuneResolver)
		{
			this._comuneResolver = comuneResolver;
		}


		public RappresentanteDenunciaTares Map(datiRappresentanteType source)
		{
			if (source == null)
			{
				return null;
			}

			var dest = new RappresentanteDenunciaTares
			{
				Cognome = source.cognome,
				Nome = source.nome,
				Sesso = source.sesso == sessoType.M ? SessoEnum.Maschio : SessoEnum.Femmina,
				CodiceFiscale = source.codiceFiscale,
				Qualifica = source.qualifica,
				Pec = source.indirizzoPEC,
				Telefono = source.telefono,
				Fax = source.fax
			};

			if (!String.IsNullOrEmpty(source.comuneNascita))
			{
				dest.CodiceIstatComuneNascita = source.comuneNascita;
				dest.ComuneNascita = this._comuneResolver.GetComuneDaCodiceIstat(source.comuneNascita);
				dest.ProvinciaNascita = source.provinciaNascita;
			}
			else
			{
				dest.ComuneNascita = source.comuneEsteroNascita;
			}

			return dest;

		}
	}
}
