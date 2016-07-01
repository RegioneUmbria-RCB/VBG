using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche
{
	public partial class AnagraficaDomanda
	{
		internal static AnagraficaDomanda FacSimileRichiedente()
		{
			return new AnagraficaDomanda
			{
				Id = -1,
				TipoPersona = TipoPersonaEnum.Fisica ,
				Nominativo = " ",
				Nome = " ",
				//IdFormagiuridica = anagrafeRow.IsFORMAGIURIDICANull() ? (int?)null : (int)anagrafeRow.FORMAGIURIDICA;
				Codicefiscale = " ",
				PartitaIva = " ",
				Sesso = SessoEnum.Maschio,
				DatiNascita = new DatiNascitaAnagrafica(String.Empty,String.Empty,(DateTime?)null),
				IdTitolo = -1,
				DataCostituzione = (DateTime?)null ,
				IdCittadinanza = (int?)null ,
				IndirizzoResidenza = new IndirizzoAnagraficaDomanda(String.Empty,String.Empty,String.Empty,String.Empty,String.Empty),
				IndirizzoCorrispondenza = new IndirizzoAnagraficaDomanda(String.Empty,String.Empty,String.Empty,String.Empty,String.Empty),
				Contatti = new DatiContattoAnagrafica(String.Empty,String.Empty,String.Empty,String.Empty,String.Empty),
				Note = String.Empty,
				DatiIscrizioneRegTrib = null,
				DatiIscrizioneCciaa = null,
				DatiIscrizioneRea = null ,
				DatiIscrizioneAlboProfessionale = null,
				IdAnagraficaCollegata = (int?)null,
				TipoSoggetto = new TipoSoggettoDomanda
				{
					Id = -1,
					Descrizione = String.Empty,
					DescrizioneEstesa = String.Empty,
					Ruolo = RuoloTipoSoggettoDomandaEnum.Richiedente,
					RichiedeAnagraficaCollegata = false 
				},
				IsCittadinoExtracomunitario = false
			};
		}
	}
}
