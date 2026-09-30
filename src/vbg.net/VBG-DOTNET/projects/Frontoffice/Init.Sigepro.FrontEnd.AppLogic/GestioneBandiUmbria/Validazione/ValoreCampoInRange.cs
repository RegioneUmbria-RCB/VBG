// -----------------------------------------------------------------------
// <copyright file="ValoreCampoInRange.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria.Validazione
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using Init.Sigepro.FrontEnd.Infrastructure;
    using Init.Sigepro.FrontEnd.AppLogic.PrecompilazionePDF;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class ValoreCampoInRange : ISpecification<DatiPdfCompilabile>
    {
        string _nomeCampo;
        decimal _valoreMin;
        decimal _valoreMax;
        ValoriPdfUtils _utils = new ValoriPdfUtils();

        public ValoreCampoInRange(string nomeCampo, decimal valoreMin, decimal valoreMax)
        {
            this._nomeCampo = nomeCampo;
            this._valoreMin = valoreMin;
            this._valoreMax = valoreMax;
        }


        public bool IsSatisfiedBy(DatiPdfCompilabile datiPdf)
        {
            var valoreCampo = this._utils.ToDecimal(datiPdf.Valore(this._nomeCampo));

            return this._valoreMin <= valoreCampo && valoreCampo <= this._valoreMax;
        }
    }
}
