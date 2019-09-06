using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloServices;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.Halley.ImpresaInUnGiorno.Suap;
using System.IO;
using Init.SIGePro.Protocollo.Halley.ImpresaInUnGiorno.Sue;

namespace Init.SIGePro.Protocollo.Halley.ImpresaInUnGiorno
{
    public class ImpresaInUnGiornoFactory
    {
        public static void AggiungiAllegatoXml(ResolveDatiProtocollazioneService datiProtocollazioneService, List<ProtocolloAllegati> allegati, ProtocolloLogs logs, ProtocolloSerializer serialize)
        {
            if (datiProtocollazioneService.Istanza.AziendaRichiedente != null)
            {
                var suapGenerator = new SuapGenerator(datiProtocollazioneService, logs, allegati);
                
                var path = Path.Combine(logs.Folder, suapGenerator.NomeFile);
                if (File.Exists(path))
                {
                    logs.WarnFormat("Il path {0} non esiste", path);
                    return;
                }

                var suapXml = suapGenerator.Genera();
                serialize.Serialize(suapGenerator.NomeFile, suapXml, Validation.ProtocolloValidation.TipiValidazione.XSD, "Halley/Suap.xsd", true);

                allegati.Add(new ProtocolloAllegati
                {
                    NOMEFILE = suapGenerator.NomeFile,
                    Descrizione = suapGenerator.NomeFile,
                    MimeType = "text/xml",
                    OGGETTO = File.ReadAllBytes(path)
                });
                logs.InfoFormat("Creazione del file Suap.xml dell'istanza numero {0} creato correttamente", datiProtocollazioneService.NumeroIstanza);
            }
            else
            {
                var sueGenerator = new SueGenerator(datiProtocollazioneService, logs, allegati);

                var path = Path.Combine(logs.Folder, sueGenerator.NomeFile);

                if (File.Exists(path))
                {
                    logs.WarnFormat("Il path {0} non esiste", path);
                    return;
                }

                var sueXml = sueGenerator.Genera();
                serialize.Serialize(sueGenerator.NomeFile, sueXml, Validation.ProtocolloValidation.TipiValidazione.XSD, "Halley/Sue.xsd", true);

                allegati.Add(new ProtocolloAllegati
                {
                    NOMEFILE = sueGenerator.NomeFile,
                    Descrizione = sueGenerator.NomeFile,
                    MimeType = "text/xml",
                    OGGETTO = File.ReadAllBytes(path)
                });
                
                logs.InfoFormat("Creazione del file Sue.xml dell'istanza numero {0} creato correttamente", datiProtocollazioneService.NumeroIstanza);
            }
        }
    }
}
