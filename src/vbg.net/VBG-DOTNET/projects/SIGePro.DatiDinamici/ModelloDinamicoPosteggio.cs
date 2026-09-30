using VBG.DatiDinamici;
using VBG.DatiDinamici.Contesti;
using VBG.DatiDinamici.DependencyInjection;
using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Scripts;

namespace Init.SIGePro.DatiDinamici
{
    public class ModelloDinamicoPosteggio : ModelloDinamicoBase
    {
        public ModelloDinamicoPosteggio(ModelloDinamicoLoader loader, IScriptCompiler scriptCompiler, IScriptDependencyInjectionService scriptDependencyInjectionService, int idModello, int indice, bool readOnly, int? idStorico = null)
            : base(idModello, indice, readOnly, idStorico, loader, scriptCompiler, scriptDependencyInjectionService)
        {
        }

        protected override ContestoModelloDinamico InizializzaContesto()
        {
            return new ContestoModelloDinamico(this.Token, ContestoModelloEnum.Posteggi, this.LeggiPosteggio());
        }

        private IClasseContestoModelloDinamico LeggiPosteggio()
        {
            return null;
            //throw new NotImplementedException();
            //return this.Loader.DataAccessFactory.GetClassLoader().LoadClass();
        }
    }
}
