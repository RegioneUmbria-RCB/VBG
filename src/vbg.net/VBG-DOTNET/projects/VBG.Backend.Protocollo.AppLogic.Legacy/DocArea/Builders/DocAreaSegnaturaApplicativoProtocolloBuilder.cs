using System;
using System.Collections.Generic;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Configurations;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Builders
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

        private readonly DocAreaSegnaturaParamConfiguration _configuration;

        public DocAreaSegnaturaApplicativoProtocolloBuilder(DocAreaSegnaturaParamConfiguration configuration)
        {
            this._configuration = configuration;
            this.DatiApplicativoProtocollo = this.CreaDatiApplicativoProtocollo();
        }

        private List<KeyValuePair<string, string>> CreaDatiApplicativoProtocollo()
        {
            var list = new List<KeyValuePair<string, string>>();

            list.Add(new KeyValuePair<string, string>(Constants.TIPO_SPEDIZIONE, this._configuration.TipoDocumento ?? ""));
            list.Add(new KeyValuePair<string, string>(Constants.FORMATO_DOCUMENTO, this._configuration.TipoDocumento ?? ""));
            list.Add(new KeyValuePair<string, string>(Constants.TIPO_DOCUMENTO, this._configuration.TipoDocumento ?? ""));
            list.Add(new KeyValuePair<string, string>(Constants.TIPO_SMISTAMENTO, this._configuration.TipoSmistamento ?? ""));
            list.Add(new KeyValuePair<string, string>(Constants.MITTENTE_INDIRIZZO, this._configuration.DatiIndirizzoApplicativoProtocollo.Indirizzo ?? ""));
            list.Add(new KeyValuePair<string, string>(Constants.MITTENTE_LOCALITA, this._configuration.DatiIndirizzoApplicativoProtocollo.Localita ?? ""));
            list.Add(new KeyValuePair<string, string>(Constants.MITTENTE_CAP, this._configuration.DatiIndirizzoApplicativoProtocollo.Cap ?? ""));
            list.Add(new KeyValuePair<string, string>(Constants.MITTENTE_PROVINCIA, this._configuration.DatiIndirizzoApplicativoProtocollo.Provincia ?? ""));
            list.Add(new KeyValuePair<string, string>(Constants.OPERATORE_INSERIMENTO, this._configuration.Operatore ?? ""));
            list.Add(new KeyValuePair<string, string>(Constants.CODICE_COMUNE, this._configuration.DatiIndirizzoApplicativoProtocollo.CodiceIstatComune ?? ""));
            list.Add(new KeyValuePair<string, string>(Constants.COMUNE, this._configuration.DatiIndirizzoApplicativoProtocollo.Comune ?? ""));
            list.Add(new KeyValuePair<string, string>(Constants.LOCALITA, this._configuration.DatiIndirizzoApplicativoProtocollo.Localita ?? ""));
            list.Add(new KeyValuePair<string, string>(Constants.CAP, this._configuration.DatiIndirizzoApplicativoProtocollo.Cap ?? ""));
            list.Add(new KeyValuePair<string, string>(Constants.SIGLA_PROVINCIA, this._configuration.DatiIndirizzoApplicativoProtocollo.Provincia ?? ""));
            list.Add(new KeyValuePair<string, string>(Constants.DESCRIZIONE_INDIRIZZO, this._configuration.DatiIndirizzoApplicativoProtocollo.DescrizioneIndirizzo ?? ""));
            list.Add(new KeyValuePair<string, string>(Constants.TOPONIMO, this._configuration.DatiIndirizzoApplicativoProtocollo.Toponimo ?? ""));
            list.Add(new KeyValuePair<string, string>(Constants.TIPODESTSOGG, this._configuration.DatiIndirizzoApplicativoProtocollo.ModalitaInvio ?? ""));

            if (!String.IsNullOrEmpty(this._configuration.VertParams.Uo))
                list.Add(new KeyValuePair<string, string>(Constants.UO, this._configuration.VertParams.Uo ?? ""));

            return list;
        }
    }

    public class DocAreaSegnaturaApplicativoProtocolloIndirizziAmministrazioneBuilder : IDatiDocAreaSegnaturaApplicativoProtocollo
    {
        private readonly ProtocolloAmministrazioni _amministrazione;
        public DocAreaSegnaturaApplicativoProtocolloIndirizziAmministrazioneBuilder(ProtocolloAmministrazioni amministrazione)
        {
            this._amministrazione = amministrazione;
        }

        #region IDatiDocAreaSegnaturaApplicativoProtocollo Members

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

        public string DescrizioneIndirizzo
        {
            get
            {
                var retVal = "";

                if (!String.IsNullOrEmpty(this._amministrazione.INDIRIZZO))
                {
                    var arrIndirizzo = this._amministrazione.INDIRIZZO.Split(' ');
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
            get { return this._amministrazione.CITTA; }
        }

        public string Toponimo
        {
            get
            {
                var retVal = "";

                if (!String.IsNullOrEmpty(this._amministrazione.INDIRIZZO))
                {
                    var arrIndirizzo = this._amministrazione.INDIRIZZO.Split(' ');
                    if (arrIndirizzo.Length > 0)
                        retVal = arrIndirizzo[0];
                }

                return retVal;
            }
        }

        public string Mezzo
        {
            get { return this._amministrazione.Mezzo; }
        }

        public string ModalitaInvio
        {
            get { return this._amministrazione.ModalitaTrasmissione; }
        }

        #endregion
    }

    public class DocAreaSegnaturaApplicativoProtocolloIndirizziAnagraficaBuilder : IDatiDocAreaSegnaturaApplicativoProtocollo
    {
        private readonly ProtocolloAnagrafe _anagrafe;
        public DocAreaSegnaturaApplicativoProtocolloIndirizziAnagraficaBuilder(ProtocolloAnagrafe anagrafe)
        {
            this._anagrafe = anagrafe;
        }

        #region IDatiDocAreaSegnaturaApplicativoProtocollo Members

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

        public string CodiceIstatComune
        {
            get
            {
                var retVal = "";
                if (this._anagrafe.ComuneResidenza != null)
                    retVal = this._anagrafe.ComuneResidenza.CodiceIstat;

                return retVal;
            }
        }

        public string Comune
        {
            get
            {
                var retVal = "";
                if (this._anagrafe.ComuneResidenza != null)
                    retVal = this._anagrafe.ComuneResidenza.DenominazioneComune;

                return retVal;
            }
        }

        public string SiglaProvincia
        {
            get
            {
                var retVal = "";
                if (this._anagrafe.ComuneResidenza != null)
                    retVal = this._anagrafe.ComuneResidenza.SiglaProvincia;

                return retVal;
            }
        }

        public string DescrizioneIndirizzo
        {
            get
            {
                var retVal = "";
                if (!String.IsNullOrEmpty(this._anagrafe.INDIRIZZO))
                {
                    var arrIndirizzo = this._anagrafe.INDIRIZZO.Split(' ');
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
                var retVal = "";
                if (!String.IsNullOrEmpty(this._anagrafe.INDIRIZZO))
                {
                    var arrIndirizzo = this._anagrafe.INDIRIZZO.Split(' ');
                    if (arrIndirizzo.Length > 0)
                        retVal = arrIndirizzo[0];
                }
                return retVal;

            }
        }

        public string Mezzo
        {
            get { return this._anagrafe.Mezzo; }
        }

        public string ModalitaInvio
        {
            get { return this._anagrafe.ModalitaTrasmissione; }
        }

        #endregion
    }
}
