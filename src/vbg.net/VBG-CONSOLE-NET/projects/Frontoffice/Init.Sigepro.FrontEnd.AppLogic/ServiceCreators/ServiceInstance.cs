using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.ServiceCreators
{
	public class ServiceInstance<SERVICE_TYPE>: IDisposable where SERVICE_TYPE: IDisposable
	{
		public virtual SERVICE_TYPE Service { get; private set; }
		public virtual string Token { get; private set; }

		public ServiceInstance(SERVICE_TYPE ws , string token)
		{
			Service = ws;
			Token = token;
		}

		#region IDisposable Members

		public virtual void Dispose()
		{
			this.Service.Dispose();
			GC.SuppressFinalize(this);
		}

		#endregion
	}
}
