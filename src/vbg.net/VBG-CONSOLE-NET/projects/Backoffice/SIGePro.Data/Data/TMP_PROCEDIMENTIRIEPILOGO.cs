using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TMP_PROCEDIMENTIRIEPILOGO")]
	public class Tmp_ProcedimentiRiepilogo : BaseDataClass
	{
		string sessionid=null;
		[DataField("SESSIONID",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string SESSIONID
		{
			get { return sessionid; }
			set { sessionid = value; }
		}

		string tipiendo=null;
		[DataField("TIPIENDO", Type=DbType.Decimal)]
		public string TIPIENDO
		{
			get { return tipiendo; }
			set { tipiendo = value; }
		}

		string codiceinventario=null;
		[DataField("CODICEINVENTARIO", Type=DbType.Decimal)]
		public string CODICEINVENTARIO
		{
			get { return codiceinventario; }
			set { codiceinventario = value; }
		}

		string procedimento=null;
		[DataField("PROCEDIMENTO",Size=255, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string PROCEDIMENTO
		{
			get { return procedimento; }
			set { procedimento = value; }
		}

		string flag_autocertificazione=null;
		[DataField("FLAG_AUTOCERTIFICAZIONE", Type=DbType.Decimal)]
		public string FLAG_AUTOCERTIFICAZIONE
		{
			get { return flag_autocertificazione; }
			set { flag_autocertificazione = value; }
		}

		string flag_autorizzativo=null;
		[DataField("FLAG_AUTORIZZATIVO", Type=DbType.Decimal)]
		public string FLAG_AUTORIZZATIVO
		{
			get { return flag_autorizzativo; }
			set { flag_autorizzativo = value; }
		}

		string flag_acquisito=null;
		[DataField("FLAG_ACQUISITO", Type=DbType.Decimal)]
		public string FLAG_ACQUISITO
		{
			get { return flag_acquisito; }
			set { flag_acquisito = value; }
		}

		string flag_trasmissione=null;
		[DataField("FLAG_TRASMISSIONE", Type=DbType.Decimal)]
		public string FLAG_TRASMISSIONE
		{
			get { return flag_trasmissione; }
			set { flag_trasmissione = value; }
		}

		string flag_ritorno=null;
		[DataField("FLAG_RITORNO", Type=DbType.Decimal)]
		public string FLAG_RITORNO
		{
			get { return flag_ritorno; }
			set { flag_ritorno = value; }
		}

	}
}