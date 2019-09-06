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
using Init.SIGePro.Manager.DTO.Pagamenti;
using Init.SIGePro.Manager.Verticalizzazioni;
using Init.SIGePro.Manager.Logic.GestioneOggetti.DimensioniAllegatiLiberi;
using System.Linq;

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
                var configurazioneGeneraleTT = new ConfigurazioneMgr(db).GetById(authInfo.IdComune, "TT");
				var parametriArCfg			 = new FoArConfigurazioneMgr(db).LeggiDati(authInfo.IdComune, software);
				var parVertAreaRiservata	 = new VerticalizzazioneAreaRiservata(authInfo.Alias, software);
                var parVertSportelloCittadino = new VerticalizzazioneLivornoServiziCittadino(authInfo.Alias, software);
                var triesteAccessoAtti = new VerticalizzazioneTriesteAccessoAtti(authInfo.Alias, software);


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
                    DescrizioneDelegaATrasmettere = parVertAreaRiservata.DescrizioneDelegaATrasmettere,
                    UsernameUtenteAnonimo = parVertAreaRiservata.UsernameUtenteAnonimo,
                    PasswordUtenteAnonimo = parVertAreaRiservata.PasswordUtenteAnonimo,
                    CiviciNumerici = parVertAreaRiservata.CiviciNumerici == "1",
                    EsponentiNumerici = parVertAreaRiservata.EsponentiNumerici == "1",
                    NascondiNoteMovimento = parVertAreaRiservata.NascondiNoteMovimento,
                    IntegrazioniDocumentali = new ConfigurazioneAreaRiservataDto.ParametriIntegrazioniDocumentali
                    {
                        BloccaUploadAllegati = parVertAreaRiservata.IntegrazioniNoUploadAllegati,
                        BloccaUploadRiepiloghiSchedeDinamiche = parVertAreaRiservata.IntegrazioniNoUploadRiepiloghiSchedeDinamiche,
	                    IntegrazioniNoInserimentoNote = parVertAreaRiservata.IntegrazioniNoInserimentoNote,
    	                IntegrazioniNoNomiAllegati = parVertAreaRiservata.IntegrazioniNoNomiAllegati
                    },
                    ConfigurazioneLoghi = new ParametriConfigurazioneLoghi
                    {
                        UrlLogo = parVertAreaRiservata.UrlLogo,
                        CodiceOggettoLogoComune = configurazioneGeneraleTT.CodiceOggetto4,
                        CodiceOggettoLogoRegione = configurazioneGeneraleTT.CodiceOggetto2 
                    },
                    TecnicoInSoggettiCollegati = parVertAreaRiservata.TecnicoInSoggettiCollegati,
                    ConfigurazioneRiepilogoDomanda = new ConfigurazioneRiepilogoDomandaDto
                    {
                        FlagIncludiSchede = parVertAreaRiservata.FlagSchedeDinamicheFirmateInRiepilogo
                    },
                    UrlAuthenticationOverride = parVertAreaRiservata.UrlAuthenticationOverride,
                    AidaSmart = new ConfigurazioneAidaSmartDto
                    {
                        UrlNuovaDomanda = parVertAreaRiservata.AidaSmartUrlNuovaDomanda,
                        UrlIstanzeInSospeso = parVertAreaRiservata.AidaSmartUrlIstanzeInSospeso,
                        CrossLoginUrl = parVertAreaRiservata.AidaSmartCrossLoginUrl
                    },
                    DettaglioVisura = new ConfigurazioneDettaglioVisuraDto
                    {
                         NascondiStatoIstanza = parVertAreaRiservata.VisuraNascondiStatoIstanza,
                         NascondiResponsabili = parVertAreaRiservata.VisuraNascondiResponsabili
                    },
                    ArpaCalabria = new VerticalizzazioneArpaCalabria(authInfo.Alias, software).Attiva

                };

                if (parVertSportelloCittadino.Attiva)
                {
                    rVal.ServiziCittadino.UrlWsModulisticaDrupal = parVertSportelloCittadino.UrlWsModulisticaDrupal;
                }

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
                // Verticalizzazione MIP
                var parVertPagamentiMIP = new VerticalizzazionePagamentiMipRpcsuap(authInfo.Alias, software);

                if (parVertPagamentiMIP.Attiva)
                {
                    rVal.ConfigurazionePagamentiMIP = new ConfigurazionePagamentiMIP
                    {
                        EmailPortale = parVertPagamentiMIP.EmailPortale,
                        IdentificativoComponente = parVertPagamentiMIP.IdentificativoComponente,
                        IdServizio = parVertPagamentiMIP.IdServizio,
                        IndirizzoProxy = parVertPagamentiMIP.IndirizzoProxy,
                        PasswordChiamate = parVertPagamentiMIP.PasswordChiamate,
                        PortaleID = parVertPagamentiMIP.PortaleID,
                        PortaProxy = parVertPagamentiMIP.PortaProxy,
                        UrlServerPagamento = parVertPagamentiMIP.UrlServerPagamento,
                        WindowMinutes = parVertPagamentiMIP.WindowMinutes,
                        CodiceTipoPagamento = parVertPagamentiMIP.CodiceTipoPagamento,
                        IntestazioneRicevuta = parVertPagamentiMIP.IntestazioneRicevuta,
                        CodiceEnte = parVertPagamentiMIP.CodiceEnte,
                        CodiceUfficio = parVertPagamentiMIP.CodiceUfficio,
                        CodiceUtente = parVertPagamentiMIP.CodiceUtente,
                        TipologiaServizio = parVertPagamentiMIP.TipologiaServizio,
                        TipoUfficio = parVertPagamentiMIP.TipoUfficio,
                        ChiaveIV = parVertPagamentiMIP.ChiaveIV,
                        UrlNotifica = parVertPagamentiMIP.UrlNotifica
                    };
                }

                var parVertPagamentiEntraNext = new VerticalizzazionePagamentiEntraNext(authInfo.Alias, software);

                if (parVertPagamentiEntraNext.Attiva)
                {
                    rVal.PagamentiEntraNext = new ConfigurazionePagamentiEntraNext
                    {
                        CodiceFiscaleEnte = parVertPagamentiEntraNext.CodiceFiscaleEnte,
                        Identificativo = parVertPagamentiEntraNext.Identificativo,
                        IdentificativoConnettore = parVertPagamentiEntraNext.IdentificativoConnettore,
                        PasswordMd5 = parVertPagamentiEntraNext.PasswordMd5,
                        UrlWs = parVertPagamentiEntraNext.UrlWs,
                        Username = parVertPagamentiEntraNext.Username,
                        Versione = parVertPagamentiEntraNext.Versione,
                        CodiceTipoPagamento = parVertPagamentiEntraNext.CodiceTipoPagamento
                    };
                }

                // VerticalizzazioneCART
                var parVertCart = new VerticalizzazioneCart(authInfo.Alias, software);

                if (parVertCart.Attiva)
                {
                    rVal.ConfigurazioneCart = new ParametriCartDto
                    {
                        UrlAccettatore = parVertCart.UrlAccettatore
                    };
                }


                // Verticalizzazione FVG_SOL
                var parVertFvgSol = new VerticalizzazioneFvgSol(authInfo.Alias, software);

                if (parVertFvgSol.Attiva)
                {
                    rVal.ParametriFvgSol = new ParametriFvgSolDto
                    {
                        WebServiceUrl = parVertFvgSol.WebServiceUrl,
                        WebServicePassword = parVertFvgSol.WebServicePassword,
                        WebServiceUsername = parVertFvgSol.WebServiceUsername
                    };
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

                var parVertSitLdp = new VerticalizzazioneSitLdp(authInfo.Alias, software);

                if (parVertSitLdp.Attiva)
                {
                    rVal.SitLDP.UrlGenerazionePdfDomanda = parVertSitLdp.UrlGenerazionePdfDomanda;
                    rVal.SitLDP.UrlPresentazioneDomandaLdp = parVertSitLdp.UrlPresentazioneDomanda;
                    rVal.SitLDP.UrlServiziDomandaLdp = parVertSitLdp.UrlServizioDomande;
                    rVal.SitLDP.Username = parVertSitLdp.Username;
                    rVal.SitLDP.Password = parVertSitLdp.Password;
                }

                // Verticalizzazione AREARISERVATA_REDIRECT
                var vertRedirect = new VerticalizzazioneAreaRiservataRedirect(authInfo.Alias, software);

                if (vertRedirect.Attiva)
                {
                    rVal.AreaRiservataRedirect.VerticalizzazioneAttiva = true;
                    rVal.AreaRiservataRedirect.NomeFile = vertRedirect.NomeFile;
                    rVal.AreaRiservataRedirect.UrlRedirect = vertRedirect.UrlRedirect;
                }

                // Configurazione delle dimensioni massime degli allegati liberi
                rVal.FormatiAllegatiLiberi = new DimensioniAllegatiLiberiRepository(db, authInfo.IdComune)
                                                .GetList(software)
                                                .Select( x => new FormatoAllegatoLiberoDto
                                                {
                                                    Id = x.Id,
                                                    DimensioneMaxPagina = x.DimensioneMaxPagina,
                                                    Formato = x.Formato
                                                })
                                                .ToArray();

                // Verticalizzazione "Scrivania enti terzi"
                var scrivaniaEntiTerzi = new VerticalizzaizoneScrivaniaEntiTerzi(authInfo.Alias, software);

                if (scrivaniaEntiTerzi.Attiva)
                {
                    rVal.ScrivaniaEntiTerzi = new ConfigurazioneAreaRiservataDto.ParametriScrivaniaEntiTerzi
                    {
                        SoftwareAttivazione = scrivaniaEntiTerzi.SoftwareAttivazione ?? ""
                    };
                }

                if (triesteAccessoAtti.Attiva)
                {
                    rVal.TriesteAccessoAtti = new PrametriTriesteAccessoAtti
                    {
                        UrlTrasferimentoControllo = triesteAccessoAtti.UrlTrasferimentoControllo,
                        UrlWebService = triesteAccessoAtti.UrlWebService
                    };
                }

                return rVal;
			}
		}
	}
}
