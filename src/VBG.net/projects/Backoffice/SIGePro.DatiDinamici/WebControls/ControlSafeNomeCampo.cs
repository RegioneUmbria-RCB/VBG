// -----------------------------------------------------------------------
// <copyright file="ControlSafeNomeCampo.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.DatiDinamici.WebControls
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using System.Text.RegularExpressions;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    internal class ControlSafeNomeCampo
    {
        public static class Constants
        {
            public const string RegExMatch = "[^A-Za-z0-9]";
        }

        string _nome;
        string _nomeSafe;

        public ControlSafeNomeCampo(string nome)
        {
            this._nome = nome;
            this._nomeSafe = Regex.Replace(nome, Constants.RegExMatch, "_");
            this._nomeSafe = this._nomeSafe.ToUpperInvariant();
        }

        public override string ToString()
        {
            return this._nomeSafe;
        }
    }
}
