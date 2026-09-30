using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.DocArea.DestinatariAggiuntivi.DataManagement
{
    public class DestinatariAggiuntiviAdapter
    {

        public static DestinatariAggiuntivi Adatta(IEnumerable<IAnagraficaAmministrazione> destinatari, ProtocollazioneRet response, string codiceAmministrazione, string codiceAoo, string flusso, ProtocolloLogs logs)
        {
            if (destinatari.Count() < 2)
                return null;

            var destinatariSegnatura = destinatari.Skip(1).Select(x => 
            {
                if (String.IsNullOrEmpty(x.CodiceFiscalePartitaIva))
                    throw new Exception("IL DESTINATARIO {0} NON HA VALORIZZATO IL CODICE FISCALE / PARTITA IVA");

                var persona = new Persona
                {
                    CodiceFiscale = x.CodiceFiscalePartitaIva,
                    id = x.CodiceFiscalePartitaIva
                };

                if (x.Tipo == "F")
                {
                    persona.Nome = x.Nome;
                    persona.Cognome = x.Cognome;
                }
                else 
                {
                    persona.Nome = x.Denominazione;
                    persona.Cognome = "";
                    persona.Denominazione = x.Denominazione;
                }


                return new Destinatario
                {
                    Items = new object[] { persona },
                    IndirizzoTelematico = new IndirizzoTelematico { Text = new string[] { x.Pec } }
                };
            });

            DateTime data;
            var seData = DateTime.TryParse(response.strDataPG, out data);

            if (!seData)
            {
                logs.WarnFormat("LA DATA DI PROTOCOLLAZIONE NON HA UN FORMATO VALIDO, {0}", response.strDataPG);
                return null;
            }

            var retVal = new DestinatariAggiuntivi
            {
                Identificatore = new Identificatore
                {
                    NumeroRegistrazione = response.lngNumPG.ToString(),
                    DataRegistrazione = data.ToString("yyyy-MM-dd"),
                    Flusso = flusso,
                    CodiceAOO = codiceAoo,
                    CodiceAmministrazione = codiceAmministrazione
                },
                Destinatari = destinatariSegnatura.ToArray()
            };

            return retVal;
        }
    }
}
