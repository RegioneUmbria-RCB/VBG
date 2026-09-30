using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Reflection;
using System.IO;
using Init.SIGePro.DatiDinamici.Interfaces;
using Init.SIGePro.DatiDinamici.Contesti;

namespace Init.SIGePro.DatiDinamici
{
	public class ContestoModelloDinamico
	{
		string m_token;
		IClasseContestoModelloDinamico m_classe;
		DatiDinamiciInfoDictionaryItem m_datiContesto;
		IScriptTemplateReader _templateReader;


		public ContestoModelloEnum TipoContesto
		{
			get { return m_datiContesto.TipoContestoEnum; }
		}

		/// <summary>
		/// Token di SIGePro, utilizzato negli script
		/// </summary>
		public string Token
		{
			get { return m_token; }
		}


		/// <summary>
		/// Classe con cui valorizzare la property specificata in "NomePropertyClasse"
		/// </summary>
		internal IClasseContestoModelloDinamico Classe
		{
			get { return m_classe; }
		}


		/// <summary>
		/// Nome della property a cui assegnare come valore la classe relativa al contesto corrente
		/// </summary>
		public string NomePropertyClasse
		{
			get { return m_datiContesto.NomePropertyClasse; }
		}

		public ContestoModelloDinamico(string token, ContestoModelloEnum tipoContesto, IClasseContestoModelloDinamico classe)
			:this( token, tipoContesto, classe, new ResourceScriptTemplateReader())
		{
		}

		/// <summary>
		/// Rappresenta il contesto in cui viene utilizzato un modello
		/// </summary>
		/// <param name="token">token attualmente in uso, può essere utilizzato dagli script del modello e dei campi</param>
		/// <param name="tipoContesto">nome del contesto in cui viene utilizzato il modello, può essere Istanza(IS), Anagrafica(AN) o Attività(AT)</param>
		/// <param name="classe">classe associatà al contesto,può essere un'istanza, un'anagrafica o un'attività</param>
		/// <param name="templateReader">Oggetto che permette di leggere il template da utilizzare per gli script</param>
		public ContestoModelloDinamico(string token, ContestoModelloEnum tipoContesto, IClasseContestoModelloDinamico classe, IScriptTemplateReader templateReader)
		{
			m_token = token;
			m_classe = classe;

			m_datiContesto = DatiDinamiciInfoDictionary.Items[tipoContesto];
			this._templateReader = templateReader;
		}

		internal string GetScriptTemplate()
		{
			return this._templateReader.ReadTemplate();

		}
	}
}
