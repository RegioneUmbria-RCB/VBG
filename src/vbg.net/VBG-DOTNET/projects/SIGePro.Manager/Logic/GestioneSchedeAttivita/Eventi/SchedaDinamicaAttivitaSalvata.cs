// -----------------------------------------------------------------------
// <copyright file="SchedaDinamicaSalvata.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Manager.Logic.GestioneSchedeAttivita.Eventi
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
    using Vbg.EventBus.Abstractions;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class SchedaDinamicaAttivitaSalvata: IEvent
	{
		public readonly int IdAttivita;
		public readonly int IdScheda;

		public SchedaDinamicaAttivitaSalvata(int idAttivita, int idScheda)
		{
			this.IdAttivita = idAttivita;
            IdScheda = idScheda;
        }

	}
}
