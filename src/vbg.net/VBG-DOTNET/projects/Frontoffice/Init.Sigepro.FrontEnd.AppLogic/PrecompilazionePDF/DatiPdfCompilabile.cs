// -----------------------------------------------------------------------
// <copyright file="DatiPdfCompilabile.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.PrecompilazionePDF
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using Init.Sigepro.FrontEnd.AppLogic.ServizioPrecompilazionePDF;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class DatiPdfCompilabile
    {
        Dictionary<string, string[]> _datiPdf;
        public readonly string NomeFile;

        public DatiPdfCompilabile(IEnumerable<DatiPDFType> datiPdf, string nomeFile)
        {
            this._datiPdf = datiPdf.ToDictionary(key => key.codice, value => value.valore);
            this.NomeFile = nomeFile;
        }

        /// <summary>
        /// Restituisce la lista di valori collegati adlla chiave passata
        /// </summary>
        /// <param name="chiave"></param>
        /// <returns></returns>
        public string[] Valori(string chiave)
        {
            var result = new string[0];

            if (this._datiPdf.TryGetValue(chiave, out result))
                return result;

            return null;
        }

        /// <summary>
        /// Restituisce la lista di valori collegati alla chiave passata
        /// </summary>
        /// <param name="chiave"></param>
        /// <returns></returns>
        public string Valore(string chiave)
        {
            var result = new string[0];

            if (this._datiPdf.TryGetValue(chiave, out result))
            {
                if (result.Length == 0 || result.Length > 1)
                    return null;

                return result[0];
            }

            return null;
        }

        public IEnumerable<IGrouping<string, string[]>> Dati
        {
            get
            {
                return this._datiPdf.GroupBy(x => x.Key, y => y.Value);
            }
        }
    }
}
