using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using ProtocolloIride2Service;
using SIGePro.Manager.VerticalizzazioniBase;
using System.Data;
using System.Text.RegularExpressions;
using VBG.Backend.Protocollo.AppLogic.Core.Iride2;
using VBG.Backend.Protocollo.AppLogic.Core.Iride2.Builders;
using VBG.Backend.Protocollo.AppLogic.Core.Iride2.Configuration;
using VBG.Backend.Protocollo.AppLogic.Core.Iride2.CreaCopie;
using VBG.Backend.Protocollo.AppLogic.Core.Iride2.PosteWeb;
using VBG.Backend.Protocollo.AppLogic.Core.Iride2.Services;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Exceptions;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.Verticalizzazioni.Core;
using LeggiProtocolloRequest = VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass.LeggiProtocolloRequest;

namespace VBG.Backend.Protocollo.AppLogic.Core
{
    /// <summary>
    /// Descrizione di riepilogo per PROTOCOLLO_IRIDE.
    /// </summary>
    public class PROTOCOLLO_IRIDE2 : ProtocolloBase
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly IBindingFactory _bindingFactory;

        public PROTOCOLLO_IRIDE2(IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._bindingFactory = bindingFactory;
        }

        public static class Constants
        {
            public const string PERSONA_FISICA_IRIDE = "FI";
            public const string PERSONA_GIURIDICA_IRIDE = "GI";

        }

        public override DatiProtocolloFascicolatoResponseType IsFascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            var vert = new VerticalizzazioniConfiguration(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloIride2>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

            var protocolloService = new ProtocolloServiceWrapper(vert, this.Operatore, this.Ruolo, this.ProxyAddress, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory);
            var pDocumentoOut = protocolloService.LeggiProtocolloDocumento(idProtocollo, annoProtocollo, numeroProtocollo);

            var datiProtFasc = new DatiProtocolloFascicolatoResponseType();

            if (pDocumentoOut.IdDocumento != 0)
            {
                //Verifico se il protocollo è stato fascicolato
                if (pDocumentoOut.IdPratica == 0 && (String.IsNullOrEmpty(pDocumentoOut.NumeroPratica) || pDocumentoOut.AnnoPratica == 0))
                    datiProtFasc.Fascicolato = EnumFascicolatoType.no;
                else
                {
                    var fascicoloService = new FascicoloServiceWrapper(vert, this.Operatore, this.Ruolo, this.ProxyAddress, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory);
                    var fascicoloOut = fascicoloService.LeggiFascicolo(pDocumentoOut.NumeroPratica, pDocumentoOut.AnnoPratica.ToString(), pDocumentoOut.Classifica, pDocumentoOut.IdPratica, this.Operatore, this.Ruolo);

                    datiProtFasc.AnnoFascicolo = fascicoloOut.Anno.ToString();
                    datiProtFasc.Classifica = String.IsNullOrEmpty(fascicoloOut.Classifica) ? pDocumentoOut.Classifica : fascicoloOut.Classifica;
                    datiProtFasc.DataFascicolo = fascicoloOut.Data.Value.ToString("dd/MM/yyyy");
                    datiProtFasc.NumeroFascicolo = fascicoloOut.Numero;
                    datiProtFasc.Oggetto = fascicoloOut.Oggetto;
                    datiProtFasc.Fascicolato = EnumFascicolatoType.si;
                }
            }
            else
            {
                datiProtFasc.Fascicolato = EnumFascicolatoType.warning;
                datiProtFasc.NoteFascicolo = "Errore: " + pDocumentoOut.Messaggio + "." + pDocumentoOut.Errore;
            }

            return datiProtFasc;
        }

        public override DatiFascicoloResponseType Fascicola(Fascicolo fascicolo)
        {
            if (fascicolo == null)
                return null;

            var vert = new VerticalizzazioniConfiguration(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloIride2>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

            try
            {
                var fascicoloService = new FascicoloServiceWrapper(vert, this.Operatore, this.Ruolo, this.ProxyAddress, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory);
                return fascicoloService.FascicolaProtocollo(this.IdProtocollo, this.AnnoProtocollo, this.NumProtocollo, fascicolo, this.DatiProtocollo, this.Operatore, this.Ruolo);
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA FASCICOLAZIONE, {0}", ex.Message), ex);
            }
        }

        public override DatiFascicoloResponseType CambiaFascicolo(Shared.Data.Fascicolo fascicolo)
        {
            var vert = new VerticalizzazioniConfiguration(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloIride2>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

            try
            {
                var fascicoloService = new FascicoloServiceWrapper(vert, this.Operatore, this.Ruolo, this.ProxyAddress, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory);
                return fascicoloService.CambiaFascicolo(this.IdProtocollo, this.AnnoProtocollo, this.NumProtocollo, fascicolo, this.DatiProtocollo, this.Operatore, this.Ruolo);
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE IL CAMBIAMENTO DI UN FASCICOLO, {0}", ex.Message), ex);
            }
        }

        public override EtichetteResponseType StampaEtichette(string idProtocollo, DateTime? dataProtocollo, string numeroProtocollo, int numeroCopie, string stampante)
        {
            var vert = new VerticalizzazioniConfiguration(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloIride2>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

            this.DataProtocollo = dataProtocollo;

            try
            {
                var fascicoloService = new FascicoloServiceWrapper(vert, this.Operatore, this.Ruolo, this.ProxyAddress, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory);
                return fascicoloService.StampaEtichette(this.IdProtocollo, this.AnnoProtocollo, this.NumProtocollo);
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA STAMPA DI UN'ETICHETTA, {0}", ex.Message), ex);
            }
        }

        public override DatiProtocolloResponseType CreaCopie()
        {
            var vert = new VerticalizzazioniConfiguration(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloIride2>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

            if (vert.DisabilitaCreaCopie)
            {
                this._protocolloLogs.Debug("Funzionalità CreaCopie disabilitata tramite parametro DISABILITA_CREACOPIE della verticalizzazione PROTOCOLLO_IRIDE");
                return null;
            }

            var protocolloService = new ProtocolloServiceWrapper(vert, this.Operatore, this.Ruolo, this.ProxyAddress, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory);
            var info = new CreaCopieInfo(this._protocolloLogs, this._protocolloSerializer, protocolloService, vert, this.DatiProtocollo.Istanza.FKIDPROTOCOLLO,
                                            this.DatiProtocollo.Istanza.NUMEROPROTOCOLLO, this.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value.Year.ToString(), this.Operatore,
                                            this.Ruolo, this.Uo, this.ProxyAddress, this.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value);

            var factory = CreaCopieFactory.Create(info, this._bindingFactory);
            var documentoOut = factory.GeneraCopia();

            var retVal = this.CreaDatiProtocollo(documentoOut, vert);

            return retVal;
        }

        public override DatiProtocolloResponseType MettiAllaFirma(DatiProtocolloIn proto)
        {
            var vert = new VerticalizzazioniConfiguration(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloIride2>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

            DatiProtocolloResponseType documentoRes = null;
            try
            {
                var protoIn = this.CreaProtocolloIn(proto, vert);

                this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.InserisciDocumentoRequestFileName, protoIn);
                this._protocolloLogs.InfoFormat("Chiamata a web method InserisciDocumento da metti alla firma, request file: {0}", ProtocolloLogsConstants.InserisciDocumentoRequestFileName);

                var protocolloService = new ProtocolloServiceWrapper(vert, this.Operatore, this.Ruolo, this.ProxyAddress, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory);
                var docOut = protocolloService.InserisciDocumento(protoIn);

                this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.InserisciDocumentoResponseFileName, docOut);

                if (docOut.IdDocumento != 0 && String.IsNullOrEmpty(docOut.Errore))
                {
                    this._protocolloLogs.Info("MESSA ALLA FIRMA AVVENUTA CON SUCCESSO");
                    documentoRes = this.CreaDatiProtocollo(docOut, vert);
                }
                else
                    throw new Exception(String.Format("METODO INSERISCIDOCUMENTO. MESSAGGIO DI ERRORE: {0}. ERRORE: {1}", docOut.Messaggio, docOut.Errore));
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA MESSA ALLA FIRMA, {0}", ex.Message), ex);
            }

            return documentoRes;
        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn proto)
        {
            var vert = new VerticalizzazioniConfiguration(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloIride2>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

            try
            {
                this._protocolloLogs.Debug("#### Inizio Richiesta di Protocollazione ####");
                this._protocolloLogs.Debug("#### CreaProtocolloIn ####");
                var protoIn = this.CreaProtocolloIn(proto, vert);

                this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.ProtocollazioneRequestFileName, protoIn);

                this._protocolloLogs.InfoFormat("Chiamata al web method InserisciProtocollo, file request: {0}", ProtocolloLogsConstants.ProtocollazioneRequestFileName);
                var protocolloService = new ProtocolloServiceWrapper(vert, this.Operatore, this.Ruolo, this.ProxyAddress, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory);
                var protoOut = protocolloService.InserisciProtocollo(protoIn);

                this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.ProtocollazioneResponseFileName, protoOut);

                if (protoOut.IdDocumento != 0)
                {
                    this._protocolloLogs.InfoFormat("PROTOCOLLAZIONE AVVENUTA CON SUCCESSO, FLUSSO: {0}", protoIn.Origine);

                    if ((protoIn.Origine == ProtocolloConstants.COD_PARTENZA || protoIn.Origine == ProtocolloConstants.COD_INTERNO) && (this.Anagrafiche != null && this.Anagrafiche.Count > 1))
                    {
                        this.CreaCopiePerAmministrazioniInterne(proto, protoOut, vert);
                    }
                    this._protocolloLogs.InfoFormat("URL PEC PRESENTE: {0}, FLUSSO: {1}", !String.IsNullOrEmpty(vert.UrlPec), protoIn.Origine);
                    if (!String.IsNullOrEmpty(vert.UrlPec) && (protoIn.Origine == ProtocolloConstants.COD_PARTENZA))
                    {
                        this.InviaPec(proto, protoOut, protoIn.Ruolo, vert);
                    }

                    return this.CreaDatiProtocollo(protoOut, vert);
                }
                else
                    throw new Exception(String.Format("ERRORE RESTITUITO DAL WEBSERVICE. METODO InserisciProtocollo. MESSAGGIO: {0}, ERRORE: {1}", protoOut.Messaggio, protoOut.Errore));
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE, {0}", ex.Message), ex);
            }
        }

        /// <summary>
        /// Si occupa di preparare i dati riguardanti la Pec.
        /// </summary>
        /// <param name="protoIn"></param>
        /// <param name="protoOut"></param>
        /// <param name="ruolo"></param>
        /// <returns></returns>
        private void InviaPec(DatiProtocolloIn protoIn, ProtocolloOut protoOut, string ruolo, VerticalizzazioniConfiguration vert)
        {
            this._protocolloLogs.Info("INIZIO FUNZIONALITA' DI INVIO PEC");
            try
            {
                if (String.IsNullOrEmpty(protoIn.Oggetto))
                    this._protocolloLogs.WarnFormat("INVIO PEC non eseguito a causa dell'assenza dell'oggetto della mail, controllare l'oggetto di default in configurazione, protocollo numero: {0}, data: {1}", protoOut.NumeroProtocollo.ToString(), protoOut.DataProtocollo.Value.ToString("dd/MM/yyyy"));
                else if (String.IsNullOrEmpty(vert.MittenteMailPec))
                    this._protocolloLogs.WarnFormat("INVIO PEC non eseguito a causa dell'assenza del mittente della mail, controllare il parametro MITTENTE_MAIL_PEC della verticalizzazione PROTOCOLLO_IRIDE, protocollo numero: {0}, data: {1}", protoOut.NumeroProtocollo.ToString(), protoOut.DataProtocollo.Value.ToString("dd/MM/yyyy"));
                else if (String.IsNullOrEmpty(vert.UrlPec))
                    this._protocolloLogs.WarnFormat("INVIO PEC non eseguito a causa dell'assenza dell'url (end point) del servizio di invio mail PEC di Iride, controllare il parametro URL_PEC della verticalizzazione PROTOCOLLO_IRIDE, protocollo numero: {0}, data: {1}", protoOut.NumeroProtocollo.ToString(), protoOut.DataProtocollo.Value.ToString("dd/MM/yyyy"));
                else
                {

                    this.Ruolo = ruolo;
                    IEnumerable<string> seriali;

                    if (protoOut.Allegati == null)
                    {
                        var protocolloService = new ProtocolloServiceWrapper(vert, this.Operatore, this.Ruolo, this.ProxyAddress, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory);
                        var docOut = protocolloService.LeggiDocumento(protoOut.IdDocumento);
                        seriali = docOut.Allegati.Select(x => x.Serial.ToString());
                    }
                    else
                        seriali = protoOut.Allegati.Select(x => x.Serial.ToString());


                    if (vert.WarningPec)
                    {
                        var anagraficheNoPEC = this.Anagrafiche.Where(x => String.IsNullOrEmpty(x.Pec)).Select(x => x.NomeCognome);
                        if (anagraficheNoPEC.Count() > 0)
                        {
                            this._protocolloLogs.WarnFormat("I SEGUENTI DESTINATARI NON PRESENTANO UN INDIRIZZO PEC, AI QUALI NON E' STATA QUINDI INVIATA: {0}", String.Join(", ", anagraficheNoPEC));
                        }

                        var anagraficheNoMezzo = this.Anagrafiche.Where(x => x.MezzoInvio != vert.MezzoPec).Select(x => x.NomeCognome);

                        if (anagraficheNoMezzo.Count() > 0)
                        {
                            this._protocolloLogs.WarnFormat("I SEGUENTI DESTINATARI NON PRESENTANO IL MEZZO DI INVIO PEC, AI QUALI NON E' STATA QUINDI INVIATA: {0}", String.Join(", ", anagraficheNoMezzo));
                        }
                    }

                    var listaPec = this.Anagrafiche.Where(y => !String.IsNullOrEmpty(y.Pec) && y.MezzoInvio == vert.MezzoPec).
                                                                        GroupBy(x => x.Pec.ToUpperInvariant()).
                                                                        Select(x => x.Key).ToArray();



                    this._protocolloLogs.InfoFormat("NUMERO DESTINATARI PER INVIO PEC: {0}", listaPec.Length);

                    if (listaPec.Length > 0)
                    {
                        var pec = PecFactory.Create(vert.Versione, seriali, listaPec, vert.Aoo, vert.UsaInvioInteroperabilePec, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory);

                        var oggetto = protoIn.Oggetto;
                        if (vert.UsaRifProtocolloOggettoPec)
                        {
                            oggetto = $"Pr. Num.: {protoOut.NumeroProtocollo}, Data Pr.: {protoOut.DataProtocollo.Value.ToString("dd/MM/yyyy")} - {protoIn.Oggetto}";
                        }

                        pec.Invia(vert.UrlPec, this.ProxyAddress, protoOut.IdDocumento.ToString(), Regex.Replace(oggetto, @"\r\n?|\n", "-"), protoIn.CorpoMail, vert.MittenteMailPec, this.Operatore, ruolo, vert.CodiceAmministrazione);

                        if (vert.WarningPec)
                        {
                            this._protocolloLogs.WarnFormat("PEC INVIATA CORRETTAMENTE AI SEGUENTI DESTINATARI: {0}", String.Join(", ", listaPec));
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                this._protocolloLogs.WarnFormat(String.Format("PROBLEMA DURANTE LA FUNZIONALITA' DI INVIO PEC, ERRORE: {0}", ex.Message));
            }
            finally
            {
            }
        }

        private void CreaCopiePerAmministrazioniInterne(DatiProtocolloIn protocolloInput, ProtocolloOut protocolloOutputOrigine, VerticalizzazioniConfiguration vert)
        {
            try
            {
                this._protocolloLogs.DebugFormat("Inizio funzionalità crea copie del protocollo numero: {0}, data: {1}, id: {2}", protocolloOutputOrigine.NumeroProtocollo.ToString(), protocolloOutputOrigine.DataProtocollo.Value.ToString("dd/MM/yyyy"), protocolloOutputOrigine.IdDocumento.ToString());

                var uoDestinatari = new List<UODestinataria>();

                var ammList = protocolloInput.Destinatari.Amministrazione.Where(amm => !String.IsNullOrEmpty(amm.PROT_UO) && !String.IsNullOrEmpty(amm.PROT_RUOLO)).ToList();
                if (ammList.Count > 0)
                {
                    if (protocolloInput.Destinatari.Anagrafe.Count == 0 && protocolloInput.Destinatari.Amministrazione.Where(x => String.IsNullOrEmpty(x.PROT_UO) && String.IsNullOrEmpty(x.PROT_RUOLO)).Count() == 0)
                        ammList = ammList.Skip(1).ToList();

                    ammList.ForEach(amm => uoDestinatari.Add(new UODestinataria { Carico = amm.PROT_UO, Data = DateTime.Now.ToString("dd/MM/yyyy"), TipoUO = "UO", NumeroCopie = "1" }));

                    var creaCopieService = new CreaCopieService(vert.Url, this.ProxyAddress, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory);

                    var creaCopieOut = creaCopieService.CreaCopie(protocolloInput.Mittenti.Amministrazione[0].PROT_RUOLO,
                                                    protocolloOutputOrigine.IdDocumento,
                                                    protocolloOutputOrigine.AnnoProtocollo.ToString(),
                                                    protocolloOutputOrigine.NumeroProtocollo.ToString(),
                                                    this.Operatore,
                                                    vert.CodiceAmministrazione, uoDestinatari.ToArray());

                    if (creaCopieOut.CopieCreate == null || creaCopieOut.CopieCreate.Count == 0)
                        this._protocolloLogs.ErrorFormat("E' stato restituito un errore dal web service durante la funzionalità CreaCopie eseguita dopo la protocollazione, Id Protocollo Originale: {0}, numero/anno {1}/{2}, Errore: {3}",
                                                    protocolloOutputOrigine.IdDocumento,
                                                    protocolloOutputOrigine.NumeroProtocollo.ToString(),
                                                    protocolloOutputOrigine.AnnoProtocollo.ToString(),
                                                    creaCopieOut.Errore);
                    else
                        this._protocolloLogs.InfoFormat("E' stata creata una copia con la funzionalità CreaCopie eseguita dopo la protocollazione, Id Protocollo Originale:{0}, Id Protocollo Copia: {1}, Numero/Anno Protocollo: {2}/{3}",
                                                    creaCopieOut.IdDocumentoSorgente.ToString(),
                                                    protocolloOutputOrigine.IdDocumento.ToString(),
                                                    protocolloOutputOrigine.NumeroProtocollo.ToString(),
                                                    protocolloOutputOrigine.AnnoProtocollo.ToString()
                                                    );

                    this._protocolloLogs.DebugFormat("Fine funzionalità crea copie del protocollo numero: {0}, data: {1}, id: {2}", protocolloOutputOrigine.NumeroProtocollo.ToString(), protocolloOutputOrigine.DataProtocollo.Value.ToString("dd/MM/yyyy"), protocolloOutputOrigine.IdDocumento.ToString());
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE AVVENUTO DURANTE LA CREAZIONE DELLE COPIE PER LE AMMINISTRAZIONI INTERNE, COPIA DOPO PROTOCOLLAZIONE, {0}", ex.Message), ex);
            }
        }

        private DatiProtocolloResponseType CreaDatiProtocollo(DocumentoOut protoOut, VerticalizzazioniConfiguration vert)
        {
            try
            {
                var protoRes = new DatiProtocolloResponseType();

                protoRes.IdProtocollo = protoOut.IdDocumento.ToString();
                protoRes.AnnoProtocollo = protoOut.AnnoProtocollo.ToString();
                protoRes.DataProtocollo = protoOut.DataProtocollo.Value.ToString("dd/MM/yyyy");
                protoRes.NumeroProtocollo = protoOut.NumeroProtocollo.ToString();

                if (this.ModificaNumero)
                    protoRes.NumeroProtocollo = protoRes.NumeroProtocollo.TrimStart(new char[] { '0' });

                if (this.AggiungiAnno)
                    protoRes.NumeroProtocollo += "/" + protoOut.AnnoProtocollo.ToString();

                if (!String.IsNullOrEmpty(protoOut.Errore))
                {
                    this._protocolloLogs.Warn(protoOut.Errore);
                }

                protoRes.Warning = this._protocolloLogs.Warnings.WarningMessage;

                if (!String.IsNullOrEmpty(vert.MessaggioProtoOk) && !String.IsNullOrEmpty(this._protocolloLogs.Warnings.WarningMessage))
                    protoRes.Warning = this._protocolloLogs.Warnings.WarningMessage.Replace(vert.MessaggioProtoOk, "");

                this._protocolloLogs.InfoFormat("Dati protocollo restituiti, numero: {0}, anno: {1}, data: {2}", protoRes.NumeroProtocollo, protoRes.AnnoProtocollo, protoRes.DataProtocollo);

                return protoRes;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DOPO LA RESTITUZIONE DEI DATI, IL PROTOCOLLO POTREBBE ESSERE STATO CREATO, {0}", ex.Message), ex);
            }
        }

        protected DatiProtocolloResponseType CreaDatiProtocollo(ProtocolloOut protoOut, VerticalizzazioniConfiguration vert)
        {
            try
            {
                var protoRes = new DatiProtocolloResponseType();

                protoRes.IdProtocollo = protoOut.IdDocumento.ToString();
                protoRes.AnnoProtocollo = protoOut.AnnoProtocollo.ToString();
                protoRes.DataProtocollo = protoOut.DataProtocollo.Value.ToString("dd/MM/yyyy");
                protoRes.NumeroProtocollo = protoOut.NumeroProtocollo.ToString();

                if (this.ModificaNumero)
                    protoRes.NumeroProtocollo = protoRes.NumeroProtocollo.TrimStart(new char[] { '0' });

                if (this.AggiungiAnno)
                    protoRes.NumeroProtocollo += "/" + protoOut.AnnoProtocollo.ToString();

                if (!String.IsNullOrEmpty(protoOut.Errore))
                {
                    this._protocolloLogs.Warn(protoOut.Errore);
                }

                protoRes.Warning = this._protocolloLogs.Warnings.WarningMessage;

                if (!String.IsNullOrEmpty(vert.MessaggioProtoOk) && !String.IsNullOrEmpty(this._protocolloLogs.Warnings.WarningMessage))
                {
                    protoRes.Warning = this._protocolloLogs.Warnings.WarningMessage.Replace(vert.MessaggioProtoOk, "");
                }

                this._protocolloLogs.InfoFormat("Dati protocollo restituiti, numero: {0}, anno: {1}, data: {2}", protoRes.NumeroProtocollo, protoRes.AnnoProtocollo, protoRes.DataProtocollo);

                return protoRes;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DOPO LA RESTITUZIONE DEI DATI, IL PROTOCOLLO POTREBBE ESSERE STATO CREATO, {0}", ex.Message), ex);
            }
        }

        protected ProtocolloIn CreaProtocolloIn(Shared.Data.DatiProtocolloIn datiProto, VerticalizzazioniConfiguration vert)
        {
            var protoIn = new ProtocolloIn();

            //Setto i parametri della classe ProtocolloIn
            protoIn.Data = DateTime.Now.Date.ToString("dd/MM/yyyy");
            protoIn.Classifica = datiProto.Classifica;
            protoIn.TipoDocumento = datiProto.TipoDocumento;
            protoIn.Oggetto = datiProto.Oggetto;
            protoIn.Origine = datiProto.Flusso;
            protoIn.AggiornaAnagrafiche = vert.AggiornaAnagrafiche;

            if (!String.IsNullOrEmpty(datiProto.TipoSmistamento))
                protoIn.OggettoBilingue = datiProto.TipoSmistamento;

            //Gestione Fascicolazione (come comportarsi con la protocollazione delle autorizzazioni???)
            if (!string.IsNullOrEmpty(vert.NumeroPratica) && !this.GestisciFascicolazione)
            {
                //Fascicolazione con fascicolo faldone precedentemente creato
                protoIn.NumeroPratica = vert.NumeroPratica;
                protoIn.AnnoPratica = DateTime.Now.Year.ToString();
            }

            protoIn.Utente = this.Operatore.ToUpper();

            this._protocolloLogs.Debug("#### SetAllegati ####");
            //Setto gli allegati
            this.SetAllegati(protoIn, datiProto);

            this._protocolloLogs.Debug("#### SetMittenti ####");
            //Setto i mittenti
            this.SetMittenti(protoIn, datiProto, vert);

            this._protocolloLogs.Debug("#### SetDestinatari ####");
            //Setto i destinatari
            this.SetDestinatari(protoIn, datiProto, vert);

            return protoIn;
        }

        private void SetAllegati(ProtocolloIn protoIn, DatiProtocolloIn datiProtoIn)
        {
            try
            {
                var allegati = new ArrayOfAllegatoIn();
                allegati.AddRange(new AllegatoIn[datiProtoIn.NumeroAllegatiPresenti]);

                protoIn.Allegati = allegati;

                var iIndex = 0;
                foreach (var protoAllegati in datiProtoIn.RecuperaAllegati())
                {
                    //non dovrebbe essere necessario
                    if (protoAllegati.OGGETTO == null)
                        throw new ProtocolloException("Errore generato dal web method SetAllegati del protocollo Iride. Metodo: SetAllegati, modulo: ProtocolloIride. C'è un allegato con il campo OGGETTO null.\r\n");

                    protoIn.Allegati[iIndex] = new AllegatoIn();

                    protoIn.Allegati[iIndex].ContentType = protoAllegati.MimeType;
                    protoIn.Allegati[iIndex].Image = protoAllegati.OGGETTO;

                    if (!String.IsNullOrEmpty(protoAllegati.Extension))
                        protoIn.Allegati[iIndex].TipoFile = protoAllegati.Extension.Substring(1);

                    protoIn.Allegati[iIndex].Commento = protoAllegati.Descrizione;
                    protoIn.Allegati[iIndex].NomeAllegato = protoAllegati.NOMEFILE;

                    iIndex++;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE IL SETTAGGIO DEGLI ALLEGATI, {0}", ex.Message), ex);
            }
        }

        private void SetMittenti(ProtocolloIn protoIn, Shared.Data.DatiProtocolloIn datiProto, VerticalizzazioniConfiguration vert)
        {
            try
            {
                protoIn.MittenteInterno = "";
                var mittentiDestinatariList = new List<MittenteDestinatarioIn>();

                //Verifico le amministrazioni (interne ed esterne)
                if (datiProto.Mittenti.Amministrazione.Count >= 1)
                {
                    if ((!String.IsNullOrEmpty(datiProto.Mittenti.Amministrazione[0].PROT_UO)) && (!String.IsNullOrEmpty(datiProto.Mittenti.Amministrazione[0].PROT_RUOLO)))
                    {
                        protoIn.MittenteInterno = String.IsNullOrEmpty(vert.UoSmistamento) ? datiProto.Mittenti.Amministrazione[0].PROT_UO : vert.UoSmistamento;
                        protoIn.Ruolo = String.IsNullOrEmpty(vert.UoSmistamento) ? datiProto.Mittenti.Amministrazione[0].PROT_RUOLO : vert.UoSmistamento;
                        //Se il flusso è in Partenza occorre settare anche InCaricoA e Ruolo con PROT_UO
                        if (protoIn.Origine == ProtocolloConstants.COD_PARTENZA)
                        {
                            protoIn.MittenteInterno = datiProto.Mittenti.Amministrazione[0].PROT_UO;

                            if (!vert.DisabilitaCaricoPartenza)
                            {
                                protoIn.InCaricoA = datiProto.Mittenti.Amministrazione[0].PROT_UO;
                                protoIn.Ruolo = datiProto.Mittenti.Amministrazione[0].PROT_RUOLO; //modificato per test Ravenna
                            }
                        }
                    }
                    else if ((String.IsNullOrEmpty(datiProto.Mittenti.Amministrazione[0].PROT_UO)) ^ (String.IsNullOrEmpty(datiProto.Mittenti.Amministrazione[0].PROT_RUOLO)))
                        throw new Exception("PER ESEGUIRE UNA PROTOCOLLAZIONE CON IRIDE È NECESSARIO CHE L'AMMINISTRAZIONE INTERNA ABBIA SETTATO SIA L'UNITÀ ORGANIZZATIVA CHE IL RUOLO!\r\n");
                    else
                    {
                        //Ciclo per le amministrazioni esterne
                        foreach (var amministrazione in datiProto.Mittenti.Amministrazione)
                        {
                            //if (mittentiDestinatariList.Where(x => x.CodiceFiscale == amministrazione.PARTITAIVA).Count() > 0)
                            //    throw new Exception("SONO PRESENTI PIU' MITTENTI CON LO STESSO CODICE FISCALE / PARTITA IVA");

                            var mittentiDestinatari = new MittenteDestinatarioIn();
                            if (mittentiDestinatariList.Count >= 100)
                            {
                                this._protocolloLogs.WarnFormat("Sono state conteggiati {0} mittenti mentre il limite massimo è di 99", mittentiDestinatariList.Count.ToString());
                                return;
                            }

                            mittentiDestinatari.Nome = String.Empty;
                            mittentiDestinatari.CodiceComuneResidenza = String.Empty;
                            mittentiDestinatari.DataNascita = String.Empty;
                            mittentiDestinatari.CodiceComuneNascita = String.Empty;
                            mittentiDestinatari.Nazionalita = String.Empty;
                            mittentiDestinatari.DataInvio_DataProt = String.Empty;
                            mittentiDestinatari.Spese_NProt = String.Empty;

                            //listRecapiti.Add( new RecapitoIn{ TipoRecapito = "EMAIL", ValoreRecapito = amministrazione.EMAIL });
                            //mittentiDestinatari.Recapiti = new RecapitoIn[]

                            mittentiDestinatari.TipoPersona = Constants.PERSONA_GIURIDICA_IRIDE;

                            if (String.IsNullOrEmpty(amministrazione.AMMINISTRAZIONE))
                                throw new Exception(String.Format("DESCRIZIONE AMMINISTRAZIONE ID {0} NON VALORIZZATA", amministrazione.CODICEAMMINISTRAZIONE));

                            mittentiDestinatari.CognomeNome = amministrazione.AMMINISTRAZIONE.TrimEnd();

                            if (!String.IsNullOrEmpty(amministrazione.UFFICIO))
                                mittentiDestinatari.CognomeNome = String.Concat(amministrazione.AMMINISTRAZIONE, " - ", amministrazione.UFFICIO.TrimEnd());

                            mittentiDestinatari.CodiceFiscale = !String.IsNullOrEmpty(amministrazione.PARTITAIVA) ? amministrazione.PARTITAIVA : String.Empty;
                            mittentiDestinatari.Indirizzo = !String.IsNullOrEmpty(amministrazione.INDIRIZZO) ? amministrazione.INDIRIZZO : String.Empty;

                            if (!String.IsNullOrEmpty(amministrazione.CITTA))
                            {
                                mittentiDestinatari.Localita = amministrazione.CITTA;
                            }

                            mittentiDestinatari.Mezzo = !String.IsNullOrEmpty(amministrazione.Mezzo) ? amministrazione.Mezzo : String.Empty;

                            //Setto il campo DataRicevimento che, in seguito ai test fatti a Cesena, è necessario settare
                            //nel caso in cui il flusso è in A
                            if (!String.IsNullOrEmpty(this.DatiProtocollo.CodiceIstanza))
                            {
                                var istMgr = new IstanzeMgr(this.DatiProtocollo.Db);
                                mittentiDestinatari.DataRicevimento = istMgr.GetById(this.DatiProtocollo.IdComune, Convert.ToInt32(this.DatiProtocollo.CodiceIstanza)).DATA.Value.ToString("dd/MM/yyyy");
                            }
                            if (!String.IsNullOrEmpty(this.DatiProtocollo.CodiceMovimento))
                            {
                                var movMgr = new MovimentiMgr(this.DatiProtocollo.Db);
                                mittentiDestinatari.DataRicevimento = movMgr.GetById(this.DatiProtocollo.IdComune, Convert.ToInt32(this.DatiProtocollo.CodiceMovimento)).DATA.Value.ToString("dd/MM/yyyy");
                            }

                            if (!String.IsNullOrEmpty(amministrazione.PEC))
                            {
                                var rec = new IrideRecapitiEmailBuilder(amministrazione.PEC, vert.SwapEmail, vert.TipoRecapitoMail);

                                var recapiti = new ArrayOfRecapitoIn();
                                recapiti.AddRange(rec.Recapiti);

                                mittentiDestinatari.Recapiti = recapiti;
                            }

                            mittentiDestinatariList.Add(mittentiDestinatari);
                        }
                    }
                }

                if (datiProto.Mittenti.Anagrafe.Count >= 1)
                {
                    foreach (var protoAnagrafe in datiProto.Mittenti.Anagrafe)
                    {
                        if (mittentiDestinatariList.Where(x => x.CodiceFiscale == protoAnagrafe.CODICEFISCALE || x.CodiceFiscale == protoAnagrafe.PARTITAIVA).Count() == 0)
                        {

                            if (mittentiDestinatariList.Count >= 100)
                            {
                                this._protocolloLogs.WarnFormat("Sono state conteggiati {0} mittenti mentre il limite massimo è di 99", mittentiDestinatariList.Count.ToString());
                                return;
                            }

                            var mittentiDestinatari = new MittenteDestinatarioIn();

                            var codiceFiscalePartitaIva = "";

                            if (!String.IsNullOrEmpty(protoAnagrafe.CODICEFISCALE))
                                codiceFiscalePartitaIva = protoAnagrafe.CODICEFISCALE;
                            else if (!String.IsNullOrEmpty(protoAnagrafe.PARTITAIVA))
                                codiceFiscalePartitaIva = protoAnagrafe.PARTITAIVA;

                            mittentiDestinatari.CodiceFiscale = codiceFiscalePartitaIva;
                            mittentiDestinatari.CognomeNome = protoAnagrafe.NOMINATIVO;
                            mittentiDestinatari.Nome = protoAnagrafe.NOME ?? "";

                            if (protoAnagrafe.TIPOANAGRAFE == "F")
                            {
                                mittentiDestinatari.DataNascita = protoAnagrafe.DATANASCITA.HasValue ? protoAnagrafe.DATANASCITA.Value.ToString("dd/MM/yyyy") : String.Empty;
                                mittentiDestinatari.TipoPersona = Constants.PERSONA_FISICA_IRIDE;
                            }
                            else
                            {
                                mittentiDestinatari.DataNascita = protoAnagrafe.DATANOMINATIVO.GetValueOrDefault(DateTime.MinValue) == DateTime.MinValue ? String.Empty : protoAnagrafe.DATANOMINATIVO.Value.ToString("dd/MM/yyyy");
                                mittentiDestinatari.TipoPersona = Constants.PERSONA_GIURIDICA_IRIDE;
                            }

                            mittentiDestinatari.Indirizzo = protoAnagrafe.INDIRIZZO ?? "";

                            if (!String.IsNullOrEmpty(protoAnagrafe.Mezzo))
                                mittentiDestinatari.Mezzo = protoAnagrafe.Mezzo;

                            //Modificate per problemi con il Comune di Ravenna
                            mittentiDestinatari.CodiceComuneNascita = !String.IsNullOrEmpty(protoAnagrafe.CodiceStatoEsteroNasc) ? (String.IsNullOrEmpty(vert.View) ? String.Empty : this.GetDecodeCodiceIstatStato(protoAnagrafe.CodiceStatoEsteroNasc, vert)) : this.GetDecodeCodiceIstatStato(protoAnagrafe.CodiceIstatComNasc, vert);
                            mittentiDestinatari.CodiceComuneResidenza = !String.IsNullOrEmpty(protoAnagrafe.CodiceStatoEsteroRes) ? (String.IsNullOrEmpty(vert.View) ? String.Empty : this.GetDecodeCodiceIstatStato(protoAnagrafe.CodiceStatoEsteroRes, vert)) : this.GetDecodeCodiceIstatStato(protoAnagrafe.CodiceIstatComRes, vert);

                            if (!String.IsNullOrEmpty(protoAnagrafe.CITTA))
                            {
                                mittentiDestinatari.Localita = protoAnagrafe.CITTA;
                            }

                            mittentiDestinatari.Nazionalita = "100"; //N.B.: Il valore deve essere ricavato da una tabella Iride

                            //Setto il campo DataRicevimento che, in seguito ai test fatti a Cesena, è necessario settare
                            //nel caso in cui il flusso è in A
                            if (this.DatiProtocollo.TipoAmbito == AmbitoProtocollazioneEnum.DA_ISTANZA)
                                mittentiDestinatari.DataRicevimento = this.DatiProtocollo.Istanza.DATA.Value.ToString("dd/MM/yyyy");

                            if (this.DatiProtocollo.TipoAmbito == AmbitoProtocollazioneEnum.DA_MOVIMENTO)
                                mittentiDestinatari.DataRicevimento = this.DatiProtocollo.Movimento.DATA.Value.ToString("dd/MM/yyyy");

                            if (!String.IsNullOrEmpty(protoAnagrafe.PecProtocollazione))
                            {
                                var recapiti = new ArrayOfRecapitoIn();

                                if (protoAnagrafe.PecProtocollazione.Equals(protoAnagrafe.PecAnagrafica) || String.IsNullOrEmpty(protoAnagrafe.PecAnagrafica))
                                {
                                    var rec = new IrideRecapitiEmailBuilder(protoAnagrafe.PecProtocollazione, vert.SwapEmail, vert.TipoRecapitoMail);

                                    recapiti.AddRange(rec.Recapiti);
                                }
                                else
                                {
                                    var pecs = new string[] { protoAnagrafe.PecProtocollazione, protoAnagrafe.PecAnagrafica };

                                    var rec = new IrideRecapitiEmailBuilder(pecs, vert.SwapEmail, vert.TipoRecapitoMail);
                                    recapiti.AddRange(rec.Recapiti);
                                }

                                mittentiDestinatari.Recapiti = recapiti;
                            }

                            mittentiDestinatariList.Add(mittentiDestinatari);
                        }
                        else
                            if (this.TipoInserimento == Source.PROT_IST_MOV_AUT_BO)
                                throw new Exception("SONO PRESENTI PIU' MITTENTI CON LO STESSO CODICE FISCALE / PARTITA IVA");
                    }
                }
                if (mittentiDestinatariList.Count > 0)
                {
                    var mittenti = new ArrayOfMittenteDestinatarioIn();
                    mittenti.AddRange(mittentiDestinatariList.Take(99));

                    protoIn.MittentiDestinatari = mittenti;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE IL SETTAGGIO DEI MITTENTI, {0}", ex.Message), ex);
            }
        }


        private void SetDestinatari(ProtocolloIn protoIn, DatiProtocolloIn datiProto, VerticalizzazioniConfiguration vert)
        {
            var mittenti = new ArrayOfMittenteDestinatarioIn();

            try
            {
                switch (protoIn.Origine)
                {
                    case ProtocolloConstants.COD_ARRIVO:
                    case ProtocolloConstants.COD_INTERNO:

                        //Verifico le amministrazioni (interne ed esterne)
                        if (datiProto.Destinatari.Amministrazione.Count >= 1)
                        {
                            foreach (var amministrazione in datiProto.Destinatari.Amministrazione)
                            {
                                if ((!String.IsNullOrEmpty(amministrazione.PROT_UO)) && (!String.IsNullOrEmpty(amministrazione.PROT_RUOLO)))
                                {
                                    if (protoIn.Origine == ProtocolloConstants.COD_ARRIVO)
                                    {
                                        protoIn.InCaricoA = amministrazione.PROT_UO;
                                        protoIn.Ruolo = amministrazione.PROT_RUOLO; //modificato per test Ravenna
                                    }

                                    //Se il flusso è Interno occorre settare anche il Tag MittentiDestinatari
                                    if (protoIn.Origine == ProtocolloConstants.COD_INTERNO)
                                    {
                                        protoIn.InCaricoA = amministrazione.PROT_UO;
                                        var mittentiDestinatari = new MittenteDestinatarioIn();

                                        mittentiDestinatari.Nome = String.Empty;
                                        mittentiDestinatari.CodiceComuneResidenza = String.Empty;
                                        mittentiDestinatari.DataNascita = String.Empty;
                                        mittentiDestinatari.CodiceComuneNascita = String.Empty;
                                        mittentiDestinatari.Nazionalita = String.Empty;
                                        mittentiDestinatari.DataInvio_DataProt = String.Empty;
                                        mittentiDestinatari.Spese_NProt = String.Empty;

                                        if (String.IsNullOrEmpty(amministrazione.AMMINISTRAZIONE))
                                            throw new Exception(String.Format("DESCRIZIONE AMMINISTRAZIONE ID {0} NON VALORIZZATA", amministrazione.CODICEAMMINISTRAZIONE));

                                        mittentiDestinatari.CognomeNome = amministrazione.AMMINISTRAZIONE.TrimEnd();

                                        if (!String.IsNullOrEmpty(amministrazione.UFFICIO))
                                            mittentiDestinatari.CognomeNome = String.Concat(amministrazione.AMMINISTRAZIONE, " - ", amministrazione.UFFICIO.TrimEnd());

                                        mittentiDestinatari.CodiceFiscale = !String.IsNullOrEmpty(amministrazione.PARTITAIVA) ? amministrazione.PARTITAIVA : String.Empty;
                                        mittentiDestinatari.Indirizzo = !String.IsNullOrEmpty(amministrazione.INDIRIZZO) ? amministrazione.INDIRIZZO : String.Empty;

                                        if (!String.IsNullOrEmpty(amministrazione.CITTA))
                                        {
                                            mittentiDestinatari.Localita = amministrazione.CITTA;
                                        }

                                        mittentiDestinatari.Mezzo = !String.IsNullOrEmpty(amministrazione.Mezzo) ? amministrazione.Mezzo : String.Empty;

                                        mittentiDestinatari.TipoPersona = Constants.PERSONA_GIURIDICA_IRIDE;

                                        mittenti.AddRange(mittentiDestinatari);

                                        //protoIn.MittentiDestinatari = mittenti;

                                    }
                                }
                                else if ((String.IsNullOrEmpty(amministrazione.PROT_RUOLO)) ^ (String.IsNullOrEmpty(amministrazione.PROT_RUOLO)))
                                    throw new Exception("PER ESEGUIRE UNA PROTOCOLLAZIONE CON IRIDE È NECESSARIO CHE L'AMMINISTRAZIONE INTERNA ABBIA SETTATO SIA L'UNITÀ ORGANIZZATIVA CHE IL RUOLO!");
                            }
                        }
                        break;

                    case ProtocolloConstants.COD_PARTENZA:

                        var amministrazioniEsterne = datiProto.Destinatari.Amministrazione.Where(x => String.IsNullOrEmpty(x.PROT_UO) && String.IsNullOrEmpty(x.PROT_UO));

                        foreach (var amministrazione in amministrazioniEsterne)
                        {
                            //if (mittentiDestinatariList.Where(x => x.CodiceFiscale == amministrazione.PARTITAIVA).Count() > 0)
                            //    throw new Exception("SONO PRESENTI PIU' DESTINATARI CON LO STESSO CODICE FISCALE / PARTITA IVA");

                            if (mittenti.Count >= 100)
                            {
                                this._protocolloLogs.WarnFormat("Sono stati conteggiati {0} destinatari mentre il limite massimo è di 99", mittenti.Count.ToString());
                                return;
                            }

                            /*if (!String.IsNullOrEmpty(amministrazione.PROT_UO) || !String.IsNullOrEmpty(amministrazione.PROT_UO))
                                _protocolloLogs.InfoFormat("NEI DESTINATARI E' PRESENTE UN'AMMINISTRAZIONE INTERNA SI TRATTA QUINDI DI PROTOCOLLAZIONE MISTA, CODICE AMMINISTRAZIONE: {0}, DESCRIZIONE: {1}, UO: {2}, RUOLO: {3}, QUEST'AMMINISTRAZIONE NON SARA' USATA DIRETTAMENTE SULLA PROTOCOLLAZIONE, MA SULLA SUCCESSIVA CREAZIONE DELLE COPIE.", amministrazione.CODICEAMMINISTRAZIONE, amministrazione.AMMINISTRAZIONE, amministrazione.PROT_UO, amministrazione.PROT_RUOLO);
                            else
                            {*/
                            var mittentiDestinatari = new MittenteDestinatarioIn();

                            mittentiDestinatari.Nome = String.Empty;
                            mittentiDestinatari.CodiceComuneResidenza = String.Empty;
                            mittentiDestinatari.DataNascita = String.Empty;
                            mittentiDestinatari.CodiceComuneNascita = String.Empty;
                            mittentiDestinatari.Nazionalita = String.Empty;
                            mittentiDestinatari.DataInvio_DataProt = String.Empty;
                            mittentiDestinatari.Spese_NProt = String.Empty;

                            if (String.IsNullOrEmpty(amministrazione.AMMINISTRAZIONE))
                                throw new Exception(String.Format("DESCRIZIONE AMMINISTRAZIONE ID {0} NON VALORIZZATA", amministrazione.AMMINISTRAZIONE));

                            mittentiDestinatari.CognomeNome = amministrazione.AMMINISTRAZIONE.TrimEnd();

                            if (!String.IsNullOrEmpty(amministrazione.UFFICIO))
                                mittentiDestinatari.CognomeNome = String.Concat(amministrazione.AMMINISTRAZIONE, " - ", amministrazione.UFFICIO.TrimEnd());

                            mittentiDestinatari.CodiceFiscale = !String.IsNullOrEmpty(amministrazione.PARTITAIVA) ? amministrazione.PARTITAIVA : String.Empty;
                            mittentiDestinatari.Indirizzo = !String.IsNullOrEmpty(amministrazione.INDIRIZZO) ? amministrazione.INDIRIZZO : String.Empty;

                            if (!String.IsNullOrEmpty(amministrazione.CITTA))
                            {
                                mittentiDestinatari.Localita = amministrazione.CITTA;
                            }

                            mittentiDestinatari.Mezzo = !String.IsNullOrEmpty(amministrazione.Mezzo) ? amministrazione.Mezzo : String.Empty;

                            mittentiDestinatari.TipoPersona = Constants.PERSONA_GIURIDICA_IRIDE;

                            if (!String.IsNullOrEmpty(amministrazione.PEC))
                            {
                                var rec = new IrideRecapitiEmailBuilder(amministrazione.PEC, vert.SwapEmail, vert.TipoRecapitoMail);

                                var recapiti = new ArrayOfRecapitoIn();
                                recapiti.AddRange(rec.Recapiti);

                                mittentiDestinatari.Recapiti = recapiti;
                            }
                            else
                            {
                                if (!String.IsNullOrEmpty(vert.UrlPec))
                                {
                                    var warn = "";
                                    if (String.IsNullOrEmpty(amministrazione.PARTITAIVA))
                                        warn = String.Format("LA PEC E LA PARTITA IVA DELL'AMMINISTRAZIONE {0}, (CODICE {1}), NON SONO VALORIZZATI, E' PROBABILE QUINDI CHE LA PEC NON SIA STATA INVIATA DAL SERVIZIO POSTE WEB DI IRIDE", amministrazione.AMMINISTRAZIONE, amministrazione.CODICEAMMINISTRAZIONE);
                                    else
                                        warn = String.Format("LA PEC DELL'AMMINISTRAZIONE {0}, (CODICE {1}), NON E' VALORIZZATA, CONTROLLARE SU IRIDE, SE L'ANAGRAFICA CON PARTITA IVA {2} ABBIA IL RECAPITO EMAIL VALORIZZATO.", amministrazione.AMMINISTRAZIONE, amministrazione.CODICEAMMINISTRAZIONE, amministrazione.PARTITAIVA);

                                    this._protocolloLogs.Warn(warn);
                                }
                            }

                            mittenti.Add(mittentiDestinatari);
                            //}
                        }
                        break;
                }
                

                foreach (var anagrafe in datiProto.Destinatari.Anagrafe)
                {
                    if (mittenti.Where(x => x.CodiceFiscale == anagrafe.CODICEFISCALE || x.CodiceFiscale == anagrafe.PARTITAIVA).Count() > 0)
                        throw new Exception("SONO PRESENTI PIU' DESTINATARI CON LO STESSO CODICE FISCALE / PARTITA IVA");

                    if (mittenti.Count >= 100)
                    {
                        this._protocolloLogs.WarnFormat("Sono state conteggiati {0} destinatari mentre il limite massimo è di 99", mittenti.Count.ToString());
                        return;
                    }

                    var mittentiDestinatari = new MittenteDestinatarioIn();

                    mittentiDestinatari.Nome = String.Empty;
                    mittentiDestinatari.CodiceComuneResidenza = String.Empty;
                    mittentiDestinatari.DataNascita = String.Empty;
                    mittentiDestinatari.CodiceComuneNascita = String.Empty;
                    mittentiDestinatari.Nazionalita = String.Empty;
                    mittentiDestinatari.DataInvio_DataProt = String.Empty;
                    mittentiDestinatari.Spese_NProt = String.Empty;

                    var codiceFiscalePartitaIva = "";

                    if (!String.IsNullOrEmpty(anagrafe.CODICEFISCALE))
                        codiceFiscalePartitaIva = anagrafe.CODICEFISCALE;
                    else if (!String.IsNullOrEmpty(anagrafe.PARTITAIVA))
                        codiceFiscalePartitaIva = anagrafe.PARTITAIVA;

                    if (!String.IsNullOrEmpty(codiceFiscalePartitaIva))
                        mittentiDestinatari.CodiceFiscale = codiceFiscalePartitaIva;

                    /*if (!(String.IsNullOrEmpty(anagrafe.NOMINATIVO) && String.IsNullOrEmpty(anagrafe.NOME)))
                        mittentiDestinatari.CognomeNome = ((string)(anagrafe.NOMINATIVO + " " + anagrafe.NOME)).TrimEnd();*/

                    mittentiDestinatari.CognomeNome = anagrafe.NOMINATIVO;
                    mittentiDestinatari.Nome = anagrafe.NOME ?? "";

                    if (anagrafe.TIPOANAGRAFE == "F")
                    {
                        mittentiDestinatari.DataNascita = anagrafe.DATANASCITA.GetValueOrDefault(DateTime.MinValue) == DateTime.MinValue ? "" : anagrafe.DATANASCITA.Value.ToString("dd/MM/yyyy");
                        mittentiDestinatari.TipoPersona = Constants.PERSONA_FISICA_IRIDE;
                    }
                    else
                    {
                        mittentiDestinatari.DataNascita = anagrafe.DATANOMINATIVO.GetValueOrDefault(DateTime.MinValue) == DateTime.MinValue ? "" : anagrafe.DATANOMINATIVO.Value.ToString("dd/MM/yyyy");
                        mittentiDestinatari.TipoPersona = Constants.PERSONA_GIURIDICA_IRIDE;
                    }

                    mittentiDestinatari.Indirizzo = anagrafe.INDIRIZZO ?? "";

                    //Modificate per problemi con il Comune di Ravenna
                    mittentiDestinatari.CodiceComuneNascita = !String.IsNullOrEmpty(anagrafe.CodiceStatoEsteroNasc) ? (String.IsNullOrEmpty(vert.View) ? String.Empty : this.GetDecodeCodiceIstatStato(anagrafe.CodiceStatoEsteroNasc, vert)) : this.GetDecodeCodiceIstatStato(anagrafe.CodiceIstatComNasc, vert);
                    mittentiDestinatari.CodiceComuneResidenza = !String.IsNullOrEmpty(anagrafe.CodiceStatoEsteroRes) ? (String.IsNullOrEmpty(vert.View) ? String.Empty : this.GetDecodeCodiceIstatStato(anagrafe.CodiceStatoEsteroRes, vert)) : this.GetDecodeCodiceIstatStato(anagrafe.CodiceIstatComRes, vert);

                    if (!String.IsNullOrEmpty(anagrafe.CITTA))
                    {
                        mittentiDestinatari.Localita = anagrafe.CITTA;
                    }

                    if (!String.IsNullOrEmpty(anagrafe.Mezzo))
                        mittentiDestinatari.Mezzo = anagrafe.Mezzo;

                    mittentiDestinatari.Nazionalita = "100"; //N.B.: Il valore deve essere ricavato da una tabella Iride

                    if (!String.IsNullOrEmpty(anagrafe.PecProtocollazione))
                    {
                        var recapiti = new ArrayOfRecapitoIn();

                        if (anagrafe.PecProtocollazione.Equals(anagrafe.PecAnagrafica) || String.IsNullOrEmpty(anagrafe.PecAnagrafica))
                        {
                            var rec = new IrideRecapitiEmailBuilder(anagrafe.PecProtocollazione, vert.SwapEmail, vert.TipoRecapitoMail);

                            recapiti.AddRange(rec.Recapiti);
                        }
                        else
                        {
                            var pecs = new string[] { anagrafe.PecProtocollazione, anagrafe.PecAnagrafica };

                            var rec = new IrideRecapitiEmailBuilder(pecs, vert.SwapEmail, vert.TipoRecapitoMail);
                            recapiti.AddRange(rec.Recapiti);
                        }

                        mittentiDestinatari.Recapiti = recapiti;
                    }
                    else
                    {
                        if (!String.IsNullOrEmpty(vert.UrlPec))
                        {
                            var warn = "";
                            if (String.IsNullOrEmpty(codiceFiscalePartitaIva))
                                warn = String.Format("LA PEC E IL CODICE FISCALE/PARTITA IVA DELL'ANAGRAFICA {0}, (CODICE {1}), NON SONO VALORIZZATI, E' PROBABILE QUINDI CHE LA PEC NON SIA STATA INVIATA DAL SERVIZIO POSTE WEB DI IRIDE", anagrafe.NOMINATIVO, anagrafe.CODICEANAGRAFE);
                            else
                                warn = String.Format("LA PEC DELL'ANAGRAFICA {0}, (CODICE {1}), NON E' VALORIZZATA, CONTROLLARE SU IRIDE, SE L'ANAGRAFICA CON CODICE FISCALE {2} ABBIA IL RECAPITO EMAIL VALORIZZATO.", anagrafe.NOMINATIVO, anagrafe.CODICEANAGRAFE, codiceFiscalePartitaIva);

                            this._protocolloLogs.Warn(warn);
                        }
                    }

                    mittenti.Add(mittentiDestinatari);
                }

                if (mittenti.Count == 0 && datiProto.Flusso == ProtocolloConstants.COD_PARTENZA)
                {
                    var amministrazioniInterne = datiProto.Destinatari.Amministrazione.Where(x => !String.IsNullOrEmpty(x.PROT_UO) || !String.IsNullOrEmpty(x.PROT_UO)).ToList();
                    var primaAmministrazione = amministrazioniInterne.First();

                    protoIn.Origine = ProtocolloConstants.COD_INTERNO;

                    protoIn.InCaricoA = primaAmministrazione.PROT_UO;
                    protoIn.Ruolo = primaAmministrazione.PROT_RUOLO;
                }

                if (mittenti.Count > 0)
                {
                    var primi99 = new ArrayOfMittenteDestinatarioIn();
                    primi99.AddRange(mittenti.Take(99));

                    protoIn.MittentiDestinatari = primi99;
                }

            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE IL SETTAGGIO DEI DESTINATARI, {0}", ex.Message), ex);
            }
        }

        private string GetDecodeCodiceIstatStato(string codice, VerticalizzazioniConfiguration vert)
        {
            var retVal = string.Empty;

            if (string.IsNullOrEmpty(vert.View))
                return codice;
            else
            {
                if (string.IsNullOrEmpty(codice))
                    return codice;
                else
                {
                    var db = new DataBase(vert.ConnectionString, (ProviderType)Enum.Parse(typeof(ProviderType), vert.Provider, true));
                    db.Connection.Open();

                    try
                    {
                        var count = 0;
                        var query = "select COD_IRIDE from " + (string.IsNullOrEmpty(vert.Owner) ? string.Empty : vert.Owner + ".") + vert.View + " where COD_ISTAT = " + codice;
                        using (var cmd = db.CreateCommand(query))
                        {
                            using (var reader = cmd.ExecuteReader())
                            {
                                if (reader != null)
                                {
                                    while (reader.Read())
                                    {
                                        retVal = reader["COD_IRIDE"].ToString();
                                        count++;
                                    }
                                }
                            }
                        }

                        switch (count)
                        {
                            case 0:
                                throw new Exception("Il codice " + codice + " non è stato trovato.\r\n");
                            case 1:
                                return retVal;
                            default:
                                throw new Exception("Il codice " + codice + " è stato trovato " + count + " volte.\r\n");
                        }
                    }
                    catch (Exception ex)
                    {
                        throw ex;
                    }
                    finally
                    {
                        db.Connection.Close();
                    }
                }
            }
        }

        public override void CheckProtocolloLetto(string annoProtocollo, string numeroProtocollo, string idProtocollo, DatiProtocolloLettoResponseType pDatiProtocolloLetto)
        {
            if (String.IsNullOrEmpty(idProtocollo))
                base.CheckProtocolloLetto(annoProtocollo, numeroProtocollo, idProtocollo, pDatiProtocolloLetto);

        }

        public override AllegatoResponseType LeggiAllegato()
        {
            var allegato = this.LeggiAllegatoDaLeggiProtocollo();
            //_protocolloSerializer.Serialize(ProtocolloLogsConstants.AllegatoResponseFileName, allegato, ProtocolloValidation.TipiValidazione.XSD, "", true);
            return allegato;
        }

        public override List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            var vert = new VerticalizzazioniConfiguration(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloIride2>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

            DatiProtocolloLettoResponseType protocolloLetto = null;
            DocumentoOut documentoOut = null;
            try
            {
                this._protocolloLogs.DebugFormat("URL: {0}, PROXY ADDRESS: {1}", vert.Url, this.ProxyAddress);
                this._protocolloLogs.Debug("#### Inizio Richiesta di LeggiProtocollo ####");
                this.NumProtocollo = leggiProtocolloRequest.NumeroProtocollo;
                this.AnnoProtocollo = leggiProtocolloRequest.AnnoProtocollo;

                this._protocolloLogs.Debug("#### Chiamata a LeggiProtocollo ####");
                var protocolloService = new ProtocolloServiceWrapper(vert, this.Operatore, this.Ruolo, this.ProxyAddress, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory);
                documentoOut = protocolloService.LeggiProtocolloDocumento(leggiProtocolloRequest.IdProtocollo, leggiProtocolloRequest.AnnoProtocollo, leggiProtocolloRequest.NumeroProtocollo);
                this._protocolloLogs.Debug("##### Ricevuta risposta dal Protocollo IRIDE ####");

                this._protocolloLogs.DebugFormat("Inizio funzionalità di creazione dei dati del protocollo dopo la risposta del web service, Id Protocollo: {0}, Anno Protocollo: {1}, Numero Protocollo: {2}", leggiProtocolloRequest.IdProtocollo, leggiProtocolloRequest.AnnoProtocollo, leggiProtocolloRequest.NumeroProtocollo);
                protocolloLetto = this.CreaDatiProtocolloLetto(documentoOut);
                this._protocolloLogs.DebugFormat("Fine funzionalità di creazione dei dati del protocollo dopo la risposta del web service, Id Protocollo: {0}, Anno Protocollo: {1}, Numero Protocollo: {2}", leggiProtocolloRequest.IdProtocollo, leggiProtocolloRequest.AnnoProtocollo, leggiProtocolloRequest.NumeroProtocollo);

            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA LETTURA DEL PROTOCOLLO, {0}", ex.Message), ex);
            }

            return new List<DatiProtocolloLettoResponseType>() { protocolloLetto };
        }

        protected DatiProtocolloLettoResponseType CreaDatiProtocolloLetto(DocumentoOut response)
        {
            try
            {
                var protoLetto = new DatiProtocolloLettoResponseType();

                if (response.IdDocumento != 0)
                {
                    protoLetto.IdProtocollo = response.IdDocumento.ToString();
                    protoLetto.AnnoProtocollo = response.AnnoProtocollo.ToString();
                    protoLetto.NumeroProtocollo = response.NumeroProtocollo.ToString();
                    protoLetto.DataProtocollo = response.DataProtocollo.Value.ToString("dd/MM/yyyy");

                    if (!String.IsNullOrEmpty(response.Oggetto))
                        protoLetto.Oggetto = response.Oggetto;
                    if (!String.IsNullOrEmpty(response.Origine))
                        protoLetto.Origine = response.Origine;
                    if (!String.IsNullOrEmpty(response.Classifica))
                        protoLetto.Classifica = response.Classifica;
                    if (!String.IsNullOrEmpty(response.Classifica_Descrizione))
                        protoLetto.Classifica_Descrizione = $"{response.Classifica} {response.Classifica_Descrizione}";
                    if (!String.IsNullOrEmpty(response.TipoDocumento))
                        protoLetto.TipoDocumento = response.TipoDocumento;
                    if (!String.IsNullOrEmpty(response.TipoDocumento_Descrizione))
                        protoLetto.TipoDocumento_Descrizione = response.TipoDocumento_Descrizione;

                    if (!String.IsNullOrEmpty(response.MittenteInterno) && response.Origine != ProtocolloConstants.COD_ARRIVO)
                    {
                        protoLetto.MittentiDestinatari = new MittDestOutType[] { new MittDestOutType { IdSoggetto = response.MittenteInterno, CognomeNome = response.MittenteInterno_Descrizione } };
                    }

                    if (!String.IsNullOrEmpty(response.InCaricoA))
                        protoLetto.InCaricoA = response.InCaricoA;

                    if (!String.IsNullOrEmpty(response.InCaricoA_Descrizione))
                        protoLetto.InCaricoA_Descrizione = response.InCaricoA_Descrizione;

                    if (!String.IsNullOrEmpty(response.DocAllegati))
                        protoLetto.DocAllegati = response.DocAllegati;

                    if (!String.IsNullOrEmpty(response.NumeroPratica))
                        protoLetto.NumeroPratica = response.NumeroPratica;
                    if (!String.IsNullOrEmpty(response.AnnoNumeroPratica))
                        protoLetto.AnnoNumeroPratica = $"{response.AnnoNumeroPratica}/{response.Classifica}";
                    protoLetto.DataInserimento = response.DataInserimento.Value.ToString("dd/MM/yyyy");

                    if (protoLetto.Origine == ProtocolloConstants.COD_INTERNO)
                    {
                        protoLetto.MittentiDestinatari = new MittDestOutType[]
                        {
                            new MittDestOutType
                            {
                                IdSoggetto = response.MittenteInterno,
                                CognomeNome = response.MittenteInterno_Descrizione
                            }
                        };
                    }

                    //Sezione Mittenti/Destinatari
                    if (response.MittentiDestinatari != null)
                    {
                        protoLetto.MittentiDestinatari = new MittDestOutType[response.MittentiDestinatari.Count];

                        var iIndex = 0;
                        foreach (var pMittDestOut in response.MittentiDestinatari)
                        {
                            protoLetto.MittentiDestinatari[iIndex] = new MittDestOutType();
                            protoLetto.MittentiDestinatari[iIndex].IdSoggetto = pMittDestOut.IdSoggetto.ToString();
                            if (!String.IsNullOrEmpty(pMittDestOut.CognomeNome))
                            {
                                switch (protoLetto.Origine)
                                {
                                    case ProtocolloConstants.COD_ARRIVO:
                                        protoLetto.MittentiDestinatari[iIndex].CognomeNome = pMittDestOut.CognomeNome;
                                        break;
                                    case ProtocolloConstants.COD_PARTENZA:
                                        protoLetto.MittentiDestinatari[iIndex].CognomeNome = pMittDestOut.CognomeNome;
                                        break;
                                }
                            }

                            iIndex++;
                        }
                    }

                    if (response.Allegati != null && response.Allegati.Count > 0)
                    {
                        var allegatiDistinct = response.Allegati.GroupBy(x => x.IDBase).Select(x => x.Key);
                        protoLetto.Allegati = allegatiDistinct.Select(x =>
                        {
                            var a = response.Allegati.Where(z => z.IDBase == x).OrderByDescending(y => y.Versione).First();
                            var nomeFile = a.NomeAllegato;
                            if (String.IsNullOrEmpty(nomeFile))
                            {
                                nomeFile = a.Commento;
                                if (!String.IsNullOrEmpty(a.TipoFile))
                                {
                                    nomeFile = String.Format("{0}.{1}", Path.GetFileNameWithoutExtension(nomeFile), a.TipoFile);
                                    if (!String.IsNullOrEmpty(a.SottoEstensione))
                                        nomeFile = String.Format("{0}.{1}.{2}", Path.GetFileNameWithoutExtension(nomeFile), a.SottoEstensione, a.TipoFile);
                                }
                            }
                            return new AllegatoResponseType
                            {
                                Commento = nomeFile,
                                IDBase = a.IDBase.ToString(),
                                Serial = nomeFile,
                                Versione = a.Versione.ToString(),
                                Image = a.Image,
                                TipoFile = String.IsNullOrEmpty(a.TipoFile) ? "" : a.TipoFile,
                                ContentType = String.IsNullOrEmpty(a.TipoFile) ? "" : new OggettiMgr(this.DatiProtocollo.Db).GetContentType(nomeFile)
                            };
                        }).ToArray();
                    }
                }
                else
                {
                    throw new Exception(String.Format("ID DOCUMENTO UGUALE A 0, MESSAGGIO: {0}, ERRORE: {1}", response.Messaggio, response.Errore));
                }

                return protoLetto;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE IL RECUPERO DEI VALORI DAL WEB SERVICE DOPO LA LETTURA DEL PROTOCOLLO, {0}", ex.Message), ex);
            }
        }
    }
}

