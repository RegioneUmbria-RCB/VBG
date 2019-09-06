// -----------------------------------------------------------------------
// <copyright file="ISalvataggioDomandaStrategy.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
	using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
	using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
	

	public interface ISalvataggioDomandaStrategy
	{
		DomandaOnline GetById(int idPresentazione);
		void Salva(DomandaOnline domanda);
		void Elimina(DomandaOnline domanda);


        byte[] GetAsXml(int idDomanda);
        void ImpostaIdIstanzaOrigine(int idDomanda, int idDomandaOrigine);
    }
}
