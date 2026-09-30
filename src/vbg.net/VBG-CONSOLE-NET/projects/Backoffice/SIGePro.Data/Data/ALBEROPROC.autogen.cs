using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;

namespace Init.SIGePro.Data
{
    [DataTable("ALBEROPROC")]
    [Serializable]
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

            public string[] ToArray()
            {
                return this._listaId.ToArray();
            }
        }

        #region Key Fields

        private int? sc_id = null;
        [useSequence]
        [KeyField("SC_ID", Type = DbType.Decimal)]
        public int? Sc_id
        {
            get { return this.sc_id; }
            set { this.sc_id = value; }
        }

        private string idcomune = null;
        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        public string Idcomune
        {
            get { return this.idcomune; }
            set { this.idcomune = value; }
        }

        #endregion

        private string sc_codice = null;
        [isRequired(MSG = "ALBEROPROC.SC_CODICE obbligatorio")]
        [DataField("SC_CODICE", Size = 10, Type = DbType.String)]
        public string SC_CODICE
        {
            get { return this.sc_codice; }
            set { this.sc_codice = value; }
        }

        private string sc_descrizione = null;
        [DataField("SC_DESCRIZIONE", Size = 80, Type = DbType.String, CaseSensitive = false)]
        public string SC_DESCRIZIONE
        {
            get { return this.sc_descrizione; }
            set { this.sc_descrizione = value; }
        }


        private string sc_padre = null;
        [DataField("SC_PADRE", Type = DbType.Decimal)]
        public string SC_PADRE
        {
            get { return this.sc_padre; }
            set { this.sc_padre = value; }
        }

        private string sc_stato_controllo = null;
        [DataField("SC_STATO_CONTROLLO", Size = 1, Type = DbType.String, CaseSensitive = false)]
        public string SC_STATO_CONTROLLO
        {
            get { return this.sc_stato_controllo; }
            set { this.sc_stato_controllo = value; }
        }

        private string sc_note = null;
        [DataField("SC_NOTE", Size = 4000, Type = DbType.String, CaseSensitive = false)]
        public string SC_NOTE
        {
            get { return this.sc_note; }
            set { this.sc_note = value; }
        }

        private string sc_attivo = null;
        [DataField("SC_ATTIVO", Type = DbType.Decimal)]
        public string SC_ATTIVO
        {
            get { return this.sc_attivo; }
            set { this.sc_attivo = value; }
        }

        private string sc_ordine = null;
        [DataField("SC_ORDINE", Type = DbType.Decimal)]
        public string SC_ORDINE
        {
            get { return this.sc_ordine; }
            set { this.sc_ordine = value; }
        }

        private string sc_nummaxistanze = null;
        [DataField("SC_NUMMAXISTANZE", Type = DbType.Decimal)]
        public string SC_NUMMAXISTANZE
        {
            get { return this.sc_nummaxistanze; }
            set { this.sc_nummaxistanze = value; }
        }

        private double? sc_minmq = null;
        [DataField("SC_MINMQ", Type = DbType.Decimal)]
        public double? SC_MINMQ
        {
            get { return this.sc_minmq; }
            set { this.sc_minmq = value; }
        }

        private double? sc_maxmq = null;
        [DataField("SC_MAXMQ", Type = DbType.Decimal)]
        public double? SC_MAXMQ
        {
            get { return this.sc_maxmq; }
            set { this.sc_maxmq = value; }
        }

        private string software = null;
        [DataField("SOFTWARE", Size = 2, Type = DbType.String)]
        public string SOFTWARE
        {
            get { return this.software; }
            set { this.software = value; }
        }

        private string controllamq = null;
        [DataField("CONTROLLAMQ", Type = DbType.Decimal)]
        public string CONTROLLAMQ
        {
            get { return this.controllamq; }
            set { this.controllamq = value; }
        }

        private string fkidazione = null;
        [DataField("FKIDAZIONE", Type = DbType.Decimal)]
        public string FKIDAZIONE
        {
            get { return this.fkidazione; }
            set { this.fkidazione = value; }
        }

        private string fkidregistro = null;
        [DataField("FKIDREGISTRO", Type = DbType.Decimal)]
        public string FKIDREGISTRO
        {
            get { return this.fkidregistro; }
            set { this.fkidregistro = value; }
        }

        private string fkidprocedura = null;
        [DataField("FKIDPROCEDURA", Type = DbType.Decimal)]
        public string FKIDPROCEDURA
        {
            get { return this.fkidprocedura; }
            set { this.fkidprocedura = value; }
        }

        private string codicetipocausale = null;
        [DataField("CODICETIPOCAUSALE", Type = DbType.Decimal)]
        public string CODICETIPOCAUSALE
        {
            get { return this.codicetipocausale; }
            set { this.codicetipocausale = value; }
        }

        private double? importocausale = null;
        [DataField("IMPORTOCAUSALE", Type = DbType.Decimal)]
        public double? IMPORTOCAUSALE
        {
            get { return this.importocausale; }
            set { this.importocausale = value; }
        }

        private double? importoistruttoria = null;
        [DataField("IMPORTOISTRUTTORIA", Type = DbType.Decimal)]
        public double? IMPORTOISTRUTTORIA
        {
            get { return this.importoistruttoria; }
            set { this.importoistruttoria = value; }
        }

        private string codiceresponsabile = null;
        [DataField("CODICERESPONSABILE", Type = DbType.Decimal)]
        public string CODICERESPONSABILE
        {
            get { return this.codiceresponsabile; }
            set { this.codiceresponsabile = value; }
        }

        private string progressivoistanze = null;
        [DataField("PROGRESSIVOISTANZE", Size = 15, Type = DbType.String, CaseSensitive = false)]
        public string PROGRESSIVOISTANZE
        {
            get { return this.progressivoistanze; }
            set { this.progressivoistanze = value; }
        }


        private string sc_pubblica = null;
        [DataField("SC_PUBBLICA", Type = DbType.Decimal)]
        public string SC_PUBBLICA
        {
            get { return this.sc_pubblica; }
            set { this.sc_pubblica = value; }
        }

        [DataField("CODICEOGGETTO_WORKFLOW", Type = DbType.Decimal)]
        public int? CodiceoggettoWorkflow
        {
            get;
            set;
        }

        [DataField("CODICEOPERATORE_STC", Type = DbType.Decimal)]
        public int? CodiceOperatoreStc
        {
            get;
            set;
        }

        [DataField("FK_RITI_CODICE", Type = DbType.String)]
        public string FkRitiCodice
        {
            get;
            set;
        }

        [DataField("FK_FOARJSTEPSTESTATAID", Type = DbType.Decimal)]
        public int? FkFoarjstepstestataid
        {
            get;
            set;
        }

        #region Foreign
        private Responsabili m_responsabile;
        [ForeignKey(/*typeof(Responsabili),*/ "Idcomune,CODICERESPONSABILE", "IDCOMUNE,CODICERESPONSABILE")]
        public Responsabili Responsabile
        {
            get { return this.m_responsabile; }
            set { this.m_responsabile = value; }
        }

        private Azioni m_azione;
        [ForeignKey(/*typeof(Azioni),*/"FKIDAZIONE", "AZ_ID")]
        public Azioni Azione
        {
            get { return this.m_azione; }
            set { this.m_azione = value; }
        }
        #endregion

        public override string ToString()
        {
            return this.sc_descrizione;
        }

        public ListaScCodice GetListaScCodice()
        {
            var l = Enumerable.Range(0, this.SC_CODICE.Length / 2)
                              .Select(x => this.SC_CODICE.Substring(0, (x + 1) * 2));

            return new ListaScCodice(l);
        }

        [DataField("INIZIO_VALIDITA", Type = DbType.DateTime)]
        public DateTime? InizioValidita { get; set; }

        [DataField("FINE_VALIDITA", Type = DbType.DateTime)]
        public DateTime? FineValidita { get; set; }

        [DataField("LIVELLO_AUTENTICAZIONE", Type = DbType.DateTime)]
        public int? LivelloAutenticazione { get; set; }


        [DataField("FKCODICEMERCATO", Type = DbType.Decimal)]
        public int? FkCodiceMercato
        {
            get;
            set;
        }

        [DataField("DRUPAL_NID", Size = 20, Type = DbType.String, CaseSensitive = false)]
        public string DrupalNid
        {
            get;
            set;
        }

        [DataField("LDP_TIP_OCCUPAZIONE", Type = DbType.Decimal)]
        public int? LdpTipOccupazione
        {
            get;
            set;
        }

        [DataField("LDP_TIP_PERIODO", Type = DbType.Decimal)]
        public int? LdpTipPeriodo
        {
            get;
            set;
        }

        [DataField("LDP_TIP_GEOMETRIA", Type = DbType.Decimal)]
        public int? LdpTipGeometria
        {
            get;
            set;
        }
    }
}