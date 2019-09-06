namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Imu
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
	using Init.Sigepro.FrontEnd.Bari.IMU.DTOs;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public static class DatiContribuenteImuDtoExtensions
	{
		public static AnagraficaDomanda ToAnagraficaDomanda(this DatiContribuenteImuDto daticontribuente, IComuniService comuniService)
		{
			var indirizzo = daticontribuente.Residenza;

			var anagrafica = daticontribuente.TipoPersona == DatiContribuenteImuDto.TipoPersonaEnum.Fisica ?
												AnagraficaDomanda.DaCodiceFiscaleTipoPersona(TipoPersonaEnum.Fisica, daticontribuente.CodiceFiscale) :
												AnagraficaDomanda.DaCodiceFiscaleTipoPersona(TipoPersonaEnum.Giuridica, daticontribuente.CodiceFiscale);

			anagrafica.Nome = daticontribuente.Nome;
			anagrafica.Nominativo = daticontribuente.Cognome;

			// La Imu deve essere pagata esclusivamente da un cittadino o da un'azienda residente nel comune di BAri
			var comuneResidenza = comuniService.FindComuneDaNomeComune("BARI");

			var codiceComuneResidenza = comuneResidenza != null ? comuneResidenza.CodiceComune : String.Empty;
			var nomeComuneResidenza = comuneResidenza != null ? comuneResidenza.Comune : String.Empty;
			var provinciaResidenza = comuneResidenza != null ? comuneResidenza.SiglaProvincia : String.Empty;

			var indirizzoConVia = indirizzo.ToString();

			anagrafica.IndirizzoResidenza = new IndirizzoAnagraficaDomanda(indirizzoConVia, nomeComuneResidenza, indirizzo.Cap, provinciaResidenza, codiceComuneResidenza);


			if (daticontribuente.TipoPersona == DatiContribuenteImuDto.TipoPersonaEnum.Fisica)
			{
				// Comune nascita
				var comuneNascita = comuniService.FindComuneDaNomeComune(daticontribuente.ComuneNascita);
				var codiceComuneNascita = comuneNascita == null ? string.Empty : comuneNascita.CodiceComune;
				var provinciaNascita = comuneNascita == null ? string.Empty : comuneNascita.SiglaProvincia;
				var dataNascita = daticontribuente.DataNascita;

				anagrafica.DatiNascita = new DatiNascitaAnagrafica(codiceComuneNascita, provinciaNascita, dataNascita);
			}

			return anagrafica;
		}
	}
}
