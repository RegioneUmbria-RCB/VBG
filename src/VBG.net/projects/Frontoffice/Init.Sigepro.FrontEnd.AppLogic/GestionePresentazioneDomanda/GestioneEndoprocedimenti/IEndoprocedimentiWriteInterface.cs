using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneEndoprocedimenti.Sincronizzazione;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneEndoprocedimenti
{
	public interface IEndoprocedimentiWriteInterface
	{
		void Elimina(int idEndo);

		void AggiungiOAggiorna(int codice, string descrizione, bool principale, int? codiceNatura, string descrizionenatura, bool facoltativo, int binariodipendenze, bool permetteVerificaAcquisizione);

		void AggiungiESincronizza(IEnumerable<int> idNuoviEndoprocedimenti, LogicaSincronizzazioneEndo logicaSincronizzazioneEndo);
		
		void ImpostaNonPresente(int codiceEndo);
		
		void ImpostaPresente(int codiceEndo, int? idTipoTitolo, string descrizioneTipoTitolo, string numeroAtto, DateTime? dataAtto,string rilasciatoDa, string note);
		
		void AllegaAdEndoPresente(int codiceInventario, int codiceOggetto);
		
		void RimuoviAllegatoDaEndoPresente(int codiceInventario);
	}
}
