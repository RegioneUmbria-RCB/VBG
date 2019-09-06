// -----------------------------------------------------------------------
// <copyright file="DatiCatastaliTypeToDatiCatastaliDto.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.IMU.DTOs.Converters
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Bari.IMU.wsdl;
	using Init.Sigepro.FrontEnd.Infrastructure.Mapping;

	public class DatiCatastaliTypeToDatiCatastaliImuDto : IMapTo<datiCatastaliType, DatiCatastaliImuDto>
	{
		public DatiCatastaliImuDto Map(datiCatastaliType d)
		{
			if (d == null) return null;

			var sezione = d.sezione;
			var foglio = d.foglio;
			var particella = d.particella;
			var subalterno = d.subalterno;

			return new DatiCatastaliImuDto(sezione, foglio, particella, subalterno);
		}
	}
}
