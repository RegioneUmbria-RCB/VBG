using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.StcService;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri
{
	public class AttestazioneDiPagamento
	{
		public static AttestazioneDiPagamento NonPresente = new AttestazioneDiPagamento(false, String.Empty, (int?)null, false);

		bool	_presente		= false;
		string	_nomeFile		= String.Empty;
		int?	_codiceOggetto	= (int?)null;
		bool	_firmatoDigitalmente = false;

		public bool		Presente		{ get { return this._presente; } }
		public string	NomeFile		{ get { return this._nomeFile; } }
		public int?		CodiceOggetto	{ get { return this._codiceOggetto; } }
		public bool FirmatoDigitalmente { get { return this._firmatoDigitalmente; } }

		public AttestazioneDiPagamento(bool presente, string nomeFile, int? codiceOggetto, bool firmatoDigitalmente)
		{
			this._presente				= presente;
			this._nomeFile				= nomeFile;
			this._codiceOggetto			= codiceOggetto;
			this._firmatoDigitalmente	= firmatoDigitalmente;
		}

		public DocumentiType ToDocumentiType()
		{
			return new DocumentiType
			{
				id = String.Empty,
				documento = "Copia della ricevuta attestante l'avvenuto pagamento degli oneri ",
				tipoDocumento = TipoDocumentoType.Altro,
				tipoDocumentoSpecified = true,
				allegati = new AllegatiType
				{
					id = this.CodiceOggetto.ToString(),
					allegato = this.NomeFile
				}
			};
		}
	}
}
