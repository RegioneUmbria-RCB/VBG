using System.ServiceModel;

namespace GeneratoreRiepiloghiHtml.AppLogic.Infrastructure.ServiceCreators
{
    public class ServiceInstance<SERVICE_TYPE> : IDisposable where SERVICE_TYPE : IDisposable, ICommunicationObject
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
            this.DisposeInternal(true);
        }

        protected virtual void DisposeInternal(bool disposing)
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
            this.DisposeInternal(false);
        }

        #endregion
    }
}