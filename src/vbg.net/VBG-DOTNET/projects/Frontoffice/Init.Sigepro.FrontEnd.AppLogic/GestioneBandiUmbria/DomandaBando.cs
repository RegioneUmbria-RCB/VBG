// -----------------------------------------------------------------------
// <copyright file="DomandaBando.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class DomandaBando
	{
		public AllegatoDomandaBandi Allegato1 { get; set; }
		public AllegatoDomandaBandi Allegato2 { get; set; }
		public AllegatoDomandaBandi Allegato3 { get; set; }
		public AllegatoDomandaBandi Allegato4 { get; set; }
		public AllegatoDomandaBandi AllegatoAltreSediOperative { get; set; }
		public List<AziendaBando> Aziende { get; set; }

		public static DomandaBando A1(int idModelloAllegato1, int idModelloAllegato2, int idModelloAllegato7, int idModelloAltreSediOperative, IEnumerable<AnagraficaDomanda> aziende)
		{
			var domanda = new DomandaBando()
			{
				Allegato1 = new AllegatoDomandaBandi(CategoriaAllegatoBandoEnum.Allegato1, idModelloAllegato1),
				Allegato2 = new AllegatoDomandaBandi(CategoriaAllegatoBandoEnum.Allegato2, idModelloAllegato2),
				AllegatoAltreSediOperative = new AllegatoDomandaBandi(CategoriaAllegatoBandoEnum.AllegatoAltreSediOperative, idModelloAltreSediOperative),
				Aziende = aziende.Select(x => AziendaBando.AziendaBandoA1(x.Nominativo, x.Codicefiscale, x.PartitaIva, idModelloAllegato7)).ToList()
			};

			return domanda;
		}

		public static DomandaBando B1(int idModelloAllegato3, int idModelloAllegato4, int idModelloAllegato10, int idModelloAltreSediOperative, AnagraficaDomanda azienda)
		{
			var domanda = new DomandaBando()
			{
				Allegato3 = new AllegatoDomandaBandi(CategoriaAllegatoBandoEnum.Allegato3, idModelloAllegato3),
				Allegato4 = new AllegatoDomandaBandi(CategoriaAllegatoBandoEnum.Allegato4, idModelloAllegato4),
				AllegatoAltreSediOperative = new AllegatoDomandaBandi(CategoriaAllegatoBandoEnum.AllegatoAltreSediOperative, idModelloAltreSediOperative),
				Aziende = new[] { azienda }.Select(x => AziendaBando.AziendaBandoB1(x.Nominativo, x.Codicefiscale, x.PartitaIva, idModelloAllegato10)).ToList()
			};

			return domanda;
		}

		
		public DomandaBando()
		{

		}
		
		internal AllegatoDomandaBandi TrovaAllegato(string idAllegato)
		{
			var allegati = new[]
			{
				this.Allegato1,
				this.Allegato2,
				this.Allegato3,
				this.Allegato4,
				this.AllegatoAltreSediOperative
			};

			foreach (var allegato in allegati)
			{
				if (allegato != null && allegato.Id == idAllegato)
				{
					return allegato;
				}
			}

			foreach (var azienda in this.Aziende)
			{
				foreach (var allegato in azienda.Allegati)
				{
					if (allegato.Id == idAllegato)
						return allegato;
				}
			}

			return null;
		}

        internal static DomandaBando Incoming(int idModelloAllegato1, int idModelloAllegato2, int idModelloAllegato7, int idModelloAllegatoAltreSedi, IEnumerable<AnagraficaDomanda> aziende)
        {
            var domanda = new DomandaBando()
            {
                Allegato1 = new AllegatoDomandaBandi(CategoriaAllegatoBandoEnum.IncomingAllegato1, idModelloAllegato1),
                Allegato2 = new AllegatoDomandaBandi(CategoriaAllegatoBandoEnum.IncomingAllegato2, idModelloAllegato2),
                //AllegatoAltreSediOperative = new AllegatoDomandaBandi(CategoriaAllegatoBandoEnum.AllegatoAltreSediOperative, idModelloAllegatoAltreSedi),
                Aziende = aziende.Select(x => AziendaBando.AziendaBandoIncoming(x.Nominativo, x.Codicefiscale, x.PartitaIva, idModelloAllegato7)).ToList()
            };

            return domanda;
        }
    }
}
