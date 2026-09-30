using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Reflection;
using Init.SIGePro.DatiDinamici.Contesti;

namespace Init.SIGePro.DatiDinamici
{
	public interface IScriptTemplateReader
	{
		string ReadTemplate();
	}

	public class ResourceScriptTemplateReader : IScriptTemplateReader
	{
		private static class Constants
		{
			public const string NomeRisorsaTemplate = "Init.SIGePro.DatiDinamici.Resources.ClasseRuntime.cstemplate";
		}


		public ResourceScriptTemplateReader()
		{
		}

		public string ReadTemplate()
		{
			var memStream = Assembly.GetExecutingAssembly().GetManifestResourceStream(Constants.NomeRisorsaTemplate);
			var byteData = new byte[memStream.Length];

			memStream.Read(byteData, 0, byteData.Length);

			return Encoding.ASCII.GetString(byteData);
		}
	}

	public class StubScriptTemplateReader : IScriptTemplateReader
	{
		public string ScriptTemplate { get; set; }

		public string ReadTemplate()
		{
			return this.ScriptTemplate;
		}
	}

}
