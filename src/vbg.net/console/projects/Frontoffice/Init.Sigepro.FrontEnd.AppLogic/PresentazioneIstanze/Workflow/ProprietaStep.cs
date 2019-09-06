using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow
{
	public class ProprietaStep
	{
		public string Nome { get; private set; }
		public string Valore { get; private set; }

		public ProprietaStep(string nome, string valore)
		{
			this.Nome = nome;
			this.Valore = valore;
		}
	}
}
