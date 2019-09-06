using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo.PaDoc.Protocollazione.PersonaSegnatura
{
    public class PersonaAnagrafica : IPersona
    {
        ProtocolloAnagrafe _anag;

        public PersonaAnagrafica(ProtocolloAnagrafe anag)
        {
            _anag = anag;
        }

        public object[] GetPersona()
        {
            var list = new List<object>();
            
            list.Add(new Denominazione { Text = new string[] { _anag.GetNomeCompleto() } });
            if (_anag.TIPOANAGRAFE == ProtocolloConstants.COD_PERSONAFISICA)
            {
                list.Add(new Nome { Text = new string[] { _anag.NOME } });
                list.Add(new Cognome { Text = new string[] { _anag.NOMINATIVO } });
            }

            if (String.IsNullOrEmpty(_anag.CODICEFISCALE))
            {
                if (!String.IsNullOrEmpty(_anag.PARTITAIVA))
                    list.Add(new CodiceFiscale { Text = new string[] { _anag.PARTITAIVA } });
            }
            else
                list.Add(new CodiceFiscale{ Text = new string[]{ _anag.CODICEFISCALE } });

            return list.ToArray();
        }

        public string Denominazione
        {
            get { return _anag.GetNomeCompleto(); }
        }


        public IndirizzoPostale GetIndirizzoPostale()
        {
            return new IndirizzoPostale
            {
                Item = new Indirizzo
                {
                    CAP = new CAP { Text = new string[] { _anag.CAP } },
                    Civico = new Civico { Text = new string[] { "" } },
                    Comune = new Comune { Text = new string[] { _anag.COMUNERESIDENZA } },
                    Provincia = new Provincia { Text = new string[] { _anag.PROVINCIA } },
                    Toponimo = new Toponimo { dug = "", Text = new string[] { _anag.INDIRIZZO } }
                }
            };
        }


        public IndirizzoTelematico IndirizzoTelematico
        {
            get { return new IndirizzoTelematico { tipo = IndirizzoTelematicoTipo.smtp, Text = new string[] { _anag.Pec } }; }
        }
    }
}
