using System.Data;
using Init.SIGePro.Attributes;
using Init.SIGePro.Collection;
using PersonalLib2.Sql.Attributes;
using Init.SIGePro.DatiDinamici.Interfaces;
using System.Collections.Generic;

namespace Init.SIGePro.Data
{
	[DataTable("I_ATTIVITA")]
	public class IAttivita : BaseDataClass, IClasseContestoModelloDinamico
	{
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IdComune
		{
			get;
			set;
		}

		[useSequence]
		[KeyField("ID", Type=DbType.Decimal)]
		public int? Id
		{
			get;
			set;
		}

		[DataField("DENOMINAZIONE",Size=150, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string Denominazione
		{
			get;
			set;
		}

		[DataField("CODICEISTANZAULTIMA", Type=DbType.Decimal)]
		public int? CodiceIstanzaUltima
		{
			get;
			set;
		}

		[DataField("NUMEROPRESENZE",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string NumeroPresenze
		{
			get;
			set;
		}

		[DataField("ATTIVA", Type=DbType.Decimal)]
		public int? Attiva
		{
			get;
			set;
		}

		[DataField("OPERANTE", Type = DbType.Decimal)]
		public int? Operante
		{
			get;
			set;
		}

		#region Arraylist per gli inserimenti nelle tabelle collegate

		List<Istanze> _istanze = new List<Istanze>();
		public List<Istanze> Istanze
		{
			get { return _istanze; }
			set { _istanze = value; }
		}

		#endregion

	}
}