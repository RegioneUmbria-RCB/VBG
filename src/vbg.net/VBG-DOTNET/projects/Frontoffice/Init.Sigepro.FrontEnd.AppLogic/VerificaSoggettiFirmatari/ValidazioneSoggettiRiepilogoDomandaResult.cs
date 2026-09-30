using System;
using System.Collections.Generic;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.VerificaSoggettiFirmatari
{
    public class ValidazioneSoggettiRiepilogoDomandaResult
    {
        public bool Result { get; set; }
        public List<string> ErroriValidazione { get; set; }
    }
}
