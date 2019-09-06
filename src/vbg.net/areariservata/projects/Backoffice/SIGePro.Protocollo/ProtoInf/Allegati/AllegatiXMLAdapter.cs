using Init.SIGePro.Data;
using Init.SIGePro.Manager.Utils;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Runtime.InteropServices;
using System.Security.Principal;
using System.Text;
using System.Xml.Linq;

namespace Init.SIGePro.Protocollo.ProtoInf.Allegati
{
    public class AllegatiXMLAdapter
    {
        public string PercorsoDirectoryDaProtocollo { get; private set; }


        public AllegatiXMLAdapter()
        {
            this.PercorsoDirectoryDaProtocollo = "";
        }

        private string GeneraPercorso(string percorso, string codiceIstanza, string codiceMovimento, string codicePec, ProtocolloLogs log, NetworkCredential credential)
        {
            try
            {
                log.InfoFormat("INIZIO FUNZIONALITA' DI CREAZIONE DEL PERCORSO, PERCORSO BASE: {0}, CODICE ISTANZA: {1}, CODICE MOVIMENTO: {2}, CODICE PEC: {3}", percorso, codiceIstanza, codiceMovimento, codicePec);
                if (!Directory.Exists(Path.Combine(percorso, DateTime.Now.Year.ToString())))
                {
                    log.InfoFormat("CREAZIONE DELLA DIRECTORY: {0}", Path.Combine(percorso, DateTime.Now.Year.ToString()));
                    Directory.CreateDirectory(Path.Combine(percorso, DateTime.Now.Year.ToString()));
                    log.InfoFormat("CREAZIONE DELLA DIRECTORY: {0} AVVENUTA CON SUCCESSO", Path.Combine(percorso, DateTime.Now.Year.ToString()));
                }

                if (!String.IsNullOrEmpty(codicePec))
                {
                    if (!Directory.Exists(Path.Combine(percorso, DateTime.Now.Year.ToString(), codicePec)))
                    {
                        log.InfoFormat("CREAZIONE DELLA DIRECTORY: {0}", Path.Combine(percorso, DateTime.Now.Year.ToString(), codicePec));
                        Directory.CreateDirectory(Path.Combine(percorso, DateTime.Now.Year.ToString(), codicePec));
                        log.InfoFormat("CREAZIONE DELLA DIRECTORY: {0} AVVENUTA CON SUCCESSO", Path.Combine(percorso, DateTime.Now.Year.ToString(), codicePec));
                    }
                    return Path.Combine(DateTime.Now.Year.ToString(), codicePec);
                }

                var path = Path.Combine(DateTime.Now.Year.ToString(), codiceIstanza);

                if (!Directory.Exists(Path.Combine(percorso, path)))
                {
                    log.InfoFormat("CREAZIONE DELLA DIRECTORY: {0}", Path.Combine(percorso, path));
                    Directory.CreateDirectory(Path.Combine(percorso, path));
                    log.InfoFormat("CREAZIONE DELLA DIRECTORY: {0} AVVENUTA CON SUCCESSO", Path.Combine(percorso, path));
                }

                if (codiceMovimento != "0")
                {
                    path = Path.Combine(path, codiceMovimento);
                    if (!Directory.Exists(Path.Combine(percorso, path)))
                    {
                        log.InfoFormat("CREAZIONE DELLA DIRECTORY: {0}", Path.Combine(percorso, path));
                        Directory.CreateDirectory(Path.Combine(percorso, path));
                        log.InfoFormat("CREAZIONE DELLA DIRECTORY: {0} AVVENUTA CON SUCCESSO", Path.Combine(percorso, path));
                    }
                }

                return path;
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE LA CREAZIONE DEL PERCORSO, {ex.Message}");
            }

        }

        public string Adatta(RequestInfo info)
        {
            info.Logs.InfoFormat("NUMERO ALLEGATI: {0}", info.Metadati.ProtoIn.Allegati.Count);
            if (info.Metadati.ProtoIn.Allegati.Count == 0)
            {
                return "";
            }

            info.Logs.InfoFormat("CREAZIONE DELLE CREDENTIALS: USERNAME: {0}, PASSWORD: {1}, DOMINIO: {2}", info.ParametriRegola.NetworkUsername, info.ParametriRegola.NetworkPassword, info.ParametriRegola.NetworkDomain);
            var credentials = new NetworkCredential(info.ParametriRegola.NetworkUsername, info.ParametriRegola.NetworkPassword, info.ParametriRegola.NetworkDomain);
            info.Logs.InfoFormat("CREAZIONE DELLE CREDENTIALS: USERNAME: {0}, PASSWORD: {1}, DOMINIO: {2} GENERATE CON SUCCESSO", info.ParametriRegola.NetworkUsername, info.ParametriRegola.NetworkPassword, info.ParametriRegola.NetworkDomain);
            using (new NetworkConnection(info.ParametriRegola.BasePathLocal, credentials))
            {
                var percorsoDir = GeneraPercorso(info.ParametriRegola.BasePathLocal, info.CodiceIstanza, info.CodiceMovimento, info.CodicePec, info.Logs, credentials);
                this.PercorsoDirectoryDaProtocollo = Path.Combine(info.ParametriRegola.BasePathProto, percorsoDir);
                info.Logs.InfoFormat("ASSEGNATA VARIABILE PERCORSO DIRECTORY DA SERVER DI PROTOCOLLO (DIRFTP): {0}", this.PercorsoDirectoryDaProtocollo);

                var allegatiXml = new AllegatiXML
                {
                    Dimensione = new AllegatiXML.Dim
                    {
                        NumeroColonne = 3,
                        NumeroRighe = info.Metadati.ProtoIn.Allegati.Count()
                    },
                    Righe = info.Metadati.ProtoIn.Allegati.Select((x, i) =>
                    {
                        var nomeFile = $"{x.CODICEOGGETTO}-{x.NOMEFILE}";

                        info.Logs.InfoFormat("INSERIMENTO DEL FILE CODICE: {0}, NOME: {1}, NOME FILE ORIGINALE: {2} PERCORSO DA SERVER BACK: {3}", x.CODICEOGGETTO, nomeFile, x.NOMEFILE, Path.Combine(info.ParametriRegola.BasePathLocal, Path.Combine(info.ParametriRegola.BasePathLocal, percorsoDir, nomeFile)));
                        File.WriteAllBytes(Path.Combine(info.ParametriRegola.BasePathLocal, percorsoDir, nomeFile), x.OGGETTO);
                        info.Logs.InfoFormat("INSERIMENTO DEL FILE CODICE: {0}, NOME: {1}, NOME FILE ORIGINALE: {2} PERCORSO DA SERVER BACK: {3} AVVENUTO CON SUCCESSO", x.CODICEOGGETTO, nomeFile, x.NOMEFILE, Path.Combine(info.ParametriRegola.BasePathLocal, percorsoDir, nomeFile));

                        return new AllegatiXML.Riga
                        {
                            Index = i,
                            NomeFile = nomeFile,
                            Percorso = Path.Combine(this.PercorsoDirectoryDaProtocollo, nomeFile),
                            TipoAllegato = (i == 0) ? "P" : "A"
                        };

                    }).ToArray()
                };


                var xml = info.Serializer.Serialize("AllegatiXML.xml", allegatiXml, Validation.ProtocolloValidation.TipiValidazione.PROTOCOLLOXML_PROTOINF);

                var doc = XDocument.Parse(xml);

                foreach (var element in doc.Descendants())
                {
                    if (element.Name.LocalName.StartsWith("_RIGA_IDX"))
                    {
                        var indice = element.Attribute("Index");
                        element.Name = $"_RIGA_{indice.Value}";
                        element.Attribute("Index").Remove();
                    }
                }
                return doc.ToString();
            }
        }
    }
}
