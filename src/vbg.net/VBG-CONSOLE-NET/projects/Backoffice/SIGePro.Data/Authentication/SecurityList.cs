using System;
using Init.SIGePro.Collection;

namespace Init.SIGePro.Authentication
{
	/// <summary>
	/// Descrizione di riepilogo per CSecurityList.
	/// </summary>
	//Non utilizzata
	[System.Xml.Serialization.XmlRootAttribute("List")]
	public class CSecurityList
	{
		public CSecurityList()
		{
		}

		SecurityList _List;
		[System.Xml.Serialization.XmlElementAttribute("SecurityInfo")]
		public SecurityList SecurityInfoList
		{
			get { return _List; }
			set { _List = value; }
		}
	}
}
