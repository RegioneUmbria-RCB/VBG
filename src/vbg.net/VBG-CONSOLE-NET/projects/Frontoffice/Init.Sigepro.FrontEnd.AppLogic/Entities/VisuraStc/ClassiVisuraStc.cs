using System;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.StcService
{
	public partial class InterventoType
	{
		public override string ToString()
		{
			return this.descrizione;
		}
	}

	public partial class RichiedenteType
	{

		public override string ToString()
		{

			if (this.anagrafica == null)
				return String.Empty;

			return this.anagrafica.ToString() + (this.ruolo != null ? "<br /><i>In qualità di " + this.ruolo.ToString() + "</i>" : String.Empty);
		}
	}

	public partial class PersonaFisicaType
	{
		public override string ToString()
		{
			return this.cognome + " " + this.nome;
		}
	}

	public partial class PersonaGiuridicaType
	{
		public override string ToString()
		{
			return this.ragioneSociale;
		}
	}

	public partial class RuoloType
	{

		public override string ToString()
		{
			return this.ruolo;
		}
	}

	public partial class LocalizzazioneNelComuneType
	{
		public override string ToString()
		{
			var sb = new StringBuilder();

			sb.Append(this.denominazione);
			return sb.ToString();
		}

		public string GetRiferimentiCatastali(string formatString)
		{
			if (this.riferimentoCatastale == null)
				return String.Empty;

			var sb = new StringBuilder();

			foreach (var rc in this.riferimentoCatastale)
			{
				sb.AppendFormat(formatString, rc.tipoCatasto, rc.foglio, rc.particella, rc.sub);
			}

			return sb.ToString();
		}
	}


}
