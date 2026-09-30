using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Collections.Generic;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
	[DataTable("ISTANZEPROCEDIMENTI")]
	[Serializable]
	public class IstanzeProcedimenti : BaseDataClass
	{

		#region Key Fields

		string codiceistanza=null;
		[KeyField("CODICEISTANZA", Type=DbType.Decimal)]
		[XmlElement(Order=0)]
		public string CODICEISTANZA
		{
			get { return codiceistanza; }
			set { codiceistanza = value; }
		}

		string codiceinventario=null;
		[KeyField("CODICEINVENTARIO", Type=DbType.Decimal)]
		[XmlElement(Order = 1)]
		public string CODICEINVENTARIO
		{
			get { return codiceinventario; }
			set { codiceinventario = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		[XmlElement(Order = 2)]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

		string perprovvedimento=null;
		[DataField("PERPROVVEDIMENTO", Type=DbType.Decimal)]
		[XmlElement(Order = 3)]
		public string PERPROVVEDIMENTO
		{
			get { return perprovvedimento; }
			set { perprovvedimento = value; }
		}

		string acquisito=null;
		[DataField("ACQUISITO", Type=DbType.Decimal)]
		[XmlElement(Order = 4)]
		public string ACQUISITO
		{
			get { return acquisito; }
			set { acquisito = value; }
		}

        double? costorichiesto = null;
		[DataField("COSTORICHIESTO", Type=DbType.Decimal)]
		[XmlElement(Order = 5)]
		public double? COSTORICHIESTO
		{
			get { return costorichiesto; }
			set { costorichiesto = value; }
		}

        double? costopagato = null;
		[DataField("COSTOPAGATO", Type=DbType.Decimal)]
		[XmlElement(Order = 6)]
		public double? COSTOPAGATO
		{
			get { return costopagato; }
			set { costopagato = value; }
		}

		string proc_num=null;
		[DataField("PROC_NUM",Size=15, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 7)]
		public string PROC_NUM
		{
			get { return proc_num; }
			set { proc_num = value; }
		}

		string prot_num=null;
		[DataField("PROT_NUM",Size=15, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 8)]
		public string PROT_NUM
		{
			get { return prot_num; }
			set { prot_num = value; }
		}

        DateTime? proc_del = null;
		[DataField("PROC_DEL", Type=DbType.DateTime)]
		[XmlElement(Order = 9)]
		public DateTime? PROC_DEL
		{
			get { return proc_del; }
            set { proc_del = VerificaDataLocale(value); }
		}

        DateTime? prot_del = null;
		[DataField("PROT_DEL", Type=DbType.DateTime)]
		[XmlElement(Order = 10)]
		public DateTime? PROT_DEL
		{
			get { return prot_del; }
            set { prot_del = VerificaDataLocale(value); }
		}

        DateTime? dataattivazione = null; 
		[isRequired]
		[DataField("DATAATTIVAZIONE", Type=DbType.DateTime)]
		[XmlElement(Order = 11)]
		public DateTime? DATAATTIVAZIONE
		{
			get { return dataattivazione; }
            set { dataattivazione = VerificaDataLocale(value); }
		}

		#region foreign keys
		InventarioProcedimenti m_endoProcedimento;
		[ForeignKey("IDCOMUNE,CODICEINVENTARIO", "Idcomune,Codiceinventario")]
		[XmlElement(Order = 12)]
		public InventarioProcedimenti Endoprocedimento
		{
			get { return m_endoProcedimento; }
			set { m_endoProcedimento = value; }
		}

        List<IstanzeAllegati> m_istanzeallegati = new List<IstanzeAllegati>();
        [ForeignKey("IDCOMUNE,CODICEISTANZA,CODICEINVENTARIO", "IDCOMUNE,CODICEISTANZA,CODICEINVENTARIO")]
		[XmlElement(Order = 13)]
        public List<IstanzeAllegati> IstanzeAllegati
        {
            get { return m_istanzeallegati; }
            set { m_istanzeallegati = value; }
        }



		#endregion
        [DataField("NOTE", Size = 1000, Type = DbType.String, CaseSensitive = false)]
        [XmlElement(Order = 14)]
        public string Note { get; set; }

        [DataField("TIPO_ATTO", Size = 100, Type = DbType.String, CaseSensitive = false)]
        [XmlElement(Order = 15)]
        public string TipoAtto { get; set; }

        [DataField("RILASCIATO_DA", Size = 100, Type = DbType.String, CaseSensitive = false)]
        [XmlElement(Order = 16)]
        public string RilasciatoDa { get; set; }
	}
}