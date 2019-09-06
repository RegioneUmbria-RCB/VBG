using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit
{
	public enum ModificaStradario
	{
		Indirizzo,
		Catasto
	}

	public enum MessageCode
	{
		SezioneValidazione = 58000,
		FoglioValidazione = 58001,
		ParticellaValidazione = 58002,
		SubValidazione = 58003,
		UIValidazione = 58004,
		CivicoValidazione = 58005,
		KmValidazione = 58006,
		EsponenteValidazione = 58007,
		ColoreValidazione = 58008,
		ScalaValidazione = 58009,
		InternoValidazione = 58010,
		EsponenteInternoValidazione = 58011,
		FabbricatoValidazione = 58012,
		ElencoCivici = 58013,
		ElencoEsponenti = 58014,
		ElencoScale = 58015,
		ElencoInterni = 58016,
		ElencoEsponentiInterno = 58017,
		ElencoFabbricati = 58018,
		ElencoParticelle = 58019,
		GetCodCivico = 58020,
		GetCodVia = 58021,
		ElencoFogli = 58022,
		DettaglioFabbricato = 58023,
		DettaglioUI = 58024,
		ElencoSub = 58025,
		ElencoUI = 58026,
		ElencoKm = 58027,
		ElencoColori = 58028,
		ElencoSezioni = 58029,
		CodiceViaValidazione = 58030,
		ElencoCodiciVia = 58031,
		ElencoCodiciCivici = 58032,
		CodiceViaValidazioneNumero = 58033,
		CivicoValidazioneNumero = 58034,
		EsponenteValidazioneNumero = 58035,
		ScalaValidazioneNumero = 58036,
		InternoValidazioneNumero = 58037,
		EsponenteInternoValidazioneNumero = 58038,
		KmValidazioneNumero = 58039,
		FabbricatoValidazioneNumero = 58040,
		SezioneValidazioneNumero = 58041,
		FoglioValidazioneNumero = 58042,
		ParticellaValidazioneNumero = 58043,
		SubValidazioneNumero = 58044,
		UIValidazioneNumero = 58045,
		ColoreValidazioneNumero = 58046,
		ElencoDescrizioneVia = 58047,
		ElencoCAP = 58048,
		ElencoCircoscrizioni = 58049,
		ElencoFrazioni = 58050,
		CAPValidazione = 58051,
		CircoscrizioneValidazione = 58052,
		FrazioneValidazione = 58053,
		CAPValidazioneNumero = 58054,
		CircoscrizioneValidazioneNumero = 58055,
		FrazioneValidazioneNumero = 58056,
		ElencoVincoli = 58057,
		ElencoZone = 58058,
		ElencoSottoZone = 58059,
		ElencoDatiUrbanistici = 58060,
		TipoCatastoValidazione = 58061,
		PianoValidazione = 58062,
		ElencoPiani = 58063,
		QuartiereValidazione = 58064,
		ElencoQuartieri = 58065
	}

	public enum TipoElenco { Vincoli, Zone }

	public enum TipoQuery { Elenco, Validazione }
}
