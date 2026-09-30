
using System;
using System.Data;
using System.Reflection;
using System.Text;
using Init.SIGePro.Attributes;

using PersonalLib2.Sql.Attributes;
using PersonalLib2.Sql;
using System.Runtime.Serialization;

namespace Init.SIGePro.Data
{
	///
	/// File generato automaticamente dalla tabella DYN2_MODELLIT il 16/02/2010 16.52.27
	///
	///												ATTENZIONE!!!
	///	- Specificare manualmente in quali colonne vanno applicate eventuali sequenze		
	/// - Verificare l'applicazione di eventuali attributi di tipo "[isRequired]". In caso contrario applicarli manualmente
	///	- Verificare che il tipo di dati assegnato alle proprietà sia corretto
	///
	///						ELENCARE DI SEGUITO EVENTUALI MODIFICHE APPORTATE MANUALMENTE ALLA CLASSE
	///				(per tenere traccia dei cambiamenti nel caso in cui la classe debba essere generata di nuovo)
	/// -
	/// -
	/// -
	/// - 
	///
	///	Prima di effettuare modifiche al template di MyGeneration in caso di dubbi contattare Nicola Gargagli ;)
	///
	[DataTable("DYN2_MODELLIT")]
	[Serializable]
	[DataContract]
	public partial class Dyn2ModelliT : BaseDataClass
	{
		[KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        [DataMember]
		public string Idcomune { get; set; }

		[KeyField("ID", Type = DbType.Decimal)]
		[useSequence]
        [DataMember]
		public int? Id { get; set; }

		[isRequired]
		[DataField("SOFTWARE", Type = DbType.String, CaseSensitive = false, Size = 2)]
        [DataMember]
		public string Software { get; set; }

		[isRequired]
		[DataField("DESCRIZIONE", Type = DbType.String, CaseSensitive = false, Size = 150)]
        [DataMember]
		public string Descrizione { get; set; }

		[isRequired]
		[DataField("FK_D2BC_ID", Type = DbType.String, CaseSensitive = false, Size = 2)]
        [DataMember]
		public string FkD2bcId { get; set; }

		[DataField("SCRIPTCODE", Type = DbType.String, CaseSensitive = false, Size = 4000)]
        [DataMember]
		public string Scriptcode { get; set; }

		[DataField("MODELLOMULTIPLO", Type = DbType.Decimal)]
        [DataMember]
		public int? Modellomultiplo { get; set; }

		[DataField("FLG_STORICIZZA", Type = DbType.Decimal)]
        [DataMember]
		public int? FlgStoricizza { get; set; }

		[DataField("FLG_READONLY_WEB", Type = DbType.Decimal)]
        [DataMember]
		public int? FlgReadonlyWeb { get; set; }
		//[DataField("MODELLO_FRONTOFFICE", Type = DbType.Decimal)]
		//public int? ModelloFrontoffice{get; set;}

		[isRequired]
		[DataField("CODICE_SCHEDA", Type = DbType.String, CaseSensitive = false, Size = 50)]
        [DataMember]
		public string CodiceScheda { get; set; }

	}
}
