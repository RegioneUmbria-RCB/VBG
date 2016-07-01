using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Reflection;
using System.Web;
using Init.SIGePro.DatiDinamici.Exceptions;
using System.IO;
using System.CodeDom.Compiler;
using Microsoft.CSharp;
using Init.SIGePro.DatiDinamici.ValidazioneValoriCampi;

namespace Init.SIGePro.DatiDinamici.Scripts
{
	public class ScriptCampoDinamico
	{
		private static class Constants
		{
			public const string NomeNamespaceRuntime = "RuntimeNamespace";
			public const string NomeClasseRuntime = "RuntimeClass";
			public const string NomeMetodoRuntime = "RuntimeMethod";
			public const string NomeProprietaErroriValidazione = "ErroriValidazione";

			public const string NomeMetodoPreExecute = "OnPreExecute";
			public const string NomeMetodoPostExecute = "OnPostExecute";

			public const string NomeProprietaToken = "Token";
			public const string NomeProprietaModello = "ModelloCorrente";

			public const string SegnapostoNamespace = "@NAMESPACE";
			public const string SegnapostoNomeClasse = "@NOME_CLASSE";
			public const string SegnapostoNomeMetodo = "@NOME_METODO";
			public const string SegnapostoFunzioniComuni = "@FUNZIONI_COMUNI";
			public const string SegnapostoCorpoScript = "@CORPO_SCRIPT";
		}

		ModelloDinamicoBase		_modello;
		ScriptCompilato			_oggettoCompilato;
		string					_corpoScript;
		string					_funzioniCondivise;
		ContestoModelloDinamico _contesto;

		public static ScriptCampoDinamico CreaScriptDesignTime(ContestoModelloDinamico contesto, string corpoScript, string funzioniCondivise = "")
		{
			ScriptCampoDinamico script = new ScriptCampoDinamico();
			script._contesto = contesto;
			script._corpoScript = corpoScript;
			script._funzioniCondivise = funzioniCondivise;
			return script;
		}

		private ScriptCampoDinamico()
		{

		}

		internal ScriptCampoDinamico(ModelloDinamicoBase modello, string corpoScript, string funzioniCondivise = "")
		{
			_modello = modello;
			_corpoScript = corpoScript;
			_contesto = _modello.Contesto;
			_funzioniCondivise = funzioniCondivise;
		}

		public void TestCompilazione(bool flgFrontoffice)
		{
			Compila(flgFrontoffice);
		}

		public object Compila()
		{
			return Compila(false);
		}

		/// <summary>
		/// Ottiene il codice dello script in formato plain text
		/// </summary>
		/// <returns>Codice dello script</returns>
		public string GetCodiceScript()
		{
			//m_scriptTemplate = m_contesto.GetScriptTemplate();

			return PreparaCorpoScript();
		}

		internal object Compila(bool forzaFrontoffice)
		{
			//m_scriptTemplate = m_contesto.GetScriptTemplate();

			string script = PreparaCorpoScript();

			bool utilizzoFrontoffice = _modello != null && (_modello.ModelloFrontoffice);

			if (forzaFrontoffice)
				utilizzoFrontoffice = true;

			var compiler = new ScriptCompiler(new ReferencedAssembliesProvider(Assembly.GetCallingAssembly()));

			var assembly = compiler.Compila(utilizzoFrontoffice, _contesto.TipoContesto, script);

			return assembly.CreateInstance(String.Format("{0}.{1}", Constants.NomeNamespaceRuntime, Constants.NomeClasseRuntime));
		}


		/// <summary>
		/// Carica il template della classe dalla risorsa incorporata ed effettua la sostituzione dei segnaposto
		/// </summary>
		/// <returns></returns>
		private string PreparaCorpoScript()
		{
			var template = _contesto.GetScriptTemplate();

			template = template.Replace(Constants.SegnapostoNamespace, Constants.NomeNamespaceRuntime);
			template = template.Replace(Constants.SegnapostoNomeClasse, Constants.NomeClasseRuntime);
			template = template.Replace(Constants.SegnapostoNomeMetodo, Constants.NomeMetodoRuntime);
			template = template.Replace(Constants.SegnapostoFunzioniComuni, this._funzioniCondivise);
			template = template.Replace(Constants.SegnapostoCorpoScript, this._corpoScript);

			return template;
		}

		/// <summary>
		/// Esegue lo script
		/// </summary>
		public object Esegui(CampoDinamicoBase campo)
		{
			AssicuraComilazione();

			try
			{
				return this._oggettoCompilato.Esegui(campo);
			}
			catch (Exception)
			{
				throw;
			}
			finally
			{
			}
		}

		private void AssicuraComilazione()
		{
			if (this._oggettoCompilato == null)
			{
				var istanzaOggetto = Compila();

				this._oggettoCompilato = new ScriptCompilatoBuilder(istanzaOggetto)
													.WithToken(Constants.NomeProprietaToken, _modello.Contesto.Token)
													.WithModello(Constants.NomeProprietaModello, _modello)
													.WithClasseContesto(_modello.Contesto.NomePropertyClasse, _modello.Contesto.Classe)
													.WithNomeMetodoRuntime(Constants.NomeMetodoRuntime)
													.WithNomeMetodoPreExecute(Constants.NomeMetodoPreExecute)
													.WithNomeMetodoPostExecute(Constants.NomeMetodoPostExecute)
													.Build();
			}
		}

		internal IEnumerable<ErroreValidazione> GetErroriValidazione()
		{
			if (this._oggettoCompilato == null)
			{
				return Enumerable.Empty<ErroreValidazione>();
			}
			var value = this._oggettoCompilato.GetValoreProprieta(Constants.NomeProprietaErroriValidazione);

			return (IEnumerable<ErroreValidazione>)value;
		}

		internal void PreExecute(VisibilitaCampi.SessioneModificaVisibilitaCampi sessioneModificaVisibilita)
		{
			System.Diagnostics.Debug.WriteLine("ScriptCampoDinamico->PreExecute");

			AssicuraComilazione();

			this._oggettoCompilato.OnPreExecute(sessioneModificaVisibilita);
		}

		internal void PostExecute()
		{
			System.Diagnostics.Debug.WriteLine("ScriptCampoDinamico->PostExecute");

			AssicuraComilazione();

			this._oggettoCompilato.OnPostExecute();
		}
	}
}
