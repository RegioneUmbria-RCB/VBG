// -----------------------------------------------------------------------
// <copyright file="ErrorMessage.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Sit.Errors
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using Init.SIGePro.Sit.Data;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class ErrorMessage
	{
		MessageCode _code;

		public ErrorMessage(MessageCode code)
		{
			this._code = code;
		}


		public override string ToString()
		{
			switch (this._code)
			{
				// Lettura dati

				case MessageCode.ElencoVincoli:
					return "Non è possibile estrarre dalle viste informazioni sui vincoli";
				case MessageCode.ElencoZone:
					return "Non è possibile estrarre dalle viste informazioni sulle zone";
				case MessageCode.ElencoSottoZone:
					return "Non è possibile estrarre dalle viste informazioni sulle sotto zone";
				case MessageCode.ElencoDatiUrbanistici:
					return "Non è possibile estrarre dalle viste informazioni su dati urbanistici";
				case MessageCode.DettaglioFabbricato:
					return "Non è possibile estrarre dalle viste informazioni sul fabbricato";
				case MessageCode.DettaglioUI:
					return "Non è possibile estrarre dalle viste informazioni sull' unità immobiliare";
				case MessageCode.ElencoCodiciVia:
					return "Non è necessario estrarre informazioni sul codice via";
				case MessageCode.ElencoCodiciCivici:
					return "Non è necessario estrarre informazioni sul codice civico";
				case MessageCode.ElencoCAP:
					return "Non è necessario estrarre informazioni sul CAP";
				case MessageCode.ElencoKm:
					return "Non è possibile estrarre dalle viste informazioni sul km";
				case MessageCode.ElencoFrazioni:
					return "Non è necessario estrarre informazioni sulle frazioni";
				case MessageCode.ElencoCircoscrizioni:
					return "Non è necessario estrarre informazioni sulle circoscrizioni";
				case MessageCode.ElencoColori:
					return "Non è possibile estrarre dalle viste informazioni sul colore";
				case MessageCode.ElencoScale:
					return "Non è possibile estrarre dalle viste informazioni sulla scala";
				case MessageCode.ElencoInterni:
					return "Non è possibile estrarre dalle viste informazioni sull'interno";
				case MessageCode.ElencoEsponentiInterno:
					return "Non è possibile estrarre dalle viste informazioni sull'esponente dell'interno";
				case MessageCode.ElencoFabbricati:
					return "Non è possibile estrarre dalle viste informazioni sul fabbricato";
				case MessageCode.ElencoSezioni:
					return "Non è possibile estrarre dalle viste informazioni sulla sezione";
				case MessageCode.ElencoUI:
					return "Non è possibile estrarre dalle viste informazioni sulle unità immobiliari";
				case MessageCode.ElencoSub:
					return "Non è possibile estrarre dalle viste informazioni sui sub";
				case MessageCode.ElencoEsponenti:
					return "Non è possibile estrarre informazioni sugli esponenti";
				case MessageCode.ElencoPiani:
					return "Non è possibile estrarre dalle viste informazioni sui piani";
				case MessageCode.ElencoQuartieri:
					return "Non è possibile estrarre dalle viste informazioni sui quartieri";
				case MessageCode.ElencoCivici:
					return "Non è possibile estrarre informazioni sui civici";
				// Validazione

				case MessageCode.KmValidazione:
					return "Non è possibile tramite le viste validare il km";
				case MessageCode.ColoreValidazione:
					return "Non è possibile tramite le viste validare il colore";
				case MessageCode.FrazioneValidazione:
					return "Non è possibile tramite le viste validare la frazione";
				case MessageCode.CircoscrizioneValidazione:
					return "Non è possibile tramite le viste validare la circoscrizione";
				case MessageCode.ScalaValidazione:
					return "Non è possibile tramite le viste validare la scala";
				case MessageCode.InternoValidazione:
					return "Non è possibile tramite le viste validare l'interno";
				case MessageCode.EsponenteValidazione:
					return "Non è possibile tramite le viste validare l'esponente";
				case MessageCode.EsponenteInternoValidazione:
					return "Non è possibile tramite le viste validare l'esponente dell'interno";
				case MessageCode.FabbricatoValidazione:
					return "Non è possibile tramite le viste validare il fabbricato";
				case MessageCode.TipoCatastoValidazione:
					return "Non è possibile tramite le viste validare il catasto";
				case MessageCode.CodiceViaValidazione:
					return "Non è necessario validare il codice via";
				case MessageCode.SezioneValidazione:
					return "Non è possibile tramite le viste validare la sezione";
				case MessageCode.CAPValidazione:
					return "Non è possibile tramite le viste validare il CAP";
				case MessageCode.UIValidazione:
					return "Non è possibile tramite le viste validare l'unità immobiliare";
				case MessageCode.SubValidazione:
					return "Non è possibile tramite le viste validare il sub";
				case MessageCode.PianoValidazione:
					return "Non è possibile validare il piano";
				case MessageCode.QuartiereValidazione:
					return "Non è possibile validare il quartiere";
			}

			return "Si è verificato un errore: ";
		}

		public RetSit ToRetSit(bool success, string extendedErrorMessage = "")
		{
			var msg = this.ToString();

			if (!String.IsNullOrEmpty(extendedErrorMessage))
				msg += extendedErrorMessage;

			return new RetSit
			{
				ReturnValue = success,
				MessageCode = this._code.ToString("d"),
				Message = msg
			};
		}
	}
}
