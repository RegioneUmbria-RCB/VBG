using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.JIride.Protocollazione
{
    public class RecapitiEmailAdapter
    {
        private static class Constants
        { 
            public const string AOO = "CODICE_AOO";
            public const string AMMINISTRAZIONE = "CODICE_AMMINISTRAZIONE";
        }

        string _pec;
        string _codiceAmministrazione;
        string _codiceAoo;
        string _tipoRecapito;

        public readonly RecapitoInXml[] Recapiti;

        public RecapitiEmailAdapter(string pec, string tipoRecapito)
        {
            this._pec = pec;
            this._tipoRecapito = tipoRecapito;

            Recapiti = SetRecapitiEmail();
        }

        public RecapitiEmailAdapter(string pec, string codiceAmministrazione, string codiceAoo, string tipoRecapito)
        {
            this._pec = pec;
            this._codiceAmministrazione = codiceAmministrazione;
            this._codiceAoo = codiceAoo;
            this._tipoRecapito = tipoRecapito;

            Recapiti = SetRecapitiEmail();
        }

        public RecapitiEmailAdapter(string[] pecs, string tipoRecapito)
        {
            this._tipoRecapito = tipoRecapito;
            var recapiti = pecs.Select(x => 
            {
                var retVal = new RecapitoInXml { TipoRecapito = this._tipoRecapito, ValoreRecapito = x };
                return retVal;
            });

            Recapiti = recapiti.ToArray();
        }

        private RecapitoInXml[] SetRecapitiEmail()
        {
            var recapiti = new List<RecapitoInXml>();
            var recapitoPec = new RecapitoInXml { TipoRecapito = this._tipoRecapito, ValoreRecapito = _pec };
            
            recapiti.Add(recapitoPec);

            if (!String.IsNullOrEmpty(_codiceAmministrazione))
                recapiti.Add(new RecapitoInXml
                {
                    TipoRecapito = Constants.AMMINISTRAZIONE,
                    ValoreRecapito = _codiceAmministrazione
                });

            if (!String.IsNullOrEmpty(_codiceAoo))
                recapiti.Add(new RecapitoInXml
                {
                    TipoRecapito = Constants.AOO,
                    ValoreRecapito = _codiceAoo
                });

            return recapiti.ToArray();

        }

    }
}
