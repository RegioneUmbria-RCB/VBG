using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Exceptions
{
	[Serializable]
	public class StepException : System.Exception
	{
		public List<string> ErrorMessages { get; set; }

		public StepException() { ErrorMessages = new List<string>(); }
		public StepException(string message)
			: base(message)
		{
			ErrorMessages = new List<string>();

			ErrorMessages.Add(message);
		}

		public StepException(IEnumerable<string> messages)
			: base(messages.ElementAt(0))
		{
			ErrorMessages = messages.ToList();
		}

		public StepException(string message, System.Exception inner) : base(message, inner) { ErrorMessages = new List<string>(); }

		protected StepException(
		  System.Runtime.Serialization.SerializationInfo info,
		  System.Runtime.Serialization.StreamingContext context)
			: base(info, context)
		{
			ErrorMessages = new List<string>();
		}
	}
}