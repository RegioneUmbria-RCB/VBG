#if NET48
using VBG.Shared.Infrastructure.ServiceModel;
using System.Collections.Generic;
using System.ServiceModel;

namespace VBG.Shared.Infrastructure.ServiceModel.Framework
{
    public class BindingFactory : IBindingFactory
    {
        public BasicHttpBinding CreateAndConfigure(string name)
        {
            try
            {
                var binding = new BasicHttpBinding(name);

                if (name == "defaultServiceBinding" || name == "areaRiservataServiceBinding")
                {
                    binding.MaxBufferSize = int.MaxValue;
                    binding.MaxReceivedMessageSize = int.MaxValue;
                    binding.ReaderQuotas.MaxArrayLength = int.MaxValue;
                }

                return binding;
            }
            catch (KeyNotFoundException) //il binding non esiste nel web.config
            {
                var binding = new BasicHttpBinding("defaultServiceBinding"); // uso il binding di default
                binding.MaxBufferSize = int.MaxValue;
                binding.MaxReceivedMessageSize = int.MaxValue;
                binding.ReaderQuotas.MaxArrayLength = 65536;

                return binding;
            }
        }
    }
}
#endif