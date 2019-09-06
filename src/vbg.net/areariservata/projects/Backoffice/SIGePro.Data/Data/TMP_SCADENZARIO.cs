using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TMP_SCADENZARIO")]
	public class Tmp_Scadenzario : BaseDataClass
	{
		string sessionid=null;
		[KeyField("SESSIONID",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string SESSIONID
		{
			get { return sessionid; }
			set { sessionid = value; }
		}

		string id=null;
		[KeyField("ID", Type=DbType.Decimal)]
		public string ID
		{
			get { return id; }
			set { id = value; }
		}

		string tipo=null;
		[DataField("TIPO", Type=DbType.Decimal)]
		public string TIPO
		{
			get { return tipo; }
			set { tipo = value; }
		}

		string richiedente=null;
		[DataField("RICHIEDENTE",Size=250, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string RICHIEDENTE
		{
			get { return richiedente; }
			set { richiedente = value; }
		}

		string movim_tipo=null;
		[DataField("MOVIM_TIPO",Size=250, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string MOVIM_TIPO
		{
			get { return movim_tipo; }
			set { movim_tipo = value; }
		}

        DateTime? datareg = null;
		[DataField("DATAREG", Type=DbType.DateTime)]
		public DateTime? DATAREG
		{
			get { return datareg; }
            set { datareg = VerificaDataLocale(value); }
		}

		string daeff_oggetto=null;
		[DataField("DAEFF_OGGETTO",Size=250, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DAEFF_OGGETTO
		{
			get { return daeff_oggetto; }
			set { daeff_oggetto = value; }
		}

        DateTime? scadenza = null;
		[DataField("SCADENZA", Type=DbType.DateTime)]
		public DateTime? SCADENZA
		{
			get { return scadenza; }
            set { scadenza = VerificaDataLocale(value); }
		}

		string codicei_p=null;
		[DataField("CODICEI_P", Type=DbType.Decimal)]
		public string CODICEI_P
		{
			get { return codicei_p; }
			set { codicei_p = value; }
		}

		string responsabile=null;
		[DataField("RESPONSABILE",Size=250, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string RESPONSABILE
		{
			get { return responsabile; }
			set { responsabile = value; }
		}

		string tipoprocedura=null;
		[DataField("TIPOPROCEDURA",Size=500, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string TIPOPROCEDURA
		{
			get { return tipoprocedura; }
			set { tipoprocedura = value; }
		}

	}
}