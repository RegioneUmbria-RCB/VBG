// -----------------------------------------------------------------------
// <copyright file="SommaSpeseDiFidejussioneMinoreDiPercentualeSpesaPromozione.cs" company="">
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
    public class SommatoriaSpesa1MinoreDiPercentualeSommatoriaSpesa2 : ISpecification<DatiPdfCompilabile>
    {
        string  _nomeCampoSpesa1;
        string  _nomeCampoSpesa2;
        int     _numeroAziende;
        int     _percentualeAmmessa;
        int     _percentualeAnticipo;
        int     _percentualeContributo;
        ValoriPdfUtils _utils = new ValoriPdfUtils();

        public SommatoriaSpesa1MinoreDiPercentualeSommatoriaSpesa2(string nomeCampoSpesa1, string nomeCampoSpesa2, int numeroAziende, int percentualeAmmessa, int percentualeAnticipo, int percentualeContributo)
        {
            this._nomeCampoSpesa1 = nomeCampoSpesa1;
            this._nomeCampoSpesa2 = nomeCampoSpesa2;
            this._numeroAziende = numeroAziende;
            this._percentualeAmmessa = percentualeAmmessa;
            this._percentualeAnticipo = percentualeAnticipo;
            this._percentualeContributo = percentualeContributo;
        }

        public bool IsSatisfiedBy(DatiPdfCompilabile datiPdf)
        {
            var sommatoriaSpesa1 = 0.0m;
            var sommatoriaSpesa2 = 0.0m;

            for (var i = 0; i < this._numeroAziende; i++)
            {
                var campoSpesa1 = this._nomeCampoSpesa1.Replace("[n]", (i + 1).ToString());
                var campoSpesa2 = this._nomeCampoSpesa2.Replace("[n]", (i + 1).ToString());

                sommatoriaSpesa1 += this._utils.ToDecimal(datiPdf.Valore(campoSpesa1));
                sommatoriaSpesa2 += this._utils.ToDecimal(datiPdf.Valore(campoSpesa2));
            }

            var pct = (sommatoriaSpesa2 / 100.0m) * ((decimal)this._percentualeContributo);
            pct = (pct / 100.0m) * ((decimal)this._percentualeAnticipo);
            pct = (pct / 100.0m) * ((decimal)this._percentualeAmmessa);

            return sommatoriaSpesa1 < pct;
        }
    }
}
