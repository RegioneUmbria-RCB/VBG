using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.Interfaces;
using System.Reflection;
using Init.SIGePro.DatiDinamici.VisibilitaCampi;

namespace Init.SIGePro.DatiDinamici.Scripts
{
	internal class ScriptCompilatoBuilder
	{
		object _objectInstance;
		string _nomeProprietaToken;
		string _token;
		string _nomeProprietaModello;
		ModelloDinamicoBase _modello;
		string _nomeProprietaClasseContesto;
		IClasseContestoModelloDinamico _classeContesto;
		string _nomeMetodoRuntime;
		string _nomeMetodoPreExecute;
		string _nomeMetodoPostExecute;

		internal ScriptCompilatoBuilder (object objectInstance)
		{
			_objectInstance = objectInstance;
		}

		internal ScriptCompilatoBuilder WithToken(string nomeProprietaToken, string token)
		{
			_nomeProprietaToken = nomeProprietaToken;
			_token = token;

			return this;
		}

		internal ScriptCompilatoBuilder WithModello(string nomeProprietaModello, ModelloDinamicoBase modello)
		{
			_nomeProprietaModello = nomeProprietaModello;
			_modello = modello;

			return this;
		}

		internal ScriptCompilatoBuilder WithClasseContesto(string nomeProprietaClasseContesto, IClasseContestoModelloDinamico classeContesto)
		{
			_nomeProprietaClasseContesto = nomeProprietaClasseContesto;
			_classeContesto = classeContesto;

			return this;
		}

		internal ScriptCompilatoBuilder WithNomeMetodoRuntime(string nomeMetodoRuntime)
		{
			_nomeMetodoRuntime = nomeMetodoRuntime;

			return this;
		}

		internal ScriptCompilatoBuilder WithNomeMetodoPreExecute(string nomeMetodo)
		{
			this._nomeMetodoPreExecute = nomeMetodo;

			return this;
		}

		internal ScriptCompilatoBuilder WithNomeMetodoPostExecute(string nomeMetodo)
		{
			this._nomeMetodoPostExecute = nomeMetodo;

			return this;
		}

		internal ScriptCompilato Build()
		{
			return new ScriptCompilato(this._objectInstance,
										this._nomeProprietaToken, this._token,
										this._nomeProprietaModello, this._modello,
										this._nomeProprietaClasseContesto, this._classeContesto,
										this._nomeMetodoRuntime,
										this._nomeMetodoPreExecute,
										this._nomeMetodoPostExecute);
		}
	}


	internal class ScriptCompilato
	{
		MethodInfo _metodoRuntime;
		MethodInfo _metodoPreExecute;
		MethodInfo _metodoPostExecute;
		object _objectInstance;

		internal ScriptCompilato(object objectInstance, 
								 string nomeProprietaToken , string token , 
								 string nomeProprietaModello , ModelloDinamicoBase modello,
								 string nomeProprietaClasse, IClasseContestoModelloDinamico classeContesto , 
								 string nomeMetodoRuntime, string nomeMetodoPreExecute, string nomeMetodoPostExecute)
		{
			this._objectInstance = objectInstance;

			ImpostaProprietaClasse(nomeProprietaToken, token);
			ImpostaProprietaClasse(nomeProprietaModello, modello);
			ImpostaProprietaClasse(nomeProprietaClasse, classeContesto);

			OttieniRiferimentoMetodoRuntime(nomeMetodoRuntime);
			OttieniRiferimentoMetodoPreExecute(nomeMetodoPreExecute);
			OttieniRiferimentoMetodoPostExecute(nomeMetodoPostExecute);
		}

		private void OttieniRiferimentoMetodoPostExecute(string nomeMetodoPostExecute)
		{
			this._metodoPostExecute = this._objectInstance.GetType().GetMethod(nomeMetodoPostExecute);
		}

		private void OttieniRiferimentoMetodoPreExecute(string nomeMetodoPreExecute)
		{
			this._metodoPreExecute = this._objectInstance.GetType().GetMethod(nomeMetodoPreExecute);
		}

		private void OttieniRiferimentoMetodoRuntime(string nomeMetodoRuntime)
		{
			this._metodoRuntime = this._objectInstance.GetType().GetMethod(nomeMetodoRuntime);
		}

		/// <summary>
		/// Imposta le proprietà della classe generata a runtime
		/// </summary>
		protected void ImpostaProprietaClasse(string nomeProprieta, object valore)
		{
			var pi = this._objectInstance.GetType().GetProperty(nomeProprieta);
			pi.SetValue(this._objectInstance, valore, null);
		}

		public object GetValoreProprieta(string nomeProprieta)
		{
			var pi = this._objectInstance.GetType().GetProperty(nomeProprieta);

			if (pi == null)
				return null;

			return pi.GetValue(this._objectInstance, null);
		}

		public object Esegui(CampoDinamicoBase campoModificato)
		{
			return this._metodoRuntime.Invoke(_objectInstance, new object[] { campoModificato });
		}

		public void OnPreExecute(SessioneModificaVisibilitaCampi sessioneModificaVisibilita)
		{
			this._metodoPreExecute.Invoke(this._objectInstance, new object[] { sessioneModificaVisibilita });
		}

		public void OnPostExecute()
		{
			this._metodoPostExecute.Invoke(this._objectInstance, null);
		}
	}
}
