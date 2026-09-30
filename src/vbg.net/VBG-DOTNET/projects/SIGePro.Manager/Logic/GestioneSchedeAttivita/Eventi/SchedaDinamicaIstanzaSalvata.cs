// -----------------------------------------------------------------------
// <copyright file="SchedaDinamicaIstanzaSalvata.cs" company="">
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
    public class SchedaDinamicaIstanzaSalvata : IEvent
    {
        // public readonly int IdAttivita;
        public readonly int CodiceIstanza;
        public readonly int IdScheda;

        public SchedaDinamicaIstanzaSalvata(/*int idAttivita,*/ int codiceIstanza, int idScheda)
        {
            // this.IdAttivita = idAttivita;
            this.CodiceIstanza = codiceIstanza;
            this.IdScheda = idScheda;
        }

        public string ToLogFormat()
        {
            return $" this.CodiceIstanza = {this.CodiceIstanza}, this.IdScheda = {this.IdScheda}";
        }
    }
}
