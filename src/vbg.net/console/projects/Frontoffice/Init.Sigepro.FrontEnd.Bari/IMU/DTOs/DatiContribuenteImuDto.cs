// -----------------------------------------------------------------------
// <copyright file="DatiContribuenteImuDto.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.IMU.DTOs
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class DatiContribuenteImuDto
	{
		public enum TipoPersonaEnum
		{
			Fisica,
			Giuridica
		}
		public enum SessoEnum
		{
			Maschio,
			Femmina
		}


		public TipoPersonaEnum TipoPersona {get; set;}
		public string Nome {get; set;}
		public string Cognome {get; set;}
		public string CodiceFiscale {get; set;}
		public SessoEnum? Sesso {get; set;}
		public DateTime? DataNascita {get; set;}
		public string CodiceIstatComuneNascita {get; set;}
        public string ComuneNascita { get; set; }
		public string ProvinciaNascita {get; set;}
		public IndirizzoImuDto Residenza {get; set;}
		public string IdContribuente {get; set;}
		// public string CodiceContribuente {get; set;}

		public List<ImmobileImuDto> ListaImmobili {get;set;}

		public DatiContribuenteImuDto()
		{
			this.ListaImmobili = new List<ImmobileImuDto>();
		}

		public void AggiungiImmobile(ImmobileImuDto immobile)
		{
			this.ListaImmobili.Add(immobile);
		}

		public void AggiungiListaImmobili(ImmobileImuDto[] immobili)
		{
			this.ListaImmobili.AddRange(immobili);
		}

		public string DatiNascita
		{
			get
			{
				var str = String.Format("Nato il {0:dd/MM/yyyy} a {1}", DataNascita, ComuneNascita);

                if (!String.IsNullOrEmpty(this.ProvinciaNascita))
                {
                    str += String.Format("({0})", this.ProvinciaNascita);
                }

				return str;
			}
		}
	}
}
