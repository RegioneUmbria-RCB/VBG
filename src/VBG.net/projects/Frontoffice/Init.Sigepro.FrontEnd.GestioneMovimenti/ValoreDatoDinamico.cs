using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.Infrastructure.ModelBase;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti
{
	public class ValoreDatoDinamico : ValueObject
	{
		public string Valore { get; private set; }
		public string ValoreDecodificato { get; private set; }

		internal ValoreDatoDinamico(string valore, string valoreDecodificato)
		{
			this.Valore = valore;
			this.ValoreDecodificato = valoreDecodificato;
		}

		public readonly static ValoreDatoDinamico Empty = new ValoreDatoDinamico(String.Empty, String.Empty);


		public override bool Equals(object obj)
		{

			if (obj == null) return false;
			if (this.GetType() != obj.GetType()) return false;

			ValoreDatoDinamico other = obj as ValoreDatoDinamico;

			return other.Valore == this.Valore && other.ValoreDecodificato == ValoreDecodificato;
		}

		public override int GetHashCode()
		{

			return Valore.GetHashCode() | ValoreDecodificato.GetHashCode();
		}

		public static Boolean operator ==(ValoreDatoDinamico v1, ValoreDatoDinamico v2)
		{

			if ((object)v1 == null)
				if ((object)v2 == null)
					return true;
				else
					return false;

			return (v1.Equals(v2));
		}

		public static Boolean operator !=(ValoreDatoDinamico v1, ValoreDatoDinamico v2)
		{

			return !(v1 == v2);
		}
	}
}
