using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Bari.IMU
{
	[Serializable]
	public class ImuServiceInvocationException : Exception
	{
		public ImuServiceInvocationException() { }
		public ImuServiceInvocationException(string message) : base(message) { }
		public ImuServiceInvocationException(string message, Exception inner) : base(message, inner) { }
		protected ImuServiceInvocationException(
		  System.Runtime.Serialization.SerializationInfo info,
		  System.Runtime.Serialization.StreamingContext context)
			: base(info, context) { }
	}
}
