using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici
{
	public class ValoreDatoDinamico : IValoreDatoDinamicoRiepilogo
    {
		public int IdCampo { get; private set; }
		public string NomeCampo { get; private set; }
		public int IndiceMolteplicita { get; private set; }
		public int IndiceScheda { get; private set; }
		public string Valore { get; private set; }
		public string ValoreDecodificato { get; private set; }

		protected ValoreDatoDinamico()
		{
		}

		internal static ValoreDatoDinamico Vuoto(int idCampo,  int indiceScheda, int indiceMolteplicita)
		{
			return new ValoreDatoDinamico
			{
				IdCampo = idCampo,
				IndiceMolteplicita = indiceMolteplicita,
				IndiceScheda = indiceScheda,
				Valore = String.Empty,
				ValoreDecodificato = String.Empty,
				NomeCampo = String.Empty
			};
		}

		internal static ValoreDatoDinamico FromDyn2DatiRow(PresentazioneIstanzaDbV2.Dyn2DatiRow row)
		{
			return new ValoreDatoDinamico
			{
				IdCampo = row.IdCampo,
				IndiceMolteplicita = row.IndiceMolteplicita,
				IndiceScheda = row.IndiceScheda,
				Valore = row.Valore,
				ValoreDecodificato = row.ValoreDecodificato,
				NomeCampo = row.NomeCampo
			};
		}
	}
}
