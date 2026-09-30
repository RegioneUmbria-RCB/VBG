using System;
using System.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.ServiceCreators
{
    public class ServiceInstance<SERVICE_TYPE> : IDisposable
        where SERVICE_TYPE : ICommunicationObject
#if IGNORE
        ,IDisposable 
#endif
    {
        public virtual SERVICE_TYPE Service { get; private set; }
        public virtual string Token { get; private set; }

        public ServiceInstance(SERVICE_TYPE ws, string token)
        {
            this.Service = ws;
            this.Token = token;
        }

        #region IDisposable Members

        void IDisposable.Dispose()
        {
            this.Dispose(true);
        }

        protected virtual void Dispose(bool disposing)
        {
            if (disposing)
            {
                try
                {
                    if (this.Service.State != CommunicationState.Faulted)
                    {
                        this.Service.Close();
                    }
                }
                finally
                {
                    if (this.Service.State != CommunicationState.Closed)
                    {
                        this.Service.Abort();
                    }
                }
            }
        }

        ~ServiceInstance()
        {
            this.Dispose(false);
        }

        #endregion
    }
}
