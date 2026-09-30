using Init.SIGePro.Manager.Authentication.SoftwareAttivi;
using Init.SIGePro.Manager.DTO.Configurazione;
using Init.SIGePro.Manager.DTO.DatiDinamici;
using Init.SIGePro.Manager.DTO.Pagamenti;
using Init.SIGePro.Manager.Logic.GestioneConfigurazione.Verticalizzazioni;
using Init.SIGePro.Manager.Logic.GestioneIntegrazioneLDP.Verticalizzazioni;
using Init.SIGePro.Manager.Logic.GestioneOggetti.DimensioniAllegatiLiberi;
using PersonalLib2.Data;
using SIGePro.Manager.Verticalizzazioni;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Configuration;
using System.Linq;

namespace Init.SIGePro.Manager.Logic.GestioneConfigurazione
{
    public class ConfigurazioneAreaRiservataService
    {
        private readonly DataBase _db;
        private readonly string _idComune;
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;

        public ConfigurazioneAreaRiservataService(DataBase db, string idComune, IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            this._db = db;
            this._idComune = idComune;
            this._verticalizzazioniFactory = verticalizzazioniFactory;
        }

        public ConfigurazioneAreaRiservataDto GetConfigurazioneAreaRiservata(string alias, string software)
        {
            var softwareAttivi = new SoftwareAttiviService(this._db, this._idComune).GetSoftwareAttivi();

            if (!softwareAttivi.SoftwareAttiviFrontoffice.Contains(software))
            {
                throw new ConfigurationErrorsException($"Il modulo {software} non è attivo nell'area riservata");
            }

            var configurazioneGeneraleTT = new ConfigurazioneMgr(this._db).GetById(this._idComune, "TT");
            var configurazioneGeneraleSoftware = new ConfigurazioneMgr(this._db).GetById(this._idComune, software);
            var parametriArCfg = new FoArConfigurazioneMgr(this._db).LeggiDati(this._idComune, software);

            var parVertAreaRiservata = this._verticalizzazioniFactory.Create<VerticalizzazioneAreaRiservata>(alias, software);
            var parVertSportelloCittadino = this._verticalizzazioniFactory.Create<VerticalizzazioneLivornoServiziCittadino>(alias, software);
            var triesteAccessoAtti = this._verticalizzazioniFactory.Create<VerticalizzazioneTriesteAccessoAtti>(alias, software);
            var verticalizzazioneRabbitMQ = this._verticalizzazioniFactory.Create<VerticalizzazioneRabbitMQ>(alias);
            var verticalizzazioneArSsu = this._verticalizzazioniFactory.Create<VerticalizzazioneAreaRiservataSsu>(alias, software);

            if (!parVertAreaRiservata.Attiva)
                throw new ConfigurationErrorsException("La verticalizzazione AREA_RISERVATA non è attiva");

            var rVal = new ConfigurazioneAreaRiservataDto
            {
                CodiceOggettoInvioConFirma = parametriArCfg.CodiceoggettoFirma,
                StatoInizialeIstanza = parametriArCfg.StatoInizialeIstanza,
                // CodiceOggettoInvioConSottoscrizione = parametriArCfg.CodiceoggettoSottoscriz,
                CodiceOggettoWorkflow = parametriArCfg.CodiceoggettoWorkflow,
                CodiceOggettoMenuXml = parametriArCfg.CodiceoggettoMenuXml,
                CodiceOggettoRiepilogoSchede = parametriArCfg.CodiceOggettoRiepilogoSchede,
                IntestazioneDettaglioVisura = parametriArCfg.IntestazioneDettaglioVisura,
                MessaggioInvioFallito = parametriArCfg.MsgInvioFallito,
                MessaggioInvioPec = parametriArCfg.MsgInvioPec,
                MessaggioRegistrazioneCompletata = parametriArCfg.MsgRegistrazioneCompletata,
                NomeParametroUrlLogin = parametriArCfg.NomeParametroLoginUrl,
                ImpostaAutomaticamenteRichiedente = parVertAreaRiservata.ImpostaAutoRichiedente == "1",
                IdCampoDinamicoAttivitaAtecoPrevalente = String.IsNullOrEmpty(parVertAreaRiservata.AtecoPrimariaIdCampo) ? (int?)null : Int32.Parse(parVertAreaRiservata.AtecoPrimariaIdCampo),
                NomeConfigurazioneContenuti = parametriArCfg.NomeConfigurazioneContenuti,
                AttivaCompilazioneOnceOnly = parVertAreaRiservata.AttivaCompilazioneOnceOnly,
                ImpostaAutomaticamenteTecnico = parVertAreaRiservata.ImpostaAutoTecnico == "1",
                VerificaHashFilesFirmati = parVertAreaRiservata.VerificaHashFilesFirmati == "1",
                UrlApplicazioneFacct = parVertAreaRiservata.UrlApplicazioneFacct ?? String.Empty,
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
                DettaglioVisura = new ConfigurazioneDettaglioVisuraDto
                {
                    NascondiStatoIstanza = parVertAreaRiservata.VisuraNascondiStatoIstanza,
                    NascondiResponsabili = parVertAreaRiservata.VisuraNascondiResponsabili,
                    MostraPosizioneArchivio = parVertAreaRiservata.VisuraMostraPosizioneArchivio
                },
                ArpaCalabria = false,
                NascondiRigeneraRiepilogo = parVertAreaRiservata.NascondiRigeneraRiepilogo,
                GenerazioneCertificatoDiInvio = new ConfigurazioneAreaRiservataDto.ParametriGenerazioneCertificatoDiInvio
                {
                    NomeFile = parVertAreaRiservata.NomeFileRicevuta,
                    DescrizioneFile = parVertAreaRiservata.DescrizioneFileRicevuta
                },
                RichiedenteSoloPersonaFisica = (configurazioneGeneraleSoftware.FLAG_RICHIEDENTEPF == "1"),
                AbilitaTemplateDomanda = parVertAreaRiservata.AbilitaTemplateDomanda,
                QuestionarioSoddisfazione = new ConfigurazioneAreaRiservataDto.ParametriQuestionarioSoddisfazione
                {
                    Attivo = parVertAreaRiservata.QuestionarioSoddisfazioneAttivo
                },
                AreaRiservataCore = new ParametriAreaRiservataCore
                {
                    UsaAreaRiservataCore = parVertAreaRiservata.UsaAreaRiservataCore,
                    BaseUrlCore = parVertAreaRiservata.BaseUrlCore,
                    BaseUrlFramework = parVertAreaRiservata.BaseUrlFramework
                },
                RabbitMQ = new ConfigurazioneAreaRiservataDto.ParametriRabbitMQ
                {
                    Attivo = verticalizzazioneRabbitMQ.Attiva
                },
                DomandaOnLine = new ConfigurazioneAreaRiservataDto.ParametriDomandaOnLine
                {
                    UrlLayoutConfigService = parVertAreaRiservata.DolUrlLayoutConfigService,
                    UrlHomepageComune = parVertAreaRiservata.DolUrlHomepageComune,
                    UrlTerminiECondizioni = parVertAreaRiservata.DolUrlTerminiECondizioni,
                    ModalitaInvioNotifiche = parVertAreaRiservata.DolModalitaInvioNotifiche
                },
                IntegrazioniMessaggioTermineInvio = parVertAreaRiservata.IntegrazioniMessaggioTermineInvio,
                PagamentiPermettiAnnullamentoModello3 = parVertAreaRiservata.PagamentiPermettiAnnullamentoModello3,
                VerificaFirmaSoggettiRiepilogo = parVertAreaRiservata.VerificaFirmaSoggettiRiepilogo
            };

            if (verticalizzazioneArSsu.Attiva)
            {
                rVal.AreaRiservataSsu = new ConfigurazioneAreaRiservataDto.ParametriAreaRiservataSsu
                {
                    Attiva = true,
                    BaseUrlApiCatalogoServizi = verticalizzazioneArSsu.BaseUrlApiCatalogoServizi,
                    IdNodoDestinatario = verticalizzazioneArSsu.IdNodoDestinatario,
                    IdEnteDestinatario = verticalizzazioneArSsu.IdEnteDestinatario,
                    IdSportelloDestinatario = verticalizzazioneArSsu.IdSportelloDestinatario,
                    BaseUrlApiValidator = verticalizzazioneArSsu.BaseUrlApiValidator
                };
            }

            if (parVertSportelloCittadino.Attiva)
            {
                rVal.ServiziCittadino.UrlWsModulisticaDrupal = parVertSportelloCittadino.UrlWsModulisticaDrupal;
            }

            if (parametriArCfg.FkidSchedaEc.HasValue)
            {
                var schedaCittadinoEC = new Dyn2ModelliTMgr(this._db).GetById(this._idComune, parametriArCfg.FkidSchedaEc.Value);

                if (schedaCittadinoEC != null)
                {
                    rVal.SchedaDinamicaCittadiniExtracomunitari = new SchedaDinamicaCittadinoExtracomunitario
                    {
                        Codice = parametriArCfg.FkidSchedaEc.Value,
                        RichiedeFirma = (parametriArCfg.FlgSchedaEcRichiedeFirma ?? 0) == 1,
                        Descrizione = schedaCittadinoEC.Descrizione
                    };
                }
            }
            // Verticalizzazione MIP
            var parVertPagamentiMIP = this._verticalizzazioniFactory.Create<VerticalizzazionePagamentiMipRpcsuap>(alias, software);

            if (parVertPagamentiMIP.Attiva)
            {
                rVal.ConfigurazionePagamentiMIP = new ConfigurazionePagamentiMIP
                {
                    VerticalizzazioneAttiva = true,
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

            // Verticalizzazioni EntraNext
            var parVertPagamentiEntraNext = this._verticalizzazioniFactory.Create<VerticalizzazionePagamentiEntraNext>(alias, software);

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
            var parVertCart = this._verticalizzazioniFactory.Create<VerticalizzazioneCart>(alias, software);

            if (parVertCart.Attiva)
            {
                rVal.ConfigurazioneCart = new ParametriCartDto
                {
                    UrlAccettatore = parVertCart.UrlAccettatore
                };
            }

            // Verticalizzazione FVG_SOL
            var parVertFvgSol = this._verticalizzazioniFactory.Create<VerticalizzazioneFvgSol>(alias, software);

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
            var parVertWsAnagrafe = this._verticalizzazioniFactory.Create<VerticalizzazioneWsanagrafe>(alias, software);

            var urlRicercheAnagraficheDefault = "";
            var urlRicercheAnagrafichePf = parVertWsAnagrafe.Attiva && !String.IsNullOrEmpty(parVertWsAnagrafe.UrlRicercaPf) ? parVertWsAnagrafe.UrlRicercaPf : urlRicercheAnagraficheDefault;
            var urlRicercheAnagrafichePg = parVertWsAnagrafe.Attiva && !String.IsNullOrEmpty(parVertWsAnagrafe.UrlRicercaPg) ? parVertWsAnagrafe.UrlRicercaPg : urlRicercheAnagraficheDefault;

            rVal.UrlWsRicercheAnagrafiche = new ConfigurazioneAreaRiservataDto.UrlWebserviceRicercaAnagrafiche
            {
                PersoneFisiche = urlRicercheAnagrafichePf,
                PersoneGiuridiche = urlRicercheAnagrafichePg
            };

            var parVertSitLdp = this._verticalizzazioniFactory.Create<VerticalizzazioneSitLdp>(alias, software);

            if (parVertSitLdp.Attiva)
            {
                rVal.SitLDP.UrlGenerazionePdfDomanda = parVertSitLdp.UrlGenerazionePdfDomanda;
                rVal.SitLDP.UrlPresentazioneDomandaLdp = parVertSitLdp.UrlPresentazioneDomanda;
                rVal.SitLDP.UrlServiziDomandaLdp = parVertSitLdp.UrlServizioDomande;
                rVal.SitLDP.Username = parVertSitLdp.Username;
                rVal.SitLDP.Password = parVertSitLdp.Password;
                rVal.SitLDP.UrlRitornoPraticaGIS = parVertSitLdp.UrlRitornoPraticaGIS;
                rVal.SitLDP.UrlPresentazioneIntegrazione = parVertSitLdp.UrlPresentazioneIntegrazione;
            }

            // Verticalizzazione AREARISERVATA_REDIRECT
            var vertRedirect = this._verticalizzazioniFactory.Create<VerticalizzazioneAreaRiservataRedirect>(alias, software);

            if (vertRedirect.Attiva)
            {
                rVal.AreaRiservataRedirect.VerticalizzazioneAttiva = true;
                rVal.AreaRiservataRedirect.NomeFile = vertRedirect.NomeFile;
                rVal.AreaRiservataRedirect.UrlRedirect = vertRedirect.UrlRedirect;
            }

            // Configurazione delle dimensioni massime degli allegati liberi
            rVal.FormatiAllegatiLiberi = new DimensioniAllegatiLiberiRepository(this._db, this._idComune)
                                            .GetList(software)
                                            .Select(x => new FormatoAllegatoLiberoDto
                                            {
                                                Id = x.Id,
                                                DimensioneMaxPagina = x.DimensioneMaxPagina,
                                                Formato = x.Formato
                                            })
                                            .ToArray();

            // Verticalizzazione "Scrivania enti terzi"
            var scrivaniaEntiTerzi = this._verticalizzazioniFactory.Create<VerticalizzaizoneScrivaniaEntiTerzi>(alias, software);

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


            // SIT attivo
            var verticalizzazioneSitAttivo = this._verticalizzazioniFactory.Create<VerticalizzazioneSitAttivo>(alias, software);

            if (verticalizzazioneSitAttivo.Attiva /*&& !String.IsNullOrEmpty(verticalizzazioneSitAttivo.UrlWssit)*/)
            {
                rVal.ConfigurazioneSit = new ConfigurazioneSitDto
                {
                    Attivo = true,
                    UrlWsSit = verticalizzazioneSitAttivo.UrlWssit,
                    ForzaStepLocalizzazioniSit = parVertAreaRiservata.ForzaStepLocalizzazioniSit
                };
            }

            // Accesso agli atti
            var vertAccessoAgliAtti = this._verticalizzazioniFactory.Create<VerticalizzazioneAccessoAgliAtti>(alias, software);

            if (vertAccessoAgliAtti.Attiva)
            {
                rVal.AccessoAgliAtti = new ParametriAccessoAgliAtti
                {
                    MostraDatiMovimenti = vertAccessoAgliAtti.ArMostraDatiMovimenti
                };
            }

            return rVal;
        }
    }
}
