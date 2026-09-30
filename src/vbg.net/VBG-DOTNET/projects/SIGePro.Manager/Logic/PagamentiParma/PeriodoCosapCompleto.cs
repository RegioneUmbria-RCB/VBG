using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.PagamentiParma
{
    public class PeriodoCosapCompleto
    {
        private readonly DateTime _dataInizio;
        private readonly DateTime _dataFine;
        private readonly int _totaleGiorni;

        public PeriodoCosapCompleto(PeriodoCosap periodo, int totaleGiorni = 0)
            : this(periodo._dataInizio, periodo._dataFine, totaleGiorni)
        {
        }


        public PeriodoCosapCompleto(DateTime dataInizio, DateTime dataFine, int totaleGiorni = 0)
        {
            _dataInizio = dataInizio;
            _dataFine = dataFine;
            _totaleGiorni = totaleGiorni;
        }

        public override string ToString()
        {
            var numgg = (this._dataFine - this._dataInizio).TotalDays;
            return $"periodo dal {this._dataInizio.ToString("dd/MM/yyyy")} al {this._dataFine.ToString("dd/MM/yyyy")} ({this._totaleGiorni} giorni)";
        }
    }
}
