using System;
using System.Data;
using Init.SIGePro.Attributes;
using Init.SIGePro.Collection;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TIPICONTROMOVIMENTO")]
	public class TipiContromovimento : BaseDataClass
	{
		string tipomovimento=null;
		[isRequired]
		[DataField("TIPOMOVIMENTO",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string TIPOMOVIMENTO
		{
			get { return tipomovimento; }
			set { tipomovimento = value; }
		}

		string tipocontromovimento=null;
		[isRequired]
		[DataField("TIPOCONTROMOVIMENTO",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string TIPOCONTROMOVIMENTO
		{
			get { return tipocontromovimento; }
			set { tipocontromovimento = value; }
		}

		string ammmov=null;
		[isRequired]
		[DataField("AMMMOV", Type=DbType.Decimal)]
		public string AMMMOV
		{
			get { return ammmov; }
			set { ammmov = value; }
		}

		string ammritsu=null;
		[isRequired]
		[DataField("AMMRITSU", Type=DbType.Decimal)]
		public string AMMRITSU
		{
			get { return ammritsu; }
			set { ammritsu = value; }
		}

		string flagbase=null;
		[isRequired]
		[DataField("FLAGBASE", Type=DbType.Decimal)]
		public string FLAGBASE
		{
			get { return flagbase; }
			set { flagbase = value; }
		}

		string soloseesitonegativo=null;
		[isRequired]
		[DataField("SOLOSEESITONEGATIVO", Type=DbType.Decimal)]
		public string SOLOSEESITONEGATIVO
		{
			get { return soloseesitonegativo; }
			set { soloseesitonegativo = value; }
		}

        int? contatore = null;
		[isRequired]
		[DataField("CONTATORE", Type=DbType.Decimal)]
		public int? Contatore
		{
			get { return contatore; }
			set { contatore = value; }
		}

		string codiceprocedura=null;
		[DataField("CODICEPROCEDURA", Type=DbType.Decimal)]
		public string CODICEPROCEDURA
		{
			get { return codiceprocedura; }
			set { codiceprocedura = value; }
		}

		string idcomune=null;
		[isRequired]
		[DataField("IDCOMUNE",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string seprecedente=null;
		[DataField("SEPRECEDENTE", Type=DbType.Decimal)]
		public string SEPRECEDENTE
		{
			get { return seprecedente; }
			set { seprecedente = value; }
		}

	}
}