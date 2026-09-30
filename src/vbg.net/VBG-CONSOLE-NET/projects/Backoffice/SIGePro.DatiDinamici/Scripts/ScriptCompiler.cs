using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Reflection;
using Microsoft.CSharp;
using System.CodeDom.Compiler;
using Init.SIGePro.DatiDinamici.Exceptions;
using Init.SIGePro.DatiDinamici.Contesti;

namespace Init.SIGePro.DatiDinamici.Scripts
{
	internal class ScriptCompiler
	{
		private static class Constants
		{
			public static string CompilerVersion = "v4.0";
		}


		ReferencedAssembliesProvider _referencedAssembliesProvider;

		internal ScriptCompiler(ReferencedAssembliesProvider referencedAssembliesProvider)
		{
			this._referencedAssembliesProvider = referencedAssembliesProvider;
		}


		internal Assembly Compila(bool flagFrontoffice, ContestoModelloEnum contesto, string script)
		{
			var providerOptions = new Dictionary<string, string>();
			providerOptions.Add("CompilerVersion", Constants.CompilerVersion);

			var provider = new CSharpCodeProvider(providerOptions);

			CompilerParameters cp = new CompilerParameters();

			var referencedAssemblies = this._referencedAssembliesProvider.GetAssemblyReferenziati();

			foreach (var nomeAssembly in referencedAssemblies)
				cp.ReferencedAssemblies.Add(nomeAssembly);

			cp.CompilerOptions = "/define:" + contesto.ToString() + (flagFrontoffice ? ",frontoffice,Frontoffice,FRONTOFFICE,FrontOffice" : "") + ",DEBUG_SCRIPT";


			if (flagFrontoffice)
				script = script.Replace("IstanzaCorrente.Richiedenti.Count", "IstanzaCorrente.Richiedenti.Count()");
			
			cp.GenerateExecutable = false;
			cp.GenerateInMemory = true;

			// Verifico che la compilazione non abbia generato errori

			CompilerResults cr = provider.CompileAssemblyFromSource(cp, script);

			if (cr.Errors.HasErrors)
			{
				StringBuilder error = new StringBuilder();
				error.Append("Errore durante la compilazione della classe: \n\n");
				foreach (CompilerError err in cr.Errors)
				{
					if (ErroreDaEscludere(err.ErrorText))
					{
						continue;
					}

					error.AppendFormat("{0} (riga: {1})\n\n", err.ErrorText, err.Line);
				}
				throw new EvaluationException(error.ToString(), script);
			}

			return cr.CompiledAssembly;
		}

		private bool ErroreDaEscludere(string errMsg)
		{
			var exclusionList = new[]{
				"'La proprietà è obsoleta, utilizzare ListaValori[x].GetValoreODefault'",
				"'La proprietà è obsoleta, utilizzare ListaValori[x].Valore'"
			};

			for (int i = 0; i < exclusionList.Length; i++)
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
