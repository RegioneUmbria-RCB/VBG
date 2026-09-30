using System;

namespace Init.SIGePro.DatiDinamici.DependencyInjection
{
    /// <summary>
    /// Implementato a livello di web application, altrimenti avrei dovuto tirare dentro una dipendenza da *.infrastructure.dll
    /// Siccome questa libreria è utilizzata anche a livello di backend preferisco astrarre l'interfaccia che risolve le dipendenze per lasciarla
    /// implementare nei vari progetti specifici
    /// </summary>
    public interface IFrameworkDependencyResolver
    {
        object GetServiceForType(Type type);
    }
}
