using System;
using System.Data;
using System.Web;
using System.Collections;
using System.Web.Services;
using System.Web.Services.Protocols;
using System.ComponentModel;
using Init.SIGePro.Data;
using Init.SIGePro.Authentication;
using Init.SIGePro.Exceptions.Token;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.DTO.Configurazione;
using Init.SIGePro.Verticalizzazioni;
using System.Configuration;
using Init.SIGePro.Manager.DTO.DatiDinamici;
using Init.SIGePro.Manager.Configuration;

namespace Sigepro.net.WebServices.WsAreaRiservata
{
	/// <summary>
	/// Summary description for DatiConfigurazioneAreaRiservata
	/// </summary>
	[WebService(Namespace = "http://init.sigepro.it")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[ToolboxItem(false)]
	public class DatiConfigurazioneAreaRiservataService : System.Web.Services.WebService
	{

		[WebMethod]
		public ConfigurazioneAreaRiservataDto LeggiConfigurazione(string token, string software)
		{
			AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

			if (authInfo == null)
				throw new InvalidTokenException(token);

			using( var db = authInfo.CreateDatabase() )
			{

				var parametriArCfg			= new FoArConfigurazioneMgr(db).LeggiDati(authInfo.IdComune, software);
				var parVertAreaRiservata	= new VerticalizzazioneAreaRiservata(authInfo.Alias, software);

				if (!parVertAreaRiservata.Attiva)
					throw new ConfigurationException("La verticalizzazione AREA_RISERVATA non è attiva");

				var rVal = new ConfigurazioneAreaRiservataDto
				{
					CodiceOggettoInvioConFirma = parametriArCfg.CodiceoggettoFirma,
					StatoInizialeIstanza = parametriArCfg.StatoInizialeIstanza,
					CodiceOggettoInvioConSottoscrizione = parametriArCfg.CodiceoggettoSottoscriz,
					CodiceOggettoWorkflow = parametriArCfg.CodiceoggettoWorkflow,
					CodiceOggettoMenuXml = parametriArCfg.CodiceoggettoMenuXml,
					IntestazioneDettaglioVisura = parametriArCfg.IntestazioneDettaglioVisura,
					MessaggioInvioFallito = parametriArCfg.MsgInvioFallito,
					MessaggioInvioPec = parametriArCfg.MsgInvioPec,
					MessaggioRegistrazioneCompletata = parametriArCfg.MsgRegistrazioneCompletata,
					NomeParametroUrlLogin = parametriArCfg.NomeParametroLoginUrl,
					ImpostaAutomaticamenteRichiedente = parVertAreaRiservata.ImpostaAutoRichiedente == "1",
					IdCampoDinamicoAttivitaAtecoPrevalente = String.IsNullOrEmpty(parVertAreaRiservata.AtecoPrimariaIdCampo) ? (int?)null : Int32.Parse(parVertAreaRiservata.AtecoPrimariaIdCampo),
					NomeConfigurazioneContenuti = parametriArCfg.NomeConfigurazioneContenuti,

					ImpostaAutomaticamenteTecnico = parVertAreaRiservata.ImpostaAutoTecnico == "1",
					VerificaHashFilesFirmati = parVertAreaRiservata.VerificaHashFilesFirmati == "1",
					UrlApplicazioneFacct = parVertAreaRiservata.UrlApplicazioneFacct == null ? String.Empty : parVertAreaRiservata.UrlApplicazioneFacct,
					ParametriRicercaScadenzario = new ParametriRicercaVisuraDto
					{
						CercaComeAzienda = String.IsNullOrEmpty(parVertAreaRiservata.ScadCercaAzienda) || parVertAreaRiservata.ScadCercaAzienda == "1",
						CercaComeRichiedente = String.IsNullOrEmpty(parVertAreaRiservata.ScadCercaRichiedente) || parVertAreaRiservata.ScadCercaRichiedente == "1",
						CercaComeTecnico = String.IsNullOrEmpty(parVertAreaRiservata.ScadCercaTecnico) || parVertAreaRiservata.ScadCercaTecnico == "1",
						CercaPartitaIva = String.IsNullOrEmpty(parVertAreaRiservata.ScadCercaPartitaiva) || parVertAreaRiservata.ScadCercaPartitaiva == "1"
					},
					ParametriRicercaVisuraTecnico = new ParametriRicercaVisuraDto
					{
						CercaComeAzienda = String.IsNullOrEmpty(parVertAreaRiservata.VisTCercaAzienda) || parVertAreaRiservata.VisTCercaAzienda == "1",
						CercaComeRichiedente = String.IsNullOrEmpty(parVertAreaRiservata.VisTCercaRichiedente) || parVertAreaRiservata.VisTCercaRichiedente == "1",
						CercaComeTecnico = String.IsNullOrEmpty(parVertAreaRiservata.VisTCercaTecnico) || parVertAreaRiservata.VisTCercaTecnico == "1",
						CercaPartitaIva = String.IsNullOrEmpty(parVertAreaRiservata.VisTCercaPartitaiva) || parVertAreaRiservata.VisTCercaPartitaiva == "1",
						CercaSoggettiCollegati = parVertAreaRiservata.VisTCercaSoggColl == "1"
					},
					ParametriRicercaVisuraNonTecnico = new ParametriRicercaVisuraDto
					{
						CercaComeAzienda = String.IsNullOrEmpty(parVertAreaRiservata.VisNtCercaAzienda) || parVertAreaRiservata.VisNtCercaAzienda == "1",
						CercaComeRichiedente = String.IsNullOrEmpty(parVertAreaRiservata.VisNtCercaRichiedente) || parVertAreaRiservata.VisNtCercaRichiedente == "1",
						CercaComeTecnico = String.IsNullOrEmpty(parVertAreaRiservata.VisNtCercaTecnico) || parVertAreaRiservata.VisNtCercaTecnico == "1",
						CercaPartitaIva = String.IsNullOrEmpty(parVertAreaRiservata.VisNtCercaPartitaiva) || parVertAreaRiservata.VisNtCercaPartitaiva == "1",
						CercaSoggettiCollegati = parVertAreaRiservata.VisNtCercaSoggColl == "1"
					},
					ParametriRicercaVisuraFiltroRichiedente = new ParametriRicercaVisuraDto
					{
						CercaComeAzienda = String.IsNullOrEmpty(parVertAreaRiservata.VisFilCercaAzienda) || parVertAreaRiservata.VisFilCercaAzienda == "1",
						CercaComeRichiedente = String.IsNullOrEmpty(parVertAreaRiservata.VisFilCercaRichiedente) || parVertAreaRiservata.VisFilCercaRichiedente == "1",
						CercaComeTecnico = String.IsNullOrEmpty(parVertAreaRiservata.VisFilCercaTecnico) || parVertAreaRiservata.VisFilCercaTecnico == "1",
						CercaPartitaIva = String.IsNullOrEmpty(parVertAreaRiservata.VisFilCercaPartitaiva) || parVertAreaRiservata.VisFilCercaPartitaiva == "1",
						CercaSoggettiCollegati = parVertAreaRiservata.VisFilCercaSoggColl == "1"
					},
					UrlPaginaIniziale = parVertAreaRiservata.UrlPaginaIniziale,
					ParametriVisuraMobile = new ParametriVisuraMobileDto
					{
						UrlServizioProfili = parVertAreaRiservata.UrlServiziMobile,
						AliasSportello = parVertAreaRiservata.AliasSportelloServiziMobile
					},
                    IdSchedaEstremiDocumento = parVertAreaRiservata.IdSchedaEstremiDocumento,
					IntestazioneCertificatoInvio = parVertAreaRiservata.IntestazioneCertificatoInvio,
					DimensioneMassimaAllegati = parVertAreaRiservata.DimensioneMassimaAllegati,
					WarningDimensioneMassimaAllegati = parVertAreaRiservata.WarningDimensioneMassimaAllegati,
                    DescrizioneDelegaATrasmettere = parVertAreaRiservata.DescrizioneDelegaATrasmettere
				};

				if( parametriArCfg.FkidSchedaEc.HasValue )
				{
					var schedaCittadinoEC = new Dyn2ModelliTMgr( db ).GetById( authInfo.IdComune , parametriArCfg.FkidSchedaEc.Value );

					if(schedaCittadinoEC != null)
					{

						rVal.SchedaDinamicaCittadiniExtracomunitari = new SchedaDinamicaCittadinoExtracomunitario
						{
							Codice = parametriArCfg.FkidSchedaEc.Value,
							RichiedeFirma = parametriArCfg.FlgSchedaEcRichiedeFirma.GetValueOrDefault(0) == 1,
							Descrizione = schedaCittadinoEC.Descrizione
						};
					}
				}


				// Imposto gli url per la ricerca delle anagrafiche
				var parVertWsAnagrafe = new VerticalizzazioneWsanagrafe(authInfo.Alias, software);

				var urlRicercheAnagraficheDefault = ParametriConfigurazione.Get.WsHostUrlAspNet + "/WebServices/WsSIGeProAnagrafe/WsAnagrafe2.asmx";
				var urlRicercheAnagrafichePf = parVertWsAnagrafe.Attiva && !String.IsNullOrEmpty(parVertWsAnagrafe.UrlRicercaPf) ? parVertWsAnagrafe.UrlRicercaPf : urlRicercheAnagraficheDefault;
				var urlRicercheAnagrafichePg = parVertWsAnagrafe.Attiva && !String.IsNullOrEmpty(parVertWsAnagrafe.UrlRicercaPg) ? parVertWsAnagrafe.UrlRicercaPg : urlRicercheAnagraficheDefault;

				rVal.UrlWsRicercheAnagrafiche = new ConfigurazioneAreaRiservataDto.UrlWebserviceRicercaAnagrafiche
				{
					PersoneFisiche = urlRicercheAnagrafichePf,
					PersoneGiuridiche = urlRicercheAnagrafichePg
				};

				
				
				return rVal;
			}
		}
	}
}
