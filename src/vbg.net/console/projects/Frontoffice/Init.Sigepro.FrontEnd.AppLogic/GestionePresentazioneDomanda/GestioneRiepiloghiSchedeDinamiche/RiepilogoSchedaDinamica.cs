using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.StcService;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneRiepiloghiSchedeDinamiche
{
	public class RiepilogoSchedaDinamica
	{
		public class AllegatoUtente
		{
			public int CodiceOggetto { get; private set; }
			public string NomeFile { get; private set; }
			public string Md5 { get; private set; }
			public string Note { get; private set; }
			public bool FirmatoDigitalmente { get; private set; }

			RiepilogoSchedaDinamica _parent;

			public AllegatoUtente(RiepilogoSchedaDinamica parent, int codiceOggetto, string nomeFile, string gmd5, string note, bool firmatoDigitalmente)
			{
				this.CodiceOggetto = codiceOggetto;
				this.NomeFile = nomeFile;
				this.Md5 = Md5;
				this.Note = note;
				this._parent = parent;
				this.FirmatoDigitalmente = firmatoDigitalmente;
			}


			public static AllegatoUtente FromAllegatiRow(RiepilogoSchedaDinamica parent, PresentazioneIstanzaDbV2.AllegatiRow row)
			{
				return new AllegatoUtente(parent, row.CodiceOggetto, row.NomeFile, row.Md5, row.Note, row.FirmatoDigitalmente);
			}

			public DocumentiType ToDocumentiType(IAliasResolver aliasResolver)
			{
				return new DocumentiType
				{
					id = String.Empty,
					documento = this._parent.Descrizione,
					tipoDocumento = "Altro",
					allegati = new AllegatiType
					{
                        id = $"{aliasResolver.AliasComune}@{this.CodiceOggetto.ToString()}",
                        allegato = this.NomeFile
					}
				};
			}
		}

		public int IdModello { get; private set; }
		public int IndiceMolteplicita { get; private set; }
		public string Descrizione { get; private set; }
		public AllegatoUtente AllegatoDellUtente { get; private set; }
		public string HashConfronto { get; private set; }


		public static RiepilogoSchedaDinamica FromRiepilogoDomandaRow(PresentazioneIstanzaDbV2.RiepilogoDatiDinamiciRow riepilogoRow, PresentazioneIstanzaDbV2.AllegatiRow allegatiRow)
		{
			return new RiepilogoSchedaDinamica(riepilogoRow, allegatiRow);
		}

		protected RiepilogoSchedaDinamica(PresentazioneIstanzaDbV2.RiepilogoDatiDinamiciRow riepilogoRow, PresentazioneIstanzaDbV2.AllegatiRow allegatiRow)
		{
			// TODO: Complete member initialization
			this.IdModello = riepilogoRow.IdModello;
			this.IndiceMolteplicita = riepilogoRow.IndiceMolteplicita;
			this.Descrizione = riepilogoRow.Descrizione;
			this.HashConfronto = riepilogoRow.HashConfronto;

			if (!riepilogoRow.IsIdAllegatoNull())
			{
				this.AllegatoDellUtente = AllegatoUtente.FromAllegatiRow( this, allegatiRow);
			}
		}
	}
}
