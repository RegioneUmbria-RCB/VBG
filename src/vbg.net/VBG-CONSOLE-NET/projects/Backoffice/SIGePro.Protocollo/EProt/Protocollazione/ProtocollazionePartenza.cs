using Init.SIGePro.Manager.Utils;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.EProt.Protocollazione
{
    public class ProtocollazionePartenza : IProtocollazione
    {
        private class Constants
        {
            public const string UtenteProt = "UTENTE_PROT";
            public const string UtenteMitt = "UTENTE_MITT";
            public const string Password = "PASSWORD";
            public const string Registro = "REGISTRO";
            public const string TipoDestinatario = "TIPO_DESTINATARIO";
            public const string NomeDestinatario = "NOME";
            public const string CognomeDestinatario = "COGNOME";
            public const string RagioneSocialeDestinatario = "RAGIONE_SOCIALE";
            public const string Oggetto = "OGGETTO";
            public const string TipoDocumento = "TIPO_DOCUMENTO";
            public const string Titolario = "TITOLARIO";
            public const string CodiceUtente = "CODICE_UTENTE";
            public const string File64_1 = "FILE_ENCODED";
            public const string NomeFile_1 = "FILE_ENCODED_NAME";
            public const string File64_2 = "FILE_ENCODED_2";
            public const string NomeFile_2 = "FILE_ENCODED_2_NAME";
        }

        IDatiProtocollo _datiProtocollo;
        VerticalizzazioniConfiguration _vert;
        IAnagraficaAmministrazione _destinatario;

        public ProtocollazionePartenza(IDatiProtocollo datiProtocollo, VerticalizzazioniConfiguration vert, IAnagraficaAmministrazione destinatario)
        {
            _datiProtocollo = datiProtocollo;
            _vert = vert;
            _destinatario = destinatario;
        }

        public IEnumerable<KeyValuePair<string, string>> GetParametri()
        {
            var retVal = new List<KeyValuePair<string, string>>();

            retVal.Add(new KeyValuePair<string, string>(Constants.UtenteProt, _vert.Username));
            retVal.Add(new KeyValuePair<string, string>(Constants.UtenteMitt, _vert.Username));
            retVal.Add(new KeyValuePair<string, string>(Constants.Password, _vert.Password));
            retVal.Add(new KeyValuePair<string, string>(Constants.Registro, _vert.Registro));
            retVal.Add(new KeyValuePair<string, string>(Constants.TipoDestinatario, _destinatario.Tipo));

            if (_destinatario.Tipo == "F")
            {
                retVal.Add(new KeyValuePair<string, string>(Constants.NomeDestinatario, _destinatario.Nome));
                retVal.Add(new KeyValuePair<string, string>(Constants.CognomeDestinatario, _destinatario.Cognome));
            }
            else
                retVal.Add(new KeyValuePair<string, string>(Constants.RagioneSocialeDestinatario, _destinatario.Denominazione));

            retVal.Add(new KeyValuePair<string, string>(Constants.Oggetto, _datiProtocollo.ProtoIn.Oggetto));
            retVal.Add(new KeyValuePair<string, string>(Constants.TipoDocumento, _datiProtocollo.ProtoIn.TipoDocumento));
            
            if (!_vert.EscludiClassifica)
                retVal.Add(new KeyValuePair<string, string>(Constants.Titolario, _datiProtocollo.ProtoIn.Classifica));
            
            retVal.Add(new KeyValuePair<string, string>(Constants.CodiceUtente, _destinatario.CodiceFiscale));

            if (_datiProtocollo.ProtoIn.Allegati.Count() > 0)
            {
                var allegatiAccettati = _datiProtocollo.ProtoIn.Allegati.Where(x =>
                {
                    if (String.IsNullOrEmpty(x.Extension) || x.Extension.Length == 1)
                        return false;

                    return _vert.EstensioniAllegatiAccettate.Contains(x.Extension.Substring(1).ToLower());
                });

                if (allegatiAccettati.Count() > 0)
                {
                    var allegato_1 = allegatiAccettati.First();
                    retVal.Add(new KeyValuePair<string, string>(Constants.File64_1, Base64Utils.Base64Encode(allegato_1.OGGETTO)));
                    retVal.Add(new KeyValuePair<string, string>(Constants.NomeFile_1, allegato_1.NOMEFILE));
                }

                if (allegatiAccettati.Count() > 1)
                {
                    var allegato_2 = allegatiAccettati.ToArray()[1];

                    retVal.Add(new KeyValuePair<string, string>(Constants.File64_2, Base64Utils.Base64Encode(allegato_2.OGGETTO)));
                    retVal.Add(new KeyValuePair<string, string>(Constants.NomeFile_2, allegato_2.NOMEFILE));
                }
            }

            return retVal;
        }

        public string Metodo
        {
            get { return String.Format("{0}/{1}", _vert.UrlBase.TrimEnd('/'), "richiesta_protocollazione_uscita"); }
        }


        public void Valida(IEnumerable<KeyValuePair<string, string>> parametri, Logs.ProtocolloLogs log)
        {
            var isFileExist = parametri.Where(x => x.Key == Constants.NomeFile_1).Count() > 0;
            if (!isFileExist)
                log.WarnFormat("FILE ALLEGATO PRINCIPALE NON INVIATO, VERIFICARE LA PRESENZA DEL FILE E LE ESTENSIONI UTILIZZABILI, CHE SONO: {0}", String.Join(";", _vert.EstensioniAllegatiAccettate));

            var isTitolarioExist = parametri.Where(x => x.Key == Constants.Titolario).Count() > 0;
            if (!isTitolarioExist)
                log.Warn("TITOLARIO NON INVIATO");
        }
    }
}
