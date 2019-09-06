using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.DocPro.Configurations;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.DocPro.Interfaces;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.DocPro.Builders
{
    public class DocProSegnaturaApplicativoProtocolloBuilder
    {
        public readonly List<KeyValuePair<string, string>> DatiApplicativoProtocollo;

        private DocProSegnaturaParamConfiguration _configuration;

        public DocProSegnaturaApplicativoProtocolloBuilder(DocProSegnaturaParamConfiguration configuration)
        {
            _configuration = configuration;
            DatiApplicativoProtocollo = CreaDatiApplicativoProtocollo();
        }

        private List<KeyValuePair<string, string>> CreaDatiApplicativoProtocollo()
        {
            var list = new List<KeyValuePair<string, string>>();

            list.Add(new KeyValuePair<string, string>("mittenteindirizzo", _configuration.DatiIndirizzoApplicativoProtocollo.Indirizzo));
            list.Add(new KeyValuePair<string, string>("mittentelocalita", _configuration.DatiIndirizzoApplicativoProtocollo.Localita));
            list.Add(new KeyValuePair<string, string>("mittentecap", _configuration.DatiIndirizzoApplicativoProtocollo.Cap));
            list.Add(new KeyValuePair<string, string>("mittenteprovincia", _configuration.DatiIndirizzoApplicativoProtocollo.Provincia));
            list.Add(new KeyValuePair<string, string>("modalitaspedizione", _configuration.Flusso == ProtocolloConstants.COD_PARTENZA_DOCAREA ? _configuration.VertParams.ModalitaTrasmissione : _configuration.VertParams.ModalitaTrasmissioneArrivo));
            list.Add(new KeyValuePair<string, string>("utenteprotocollatore", _configuration.Operatore));
            list.Add(new KeyValuePair<string, string>("uo", _configuration.VertParams.Uo));
            list.Add(new KeyValuePair<string, string>("argomento", _configuration.TipoDocumento));
            list.Add(new KeyValuePair<string, string>("dataricevimento", _configuration.DataRicevimento.ToString("yyyy-MM-dd")));

            return list;
        }
    }

    public class DocProSegnaturaApplicativoProtocolloIndirizziAmministrazioneBuilder : IDatiDocProSegnaturaApplicativoProtocollo
    {
        Amministrazioni _amministrazione;
        public DocProSegnaturaApplicativoProtocolloIndirizziAmministrazioneBuilder(Amministrazioni amministrazione)
        {
            _amministrazione = amministrazione;
        }

        #region IDatiDocProSegnaturaApplicativoProtocollo Members

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

    public class DocProSegnaturaApplicativoProtocolloIndirizziAnagraficaBuilder : IDatiDocProSegnaturaApplicativoProtocollo
    {
        Anagrafe _anagrafe;
        public DocProSegnaturaApplicativoProtocolloIndirizziAnagraficaBuilder(Anagrafe anagrafe)
        {
            _anagrafe = anagrafe;
        }

        #region IDatiDocProSegnaturaApplicativoProtocollo Members

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
