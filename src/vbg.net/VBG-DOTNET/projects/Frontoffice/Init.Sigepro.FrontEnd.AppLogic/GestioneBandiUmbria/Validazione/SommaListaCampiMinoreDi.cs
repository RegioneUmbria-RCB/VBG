// -----------------------------------------------------------------------
// <copyright file="SommaListaCampiMinoreDi.cs" company="">
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
    public class SommaListaCampiMinoreDi : ISpecification<DatiPdfCompilabile>
    {
        string _nomeCampo;
        int _numeroElementi;
        decimal _limite;
        ValoriPdfUtils _utils = new ValoriPdfUtils();

        public SommaListaCampiMinoreDi(string nomeCampo, int numeroElementi, decimal limite)
        {
            this._nomeCampo = nomeCampo;
            this._numeroElementi = numeroElementi;
            this._limite = limite;
        }

        public bool IsSatisfiedBy(DatiPdfCompilabile datiPdf)
        {
            var valore = 0.0m;

            for (int i = 0; i < this._numeroElementi; i++)
            {
                var campo = this._nomeCampo.Replace("[n]", (i + 1).ToString());

                valore += this._utils.ToDecimal(datiPdf.Valore(campo));
            }

            return valore < this._limite;

        }
    }
}
