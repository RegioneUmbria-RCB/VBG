using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Data
{
	[DataTable("ALBEROPROC")]
	[Serializable]
	public partial class AlberoProc : BaseDataClass
	{
		public class ListaScCodice
		{
			IEnumerable<string> _listaId;

			public ListaScCodice(IEnumerable<string> listaId)
			{
				this._listaId = listaId;
			}

			public override string ToString()
			{
				if(this._listaId.Count() == 0)
				{
					return String.Empty;
				}

				return String.Format("'{0}'", String.Join("','", ToArray()));
			}

			public string[] ToArray()
			{
				return this._listaId.ToArray();
			}
		}

		#region Key Fields

        int? sc_id = null;
		[useSequence]
		[KeyField("SC_ID", Type=DbType.Decimal)]
        public int? Sc_id
		{
			get { return sc_id; }
			set { sc_id = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
        public string Idcomune
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

		string sc_codice=null;
		[isRequired(MSG="ALBEROPROC.SC_CODICE obbligatorio")]
		[DataField("SC_CODICE",Size=10, Type=DbType.String)]
		public string SC_CODICE
		{
			get { return sc_codice; }
			set { sc_codice = value; }
		}

		string sc_descrizione=null;
		[DataField("SC_DESCRIZIONE",Size=80, Type=DbType.String, CaseSensitive=false)]
		public string SC_DESCRIZIONE
		{
			get { return sc_descrizione; }
			set { sc_descrizione = value; }
		}


		string sc_padre=null;
		[DataField("SC_PADRE", Type=DbType.Decimal)]
		public string SC_PADRE
		{
			get { return sc_padre; }
			set { sc_padre = value; }
		}

		string sc_stato_controllo=null;
		[DataField("SC_STATO_CONTROLLO",Size=1, Type=DbType.String, CaseSensitive=false)]
		public string SC_STATO_CONTROLLO
		{
			get { return sc_stato_controllo; }
			set { sc_stato_controllo = value; }
		}

		string sc_note=null;
		[DataField("SC_NOTE",Size=4000, Type=DbType.String, CaseSensitive=false)]
		public string SC_NOTE
		{
			get { return sc_note; }
			set { sc_note = value; }
		}

		string sc_attivo=null;
		[DataField("SC_ATTIVO", Type=DbType.Decimal)]
		public string SC_ATTIVO
		{
			get { return sc_attivo; }
			set { sc_attivo = value; }
		}

		string sc_ordine=null;
		[DataField("SC_ORDINE", Type=DbType.Decimal)]
		public string SC_ORDINE
		{
			get { return sc_ordine; }
			set { sc_ordine = value; }
		}

		string sc_nummaxistanze=null;
		[DataField("SC_NUMMAXISTANZE", Type=DbType.Decimal)]
		public string SC_NUMMAXISTANZE
		{
			get { return sc_nummaxistanze; }
			set { sc_nummaxistanze = value; }
		}

		double? sc_minmq=null;
		[DataField("SC_MINMQ", Type=DbType.Decimal)]
		public double? SC_MINMQ
		{
			get { return sc_minmq; }
			set { sc_minmq = value; }
		}

        double? sc_maxmq = null;
		[DataField("SC_MAXMQ", Type=DbType.Decimal)]
        public double? SC_MAXMQ
		{
			get { return sc_maxmq; }
			set { sc_maxmq = value; }
		}

		string software=null;
		[DataField("SOFTWARE",Size=2, Type=DbType.String)]
		public string SOFTWARE
		{
			get { return software; }
			set { software = value; }
		}

		string controllamq=null;
		[DataField("CONTROLLAMQ", Type=DbType.Decimal)]
		public string CONTROLLAMQ
		{
			get { return controllamq; }
			set { controllamq = value; }
		}

		string fkidazione=null;
		[DataField("FKIDAZIONE", Type=DbType.Decimal)]
		public string FKIDAZIONE
		{
			get { return fkidazione; }
			set { fkidazione = value; }
		}

		string fkidregistro=null;
		[DataField("FKIDREGISTRO", Type=DbType.Decimal)]
		public string FKIDREGISTRO
		{
			get { return fkidregistro; }
			set { fkidregistro = value; }
		}

		string fkidprocedura=null;
		[DataField("FKIDPROCEDURA", Type=DbType.Decimal)]
		public string FKIDPROCEDURA
		{
			get { return fkidprocedura; }
			set { fkidprocedura = value; }
		}

		string codicetipocausale=null;
		[DataField("CODICETIPOCAUSALE", Type=DbType.Decimal)]
		public string CODICETIPOCAUSALE
		{
			get { return codicetipocausale; }
			set { codicetipocausale = value; }
		}

        double? importocausale = null;
		[DataField("IMPORTOCAUSALE", Type=DbType.Decimal)]
        public double? IMPORTOCAUSALE
		{
			get { return importocausale; }
			set { importocausale = value; }
		}

		double? importoistruttoria=null; 
		[DataField("IMPORTOISTRUTTORIA", Type=DbType.Decimal)]
		public double? IMPORTOISTRUTTORIA
		{
			get { return importoistruttoria; }
			set { importoistruttoria = value; }
		}

		string codiceresponsabile=null;
		[DataField("CODICERESPONSABILE", Type=DbType.Decimal)]
		public string CODICERESPONSABILE
		{
			get { return codiceresponsabile; }
			set { codiceresponsabile = value; }
		}

		string progressivoistanze=null;
		[DataField("PROGRESSIVOISTANZE",Size=15, Type=DbType.String, CaseSensitive=false)]
		public string PROGRESSIVOISTANZE
		{
			get { return progressivoistanze; }
			set { progressivoistanze = value; }
		}


		string sc_pubblica=null;
		[DataField("SC_PUBBLICA", Type=DbType.Decimal)]
		public string SC_PUBBLICA
		{
			get { return sc_pubblica; }
			set { sc_pubblica = value; }
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
		Responsabili m_responsabile;
		[ForeignKey(/*typeof(Responsabili),*/ "Idcomune,CODICERESPONSABILE", "IDCOMUNE,CODICERESPONSABILE")]
		public Responsabili Responsabile
		{
			get { return m_responsabile; }
			set { m_responsabile = value; }
		}

		Azioni m_azione;
		[ForeignKey(/*typeof(Azioni),*/"FKIDAZIONE", "AZ_ID")]
		public Azioni Azione
		{
			get { return m_azione; }
			set { m_azione = value; }
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