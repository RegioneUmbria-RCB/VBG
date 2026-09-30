using System;

namespace Init.SIGePro.Manager.Logic.Cosap
{
    public abstract class FormulaCosapBase : IFormulaCosap
    {
        public Calcolo Calcola()
        {
            var stringa = GetStringaCalcolo();
            var importo = GetImporto();

            return new Calcolo(stringa, importo);
        }

        protected abstract string GetStringaCalcolo();
        protected abstract double GetImporto();

        protected string ImportoToString(double importo)
        {
            return new Importo(importo).ToString();
        }

        protected Importo TariffaArrotondata(double tariffa)
        {
            var decimali = (tariffa - Math.Truncate(tariffa)) * 100.0d;
            var terzoDecimale = decimali - Math.Truncate(decimali);
            var retVal = Math.Truncate(tariffa) + (Math.Truncate(decimali) / 100);
            if (terzoDecimale >= 0.5d)
            {
                retVal += 0.01d;
            }
            return new Importo(retVal);
        }
    }
}
