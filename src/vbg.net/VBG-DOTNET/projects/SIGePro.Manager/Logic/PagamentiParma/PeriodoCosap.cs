using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.PagamentiParma
{
    public class PeriodoCosap
    {
        public readonly DateTime _dataInizio;
        public readonly DateTime _dataFine;


        public PeriodoCosap(DateTime dataInizio, DateTime dataFine)
        {
            _dataInizio = dataInizio;
            _dataFine = dataFine;
        }


    }
}
