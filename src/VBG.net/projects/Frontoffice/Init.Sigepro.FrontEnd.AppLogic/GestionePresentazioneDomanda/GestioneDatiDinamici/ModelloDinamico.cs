using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici
{
	public class ModelloDinamico
	{
		public static class TipiFirmaSigepro
		{
			public const int Nessuna = 0;
			public const int InteroDocumento = 1;
			public const int ABlocchi = 2;
		}

		public enum TipoFirmaEnum
		{
			Nessuna = 0,
			InteroDocumento = 1,
			ABlocchi = 2
		}

		public int IdModello { get; private set; }
		public string Descrizione { get; private set; }
		public int MaxMolteplicita { get; private set; }
		public bool Compilato { get; private set; }
		public bool Facoltativo { get; private set; }
		public TipoFirmaEnum TipoFirma { get; private set; }

		protected ModelloDinamico()
		{

		}

		internal static ModelloDinamico FromDyn2ModelliRow(PresentazioneIstanzaDbV2.Dyn2ModelliRow row)
		{
			return new ModelloDinamico
			{
				IdModello		= row.IdModello,
				Descrizione		= row.NomeScheda,
				MaxMolteplicita = row.MaxMolteplicita,
				TipoFirma		= DecodeTipoFirma( row.TipoFirma ),
				Compilato		= row.IsCompilatoNull() ? false : row.Compilato,
				Facoltativo		= row.IsFacoltativoNull() ? false : row.Facoltativo
			};
		}

		private static TipoFirmaEnum DecodeTipoFirma(int itipofirma)
		{
			switch (itipofirma)
			{
				case TipiFirmaSigepro.Nessuna:
					return TipoFirmaEnum.Nessuna;
				case TipiFirmaSigepro.InteroDocumento:
					return TipoFirmaEnum.InteroDocumento;
			}

			return TipoFirmaEnum.ABlocchi;
		}

		internal ModelloDinamicoOrdinato EstraiOrdine(IDatiDinamiciReadInterface readInterface)
		{
			return readInterface.OrdineAssoluto(this);
		}
	}
}
