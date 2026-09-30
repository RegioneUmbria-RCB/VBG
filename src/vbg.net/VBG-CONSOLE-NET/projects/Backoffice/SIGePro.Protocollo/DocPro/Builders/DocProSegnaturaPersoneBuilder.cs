using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;

namespace Init.SIGePro.Protocollo.DocPro.Builders
{
    public class DocProSegnaturaPersoneBuilder
    {
        IDatiProtocollo _protoIn;
        //string _indirizzoTelematico = String.Empty;
        public readonly List<Persona> SegnaturaPersona;

        public DocProSegnaturaPersoneBuilder(IDatiProtocollo protoIn/*, string indirizzoTelematico*/)
        {
            _protoIn = protoIn;
            //_indirizzoTelematico = indirizzoTelematico;
            //SegnaturaPersona = GetPersona();
            SegnaturaPersona = GetFirstPersona();
        }

        private List<Persona> GetFirstPersona()
        {
            var listPersona = new List<Persona>();

            if (_protoIn.AnagraficheProtocollo.Count == 0)
                listPersona = GetFirstAmministrazione();
            else
            {
                var anag = _protoIn.AnagraficheProtocollo[0];

                string codiceFiscale = String.IsNullOrEmpty(anag.CODICEFISCALE) ? anag.PARTITAIVA : anag.CODICEFISCALE;

                if (String.IsNullOrEmpty(codiceFiscale))
                    throw new Exception(String.Format("I CAMPI PARTITA IVA O CODICE FISCALE DELL'ANAGRAFICA CODICE {0} DESCRIZIONE {1} NON SONO VALORIZZATI, DEVE ESSERE VALORIZZATO ALMENO UNO DEI DUE", anag.CODICEANAGRAFE, anag.NOMINATIVO));

                listPersona.Add(new Persona
                {
                    id = codiceFiscale,
                    CodiceFiscale = codiceFiscale,
                    Nome = anag.NOME,
                    Cognome = anag.NOMINATIVO,
                    IndirizzoTelematico = new IndirizzoTelematico { Text = new string[] { anag.Pec } }
                });
            }

            return listPersona;
        }


        private List<Persona> GetFirstAmministrazione()
        {
            var listPersona = new List<Persona>();
            var listAmministrazioniEsterne = _protoIn.AmministrazioniProtocollo.Where(x => String.IsNullOrEmpty(x.PROT_UO) && String.IsNullOrEmpty(x.PROT_RUOLO)).ToList();

            if (listAmministrazioniEsterne.Count > 0)
            {
                var amm = listAmministrazioniEsterne[0];

                if (String.IsNullOrEmpty(amm.PARTITAIVA))
                    throw new Exception(String.Format("IL CAMPO PARTITA IVA DELL'AMMINISTRAZIONE CODICE {0} DESCRIZIONE {1} NON E' VALORIZZATO", amm.CODICEAMMINISTRAZIONE, amm.AMMINISTRAZIONE));

                listPersona.Add(new Persona
                {
                    id = amm.PARTITAIVA,
                    CodiceFiscale = amm.PARTITAIVA,
                    Cognome = amm.AMMINISTRAZIONE,
                    IndirizzoTelematico = new IndirizzoTelematico { Text = new string[] { amm.PEC } }
                });
            }

            return listPersona;
        }

        /// <summary>
        /// Al momento da non usare in quanto questo tipo di protocollo accetta solo un mittente / destinatario e rischia di andare spesso in eccezione quando la protocollazione 
        /// arriva da on line.
        /// </summary>
        /// <returns></returns>
        private List<Persona> GetPersona()
        {
            var listPersona = new List<Persona>();

            foreach(var anag in _protoIn.AnagraficheProtocollo)
            {
                string codiceFiscale = String.IsNullOrEmpty(anag.CODICEFISCALE) ? anag.PARTITAIVA : anag.CODICEFISCALE;

                if (String.IsNullOrEmpty(codiceFiscale))
                    throw new Exception(String.Format("I CAMPI PARTITA IVA O CODICE FISCALE DELL'ANAGRAFICA CODICE {0} DESCRIZIONE {1} NON SONO VALORIZZATI, DEVE ESSERE VALORIZZATO ALMENO UNO DEI DUE", anag.CODICEANAGRAFE, anag.NOMINATIVO));
                
                listPersona.Add(new Persona 
                {
                    id = codiceFiscale,
                    CodiceFiscale = codiceFiscale,
                    Nome = anag.NOME,
                    Cognome = anag.NOMINATIVO,
                    IndirizzoTelematico = new IndirizzoTelematico{ Text = new string[] { anag.Pec } }
                });
            }

            var listAmministrazioniEsterne = _protoIn.AmministrazioniProtocollo.Where(x => String.IsNullOrEmpty(x.PROT_UO) && String.IsNullOrEmpty(x.PROT_RUOLO)).ToList();

            foreach (var amm in listAmministrazioniEsterne)
            {
                if (String.IsNullOrEmpty(amm.PARTITAIVA))
                    throw new Exception(String.Format("IL CAMPO PARTITA IVA DELL'AMMINISTRAZIONE CODICE {0} DESCRIZIONE {1} NON E' VALORIZZATO", amm.CODICEAMMINISTRAZIONE, amm.AMMINISTRAZIONE));

                listPersona.Add(new Persona
                {
                    id = amm.PARTITAIVA,
                    CodiceFiscale = amm.PARTITAIVA,
                    Cognome = amm.AMMINISTRAZIONE,
                    IndirizzoTelematico = new IndirizzoTelematico { Text = new string[] { amm.PEC } }
                });
            }
            
            //mittente.Items = new object[] { persona };
            return listPersona;
        }
    }
}
