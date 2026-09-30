using System;
using System.ServiceModel;

namespace VBG.Backend.Protocollo.AppLogic.Shared.ServiceCreators
{
    public class ServiceInstance<SERVICE_TYPE> : IDisposable where SERVICE_TYPE : class, IDisposable, ICommunicationObject
    {
        public virtual SERVICE_TYPE Service { get; private set; }

        public ServiceInstance(SERVICE_TYPE ws)
        {
            this.Service = ws;
        }

        #region IDisposable Members

        void IDisposable.Dispose()
        {
            this.Dispose(true);
            GC.SuppressFinalize(this);
        }

        protected virtual void Dispose(bool disposing)
        {
            if (this.Service != null)
            {
                try
                {
                    if (this.Service.State != CommunicationState.Closed && this.Service.State != CommunicationState.Faulted)
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

                this.Service.Dispose();

                this.Service = null;
            }
        }

        ~ServiceInstance()
        {
            this.Dispose(false);
        }

        #endregion
    }
}
