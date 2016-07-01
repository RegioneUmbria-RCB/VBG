// -----------------------------------------------------------------------
// <copyright file="DatiAnagraficiContribuente.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.TASI.DTOs
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class DatiContribuenteTasiDto
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
		public IndirizzoTasiDto Residenza {get; set;}
		public string IdContribuente {get; set;}
		public string CodiceContribuente {get; set;}

		public List<ImmobileTasiDto> ListaImmobili {get;set;}

		public DatiContribuenteTasiDto()
		{
			this.ListaImmobili = new List<ImmobileTasiDto>();
		}

		public void AggiungiImmobile(ImmobileTasiDto immobile)
		{
			this.ListaImmobili.Add(immobile);
		}

		public void AggiungiListaImmobili(ImmobileTasiDto[] immobili)
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
