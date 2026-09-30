using System;
using System.Collections.Generic;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocPro.Configurations;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocPro.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocPro.Builders
{
    public class DocProSegnaturaApplicativoProtocolloBuilder
    {
        public readonly List<KeyValuePair<string, string>> DatiApplicativoProtocollo;

        private readonly DocProSegnaturaParamConfiguration _configuration;

        public DocProSegnaturaApplicativoProtocolloBuilder(DocProSegnaturaParamConfiguration configuration)
        {
            this._configuration = configuration;
            this.DatiApplicativoProtocollo = this.CreaDatiApplicativoProtocollo();
        }

        private List<KeyValuePair<string, string>> CreaDatiApplicativoProtocollo()
        {
            var list = new List<KeyValuePair<string, string>>();

            list.Add(new KeyValuePair<string, string>("mittenteindirizzo", this._configuration.DatiIndirizzoApplicativoProtocollo.Indirizzo));
            list.Add(new KeyValuePair<string, string>("mittentelocalita", this._configuration.DatiIndirizzoApplicativoProtocollo.Localita));
            list.Add(new KeyValuePair<string, string>("mittentecap", this._configuration.DatiIndirizzoApplicativoProtocollo.Cap));
            list.Add(new KeyValuePair<string, string>("mittenteprovincia", this._configuration.DatiIndirizzoApplicativoProtocollo.Provincia));
            list.Add(new KeyValuePair<string, string>("modalitaspedizione", this._configuration.Flusso == ProtocolloConstants.COD_PARTENZA_DOCAREA ? this._configuration.VertParams.ModalitaTrasmissione : this._configuration.VertParams.ModalitaTrasmissioneArrivo));
            list.Add(new KeyValuePair<string, string>("utenteprotocollatore", this._configuration.Operatore));
            list.Add(new KeyValuePair<string, string>("uo", this._configuration.VertParams.Uo));
            list.Add(new KeyValuePair<string, string>("argomento", this._configuration.TipoDocumento));
            list.Add(new KeyValuePair<string, string>("dataricevimento", this._configuration.DataRicevimento.ToString("yyyy-MM-dd")));

            return list;
        }
    }

    public class DocProSegnaturaApplicativoProtocolloIndirizziAmministrazioneBuilder : IDatiDocProSegnaturaApplicativoProtocollo
    {
        private readonly ProtocolloAmministrazioni _amministrazione;
        public DocProSegnaturaApplicativoProtocolloIndirizziAmministrazioneBuilder(ProtocolloAmministrazioni amministrazione)
        {
            this._amministrazione = amministrazione;
        }

        #region IDatiDocProSegnaturaApplicativoProtocollo Members

        public string Indirizzo
        {
            get { return String.IsNullOrEmpty(this._amministrazione.INDIRIZZO) ? String.Empty : this._amministrazione.INDIRIZZO; }
        }

        public string Localita
        {
            get { return String.IsNullOrEmpty(this._amministrazione.CITTA) ? String.Empty : this._amministrazione.CITTA; }
        }

        public string Provincia
        {
            get { return String.IsNullOrEmpty(this._amministrazione.PROVINCIA) ? String.Empty : this._amministrazione.PROVINCIA; }
        }

        public string Cap
        {
            get { return String.IsNullOrEmpty(this._amministrazione.CAP) ? String.Empty : this._amministrazione.CAP; }
        }

        #endregion
    }

    public class DocProSegnaturaApplicativoProtocolloIndirizziAnagraficaBuilder : IDatiDocProSegnaturaApplicativoProtocollo
    {
        private readonly ProtocolloAnagrafe _anagrafe;
        public DocProSegnaturaApplicativoProtocolloIndirizziAnagraficaBuilder(ProtocolloAnagrafe anagrafe)
        {
            this._anagrafe = anagrafe;
        }

        #region IDatiDocProSegnaturaApplicativoProtocollo Members

        public string Indirizzo
        {
            get { return String.IsNullOrEmpty(this._anagrafe.INDIRIZZO) ? String.Empty : this._anagrafe.INDIRIZZO; }
        }

        public string Localita
        {
            get { return String.IsNullOrEmpty(this._anagrafe.CITTA) ? String.Empty : this._anagrafe.CITTA; }
        }

        public string Provincia
        {
            get { return String.IsNullOrEmpty(this._anagrafe.PROVINCIA) ? String.Empty : this._anagrafe.PROVINCIA; }
        }

        public string Cap
        {
            get { return String.IsNullOrEmpty(this._anagrafe.CAP) ? String.Empty : this._anagrafe.CAP; }
        }

        #endregion
    }
}
