using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;
using System.IO;
using System.Reflection;

namespace Init.SIGePro.DatiDinamici.Scripts
{
	internal class ReferencedAssembliesProvider
	{
		string[] _dllFiles;

		internal ReferencedAssembliesProvider(Assembly assemblyinEsecuzione)
		{
			string referencePath = null;

			if (HttpContext.Current != null)
				referencePath = HttpContext.Current.Server.MapPath("~/bin");
			else
				referencePath = Path.GetDirectoryName(assemblyinEsecuzione.Location);

			_dllFiles = Directory.GetFiles(referencePath, "*.dll");
		}


		internal IEnumerable<string> GetAssemblyReferenziati( )
		{
			yield return "mscorlib.dll";
			yield return "system.dll";
			yield return "system.Core.dll";
			yield return "system.data.dll";
			yield return "System.Web.dll";
			yield return "System.Xml.dll";
			yield return "System.Web.Services.dll";

			foreach (var dll in _dllFiles)
				yield return dll;
		}
	}
}
