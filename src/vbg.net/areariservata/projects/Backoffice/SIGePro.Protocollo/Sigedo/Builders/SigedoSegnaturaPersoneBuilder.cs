using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;

namespace Init.SIGePro.Protocollo.Sigedo.Builders
{
    public class SigedoSegnaturaPersoneBuilder
    {
        IDatiProtocollo _protoIn;
        public readonly List<Persona> SegnaturaPersona;
        bool _inviaCF = false;

        public SigedoSegnaturaPersoneBuilder(IDatiProtocollo protoIn, bool inviaCF)
        {
            _protoIn = protoIn;
            _inviaCF = inviaCF;
            SegnaturaPersona = GetPersona();
        }

        private List<Persona> GetPersona()
        {
            var listPersona = new List<Persona>();

            foreach(var anag in _protoIn.AnagraficheProtocollo)
            {
                string codiceFiscale = String.IsNullOrEmpty(anag.CODICEFISCALE) ? anag.PARTITAIVA : anag.CODICEFISCALE;

                var persona = new Persona
                {
                    Nome = anag.NOME,
                    Cognome = anag.NOMINATIVO,
                    IndirizzoTelematico = new IndirizzoTelematico { Text = new string[] { anag.EMAIL } }
                };

                if (!String.IsNullOrEmpty(codiceFiscale))
                    persona.CodiceFiscale = codiceFiscale;

                if (_inviaCF)
                {
                    if (String.IsNullOrEmpty(codiceFiscale))
                        throw new Exception(String.Format("SE IL PARAMETRO INVIA_CF DELLA VERTICALIZZAZIONE PROTOCOLLO_SIGEDO E' IMPOSTATO A 1, UNO TRA I CAMPI PARTITA IVA O CODICE FISCALE DELL'ANAGRAFICA CODICE {0} DESCRIZIONE {1} DEVE ESSERE VALORIZZATO.", anag.CODICEANAGRAFE, anag.NOMINATIVO));

                    persona.id = codiceFiscale;
                }
                
                listPersona.Add(persona);
            }

            var listAmministrazioniEsterne = _protoIn.AmministrazioniProtocollo.Where(x => String.IsNullOrEmpty(x.PROT_UO) && String.IsNullOrEmpty(x.PROT_RUOLO)).ToList();

            var amministrazioniInterne = _protoIn.AmministrazioniProtocollo.Where(x => !String.IsNullOrEmpty(x.PROT_UO) || !String.IsNullOrEmpty(x.PROT_RUOLO)).ToList();
            if (amministrazioniInterne.Count > 0)
                throw new Exception("E' STATA UTILIZZATA UN'AMMINISTRAZIONE INTERNA COME MITTENTE / DESTINATARIO, USARE IL FLUSSO INTERNO");

            foreach (var amm in listAmministrazioniEsterne)
            {
                var persona = new Persona
                {
                    Cognome = amm.AMMINISTRAZIONE,
                    IndirizzoTelematico = new IndirizzoTelematico { Text = new string[] { amm.EMAIL } }
                };

                if (!String.IsNullOrEmpty(amm.PARTITAIVA))
                    persona.CodiceFiscale = amm.PARTITAIVA;

                if (_inviaCF)
                {
                    if (String.IsNullOrEmpty(amm.PARTITAIVA))
                        throw new Exception(String.Format("SE IL PARAMETRO INVIA_CF DELLA VERTICALIZZAZIONE PROTOCOLLO_SIGEDO E' IMPOSTATO A 1 IL CAMPO PARTITA IVA DELL'AMMINISTRAZIONE CODICE {0} DESCRIZIONE {1} DEVE ESSERE VALORIZZATO", amm.CODICEAMMINISTRAZIONE, amm.AMMINISTRAZIONE));

                    persona.id = amm.PARTITAIVA;
                }

                listPersona.Add(persona);
            }
            
            //mittente.Items = new object[] { persona };
            return listPersona;
        }
    }
}
