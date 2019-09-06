// -----------------------------------------------------------------------
// <copyright file="SpeseAmmissibiliAContributoInRange.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria.Validazione
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.PrecompilazionePDF;
    using Init.Sigepro.FrontEnd.Infrastructure;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class SommaCampiInRange : ISpecification<DatiPdfCompilabile>
    {
        string _nomeCampo1;
        string _nomeCampo2;
        decimal _minValue;
        decimal _maxValue;
        ValoriPdfUtils _utils = new ValoriPdfUtils();

        public SommaCampiInRange(string nomeCampo1, string nomeCampo2, decimal minValue, decimal maxValue)
        {
            this._nomeCampo1 = nomeCampo1;
            this._nomeCampo2 = nomeCampo2;
            this._minValue = minValue;
            this._maxValue = maxValue;
        }

        public bool IsSatisfiedBy(DatiPdfCompilabile datiPdf)
        {
            var spesePromozione = this._utils.ToDecimal(datiPdf.Valore(this._nomeCampo1));
            var speseInvestimenti = this._utils.ToDecimal(datiPdf.Valore(this._nomeCampo2));

            var spesaAmmissibile = spesePromozione + speseInvestimenti;

            return this._minValue <= spesaAmmissibile && spesaAmmissibile <= this._maxValue;
        }
    }
}
