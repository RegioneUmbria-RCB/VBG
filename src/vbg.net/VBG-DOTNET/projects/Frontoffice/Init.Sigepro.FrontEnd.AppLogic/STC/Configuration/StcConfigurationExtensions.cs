using VBG.Shared.Infrastructure.DependencyInjection;
using Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Stc;
using Init.Sigepro.FrontEnd.AppLogic.STC.Adapter;
using Init.Sigepro.FrontEnd.AppLogic.STC.Service;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.STC.Configuration
{
    public static class StcConfigurationExtensions
    {
        private static IstanzaStcAdapterFactory? _stcAdapterFactory;

        private static IDIProvider ConfiguraStcAdapter(this IDIProvider services)
        {
            if (_stcAdapterFactory != null)
            {
                throw new InvalidOperationException("La configurazione degli adapter STC è già stata eseguita");
            }

            _stcAdapterFactory = new IstanzaStcAdapterFactory();

            services.AddScoped<IstanzaStcAdapter>();

            services.RegistraStcAdapter<DatiPraticaAdapter>();
            services.RegistraStcAdapter<ComuniAssociatiAdapter>();
            services.RegistraStcAdapter<RichiedenteAdapter>();
            services.RegistraStcAdapter<AziendaAdapter>();
            services.RegistraStcAdapter<TecnicoAdapter>();
            services.RegistraStcAdapter<AltriSoggettiAdapter>();
            services.RegistraStcAdapter<ProcureAdapter>();
            services.RegistraStcAdapter<LocalizzazioneAdapter>();
            services.RegistraStcAdapter<DocumentiAdapter>();
            services.RegistraStcAdapter<AltriDatiAdapter>();
            services.RegistraStcAdapter<ProcedimentiAdapter>();
            services.RegistraStcAdapter<OneriAdapter>();
            services.RegistraStcAdapter<DatiDinamiciAdapter>();

            services.AddScoped(sp =>
            {
                return _stcAdapterFactory.CreateInstance(sp);
            });
            return services;
        }

        public static IDIProvider RegistraStcAdapter<T>(this IDIProvider services) where T : class, IStcPartialAdapter
        {
            if (_stcAdapterFactory == null)
            {
                throw new InvalidOperationException("La configurazione degli adapter STC non è stata ancora eseguita. Eseguire prima ConfiguraStcAdapter");
            }

            services.AddScoped<T>();

            _stcAdapterFactory.RegisterAdapter<T>();

            return services;
        }


        public static IDIProvider ConfiguraStc(this IDIProvider services)
        {
            services.AddScoped<IStcService, StcServiceImpl>();
            services.AddScoped<StcToken>();
            services.AddScoped<IStcServiceCreator, StcServiceCreator>();

            services.ConfiguraStcAdapter();

            return services;
        }
    }
}
