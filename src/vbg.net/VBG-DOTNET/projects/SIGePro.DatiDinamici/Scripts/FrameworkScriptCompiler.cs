using Microsoft.CSharp;
using System.CodeDom.Compiler;
using System.Collections.Generic;
using System.Reflection;
using System.Text;
using VBG.DatiDinamici.Scripts;
using VBG.DatiDinamici.Standard.Scripts;
using VBG.DatiDinamici.Standard.Scripts.TemplateReaders;

namespace Init.SIGePro.DatiDinamici.Framework.Scripts
{
    public class FrameworkScriptCompiler : IScriptCompiler
    {
        private static class Constants
        {
            public const string CompilerVersion = "v4.5";
        }

        private IScriptTemplateReader _scriptTemplateReader = new EmbeddedResourceScriptTemplateReader();

        public string ScriptTemplate
        {
            get
            {
                return this._scriptTemplateReader.ReadTemplate();
            }
            set
            {
                this.SetTemplateReader(new StringScriptTemplateReader(value));
            }
        }

        public IScriptTemplateReader GetScriptTemplateReader()
        {
            return this._scriptTemplateReader;
        }

        public void SetTemplateReader(IScriptTemplateReader templateReader)
        {
            this._scriptTemplateReader = templateReader;
        }

        private readonly ReferencedAssembliesProvider _referencedAssembliesProvider;

        public FrameworkScriptCompiler(ReferencedAssembliesProvider referencedAssembliesProvider)
        {
            this._referencedAssembliesProvider = referencedAssembliesProvider;
        }

        public FrameworkScriptCompiler()
        {
            this._referencedAssembliesProvider = new FrameworkReferencedAssembliesProvider();
        }


        public Assembly Compila(IScriptCompilabile script)
        {
            var referencedAssemblies = this._referencedAssembliesProvider.GetAssemblyReferenziati();

            var providerOptions = new Dictionary<string, string>();
            providerOptions.Add("CompilerVersion", Constants.CompilerVersion);

            var provider = new CSharpCodeProvider(/*providerOptions*/);

            var cp = new CompilerParameters();

            foreach (var nomeAssembly in referencedAssemblies)
                cp.ReferencedAssemblies.Add(nomeAssembly);

            cp.CompilerOptions = "/define:" + script.ContestoModello.ToString() + (script.ContestoScript == ContestoScriptEnum.Frontoffice ? ",frontoffice,Frontoffice,FRONTOFFICE,FrontOffice" : ",BACKOFFICE") + ",DEBUG_SCRIPT";

            var corpoScript = script.CorpoScript;

            if (script.ContestoScript == ContestoScriptEnum.Frontoffice)
                corpoScript = corpoScript.Replace("IstanzaCorrente.Richiedenti.Count", "IstanzaCorrente.Richiedenti.Count()");

            cp.GenerateExecutable = false;
            cp.GenerateInMemory = true;

            // Verifico che la compilazione non abbia generato errori

            var cr = provider.CompileAssemblyFromSource(cp, corpoScript);

            if (cr.Errors.HasErrors)
            {
                var error = new StringBuilder();
                error.Append("Errore durante la compilazione della classe: \n\n");
                foreach (CompilerError err in cr.Errors)
                {
                    if (this.ErroreDaEscludere(err.ErrorText))
                    {
                        continue;
                    }

                    error.AppendFormat("{0} (riga: {1})\n\n", err.ErrorText, err.Line);
                }
                throw new EvaluationException(error.ToString(), corpoScript);
            }

            return cr.CompiledAssembly;
        }

        private bool ErroreDaEscludere(string errMsg)
        {
            var exclusionList = new[]{
                "'La proprietà è obsoleta, utilizzare ListaValori[x].GetValoreODefault'",
                "'La proprietà è obsoleta, utilizzare ListaValori[x].Valore'"
            };

            for (var i = 0; i < exclusionList.Length; i++)
            {
                if (errMsg.IndexOf(exclusionList[i]) > 0)
                {
                    return true;
                }
            }

            return false;
        }
    }
}

