using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici.Sincronizzazione;
using Init.SIGePro.DatiDinamici.VisibilitaCampi;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici
{
	public interface IDatiDinamiciWriteInterface
	{
		void AggiornaOCrea(int idCampo, int indiceScheda, int indiceMolteplicita, string valore, string valoreDecodificato, string nomeCampo);
		void EliminaValoreDaIdcampoIndiceMolteplicita(int idCampo, int indiceScheda, int indiceMolteplicita);
		void AggiungiDatoDinamico(int idCampo, int indiceScheda, int indiceMolteplicita, string valore, string valoreDecodificato, string nomeCampo);
		void ModificaValoreCampo(int idCampo, int indiceScheda, int indiceMolteplicita, string valore, string valoreDecodificato);
		void ModificaStatoCompilazioneModello(int idModello, int maxMolteplicita, bool compilato);
		void SincronizzaModelliDinamici(SincronizzaModelliDinamiciCommand command);

		void SalvaCampiNonVisibili(int idModello, IEnumerable<IdValoreCampo> campiNonVisibili);
	}
}
