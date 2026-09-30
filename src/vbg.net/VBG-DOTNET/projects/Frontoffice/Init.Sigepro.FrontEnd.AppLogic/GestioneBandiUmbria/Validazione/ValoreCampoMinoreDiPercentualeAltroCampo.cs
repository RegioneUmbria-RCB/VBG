// -----------------------------------------------------------------------
// <copyright file="ValoreCampoMinoreDiPercentualeAltroCampo.cs" company="">
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
    public class ValoreCampoMinoreDiPercentualeAltroCampo : ISpecification<DatiPdfCompilabile>
    {
        string _nomeCampo;
        string _nomeCampoConfronto;
        int _percentuale;
        ValoriPdfUtils _utils = new ValoriPdfUtils();

        public ValoreCampoMinoreDiPercentualeAltroCampo(string nomeCampo, string nomeCampoConfronto, int percentuale)
        {
            this._nomeCampo = nomeCampo;
            this._nomeCampoConfronto = nomeCampoConfronto;
            this._percentuale = percentuale;
        }

        public bool IsSatisfiedBy(DatiPdfCompilabile datiPdf)
        {
            var valore = this._utils.ToDecimal(datiPdf.Valore(this._nomeCampo));
            var valoreConfronto = this._utils.ToDecimal(datiPdf.Valore(this._nomeCampoConfronto));

            var pct = (valoreConfronto / 100.0m) * ((decimal)this._percentuale);

            return valore < pct;
        }
    }
}
