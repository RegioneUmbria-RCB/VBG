// -----------------------------------------------------------------------
// <copyright file="ImpostaDatiImmobiliBaseCommand.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Core
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class ImpostaDatiImmobiliBaseCommand<T>
	{
		public readonly int IdDomanda;
		public readonly T DatiContribuente;
		public readonly int IdTipoSoggetto;
		public readonly IComuniService ComuniService;

		public ImpostaDatiImmobiliBaseCommand(int idDomanda, T datiContribuente, int idTipoSoggetto, IComuniService comuniService)
		{
			this.IdDomanda = idDomanda;
			this.DatiContribuente = datiContribuente;
			this.IdTipoSoggetto = idTipoSoggetto;
			this.ComuniService = comuniService;
		}
	}
}
