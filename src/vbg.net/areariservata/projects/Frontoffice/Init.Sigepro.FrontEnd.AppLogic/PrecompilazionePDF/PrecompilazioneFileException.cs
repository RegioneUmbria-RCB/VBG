using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.PrecompilazionePDF
{
	[Serializable]
	public class PrecompilazionePdfException : Exception
	{
		public PrecompilazionePdfException() { }
		public PrecompilazionePdfException(string message) : base(message) { }
		public PrecompilazionePdfException(string message, Exception inner) : base(message, inner) { }
		protected PrecompilazionePdfException(
		  System.Runtime.Serialization.SerializationInfo info,
		  System.Runtime.Serialization.StreamingContext context)
			: base(info, context) { }
	}
}
