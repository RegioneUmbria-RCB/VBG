using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
	[DataTable("ISTANZEMAPPALI")]
	[Serializable]
	public partial class IstanzeMappali : BaseDataClass
	{
		[KeyField("IDMAPPALE" , Type=DbType.Decimal)]
        [useSequence]
		[XmlElement(Order=0)]
		public int? Idmappale {get;set;}
							
		[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
		[XmlElement(Order = 1)]
		public string Idcomune{get;set;}
							
		[DataField("FOGLIO" , Type=DbType.String, CaseSensitive=false, Size=50)]
		[XmlElement(Order = 2)]
		public string Foglio{get;set;}
							
		[DataField("PARTICELLA" , Type=DbType.String, CaseSensitive=false, Size=150)]
		[XmlElement(Order = 3)]
		public string Particella{get;set;}
							
		[DataField("SUB" , Type=DbType.String, CaseSensitive=false, Size=50)]
		[XmlElement(Order = 4)]
		public string Sub {get;set;}
							
		[isRequired]
        [DataField("FKCODICEISTANZA" , Type=DbType.Decimal)]
		[XmlElement(Order = 5)]
		public int? Fkcodiceistanza {get;set;}
							
		[DataField("PRIMARIO" , Type=DbType.Decimal)]
		[XmlElement(Order = 6)]
		public int? Primario {get;set;}
							
		[DataField("CODICECATASTO" , Type=DbType.String, CaseSensitive=false, Size=1)]
		[XmlElement(Order = 7)]
		public string Codicecatasto {get;set;}
							
		[DataField("SEZIONE" , Type=DbType.String, CaseSensitive=false, Size=10)]
		[XmlElement(Order = 8)]
		public string Sezione {get;set;}
							
		[DataField("UNITAIMMOB" , Type=DbType.String, CaseSensitive=false, Size=30)]
		[XmlElement(Order = 9)]
		public string Unitaimmob {get;set;}

		[DataField("FKIDISTANZESTRADARIO", Type = DbType.Decimal)]
		[XmlElement(Order = 10)]
		public int? FkIdIstanzeStradario { get; set; }							

		[ForeignKey("Codicecatasto", "CODICE")]
		[XmlElement(Order = 11)]
		public Catasto Catasto{get;set;}

	}
}