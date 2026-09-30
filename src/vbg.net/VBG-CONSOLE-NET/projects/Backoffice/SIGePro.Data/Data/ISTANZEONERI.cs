using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("ISTANZEONERI")]
	[Serializable]
	public class IstanzeOneri : BaseDataClass
	{

		#region Key Fields

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string id=null;
		[useSequence]
		[KeyField("ID", Type=DbType.Decimal)]
		public string ID
		{
			get { return id; }
			set { id = value; }
		}

		#endregion

		string codiceinventario=null;
		[DataField("CODICEINVENTARIO", Type=DbType.Decimal)]
		public string CODICEINVENTARIO
		{
			get { return codiceinventario; }
			set { codiceinventario = value; }
		}

		string codiceistanza=null;
		[isRequired]
		[DataField("CODICEISTANZA", Type=DbType.Decimal)]
		public string CODICEISTANZA
		{
			get { return codiceistanza; }
			set { codiceistanza = value; }
		}

        double? prezzo = null;
		[DataField("PREZZO", Type=DbType.Decimal)]
		public double? PREZZO
		{
			get { return prezzo; }
			set { prezzo = value; }
		}

		string flentratauscita=null;
		[isRequired]
		[DataField("FLENTRATAUSCITA", Type=DbType.Decimal)]
		public string FLENTRATAUSCITA
		{
			get { return flentratauscita; }
			set { flentratauscita = value; }
		}

        DateTime? data = null;
		[isRequired]
		[DataField("DATA", Type=DbType.DateTime)]
		public DateTime? DATA
		{
			get { return data; }
            set { data = VerificaDataLocale(value); }
		}

		string note=null;
		[DataField("NOTE",Size=4000, Type=DbType.String, CaseSensitive=false)]
		public string NOTE
		{
			get { return note; }
			set { note = value; }
		}

		string codiceutente=null;
		[DataField("CODICEUTENTE", Type=DbType.Decimal)]
		public string CODICEUTENTE
		{
			get { return codiceutente; }
			set { codiceutente = value; }
		}

		string flribasso=null;
		[isRequired]
		[DataField("FLRIBASSO", Type=DbType.Decimal)]
		public string FLRIBASSO
		{
			get { return flribasso; }
			set { flribasso = value; }
		}

		string percribasso=null;
		[DataField("PERCRIBASSO", Type=DbType.Decimal)]
		public string PERCRIBASSO
		{
			get { return percribasso; }
			set { percribasso = value; }
		}

        DateTime? datapagamento = null;
		[DataField("DATAPAGAMENTO", Type=DbType.DateTime)]
		public DateTime? DATAPAGAMENTO
		{
			get { return datapagamento; }
            set { datapagamento = VerificaDataLocale(value); }
		}

        double? prezzoistruttoria = null;
		[DataField("PREZZOISTRUTTORIA", Type=DbType.Decimal)]
		public double? PREZZOISTRUTTORIA
		{
			get { return prezzoistruttoria; }
			set { prezzoistruttoria = value; }
		}

		string docriferimento=null;
		[DataField("DOCRIFERIMENTO",Size=80, Type=DbType.String, CaseSensitive=false)]
		public string DOCRIFERIMENTO
		{
			get { return docriferimento; }
			set { docriferimento = value; }
		}

		string fkidtipocausale=null;
		[isRequired]
		[DataField("FKIDTIPOCAUSALE", Type=DbType.Decimal)]
		public string FKIDTIPOCAUSALE
		{
			get { return fkidtipocausale; }
			set { fkidtipocausale = value; }
		}

        DateTime? datascadenza = null;
		[DataField("DATASCADENZA", Type=DbType.DateTime)]
		public DateTime? DATASCADENZA
		{
			get { return datascadenza; }
            set { datascadenza = VerificaDataLocale(value); }
		}

		string fkmodalitapagamento=null;
		[DataField("FKMODALITAPAGAMENTO", Type=DbType.Decimal)]
		public string FKMODALITAPAGAMENTO
		{
			get { return fkmodalitapagamento; }
			set { fkmodalitapagamento = value; }
		}

		string tipomovimento=null;
		[DataField("TIPOMOVIMENTO",Size=6, Type=DbType.String)]
		public string TIPOMOVIMENTO
		{
			get { return tipomovimento; }
			set { tipomovimento = value; }
		}

		string codiceamministrazione=null;
		[DataField("CODICEAMMINISTRAZIONE", Type=DbType.Decimal)]
		public string CODICEAMMINISTRAZIONE
		{
			get { return codiceamministrazione; }
			set { codiceamministrazione = value; }
		}

		string numerorata=null;
		[DataField("NUMERORATA", Type=DbType.Decimal)]
		public string NUMERORATA
		{
			get { return numerorata; }
			set { numerorata = value; }
		}

        string nr_documento = null;
        [DataField("NR_DOCUMENTO", Size = 20, Type = DbType.String, CaseSensitive = false)]
        public string NR_DOCUMENTO
        {
            get { return nr_documento; }
            set { nr_documento = value; }
        }

		[DataField("IMPORTOPAGATO", Type = DbType.Decimal)]
		public int? ImportoPagato
		{
			get;
			set;
		}

		#region Foreign keys
		TipiCausaliOneri m_causaleOnere;
		[ForeignKey("IDCOMUNE,FKIDTIPOCAUSALE", "Idcomune,CoId")]
		public TipiCausaliOneri CausaleOnere
		{
			get { return m_causaleOnere; }
			set { m_causaleOnere = value; }
		}

		#endregion
	}
}