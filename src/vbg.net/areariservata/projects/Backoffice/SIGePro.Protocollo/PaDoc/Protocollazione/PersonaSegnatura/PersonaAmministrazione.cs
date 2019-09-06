using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.PaDoc.Protocollazione.PersonaSegnatura
{
    public class PersonaAmministrazione : IPersona
    {
        Amministrazioni _amm;

        public PersonaAmministrazione(Amministrazioni amm)
        {
            _amm = amm;
        }

        public object[] GetPersona()
        {
            var list = new List<object>();

            list.Add(new Denominazione { Text = new string[]{ _amm.AMMINISTRAZIONE } });

            if (!String.IsNullOrEmpty(_amm.PARTITAIVA))
                list.Add(new CodiceFiscale { Text = new string[]{ _amm.PARTITAIVA } });

            return list.ToArray();
        }

        public string Denominazione
        {
            get { return _amm.AMMINISTRAZIONE; }
        }


        public IndirizzoPostale GetIndirizzoPostale()
        {
            return new IndirizzoPostale
            {
                Item = new Indirizzo
                {
                    CAP = new CAP { Text = new string[] { _amm.CAP } },
                    Civico = new Civico { Text = new string[] { "" } },
                    Comune = new Comune { Text = new string[] { _amm.CITTA } },
                    Provincia = new Provincia { Text = new string[] { _amm.PROVINCIA } },
                    Toponimo = new Toponimo { dug = "", Text = new string[] { _amm.INDIRIZZO } }
                }
            };
        }

        public IndirizzoTelematico IndirizzoTelematico
        {
            get { return new IndirizzoTelematico { tipo = IndirizzoTelematicoTipo.smtp, Text = new string[]{ _amm.PEC } }; }
        }
    }
}
