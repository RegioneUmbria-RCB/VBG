using Init.SIGePro.Data;
using System.Runtime.Serialization;
using System.Text;
using System.Xml.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Data
{
    [DataContract]
    public class ProtocolloComune
    {
        [XmlElement(Order = 0), DataMember(Order = 0)]
        public string DenominazioneComune { get; set; }
        [XmlElement(Order = 1), DataMember(Order = 1)]
        public string SiglaProvincia { get; set; }
        [XmlElement(Order = 2), DataMember(Order = 2)]
        public string CodiceStatoEstero { get; set; }
        [XmlElement(Order = 3), DataMember(Order = 3)]
        public string CodiceIstat { get; set; }
        [XmlElement(Order = 4), DataMember(Order = 4)]
        public string Provincia { get; set; }

        public ProtocolloComune()
        {

        }



        private ProtocolloComune(Comuni comune)
        {
            this.DenominazioneComune = comune.COMUNE;
            this.SiglaProvincia = comune.SIGLAPROVINCIA;
            this.Provincia = comune.PROVINCIA;
            this.CodiceStatoEstero = comune.CODICESTATOESTERO ?? "";
            this.CodiceIstat = this.AdattaCodiceIstat(comune.CODICEISTAT);
        }

        private string AdattaCodiceIstat(string codiceIstat)
        {
            codiceIstat ??= "";

            if (!string.IsNullOrEmpty(codiceIstat))
            {
                if (codiceIstat.IndexOf('0') == 0)
                {
                    return codiceIstat.TrimStart('0');
                }
            }

            return codiceIstat;
        }

        internal static ProtocolloComune FromComuni(Comuni comune)
        {
            if (comune == null)
            {
                return null;
            }
            return new ProtocolloComune(comune);
        }
    }

    /// <summary>
    /// Descrizione di riepilogo per ProtocolloAnagrafe.
    /// </summary>
    public class ProtocolloAnagrafe
    {
        public enum TipoGestionePecEnum { DEFAULT = 0, DA_DATI_ANAGRAFICI = 1, DA_DATI_ANAGRAFICI_DOMICILIO = 2, DA_DATI_ANAGRAFICI_DOMICILIO_TECNICO = 3, DA_DATI_ANAGRAFICI_AZIENDA_TECNICO = 4 };

        [XmlElement(Order = 0)]
        public string CODICEANAGRAFE { get; set; }

        [XmlElement(Order = 2)]
        public string NOMINATIVO { get; set; }

        /// <summary>
        /// Indirizzo di residenza
        /// </summary>
        [XmlElement(Order = 6)]
        public string INDIRIZZO { get; set; }

        /// <summary>
        /// Città residenza
        /// </summary>
        [XmlElement(Order = 7)]
        public string CITTA { get; set; }

        /// <summary>
        /// CAP residenza
        /// </summary>
        [XmlElement(Order = 8)]
        public string CAP { get; set; }

        /// <summary>
        /// Provincia residenza
        /// </summary>
        [XmlElement(Order = 9)]
        public string PROVINCIA { get; set; }

        /// <summary>
        /// Telefono
        /// </summary>
        [XmlElement(Order = 10)]
        public string TELEFONO { get; set; }

        /// <summary>
        /// Cellulare
        /// </summary>
        [XmlElement(Order = 11)]
        public string TELEFONOCELLULARE { get; set; }

        /// <summary>
        /// Fax
        /// </summary>
        [XmlElement(Order = 12)]
        public string FAX { get; set; }

        private string _partitaiva = null;
        /// <summary>
        /// Partita IVA
        /// </summary>
        [XmlElement(Order = 13)]
        public string PARTITAIVA
        {
            get { return this._partitaiva; }
            set
            {
                this._partitaiva = value;
                if (!String.IsNullOrEmpty(this._partitaiva))
                    this._partitaiva = this._partitaiva.ToUpper();
            }
        }

        private string _codicefiscale = null;
        /// <summary>
        /// Codice fiscale
        /// </summary>
        [XmlElement(Order = 14)]
        public string CODICEFISCALE
        {
            get { return this._codicefiscale; }
            set
            {
                this._codicefiscale = value;
                if (!String.IsNullOrEmpty(this._codicefiscale))
                    this._codicefiscale = this._codicefiscale.ToUpper();
            }
        }

        /// <summary>
        /// Indirizzo Email
        /// </summary>
        [XmlElement(Order = 16)]
        public string EMAIL { get; set; }

        /// <summary>
        /// Codice del comune di nascita (fk su COMUNI.CODICECOMUNE)
        /// </summary>
        [XmlElement(Order = 21)]
        public string CODCOMNASCITA { get; set; } = null;

        /// <summary>
        /// Data di nascita
        /// </summary>
        [XmlElement(Order = 22)]
        public DateTime? DATANASCITA { get; set; }

        /// <summary>
        /// Sesso: M o F
        /// </summary>
        [XmlElement(Order = 26)]
        public string SESSO { get; set; } = null;

        /// <summary>
        /// Nome (solo per persone fisiche)
        /// </summary>
        [XmlElement(Order = 27)]
        public string NOME { get; set; } = null;

        /// <summary>
        /// Id del titolo (fk su TITOLI.CODICETITOLO insieme a IDCOMUNE)
        /// </summary>
        [XmlElement(Order = 28)]
        public string TITOLO { get; set; } = null;

        /// <summary>
        /// Specifica se l'anagrafica è una persona fisica (F) o giuridica (G)
        /// </summary>
        [XmlElement(Order = 29)]
        public string TIPOANAGRAFE { get; set; } = null;

        /// <summary>
        /// Data di costituzione (solo per persone giuridiche)
        /// </summary>
        [XmlElement(Order = 30)]
        public DateTime? DATANOMINATIVO { get; set; }

        /// <summary>
        /// Codice del comune di residenza (fk su COMUNI.CODICECOMUNE)
        /// </summary>
        [XmlElement(Order = 33)]
        public string COMUNERESIDENZA { get; set; } = null;

        /// <summary>
        /// INdirizzo PEC per le comunicazioni
        /// </summary>
        [XmlElement(Order = 50)]
        public string PecProtocollazione { get; set; }

        /// <summary>
        /// Risoluzione della FK COMUNERESIDENZA (dati del comune di residenza)
        /// </summary>
        [XmlElement(Order = 62)]
        public ProtocolloComune ComuneResidenza { get; set; } = null;

        /// <summary>
        /// Ottiene il nome completo dell'anagrafica nella forma "COGNOME NOME"
        /// </summary>
        /// <returns></returns>
        public string GetNomeCompleto()
        {
            var sb = new StringBuilder();

            sb.Append(this.NOMINATIVO.Trim());

            if (!String.IsNullOrEmpty(this.NOME))
                sb.Append(" ").Append(this.NOME.Trim());

            return sb.ToString();
        }

        public void SetPec(Istanze istanza, TipoGestionePecEnum tipoGestionePec)
        {
            if (tipoGestionePec == TipoGestionePecEnum.DA_DATI_ANAGRAFICI)
            {
                return;
            }

            if (string.IsNullOrEmpty(istanza.DOMICILIO_ELETTRONICO))
            {
                return;
            }

            switch (tipoGestionePec)
            {
                case TipoGestionePecEnum.DA_DATI_ANAGRAFICI_DOMICILIO:
                    if (string.IsNullOrEmpty(this.PecProtocollazione) && (this.CODICEANAGRAFE == istanza.CODICERICHIEDENTE || this.CODICEANAGRAFE == istanza.CODICETITOLARELEGALE))
                    {
                        this.PecProtocollazione = istanza.DOMICILIO_ELETTRONICO;
                    }
                    break;
                case TipoGestionePecEnum.DA_DATI_ANAGRAFICI_DOMICILIO_TECNICO:
                    if (string.IsNullOrEmpty(this.PecProtocollazione) && this.CODICEANAGRAFE == istanza.CODICETITOLARELEGALE)
                    {
                        this.PecProtocollazione = istanza.DOMICILIO_ELETTRONICO;
                    }
                    break;
                case TipoGestionePecEnum.DA_DATI_ANAGRAFICI_AZIENDA_TECNICO:
                    if (this.CODICEANAGRAFE == istanza.CODICEPROFESSIONISTA)
                    {
                        this.PecProtocollazione = istanza.DOMICILIO_ELETTRONICO;
                    }

                    if (this.CODICEANAGRAFE == istanza.CODICERICHIEDENTE && string.IsNullOrEmpty(istanza.CODICEPROFESSIONISTA))
                    {
                        this.PecProtocollazione = istanza.DOMICILIO_ELETTRONICO;
                    }

                    if (this.CODICEANAGRAFE == istanza.CODICETITOLARELEGALE && string.IsNullOrEmpty(istanza.CODICEPROFESSIONISTA))
                    {
                        this.PecProtocollazione = istanza.DOMICILIO_ELETTRONICO;
                    }

                    break;
                default:
                    if (this.CODICEANAGRAFE == istanza.CODICERICHIEDENTE || this.CODICEANAGRAFE == istanza.CODICETITOLARELEGALE)
                    {
                        this.PecProtocollazione = istanza.DOMICILIO_ELETTRONICO;
                    }
                    break;
            }
        }

        internal static ProtocolloAnagrafe? FromAnagrafe(Anagrafe? anag, string pecProtocolloazione, IComuniRepository comuniRepository)
        {
            if (anag == null)
            {
                return null;
            }

            var protoAnag = new ProtocolloAnagrafe
            {
                CAP = anag.CAP,
                CITTA = anag.CITTA,
                CODCOMNASCITA = anag.CODCOMNASCITA,
                CODICEANAGRAFE = anag.CODICEANAGRAFE,
                CODICEFISCALE = anag.CODICEFISCALE,
                COMUNERESIDENZA = anag.COMUNERESIDENZA,
                DATANASCITA = anag.DATANASCITA,
                DATANOMINATIVO = anag.DATANOMINATIVO,
                EMAIL = anag.EMAIL,

                PecProtocollazione = String.IsNullOrEmpty(pecProtocolloazione) ? anag.Pec : pecProtocolloazione,
                PecAnagrafica = anag.Pec,

                FAX = anag.FAX,
                INDIRIZZO = anag.INDIRIZZO,
                NOME = anag.NOME,
                NOMINATIVO = anag.NOMINATIVO,
                PARTITAIVA = anag.PARTITAIVA,
                PROVINCIA = anag.PROVINCIA,
                SESSO = anag.SESSO,
                TELEFONO = anag.TELEFONO,
                TELEFONOCELLULARE = anag.TELEFONOCELLULARE,
                TIPOANAGRAFE = anag.TIPOANAGRAFE,
                TITOLO = anag.TITOLO,
            };

            if (!String.IsNullOrEmpty(anag.CODCOMNASCITA))
            {
                var comune = comuniRepository.RepositoryGetComuneById(anag.CODCOMNASCITA);

                protoAnag.CodiceIstatComNasc = comune.CodiceIstat;
                protoAnag.CodiceStatoEsteroNasc = comune.CodiceStatoEstero;
            }

            if (!String.IsNullOrEmpty(anag.COMUNERESIDENZA))
            {
                var comune = comuniRepository.RepositoryGetComuneById(anag.COMUNERESIDENZA);

                protoAnag.CodiceIstatComRes = comune.CodiceIstat;
                protoAnag.CodiceStatoEsteroRes = comune.CodiceStatoEstero;
                protoAnag.ComuneResidenza = comune;
            }

            return protoAnag;
        }

        [XmlElement(Order = 65)]
        public string CodiceIstatComRes { get; set; }

        [XmlElement(Order = 66)]
        public string CodiceIstatComNasc { get; set; }

        [XmlElement(Order = 67)]
        public string CodiceStatoEsteroRes { get; set; }

        [XmlElement(Order = 68)]
        public string CodiceStatoEsteroNasc { get; set; }

        [XmlElement(Order = 69)]
        public string Mezzo { get; set; }

        [XmlElement(Order = 70)]
        public string ModalitaTrasmissione { get; set; }

        [XmlElement(Order = 71)]
        public string PecAnagrafica { get; set; }

    }
}
