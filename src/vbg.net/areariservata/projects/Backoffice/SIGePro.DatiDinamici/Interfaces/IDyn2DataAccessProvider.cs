using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.Interfaces.Istanze;
using Init.SIGePro.DatiDinamici.Interfaces.Anagrafe;
using Init.SIGePro.DatiDinamici.Interfaces.Attivita;
using Init.SIGePro.DatiDinamici.Interfaces.WebControls;
using Init.SIGePro.DatiDinamici.GestioneLocalizzazioni;

namespace Init.SIGePro.DatiDinamici.Interfaces
{
	/// <summary>
	/// Interfaccia del provider dei dati
	/// </summary>
	public interface IDyn2DataAccessProvider
	{
		IDyn2ProprietaCampiManager GetProprietaCampiManager();
		IDyn2ModelliManager GetModelliManager();
		IDyn2DettagliModelloManager GetDettagliModelloManager();
		IDyn2TestoModelloManager GetTestoModelloManager();
		IDyn2CampiManager GetCampiManager();
		
		IDyn2ScriptCampiManager GetScriptCampiManager();
		IDyn2ScriptModelloManager GetScriptModelliManager();

		// Manager delle istanze
		IIstanzeDyn2DatiManager GetIstanzeDyn2DatiManager();
		IIstanzeDyn2DatiStoricoManager GetIstanzeDyn2DatiStoricoManager();
		IIstanzeManager GetIstanzeManager();

		// Manager delle anagrafiche
		IAnagrafeDyn2DatiManager GetAnagrafeDyn2DatiManager();
		IAnagrafeDyn2DatiStoricoManager GetAnagrafeDyn2DatiStoricoManager();
		IAnagrafeManager GetAnagrafeManager();

		// Manager delle attivita
		IIAttivitaDyn2DatiManager GetAttivitaDyn2DatiManager();
		IIAttivitaDyn2DatiStoricoManager GetAttivitaDyn2DatiStoricoManager();
		IIAttivitaManager GetAttivitaManager();

		// Query
		IDyn2QueryDatiDinamiciManager GetDyn2QueryDatiDinamiciManager();

		// Ricerca Localizzazioni
		IQueryLocalizzazioni GetQueryLocalizzazioni();

		string GetToken();
	}
}
