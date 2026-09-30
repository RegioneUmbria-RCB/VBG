using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAccessoAtti.Trieste
{
    public class TAAStatoDomanda
    {
        public string PrimoStep { get; set; }
        public string SecondoStep { get; set; }

        public IEnumerable<TAAAtto> Atti { get; set; }
    }
}
