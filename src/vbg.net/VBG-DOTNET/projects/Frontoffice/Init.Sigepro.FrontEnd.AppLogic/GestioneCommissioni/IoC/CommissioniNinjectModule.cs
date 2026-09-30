using Init.Sigepro.FrontEnd.AppLogic.GestioneCommissioni.AccessoPIN;
using Init.Sigepro.FrontEnd.AppLogic.GestioneCommissioni.AccessoPIN.WebService;
using Init.Sigepro.FrontEnd.AppLogic.GestioneCommissioni.Votazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneCommissioni.Votazioni.WebService;
using Init.Sigepro.FrontEnd.AppLogic.GestioneCommissioni.WebService;
using VBG.Shared.Infrastructure.DependencyInjection;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneCommissioni.IoC
{
    public static class CommissioniNinjectExtensions
    {
        public static IDIProvider ConfigurCommissioni(this IDIProvider k)
        {
            k.AddScoped<CommissioniWsProxyCreator>();
            k.AddScoped<ICommissioniService, CommissioniService>();
            k.AddScoped<ICommissioniDao, CommissioniWsDao>();

            // Votazioni
            k.AddScoped<VotazioniCommissioneWsProxyCreator>();
            k.AddScoped<IVotazioniCommissioniService, VotazioniCommissioneService>();
            k.AddScoped<IVotazioniCommissioneDao, VotazioniCommissioneWsDao>();

            // Accesso PIN
            k.AddScoped<CommissioniAccessoPINServiceCreator>();
            k.AddScoped<IAccessoPINService, AccessoPINService>();
            k.AddScoped<IAccessoPINDao, AccessoPINWsDao>();

            return k;
        }
    }
}
