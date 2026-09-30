using System.Collections.Generic;
using System.ServiceModel;

namespace Init.Sigepro.FrontEnd.Infrastructure.ServiceModel.Framework
{
    public class FrameworkBindingFactory : IBindingFactory
    {
        public BasicHttpBinding CreateAndConfigure(string bindingName)
        {
            try
            {
                return new BasicHttpBinding(bindingName);
            }
            catch (KeyNotFoundException) //il binding non esiste nel web.config
            {
                return new BasicHttpBinding("defaultServiceBinding"); // uso il binding di default
            }
        }
    }
}
