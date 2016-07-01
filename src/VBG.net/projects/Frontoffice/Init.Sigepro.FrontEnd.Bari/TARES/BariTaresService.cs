using System.Collections.Generic;
using System.ServiceModel;
using Init.Sigepro.FrontEnd.Bari.TARES.DTOs;
using Init.Sigepro.FrontEnd.Bari.TARES.ServicesProxies;
using System;
using System.Linq;
using Init.Sigepro.FrontEnd.Bari.Core.CafServices;

namespace Init.Sigepro.FrontEnd.Bari.TARES
{
	public class BariTaresService
	{
		private enum TipoRichiestaUtenzaEnum
		{
			CodiceFiscale,
			CodiceUtenza
		}

		IUtenzeServiceProxy _taresServiceProxy;
		ICafServiceProxy _sigeproServiceProxy;

		internal BariTaresService(IUtenzeServiceProxy taresServiceProxy, ICafServiceProxy sigeproServiceProxy)
		{
			this._sigeproServiceProxy = sigeproServiceProxy;
			this._taresServiceProxy = taresServiceProxy;
		}

		public bool OperatoreAppartieneACaf(string codiceFiscaleOperatore)
		{
			return this._sigeproServiceProxy.UtenteAppartieneACaf(codiceFiscaleOperatore);
		}

		public string GetCodiceFiscaleCafDaCodiceFiscaleOperatore(string codiceFiscaleOperatore)
		{
			return this._sigeproServiceProxy.GetCodiceFiscaleCafDaCodiceFiscaleOperatore(codiceFiscaleOperatore);
		}



		public IEnumerable<DatiContribuenteDto> TrovaUtenze(string codiceFiscaleOperatore, string codFiscaleOCodUtente)
		{
			var riferimentiCaf = this._sigeproServiceProxy.GetRiferimentiCafDaCodiceFiscaleoperatore(codiceFiscaleOperatore);

			if (GetTipoRichiesta(codFiscaleOCodUtente) == TipoRichiestaUtenzaEnum.CodiceFiscale)
				return this._taresServiceProxy.GetUtenzeByCodiceFiscale(riferimentiCaf.CodiceFiscale, riferimentiCaf.IndirizzoPec, codFiscaleOCodUtente);

			return this._taresServiceProxy.GetUtenzeByIdentificativoUtenza(riferimentiCaf.CodiceFiscale, riferimentiCaf.IndirizzoPec, Convert.ToInt32(codFiscaleOCodUtente));
		}

		private TipoRichiestaUtenzaEnum GetTipoRichiesta(string codFiscaleOCodUtente)
		{
			Int32 codiceUtente = 0;

			if (Int32.TryParse(codFiscaleOCodUtente, out codiceUtente))
				return TipoRichiestaUtenzaEnum.CodiceUtenza;

			return TipoRichiestaUtenzaEnum.CodiceFiscale;
		}

		public DatiContribuenteDto GetDettagliUtenza(string codiceFiscaleOperatore, int identificativoContribuente/*, string identificativoUtenza*/)
		{
			var riferimentiCaf = this._sigeproServiceProxy.GetRiferimentiCafDaCodiceFiscaleoperatore(codiceFiscaleOperatore);

			var listaUtenze = this._taresServiceProxy.GetUtenzeByIdentificativoUtenza(riferimentiCaf.CodiceFiscale, riferimentiCaf.IndirizzoPec, identificativoContribuente);

			//var dettagliUtenza = listaUtenze.Where(x => x.ElencoUtenzeAttive.Where(y => y.IdentificativoUtenza == identificativoUtenza).FirstOrDefault() != null).FirstOrDefault();

			//dettagliUtenza.ElencoUtenzeAttive = dettagliUtenza.ElencoUtenzeAttive.Where(x => x.IdentificativoUtenza == identificativoUtenza).ToArray();

			return listaUtenze.FirstOrDefault();
		}
	}
}
