// -----------------------------------------------------------------------
// <copyright file="ValoriPdfUtils.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria.Validazione
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class ValoriPdfUtils
    {
        public decimal ToDecimal(string valore)
        {
            if (String.IsNullOrEmpty(valore))
                return 0.0m;

            return Convert.ToDecimal(valore.Replace('.',','));
        }

    }
}
