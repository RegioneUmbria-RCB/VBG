using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TMP_PIANIFICAZIONI")]
	public class Tmp_Pianificazioni : BaseDataClass
	{
		string id=null;
		[DataField("ID", Type=DbType.Decimal)]
		public string ID
		{
			get { return id; }
			set { id = value; }
		}

		string sessionid=null;
		[DataField("SESSIONID",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string SESSIONID
		{
			get { return sessionid; }
			set { sessionid = value; }
		}

        DateTime? datap = null;
		[DataField("DATAP", Type=DbType.DateTime)]
		public DateTime? DATAP
		{
			get { return datap; }
            set { datap = VerificaDataLocale(value); }
		}

		string codiceistanza=null;
		[DataField("CODICEISTANZA", Type=DbType.Decimal)]
		public string CODICEISTANZA
		{
			get { return codiceistanza; }
			set { codiceistanza = value; }
		}

		string codiceinventario=null;
		[DataField("CODICEINVENTARIO",Size=10, Type=DbType.String, CaseSensitive=false)]
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

		string codiceamministrazione=null;
		[DataField("CODICEAMMINISTRAZIONE", Type=DbType.Decimal)]
		public string CODICEAMMINISTRAZIONE
		{
			get { return codiceamministrazione; }
			set { codiceamministrazione = value; }
		}

		string amministrazione=null;
		[DataField("AMMINISTRAZIONE",Size=80, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string AMMINISTRAZIONE
		{
			get { return amministrazione; }
			set { amministrazione = value; }
		}

		string codammrichiedente=null;
		[DataField("CODAMMRICHIEDENTE", Type=DbType.Decimal)]
		public string CODAMMRICHIEDENTE
		{
			get { return codammrichiedente; }
			set { codammrichiedente = value; }
		}

		string ammrichiedente=null;
		[DataField("AMMRICHIEDENTE",Size=80, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string AMMRICHIEDENTE
		{
			get { return ammrichiedente; }
			set { ammrichiedente = value; }
		}

		string tipomovimento=null;
		[DataField("TIPOMOVIMENTO",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string TIPOMOVIMENTO
		{
			get { return tipomovimento; }
			set { tipomovimento = value; }
		}

		string movimento=null;
		[DataField("MOVIMENTO",Size=128, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string MOVIMENTO
		{
			get { return movimento; }
			set { movimento = value; }
		}

		string flag_richiestaintegrazione=null;
		[DataField("FLAG_RICHIESTAINTEGRAZIONE", Type=DbType.Decimal)]
		public string FLAG_RICHIESTAINTEGRAZIONE
		{
			get { return flag_richiestaintegrazione; }
			set { flag_richiestaintegrazione = value; }
		}

		string flag_interruzione=null;
		[DataField("FLAG_INTERRUZIONE", Type=DbType.Decimal)]
		public string FLAG_INTERRUZIONE
		{
			get { return flag_interruzione; }
			set { flag_interruzione = value; }
		}

		string flag_convocazionecds=null;
		[DataField("FLAG_CONVOCAZIONECDS", Type=DbType.Decimal)]
		public string FLAG_CONVOCAZIONECDS
		{
			get { return flag_convocazionecds; }
			set { flag_convocazionecds = value; }
		}

        DateTime? datascad = null;
		[DataField("DATASCAD", Type=DbType.DateTime)]
		public DateTime? DATASCAD
		{
			get { return datascad; }
            set { datascad = VerificaDataLocale(value); }
		}

		string flag_elab=null;
		[DataField("FLAG_ELAB",Size=1, Type=DbType.String, CaseSensitive=false)]
		public string FLAG_ELAB
		{
			get { return flag_elab; }
			set { flag_elab = value; }
		}

		string note=null;
		[DataField("NOTE",Size=100, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string NOTE
		{
			get { return note; }
			set { note = value; }
		}

		string esitonegativo=null;
		[DataField("ESITONEGATIVO", Type=DbType.Decimal)]
		public string ESITONEGATIVO
		{
			get { return esitonegativo; }
			set { esitonegativo = value; }
		}

		string codicemovimento=null;
		[DataField("CODICEMOVIMENTO", Type=DbType.Decimal)]
		public string CODICEMOVIMENTO
		{
			get { return codicemovimento; }
			set { codicemovimento = value; }
		}

		string esito=null;
		[DataField("ESITO", Type=DbType.Decimal)]
		public string ESITO
		{
			get { return esito; }
			set { esito = value; }
		}

		string tipologiaesito=null;
		[DataField("TIPOLOGIAESITO", Type=DbType.Decimal)]
		public string TIPOLOGIAESITO
		{
			get { return tipologiaesito; }
			set { tipologiaesito = value; }
		}

		string autocertificabile=null;
		[DataField("AUTOCERTIFICABILE", Type=DbType.Decimal)]
		public string AUTOCERTIFICABILE
		{
			get { return autocertificabile; }
			set { autocertificabile = value; }
		}

		string tipomovtx=null;
		[DataField("TIPOMOVTX",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string TIPOMOVTX
		{
			get { return tipomovtx; }
			set { tipomovtx = value; }
		}

		string flagbase=null;
		[DataField("FLAGBASE", Type=DbType.Decimal)]
		public string FLAGBASE
		{
			get { return flagbase; }
			set { flagbase = value; }
		}

		string ufficio=null;
		[DataField("UFFICIO",Size=80, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string UFFICIO
		{
			get { return ufficio; }
			set { ufficio = value; }
		}

		string noneseguecontromovobblig=null;
		[DataField("NONESEGUECONTROMOVOBBLIG", Type=DbType.Decimal)]
		public string NONESEGUECONTROMOVOBBLIG
		{
			get { return noneseguecontromovobblig; }
			set { noneseguecontromovobblig = value; }
		}

        DateTime? dataattivazione = null;
		[DataField("DATAATTIVAZIONE", Type=DbType.DateTime)]
		public DateTime? DATAATTIVAZIONE
		{
			get { return dataattivazione; }
            set { dataattivazione = VerificaDataLocale(value); }
		}

	}
}