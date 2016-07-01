// -----------------------------------------------------------------------
// <copyright file="FirmaGrafometricaService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneFirma.FirmaGrafometrica
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class FirmaGrafometricaService
    {
        IConfigurazione<ParametriFirmaDigitale> _parametriFirmaDigitale;
        ISalvataggioDomandaStrategy _persistenzaStrategy;
		IOggettiService _oggettiService;


		public FirmaGrafometricaService(IConfigurazione<ParametriFirmaDigitale> parametriFirmaDigitale, ISalvataggioDomandaStrategy persistenzaStrategy, IOggettiService oggettiService)
        {
            this._parametriFirmaDigitale = parametriFirmaDigitale;
            this._persistenzaStrategy = persistenzaStrategy;
			this._oggettiService = oggettiService;
        }


        public bool VerificaPresenzaSchedaConEstremiDocumento(int idDomanda)
        {
            if (!this._parametriFirmaDigitale.Parametri.IdSchedaDinamicaEstremiDocumento.HasValue)
            {
                throw new Exception("Non è stato configurato l'id della scheda dinamica contenente gli estremi del documento del firmatario. Verificare il parametro ID_SCHEDA_ESTREMI_DOCUMENTO della regola AREA_RISERVATA.");
            }

            var domanda = this._persistenzaStrategy.GetById(idDomanda);
            var idModello = this._parametriFirmaDigitale.Parametri.IdSchedaDinamicaEstremiDocumento.Value;

            var modello = domanda.ReadInterface.DatiDinamici.GetModelloById(idModello);

            return modello.Compilato;
        }

        public string GetNomeSchedaDinamicaEstremiDocumento(int idDomanda)
        {
            if (!this._parametriFirmaDigitale.Parametri.IdSchedaDinamicaEstremiDocumento.HasValue)
            {
                throw new Exception("Non è stato configurato l'id della scheda dinamica contenente gli estremi del documento del firmatario. Verificare il parametro ID_SCHEDA_ESTREMI_DOCUMENTO della regola AREA_RISERVATA.");
            }

            var domanda = this._persistenzaStrategy.GetById(idDomanda);
            var idModello = this._parametriFirmaDigitale.Parametri.IdSchedaDinamicaEstremiDocumento.Value;

            var modello = domanda.ReadInterface.DatiDinamici.GetModelloById(idModello);

            return modello.Descrizione;
        }

		public void AggiornaOggettoFirmato(int idDomanda, int codiceOggetto, BinaryFile file)
		{
			var domanda = this._persistenzaStrategy.GetById(idDomanda);
			
			this._oggettiService.AggiornaOggetto(codiceOggetto, file.FileContent);

			domanda.WriteInterface.Allegati.ModificaNomeFileEFlagFirmaDaCodiceOggetto(codiceOggetto, file.FileName, true);

			this._persistenzaStrategy.Salva(domanda);
		}
    }
}
