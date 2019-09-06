// -----------------------------------------------------------------------
// <copyright file="IdValoreCampo.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.DatiDinamici.VisibilitaCampi
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	public class IdValoreCampo
	{
		public int Id { get; set; }
		public int IndiceMolteplicita { get; set; }

		public IdValoreCampo()
		{
		}

		public IdValoreCampo(int idCampo, int indiceMolteplicita)
		{
			this.Id = idCampo;
			this.IndiceMolteplicita = indiceMolteplicita;
		}

		public override bool Equals(System.Object obj)
		{
			// If parameter is null return false.
			if (obj == null)
			{
				return false;
			}

			// If parameter cannot be cast to Point return false.
			IdValoreCampo p = obj as IdValoreCampo;
			if ((System.Object)p == null)
			{
				return false;
			}

			// Return true if the fields match:
			return (Id == p.Id) && (IndiceMolteplicita == p.IndiceMolteplicita);
		}

		public bool Equals(IdValoreCampo p)
		{
			// If parameter is null return false:
			if ((object)p == null)
			{
				return false;
			}

			// Return true if the fields match:
			return (Id == p.Id) && (IndiceMolteplicita == p.IndiceMolteplicita);
		}

		public override int GetHashCode()
		{
			return Id ^ IndiceMolteplicita;
		}
	}
}
