using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;

namespace Init.SIGePro.DatiDinamici.GestioneLocalizzazioni.StringaFormattazioneIndirizzi
{
	public class LocalizzazioneFormatProvider
	{
		private static class Constants
		{
			public const string DefaultFormatString = "";
		}

		private LocalizzazioneIstanza _localizzazioneIstanza;
		private ValueFormatter[] formatters;

		public LocalizzazioneFormatProvider(LocalizzazioneIstanza localizzazioneIstanza)
		{
			this._localizzazioneIstanza = localizzazioneIstanza;

			this.formatters = new ValueFormatter[]{
				new ValueFormatter( "indirizzo",() => localizzazioneIstanza.Indirizzo),			
				new ValueFormatter( "civico",() => localizzazioneIstanza.Civico),			
				new ValueFormatter( "esponente",() => localizzazioneIstanza.Esponente),
				new ValueFormatter( "scala",() => localizzazioneIstanza.Scala),
				new ValueFormatter( "piano",() => localizzazioneIstanza.Piano),
				new ValueFormatter( "interno",() => localizzazioneIstanza.Interno),
				new ValueFormatter( "esponenteinterno",() => localizzazioneIstanza.EsponenteInterno),
				new ValueFormatter( "km",() => localizzazioneIstanza.Km),
				new ValueFormatter( "tipo",() => localizzazioneIstanza.TipoLocalizzazione),
				new ValueFormatter( "coordinate",() => localizzazioneIstanza.Coordinate == null ? String.Empty : localizzazioneIstanza.Coordinate.ToString() ),
				new ValueFormatter( "mappali",() => localizzazioneIstanza.Mappali == null ? String.Empty : localizzazioneIstanza.Mappali.ToString() ),
				new ValueFormatter( "note",() => localizzazioneIstanza.Note == null ? String.Empty : localizzazioneIstanza.Note.ToString() ),
			};

		}

		internal string Format(string espressioneFormattazione)
		{
			for (int i = 0; i < this.formatters.Length; i++)
			{
				espressioneFormattazione = formatters[i].ApplyTo(espressioneFormattazione);
			}

			espressioneFormattazione = Regex.Replace( espressioneFormattazione, "\\s+", " " );

			return espressioneFormattazione.Trim();
		}
	}

	internal class ValueFormatter
	{
		string _nomeSegnaposto;
		Func<string> _selector;

		public ValueFormatter(string nomeSegnaposto, Func<string> selector)
		{
			this._nomeSegnaposto = "{" + nomeSegnaposto + "}";
			this._selector = selector;
		}

		public string ApplyTo(string stringa)
		{
			return ReplaceInsensitive(stringa, this._nomeSegnaposto, this._selector(), StringComparison.InvariantCultureIgnoreCase);
		}

		public static string ReplaceInsensitive(string str, string oldValue, string newValue, StringComparison comparison)
		{
			StringBuilder sb = new StringBuilder();

			int previousIndex = 0;
			int index = str.IndexOf(oldValue, comparison);
			while (index != -1)
			{
				sb.Append(str.Substring(previousIndex, index - previousIndex));
				sb.Append(newValue);
				index += oldValue.Length;

				previousIndex = index;
				index = str.IndexOf(oldValue, index, comparison);
			}
			sb.Append(str.Substring(previousIndex));

			return sb.ToString();
		}
	}
}
