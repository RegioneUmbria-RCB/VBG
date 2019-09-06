// -----------------------------------------------------------------------
// <copyright file="CondizioneWhereToNomiCampi.cs" company="">
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
    public class CondizioneWhereToNomiCampi
    {
        private static class Constants
        {
            public const string Regex = "{([a-zA-Z0-9_]+)}";
        }

        string _condizioneWhere;
        IEnumerable<string> _nomiCampi;

        public CondizioneWhereToNomiCampi(string condizioneWhere)
        {
            this._condizioneWhere = condizioneWhere;
            this._nomiCampi = Parse(this._condizioneWhere);
        }

        private IEnumerable<string> Parse(string condizioneWhere)
        {
            var results = new List<string>();
            var matches = Regex.Matches(condizioneWhere, Constants.Regex);

            foreach (var capture in matches.Cast<Match>())
            {
                var nomeCampo = capture.Groups[1].Captures[0].Value;
                results.Add(new ControlSafeNomeCampo(nomeCampo).ToString());
            }

            return results;
        }

        public IEnumerable<string> GetNomiCampi()
        {
            return this._nomiCampi;
        }

        internal string GetListaNomiCampi()
        {
            return String.Join(",", GetNomiCampi().ToArray());
        }
    }
}
