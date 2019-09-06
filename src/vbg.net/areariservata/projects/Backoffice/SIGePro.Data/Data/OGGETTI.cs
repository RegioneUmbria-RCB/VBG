using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
	[DataTable("OGGETTI")]
	[Serializable]
	public class Oggetti : BaseDataClass
	{

		#region Key Fields
		string codiceoggetto=null;
		[useSequence]
		[KeyField("CODICEOGGETTO",Type=DbType.Int16)]
		[XmlElement(Order = 0)]
		public string CODICEOGGETTO
		{
			get { return codiceoggetto; }
			set { codiceoggetto = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE", Size=6, Type=DbType.String)]
		[XmlElement(Order = 1)]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

		byte[] oggetto=null;
		//TODO: Commentato per ravenna
		//[isRequired]
		[DataField("OGGETTO", Type=DbType.Binary)]
		[XmlElement(Order = 2)]
		public byte[] OGGETTO
		{
			get { return oggetto; }
			set { oggetto = value; }
		}

		string nomefile=null;
		[isRequired]
		[DataField("NOMEFILE", Size=128, Type=DbType.String, CaseSensitive=false)]
		[XmlElement(Order = 3)]
		public string NOMEFILE
		{
			get { return nomefile; }
			set { nomefile = value; }
		}
	}
}