using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("INTERVENTI")]
	[Serializable]
	public class Interventi : BaseDataClass
	{
		
		#region Key Fields

        private int? m_codiceintervento = null;
        [useSequence]
        [KeyField("CODICEINTERVENTO", Type = DbType.Decimal)]
        public int? CodiceIntervento
        {
            get { return m_codiceintervento; }
            set { m_codiceintervento = value; }
        }

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
        public string Idcomune
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

		string intervento=null;
		[DataField("INTERVENTO",Size=50, Type=DbType.String, CaseSensitive=false)]
		public string INTERVENTO
		{
			get { return intervento; }
			set { intervento = value; }
		}

		string note=null;
		[DataField("NOTE",Size=2000, Type=DbType.String, CaseSensitive=false)]
		public string NOTE
		{
			get { return note; }
			set { note = value; }
		}

		string codificaregionale=null;
		[DataField("CODIFICAREGIONALE",Size=2, Type=DbType.String, CaseSensitive=false)]
		public string CODIFICAREGIONALE
		{
			get { return codificaregionale; }
			set { codificaregionale = value; }
		}

		string fkidregistro=null;
		[DataField("FKIDREGISTRO", Type=DbType.Decimal)]
		public string FKIDREGISTRO
		{
			get { return fkidregistro; }
			set { fkidregistro = value; }
		}

		string software=null;
		[DataField("SOFTWARE",Size=2, Type=DbType.String)]
		public string SOFTWARE
		{
			get { return software; }
			set { software = value; }
		}

		string fkidmodello=null;
		[DataField("FKIDMODELLO", Type=DbType.Decimal)]
		public string FKIDMODELLO
		{
			get { return fkidmodello; }
			set { fkidmodello = value; }
		}

		string codiceresponsabile=null;
		[DataField("CODICERESPONSABILE", Type=DbType.Decimal)]
		public string CODICERESPONSABILE
		{
			get { return codiceresponsabile; }
			set { codiceresponsabile = value; }
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

        double? importoistruttoria = null;
		[DataField("IMPORTOISTRUTTORIA", Type=DbType.Decimal)]
		public double? IMPORTOISTRUTTORIA
		{
			get { return importoistruttoria; }
			set { importoistruttoria = value; }
		}

		string fkidazione=null;
		[DataField("FKIDAZIONE", Type=DbType.Decimal)]
		public string FKIDAZIONE
		{
			get { return fkidazione; }
			set { fkidazione = value; }
		}

		Azioni m_azione;
		[ForeignKey(/*typeof(Azioni),*/"FKIDAZIONE", "AZ_ID")]
		public Azioni Azione
		{
			get { return m_azione; }
			set { m_azione=value; }
		}
	}
}