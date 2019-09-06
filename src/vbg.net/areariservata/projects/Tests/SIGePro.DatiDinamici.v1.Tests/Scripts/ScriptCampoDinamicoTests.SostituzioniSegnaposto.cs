using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Init.SIGePro.DatiDinamici.Scripts;
using Init.SIGePro.DatiDinamici;
using Init.SIGePro.DatiDinamici.Contesti;

namespace SIGePro.DatiDinamici.v1.Tests.Scripts
{
	public partial class ScriptCampoDinamicoTests
	{
		[TestClass]
		public class SostituzioniSegnaposto
		{
			StubScriptTemplateReader _templateReader;
			ScriptCampoDinamico _script;
			string _corpoScript = "corpo_script";
			string _funzioniComuni = "funzioni-comuni";

			[TestInitialize]
			public void Initialize()
			{
				this._templateReader = new StubScriptTemplateReader();

				var contesto = new ContestoModelloDinamico("", ContestoModelloEnum.Istanza, null, this._templateReader);

				this._script = ScriptCampoDinamico.CreaScriptDesignTime(contesto, _corpoScript, _funzioniComuni);
			}

			[TestMethod]
			public void Il_segnaposto_dei_namespace_viene_sostituito()
			{
				this._templateReader.ScriptTemplate = "@NAMESPACE";

				var expected = "RuntimeNamespace";

				var result = this._script.GetCodiceScript();

				Assert.AreEqual(expected, result);
			}

			[TestMethod]
			public void Il_segnaposto_del_nome_classe_viene_sostituito()
			{
				this._templateReader.ScriptTemplate = "@NOME_CLASSE";

				var expected = "RuntimeClass";

				var result = this._script.GetCodiceScript();

				Assert.AreEqual(expected, result);
			}

			[TestMethod]
			public void Il_segnaposto_del_metodo_viene_sostituito()
			{
				this._templateReader.ScriptTemplate = "@NOME_METODO";

				var expected = "RuntimeMethod";

				var result = this._script.GetCodiceScript();

				Assert.AreEqual(expected, result);
			}

			[TestMethod]
			public void Il_segnaposto_del_corpo_script_viene_sostituito()
			{
				this._templateReader.ScriptTemplate = "@CORPO_SCRIPT";

				var expected = this._corpoScript;

				var result = this._script.GetCodiceScript();

				Assert.AreEqual(expected, result);
			}

			[TestMethod]
			public void Il_segnaposto_delle_funzioni_comuni_viene_sostituito()
			{
				this._templateReader.ScriptTemplate = "@FUNZIONI_COMUNI";

				var expected = this._funzioniComuni;

				var result = this._script.GetCodiceScript();

				Assert.AreEqual(expected, result);
			}
		}
	}
}
