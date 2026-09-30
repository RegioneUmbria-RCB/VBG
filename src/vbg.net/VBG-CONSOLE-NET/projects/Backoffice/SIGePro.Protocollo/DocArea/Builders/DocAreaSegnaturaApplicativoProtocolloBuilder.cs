using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.DocArea.Configurations;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.DocArea.Interfaces;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.DocArea.Builders
{
    public class DocAreaSegnaturaApplicativoProtocolloBuilder
    {
        public static class Constants
        {
            public const string TIPO_SPEDIZIONE = "TipoSpedizione";
            public const string UO = "uo";
            public const string MITTENTE_INDIRIZZO = "mittenteindirizzo";
            public const string MITTENTE_LOCALITA = "mittentelocalita";
            public const string MITTENTE_CAP = "mittentecap";
            public const string MITTENTE_PROVINCIA = "mittenteprovincia";
            public const string OPERATORE_INSERIMENTO = "OperatoreInserimento";
            public const string DESCRIZIONE_INDIRIZZO = "Descrizione";
            public const string TIPO_SMISTAMENTO = "tipoSmistamento";

            /*Usate al comune di Vinci*/
            public const string FORMATO_DOCUMENTO = "FORMATODOC";
            public const string TIPO_DOCUMENTO = "TipoDocumento";
            public const string CODICE_COMUNE = "CodiceComune";
            public const string COMUNE = "Comune";
            public const string LOCALITA = "Localita";
            public const string CAP = "Cap";
            public const string SIGLA_PROVINCIA = "Provincia";
            public const string TOPONIMO = "Toponimo";
            public const string CIVICO = "Civico";
            public const string TIPODESTSOGG = "TIPODESTSOGG";
        }

        public readonly List<KeyValuePair<string, string>> DatiApplicativoProtocollo;

        private DocAreaSegnaturaParamConfiguration _configuration;

        public DocAreaSegnaturaApplicativoProtocolloBuilder(DocAreaSegnaturaParamConfiguration configuration)
        {
            _configuration = configuration;
            DatiApplicativoProtocollo = CreaDatiApplicativoProtocollo();
        }

        private List<KeyValuePair<string, string>> CreaDatiApplicativoProtocollo()
        {
            var list = new List<KeyValuePair<string, string>>();
            
            list.Add(new KeyValuePair<string, string>(Constants.TIPO_SPEDIZIONE, _configuration.TipoDocumento ?? ""));
            list.Add(new KeyValuePair<string, string>(Constants.FORMATO_DOCUMENTO, _configuration.TipoDocumento ?? ""));
            list.Add(new KeyValuePair<string, string>(Constants.TIPO_DOCUMENTO, _configuration.TipoDocumento ?? ""));
            list.Add(new KeyValuePair<string, string>(Constants.TIPO_SMISTAMENTO, _configuration.TipoSmistamento ?? ""));
            list.Add(new KeyValuePair<string, string>(Constants.MITTENTE_INDIRIZZO, _configuration.DatiIndirizzoApplicativoProtocollo.Indirizzo ?? ""));
            list.Add(new KeyValuePair<string, string>(Constants.MITTENTE_LOCALITA, _configuration.DatiIndirizzoApplicativoProtocollo.Localita ?? ""));
            list.Add(new KeyValuePair<string, string>(Constants.MITTENTE_CAP, _configuration.DatiIndirizzoApplicativoProtocollo.Cap ?? ""));
            list.Add(new KeyValuePair<string, string>(Constants.MITTENTE_PROVINCIA, _configuration.DatiIndirizzoApplicativoProtocollo.Provincia ?? ""));
            list.Add(new KeyValuePair<string, string>(Constants.OPERATORE_INSERIMENTO, _configuration.Operatore ?? ""));
            list.Add(new KeyValuePair<string, string>(Constants.CODICE_COMUNE, _configuration.DatiIndirizzoApplicativoProtocollo.CodiceIstatComune ?? ""));
            list.Add(new KeyValuePair<string, string>(Constants.COMUNE, _configuration.DatiIndirizzoApplicativoProtocollo.Comune ?? ""));
            list.Add(new KeyValuePair<string, string>(Constants.LOCALITA, _configuration.DatiIndirizzoApplicativoProtocollo.Localita ?? ""));
            list.Add(new KeyValuePair<string, string>(Constants.CAP, _configuration.DatiIndirizzoApplicativoProtocollo.Cap ?? ""));
            list.Add(new KeyValuePair<string, string>(Constants.SIGLA_PROVINCIA, _configuration.DatiIndirizzoApplicativoProtocollo.Provincia ?? ""));
            list.Add(new KeyValuePair<string, string>(Constants.DESCRIZIONE_INDIRIZZO, _configuration.DatiIndirizzoApplicativoProtocollo.DescrizioneIndirizzo ?? ""));
            list.Add(new KeyValuePair<string, string>(Constants.TOPONIMO, _configuration.DatiIndirizzoApplicativoProtocollo.Toponimo ?? ""));
            list.Add(new KeyValuePair<string, string>(Constants.TIPODESTSOGG, _configuration.DatiIndirizzoApplicativoProtocollo.ModalitaInvio ?? ""));

            if (!String.IsNullOrEmpty(_configuration.VertParams.Uo))
                list.Add(new KeyValuePair<string, string>(Constants.UO, _configuration.VertParams.Uo ?? ""));

            return list;
        }
    }

    public class DocAreaSegnaturaApplicativoProtocolloIndirizziAmministrazioneBuilder : IDatiDocAreaSegnaturaApplicativoProtocollo
    {
        Amministrazioni _amministrazione;
        public DocAreaSegnaturaApplicativoProtocolloIndirizziAmministrazioneBuilder(Amministrazioni amministrazione)
        {
            _amministrazione = amministrazione;
        }

        #region IDatiDocAreaSegnaturaApplicativoProtocollo Members

        public string Indirizzo
        {
            get { return String.IsNullOrEmpty(_amministrazione.INDIRIZZO) ? String.Empty : _amministrazione.INDIRIZZO; }
        }

        public string Localita
        {
            get { return String.IsNullOrEmpty(_amministrazione.CITTA) ? String.Empty : _amministrazione.CITTA; }
        }

        public string Provincia
        {
            get { return String.IsNullOrEmpty(_amministrazione.PROVINCIA) ? String.Empty : _amministrazione.PROVINCIA; }
        }

        public string Cap
        {
            get { return String.IsNullOrEmpty(_amministrazione.CAP) ? String.Empty : _amministrazione.CAP; }
        }

        public string DescrizioneIndirizzo
        {
            get 
            {
                string retVal = "";

                if (!String.IsNullOrEmpty(_amministrazione.INDIRIZZO))
                {
                    var arrIndirizzo = _amministrazione.INDIRIZZO.Split(' ');
                    if (arrIndirizzo.Length > 1)
                        retVal = String.Join(" ", arrIndirizzo.Skip(1).ToArray());
                }

                return retVal;
            }
        }

        public string SiglaProvincia
        {
            get { return ""; }
        }

        public string CodiceIstatComune
        {
            get { return ""; }
        }

        public string Comune
        {
            get { return _amministrazione.CITTA; }
        }

        public string Toponimo
        {
            get 
            {
                string retVal = "";

                if (!String.IsNullOrEmpty(_amministrazione.INDIRIZZO))
                {
                    var arrIndirizzo = _amministrazione.INDIRIZZO.Split(' ');
                    if (arrIndirizzo.Length > 0)
                        retVal = arrIndirizzo[0];
                }

                return retVal;
            }
        }
        
        public string Mezzo
        {
            get { return _amministrazione.Mezzo; }
        }

        public string ModalitaInvio
        {
            get { return _amministrazione.ModalitaTrasmissione; }
        }

        #endregion
    }

    public class DocAreaSegnaturaApplicativoProtocolloIndirizziAnagraficaBuilder : IDatiDocAreaSegnaturaApplicativoProtocollo
    {
        ProtocolloAnagrafe _anagrafe;
        public DocAreaSegnaturaApplicativoProtocolloIndirizziAnagraficaBuilder(ProtocolloAnagrafe anagrafe)
        {
            _anagrafe = anagrafe;
        }

        #region IDatiDocAreaSegnaturaApplicativoProtocollo Members

        public string Indirizzo
        {
            get { return String.IsNullOrEmpty(_anagrafe.INDIRIZZO) ? String.Empty : _anagrafe.INDIRIZZO; }
        }

        public string Localita
        {
            get { return String.IsNullOrEmpty(_anagrafe.CITTA) ? String.Empty : _anagrafe.CITTA; }
        }

        public string Provincia
        {
            get { return String.IsNullOrEmpty(_anagrafe.PROVINCIA) ? String.Empty : _anagrafe.PROVINCIA; }
        }

        public string Cap
        {
            get { return String.IsNullOrEmpty(_anagrafe.CAP) ? String.Empty : _anagrafe.CAP; }
        }

        public string CodiceIstatComune
        {
            get 
            {
                string retVal = "";
                if (_anagrafe.ComuneResidenza != null)
                    retVal = _anagrafe.ComuneResidenza.CODICEISTAT;

                return retVal; 
            }
        }

        public string Comune
        {
            get 
            {
                string retVal = "";
                if (_anagrafe.ComuneResidenza != null)
                    retVal = _anagrafe.ComuneResidenza.COMUNE;

                return retVal;   
            }
        }

        public string SiglaProvincia
        {
            get
            {
                string retVal = "";
                if (_anagrafe.ComuneResidenza != null)
                    retVal = _anagrafe.ComuneResidenza.SIGLAPROVINCIA;

                return retVal;
            }
        }

        public string DescrizioneIndirizzo
        {
            get 
            {
                string retVal = "";
                if (!String.IsNullOrEmpty(_anagrafe.INDIRIZZO))
                {
                    var arrIndirizzo = _anagrafe.INDIRIZZO.Split(' ');
                    if (arrIndirizzo.Length > 1)
                        retVal = String.Join(" ", arrIndirizzo.Skip(1).ToArray());
                }
                return retVal;
            }
        }

        public string Toponimo
        {
            get 
            {
                string retVal = "";
                if (!String.IsNullOrEmpty(_anagrafe.INDIRIZZO))
                {
                    var arrIndirizzo = _anagrafe.INDIRIZZO.Split(' ');
                    if (arrIndirizzo.Length > 0)
                        retVal = arrIndirizzo[0];
                }
                return retVal;
            
            }
        }

        public string Mezzo
        {
            get { return _anagrafe.Mezzo; }
        }

        public string ModalitaInvio
        {
            get { return _anagrafe.ModalitaTrasmissione; }
        }

        #endregion
    }
}
