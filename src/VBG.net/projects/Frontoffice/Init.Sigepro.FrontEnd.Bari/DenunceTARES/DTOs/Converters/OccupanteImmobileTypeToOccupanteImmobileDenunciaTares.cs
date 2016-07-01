// -----------------------------------------------------------------------
// <copyright file="OccupanteImmobileTypeToOccupanteImmobileDenunciaTares.cs" company="">
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

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class OccupanteImmobileTypeToOccupanteImmobileDenunciaTares: IMapTo<occupanteImmobileType, OccupanteImmobileDenunciaTares>
	{
		public OccupanteImmobileDenunciaTares Map(occupanteImmobileType src)
		{
			if (src == null)
			{
				return null;
			}

			var dst = new OccupanteImmobileDenunciaTares
			{
				Cognome = src.cognome,
				Nome = src.nome,
				DataNascita = new DataDto(src.dataNascita),
				CodiceFiscale = src.codiceFiscale,
				DataInizioOccupazione = new DataDto(src.dataInizioOccupazione),
				DataFineOccupazione = new DataDto(src.dataFineOccupazione)
			};

			return dst;
		}
	}
}
