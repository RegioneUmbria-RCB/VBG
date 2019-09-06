using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.DocArea.Interfaces;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.DocArea.Factories;

namespace Init.SIGePro.Protocollo.DocArea.Builders.MittentiDestinatari
{
    public class DocAreaSegnaturaPersonaBuilder : IDocAreaSegnaturaPersoneBuilder
    {
        ProtocolloLogs _logs;

        public DocAreaSegnaturaPersonaBuilder(ProtocolloLogs logs)
        {
            _logs = logs;
        }

        #region IDocAreaSegnaturaPersoneBuilder Members

        public List<Persona> GetMittenteDestinatario(IDatiProtocollo protoIn, bool usaDenominazionePg, string indirizzoTelematico)
        {
            var listPersona = new List<Persona>();

            if (protoIn.AnagraficheProtocollo.Count == 0)
                listPersona = GetFirstAmministrazione(protoIn, usaDenominazionePg);
            else
            {
                var anag = protoIn.AnagraficheProtocollo[0];

                var nominativoPersonaFactory = DocAreaSegnaturaAnagraficaFactory.Create(usaDenominazionePg, anag);

                string codiceFiscale = String.IsNullOrEmpty(anag.CODICEFISCALE) ? anag.PARTITAIVA : anag.CODICEFISCALE;

                if (String.IsNullOrEmpty(codiceFiscale))
                    throw new Exception(String.Format("I CAMPI PARTITA IVA O CODICE FISCALE DELL'ANAGRAFICA CODICE {0} DESCRIZIONE {1} NON SONO VALORIZZATI, DEVE ESSERE VALORIZZATO ALMENO UNO DEI DUE", anag.CODICEANAGRAFE, anag.NOMINATIVO));

                var persona = new Persona
                {
                    id = codiceFiscale,
                    CodiceFiscale = codiceFiscale,
                    IndirizzoTelematico = new IndirizzoTelematico { Text = new string[] { anag.Pec }, tipo = "smtp" },
                };

                persona.Nome = nominativoPersonaFactory.Nome;
                persona.Cognome = nominativoPersonaFactory.Cognome;
                persona.Denominazione = nominativoPersonaFactory.Denominazione;
                
                listPersona.Add(persona);

            }

            return listPersona;
        }

        private List<Persona> GetFirstAmministrazione(IDatiProtocollo protoIn, bool usaDenominazione)
        {
            var listPersona = new List<Persona>();
            var listAmministrazioniEsterne = protoIn.AmministrazioniProtocollo.Where(x => String.IsNullOrEmpty(x.PROT_UO) && String.IsNullOrEmpty(x.PROT_RUOLO)).ToList();

            if (listAmministrazioniEsterne.Count > 0)
            {
                var amm = listAmministrazioniEsterne[0];

                var amministrazioneFactory = DocAreaSegnaturaAmministrazioneFactory.Create(usaDenominazione, amm);

                if (String.IsNullOrEmpty(amm.PARTITAIVA))
                    throw new Exception(String.Format("IL CAMPO PARTITA IVA DELL'AMMINISTRAZIONE CODICE {0} DESCRIZIONE {1} NON E' VALORIZZATO", amm.CODICEAMMINISTRAZIONE, amm.AMMINISTRAZIONE));

                listPersona.Add(new Persona
                {
                    id = amm.PARTITAIVA,
                    CodiceFiscale = amm.PARTITAIVA,
                    Denominazione = amministrazioneFactory.Denominazione,
                    Cognome = amministrazioneFactory.Cognome,
                    Nome = amministrazioneFactory.Nome,
                    IndirizzoTelematico = new IndirizzoTelematico { Text = new string[] { amm.PEC }, tipo = "smtp" }
                });
            }

            return listPersona;
        }


        #endregion
    }
}
