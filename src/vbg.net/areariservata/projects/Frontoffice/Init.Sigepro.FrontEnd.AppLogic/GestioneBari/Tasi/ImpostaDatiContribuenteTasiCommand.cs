// -----------------------------------------------------------------------
// <copyright file="ImpostaDatiContribuenteTasiCommand.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Tasi
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Core;
	using Init.Sigepro.FrontEnd.Bari.TASI.DTOs;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class ImpostaDatiContribuenteTasiCommand : ImpostaDatiImmobiliBaseCommand<DatiContribuenteTasiDto>
	{
		public ImpostaDatiContribuenteTasiCommand(int idDomanda, DatiContribuenteTasiDto datiContribuente, int idTipoSoggetto, IComuniService comuniService)
			:base(idDomanda, datiContribuente, idTipoSoggetto, comuniService)
		{

		}
	}
}
