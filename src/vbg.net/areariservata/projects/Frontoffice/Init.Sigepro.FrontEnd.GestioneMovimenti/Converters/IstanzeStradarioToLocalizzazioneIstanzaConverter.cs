using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.GestioneLocalizzazioni.StringaFormattazioneIndirizzi;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using AutoMapper;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.Converters
{
	public class IstanzeStradarioToLocalizzazioneIstanzaConverter : ITypeConverter<IstanzeStradario, LocalizzazioneIstanza>
	{
		public LocalizzazioneIstanza Convert(ResolutionContext context)
		{
			var x = (IstanzeStradario)context.SourceValue;

			return new LocalizzazioneIstanza
			{
				Civico = x.CIVICO,
				Indirizzo = x.Stradario.PREFISSO + " " + x.Stradario.DESCRIZIONE,
				Coordinate = String.IsNullOrEmpty(x.Longitudine) ? null : new LocalizzazioneIstanza.Coordinata(x.Longitudine, x.Latitudine),
				Esponente = x.ESPONENTE,
				EsponenteInterno = x.ESPONENTEINTERNO,
				Interno = x.INTERNO,
				Km = x.Km,
				Note = x.NOTE,
				Mappali = null,
				Piano = x.Piano,
				Scala = x.SCALA,
				TipoLocalizzazione = x.TipoLocalizzazione == null ? String.Empty : x.TipoLocalizzazione.Descrizione,
				Uuid = x.Uuid
			};
		}
	}
}
