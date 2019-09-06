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
            public const string AOO = "CODICE_AOO";
            public const string AMMINISTRAZIONE = "CODICE_AMMINISTRAZIONE";
        }

        string _pec;
        string _codiceAmministrazione;
        string _codiceAoo;
        string _swapEmail;
        string _tipoRecapito;


        public readonly RecapitoIn[] Recapiti;

        public IrideRecapitiEmailBuilder(string pec, string swapEmail, string tipoRecapito)
        {
            this._pec = pec;
            this._swapEmail = swapEmail;
            this._tipoRecapito = tipoRecapito;
            Recapiti = SetRecapitiEmail();
        }

        public IrideRecapitiEmailBuilder(string pec, string codiceAmministrazione, string codiceAoo, string swapEmail, string tipoRecapito)
        {
            this._pec = pec;
            this._codiceAmministrazione = codiceAmministrazione;
            this._codiceAoo = codiceAoo;
            this._swapEmail = swapEmail;
            this._tipoRecapito = tipoRecapito;

            Recapiti = SetRecapitiEmail();
        }

        public IrideRecapitiEmailBuilder(string[] pecs, string swapEmail, string tipoRecapito)
        {
            this._tipoRecapito = tipoRecapito;
            var recapiti = pecs.Select(x => 
            {
                var retVal = new RecapitoIn { TipoRecapito = this._tipoRecapito, ValoreRecapito = x };
                
                if (!String.IsNullOrEmpty(_swapEmail))
                    retVal.Swap = _swapEmail;

                return retVal;
            });

            Recapiti = recapiti.ToArray();
        }


        private RecapitoIn[] SetRecapitiEmail()
        {
            var recapiti = new List<RecapitoIn>();
            var recapitoPec = new RecapitoIn { TipoRecapito = this._tipoRecapito, ValoreRecapito = _pec };
            
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
