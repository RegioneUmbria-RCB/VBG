using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.DocArea.Interfaces;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.DocArea.Factories;

namespace Init.SIGePro.Protocollo.DocArea.Builders.MittentiDestinatari
{
    public class DocAreaSegnaturaMultiPersonaBuilder : IDocAreaSegnaturaPersoneBuilder
    {
        ProtocolloLogs _logs;

        public DocAreaSegnaturaMultiPersonaBuilder(ProtocolloLogs logs)
        {
            _logs = logs;
        }

        #region IDocAreaSegnaturaPersoneBuilder Members

        public List<Persona> GetMittenteDestinatario(IDatiProtocollo protoIn, bool usaDenominazionePg, string indirizzoTelematico)
        {
            var listPersona = new List<Persona>();

            foreach (var anag in protoIn.AnagraficheProtocollo)
            {
                string codiceFiscale = String.IsNullOrEmpty(anag.CODICEFISCALE) ? anag.PARTITAIVA : anag.CODICEFISCALE;

                if (String.IsNullOrEmpty(codiceFiscale))
                    throw new Exception(String.Format("I CAMPI PARTITA IVA O CODICE FISCALE DELL'ANAGRAFICA CODICE {0} DESCRIZIONE {1} NON SONO VALORIZZATI, DEVE ESSERE VALORIZZATO ALMENO UNO DEI DUE", anag.CODICEANAGRAFE, anag.NOMINATIVO));

                var persona = new Persona
                {
                    id = codiceFiscale,
                    CodiceFiscale = codiceFiscale,
                    IndirizzoTelematico = new IndirizzoTelematico { Text = new string[] { indirizzoTelematico } }
                };

                var nominativoPersonaFactory = DocAreaSegnaturaAnagraficaFactory.Create(usaDenominazionePg, anag);

                persona.Cognome = nominativoPersonaFactory.Cognome;
                persona.Nome = nominativoPersonaFactory.Nome;
                persona.Denominazione = nominativoPersonaFactory.Denominazione;

                listPersona.Add(persona);

            }


            var listAmministrazioniInterne = protoIn.AmministrazioniProtocollo.Where(x => !String.IsNullOrEmpty(x.PROT_UO) || !String.IsNullOrEmpty(x.PROT_RUOLO)).ToList();

            if (listAmministrazioniInterne.Count > 0)
                _logs.WarnFormat("Si sta tentando di protocollare delle amministrazioni interne tra i mittenti / destinatari, le amministrazioni sono: {0}, queste amministrazioni non saranno prese in considerazione in fase di protocollazione", String.Join(", ", listAmministrazioniInterne.Select(x => String.Format("({0}) {1}", x.CODICEAMMINISTRAZIONE, x.AMMINISTRAZIONE))));

            var listAmministrazioniEsterne = protoIn.AmministrazioniProtocollo.Where(x => String.IsNullOrEmpty(x.PROT_UO) && String.IsNullOrEmpty(x.PROT_RUOLO)).ToList();

            foreach (var amm in listAmministrazioniEsterne)
            {

                var nominativoAmministrazioneFactory = DocAreaSegnaturaAmministrazioneFactory.Create(usaDenominazionePg, amm);

                if (String.IsNullOrEmpty(amm.PARTITAIVA))
                    throw new Exception(String.Format("IL CAMPO PARTITA IVA DELL'AMMINISTRAZIONE CODICE {0} DESCRIZIONE {1} NON E' VALORIZZATO", amm.CODICEAMMINISTRAZIONE, amm.AMMINISTRAZIONE));

                listPersona.Add(new Persona
                {
                    id = amm.PARTITAIVA,
                    CodiceFiscale = amm.PARTITAIVA,
                    Denominazione = nominativoAmministrazioneFactory.Denominazione,
                    Cognome = nominativoAmministrazioneFactory.Cognome,
                    Nome = nominativoAmministrazioneFactory.Nome,
                    IndirizzoTelematico = new IndirizzoTelematico { Text = new string[] { amm.EMAIL } }
                });
            }

            //mittente.Items = new object[] { persona };
            return listPersona;
        }

        #endregion
    }
}
