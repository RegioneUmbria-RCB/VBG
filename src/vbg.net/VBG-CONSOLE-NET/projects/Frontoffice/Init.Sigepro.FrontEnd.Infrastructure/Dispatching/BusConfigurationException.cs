// -----------------------------------------------------------------------
// <copyright file="BusConfigurationException.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Infrastructure.Dispatching
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	[Serializable]
	public class  BusConfigurationException : Exception
	{
		public  BusConfigurationException() { }
		public  BusConfigurationException(string message) : base(message) { }
		public  BusConfigurationException(string message, Exception inner) : base(message, inner) { }
		protected  BusConfigurationException(
		  System.Runtime.Serialization.SerializationInfo info,
		  System.Runtime.Serialization.StreamingContext context)
			: base(info, context) { }
	}
}
