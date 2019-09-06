using System;
using Init.SIGePro.Data;
using System.Collections.Generic;
using System.Collections.Specialized;
using PersonalLib2.Data;
namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche
{
	public interface IAnagrafeSearcher
	{
		Anagrafe ByCodiceFiscaleImp(TipoPersona tipoPersona, string codiceFiscale);
		Anagrafe ByCodiceFiscaleImp(string codiceFiscale);
		List<Anagrafe> ByNomeCognomeImp(string nome, string cognome);
		Anagrafe ByPartitaIvaImp(string partitaIva);
		void CleanUp();
		Dictionary<string, string> Configuration { get; }
		void Init();
		void InitParams(string idComune, string idComuneAlias, DataBase db);
        IEnumerable<Anagrafe> GetVariazioni(DateTime from, DateTime to);
	}
}
