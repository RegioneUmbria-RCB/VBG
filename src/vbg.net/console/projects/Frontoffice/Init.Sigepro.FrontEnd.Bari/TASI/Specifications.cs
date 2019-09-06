// -----------------------------------------------------------------------
// <copyright file="Specifications.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.TASI
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Bari.TASI.DTOs;
	using Init.Sigepro.FrontEnd.Infrastructure;

	public class AlmenoUnaAbitazioneSelezionataSpecification : ISpecification<IEnumerable<ImmobileTasiDto>>
	{
		public bool IsSatisfiedBy(IEnumerable<ImmobileTasiDto> item)
		{
			var abitazione = item.Where(x => x.RiferimentiCatastali.CategoriaCatastale.StartsWith("A")).FirstOrDefault();

			return abitazione != null;
		}
	}

	public class SoloUnaAbitazioneSelezionataSpecification : ISpecification<IEnumerable<ImmobileTasiDto>>
	{
		public bool IsSatisfiedBy(IEnumerable<ImmobileTasiDto> item)
		{
			var abitazioni = item.Where(x => x.RiferimentiCatastali.CategoriaCatastale.StartsWith("A"));

			return abitazioni.Count() <= 1;
		}
	}

	public class SelezionateMaxTrePertinenzeSpecification : ISpecification<IEnumerable<ImmobileTasiDto>>
	{
		public bool IsSatisfiedBy(IEnumerable<ImmobileTasiDto> item)
		{
			var pertinenze = item.Where(x => x.RiferimentiCatastali.CategoriaCatastale.StartsWith("C"));

			return pertinenze.Count() <= 3;
		}
	}

	public class UnaSolaPertinenzaPerCiascunaCategoriaSpecification : ISpecification<IEnumerable<ImmobileTasiDto>>
	{
		public delegate void ErroreSuCategoriaCatastaleDelegate(object sender, string categoria);
		public event ErroreSuCategoriaCatastaleDelegate ErroreSuCategoriaCatastale;

		public bool IsSatisfiedBy(IEnumerable<ImmobileTasiDto> item)
		{
			var pertinenze = item.Where(x => x.RiferimentiCatastali.CategoriaCatastale.StartsWith("C"));
			var groups = pertinenze.GroupBy(x => x.RiferimentiCatastali.CategoriaCatastale);

			var success = true;

			foreach(var g in groups)
			{
				if (g.Count() > 1)
				{
					success = false;

					if (ErroreSuCategoriaCatastale != null)
						ErroreSuCategoriaCatastale(this, g.Key);
				}
			}

			return success;
		}
	}


}
