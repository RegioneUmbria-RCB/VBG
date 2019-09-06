// -----------------------------------------------------------------------
// <copyright file="DatiAnagraficiContribuenteDenunciaTares.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.DenunceTARES.DTOs
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Bari.Core.SharedDTOs;
	using System.Xml.Serialization;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class DatiAnagraficiContribuenteDenunciaTares : PersonaDenunciaTares
	{
		public int? IdContribuente {get; set;}
		public string PartitaIva { get; set; }
		public long NumeroREA { get; set; }
		public string ProvinciaREA { get; set; }
		public RappresentanteDenunciaTares LegaleRappresentante { get; set; }
		public IndirizzoDenunciaTares IndirizzoResidenza { get; set; }
		public IndirizzoDenunciaTares IndirizzoCorrispondenza { get; set; }

		public List<UtenzaCommercialeDenunciaTaresDto> UtenzeCommerciali {get;set;}
		public List<UtenzaDomesticaDenunciaTaresDto> UtenzeDomestiche { get; set; }

		public DatiAnagraficiContribuenteDenunciaTares()
		{
			this.CodiceFiscale = String.Empty;
			this.PartitaIva = String.Empty;
			this.UtenzeCommerciali = new List<UtenzaCommercialeDenunciaTaresDto>();
			this.UtenzeDomestiche = new List<UtenzaDomesticaDenunciaTaresDto>();
			this.IndirizzoResidenza = IndirizzoDenunciaTares.Vuoto();
		}

		public void AggiungiUtenzaCommerciale(UtenzaCommercialeDenunciaTaresDto utenza)
		{
			this.UtenzeCommerciali.Add(utenza);
		}

		public void AggiungiUtenzeCommerciali(IEnumerable<UtenzaCommercialeDenunciaTaresDto> utenze)
		{
			this.UtenzeCommerciali.AddRange(utenze);
		}

		public void AggiungiUtenzaCommerciale(UtenzaDomesticaDenunciaTaresDto utenza)
		{
			this.UtenzeDomestiche.Add(utenza);
		}

		public void AggiungiUtenzeDomestiche(IEnumerable<UtenzaDomesticaDenunciaTaresDto> utenze)
		{
			this.UtenzeDomestiche.AddRange(utenze);
		}

		public string DatiNascita
		{
			get
			{
				if (!this.DataNascita.HasValue)
				{
					return String.Empty;
				}

				var str = String.Format("Nato il {0:dd/MM/yyyy} a {1}", DataNascita, ComuneNascita);

				if (!String.IsNullOrEmpty(this.ProvinciaNascita))
				{
					str += String.Format("({0})", this.ProvinciaNascita);
				}

				return str;
			}
		}


		[XmlIgnore]
		public string NominativoCompleto 
		{
			get
			{
				var sb = new StringBuilder();

				if (!String.IsNullOrEmpty(this.Nome))
				{
					sb.Append(this.Nome);
					sb.Append(" ");
				}

				sb.Append(this.Cognome);

				return sb.ToString();
			}
		}

		public static DatiAnagraficiContribuenteDenunciaTares Nuovo()
		{
			var utenza = new DatiAnagraficiContribuenteDenunciaTares();

			utenza.Nome = "Nuova Utenza";

			return utenza;

		}

        public void MantieniUtenze(IEnumerable<string> idUtenze)
        {
            var nuoveUtenzeDomestiche = this.UtenzeDomestiche.Where(x => idUtenze.Contains(x.Id)).ToList();
            var nuoveUtenzeNonDomestiche = this.UtenzeCommerciali.Where(x => idUtenze.Contains(x.Id)).ToList();

            this.UtenzeDomestiche = nuoveUtenzeDomestiche;
            this.UtenzeCommerciali = nuoveUtenzeNonDomestiche;
        }
    }
}
