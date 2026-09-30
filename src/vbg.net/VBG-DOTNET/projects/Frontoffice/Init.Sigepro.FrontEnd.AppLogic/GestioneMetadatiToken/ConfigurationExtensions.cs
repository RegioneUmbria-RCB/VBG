using VBG.Shared.Infrastructure.DependencyInjection;
using Init.Sigepro.FrontEnd.AppLogic.GestioneMetadatiToken.Client;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMetadatiToken
{
    public static class ConfigurazioneGestioneMetadatiToken
    {

        public static IDIProvider ConfiguraMetadatiToken(this IDIProvider k)
        {
            k.AddScoped<IMetadatiTokenUtenteService, MetadatiTokenUtenteService>();



            return k;
        }
    }
}
