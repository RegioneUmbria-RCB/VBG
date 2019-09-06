using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.QuestioFlorentia
{
	public class RiferimentoCatastaleParsato
	{
		public readonly string Foglio;
		public readonly string Particella;

		internal RiferimentoCatastaleParsato(string foglio, string particella)
		{
			this.Foglio = foglio.TrimStart('0');
			this.Particella = particella.TrimStart('0');

		}
	}
}
