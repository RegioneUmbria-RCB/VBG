using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Init.Sigepro.FrontEnd.AppLogic.Wrappers;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAllegati
{
	public class AllegatoDellaDomanda
	{
        private static class Constants
        {
            public const string MD5SumKey = "MD5_SUM";
        }

		public int CodiceOggetto { get; private set; }
		public string NomeFile { get; private set; }
		public bool FirmatoDigitalmente { get; private set; }
		public string Md5 { get; private set; }

		public AllegatoDellaDomanda(int codiceOggetto, string nomeFile, bool firmatoDigitalmente, string md5)
		{
			this.CodiceOggetto = codiceOggetto;
			this.NomeFile = nomeFile;
			this.FirmatoDigitalmente = firmatoDigitalmente;
			this.Md5 = md5;
		}

		internal AllegatoDellaDomanda(PresentazioneIstanzaDbV2.AllegatiRow row)
		{
			this.CodiceOggetto = row.CodiceOggetto;
			this.NomeFile = row.NomeFile;
			this.FirmatoDigitalmente = row.FirmatoDigitalmente;
			this.Md5 = row.Md5;
		}

		public DocumentiType ToDocumentiType(string descrizione)
		{
			return new DocumentiType
			{
				id = String.Empty,
				documento = descrizione,
				tipoDocumento = "Altro",
				allegati = new AllegatiType
				{
					id = this.CodiceOggetto.ToString(),
					allegato = this.NomeFile
				}
			};
		}

        public DocumentiIstanza ToDocumentiIstanza(string descrizione, IDateTimeServiceWrapper dateTimeServiceWrapper)
        {
            var doc = new DocumentiIstanza
            {
                DOCUMENTO = descrizione,
                DATA = dateTimeServiceWrapper.NewDate(),
                CODICEOGGETTO = this.CodiceOggetto.ToString(),
                Oggetto = new DocumentiIstanzaOggetti
                {
                    NOMEFILE = this.NomeFile,
					Md5 = this.Md5
                }
            };

            return doc;
        }
    }
}
