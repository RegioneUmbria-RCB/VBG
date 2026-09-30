using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.Exceptions
{
	/// <summary>
	/// Rappresenta un errore generato durante la compilazione di uno script
	/// </summary>
	public class EvaluationException : Exception
	{
		public string Code { get; protected set; }

		public EvaluationException(string message, string code)
			: base(message)
		{
			Code = code;
		}
	}
}
