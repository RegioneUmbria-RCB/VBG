using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.StcService;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneProcure
{
	public class ProcuraDomandaOnline
	{
		public class SoggettoProcura
		{
			public string CodiceFiscale { get; protected set; }
			public string Nominativo { get; protected set; }

			public SoggettoProcura(string codiceFiscale, string nominativo)
			{
				this.CodiceFiscale = codiceFiscale;
				this.Nominativo = nominativo;
			}

			public static SoggettoProcura FromAnagrafeRow(PresentazioneIstanzaDbV2.ANAGRAFERow anagrafeRow)
			{
				return new SoggettoProcura(anagrafeRow.CODICEFISCALE, anagrafeRow.NOMINATIVO + " " + anagrafeRow.NOME);
			}
		}

		public class AllegatoProcura
		{
			public int		CodiceOggetto{get;private set;}
			public string	CodiceFiscaleProcuratore { get; private set; }
			public string	NomeFile{get;private set;}
			public bool		FirmatoDigitalmente { get; private set; }

			public AllegatoProcura (string codiceFiscaleProcuratore, int codiceOggetto, string nomefile, bool firmatoDigitalmente)
			{
				this.CodiceOggetto				= codiceOggetto;
				this.NomeFile					= nomefile;
				this.CodiceFiscaleProcuratore	= codiceFiscaleProcuratore;
				this.FirmatoDigitalmente		= firmatoDigitalmente;
			}

			public DocumentiType ToDocumentiType()
			{
				return new DocumentiType
				{
					id = String.Empty,
					documento = "Procura." + this.CodiceFiscaleProcuratore,
					tipoDocumento = TipoDocumentoType.Procura,
					tipoDocumentoSpecified = true,
					allegati = new AllegatiType
					{
						id = this.CodiceOggetto.ToString(),
						allegato = this.NomeFile
					}
				};
			}
		}

		public SoggettoProcura Procurato { get; private set; }
		public SoggettoProcura Procuratore { get; private set; }
		public AllegatoProcura Allegato { get; private set; }

		public ProcuraDomandaOnline(SoggettoProcura procurato, SoggettoProcura procuratore, AllegatoProcura allegato)
		{
			this.Procurato = procurato;
			this.Procuratore = procuratore;
			this.Allegato = allegato;
		}

	}
}
