using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;


namespace VBG.Backend.Protocollo.AppLogic.Core.Delta.Services
{
    public class DeltaAllegatiService
    {
        private readonly ProtocolloLogs _logs;
        private readonly ProtocolloClientServiceCreator _protocolloClientServiceCreator;
        private readonly string _username;
        private readonly string _password;

        public DeltaAllegatiService(string url, ProtocolloLogs logs, ProtocolloSerializer serializer, IBindingFactory bindingFactory, string username, string password, string proxy)
        {
            this._logs = logs;
            this._protocolloClientServiceCreator = new ProtocolloClientServiceCreator(logs, bindingFactory, url, proxy);
            this._username = username;
            this._password = password;
            
        }

        internal ProtocolloDeltaService.Allegato GetAllegato(string codiceRegistro, string anno, string progressivo, string idAllegato)
        {
            try
            {
                int annoParsed;
                if (!int.TryParse(anno, out annoParsed))
                    throw new Exception("IL PARAMETRO ANNO DEVE AVERE UN FORMATO NUMERICO");

                int progressivoParsed;
                if (!int.TryParse(progressivo, out progressivoParsed))
                    throw new Exception("IL PARAMETRO PROGRESSIVO (NUMERO PROTOCOLLO) DEVE AVERE UN FORMATO NUMERICO");

                int idAllegatoParsed;
                if (!int.TryParse(idAllegato, out idAllegatoParsed))
                    throw new Exception("IL PARAMETRO ID ALLEGATO DEVE AVERE UN FORMATO NUMERICO");

                using (var ws = this._protocolloClientServiceCreator.CreateClient())
                {
                    _logs.InfoFormat("Lettura dell'allegato con id {0} del protoocollo numero {1}, anno {2}", idAllegato.ToString(), progressivo.ToString(), anno.ToString());
                    var response = ws.Service.getAllegato(codiceRegistro, annoParsed, progressivoParsed, idAllegatoParsed, _username, _password);
                    _logs.InfoFormat("Lettura dell'allegato con id {0} del protoocollo numero {1}, anno {2} avvenuta con successo", idAllegato, progressivo, anno);

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE IL RECUPERO DELL'ALLEGATO DAL WEB SERVICE", ex);
            }
        }

        internal void UploadAllegati(List<ProtocolloAllegati> allegati, string codiceRegistro, int annoProtocollo, int progressivoProtocollo, string dataProtocollo)
        {
            try
            {
                using (var ws = this._protocolloClientServiceCreator.CreateClient())
                {
                    foreach (var allegato in allegati)
                    {
                        string path = Path.Combine(_logs.Folder, allegato.NOMEFILE);
                        File.WriteAllBytes(path, allegato.OGGETTO);
                        
                        _logs.InfoFormat("SALVATO IL FILE {0}, CODICE ALLEGATO {1}", allegato.NOMEFILE, allegato.CODICEOGGETTO);

                        _logs.InfoFormat("CHIAMATA A METODO UPLOAD DELL'ALLEGATO CON NOME FILE {0} CODICE OGGETTO {1}", allegato.NOMEFILE, allegato.CODICEOGGETTO);
                        var response = ws.Service.uploadAllegato(codiceRegistro, annoProtocollo, progressivoProtocollo, allegato.NOMEFILE, allegato.OGGETTO, _username, _password);
                        _logs.InfoFormat("UPLOAD DELL'ALLEGATO CON NOME FILE {0} CODICE OGGETTO {1} AVVENUTO CORRETTAMENTE", allegato.NOMEFILE, allegato.CODICEOGGETTO);
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format(@"ERRORE GENERATO DURANTE L'UPLOAD DEGLI ALLEGATI, IL PROTOCOLLO PROGRESSIVO {0} ANNO {1} E' STATO QUINDI INSERITO 
                                                    SENZA TUTTE LE INFORMAZIONI RICHIESTE, E' NECESSARIO QUINDI COMPLETARE L'OPERAZIONE TRAMITE L'APPLICATIVO DI PROTOCOLLO. 
                                                    PURTROPPO, VISTO L'ERRORE, LA NUMERAZIONE NON E' STATA RIPORTATA NELL'ISTANZA, E' QUINDI NECESSARIO INSERIRLA MANUALMENTE, 
                                                    CON NUMERO {0} e DATA {2}", progressivoProtocollo, annoProtocollo, dataProtocollo), ex);
            }
        }
    }
}
