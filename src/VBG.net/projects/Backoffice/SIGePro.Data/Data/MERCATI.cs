using System.Data;
using Init.SIGePro.Attributes;
using Init.SIGePro.Collection;
using Init.SIGePro.Data;
using PersonalLib2.Sql.Attributes;
using System;

namespace Init.SIGePro.Data
{
	[DataTable("MERCATI")]
    [Serializable]
	public class Mercati : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
        public string IdComune
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

        int? codicemercato = null;
		[useSequence]
		[KeyField("CODICEMERCATO", Type=DbType.Decimal)]
        public int? CodiceMercato
		{
			get { return codicemercato; }
			set { codicemercato = value; }
		}

		string descrizione=null;
		[DataField("DESCRIZIONE",Size=100, Type=DbType.String, Compare="like", CaseSensitive=false)]
        public string Descrizione
		{
			get { return descrizione; }
			set { descrizione = value; }
		}

    	string attivo=null;
		[DataField("ATTIVO", Type=DbType.Decimal)]
		public string Attivo
		{
			get { return attivo; }
			set { attivo = value; }
		}

		string software=null;
		[DataField("SOFTWARE",Size=2, Type=DbType.String )]
		public string Software
		{
			get { return software; }
			set { software = value; }
		}

        string note = null;
        [DataField("NOTE", Size = 1000, Type = DbType.String, Compare = "like", CaseSensitive = false)]
        public string Note
        {
            get { return note; }
            set { note = value; }
        }

        string tipo_mercato = null;
        [DataField("TIPO_MERCATO", Size = 2, Type = DbType.String)]
        public string Tipo_mercato
        {
            get { return tipo_mercato; }
            set { tipo_mercato = value; }
        }

        string flagRegContaAssenza=null;
        [DataField("FLAG_REGCONTASSENZA", Type = DbType.Int32)]
        public string FlagRegContaAssenza
		{
            get { return flagRegContaAssenza; }
            set { flagRegContaAssenza = value; }
		}

        int? m_codiceoggetto = null;
        [DataField("CODICEOGGETTO", Type = DbType.Int32)]
        public int? Codiceoggetto
        {
            get { return m_codiceoggetto; }
            set { m_codiceoggetto = value; }
        }

        int? tipo_manifest = null;
        [isRequired]
        [DataField("TIPO_MANIFEST", Type = DbType.Int32)]
        public int? TipoManifest
        {
            get { return tipo_manifest; }
            set { tipo_manifest = value; }
        }

        string flagContabilita = null;
        [DataField("FLAG_CONTABILITA", Type = DbType.Int32)]
        public string FlagContabilita
        {
            get { return flagContabilita; }
            set { flagContabilita = value; }
        }

        string oraingressoinizio = null;
        [DataField("ORA_INGRESSO_INIZIO", Size = 6, Type = DbType.String, CaseSensitive = false)]
        public string OraIngressoInizio
        {
            get { return oraingressoinizio; }
            set { oraingressoinizio = value; }
        }

        string oraingressofine = null;
        [DataField("ORA_INGRESSO_FINE", Size = 6, Type = DbType.String, CaseSensitive = false)]
        public string OraIngressoFine
        {
            get { return oraingressofine; }
            set { oraingressofine = value; }
        }

        string oravenditainizio = null;
        [DataField("ORA_VENDITA_INIZIO", Size = 6, Type = DbType.String, CaseSensitive = false)]
        public string OraVenditaInizio
        {
            get { return oravenditainizio; }
            set { oravenditainizio = value; }
        }

        string oravenditafine = null;
        [DataField("ORA_VENDITA_FINE", Size = 6, Type = DbType.String, CaseSensitive = false)]
        public string OraVenditaFine
        {
            get { return oravenditafine; }
            set { oravenditafine = value; }
        }

        string orasgomberoinizio = null;
        [DataField("ORA_SGOMBERO_INIZIO", Size = 6, Type = DbType.String, CaseSensitive = false)]
        public string OraSgomberoInizio
        {
            get { return orasgomberoinizio; }
            set { orasgomberoinizio = value; }
        }

        string orasgomberofine = null;
        [DataField("ORA_SGOMBERO_FINE", Size = 6, Type = DbType.String, CaseSensitive = false)]
        public string OraSgomberoFine
        {
            get { return orasgomberofine; }
            set { orasgomberofine = value; }
        }

        string detdirigenzialenumero = null;
        [DataField("DET_DIRIGENZIALE_NUMERO", Size = 10, Type = DbType.String, Compare="like", CaseSensitive = false)]
        public string DetDirigenzialeNumero
        {
            get { return detdirigenzialenumero; }
            set { detdirigenzialenumero = value; }
        }

        DateTime? detdirigenzialedata = null;
        [DataField("DET_DIRIGENZIALE_DATA", Type = DbType.DateTime)]
        public DateTime? DetDirigenzialeData
        {
            get { return detdirigenzialedata; }
            set { detdirigenzialedata = VerificaDataLocale(value); }
        }


		#region Arraylist per gli inserimenti nelle tabelle collegate
		Mercati_DCollection _PosteggiMercato = new Mercati_DCollection();
		public Mercati_DCollection PosteggiMercato
		{
			get { return _PosteggiMercato; }
			set { _PosteggiMercato = value; }
		}

		MercatiStradarioCollection _Stradario = new MercatiStradarioCollection();
		public MercatiStradarioCollection Stradario
		{
			get { return _Stradario; }
			set { _Stradario = value; }
		}
		#endregion

        public override string ToString()
        {
            return Descrizione;
        }
	}
}