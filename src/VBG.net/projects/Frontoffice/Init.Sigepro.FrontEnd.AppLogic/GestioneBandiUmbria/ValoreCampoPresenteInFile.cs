using Init.Sigepro.FrontEnd.AppLogic.PrecompilazionePDF;
using Init.Sigepro.FrontEnd.Infrastructure;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria
{
    class ValoreCampoPresenteInFile : ISpecification<DatiPdfCompilabile>
    {
        string _nomeCampoA;
        DatiPdfCompilabile _datiPdfA;
        string _nomeCampoB;

        public ValoreCampoPresenteInFile(string nomeCampoA, DatiPdfCompilabile datiPdfA, string nomeCampoB)
        {
            this._nomeCampoA = nomeCampoA;
            this._nomeCampoB = nomeCampoB;
            this._datiPdfA = datiPdfA;
        }

        public bool IsSatisfiedBy(DatiPdfCompilabile item)
        {
            var valoreCampo1 = this._datiPdfA.Valore(this._nomeCampoA);
            var valoreCampo2 = item.Valore(this._nomeCampoB);

            return valoreCampo1 == valoreCampo2;            
        }
    }
}
