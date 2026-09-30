using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
	public class ParametriMenu : IParametriConfigurazione
	{
		private static class Constants
		{
			public const string TitoloPaginaDefault = "Benvenuto nel sistema di presentazione on-line delle pratiche";
		}

		public readonly string TitoloPagina;
		public readonly string DescrizionePagina;
		public readonly IEnumerable<ElementoMenuNavigazione> VociMenu;

		internal ParametriMenu(string titoloPagina, string descrizionePagina, IEnumerable<ElementoMenuNavigazione> vociMenu)
		{
			this.TitoloPagina = titoloPagina;
			this.DescrizionePagina = descrizionePagina;

			if (String.IsNullOrEmpty(this.TitoloPagina))
				this.TitoloPagina = Constants.TitoloPaginaDefault;

			if (vociMenu == null)
				throw new ArgumentNullException("vociMenu");

			this.VociMenu = vociMenu;
		}

        public IEnumerable<ElementoMenuNavigazione> GetVociMenuUtente()
        {
            return this.VociMenu.Where(x => x.IsVoceMenuUtente);
        }

        public IEnumerable<ElementoMenuNavigazione> GetVociMenuGenerali()
        {
            return this.VociMenu.Where(x => !x.IsVoceMenuUtente);
        }
	}
}
