using System;

namespace Init.Sigepro.FrontEnd.AppLogic
{
	/// <summary>
	/// Eccezione verificatasi durante la visura
	/// </summary>
	public class VisuraException : Exception
	{
		public VisuraException(string msg)
			: base(msg)
		{

		}
	}
}
