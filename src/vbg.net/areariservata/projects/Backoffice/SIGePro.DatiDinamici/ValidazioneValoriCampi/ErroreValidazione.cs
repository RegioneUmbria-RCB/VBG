using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.ValidazioneValoriCampi
{
	public class ErroreValidazione
	{
		public readonly string Messaggio;
		public readonly int Indice;
		public readonly int IdCampo;

		public ErroreValidazione(string messaggio)
			:this( messaggio,-1,-1)
		{
		}

		public ErroreValidazione(string messaggio, int idCampo, int indice)
		{
			this.Messaggio = messaggio;
			this.Indice = indice;
			this.IdCampo = idCampo;
		}

		public override bool Equals(Object obj)
		{
			return obj is ErroreValidazione && this == (ErroreValidazione)obj;
		}
		public override int GetHashCode()
		{
			return Messaggio.GetHashCode() ^ IdCampo.GetHashCode() ^ Indice.GetHashCode();
		}
		public static bool operator ==(ErroreValidazione x, ErroreValidazione y)
		{
			if ((object)x == null && (object)y == null)
				return true;

			if ((object)x != null && (object)y != null)
				return x.Messaggio == y.Messaggio && x.IdCampo == y.IdCampo && x.Indice == y.Indice;

			return false;
		}
		public static bool operator !=(ErroreValidazione x, ErroreValidazione y)
		{
			return !(x == y);
		}
	}
}
