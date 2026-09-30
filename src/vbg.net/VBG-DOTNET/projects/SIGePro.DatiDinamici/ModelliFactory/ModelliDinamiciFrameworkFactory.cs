using Init.SIGePro.DatiDinamici.Framework.Scripts;
using VBG.DatiDinamici;
using VBG.DatiDinamici.DependencyInjection;
using VBG.DatiDinamici.Scripts;

namespace Init.SIGePro.DatiDinamici.Framework.ModelliFactory
{
    public class ModelliDinamiciFrameworkFactory : IModelliDinamiciFactory
    {
        private readonly IScriptDependencyInjectionService _scriptDependencyInjectionService;

        public ModelliDinamiciFrameworkFactory(IScriptDependencyInjectionService scriptDependencyInjectionService)
        {
            this._scriptDependencyInjectionService = scriptDependencyInjectionService;
        }

        public ModelloDinamicoAnagrafica CreaModelloAnagrafe(ModelloDinamicoLoader loader, int idModello, int indice, bool readOnly, int? idStorico = null)
        {
            var scriptCompiler = new CachedScriptCompiler(new FrameworkScriptCompiler(new FrameworkReferencedAssembliesProvider()));
            return new ModelloDinamicoAnagrafica(loader, scriptCompiler, this._scriptDependencyInjectionService, idModello, indice, readOnly, idStorico);
        }

        public ModelloDinamicoIstanza CreaModelloIstanza(ModelloDinamicoLoader loader, int idModello, int indice, bool readOnly, int? idStorico = null)
        {
            var scriptCompiler = new CachedScriptCompiler(new FrameworkScriptCompiler(new FrameworkReferencedAssembliesProvider()));
            return new ModelloDinamicoIstanza(loader, scriptCompiler, this._scriptDependencyInjectionService, idModello, indice, readOnly, idStorico);
        }

        public ModelloDinamicoMercato CreaModelloMercato(ModelloDinamicoLoader loader, int idModello, int indice, bool readOnly, int? idStorico = null)
        {
            var scriptCompiler = new CachedScriptCompiler(new FrameworkScriptCompiler(new FrameworkReferencedAssembliesProvider()));
            return new ModelloDinamicoMercato(loader, scriptCompiler, this._scriptDependencyInjectionService, idModello, indice, readOnly, idStorico);
        }

        public ModelloDinamicoAttivita CreaModelloAttivita(ModelloDinamicoLoader loader, int idModello, int indice, bool readOnly, int? idStorico = null)
        {
            var scriptCompiler = new CachedScriptCompiler(new FrameworkScriptCompiler(new FrameworkReferencedAssembliesProvider()));
            return new ModelloDinamicoAttivita(loader, scriptCompiler, this._scriptDependencyInjectionService, idModello, indice, readOnly, idStorico);
        }

    }
}
