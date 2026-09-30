
using VBG.Shared.Infrastructure.DependencyInjection;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;

public static class GestioneLocalizzazioniModule
{
    public static IDIProvider ConfiguraGestioneLocalizzazioni(this IDIProvider k)
    {
        k.AddScoped<StradarioServiceCreator>();
        k.AddScoped<CivicoValidoSpecification>();
        k.AddScoped<EsponenteValidoSpecification>();

        return k;
    }
}
