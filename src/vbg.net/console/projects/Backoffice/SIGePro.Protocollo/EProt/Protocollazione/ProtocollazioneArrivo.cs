using Init.SIGePro.Data;
using Init.SIGePro.Manager.Utils;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.EProt.Protocollazione
{
    public class ProtocollazioneArrivo : IProtocollazione
    {
        private class Constants
        {
            public const string UtenteProt = "UTENTE_PROT";
            public const string Password = "PASSWORD";
            public const string TipoAssegnatario = "TIPO_ASSEGNATARIO";
            public const string Registro = "REGISTRO";
            public const string UfficioAssegnatario = "UFFICIO_ASSEGNATARIO";
            public const string TipoMittente = "TIPO_MITTENTE";
            public const string NomeMittente = "NOME";
            public const string CognomeMittente = "COGNOME";
            public const string RagioneSocialeMittente = "RAGIONE_SOCIALE";
            public const string Oggetto = "OGGETTO";
            public const string TipoDocumento = "TIPO_DOCUMENTO";
            public const string Titolario = "TITOLARIO";
            public const string CodiceUtente = "CODICE_UTENTE";
            public const string File64 = "FILE_ENCODED";
            public const string NomeFile = "FILE_ENCODED_NAME";
        }

        IDatiProtocollo _datiProtocollo;
        VerticalizzazioniConfiguration _vert;
        IAnagraficaAmministrazione _mittente;

        public ProtocollazioneArrivo(IDatiProtocollo datiProtocollo, VerticalizzazioniConfiguration vert, IAnagraficaAmministrazione mittente)
        {
            _datiProtocollo = datiProtocollo;
            _vert = vert;
            _mittente = mittente;
        }

        public IEnumerable<KeyValuePair<string, string>> GetParametri()
        {
            var retVal = new List<KeyValuePair<string, string>>();

            retVal.Add(new KeyValuePair<string,string>(Constants.UtenteProt, _vert.Username));
            retVal.Add(new KeyValuePair<string, string>(Constants.Password, _vert.Password));
            retVal.Add(new KeyValuePair<string, string>(Constants.Registro, _vert.Registro));
            retVal.Add(new KeyValuePair<string, string>(Constants.TipoAssegnatario, Enumerators.TipoAssegnatario.UFFICIO.ToString()));
            retVal.Add(new KeyValuePair<string, string>(Constants.UfficioAssegnatario, _datiProtocollo.Uo));
            retVal.Add(new KeyValuePair<string, string>(Constants.TipoMittente, _mittente.Tipo));

            if (_mittente.Tipo == "F")
            {
                retVal.Add(new KeyValuePair<string, string>(Constants.NomeMittente, _mittente.Nome));
                retVal.Add(new KeyValuePair<string, string>(Constants.CognomeMittente, _mittente.Cognome));
            }
            else
                retVal.Add(new KeyValuePair<string, string>(Constants.RagioneSocialeMittente, _mittente.Denominazione));

            retVal.Add(new KeyValuePair<string, string>(Constants.Oggetto, _datiProtocollo.ProtoIn.Oggetto));
            retVal.Add(new KeyValuePair<string, string>(Constants.TipoDocumento, _datiProtocollo.ProtoIn.TipoDocumento));
            
            if(!_vert.EscludiClassifica)
                retVal.Add(new KeyValuePair<string, string>(Constants.Titolario, _datiProtocollo.ProtoIn.Classifica));
            
            retVal.Add(new KeyValuePair<string, string>(Constants.CodiceUtente, _mittente.CodiceFiscale));

            if(_datiProtocollo.ProtoIn.Allegati.Count() > 0)
            {
                var allegatiAccettati = _datiProtocollo.ProtoIn.Allegati.Where(x => 
                {
                    if (String.IsNullOrEmpty(x.Extension) || x.Extension.Length == 1)
                        return false;

                    return _vert.EstensioniAllegatiAccettate.Contains(x.Extension.Substring(1).ToLower());
                });

                if (allegatiAccettati.Count() > 0)
                {
                    var allegato = allegatiAccettati.First();
                    retVal.Add(new KeyValuePair<string, string>(Constants.File64, Base64Utils.Base64Encode(allegato.OGGETTO)));
                    retVal.Add(new KeyValuePair<string, string>(Constants.NomeFile, allegato.NOMEFILE));
                }
            }

            return retVal;
        }

        public void Valida(IEnumerable<KeyValuePair<string, string>> parametri, ProtocolloLogs log)
        {
            var isFileExist = parametri.Where(x => x.Key == Constants.NomeFile).Count() > 0;
            if (!isFileExist)
                log.WarnFormat("FILE ALLEGATO NON INVIATO, VERIFICARE LA PRESENZA DEL FILE E LE ESTENSIONI UTILIZZABILI, CHE SONO: {0}", String.Join(";", _vert.EstensioniAllegatiAccettate));

            var isTitolarioExist = parametri.Where(x => x.Key == Constants.Titolario).Count() > 0;
            if(!isTitolarioExist)
                log.Warn("TITOLARIO NON INVIATO");
        }

        public string Metodo
        {
            get { return String.Format("{0}/{1}", _vert.UrlBase.TrimEnd('/'), "richiesta_protocollazione_ingresso"); }
        }
    }
}
