using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Iride2.Proxies;

namespace Init.SIGePro.Protocollo.Iride2.Builders
{
    public class IrideRecapitiEmailBuilder
    {
        private static class Constants
        { 
            public const string PEC = "EMAIL";
            public const string AOO = "CODICE_AOO";
            public const string AMMINISTRAZIONE = "CODICE_AMMINISTRAZIONE";
        }

        private string _pec;
        private string _codiceAmministrazione;
        private string _codiceAoo;
        private string _swapEmail;
        public readonly RecapitoIn[] Recapiti;

        public IrideRecapitiEmailBuilder(string pec, string swapEmail)
        {
            _pec = pec;
            _swapEmail = swapEmail;
            Recapiti = SetRecapitiEmail();
        }

        public IrideRecapitiEmailBuilder(string pec, string codiceAmministrazione, string codiceAoo, string swapEmail)
        {
            _pec = pec;
            _codiceAmministrazione = codiceAmministrazione;
            _codiceAoo = codiceAoo;
            _swapEmail = swapEmail;

            Recapiti = SetRecapitiEmail();
        }

        public IrideRecapitiEmailBuilder(string[] pecs, string swapEmail)
        {
            var recapiti = pecs.Select(x => 
            {
                var retVal = new RecapitoIn { TipoRecapito = Constants.PEC, ValoreRecapito = x };
                
                if (!String.IsNullOrEmpty(_swapEmail))
                    retVal.Swap = _swapEmail;

                return retVal;
            });

            Recapiti = recapiti.ToArray();
        }


        private RecapitoIn[] SetRecapitiEmail()
        {
            var recapiti = new List<RecapitoIn>();
            var recapitoPec = new RecapitoIn { TipoRecapito = Constants.PEC, ValoreRecapito = _pec };
            
            if (!String.IsNullOrEmpty(_swapEmail))
                recapitoPec.Swap = _swapEmail;

            recapiti.Add(recapitoPec);

            if (!String.IsNullOrEmpty(_codiceAmministrazione))
                recapiti.Add(new RecapitoIn
                {
                    TipoRecapito = Constants.AMMINISTRAZIONE,
                    ValoreRecapito = _codiceAmministrazione
                });

            if (!String.IsNullOrEmpty(_codiceAoo))
                recapiti.Add(new RecapitoIn
                {
                    TipoRecapito = Constants.AOO,
                    ValoreRecapito = _codiceAoo
                });

            return recapiti.ToArray();

        }

    }
}
