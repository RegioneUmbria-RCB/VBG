// -----------------------------------------------------------------------
// <copyright file="FirmaCidPinService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Cid
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.Bari.CID;
	using System.Security.Authentication;
using Init.Sigepro.FrontEnd.Bari.FirmaCidPin;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
	using System.IO;
using log4net;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class FirmaCidPinService
    {
		ILog _log = LogManager.GetLogger(typeof(FirmaCidPinService));
        IConfigurazione<ParametriFirmaDigitale> _parametriFirmaDigitale;
        ISalvataggioDomandaStrategy _persistenzaStrategy;
        IBariCidService _cidService;
		IFirmaCidPinService _firmaCidPinService;
		IOggettiService _oggettiService;

		public FirmaCidPinService(IConfigurazione<ParametriFirmaDigitale> parametriFirmaDigitale, ISalvataggioDomandaStrategy persistenzaStrategy, IBariCidService cidService, IFirmaCidPinService firmaCidPinService, IOggettiService oggettiService)
        {
            this._parametriFirmaDigitale = parametriFirmaDigitale;
            this._persistenzaStrategy = persistenzaStrategy;
            this._cidService = cidService;
			this._firmaCidPinService = firmaCidPinService;
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

        private bool AutenticaCidPin(string cid, string pin)
        {
            var result = this._cidService.ValidaCidPin(cid, pin);

            return result != null;
        }

		public void Firma(int idDomanda, string cid, string pin, int codiceOggetto)
		{
			if (!AutenticaCidPin(cid, pin))
			{
				throw new AuthenticationException("Il CID o il PIN immesso non è valido. Verificare i dati inseriti");
			}

			try
			{
				var domanda = this._persistenzaStrategy.GetById(idDomanda);
				var oggetto = this._oggettiService.GetById(codiceOggetto);
				var nomeFile = Path.GetFileNameWithoutExtension(oggetto.FileName);
				var ext = Path.GetExtension(oggetto.FileName);

				this._log.DebugFormat("inizio della firma cid/pin per il file {0} della domanda {1}", codiceOggetto, idDomanda);

				var request = new CidPinSignRequest(cid, pin, oggetto.FileName, oggetto.FileContent);

				var risultato = this._firmaCidPinService.Firma(request);

				this._log.DebugFormat("Aggiornamento dell'oggetto {0} della domanda {1} con il file firmato digitalmente", codiceOggetto, idDomanda);

				this._oggettiService.AggiornaOggetto(codiceOggetto, risultato);

				oggetto = new BinaryFile(nomeFile + "_firmato" + ext, oggetto.MimeType, risultato);

				this._log.DebugFormat("Aggiunta del file firmato con cid/pin alla domanda {0}, codiceoggetto={1}, nomeFile={2}", idDomanda, codiceOggetto, oggetto.FileName);

				domanda.WriteInterface.Allegati.ModificaNomeFileEFlagFirmaDaCodiceOggetto(codiceOggetto, oggetto.FileName, true);

				this._log.Debug("Firma con cid/pin completata con successo");
			}
			catch (Exception ex)
			{
				this._log.ErrorFormat("Errore durante la firma del documento con codiceoggetto {0} e cid {1} per la domanda {2}: {3}", codiceOggetto, cid, idDomanda, ex.ToString());

				throw;
			}
		}
    }
}
