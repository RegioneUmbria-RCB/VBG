using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("DYN_CAMPI")]
	[Serializable]
	public class DynCampi : BaseDataClass
	{
		
		#region Key Fields

		string codice=null;
		[useSequence]
		[KeyField("CODICE", Type=DbType.Decimal)]
		public string CODICE
		{
			get { return codice; }
			set { codice = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

		string etichetta=null;
		[DataField("ETICHETTA",Size=150, Type=DbType.String, CaseSensitive=false)]
		public string ETICHETTA
		{
			get { return etichetta; }
			set { etichetta = value; }
		}

		string descrizione=null;
		[DataField("DESCRIZIONE",Size=1000, Type=DbType.String, CaseSensitive=false)]
		public string DESCRIZIONE
		{
			get { return descrizione; }
			set { descrizione = value; }
		}

		string nomecampo=null;
		[DataField("NOMECAMPO",Size=50, Type=DbType.String, CaseSensitive=false)]
		public string NOMECAMPO
		{
			get { return nomecampo; }
			set { nomecampo = value; }
		}

		string tipo=null;
		[DataField("TIPO",Size=1, Type=DbType.String, CaseSensitive=false)]
		public string TIPO
		{
			get { return tipo; }
			set { tipo = value; }
		}

		string lunghezza=null;
		[DataField("LUNGHEZZA", Type=DbType.Decimal)]
		public string LUNGHEZZA
		{
			get { return lunghezza; }
			set { lunghezza = value; }
		}

		string dimensione=null;
		[DataField("DIMENSIONE", Type=DbType.Decimal)]
		public string DIMENSIONE
		{
			get { return dimensione; }
			set { dimensione = value; }
		}

		string rangebasso=null;
		[DataField("RANGEBASSO",Size=25, Type=DbType.String, CaseSensitive=false)]
		public string RANGEBASSO
		{
			get { return rangebasso; }
			set { rangebasso = value; }
		}

		string rangealto=null;
		[DataField("RANGEALTO",Size=25, Type=DbType.String, CaseSensitive=false)]
		public string RANGEALTO
		{
			get { return rangealto; }
			set { rangealto = value; }
		}

		string bloccante=null;
		[DataField("BLOCCANTE",Size=1, Type=DbType.String, CaseSensitive=false)]
		public string BLOCCANTE
		{
			get { return bloccante; }
			set { bloccante = value; }
		}

		string obbligatorio=null;
		[DataField("OBBLIGATORIO",Size=1, Type=DbType.String, CaseSensitive=false)]
		public string OBBLIGATORIO
		{
			get { return obbligatorio; }
			set { obbligatorio = value; }
		}

		string lista=null;
		[DataField("LISTA",Size=250, Type=DbType.String, CaseSensitive=false)]
		public string LISTA
		{
			get { return lista; }
			set { lista = value; }
		}

		string software=null;
		[DataField("SOFTWARE",Size=2, Type=DbType.String)]
		public string SOFTWARE
		{
			get { return software; }
			set { software = value; }
		}
	}
}