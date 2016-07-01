using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TMP_ISTANZEPROCEDIMENTI")]
	public class Tmp_IstanzeProcedimenti : BaseDataClass
	{
		string codiceistanza=null;
		[DataField("CODICEISTANZA", Type=DbType.Decimal)]
		public string CODICEISTANZA
		{
			get { return codiceistanza; }
			set { codiceistanza = value; }
		}

		string codiceinventario=null;
		[DataField("CODICEINVENTARIO", Type=DbType.Decimal)]
		public string CODICEINVENTARIO
		{
			get { return codiceinventario; }
			set { codiceinventario = value; }
		}

		string perprovvedimento=null;
		[DataField("PERPROVVEDIMENTO", Type=DbType.Decimal)]
		public string PERPROVVEDIMENTO
		{
			get { return perprovvedimento; }
			set { perprovvedimento = value; }
		}

		string acquisito=null;
		[DataField("ACQUISITO", Type=DbType.Decimal)]
		public string ACQUISITO
		{
			get { return acquisito; }
			set { acquisito = value; }
		}

		string costorichiesto=null;
		[DataField("COSTORICHIESTO", Type=DbType.Decimal)]
		public string COSTORICHIESTO
		{
			get { return costorichiesto; }
			set { costorichiesto = value; }
		}

		string costopagato=null;
		[DataField("COSTOPAGATO", Type=DbType.Decimal)]
		public string COSTOPAGATO
		{
			get { return costopagato; }
			set { costopagato = value; }
		}

		string proc_num=null;
		[DataField("PROC_NUM",Size=15, Type=DbType.String, CaseSensitive=false)]
		public string PROC_NUM
		{
			get { return proc_num; }
			set { proc_num = value; }
		}

		string prot_num=null;
		[DataField("PROT_NUM",Size=15, Type=DbType.String, CaseSensitive=false)]
		public string PROT_NUM
		{
			get { return prot_num; }
			set { prot_num = value; }
		}

        DateTime? proc_del = null;
		[DataField("PROC_DEL", Type=DbType.DateTime)]
		public DateTime? PROC_DEL
		{
			get { return proc_del; }
            set { proc_del = VerificaDataLocale(value); }
		}

        DateTime? prot_del = null;
		[DataField("PROT_DEL", Type=DbType.DateTime)]
		public DateTime? PROT_DEL
		{
			get { return prot_del; }
            set { prot_del = VerificaDataLocale(value); }
		}

		string sessionid=null;
		[DataField("SESSIONID",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string SESSIONID
		{
			get { return sessionid; }
			set { sessionid = value; }
		}

	}
}