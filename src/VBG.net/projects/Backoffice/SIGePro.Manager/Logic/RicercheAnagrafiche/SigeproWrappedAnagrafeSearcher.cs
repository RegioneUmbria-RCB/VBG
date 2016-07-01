// -----------------------------------------------------------------------
// <copyright file="SigeproWrappedAnagrafeSearcher.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.SIGePro.Data;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class SigeproWrappedAnagrafeSearcher : IAnagrafeSearcher
	{
		AnagrafeSearcher _sigeproAnagrafeSearcher;
		AnagrafeSearcherBase _wrappedAnagrafeSearcher;

		public SigeproWrappedAnagrafeSearcher(AnagrafeSearcher sigeproAnagrafeSearcher, AnagrafeSearcherBase wrappedAnagrafeSearcher)
		{
			this._sigeproAnagrafeSearcher = sigeproAnagrafeSearcher;
			this._wrappedAnagrafeSearcher = wrappedAnagrafeSearcher;

			this.RestituisciAnagraficaSigeproSeNonTrovato = true;
		}

		public bool RestituisciAnagraficaSigeproSeNonTrovato { get; set; }

		#region IAnagrafeSearcher Members


		public void CleanUp()
		{
			this._wrappedAnagrafeSearcher.CleanUp();
			this._sigeproAnagrafeSearcher.CleanUp();
		}

		public Dictionary<string, string> Configuration
		{
			get { return this._wrappedAnagrafeSearcher.Configuration; }
		}

		public void Init()
		{
			this._wrappedAnagrafeSearcher.Init();
			this._sigeproAnagrafeSearcher.Init();
		}

		public void InitParams(string idComune, string idComuneAlias, PersonalLib2.Data.DataBase db)
		{
			this._wrappedAnagrafeSearcher.InitParams(idComune, idComuneAlias, db);
			this._sigeproAnagrafeSearcher.InitParams(idComune, idComuneAlias, db);
		}

		public Anagrafe ByCodiceFiscaleImp(TipoPersona tipoPersona, string codiceFiscale)
		{
			var anagrafe = this._wrappedAnagrafeSearcher.ByCodiceFiscaleImp(tipoPersona, codiceFiscale);

			return anagrafe == null ? this._sigeproAnagrafeSearcher.ByCodiceFiscaleImp(tipoPersona , codiceFiscale ) : anagrafe;
		}

		public Anagrafe ByCodiceFiscaleImp(string codiceFiscale)
		{
			var anagrafe = this._wrappedAnagrafeSearcher.ByCodiceFiscaleImp( codiceFiscale);

			return anagrafe == null ? this._sigeproAnagrafeSearcher.ByCodiceFiscaleImp( codiceFiscale) : anagrafe;
		}

		public List<Anagrafe> ByNomeCognomeImp(string nome, string cognome)
		{
			var res = this._wrappedAnagrafeSearcher.ByNomeCognomeImp(nome,cognome);

			return res == null ? this._sigeproAnagrafeSearcher.ByNomeCognomeImp(nome, cognome) : res;
		}

		public Anagrafe ByPartitaIvaImp(string partitaIva)
		{
			var res = this._wrappedAnagrafeSearcher.ByPartitaIvaImp(partitaIva);

			return res == null && RestituisciAnagraficaSigeproSeNonTrovato ? this._sigeproAnagrafeSearcher.ByPartitaIvaImp(partitaIva) : res;
		}

		#endregion
    }
}
