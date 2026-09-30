using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
    [DataTable("ALBEROPROC")]
    [Serializable]
    [DataContract]
    public partial class AlberoProc : BaseDataClass
    {
        public class ListaScCodice
        {
            private readonly IEnumerable<string> _listaId;

            public ListaScCodice(IEnumerable<string> listaId)
            {
                this._listaId = listaId;
            }

            public override string ToString()
            {
                if (this._listaId.Count() == 0)
                {
                    return String.Empty;
                }

                return String.Format("'{0}'", String.Join("','", this.ToArray()));
            }

            public string ToCondizioneWhere()
            {
                return this.ToString();
            }

            public string[] ToArray()
            {
                return this._listaId.ToArray();
            }
        }

        #region Key Fields

        [useSequence]
        [KeyField("SC_ID", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 0)]
        public int? Sc_id { get; set; }

        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        [DataMember]
        [XmlElement(Order = 1)]
        public string Idcomune { get; set; }

        #endregion

        [isRequired(MSG = "ALBEROPROC.SC_CODICE obbligatorio")]
        [DataMember]
        [DataField("SC_CODICE", Size = 10, Type = DbType.String)]
        [XmlElement(Order = 2)]
        public string SC_CODICE { get; set; }

        [DataMember]
        [DataField("SC_DESCRIZIONE", Size = 80, Type = DbType.String, CaseSensitive = false)]
        [XmlElement(Order = 3)]
        public string SC_DESCRIZIONE { get; set; }

        [DataMember]
        [DataField("SC_PADRE", Type = DbType.Decimal)]
        [XmlElement(Order = 4)]
        public string SC_PADRE { get; set; }

        [DataMember]
        [DataField("SC_STATO_CONTROLLO", Size = 1, Type = DbType.String, CaseSensitive = false)]
        [XmlElement(Order = 5)]
        public string SC_STATO_CONTROLLO { get; set; }

        [DataMember]
        [DataField("SC_NOTE", Size = 4000, Type = DbType.String, CaseSensitive = false)]
        [XmlElement(Order = 6)]
        public string SC_NOTE { get; set; }

        [DataMember]
        [DataField("SC_ATTIVO", Type = DbType.Decimal)]
        [XmlElement(Order = 7)]
        public string SC_ATTIVO { get; set; }

        [DataMember]
        [DataField("SC_ORDINE", Type = DbType.Decimal)]
        [XmlElement(Order = 8)]
        public string SC_ORDINE { get; set; }

        [DataMember]
        [DataField("SC_NUMMAXISTANZE", Type = DbType.Decimal)]
        [XmlElement(Order = 9)]
        public string SC_NUMMAXISTANZE { get; set; }

        [DataMember]
        [DataField("SC_MINMQ", Type = DbType.Decimal)]
        [XmlElement(Order = 10)]
        public double? SC_MINMQ { get; set; }

        [DataMember]
        [DataField("SC_MAXMQ", Type = DbType.Decimal)]
        [XmlElement(Order = 11)]
        public double? SC_MAXMQ { get; set; }

        [DataMember]
        [DataField("SOFTWARE", Size = 2, Type = DbType.String)]
        [XmlElement(Order = 12)]
        public string SOFTWARE { get; set; }

        [DataMember]
        [DataField("CONTROLLAMQ", Type = DbType.Decimal)]
        [XmlElement(Order = 13)]
        public string CONTROLLAMQ { get; set; }

        [DataMember]
        [DataField("FKIDAZIONE", Type = DbType.Decimal)]
        [XmlElement(Order = 14)]
        public string FKIDAZIONE { get; set; }

        [DataMember]
        [DataField("FKIDREGISTRO", Type = DbType.Decimal)]
        [XmlElement(Order = 15)]
        public string FKIDREGISTRO { get; set; }

        [DataMember]
        [DataField("FKIDPROCEDURA", Type = DbType.Decimal)]
        [XmlElement(Order = 16)]
        public string FKIDPROCEDURA { get; set; }

        [DataMember]
        [DataField("CODICETIPOCAUSALE", Type = DbType.Decimal)]
        [XmlElement(Order = 17)]
        public string CODICETIPOCAUSALE { get; set; }

        [DataMember]
        [DataField("IMPORTOCAUSALE", Type = DbType.Decimal)]
        [XmlElement(Order = 18)]
        public double? IMPORTOCAUSALE { get; set; }

        [DataMember]
        [DataField("IMPORTOISTRUTTORIA", Type = DbType.Decimal)]
        [XmlElement(Order = 19)]
        public double? IMPORTOISTRUTTORIA { get; set; }

        [DataMember]
        [DataField("CODICERESPONSABILE", Type = DbType.Decimal)]
        [XmlElement(Order = 20)]
        public string CODICERESPONSABILE { get; set; }

        [DataMember]
        [DataField("PROGRESSIVOISTANZE", Size = 15, Type = DbType.String, CaseSensitive = false)]
        [XmlElement(Order = 21)]
        public string PROGRESSIVOISTANZE { get; set; }

        [DataMember]
        [DataField("SC_PUBBLICA", Type = DbType.Decimal)]
        [XmlElement(Order = 22)]
        public string SC_PUBBLICA { get; set; }

        [DataMember]
        [DataField("CODICEOGGETTO_WORKFLOW", Type = DbType.Decimal)]
        [XmlElement(Order = 23)]
        public int? CodiceoggettoWorkflow { get; set; }

        [DataMember]
        [DataField("CODICEOPERATORE_STC", Type = DbType.Decimal)]
        [XmlElement(Order = 24)]
        public int? CodiceOperatoreStc { get; set; }

        [DataMember]
        [DataField("FK_RITI_CODICE", Type = DbType.String)]
        [XmlElement(Order = 25)]
        public string FkRitiCodice { get; set; }

        [DataMember]
        [DataField("FK_FOARJSTEPSTESTATAID", Type = DbType.Decimal)]
        [XmlElement(Order = 26)]
        public int? FkFoarjstepstestataid { get; set; }

        #region Foreign
        [ForeignKey(/*typeof(Responsabili),*/ "Idcomune,CODICERESPONSABILE", "IDCOMUNE,CODICERESPONSABILE")]
        [XmlElement(Order = 27)]
        public Responsabili Responsabile { get; set; }

        [ForeignKey(/*typeof(Azioni),*/"FKIDAZIONE", "AZ_ID")]
        [XmlElement(Order = 28)]
        public Azioni Azione { get; set; }
        #endregion

        public override string ToString()
        {
            return this.SC_DESCRIZIONE;
        }

        public ListaScCodice GetListaScCodice()
        {
            var l = Enumerable.Range(0, this.SC_CODICE.Length / 2)
                              .Select(x => this.SC_CODICE.Substring(0, (x + 1) * 2));

            return new ListaScCodice(l);
        }

        [DataMember]
        [DataField("INIZIO_VALIDITA", Type = DbType.DateTime)]
        [XmlElement(Order = 29)]
        public DateTime? InizioValidita { get; set; }

        [DataMember]
        [DataField("FINE_VALIDITA", Type = DbType.DateTime)]
        [XmlElement(Order = 30)]
        public DateTime? FineValidita { get; set; }

        [DataMember]
        [DataField("LIVELLO_AUTENTICAZIONE", Type = DbType.DateTime)]
        [XmlElement(Order = 31)]
        public int? LivelloAutenticazione { get; set; }

        [DataMember]
        [DataField("FKCODICEMERCATO", Type = DbType.Decimal)]
        [XmlElement(Order = 32)]
        public int? FkCodiceMercato { get; set; }

        [DataMember]
        [DataField("DRUPAL_NID", Size = 20, Type = DbType.String, CaseSensitive = false)]
        [XmlElement(Order = 33)]
        public string DrupalNid { get; set; }

        [DataMember]
        [DataField("LDP_TIP_OCCUPAZIONE", Type = DbType.Decimal)]
        [XmlElement(Order = 34)]
        public int? LdpTipOccupazione { get; set; }

        [DataMember]
        [DataField("LDP_TIP_PERIODO", Type = DbType.Decimal)]
        [XmlElement(Order = 35)]
        public int? LdpTipPeriodo { get; set; }

        [DataMember]
        [DataField("LDP_TIP_GEOMETRIA", Type = DbType.Decimal)]
        [XmlElement(Order = 36)]
        public int? LdpTipGeometria { get; set; }

        [DataMember]
        [DataField("LDP_DOL_QSTRING", Type = DbType.String)]
        [XmlElement(Order = 37)]
        public string LdpDolQString { get; set; }

        [DataMember]
        [DataField("DESCRIZIONE_COMPLETA", Type = DbType.String)]
        [XmlElement(Order = 38)]
        public string DescrizioneCompleta { get; set; }

        [XmlIgnore]
        [IgnoreDataMember]
        public string ClassificaProtocollazione { get; set; }
        [XmlIgnore]
        [IgnoreDataMember]
        public string TipoDocumentoProtocollazione { get; set; }
        [XmlIgnore]
        [IgnoreDataMember]
        public string TestoTipoProtocollazione { get; set; }
        [XmlIgnore]
        [IgnoreDataMember]
        public string NumeroFascicolazione { get; set; }
        [XmlIgnore]
        [IgnoreDataMember]
        public string ClassificaFascicolazione { get; set; }
        [XmlIgnore]
        [IgnoreDataMember]
        public string TestoTipoFascicolazione { get; set; }
        [XmlIgnore]
        [IgnoreDataMember]
        public string ProtocollazioneAutomatica { get; set; }
        [XmlIgnore]
        [IgnoreDataMember]
        public string FascicolazioneAutomatica { get; set; }
        [XmlIgnore]
        [IgnoreDataMember]
        public int? CodiceAmministrazione { get; set; }
    }
}
