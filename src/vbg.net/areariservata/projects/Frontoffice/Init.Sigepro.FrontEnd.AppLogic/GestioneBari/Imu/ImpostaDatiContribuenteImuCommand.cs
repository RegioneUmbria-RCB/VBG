// -----------------------------------------------------------------------
// <copyright file="ImpostaDatiContribuenteImuCommand.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Imu
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using Init.Sigepro.FrontEnd.Bari.IMU.DTOs;
using Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Core;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;

	public class ImpostaDatiContribuenteImuCommand : ImpostaDatiImmobiliBaseCommand<DatiContribuenteImuDto>
	{
		public ImpostaDatiContribuenteImuCommand(int idDomanda, DatiContribuenteImuDto datiContribuente, int idTipoSoggetto, IComuniService comuniService)
			: base(idDomanda, datiContribuente, idTipoSoggetto, comuniService)
		{

		}
	}
}
