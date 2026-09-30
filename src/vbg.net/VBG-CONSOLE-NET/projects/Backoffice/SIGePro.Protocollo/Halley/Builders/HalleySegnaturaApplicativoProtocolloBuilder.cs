using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Halley.Configurations;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.Halley.Interfaces;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.Halley.Builders
{
    public class HalleySegnaturaApplicativoProtocolloBuilder
    {
        public static class Constants
        {
            public const string TIPO_DOCUMENTO = "tipoDocumento";
            public const string UO = "uo";
            public const string MITTENTE_INDIRIZZO = "mittenteindirizzo";
            public const string MITTENTE_LOCALITA = "mittentelocalita";
            public const string MITTENTE_CAP = "mittentecap";
            public const string MITTENTE_PROVINCIA = "mittenteprovincia";
            public const string OPERATORE_INSERIMENTO = "operatoreInserimento";

            
        }

        public readonly List<KeyValuePair<string, string>> DatiApplicativoProtocollo;

        private HalleySegnaturaParamConfiguration _configuration;

        public HalleySegnaturaApplicativoProtocolloBuilder(HalleySegnaturaParamConfiguration configuration)
        {
            _configuration = configuration;
            DatiApplicativoProtocollo = CreaDatiApplicativoProtocollo();
        }

        private List<KeyValuePair<string, string>> CreaDatiApplicativoProtocollo()
        {
            var list = new List<KeyValuePair<string, string>>();

            list.Add(new KeyValuePair<string, string>(Constants.TIPO_DOCUMENTO, _configuration.TipoDocumento));
            
            list.Add(new KeyValuePair<string, string>(Constants.MITTENTE_INDIRIZZO, _configuration.DatiIndirizzoApplicativoProtocollo.Indirizzo));
            list.Add(new KeyValuePair<string, string>(Constants.MITTENTE_LOCALITA, _configuration.DatiIndirizzoApplicativoProtocollo.Localita));
            list.Add(new KeyValuePair<string, string>(Constants.MITTENTE_CAP, _configuration.DatiIndirizzoApplicativoProtocollo.Cap));
            list.Add(new KeyValuePair<string, string>(Constants.MITTENTE_PROVINCIA, _configuration.DatiIndirizzoApplicativoProtocollo.Provincia));

            list.Add(new KeyValuePair<string, string>(Constants.OPERATORE_INSERIMENTO, _configuration.Operatore));
    
            if (!String.IsNullOrEmpty(_configuration.VertParams.Uo))
                list.Add(new KeyValuePair<string, string>(Constants.UO, _configuration.VertParams.Uo));

            return list;
        }
    }

    public class HalleySegnaturaApplicativoProtocolloIndirizziAmministrazioneBuilder : IDatiHalleySegnaturaApplicativoProtocollo
    {
        Amministrazioni _amministrazione;
        public HalleySegnaturaApplicativoProtocolloIndirizziAmministrazioneBuilder(Amministrazioni amministrazione)
        {
            _amministrazione = amministrazione;
        }

        #region IDatiHalleySegnaturaApplicativoProtocollo Members

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

        #endregion
    }

    public class HalleySegnaturaApplicativoProtocolloIndirizziAnagraficaBuilder : IDatiHalleySegnaturaApplicativoProtocollo
    {
        Anagrafe _anagrafe;
        public HalleySegnaturaApplicativoProtocolloIndirizziAnagraficaBuilder(Anagrafe anagrafe)
        {
            _anagrafe = anagrafe;
        }

        #region IDatiHalleySegnaturaApplicativoProtocollo Members

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

        #endregion
    }
}
