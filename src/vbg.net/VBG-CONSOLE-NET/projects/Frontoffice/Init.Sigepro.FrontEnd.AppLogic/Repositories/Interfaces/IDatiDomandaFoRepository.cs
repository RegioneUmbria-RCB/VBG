using System;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using System.Collections.Generic;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;

namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces
{
	public interface IDatiDomandaFoRepository
	{
		void Elimina(string aliasComune, int idDomanda);
		PresentazioneIstanzaDbV2 LeggiDataSetDomanda(string aliasComune, int idDomanda);
		List<FoDomande> LeggiDomandeInSospeso(string aliasComune, string software, int codiceAnagrafe);
		EsitoSalvataggioDomandaOnline Salva(DomandaOnline domanda);

		bool DomandaPresentata(string aliasComune, int idDomanda);
		FoDomande LeggiDatiDomanda(string aliasComune, int idDomanda);
		int GeneraProssimoIdDomanda(string aliasComune);

        byte[] ConvertToXml(DomandaOnline domanda);
        void ImpostaIdIstanzaOrigine(int idDomanda, int idDomandaOrigine);
    }
}
