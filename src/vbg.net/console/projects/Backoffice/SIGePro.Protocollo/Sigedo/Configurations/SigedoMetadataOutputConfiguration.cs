using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Sigedo.Proxies;
using Init.SIGePro.Protocollo.Sigedo.Proxies.QueryService;

namespace Init.SIGePro.Protocollo.Sigedo.Configurations
{
    public class SigedoMetadataOutputConfiguration
    {
        Metadato[] _metadati;
        public readonly Dictionary<string, string> Dictionary;


        public const string ID_DOCUMENTO = "ID_DOCUMENTO";
        public const string ANNULLATO = "ANNULLATO";
        public const string DATA = "DATA";
        public const string FLUSSO = "MODALITA";
        public const string NUMERO_PROTOCOLLO = "NUMERO";
        public const string OGGETTO = "OGGETTO";
        public const string ANNO = "ANNO";
        public const string CODICE_CLASSIFICA = "CLASS_COD";
        public const string NUMERO_FASCICOLO = "FASCICOLO_NUMERO";
        public const string ANNO_FASCICOLO = "FASCICOLO_ANNO";
        public const string OGGETTO_FASCICOLO = "FASCICOLO_OGGETTO";
        public const string CODICE_UFFICIO = "UNITA_PROTOCOLLANTE";
        public const string IDRIF = "IDRIF";
        public const string COGNOME = "COGNOME";
        public const string NOME = "NOME";
        public const string PARTITA_IVA = "PARTITA_IVA";
        public const string CODICE_FISCALE = "CF_PER_SEGNATURA";
        public const string CODICE_UFFICIO_SMISTAMENTO = "UFFICIO_SMISTAMENTO";
        public const string DESCRIZIONE_UFFICIO_SMISTAMENTO = "DES_UFFICIO_SMISTAMENTO";
        public const string DESCRIZIONE_UFFICIO_TRASMISSIONE = "DES_UFFICIO_TRASMISSIONE";
        public const string DESCRIZIONE_CLASSIFICA = "CLASS_DESCR";

        public const string ARRIVO = "ARR";
        public const string PARTENZA = "PAR";
        public const string INTERNO = "INT";

        public SigedoMetadataOutputConfiguration(Metadato[] metadati)
        {
            _metadati = metadati;
            Dictionary = CreaMetadati();
        }

        private Dictionary<string, string> CreaMetadati()
        {
            var dic = new Dictionary<string, string>();
            foreach (var metadato in _metadati)
                dic[metadato.codice] = metadato.valore;

            return dic;
        }

        public string GetValue(string parametro)
        {
            var retVal = this.Dictionary.ContainsKey(parametro) && this.Dictionary[parametro] != null ? this.Dictionary[parametro].ToString() : String.Empty;
            return retVal;
        }
    }
}
