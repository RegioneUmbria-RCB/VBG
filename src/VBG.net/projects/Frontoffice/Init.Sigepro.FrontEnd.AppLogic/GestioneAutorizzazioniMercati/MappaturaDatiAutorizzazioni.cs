using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAutorizzazioni;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAutorizzazioniMercati
{
	public class MappaturaDatiAutorizzazioni
	{
		public class CampoDinamicoMappato
		{
			public readonly int IdCampo;
			public readonly string Valore;
			public readonly string ValoreDecodificato;

			public CampoDinamicoMappato(int idCampo, string valore, string valoreDecodificato)
			{
				this.IdCampo = idCampo;
				this.Valore = valore;
				this.ValoreDecodificato = valoreDecodificato;
			}
			public CampoDinamicoMappato(int idCampo, string valore)
			{
				this.IdCampo = idCampo;
				this.Valore = valore;
				this.ValoreDecodificato = valore;
			}
		}

		public int IdCampoCodiceAutorizzazione;
		public int IdCampoNumeroAutorizzazione;
		public int IdCampoDataAutorizzazione;
		public int IdCampoCodiceEnteRilascio;
		public int IdCampoDescrizioneEnteRilascio;
		public int IdCampoNumeroPresenzeCalcolate;
		public int IdCampoNumeroPresenzeDichiarate;

		public IEnumerable<CampoDinamicoMappato> Mappa(EstremiAutorizzazioneMercato autorizzazione)
		{
			if (IdCampoCodiceAutorizzazione != -1)
				yield return new CampoDinamicoMappato(IdCampoCodiceAutorizzazione, autorizzazione.Id.ToString());

			if (IdCampoNumeroAutorizzazione != -1)
				yield return new CampoDinamicoMappato(IdCampoNumeroAutorizzazione, autorizzazione.Numero);

			if (IdCampoDataAutorizzazione != -1)
			{
				if (!String.IsNullOrEmpty(autorizzazione.Data))
				{
					var data = DateTime.ParseExact(autorizzazione.Data, "dd/MM/yyyy", null);

					yield return new CampoDinamicoMappato(IdCampoDataAutorizzazione, data.ToString("yyyyMMdd"), autorizzazione.Data);
				}
				else
				{
					yield return new CampoDinamicoMappato(IdCampoDataAutorizzazione, autorizzazione.Data);
				}
			}

			if (IdCampoCodiceEnteRilascio != -1 && !String.IsNullOrEmpty(autorizzazione.EnteRilascio.Codice))
				yield return new CampoDinamicoMappato(IdCampoCodiceEnteRilascio, autorizzazione.EnteRilascio.Codice, autorizzazione.EnteRilascio.Descrizione);

			if (IdCampoDescrizioneEnteRilascio != -1)
				yield return new CampoDinamicoMappato(IdCampoDescrizioneEnteRilascio, autorizzazione.EnteRilascio.Descrizione);

			if (IdCampoNumeroPresenzeCalcolate != -1)
				yield return new CampoDinamicoMappato(IdCampoNumeroPresenzeCalcolate, autorizzazione.NumeroPresenzeCalcolato);

			if (IdCampoNumeroPresenzeDichiarate != -1)
				yield return new CampoDinamicoMappato(IdCampoNumeroPresenzeDichiarate, autorizzazione.NumeroPresenzeDichiarato);
		}
	}
}
