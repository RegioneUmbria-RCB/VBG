using VBG.Backend.Protocollo.AppLogic.Core.Halley.Configurations;
using VBG.Backend.Protocollo.AppLogic.Core.Halley.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Core.Halley.Builders
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

        private readonly HalleySegnaturaParamConfiguration _configuration;

        public HalleySegnaturaApplicativoProtocolloBuilder(HalleySegnaturaParamConfiguration configuration)
        {
            this._configuration = configuration;
            this.DatiApplicativoProtocollo = this.CreaDatiApplicativoProtocollo();
        }

        private List<KeyValuePair<string, string>> CreaDatiApplicativoProtocollo()
        {
            var list = new List<KeyValuePair<string, string>>();

            list.Add(new KeyValuePair<string, string>(Constants.TIPO_DOCUMENTO, this._configuration.TipoDocumento));

            list.Add(new KeyValuePair<string, string>(Constants.MITTENTE_INDIRIZZO, this._configuration.DatiIndirizzoApplicativoProtocollo.Indirizzo));
            list.Add(new KeyValuePair<string, string>(Constants.MITTENTE_LOCALITA, this._configuration.DatiIndirizzoApplicativoProtocollo.Localita));
            list.Add(new KeyValuePair<string, string>(Constants.MITTENTE_CAP, this._configuration.DatiIndirizzoApplicativoProtocollo.Cap));
            list.Add(new KeyValuePair<string, string>(Constants.MITTENTE_PROVINCIA, this._configuration.DatiIndirizzoApplicativoProtocollo.Provincia));

            list.Add(new KeyValuePair<string, string>(Constants.OPERATORE_INSERIMENTO, this._configuration.Operatore));

            if (!String.IsNullOrEmpty(this._configuration.VertParams.Uo))
                list.Add(new KeyValuePair<string, string>(Constants.UO, this._configuration.VertParams.Uo));

            return list;
        }
    }

    public class HalleySegnaturaApplicativoProtocolloIndirizziAmministrazioneBuilder : IDatiHalleySegnaturaApplicativoProtocollo
    {
        private readonly ProtocolloAmministrazioni _amministrazione;
        public HalleySegnaturaApplicativoProtocolloIndirizziAmministrazioneBuilder(ProtocolloAmministrazioni amministrazione)
        {
            this._amministrazione = amministrazione;
        }

        #region IDatiHalleySegnaturaApplicativoProtocollo Members

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

    public class HalleySegnaturaApplicativoProtocolloIndirizziAnagraficaBuilder : IDatiHalleySegnaturaApplicativoProtocollo
    {
        private readonly ProtocolloAnagrafe _anagrafe;
        public HalleySegnaturaApplicativoProtocolloIndirizziAnagraficaBuilder(ProtocolloAnagrafe anagrafe)
        {
            this._anagrafe = anagrafe;
        }

        #region IDatiHalleySegnaturaApplicativoProtocollo Members

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
