using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters
{
	internal class AnagraficheHelper
	{
		ComuniStcHelper _comuniHelper = new ComuniStcHelper();

		internal RuoloType AdattaRuolo(AnagraficaDomanda anagrafe)
		{
			if (anagrafe.TipoSoggetto == null)
				return null;

			return new RuoloType
			{
				idRuolo = anagrafe.TipoSoggetto.Id.ToString(),
				ruolo = anagrafe.TipoSoggetto.Descrizione
			};
		}

		internal PersonaFisicaType AdattaPersonaFisica(AnagraficaDomanda anagrafe)
		{
			if (anagrafe.TipoPersona != TipoPersonaEnum.Fisica)
				throw new ArgumentException("AdattaPersonaFisica: L'anagrafica con codice " + anagrafe.Codicefiscale + " non è una persona fisica");


			var persona = new PersonaFisicaType
			{
				codiceFiscale = anagrafe.Codicefiscale,
				cognome = anagrafe.Nominativo,
				comuneNascita = _comuniHelper.AdattaComuneDaCodiceBelfiore(anagrafe.DatiNascita.CodiceComune),
				dataNascita = anagrafe.DatiNascita.Data.Value,
				dataNascitaSpecified = true,
				nome = anagrafe.Nome,
				sesso = anagrafe.Sesso == SessoEnum.Maschio ? "M" : "F",
				titolo = anagrafe.IdTitolo.ToString(),
				email = anagrafe.Contatti.Email,
				pec = anagrafe.Contatti.Pec,
				cittadinanza = new CittadinanzaType
				{
					id = anagrafe.IdCittadinanza.ToString()
				},
				telefono = anagrafe.Contatti.Telefono,
				telefonoCellulare = anagrafe.Contatti.TelefonoCellulare
			};

            if (anagrafe.IndirizzoResidenza != null) 
            {
                persona.residenza = new LocalizzazioneType
                {
                    cap = anagrafe.IndirizzoResidenza.Cap,
                    indirizzo = anagrafe.IndirizzoResidenza.Via,
                    localita = anagrafe.IndirizzoResidenza.Citta,
                    provincia = anagrafe.IndirizzoResidenza.SiglaProvincia
                };

                if (!String.IsNullOrEmpty(anagrafe.IndirizzoResidenza.CodiceComune))
                {
                    persona.residenza.comune = _comuniHelper.AdattaComuneDaCodiceBelfiore(anagrafe.IndirizzoResidenza.CodiceComune);
                }
            }


			if (anagrafe.DatiIscrizioneAlboProfessionale != null && anagrafe.DatiIscrizioneAlboProfessionale.IdAlbo.HasValue)
			{
				persona.datiIscrizioneAlbo = new DatiIscrizioneAlboType
				{
					numeroIscrizione = anagrafe.DatiIscrizioneAlboProfessionale.Numero,
					siglaProvincia = anagrafe.DatiIscrizioneAlboProfessionale.SiglaProvincia,
					tipoOrdineProfessionisti = new CodiceDescrizioneType
					{
						codice = anagrafe.DatiIscrizioneAlboProfessionale.IdAlbo.Value.ToString(),
						descrizione = anagrafe.DatiIscrizioneAlboProfessionale.Descrizione
					}
				};
			}

			if (anagrafe.IndirizzoCorrispondenza != null && !String.IsNullOrEmpty(anagrafe.IndirizzoCorrispondenza.Via))
			{
				persona.corrispondenza = new LocalizzazioneType
				{
					indirizzo = anagrafe.IndirizzoCorrispondenza.Via,
					cap = anagrafe.IndirizzoCorrispondenza.Cap,
					localita = anagrafe.IndirizzoCorrispondenza.Citta,
					provincia = anagrafe.IndirizzoCorrispondenza.SiglaProvincia,
					comune = _comuniHelper.AdattaComuneDaCodiceBelfiore(anagrafe.IndirizzoCorrispondenza.CodiceComune)
				};
			}

			return persona;
		}


		internal PersonaGiuridicaType AdattaPersonaGiuridica(AnagraficaDomanda azienda, AnagraficaDomanda legaleRappresentante = null)
		{
			if (azienda.TipoPersona != TipoPersonaEnum.Giuridica)
				throw new ArgumentException("AdattaPersonaGiuridica: L'anagrafica con codice " + azienda.Codicefiscale + " non è una persona giuridica");

			var pg = new PersonaGiuridicaType
			{
                ragioneSociale = azienda.Nominativo,
                naturaGiuridica = azienda.IdFormagiuridica.HasValue ? azienda.IdFormagiuridica.ToString() : String.Empty,

				indirizzoCorrispondenza = new LocalizzazioneType
				{
					cap = azienda.IndirizzoCorrispondenza.Cap,
					comune = _comuniHelper.AdattaComuneDaCodiceBelfiore(azienda.IndirizzoCorrispondenza.CodiceComune),
					indirizzo = azienda.IndirizzoCorrispondenza.Via,
					localita = azienda.IndirizzoCorrispondenza.Citta,
					provincia = azienda.IndirizzoCorrispondenza.SiglaProvincia
				},
				
				partitaIva = azienda.PartitaIva,
				codiceFiscale = azienda.Codicefiscale,
				
				sedeLegale = new LocalizzazioneType
				{
					cap = azienda.IndirizzoResidenza.Cap,
					comune = _comuniHelper.AdattaComuneDaCodiceBelfiore(azienda.IndirizzoResidenza.CodiceComune),
					indirizzo = azienda.IndirizzoResidenza.Via,
					localita = azienda.IndirizzoResidenza.Citta,
					provincia = azienda.IndirizzoResidenza.SiglaProvincia
				},
				telefono = azienda.Contatti.Telefono,
                telefonoCellulare = azienda.Contatti.TelefonoCellulare,
                fax = azienda.Contatti.Fax,
				pec = azienda.Contatti.Pec,
				email = azienda.Contatti.Email,
                datiInps = azienda.DatiIscrizioneInps.ToDatiInpsType(),
                datiInail = azienda.DatiIscrizioneInail.ToDatiInailType()
			};

			if (azienda.DatiIscrizioneCciaa != null)
			{
				if (azienda.DatiIscrizioneCciaa.TuttiIDatiSonoPopolati())
				{
					pg.iscrizioneCCIAA = new IscrizioneRegistroType
					{
						comune = _comuniHelper.AdattaComuneDaCodiceBelfiore(azienda.DatiIscrizioneCciaa.CodiceComune),
						numero = azienda.DatiIscrizioneCciaa.Numero,
						data = azienda.DatiIscrizioneCciaa.Data.Value
					};
				}
			}

			if (azienda.DatiIscrizioneRea.TuttiICampiSonoPopolati)
			{
				pg.iscrizioneREA = new RegistroREAType
				{
					data = azienda.DatiIscrizioneRea.Data.Value,
					numero = azienda.DatiIscrizioneRea.Numero,
					siglaProvincia = azienda.DatiIscrizioneRea.SiglaProvincia
				};
			}

			if (legaleRappresentante != null)
				pg.legaleRappresentante = AdattaPersonaFisica(legaleRappresentante);

			return pg;
		}


		/// <summary>
		/// Adatta un'anagrafica dell'area riservata in un'anagrafica della domanda STC
		/// </summary>
		/// <param name="anagrafe"></param>
		/// <returns></returns>
		internal AnagrafeType AdattaAnagrafica(AnagraficaDomanda anagrafe)
		{
			if (anagrafe == null)
				return null;

			if (anagrafe.TipoPersona == TipoPersonaEnum.Fisica)
			{
				return new AnagrafeType { Item = AdattaPersonaFisica(anagrafe) };
			}
			else
			{
				return new AnagrafeType { Item = AdattaPersonaGiuridica(anagrafe, null) };
			}
		}
	}
}
