using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.GestioneQrCode;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using VBG.Shared.Infrastructure.DependencyInjection;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda
{
    public static class ConfigurazioneDIGenerazioneDocumenti
    {
        public static IDIProvider ConfiguraGenerazioneDocumenti(this IDIProvider services)
        {
            // Qr code
            services.AddScoped<QrCodeServiceCreator>();
            services.AddScoped<IGenerazioneQrCodeServiceWrapper, GenerazioneQrCodeServiceWrapper>();
            services.AddScoped<ISostituzioneSegnapostoQrCode, SostituzioneSegnapostoQrCode>();

            return services;
        }
    }
}
