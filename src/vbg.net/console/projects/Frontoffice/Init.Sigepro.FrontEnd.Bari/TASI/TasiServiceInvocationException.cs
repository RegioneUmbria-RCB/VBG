// -----------------------------------------------------------------------
// <copyright file="TasiServiceInvocationException.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.TASI
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	[Serializable]
	public class TasiServiceInvocationException : Exception
	{
		public TasiServiceInvocationException() { }
		public TasiServiceInvocationException(string message) : base(message) { }
		public TasiServiceInvocationException(string message, Exception inner) : base(message, inner) { }
		protected TasiServiceInvocationException(
		  System.Runtime.Serialization.SerializationInfo info,
		  System.Runtime.Serialization.StreamingContext context)
			: base(info, context) { }
	}
}
