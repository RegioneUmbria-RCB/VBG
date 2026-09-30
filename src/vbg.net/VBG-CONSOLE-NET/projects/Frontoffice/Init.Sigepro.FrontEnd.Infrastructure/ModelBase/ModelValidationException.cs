// -----------------------------------------------------------------------
// <copyright file="ModelValidationException.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Infrastructure.ModelBase
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	[Serializable]
	public class ModelValidationException : Exception
	{
		public ModelValidationException() { }
		public ModelValidationException(string message) : base(message) { }
		public ModelValidationException(string message, Exception inner) : base(message, inner) { }
		protected ModelValidationException(
		  System.Runtime.Serialization.SerializationInfo info,
		  System.Runtime.Serialization.StreamingContext context)
			: base(info, context) { }
	}
}
