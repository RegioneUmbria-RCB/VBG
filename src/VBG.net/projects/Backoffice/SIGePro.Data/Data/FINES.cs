using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("FINES")]
	public class Fines : BaseDataClass
	{
		string fi_id=null;
		[KeyField("FI_ID", Type=DbType.Decimal)]
		public string FI_ID
		{
			get { return fi_id; }
			set { fi_id = value; }
		}

        DateTime? fi_data = null;
		[DataField("FI_DATA", Type=DbType.DateTime)]
		public DateTime? FI_DATA
		{
			get { return fi_data; }
            set { fi_data = VerificaDataLocale(value); }
		}

		string fi_ora=null;
		[DataField("FI_ORA",Size=4, Type=DbType.String, CaseSensitive=false)]
		public string FI_ORA
		{
			get { return fi_ora; }
			set { fi_ora = value; }
		}

		string fi_luogo=null;
		[DataField("FI_LUOGO",Size=30, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string FI_LUOGO
		{
			get { return fi_luogo; }
			set { fi_luogo = value; }
		}

		string fi_luogo_num=null;
		[DataField("FI_LUOGO_NUM",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string FI_LUOGO_NUM
		{
			get { return fi_luogo_num; }
			set { fi_luogo_num = value; }
		}

		string fi_veicolo=null;
		[DataField("FI_VEICOLO",Size=35, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string FI_VEICOLO
		{
			get { return fi_veicolo; }
			set { fi_veicolo = value; }
		}

		string fi_tipo_veicolo=null;
		[DataField("FI_TIPO_VEICOLO",Size=25, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string FI_TIPO_VEICOLO
		{
			get { return fi_tipo_veicolo; }
			set { fi_tipo_veicolo = value; }
		}

		string fi_targa=null;
		[DataField("FI_TARGA",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string FI_TARGA
		{
			get { return fi_targa; }
			set { fi_targa = value; }
		}

		string fi_cognome=null;
		[DataField("FI_COGNOME",Size=35, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string FI_COGNOME
		{
			get { return fi_cognome; }
			set { fi_cognome = value; }
		}

		string fi_nome=null;
		[DataField("FI_NOME",Size=35, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string FI_NOME
		{
			get { return fi_nome; }
			set { fi_nome = value; }
		}

		string fi_categoria_patente=null;
		[DataField("FI_CATEGORIA_PATENTE",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string FI_CATEGORIA_PATENTE
		{
			get { return fi_categoria_patente; }
			set { fi_categoria_patente = value; }
		}

		string fi_numero_patente=null;
		[DataField("FI_NUMERO_PATENTE",Size=30, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string FI_NUMERO_PATENTE
		{
			get { return fi_numero_patente; }
			set { fi_numero_patente = value; }
		}

        DateTime? fi_data_rilascio = null;
		[DataField("FI_DATA_RILASCIO", Type=DbType.DateTime)]
		public DateTime? FI_DATA_RILASCIO
		{
			get { return fi_data_rilascio; }
            set { fi_data_rilascio = VerificaDataLocale(value); }
		}

		string fi_luogo_rilascio=null;
		[DataField("FI_LUOGO_RILASCIO",Size=35, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string FI_LUOGO_RILASCIO
		{
			get { return fi_luogo_rilascio; }
			set { fi_luogo_rilascio = value; }
		}

		string fi_ridotto=null;
		[DataField("FI_RIDOTTO", Type=DbType.Decimal)]
		public string FI_RIDOTTO
		{
			get { return fi_ridotto; }
			set { fi_ridotto = value; }
		}

		string fi_intero=null;
		[DataField("FI_INTERO", Type=DbType.Decimal)]
		public string FI_INTERO
		{
			get { return fi_intero; }
			set { fi_intero = value; }
		}

		string fi_sanzioni=null;
		[DataField("FI_SANZIONI",Size=30, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string FI_SANZIONI
		{
			get { return fi_sanzioni; }
			set { fi_sanzioni = value; }
		}

		string fi_ritiro_patente=null;
		[DataField("FI_RITIRO_PATENTE", Type=DbType.Decimal)]
		public string FI_RITIRO_PATENTE
		{
			get { return fi_ritiro_patente; }
			set { fi_ritiro_patente = value; }
		}

		string fi_ritiro_carta=null;
		[DataField("FI_RITIRO_CARTA", Type=DbType.Decimal)]
		public string FI_RITIRO_CARTA
		{
			get { return fi_ritiro_carta; }
			set { fi_ritiro_carta = value; }
		}

		string fi_ritiro_certificato=null;
		[DataField("FI_RITIRO_CERTIFICATO", Type=DbType.Decimal)]
		public string FI_RITIRO_CERTIFICATO
		{
			get { return fi_ritiro_certificato; }
			set { fi_ritiro_certificato = value; }
		}

		string fi_agente1=null;
		[DataField("FI_AGENTE1",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string FI_AGENTE1
		{
			get { return fi_agente1; }
			set { fi_agente1 = value; }
		}

		string fi_agente2=null;
		[DataField("FI_AGENTE2",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string FI_AGENTE2
		{
			get { return fi_agente2; }
			set { fi_agente2 = value; }
		}

		string fi_violazione1=null;
		[DataField("FI_VIOLAZIONE1",Size=20, Type=DbType.String, CaseSensitive=false)]
		public string FI_VIOLAZIONE1
		{
			get { return fi_violazione1; }
			set { fi_violazione1 = value; }
		}

		string fi_violazione2=null;
		[DataField("FI_VIOLAZIONE2",Size=20, Type=DbType.String, CaseSensitive=false)]
		public string FI_VIOLAZIONE2
		{
			get { return fi_violazione2; }
			set { fi_violazione2 = value; }
		}

		string fi_violazione3=null;
		[DataField("FI_VIOLAZIONE3",Size=20, Type=DbType.String, CaseSensitive=false)]
		public string FI_VIOLAZIONE3
		{
			get { return fi_violazione3; }
			set { fi_violazione3 = value; }
		}

		string fi_articolo_violato=null;
		[DataField("FI_ARTICOLO_VIOLATO",Size=30, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string FI_ARTICOLO_VIOLATO
		{
			get { return fi_articolo_violato; }
			set { fi_articolo_violato = value; }
		}

		string og_id=null;
		[DataField("OG_ID", Type=DbType.Decimal)]
		public string OG_ID
		{
			get { return og_id; }
			set { og_id = value; }
		}

		string idcomune=null;
		[DataField("IDCOMUNE",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

	}
}