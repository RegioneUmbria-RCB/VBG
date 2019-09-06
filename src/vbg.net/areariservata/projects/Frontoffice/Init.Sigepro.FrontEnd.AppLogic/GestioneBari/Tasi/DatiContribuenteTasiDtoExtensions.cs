// -----------------------------------------------------------------------
// <copyright file="DatiContribuenteTasiDtoExtensions.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Tasi
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.Bari.TASI.DTOs;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public static class DatiContribuenteTasiDtoExtensions
	{
		public static AnagraficaDomanda ToAnagraficaDomanda(this DatiContribuenteTasiDto daticontribuente, IComuniService comuniService)
		{
			var indirizzo = daticontribuente.Residenza;

			var anagrafica = daticontribuente.TipoPersona == DatiContribuenteTasiDto.TipoPersonaEnum.Fisica ?
												AnagraficaDomanda.DaCodiceFiscaleTipoPersona(TipoPersonaEnum.Fisica, daticontribuente.CodiceFiscale) :
												AnagraficaDomanda.DaCodiceFiscaleTipoPersona(TipoPersonaEnum.Giuridica, daticontribuente.CodiceFiscale);

			anagrafica.Nome = daticontribuente.Nome;
			anagrafica.Nominativo = daticontribuente.Cognome;

			// La tasi deve essere pagata esclusivamente da un cittadino o da un'azienda residente nel comune di BAri
			var comuneResidenza = comuniService.FindComuneDaNomeComune("BARI");

			var codiceComuneResidenza = comuneResidenza != null ? comuneResidenza.CodiceComune : String.Empty;
			var nomeComuneResidenza = comuneResidenza != null ? comuneResidenza.Comune : String.Empty;
			var provinciaResidenza = comuneResidenza != null ? comuneResidenza.SiglaProvincia : String.Empty;

			var indirizzoConVia = indirizzo.ToString();

			anagrafica.IndirizzoResidenza = new IndirizzoAnagraficaDomanda(indirizzoConVia, nomeComuneResidenza, indirizzo.Cap, provinciaResidenza, codiceComuneResidenza);


			if (daticontribuente.TipoPersona == DatiContribuenteTasiDto.TipoPersonaEnum.Fisica)
			{
				// Comune nascita
				var comuneNascita = comuniService.FindComuneDaNomeComune(daticontribuente.ComuneNascita);
				var codiceComuneNascita = comuneNascita == null ? string.Empty : comuneNascita.CodiceComune;
				var provinciaNascita = comuneNascita == null ? string.Empty : comuneNascita.SiglaProvincia;
				var dataNascita = daticontribuente.DataNascita;

				anagrafica.DatiNascita = new DatiNascitaAnagrafica(codiceComuneNascita, provinciaNascita, dataNascita);

				if (daticontribuente.Sesso.GetValueOrDefault() == DatiContribuenteTasiDto.SessoEnum.Femmina )
				{
					anagrafica.Sesso = SessoEnum.Femmina ;
				}
			}

			return anagrafica;
		}
	}
}
