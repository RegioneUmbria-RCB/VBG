using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneProcure.Sincronizzazione;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneProcure
{
	public interface IProcureWriteInterface
	{
		void AllegaFileProcura(string codicefiscaleProcurato, string codiceFiscaleProcuratore, int codiceOggetto, string nomeFile, bool isFirmatoDigitalmente);
		void EliminaFileProcura(string codicefiscaleProcurato, string codiceFiscaleProcuratore);
        void AllegaDocumentoIdentita(string codicefiscaleProcurato, string codiceFiscaleProcuratore, int codiceOggetto, string nomeFile, bool isFirmatoDigitalmente);
        void EliminaDocumentoIdentita(string codicefiscaleProcurato, string codiceFiscaleProcuratore);


        void EliminaProcureContenenti(string codiceFiscaleSoggetto);
		void Aggiungi(string codiceFiscaleProcurato, string codiceFiscaleProcuratore, int idAllegatoProcura = -1, int idAllegatoDocumentoIdentita = -1);

		void Sincronizza(SincronizzaProcureCommand sincronizzaProcureCommand);
	}
}
