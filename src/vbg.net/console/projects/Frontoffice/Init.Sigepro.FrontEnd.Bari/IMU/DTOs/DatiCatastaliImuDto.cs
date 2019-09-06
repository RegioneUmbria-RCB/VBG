using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.Bari.Core.SharedDTOs;

namespace Init.Sigepro.FrontEnd.Bari.IMU.DTOs
{
	public class DatiCatastaliImuDto : DatiCatastaliDto
	{
		public static DatiCatastaliImuDto Vuoto()
		{
			return new DatiCatastaliImuDto(String.Empty, String.Empty, String.Empty, String.Empty);
		}

		[Obsolete("Utilizzare solo per la serializzazione")]
		public DatiCatastaliImuDto()
		{

		}

		public DatiCatastaliImuDto(string sezione, string foglio, string particella, string subalterno)
		{
			this.Sezione = sezione;
			this.Foglio = foglio;
			this.Particella = particella;
			this.Subalterno = subalterno;
		}

		public override string ToString()
		{
			return String.Format("Sez: {0}, Foglio: {1}, Particella: {2}, Subalterno: {3}", this.Sezione, this.Foglio, this.Particella, this.Subalterno);
		}
	}
}
