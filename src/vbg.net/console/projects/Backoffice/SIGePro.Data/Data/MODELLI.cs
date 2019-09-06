using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("MODELLI")]
	public class Modelli : BaseDataClass
	{
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
        public string IdComune { get; set; }

		[KeyField("CODICEMODELLO", Type=DbType.Decimal)]
        public string CodiceModello { get; set; }

		[DataField("TITOLO",Size=512, Type=DbType.String, Compare="like", CaseSensitive=false)]
        public string Titolo { get; set; }

		[DataField("DESCRIZIONE",Size=4000, Type=DbType.String, Compare="like", CaseSensitive=false)]
        public string Descrizione { get; set; }

		[DataField("NOMEFILE",Size=255, Type=DbType.String, Compare="like", CaseSensitive=false)]
        public string NomeFile { get; set; }

		[DataField("ORDINE", Type=DbType.Decimal)]
        public int? Ordine { get; set; }

		[DataField("INDIRIZZOWEB",Size=100, Type=DbType.String, Compare="like", CaseSensitive=false)]
        public string IndirizzoWeb { get; set; }

		[DataField("TIPOSUAP",Size=2, Type=DbType.String, CaseSensitive=false)]
        public string TipoSuap { get; set; }

        [DataField("CODICEOGGETTO", Type = DbType.Decimal)]
        public int? CodiceOggetto { get; set; }

        [DataField("FLAG_PUBBLICA", Type = DbType.Decimal)]
        public int? FlagPubblica{get;set;}

        [DataField("FK_TIPIMODULO", Type = DbType.Decimal)]
        public int? FkTipiModulo { get; set; }
	}
}