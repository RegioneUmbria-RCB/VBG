// -----------------------------------------------------------------------
// <copyright file="AziendaBando.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class AziendaBando
	{
		public string RagioneSociale { get; set; }
		public string CodiceFiscale{get;set;}
		public string PartitaIva { get; set; }

		public List<AllegatoDomandaBandi> Allegati
		{
			set;
			get;
		}

        public AllegatoDomandaBandi Allegato3Incoming
        {
            get
            {
                return this.Allegati.Where(x => x.Categoria == CategoriaAllegatoBandoEnum.IncomingAllegato3).FirstOrDefault();
            }
        }

		public AziendaBando()
		{
			this.Allegati = new List<AllegatoDomandaBandi>();
		}

		public static AziendaBando AziendaBandoA1(string ragioneSociale, string codiceFiscale, string partitaIva, int idModelloAllegato7)
		{
			return new AziendaBando
			{
				RagioneSociale = ragioneSociale,
				CodiceFiscale = codiceFiscale,
				PartitaIva = partitaIva,

				Allegati = new List<AllegatoDomandaBandi>()
				{
					new AllegatoDomandaBandi(CategoriaAllegatoBandoEnum.Allegato7, ragioneSociale, idModelloAllegato7), 
					new AllegatoDomandaBandi(CategoriaAllegatoBandoEnum.DocumentoLegaleRappresentante, ragioneSociale),
					new AllegatoDomandaBandi(CategoriaAllegatoBandoEnum.Procura, ragioneSociale),
					new AllegatoDomandaBandi(CategoriaAllegatoBandoEnum.ProvvedimentoAbilitativo, ragioneSociale),
					new AllegatoDomandaBandi(CategoriaAllegatoBandoEnum.TitoloDiProprieta, ragioneSociale),
					new AllegatoDomandaBandi(CategoriaAllegatoBandoEnum.ComputoMetrico, ragioneSociale)					
				}
			};
		}

		public static AziendaBando AziendaBandoB1(string ragioneSociale, string codiceFiscale, string partitaIva, int idModelloAllegato10)
		{
			return new AziendaBando
			{
				RagioneSociale = ragioneSociale,
				CodiceFiscale = codiceFiscale,
				PartitaIva = partitaIva,
				Allegati = new List<AllegatoDomandaBandi>()
				{
					new AllegatoDomandaBandi(CategoriaAllegatoBandoEnum.Allegato10, ragioneSociale, idModelloAllegato10),
					new AllegatoDomandaBandi(CategoriaAllegatoBandoEnum.DocumentoLegaleRappresentante, ragioneSociale),
					new AllegatoDomandaBandi(CategoriaAllegatoBandoEnum.Procura, ragioneSociale),
					new AllegatoDomandaBandi(CategoriaAllegatoBandoEnum.ProvvedimentoAbilitativo, ragioneSociale),
					new AllegatoDomandaBandi(CategoriaAllegatoBandoEnum.TitoloDiProprieta, ragioneSociale),
					new AllegatoDomandaBandi(CategoriaAllegatoBandoEnum.ComputoMetrico, ragioneSociale)					
				}
			};
		}
		
		public override string ToString()
		{
			var sb = new StringBuilder(this.RagioneSociale);

			if (String.IsNullOrEmpty(this.CodiceFiscale) && String.IsNullOrEmpty(this.PartitaIva))
				return sb.ToString();

			sb.Append("[");

			if (!String.IsNullOrEmpty(this.CodiceFiscale))
			{
				sb.AppendFormat("CF: {0}", this.CodiceFiscale);
			}

			if (!String.IsNullOrEmpty(this.PartitaIva))
			{
				if (!String.IsNullOrEmpty(this.CodiceFiscale))
					sb.Append(", ");

				sb.AppendFormat("P.IVA: {0}", this.PartitaIva);
			}

			sb.Append("]");

			return sb.ToString();
		}

        internal static AziendaBando AziendaBandoIncoming(string ragioneSociale, string codiceFiscale, string partitaIva, int idModelloAllegato3)
        {
            return new AziendaBando
            {
                RagioneSociale = ragioneSociale,
                CodiceFiscale = codiceFiscale,
                PartitaIva = partitaIva,
                Allegati = new List<AllegatoDomandaBandi>()
				{
					new AllegatoDomandaBandi(CategoriaAllegatoBandoEnum.IncomingAllegato3, ragioneSociale, idModelloAllegato3),
					new AllegatoDomandaBandi(CategoriaAllegatoBandoEnum.DocumentoLegaleRappresentante, ragioneSociale),
					new AllegatoDomandaBandi(CategoriaAllegatoBandoEnum.Procura, ragioneSociale)/*,
					new AllegatoDomandaBandi(CategoriaAllegatoBandoEnum.ProvvedimentoAbilitativo, ragioneSociale),
					new AllegatoDomandaBandi(CategoriaAllegatoBandoEnum.TitoloDiProprieta, ragioneSociale),
					new AllegatoDomandaBandi(CategoriaAllegatoBandoEnum.ComputoMetrico, ragioneSociale)				*/	
				}
            };
        }
    }
}
