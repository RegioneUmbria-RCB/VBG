using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloServices;
using Init.SIGePro.Protocollo.Logs;
using System.Collections;

namespace Init.SIGePro.Protocollo.Halley.ImpresaInUnGiorno.Suap
{
    public class IndirizziConRecapitiAdapter
    {

        private static class Constants
        {
            public const string StatoEstero = "EE";
            public const string SiglaStatoItalia = "IT";
            public const string StatoItaliaEsteso = "ITALIA";

            public const string SiglaNonDefinito = "ND";
            public const string NonDefinito = "NON DEFINITO";
            public const string CodiceCatastaleNonDefinito = "A000";

            public const string ToponimoDefault = ".";

        }

        ResolveDatiProtocollazioneService _datiProtocollazioneService;
        ProtocolloLogs _logs;

        public IndirizziConRecapitiAdapter(ResolveDatiProtocollazioneService datiProtocollazioneService, ProtocolloLogs logs)
        {
            _datiProtocollazioneService = datiProtocollazioneService;
            _logs = logs;
        }

        public IndirizzoConRecapiti Adatta()
        {
            var azienda = _datiProtocollazioneService.Istanza.AziendaRichiedente;
            var comuneRes = azienda.ComuneResidenza;

            var res = new IndirizzoConRecapiti
            {
                stato = new Stato
                {
                    codice = Constants.SiglaNonDefinito,
                    Value = Constants.NonDefinito
                },
                Items = new object[]
                { 
                    new Provincia{ sigla = Constants.SiglaNonDefinito, Value = Constants.NonDefinito },
                    new Comune{ codicecatastale = Constants.CodiceCatastaleNonDefinito, Value = Constants.NonDefinito }
                },
                toponimo = Constants.ToponimoDefault,
                numerocivico = Constants.SiglaNonDefinito,
                denominazionestradale = Constants.NonDefinito,
                cap = azienda.CAP
            };

            if (comuneRes != null)
            {
                if (comuneRes.PROVINCIA == "EE")
                {
                    res.Items = new object[] { azienda.CITTA };
                    res.stato = new Stato
                    {
                        codice = Constants.StatoEstero,
                        Value = comuneRes.COMUNE,
                        codiceistat = comuneRes.CODICEISTAT,
                        codicecatastale = comuneRes.CF
                    };
                }
                else
                {
                    if (comuneRes != null)
                    {
                        res.Items = new object[] 
                        { 
                            new Provincia { codiceistat = comuneRes.CODICEISTAT, sigla = comuneRes.SIGLAPROVINCIA, Value = comuneRes.PROVINCIA },
                            new Comune{ codicecatastale = comuneRes.CF, Value = comuneRes.COMUNE }
                        };
                        res.stato = new Stato
                        {
                            codice = Constants.SiglaStatoItalia,
                            Value = Constants.StatoItaliaEsteso
                        };
                    }
                }
            }


            if (!String.IsNullOrEmpty(azienda.INDIRIZZO))
            {
                IEnumerable<string> split = azienda.INDIRIZZO.Split(' ');
                if (split.Count() > 0)
                    res.toponimo = split.First();

                if (split.Count() > 1)
                {
                    res.denominazionestradale = String.Join(" ", split.Skip(1));
                    res.numerocivico = split.Last();
                }
            }

            return res;
        }
    }
}
