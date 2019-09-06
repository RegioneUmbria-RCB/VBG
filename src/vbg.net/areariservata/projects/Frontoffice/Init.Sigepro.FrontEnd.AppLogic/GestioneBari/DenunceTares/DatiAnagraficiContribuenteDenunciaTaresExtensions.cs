// -----------------------------------------------------------------------
// <copyright file="DatiContribuenteTasiExtensions.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBari.DenunceTares
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
	using Init.Sigepro.FrontEnd.Bari.DenunceTARES.DTOs;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public static class DatiAnagraficiContribuenteDenunciaTaresExtensions
	{
		public static AnagraficaDomanda ToAnagraficaDomanda(this DatiAnagraficiContribuenteDenunciaTares daticontribuente, IComuniService comuniService)
		{
			var indirizzoResidenza = daticontribuente.IndirizzoResidenza;
			var indirizzoCorrispondenza = daticontribuente.IndirizzoCorrispondenza;

			var anagrafica = daticontribuente.TipoPersona == Bari.Core.SharedDTOs.TipoPersonaEnum.Fisica ?
												AnagraficaDomanda.DaCodiceFiscaleTipoPersona(TipoPersonaEnum.Fisica, daticontribuente.CodiceFiscale) :
												AnagraficaDomanda.DaCodiceFiscaleTipoPersona(TipoPersonaEnum.Giuridica, daticontribuente.CodiceFiscale);

			anagrafica.Nome = daticontribuente.Nome;
			anagrafica.Nominativo = daticontribuente.Cognome;

			anagrafica.IndirizzoResidenza = IndirizzoAnagraficaDomandaFromIndirizzoDenunciaTares(indirizzoResidenza, comuniService);
			anagrafica.IndirizzoCorrispondenza = IndirizzoAnagraficaDomandaFromIndirizzoDenunciaTares(indirizzoCorrispondenza, comuniService);

			anagrafica.Codicefiscale = daticontribuente.CodiceFiscale;
			anagrafica.PartitaIva = daticontribuente.PartitaIva;


			if (anagrafica.TipoPersona == TipoPersonaEnum.Fisica)
			{
				// Comune nascita
				var comuneNascita = comuniService.FindComuneDaNomeComune(daticontribuente.ComuneNascita);
				var codiceComuneNascita = comuneNascita == null ? string.Empty : comuneNascita.CodiceComune;
				var provinciaNascita = comuneNascita == null ? string.Empty : comuneNascita.SiglaProvincia;
				var dataNascita = daticontribuente.DataNascita;

				anagrafica.DatiNascita = new DatiNascitaAnagrafica(codiceComuneNascita, provinciaNascita, dataNascita);
				anagrafica.Sesso = daticontribuente.Sesso.HasValue && daticontribuente.Sesso.Value == Bari.Core.SharedDTOs.SessoEnum.Maschio ? SessoEnum.Maschio : SessoEnum.Femmina;

				anagrafica.Contatti = new DatiContattoAnagrafica(daticontribuente.Telefono, String.Empty, daticontribuente.Fax, String.Empty, daticontribuente.Pec);
			}

			if (anagrafica.TipoPersona == TipoPersonaEnum.Giuridica && !String.IsNullOrEmpty(daticontribuente.ProvinciaREA))
			{
				anagrafica.DatiIscrizioneRea = new DatiIscrizioneReaAnagrafica(daticontribuente.ProvinciaREA, daticontribuente.NumeroREA.ToString(), null);
			}
			

			return anagrafica;
		}

		public static AnagraficaDomanda ToAnagraficaDomanda(this RappresentanteDenunciaTares rapp, IComuniService comuniService)
		{
			var anagrafe = AnagraficaDomanda.DaCodiceFiscaleTipoPersona(TipoPersonaEnum.Fisica, rapp.CodiceFiscale);

			anagrafe.Nome = rapp.Nome;
			anagrafe.Codicefiscale = rapp.Cognome;

			var comuneNascita = comuniService.FindComuneDaNomeComune(rapp.ComuneNascita);
			var codiceComuneNascita = comuneNascita == null ? string.Empty : comuneNascita.CodiceComune;
			var provinciaNascita = comuneNascita == null ? string.Empty : comuneNascita.SiglaProvincia;
			var dataNascita = rapp.DataNascita;

			anagrafe.DatiNascita = new DatiNascitaAnagrafica(codiceComuneNascita, provinciaNascita, dataNascita);
			anagrafe.Sesso = rapp.Sesso.HasValue && rapp.Sesso.Value == Bari.Core.SharedDTOs.SessoEnum.Maschio ? SessoEnum.Maschio : SessoEnum.Femmina;

			anagrafe.Contatti = new DatiContattoAnagrafica(rapp.Telefono, String.Empty, rapp.Fax, String.Empty, rapp.Pec);

			return anagrafe;
		}

		private static IndirizzoAnagraficaDomanda IndirizzoAnagraficaDomandaFromIndirizzoDenunciaTares(IndirizzoDenunciaTares i, IComuniService comuniService)
		{
            if (i == null)
            {
                return null;
            }

			var comuneResidenza = comuniService.FindComuneDaNomeComune(i.CodiceIstatComune);

			var codiceComune = comuneResidenza != null ? comuneResidenza.CodiceComune : String.Empty;
			var nomeComune = comuneResidenza != null ? comuneResidenza.Comune : String.Empty;
			var provincia = comuneResidenza != null ? comuneResidenza.SiglaProvincia : String.Empty;

			var indirizzoConVia = i.ToString();

			return new IndirizzoAnagraficaDomanda(indirizzoConVia, nomeComune, i.Cap, provincia, codiceComune);
			
		}
	}
}
