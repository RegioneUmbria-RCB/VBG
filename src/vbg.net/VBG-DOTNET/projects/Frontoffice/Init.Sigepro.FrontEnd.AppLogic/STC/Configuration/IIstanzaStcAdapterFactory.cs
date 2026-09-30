using VBG.Shared.Infrastructure.DependencyInjection;
using Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters;
using Init.Sigepro.FrontEnd.AppLogic.STC.Adapter;
using System;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.STC.Configuration
{
    public class IstanzaStcAdapterFactory
    {
        private readonly List<Type> _registeredAdapters = new List<Type>();
        public void RegisterAdapter<T>() where T : IStcPartialAdapter
        {
            this._registeredAdapters.Add(typeof(T));
        }

        public IIstanzaStcAdapter CreateInstance(IDIServiceProvider serviceProvider)
        {
            var stcAdapter = serviceProvider.GetService<IstanzaStcAdapter>();

            foreach (var adapterType in this._registeredAdapters)
            {
                var adapterInstance = (IStcPartialAdapter)serviceProvider.GetService(adapterType);
                stcAdapter.RegisterPartialAdapter(adapterInstance);
            }

            return stcAdapter;
        }
    }
}
