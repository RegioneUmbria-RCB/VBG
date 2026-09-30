using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using SIGePro.Manager.VerticalizzazioniBase;
using System.Data;
using System.Text.RegularExpressions;
using VBG.Backend.Protocollo.AppLogic.Core.JIride;
using VBG.Backend.Protocollo.AppLogic.Core.JIride.Fascicolazione;
using VBG.Backend.Protocollo.AppLogic.Core.JIride.Fascicolazione.Lettura;
using VBG.Backend.Protocollo.AppLogic.Core.JIride.Metadati;
using VBG.Backend.Protocollo.AppLogic.Core.JIride.PEC;
using VBG.Backend.Protocollo.AppLogic.Core.JIride.Protocollazione;
using VBG.Backend.Protocollo.AppLogic.Core.JIride.Protocollazione.CreaCopie;
using VBG.Backend.Protocollo.AppLogic.Core.JIride.Protocollazione.LeggiProtocollo;
using VBG.Backend.Protocollo.AppLogic.Core.JIride.Protocollazione.LeggiProtocollo.Storico;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Exceptions;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.Verticalizzazioni.Core;
using VBG.Backend.Protocollo.Verticalizzazioni.Shared;

namespace VBG.Backend.Protocollo.AppLogic.Core
{
    /// <summary>
    /// Descrizione di riepilogo per PROTOCOLLO_IRIDE.
    /// </summary>
    public class PROTOCOLLO_JIRIDE : ProtocolloBase, IProtocolloStorico
    {
        public static class Constants
        {
            public const string PERSONA_FISICA_IRIDE = "FI";
            public const string PERSONA_GIURIDICA_IRIDE = "GI";
        }
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly IBindingFactory _bindingFactory;
        private ProtocollazioneServiceWrapper _protocolloService;
        private FascicolazioneServiceWrapper _fascicolazione;

        private ParametriRegoleInfo _vert;

        public PROTOCOLLO_JIRIDE(IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._bindingFactory = bindingFactory;
        }

        public override void InizializzaProtocolloBase(ResolveDatiProtocollazioneService datiProtocolloService)
        {
            base.InizializzaProtocolloBase(datiProtocolloService);
            base._protocolloSerializer = new JIrideSerializer(this._protocolloLogs, this._protocolloValidation);
        }

        public override DatiProtocolloFascicolatoResponseType IsFascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            this._protocolloLogs.Debug("Inizio IsFascicolato Jiride");
            this._vert = new ParametriRegoleInfo(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloJIride>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));

            this._protocolloService = new ProtocollazioneServiceWrapper(this._vert.Url, this._protocolloLogs, this._protocolloSerializer, this._vert.CodiceAmministrazione, this._vert.Aoo, this._bindingFactory);
            this._fascicolazione = new FascicolazioneServiceWrapper(this._vert.UrlFasc, this._protocolloLogs, this._protocolloSerializer, this._vert.CodiceAmministrazione, this._vert.Aoo, this._bindingFactory);

            return this.Fascicolato(idProtocollo, annoProtocollo, numeroProtocollo);
        }

        private FascicoloOutXml FascicoloEsistente(Shared.Data.Fascicolo fascicolo)
        {
            if (String.IsNullOrEmpty(fascicolo.NumeroFascicolo) || (fascicolo.AnnoFascicolo == 0))
            {
                return new FascicoloOutXml();
            }
            else
            {
                var verticalizzazione = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloJIride>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune);
                var leggiFascicoloRequest = new LeggiFascicoloRequestBuilder(verticalizzazione, fascicolo, null, this.Operatore, this.Ruolo).Build();
                return new FascicolazioneService(this._protocolloLogs, this._protocolloSerializer, this._bindingFactory).LeggiFascicolo(leggiFascicoloRequest);
            }
        }

        public override DatiFascicoloResponseType Fascicola(Shared.Data.Fascicolo fascicolo)
        {
            try
            {
                if (fascicolo == null)
                {
                    return null;
                }

                this._vert = new ParametriRegoleInfo(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloJIride>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
                this._protocolloService = new ProtocollazioneServiceWrapper(this._vert.Url, this._protocolloLogs, this._protocolloSerializer, this._vert.CodiceAmministrazione, this._vert.Aoo, this._bindingFactory);
                this._fascicolazione = new FascicolazioneServiceWrapper(this._vert.UrlFasc, this._protocolloLogs, this._protocolloSerializer, this._vert.CodiceAmministrazione, this._vert.Aoo, this._bindingFactory);

                var retVal = this.FascicolaProtocollo(fascicolo);
                return retVal;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA FASCICOLAZIONE, {0}", ex.Message), ex);
            }
        }

        private DatiFascicoloResponseType FascicolaProtocollo(Shared.Data.Fascicolo fascicolo)
        {
            var verticalizzazione = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloJIride>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune);

            var fascicoloOut = this.FascicoloEsistente(fascicolo);
            int iFascicoloId;
            int iDocumentoId;

            var pDatiFascicolo = new DatiFascicoloResponseType();

            this._protocolloLogs.InfoFormat("FASCICOLAZIONE AMBITO: {0}", this.DatiProtocollo.TipoAmbito.ToString());
            if (this.DatiProtocollo.TipoAmbito == AmbitoProtocollazioneEnum.NESSUNO)
            {
                this._protocolloLogs.DebugFormat($"Lettura del protocollo durante la fase di fascicolazione, id protocollo: {this.IdProtocollo}, numero protocollo: {this.NumProtocollo}, anno: {this.AnnoProtocollo}");
                var leggiProtocolloRequest = new LeggiProtocolloBuilder(verticalizzazione, this.IdProtocollo, this.NumProtocollo, this.AnnoProtocollo, this.Operatore, this.Ruolo).Build();
                leggiProtocolloRequest.UsaLeggiDocumentoPlus = true;
                var protocolloLetto = new LeggiProtocolloService(this._protocolloLogs, this._protocolloSerializer, this._bindingFactory).Leggi(leggiProtocolloRequest);
                this._protocolloLogs.DebugFormat("Fine lettura del protocollo durante la fase di fascicolazione, id documento: {0}", protocolloLetto.DocumentoOut.IdDocumento.ToString());

                if (protocolloLetto.DocumentoOut.IdDocumento != 0)
                {
                    iDocumentoId = protocolloLetto.DocumentoOut.IdDocumento;
                }
                else
                {
                    throw new Exception($"ERRORE RESTITUITO DAL WEB SERVICE DURANTE LA LETTURA DEL PROTOCOLLO, MESSAGGIO: {protocolloLetto.DocumentoOut.Messaggio}, ERRORE: {protocolloLetto.DocumentoOut.Errore}");
                }

                this._protocolloLogs.DebugFormat("Id fascicolo: {0}", fascicoloOut.Id);

                if (fascicoloOut.Id != 0)
                {
                    iFascicoloId = fascicoloOut.Id;
                }
                else
                {
                    //Prima sia se il fascicolo non era passato che se non era esistente veniva sempre creato
                    //il nuovo fascicolo. Ora viene creato solo se non è passato; se non esiste si comporta come il Cambio Fascicolo
                    if (String.IsNullOrEmpty(fascicolo.NumeroFascicolo) || (fascicolo.AnnoFascicolo == 0))
                    {
                        //Il fascicolo non è passato quindi viene creato
                        var fascicoloNuovoRequest = new FascicoloNuovoRequestBuilder(verticalizzazione, fascicolo, this.Operatore, this.Ruolo).Build();
                        var fascicoloNuovoResponse = new FascicolazioneService(this._protocolloLogs, this._protocolloSerializer, this._bindingFactory).FascicoloNuovo(fascicoloNuovoRequest);
                        iFascicoloId = fascicoloNuovoResponse.Id;
                    }
                    else
                    {
                        throw new Exception("IL FASCICOLO SELEZIONATO NON ESISTE!!");
                    }
                }
                this._fascicolazione.FascicolaDocumento(iFascicoloId, iDocumentoId, this._vert.AggiornaClassifica, this.Operatore.ToUpper(), this.Ruolo, "");

                if (fascicoloOut.Id != 0)
                {
                    //Il fascicolo è esistente
                    pDatiFascicolo.AnnoFascicolo = fascicoloOut.Anno.ToString();
                    pDatiFascicolo.DataFascicolo = fascicoloOut.Data.Value.ToString("dd/MM/yyyy");
                    pDatiFascicolo.NumeroFascicolo = fascicoloOut.Numero;
                }
                else
                {
                    var leggiFascicoloRequest = new LeggiFascicoloRequestBuilder(verticalizzazione, new Shared.Data.Fascicolo(), iFascicoloId, this.Operatore, this.Ruolo).Build();
                    var leggiFascicoloResponse = new FascicolazioneService(this._protocolloLogs, this._protocolloSerializer, this._bindingFactory).LeggiFascicolo(leggiFascicoloRequest);

                    pDatiFascicolo.AnnoFascicolo = leggiFascicoloResponse.Anno.ToString();
                    pDatiFascicolo.DataFascicolo = leggiFascicoloResponse.Data.Value.ToString("dd/MM/yyyy");
                    pDatiFascicolo.NumeroFascicolo = leggiFascicoloResponse.Numero;
                }
            }

            //Verifico se si intende fascicolare una pratica
            if (this.DatiProtocollo.TipoAmbito == AmbitoProtocollazioneEnum.DA_ISTANZA)
            {
                int iDocumentoIdIstanza;

                this._protocolloLogs.DebugFormat("Lettura del protocollo durante la fase di fascicolazione, id protocollo: {0}, numero protocollo: {1}, data protocollo: {2}", this.DatiProtocollo.Istanza.FKIDPROTOCOLLO, this.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value.Year.ToString(), this.DatiProtocollo.Istanza.NUMEROPROTOCOLLO);
                var leggiProtocolloRequest = new LeggiProtocolloBuilder(verticalizzazione, this.DatiProtocollo.Istanza.FKIDPROTOCOLLO, this.DatiProtocollo.Istanza.NUMEROPROTOCOLLO, this.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value.Year.ToString(), this.Operatore, this.Ruolo).Build();
                leggiProtocolloRequest.UsaLeggiDocumentoPlus = true;
                var protocolloLetto = new LeggiProtocolloService(this._protocolloLogs, this._protocolloSerializer, this._bindingFactory).Leggi(leggiProtocolloRequest);
                this._protocolloLogs.DebugFormat($"Fine lettura del protocollo durante la fase di fascicolazione, id documento: {protocolloLetto.DocumentoOut.IdDocumento}");

                if (protocolloLetto.DocumentoOut.IdDocumento != 0)
                    iDocumentoId = protocolloLetto.DocumentoOut.IdDocumento;
                else
                    throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE DURANTE LA LETTURA DEL PROTOCOLLO, MESSAGGIO: {0}, ERRORE: {1}", protocolloLetto.DocumentoOut.Messaggio, protocolloLetto.DocumentoOut.Errore));

                //Usata per evitare di fascicolare ancora il movimento di avvio
                iDocumentoIdIstanza = iDocumentoId;

                if (fascicoloOut.Id != 0)
                    iFascicoloId = fascicoloOut.Id;
                else
                {
                    //Prima sia se il fascicolo non era passato che se non era esistente veniva sempre creato
                    //il nuovo fascicolo. Ora viene creato solo se non è passato; se non esiste si comporta come il Cambio Fascicolo
                    if (String.IsNullOrEmpty(fascicolo.NumeroFascicolo) || (fascicolo.AnnoFascicolo == 0))
                    {
                        //Il fascicolo non è passato quindi viene creato
                        var fascicoloNuovoRequest = new FascicoloNuovoRequestBuilder(verticalizzazione, fascicolo, this.Operatore, this.Ruolo).Build();
                        var fascicoloNuovoResponse = new FascicolazioneService(this._protocolloLogs, this._protocolloSerializer, this._bindingFactory).FascicoloNuovo(fascicoloNuovoRequest);
                        iFascicoloId = fascicoloNuovoResponse.Id;
                    }
                    else
                    {
                        throw new Exception("IL FASCICOLO SELEZIONATO NON ESISTE!!");
                    }
                }

                this._fascicolazione.FascicolaDocumento(iFascicoloId, iDocumentoId, this._vert.AggiornaClassifica, this.Operatore.ToUpper(), this.Ruolo, this.DatiProtocollo.Istanza.FKIDPROTOCOLLO);

                //Fascicolo i moviemnti della pratica se protocollati
                var pMovimentiMgr = new MovimentiMgr(this.DatiProtocollo.Db);
                var _Movimento = new Movimenti
                {
                    IDCOMUNE = this.DatiProtocollo.IdComune,
                    CODICEISTANZA = this.DatiProtocollo.CodiceIstanza
                };

                foreach (var elem in pMovimentiMgr.GetList(_Movimento))
                {
                    this._protocolloLogs.InfoFormat("AGGIORNAMENTO DEI FASCICOLI DEI MOVIMENTI, ISTANZA: {0} MOVIMENTO: {1}, NUMERO PROTOCOLLO: {2}, DATAPROTOCOLLO: {3}", elem.CODICEISTANZA, elem.CODICEMOVIMENTO, elem.FKIDPROTOCOLLO, elem.DATAPROTOCOLLO.HasValue ? elem.DATAPROTOCOLLO.Value.ToString("dd/MM/yyyy") : "");
                    if (!string.IsNullOrEmpty(elem.FKIDPROTOCOLLO) || (!string.IsNullOrEmpty(elem.NUMEROPROTOCOLLO) && elem.DATAPROTOCOLLO.HasValue))
                    {
                        try
                        {
                            var leggiProtMovimentoRequest = new LeggiProtocolloBuilder(verticalizzazione, elem.FKIDPROTOCOLLO, elem.NUMEROPROTOCOLLO, elem.DATAPROTOCOLLO.GetValueOrDefault(DateTime.MinValue).Year.ToString(), this.Operatore, this.Ruolo).Build();
                            leggiProtMovimentoRequest.UsaLeggiDocumentoPlus = true;
                            var protMovimentoLetto = new LeggiProtocolloService(this._protocolloLogs, this._protocolloSerializer, this._bindingFactory).Leggi(leggiProtMovimentoRequest);

                            if (protMovimentoLetto.DocumentoOut.IdDocumento != 0)
                            {
                                iDocumentoId = protMovimentoLetto.DocumentoOut.IdDocumento;
                            }
                            else
                            {
                                continue;
                            }
                        }
                        catch (Exception)
                        {
                            continue;
                        }

                        if (iDocumentoIdIstanza != iDocumentoId)
                        {
                            try
                            {
                                this._fascicolazione.FascicolaDocumento(iFascicoloId, iDocumentoId, this._vert.AggiornaClassifica, this.Operatore.ToUpper(), this.Ruolo, "");
                            }
                            catch (Exception) { }
                        }
                    }
                }

                if (fascicoloOut.Id != 0)
                {
                    //Il fascicolo è esistente
                    pDatiFascicolo.AnnoFascicolo = fascicoloOut.Anno.ToString();
                    pDatiFascicolo.DataFascicolo = fascicoloOut.Data.Value.ToString("dd/MM/yyyy");
                    pDatiFascicolo.NumeroFascicolo = fascicoloOut.Numero;
                }
                else
                {
                    var leggiFascicoloRequest = new LeggiFascicoloRequestBuilder(verticalizzazione, new Shared.Data.Fascicolo(), iFascicoloId, this.Operatore, this.Ruolo).Build();
                    var leggiFascicoloResponse = new FascicolazioneService(this._protocolloLogs, this._protocolloSerializer, this._bindingFactory).LeggiFascicolo(leggiFascicoloRequest);

                    pDatiFascicolo.AnnoFascicolo = leggiFascicoloResponse.Anno.ToString();
                    pDatiFascicolo.DataFascicolo = leggiFascicoloResponse.Data.Value.ToString("dd/MM/yyyy");
                    pDatiFascicolo.NumeroFascicolo = leggiFascicoloResponse.Numero;
                }
            }

            //Verifico se si intende fascicolare un movimento
            if (this.DatiProtocollo.TipoAmbito == AmbitoProtocollazioneEnum.DA_MOVIMENTO)
            {
                this._protocolloLogs.DebugFormat("FascicoloOut.Id={0}", fascicoloOut.Id);

                if (fascicoloOut.Id != 0)
                {
                    iFascicoloId = fascicoloOut.Id;
                    this._protocolloLogs.Debug("Chiamata a LeggiProtocolloDocumento");
                    var leggiProtocolloRequest = new LeggiProtocolloBuilder(verticalizzazione, this.DatiProtocollo.Movimento.FKIDPROTOCOLLO, this.DatiProtocollo.Movimento.NUMEROPROTOCOLLO, this.DatiProtocollo.Movimento.DATAPROTOCOLLO.GetValueOrDefault(DateTime.MinValue).Year.ToString(), this.Operatore, this.Ruolo).Build();
                    leggiProtocolloRequest.UsaLeggiDocumentoPlus = true;
                    var protocolloLetto = new LeggiProtocolloService(this._protocolloLogs, this._protocolloSerializer, this._bindingFactory).Leggi(leggiProtocolloRequest);
                    this._protocolloLogs.DebugFormat($"Fine chiamata a LeggiProtocolloDocumento, id {protocolloLetto.DocumentoOut.IdDocumento}");
                    if (protocolloLetto.DocumentoOut.IdDocumento == 0)
                    {
                        throw new Exception($"ERRORE GENERATO DAL WEB METHOD LEGGIPROTOCOLLO DURANTE LA FASCICOLAZIONE DI UN MOVIMENTO. MESSAGGIO DI ERRORE: {protocolloLetto.DocumentoOut.Messaggio}, ERRORE: {protocolloLetto.DocumentoOut.Errore}");
                    }

                    iDocumentoId = protocolloLetto.DocumentoOut.IdDocumento;

                    this._protocolloLogs.DebugFormat("Chiamata a FascicolaDocumento, IdFascicolo: {0}, IdDocumento: {1}, AggiornaClassifica: {2}, Operatore: {3}, Ruolo: {4}, CodiceAmministrazione: {5}, CodiceAoo: {6}", iFascicoloId, iDocumentoId, this._vert.AggiornaClassifica, this.Operatore.ToUpper(), this.Ruolo, this._vert.CodiceAmministrazione, String.Empty);
                    var esito = this._fascicolazione.FascicolaDocumento(iFascicoloId, iDocumentoId, this._vert.AggiornaClassifica, this.Operatore.ToUpper(), this.Ruolo, "");
                    if (!esito.Esito)
                    {
                        throw new ProtocolloException("Errore generato dal web method FascicolaDocumento durante la fascicolazione di un movimento.(id fascicolo: " + iFascicoloId + " id documento: " + iDocumentoId + ". Messaggio di errore: " + esito.Messaggio + ". " + esito.Errore + "\r\n");
                    }

                    this._protocolloLogs.Debug("Documento Fascicolato");

                    pDatiFascicolo.AnnoFascicolo = fascicoloOut.Anno.ToString();
                    pDatiFascicolo.DataFascicolo = fascicoloOut.Data.Value.ToString("dd/MM/yyyy");
                    pDatiFascicolo.NumeroFascicolo = fascicoloOut.Numero;
                }
            }

            return pDatiFascicolo;
        }

        public override DatiFascicoloResponseType CambiaFascicolo(Shared.Data.Fascicolo fascicolo)
        {
            try
            {
                this._protocolloLogs.InfoFormat("RICHIESTA DI CAMBIO FASCICOLO, NUMERO: {0}, CLASSIFICA: {1}, ANNO: {2}", fascicolo.NumeroFascicolo, fascicolo.Classifica, fascicolo.AnnoFascicolo);
                this._vert = new ParametriRegoleInfo(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloJIride>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
                this._protocolloService = new ProtocollazioneServiceWrapper(this._vert.Url, this._protocolloLogs, this._protocolloSerializer, this._vert.CodiceAmministrazione, this._vert.Aoo, this._bindingFactory);
                this._fascicolazione = new FascicolazioneServiceWrapper(this._vert.UrlFasc, this._protocolloLogs, this._protocolloSerializer, this._vert.CodiceAmministrazione, this._vert.Aoo, this._bindingFactory);

                bool isCopia = this._protocolloService.IsCopia(this.DatiProtocollo.Istanza.FKIDPROTOCOLLO);

                if (isCopia)
                {
                    throw new Exception("NON E' POSSIBILE MODIFICARE IL FASCICOLO DI UNA COPIA");
                }

                if (String.IsNullOrEmpty(fascicolo.NumeroFascicolo))
                {
                    var retVal = this.FascicolaProtocollo(fascicolo);
                    this._protocolloLogs.InfoFormat("CAMBIO FASCICOLO AVVENUTO CORRETTAMENTE, NUMERO: {0}, CLASSIFICA: {1}, ANNO: {2}", fascicolo.NumeroFascicolo, fascicolo.Classifica, fascicolo.AnnoFascicolo);
                    return retVal;
                }
                else
                {
                    var fascicoloOut = this.FascicoloEsistente(fascicolo);
                    if (fascicoloOut.Id != 0)
                    {
                        var retVal = this.FascicolaProtocollo(fascicolo);
                        this._protocolloLogs.InfoFormat("CAMBIO FASCICOLO AVVENUTO CORRETTAMENTE, NUMERO: {0}, CLASSIFICA: {1}, ANNO: {2}", fascicolo.NumeroFascicolo, fascicolo.Classifica, fascicolo.AnnoFascicolo);
                        return retVal;
                    }
                    else
                    {
                        throw new ProtocolloException($"IL FASCICOLO {fascicolo.NumeroFascicolo} SELEZIONATO NON ESISTE!!");
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE IL CAMBIAMENTO DI UN FASCICOLO, {0}", ex.Message), ex);
            }
        }

        public override EtichetteResponseType StampaEtichette(string idProtocollo, DateTime? dataProtocollo, string numeroProtocollo, int numeroCopie, string stampante)
        {
            var datiEtichette = new EtichetteResponseType();

            try
            {
                var verticalizzazione = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloJIride>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune);

                this._vert = new ParametriRegoleInfo(this._protocolloLogs, verticalizzazione);
                this._protocolloService = new ProtocollazioneServiceWrapper(this._vert.Url, this._protocolloLogs, this._protocolloSerializer, this._vert.CodiceAmministrazione, this._vert.Aoo, this._bindingFactory);

                this.DataProtocollo = dataProtocollo;

                var leggiProtocolloRequest = new LeggiProtocolloBuilder(verticalizzazione, idProtocollo, numeroProtocollo, this.AnnoProtocollo, this.Operatore, this.Ruolo).Build();
                leggiProtocolloRequest.UsaLeggiDocumentoPlus = true;
                var protocolloLetto = new LeggiProtocolloService(this._protocolloLogs, this._protocolloSerializer, this._bindingFactory).Leggi(leggiProtocolloRequest);

                if (protocolloLetto.DocumentoOut.IdDocumento != 0)
                {
                    datiEtichette.IdEtichetta = protocolloLetto.DocumentoOut.IdDocumento.ToString().PadLeft(8, '0');
                }
                else
                {
                    throw new Exception($"ERRORE GENERATO DAL WEB METHOD LEGGIPROTOCOLLO. MESSAGGIO: {protocolloLetto.DocumentoOut.Messaggio}, ERRORE: {protocolloLetto.DocumentoOut.Errore}");
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE LA STAMPA DI UN'ETICHETTA, {ex.Message}", ex);
            }

            return datiEtichette;
        }

        public override DatiProtocolloResponseType CreaCopie()
        {
            var idProtocollo = this.DatiProtocollo.Istanza.FKIDPROTOCOLLO;
            var numeroProtocollo = this.DatiProtocollo.Istanza.NUMEROPROTOCOLLO;
            var dataProtocollo = this.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value;

            this._protocolloLogs.Debug($"Inizio metodo CreaCopie con idProtocollo: {idProtocollo}, annoProtocollo: {dataProtocollo.Year}, numeroProtocollo: {numeroProtocollo}");

            var verticalizzazione = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloJIride>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune);
            if (verticalizzazione.DisabilitaCreacopie == "1")
            {
                this._protocolloLogs.Debug("Funzionalità CreaCopie disabilitata tramite parametro DISABILITA_CREACOPIE della verticalizzazione PROTOCOLLO_IRIDE");
                return null;
            }

            var leggiProtocolloRequest = new LeggiProtocolloBuilder(verticalizzazione, idProtocollo, numeroProtocollo, dataProtocollo.Year.ToString(), this.Operatore, this.Ruolo).Build();
            var protocolloLetto = new LeggiProtocolloService(this._protocolloLogs, this._protocolloSerializer, this._bindingFactory).Leggi(leggiProtocolloRequest);
            if (protocolloLetto.DocumentoOut.IdDocumento == 0)
            {
                throw new Exception($"ERRORE DURANTE LA GENERAZIONE DELLA COPIA DEL DOCUMENTO SORGENTE ID: {idProtocollo}, PROTOCOLLO NUMERO: {numeroProtocollo}, ANNO: {dataProtocollo.Year}: DOCUMENTO SORGENTE NON TROVATO, MESSAGGIO: {protocolloLetto.DocumentoOut.Messaggio}, ERRORE: {protocolloLetto.DocumentoOut.Errore}");
            }

            this._protocolloLogs.Debug("Inizio creazione dei parametri della request");
            var creaCopieRequest = new CreaCopieRequestBuilder(verticalizzazione, this.Operatore, this.Ruolo, this.Uo, this.ProxyAddress, protocolloLetto, this._protocolloLogs).Build();
            this._protocolloLogs.Debug("Fine creazione dei parametri della request");

            this._protocolloLogs.Debug("Inizio chiamata al web service per la creazione della copia");
            new CreaCopieService(this._protocolloLogs, this._protocolloSerializer, this._bindingFactory).GeneraCopia(creaCopieRequest);
            this._protocolloLogs.Debug("Fine chiamata al web service per la creazione della copia");

            var adapter = new ProtocollazioneOutAdapter(base.ModificaNumero, base.AggiungiAnno, base._protocolloLogs, this._vert.MessaggioProtoOk);
            var retVal = adapter.Adatta(protocolloLetto.DocumentoOut);
            this._protocolloLogs.Debug("Fine metodo CreaCopie");

            return retVal;
        }

        public override DatiProtocolloResponseType MettiAllaFirma(DatiProtocolloIn proto)
        {
            this._vert = new ParametriRegoleInfo(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloJIride>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
            this._protocolloService = new ProtocollazioneServiceWrapper(this._vert.Url, this._protocolloLogs, this._protocolloSerializer, this._vert.CodiceAmministrazione, this._vert.Aoo, this._bindingFactory);
            var protoIn = this.CreaProtocolloIn(proto);

            this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.InserisciDocumentoRequestFileName, protoIn);
            this._protocolloLogs.InfoFormat("Chiamata a web method InserisciDocumento da metti alla firma, request file: {0}", ProtocolloLogsConstants.InserisciDocumentoRequestFileName);
            var docOut = this._protocolloService.InserisciDocumento(protoIn);

            this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.InserisciDocumentoResponseFileName, docOut);

            if (docOut.IdDocumento != 0 && String.IsNullOrEmpty(docOut.Errore))
            {
                this._protocolloLogs.Info("MESSA ALLA FIRMA AVVENUTA CON SUCCESSO");

                var adapterResponse = new ProtocollazioneOutAdapter(base.ModificaNumero, base.AggiungiAnno, base._protocolloLogs, this._vert.MessaggioProtoOk);
                return adapterResponse.Adatta(docOut);
            }
            else
            {
                throw new Exception(String.Format("METODO INSERISCIDOCUMENTO. MESSAGGIO DI ERRORE: {0}. ERRORE: {1}", docOut.Messaggio, docOut.Errore));
            }
        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn proto)
        {
            this._vert = new ParametriRegoleInfo(this._protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloJIride>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
            this._protocolloService = new ProtocollazioneServiceWrapper(this._vert.Url, this._protocolloLogs, this._protocolloSerializer, this._vert.CodiceAmministrazione, this._vert.Aoo, this._bindingFactory);
            var protoIn = this.CreaProtocolloIn(proto);

            var protoOut = this._protocolloService.InserisciProtocollo(protoIn);

            this._protocolloLogs.InfoFormat("PROTOCOLLAZIONE AVVENUTA CON SUCCESSO, FLUSSO: {0}", protoIn.Origine);

            if ((protoIn.Origine == ProtocolloConstants.COD_PARTENZA || protoIn.Origine == ProtocolloConstants.COD_INTERNO) && (this.Anagrafiche != null && this.Anagrafiche.Count > 1))
            {
                var verticalizzazione = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloJIride>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune);
                var mittente = proto.Mittenti.Amministrazione.First();
                var destinatari = proto.Destinatari.Amministrazione;
                if (proto.Destinatari.Anagrafe.Count == 0 &&
                    proto.Destinatari.Amministrazione.Where(x => String.IsNullOrEmpty(x.PROT_UO) && String.IsNullOrEmpty(x.PROT_RUOLO)).Count() == 0)
                {
                    destinatari = destinatari.Skip(1).ToList();
                }

                var creaCopieRequest = new CreaCopiePerAmministrazioniInterneRequestBuilder(verticalizzazione, protoOut, mittente, destinatari, this.Operatore).Build();
                new CreaCopieService(this._protocolloLogs, this._protocolloSerializer, this._bindingFactory).CreaCopiePerAmministrazioniInterne(creaCopieRequest);
            }
            this._protocolloLogs.InfoFormat("URL PEC PRESENTE: {0}, FLUSSO: {1}", !String.IsNullOrEmpty(this._vert.UrlPec), protoIn.Origine);
            if (!String.IsNullOrEmpty(this._vert.UrlPec) && (protoIn.Origine == ProtocolloConstants.COD_PARTENZA))
            {
                this.InviaPec(proto, protoOut, protoIn.Ruolo);
            }

            var adapterResponse = new ProtocollazioneOutAdapter(base.ModificaNumero, base.AggiungiAnno, base._protocolloLogs, this._vert.MessaggioProtoOk);
            return adapterResponse.Adatta(protoOut);
        }

        private void InviaPec(DatiProtocolloIn protoIn, ProtocolloOutXml protoOut, string ruolo)
        {
            this._protocolloLogs.Info("INIZIO FUNZIONALITA' DI INVIO PEC");

            var mezzoInvio = protoIn.RecuperaMetadati().FirstOrDefault(x => x.Chiave == MetadatiConstants.MezzoDefault)?.Valore;

            try
            {
                var verticalizzazione = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloJIride>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune);

                if (String.IsNullOrEmpty(protoIn.Oggetto))
                {
                    this._protocolloLogs.WarnFormat("INVIO PEC non eseguito a causa dell'assenza dell'oggetto della mail, controllare l'oggetto di default in configurazione, protocollo numero: {0}, data: {1}", protoOut.NumeroProtocollo.ToString(), protoOut.DataProtocollo.Value.ToString("dd/MM/yyyy"));
                }
                else if (String.IsNullOrEmpty(this._vert.MittenteMailPec))
                {
                    this._protocolloLogs.WarnFormat("INVIO PEC non eseguito a causa dell'assenza del mittente della mail, controllare il parametro MITTENTE_MAIL_PEC della verticalizzazione PROTOCOLLO_IRIDE, protocollo numero: {0}, data: {1}", protoOut.NumeroProtocollo.ToString(), protoOut.DataProtocollo.Value.ToString("dd/MM/yyyy"));
                }
                else if (String.IsNullOrEmpty(this._vert.UrlPec))
                {
                    this._protocolloLogs.WarnFormat("INVIO PEC non eseguito a causa dell'assenza dell'url (end point) del servizio di invio mail PEC di Iride, controllare il parametro URL_PEC della verticalizzazione PROTOCOLLO_IRIDE, protocollo numero: {0}, data: {1}", protoOut.NumeroProtocollo.ToString(), protoOut.DataProtocollo.Value.ToString("dd/MM/yyyy"));
                }
                else
                {
                    this.Ruolo = ruolo;
                    IEnumerable<string> seriali;

                    if (protoOut.Allegati == null)
                    {
                        var leggiProtocolloRequest = new LeggiProtocolloBuilder(verticalizzazione, protoOut.IdDocumento.ToString(), "", "", this.Operatore, this.Ruolo).Build();
                        var protocolloLetto = new LeggiProtocolloService(this._protocolloLogs, this._protocolloSerializer, this._bindingFactory).Leggi(leggiProtocolloRequest);
                        seriali = protocolloLetto.DocumentoOut.Allegati.Select(x => x.Serial.ToString());
                    }
                    else
                    {
                        seriali = protoOut.Allegati.Select(x => x.Serial.ToString());
                    }

                    if (this._vert.WarningPec)
                    {
                        var anagraficheNoPEC = this.Anagrafiche.Where(x => String.IsNullOrEmpty(x.Pec)).Select(x => x.NomeCognome);
                        if (anagraficheNoPEC.Any())
                        {
                            this._protocolloLogs.WarnFormat("I SEGUENTI DESTINATARI NON PRESENTANO UN INDIRIZZO PEC, AI QUALI NON E' STATA QUINDI INVIATA: {0}", String.Join(", ", anagraficheNoPEC));
                        }

                        var anagraficheNoMezzo = this.Anagrafiche.Where(x => (mezzoInvio ?? x.MezzoInvio) != this._vert.MezzoPec).Select(x => x.NomeCognome);

                        if (anagraficheNoMezzo.Any())
                        {
                            this._protocolloLogs.WarnFormat("I SEGUENTI DESTINATARI NON PRESENTANO IL MEZZO DI INVIO PEC, AI QUALI NON E' STATA QUINDI INVIATA: {0}", String.Join(", ", anagraficheNoMezzo));
                        }
                    }

                    var listaPec = this.Anagrafiche
                                            .Where(y => !String.IsNullOrEmpty(y.Pec) &&
                                                        (mezzoInvio ?? y.MezzoInvio) == this._vert.MezzoPec).
                                                        GroupBy(x => x.Pec.ToUpperInvariant()).
                                                        Select(x => x.Key).ToArray();

                    this._protocolloLogs.InfoFormat("NUMERO DESTINATARI PER INVIO PEC: {0}", listaPec.Length);

                    if (listaPec.Length > 0)
                    {
                        var adapter = new PecAdapter(this._protocolloSerializer);

                        var oggetto = protoIn.Oggetto;
                        if (this._vert.UsaRifProtocolloOggettoPec)
                        {
                            oggetto = $"Pr. Num.: {protoOut.NumeroProtocollo}, Data Pr.: {protoOut.DataProtocollo.Value:dd/MM/yyyy} - {protoIn.Oggetto.Replace("\r\n", " ")}";
                        }

                        var requestXml = adapter.Adatta(listaPec, seriali, protoOut.IdDocumento.ToString(), Regex.Replace(oggetto, @"\r\n?|\n", "-"), protoIn.CorpoMail, this._vert.MittenteMailPec, this.Operatore, ruolo, this._vert.UsaInvioInteroperabilePec);
                        var pecServiceWrapper = new PECServiceWrapper(this._vert.UrlPec, this._protocolloLogs, this._protocolloSerializer, this._bindingFactory);

                        if (this._vert.WarningPec)
                        {
                            this._protocolloLogs.WarnFormat("PEC INVIATA CORRETTAMENTE AI SEGUENTI DESTINATARI: {0}", String.Join(", ", listaPec));
                        }

                        pecServiceWrapper.InviaPEC(requestXml, this._vert.CodiceAmministrazione, this._vert.Aoo);
                    }
                }
            }
            catch (Exception ex)
            {
                this._protocolloLogs.WarnFormat(String.Format("PROBLEMA DURANTE LA FUNZIONALITA' DI INVIO PEC, ERRORE: {0}", ex.Message));
            }
        }

        private ProtocolloInXml CreaProtocolloIn(Shared.Data.DatiProtocolloIn datiProto)
        {
            var protoIn = new ProtocolloInXml
            {
                Data = DateTime.Now.Date.ToString("dd/MM/yyyy"),
                Classifica = datiProto.Classifica,
                TipoDocumento = datiProto.TipoDocumento,
                Oggetto = datiProto.Oggetto,
                Origine = datiProto.Flusso,
                AggiornaAnagrafiche = this._vert.AggiornaAnagrafiche
            };

            if (!String.IsNullOrEmpty(datiProto.TipoSmistamento))
            {
                protoIn.OggettoBilingue = datiProto.TipoSmistamento;
            }

            //Gestione Fascicolazione (come comportarsi con la protocollazione delle autorizzazioni???)
            if (!String.IsNullOrEmpty(this._vert.NumeroPratica) && !this.GestisciFascicolazione)
            {
                //Fascicolazione con fascicolo faldone precedentemente creato
                protoIn.NumeroPratica = this._vert.NumeroPratica;
                protoIn.AnnoPratica = DateTime.Now.Year.ToString();
            }

            protoIn.Utente = this.Operatore.ToUpper();

            this._protocolloLogs.Debug("#### SetAllegati ####");
            //Setto gli allegati
            this.SetAllegati(protoIn, datiProto);

            this._protocolloLogs.Debug("#### SetMittenti ####");
            //Setto i mittenti
            this.SetMittenti(protoIn, datiProto);

            this._protocolloLogs.Debug("#### SetDestinatari ####");
            //Setto i destinatari
            this.SetDestinatari(protoIn, datiProto);

            return protoIn;
        }

        private void SetAllegati(ProtocolloInXml protoIn, DatiProtocolloIn datiProtoIn)
        {
            try
            {
                protoIn.Allegati = new AllegatoInXml[datiProtoIn.NumeroAllegatiPresenti];

                int iIndex = 0;
                datiProtoIn
                    .RecuperaAllegati()
                    .ToList()
                    .ForEach(x =>
                    {
                        if (x.OGGETTO == null)
                        {
                            throw new ProtocolloException("Errore generato dal web method SetAllegati del protocollo Iride. Metodo: SetAllegati, modulo: ProtocolloIride. C'è un allegato con il campo OGGETTO null.\r\n");
                        }

                        protoIn.Allegati[iIndex] = new AllegatoInXml
                        {
                            ContentType = x.MimeType,
                            Image = x.OGGETTO
                        };

                        if (!String.IsNullOrEmpty(x.Extension))
                        {
                            protoIn.Allegati[iIndex].TipoFile = x.Extension.Substring(1);
                        }

                        protoIn.Allegati[iIndex].Commento = x.Descrizione;
                        protoIn.Allegati[iIndex].NomeAllegato = x.NOMEFILE;

                        iIndex++;
                    }
                );
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE IL SETTAGGIO DEGLI ALLEGATI, {0}", ex.Message), ex);
            }
        }

        private void SetMittenti(ProtocolloInXml protoIn, DatiProtocolloIn datiProto)
        {
            try
            {
                protoIn.MittenteInterno = "";
                var mittentiDestinatariList = new List<MittenteDestinatarioInXml>();

                //Verifico le amministrazioni (interne ed esterne)
                if (datiProto.Mittenti.Amministrazione.Count >= 1)
                {
                    if ((!String.IsNullOrEmpty(datiProto.Mittenti.Amministrazione[0].PROT_UO)) && (!String.IsNullOrEmpty(datiProto.Mittenti.Amministrazione[0].PROT_RUOLO)))
                    {
                        protoIn.MittenteInterno = String.IsNullOrEmpty(this._vert.UoSmistamento) ? datiProto.Mittenti.Amministrazione[0].PROT_UO : this._vert.UoSmistamento;
                        protoIn.Ruolo = String.IsNullOrEmpty(this._vert.UoSmistamento) ? datiProto.Mittenti.Amministrazione[0].PROT_RUOLO : this._vert.UoSmistamento;
                        //Se il flusso è in Partenza occorre settare anche InCaricoA e Ruolo con PROT_UO
                        if (protoIn.Origine == ProtocolloConstants.COD_PARTENZA)
                        {
                            protoIn.MittenteInterno = datiProto.Mittenti.Amministrazione[0].PROT_UO;

                            if (!this._vert.DisabilitaCaricoPartenza)
                            {
                                protoIn.InCaricoA = datiProto.Mittenti.Amministrazione[0].PROT_UO;
                                protoIn.Ruolo = datiProto.Mittenti.Amministrazione[0].PROT_RUOLO; //modificato per test Ravenna
                            }
                        }
                    }
                    else if ((String.IsNullOrEmpty(datiProto.Mittenti.Amministrazione[0].PROT_UO)) ^ (String.IsNullOrEmpty(datiProto.Mittenti.Amministrazione[0].PROT_RUOLO)))
                    {
                        throw new Exception("PER ESEGUIRE UNA PROTOCOLLAZIONE CON IRIDE È NECESSARIO CHE L'AMMINISTRAZIONE INTERNA ABBIA SETTATO SIA L'UNITÀ ORGANIZZATIVA CHE IL RUOLO!\r\n");
                    }
                    else
                    {
                        //Ciclo per le amministrazioni esterne
                        foreach (var amministrazione in datiProto.Mittenti.Amministrazione)
                        {
                            var mittentiDestinatari = new MittenteDestinatarioInXml();
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

                            mittentiDestinatari.TipoPersona = Constants.PERSONA_GIURIDICA_IRIDE;

                            if (String.IsNullOrEmpty(amministrazione.AMMINISTRAZIONE))
                            {
                                throw new Exception(String.Format("DESCRIZIONE AMMINISTRAZIONE ID {0} NON VALORIZZATA", amministrazione.CODICEAMMINISTRAZIONE));
                            }

                            mittentiDestinatari.CognomeNome = amministrazione.AMMINISTRAZIONE.TrimEnd();

                            if (!String.IsNullOrEmpty(amministrazione.UFFICIO))
                            {
                                mittentiDestinatari.CognomeNome = String.Concat(amministrazione.AMMINISTRAZIONE, " - ", amministrazione.UFFICIO.TrimEnd());
                            }
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
                                var rec = new RecapitiEmailAdapter(amministrazione.PEC, this._vert.TipoRecapitoEmail);
                                mittentiDestinatari.Recapiti = rec.Recapiti;
                            }

                            mittentiDestinatariList.Add(mittentiDestinatari);
                        }
                    }
                }

                if (datiProto.Mittenti.Anagrafe.Count >= 1)
                {
                    foreach (var protoAnagrafe in datiProto.Mittenti.Anagrafe)
                    {
                        if (!mittentiDestinatariList.Any(x => x.CodiceFiscale == protoAnagrafe.CODICEFISCALE || x.CodiceFiscale == protoAnagrafe.PARTITAIVA))
                        {
                            if (mittentiDestinatariList.Count >= 100)
                            {
                                this._protocolloLogs.WarnFormat("Sono state conteggiati {0} mittenti mentre il limite massimo è di 99", mittentiDestinatariList.Count.ToString());
                                return;
                            }

                            var mittentiDestinatari = new MittenteDestinatarioInXml();

                            string codiceFiscalePartitaIva = "";

                            if (!String.IsNullOrEmpty(protoAnagrafe.CODICEFISCALE))
                            {
                                codiceFiscalePartitaIva = protoAnagrafe.CODICEFISCALE;
                            }
                            else if (!String.IsNullOrEmpty(protoAnagrafe.PARTITAIVA))
                            {
                                codiceFiscalePartitaIva = protoAnagrafe.PARTITAIVA;
                            }

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
                                mittentiDestinatari.DataNascita = (protoAnagrafe.DATANOMINATIVO ?? DateTime.MinValue) == DateTime.MinValue ? String.Empty : protoAnagrafe.DATANOMINATIVO.Value.ToString("dd/MM/yyyy");
                                mittentiDestinatari.TipoPersona = Constants.PERSONA_GIURIDICA_IRIDE;
                            }

                            mittentiDestinatari.Indirizzo = protoAnagrafe.INDIRIZZO ?? "";

                            if (!String.IsNullOrEmpty(protoAnagrafe.Mezzo))
                            {
                                mittentiDestinatari.Mezzo = protoAnagrafe.Mezzo;
                            }

                            //Modificate per problemi con il Comune di Ravenna
                            mittentiDestinatari.CodiceComuneNascita = protoAnagrafe.CodiceIstatComNasc;
                            mittentiDestinatari.CodiceComuneResidenza = protoAnagrafe.CodiceIstatComRes;

                            if (!String.IsNullOrEmpty(protoAnagrafe.CITTA))
                            {
                                mittentiDestinatari.Localita = protoAnagrafe.CITTA;
                            }

                            mittentiDestinatari.Nazionalita = "100"; //N.B.: Il valore deve essere ricavato da una tabella Iride

                            //Setto il campo DataRicevimento che, in seguito ai test fatti a Cesena, è necessario settare
                            //nel caso in cui il flusso è in A
                            if (this.DatiProtocollo.TipoAmbito == AmbitoProtocollazioneEnum.DA_ISTANZA)
                            {
                                mittentiDestinatari.DataRicevimento = this.DatiProtocollo.Istanza.DATA.Value.ToString("dd/MM/yyyy");
                            }

                            if (this.DatiProtocollo.TipoAmbito == AmbitoProtocollazioneEnum.DA_MOVIMENTO)
                            {
                                mittentiDestinatari.DataRicevimento = this.DatiProtocollo.Movimento.DATA.Value.ToString("dd/MM/yyyy");
                            }

                            if (!String.IsNullOrEmpty(protoAnagrafe.PecProtocollazione))
                            {
                                if (protoAnagrafe.PecProtocollazione.Equals(protoAnagrafe.PecAnagrafica) || String.IsNullOrEmpty(protoAnagrafe.PecAnagrafica))
                                {
                                    var rec = new RecapitiEmailAdapter(protoAnagrafe.PecProtocollazione, this._vert.TipoRecapitoEmail);
                                    mittentiDestinatari.Recapiti = rec.Recapiti;
                                }
                                else
                                {
                                    var pecs = new string[] { protoAnagrafe.PecProtocollazione, protoAnagrafe.PecAnagrafica };

                                    var rec = new RecapitiEmailAdapter(pecs, this._vert.TipoRecapitoEmail);
                                    mittentiDestinatari.Recapiti = rec.Recapiti;
                                }
                            }

                            mittentiDestinatariList.Add(mittentiDestinatari);
                        }
                        else
                        {
                            if (this.TipoInserimento == Source.PROT_IST_MOV_AUT_BO)
                            {
                                throw new Exception("SONO PRESENTI PIU' MITTENTI CON LO STESSO CODICE FISCALE / PARTITA IVA");
                            }
                        }
                    }
                }
                if (mittentiDestinatariList.Count > 0)
                {
                    protoIn.MittentiDestinatari = mittentiDestinatariList.Take(99).ToArray();
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE IL SETTAGGIO DEI MITTENTI, {0}", ex.Message), ex);
            }
        }

        private void SetDestinatari(ProtocolloInXml protoIn, Shared.Data.DatiProtocolloIn datiProto)
        {
            try
            {
                var mittentiDestinatariList = new List<MittenteDestinatarioInXml>();

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
                                        var mittentiDestinatari = new MittenteDestinatarioInXml
                                        {
                                            Nome = String.Empty,
                                            CodiceComuneResidenza = String.Empty,
                                            DataNascita = String.Empty,
                                            CodiceComuneNascita = String.Empty,
                                            Nazionalita = String.Empty,
                                            DataInvio_DataProt = String.Empty,
                                            Spese_NProt = String.Empty
                                        };

                                        if (String.IsNullOrEmpty(amministrazione.AMMINISTRAZIONE))
                                        {
                                            throw new Exception(String.Format("DESCRIZIONE AMMINISTRAZIONE ID {0} NON VALORIZZATA", amministrazione.CODICEAMMINISTRAZIONE));
                                        }

                                        mittentiDestinatari.CognomeNome = amministrazione.AMMINISTRAZIONE.TrimEnd();

                                        if (!String.IsNullOrEmpty(amministrazione.UFFICIO))
                                        {
                                            mittentiDestinatari.CognomeNome = String.Concat(amministrazione.AMMINISTRAZIONE, " - ", amministrazione.UFFICIO.TrimEnd());
                                        }

                                        mittentiDestinatari.CodiceFiscale = !String.IsNullOrEmpty(amministrazione.PARTITAIVA) ? amministrazione.PARTITAIVA : String.Empty;
                                        mittentiDestinatari.Indirizzo = !String.IsNullOrEmpty(amministrazione.INDIRIZZO) ? amministrazione.INDIRIZZO : String.Empty;

                                        if (!String.IsNullOrEmpty(amministrazione.CITTA))
                                        {
                                            mittentiDestinatari.Localita = amministrazione.CITTA;
                                        }

                                        mittentiDestinatari.Mezzo = !String.IsNullOrEmpty(amministrazione.Mezzo) ? amministrazione.Mezzo : String.Empty;

                                        mittentiDestinatari.TipoPersona = Constants.PERSONA_GIURIDICA_IRIDE;

                                        mittentiDestinatariList.Add(mittentiDestinatari);
                                        //protoIn.MittentiDestinatari = mittentiDestinatariList.ToArray();
                                    }
                                }
                                else if ((String.IsNullOrEmpty(amministrazione.PROT_RUOLO)) ^ (String.IsNullOrEmpty(amministrazione.PROT_RUOLO)))
                                {
                                    throw new Exception("PER ESEGUIRE UNA PROTOCOLLAZIONE CON IRIDE È NECESSARIO CHE L'AMMINISTRAZIONE INTERNA ABBIA SETTATO SIA L'UNITÀ ORGANIZZATIVA CHE IL RUOLO!");
                                }
                            }
                        }
                        break;

                    case ProtocolloConstants.COD_PARTENZA:

                        foreach (var amministrazione in datiProto.Destinatari.Amministrazione.Where(x => String.IsNullOrEmpty(x.PROT_UO) && String.IsNullOrEmpty(x.PROT_UO)))
                        {
                            if (mittentiDestinatariList.Count >= 100)
                            {
                                this._protocolloLogs.WarnFormat("Sono stati conteggiati {0} destinatari mentre il limite massimo è di 99", mittentiDestinatariList.Count.ToString());
                                return;
                            }
                            var mittentiDestinatari = new MittenteDestinatarioInXml
                            {
                                Nome = String.Empty,
                                CodiceComuneResidenza = String.Empty,
                                DataNascita = String.Empty,
                                CodiceComuneNascita = String.Empty,
                                Nazionalita = String.Empty,
                                DataInvio_DataProt = String.Empty,
                                Spese_NProt = String.Empty
                            };

                            if (String.IsNullOrEmpty(amministrazione.AMMINISTRAZIONE))
                            {
                                throw new Exception(String.Format("DESCRIZIONE AMMINISTRAZIONE ID {0} NON VALORIZZATA", amministrazione.AMMINISTRAZIONE));
                            }

                            mittentiDestinatari.CognomeNome = amministrazione.AMMINISTRAZIONE.TrimEnd();

                            if (!String.IsNullOrEmpty(amministrazione.UFFICIO))
                            {
                                mittentiDestinatari.CognomeNome = String.Concat(amministrazione.AMMINISTRAZIONE, " - ", amministrazione.UFFICIO.TrimEnd());
                            }

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
                                var rec = new RecapitiEmailAdapter(amministrazione.PEC, this._vert.TipoRecapitoEmail);
                                mittentiDestinatari.Recapiti = rec.Recapiti;
                            }
                            else
                            {
                                if (!String.IsNullOrEmpty(this._vert.UrlPec))
                                {
                                    string warn = "";
                                    if (String.IsNullOrEmpty(amministrazione.PARTITAIVA))
                                    {
                                        warn = String.Format("LA PEC E LA PARTITA IVA DELL'AMMINISTRAZIONE {0}, (CODICE {1}), NON SONO VALORIZZATI, E' PROBABILE QUINDI CHE LA PEC NON SIA STATA INVIATA DAL SERVIZIO POSTE WEB DI IRIDE", amministrazione.AMMINISTRAZIONE, amministrazione.CODICEAMMINISTRAZIONE);
                                    }
                                    else
                                    {
                                        warn = String.Format("LA PEC DELL'AMMINISTRAZIONE {0}, (CODICE {1}), NON E' VALORIZZATA, CONTROLLARE SU IRIDE, SE L'ANAGRAFICA CON PARTITA IVA {2} ABBIA IL RECAPITO EMAIL VALORIZZATO.", amministrazione.AMMINISTRAZIONE, amministrazione.CODICEAMMINISTRAZIONE, amministrazione.PARTITAIVA);
                                    }

                                    this._protocolloLogs.Warn(warn);
                                }
                            }

                            mittentiDestinatariList.Add(mittentiDestinatari);
                        }
                        break;
                }

                foreach (var anagrafe in datiProto.Destinatari.Anagrafe)
                {
                    if (mittentiDestinatariList.Any(x => x.CodiceFiscale == anagrafe.CODICEFISCALE || x.CodiceFiscale == anagrafe.PARTITAIVA))
                    {
                        throw new Exception("SONO PRESENTI PIU' DESTINATARI CON LO STESSO CODICE FISCALE / PARTITA IVA");
                    }

                    if (mittentiDestinatariList.Count >= 100)
                    {
                        this._protocolloLogs.WarnFormat("Sono state conteggiati {0} destinatari mentre il limite massimo è di 99", mittentiDestinatariList.Count.ToString());
                        return;
                    }

                    var mittentiDestinatari = new MittenteDestinatarioInXml
                    {
                        Nome = String.Empty,
                        CodiceComuneResidenza = String.Empty,
                        DataNascita = String.Empty,
                        CodiceComuneNascita = String.Empty,
                        Nazionalita = String.Empty,
                        DataInvio_DataProt = String.Empty,
                        Spese_NProt = String.Empty
                    };

                    string codiceFiscalePartitaIva = "";

                    if (!String.IsNullOrEmpty(anagrafe.CODICEFISCALE))
                    {
                        codiceFiscalePartitaIva = anagrafe.CODICEFISCALE;
                    }
                    else if (!String.IsNullOrEmpty(anagrafe.PARTITAIVA))
                    {
                        codiceFiscalePartitaIva = anagrafe.PARTITAIVA;
                    }

                    if (!String.IsNullOrEmpty(codiceFiscalePartitaIva))
                    {
                        mittentiDestinatari.CodiceFiscale = codiceFiscalePartitaIva;
                    }

                    mittentiDestinatari.CognomeNome = anagrafe.NOMINATIVO;
                    mittentiDestinatari.Nome = anagrafe.NOME ?? "";

                    if (anagrafe.TIPOANAGRAFE == "F")
                    {
                        mittentiDestinatari.DataNascita = (anagrafe.DATANASCITA ?? DateTime.MinValue) == DateTime.MinValue ? "" : anagrafe.DATANASCITA.Value.ToString("dd/MM/yyyy");
                        mittentiDestinatari.TipoPersona = Constants.PERSONA_FISICA_IRIDE;
                    }
                    else
                    {
                        mittentiDestinatari.DataNascita = (anagrafe.DATANOMINATIVO ?? DateTime.MinValue) == DateTime.MinValue ? "" : anagrafe.DATANOMINATIVO.Value.ToString("dd/MM/yyyy");
                        mittentiDestinatari.TipoPersona = Constants.PERSONA_GIURIDICA_IRIDE;
                    }

                    mittentiDestinatari.Indirizzo = anagrafe.INDIRIZZO ?? "";

                    //Modificate per problemi con il Comune di Ravenna
                    mittentiDestinatari.CodiceComuneNascita = anagrafe.CodiceIstatComNasc;
                    mittentiDestinatari.CodiceComuneResidenza = anagrafe.CodiceIstatComRes;

                    if (!String.IsNullOrEmpty(anagrafe.CITTA))
                    {
                        mittentiDestinatari.Localita = anagrafe.CITTA;
                    }

                    if (!String.IsNullOrEmpty(anagrafe.Mezzo))
                    {
                        mittentiDestinatari.Mezzo = anagrafe.Mezzo;
                    }

                    mittentiDestinatari.Nazionalita = "100"; //N.B.: Il valore deve essere ricavato da una tabella Iride

                    if (!String.IsNullOrEmpty(anagrafe.PecProtocollazione))
                    {
                        if (anagrafe.PecProtocollazione.Equals(anagrafe.PecAnagrafica) || String.IsNullOrEmpty(anagrafe.PecAnagrafica))
                        {
                            var rec = new RecapitiEmailAdapter(anagrafe.PecProtocollazione, this._vert.TipoRecapitoEmail);
                            mittentiDestinatari.Recapiti = rec.Recapiti;
                        }
                        else
                        {
                            var pecs = new string[] { anagrafe.PecProtocollazione, anagrafe.PecAnagrafica };

                            var rec = new RecapitiEmailAdapter(pecs, this._vert.TipoRecapitoEmail);
                            mittentiDestinatari.Recapiti = rec.Recapiti;
                        }
                    }
                    else
                    {
                        if (!String.IsNullOrEmpty(this._vert.UrlPec))
                        {
                            string warn = "";
                            if (String.IsNullOrEmpty(codiceFiscalePartitaIva))
                            {
                                warn = String.Format("LA PEC E IL CODICE FISCALE/PARTITA IVA DELL'ANAGRAFICA {0}, (CODICE {1}), NON SONO VALORIZZATI, E' PROBABILE QUINDI CHE LA PEC NON SIA STATA INVIATA DAL SERVIZIO POSTE WEB DI IRIDE", anagrafe.NOMINATIVO, anagrafe.CODICEANAGRAFE);
                            }
                            else
                            {
                                warn = String.Format("LA PEC DELL'ANAGRAFICA {0}, (CODICE {1}), NON E' VALORIZZATA, CONTROLLARE SU IRIDE, SE L'ANAGRAFICA CON CODICE FISCALE {2} ABBIA IL RECAPITO EMAIL VALORIZZATO.", anagrafe.NOMINATIVO, anagrafe.CODICEANAGRAFE, codiceFiscalePartitaIva);
                            }

                            this._protocolloLogs.Warn(warn);
                        }
                    }

                    mittentiDestinatariList.Add(mittentiDestinatari);
                }

                if (mittentiDestinatariList.Count == 0 && datiProto.Flusso == ProtocolloConstants.COD_PARTENZA)
                {
                    var amministrazioniInterne = datiProto.Destinatari.Amministrazione.Where(x => !String.IsNullOrEmpty(x.PROT_UO) || !String.IsNullOrEmpty(x.PROT_UO)).ToList();
                    var primaAmministrazione = amministrazioniInterne.First();

                    protoIn.Origine = ProtocolloConstants.COD_INTERNO;

                    protoIn.InCaricoA = primaAmministrazione.PROT_UO;
                    protoIn.Ruolo = primaAmministrazione.PROT_RUOLO;
                }

                if (mittentiDestinatariList.Any())
                {
                    protoIn.MittentiDestinatari = mittentiDestinatariList.Take(99).ToArray();
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE IL SETTAGGIO DEI DESTINATARI, {0}", ex.Message), ex);
            }
        }

        public override List<MetadatoType> RecuperaMetadati()
        {
            return new List<MetadatoType>
            {
                new MetadatoType
                {
                    Chiave = MetadatiConstants.MezzoDefault,
                    Valore = null
                },
            };
        }

        #region Metodi PRIVATI per la fascicolazione di un protocollo

        private DatiProtocolloFascicolatoResponseType Fascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            this._protocolloLogs.Debug("Inizio Fascicolato");
            var verticalizzazione = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloJIride>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune);

            var leggiProtocolloRequest = new LeggiProtocolloBuilder(verticalizzazione, idProtocollo, numeroProtocollo, annoProtocollo, this.Operatore, this.Ruolo).Build();
            leggiProtocolloRequest.UsaLeggiDocumentoPlus = true;
            var protocolloLetto = new LeggiProtocolloService(this._protocolloLogs, this._protocolloSerializer, this._bindingFactory).Leggi(leggiProtocolloRequest);

            //Verifico l'esistenza del protocollo passato
            if (protocolloLetto.DocumentoOut.IdDocumento == 0)
            {
                return new DatiProtocolloFascicolatoResponseType
                {
                    Fascicolato = EnumFascicolatoType.warning,
                    NoteFascicolo = "Errore: " + protocolloLetto.DocumentoOut.Messaggio + "." + protocolloLetto.DocumentoOut.Errore
                };
            }

            //Verifico se il protocollo è stato fascicolato
            if (protocolloLetto.DocumentoOut.IdPratica == 0 && (String.IsNullOrEmpty(protocolloLetto.DocumentoOut.NumeroPratica) || protocolloLetto.DocumentoOut.AnnoPratica == 0))
            {
                return new DatiProtocolloFascicolatoResponseType
                {
                    Fascicolato = EnumFascicolatoType.no
                };
            }

            //Se il protocollo è una copia, e sono presenti i riferimenti agli altri fascicoli, allora
            //recupero i riferimenti del fascicolo dal primo elemento degli altri fascicoli
            var leggiFascicoloRequest = this.CreaLeggiFascicoloRequest(idProtocollo, protocolloLetto.DocumentoOut, verticalizzazione);
            var leggiFascicoloResponse = new FascicolazioneService(this._protocolloLogs, this._protocolloSerializer, this._bindingFactory).LeggiFascicolo(leggiFascicoloRequest);

            this._protocolloLogs.DebugFormat("DataFascicolo {0}", leggiFascicoloResponse.Data);
            return new DatiProtocolloFascicolatoResponseType
            {
                AnnoFascicolo = leggiFascicoloResponse.Anno.ToString(),
                Classifica = String.IsNullOrEmpty(leggiFascicoloResponse.Classifica)
                                ? protocolloLetto.DocumentoOut.Classifica
                                : leggiFascicoloResponse.Classifica,
                DataFascicolo = leggiFascicoloResponse.Data.Value.ToString("dd/MM/yyyy"),
                NumeroFascicolo = leggiFascicoloResponse.NumeroSenzaClassifica,
                Oggetto = leggiFascicoloResponse.Oggetto,
                Fascicolato = EnumFascicolatoType.si
            };
        }

        private LeggiFascicoloRequest CreaLeggiFascicoloRequest(string idProtocollo, DocumentoOutXml protocollo, VerticalizzazioneProtocolloJIride verticalizzazione)
        {
            //Se il protocollo è una copia, e sono presenti i riferimenti agli altri fascicoli, allora
            //recupero i riferimenti del fascicolo dal primo elemento degli altri fascicoli
            bool isCopia = this._protocolloService.IsCopia(idProtocollo);

            if (isCopia && protocollo.AltriFascicoli?.Length > 0)
            {
                var fascicoloDaAltriFascicoli = new Fascicolo
                {
                    AnnoFascicolo = protocollo.AltriFascicoli[0].AnnoAltroFascicolo,
                    Classifica = protocollo.AltriFascicoli[0].AnnoNumeroAltroFascicolo.Split('/').Length > 1
                                    ? protocollo.AltriFascicoli[0].AnnoNumeroAltroFascicolo.Split('/')[1]
                                    : null,
                    NumeroFascicolo = protocollo.AltriFascicoli[0].NumeroAltroFascicolo
                };

                return new LeggiFascicoloRequestBuilder(verticalizzazione, fascicoloDaAltriFascicoli, 0, this.Operatore, this.Ruolo).Build();
            }

            var fascicolo = new Fascicolo
            {
                AnnoFascicolo = protocollo.AnnoPratica,
                Classifica = protocollo.Classifica,
                NumeroFascicolo = protocollo.NumeroPratica
            };

            return new LeggiFascicoloRequestBuilder(verticalizzazione, fascicolo, protocollo.IdPratica, this.Operatore, this.Ruolo).Build();
        }

        #endregion

        #region Metodi per la lettura di un protocollo

        public override List<DatiProtocolloLettoResponseType> LeggiProtocollo(Shared.WsDataClass.LeggiProtocolloRequest leggiProtocolloRequest)
        {
            this._protocolloLogs.Debug($"Inizio metodo LeggiProtocollo con idProtocollo: {leggiProtocolloRequest.IdProtocollo}, annoProtocollo: {leggiProtocolloRequest.AnnoProtocollo}, numeroProtocollo: {leggiProtocolloRequest.NumeroProtocollo}");

            this._protocolloLogs.Debug("Inizio creazione dei parametri della request");
            var verticalizzazione = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloJIride>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune);
            var request = new LeggiProtocolloBuilder(verticalizzazione, leggiProtocolloRequest.IdProtocollo, leggiProtocolloRequest.NumeroProtocollo, leggiProtocolloRequest.AnnoProtocollo, this.Operatore, this.Ruolo).Build();
            request.UsaLeggiDocumentoPlus = true;
            this._protocolloLogs.Debug("Fine creazione dei parametri della request");

            this._protocolloLogs.Debug("Inizio chiamata al web service per la lettura del protocollo");
            var response = new LeggiProtocolloService(this._protocolloLogs, this._protocolloSerializer, this._bindingFactory).Leggi(request);
            this._protocolloLogs.Debug("Fine chiamata al web service per la lettura del protocollo");

            this._protocolloLogs.Debug($"Inizio adattamento della risposta della lettura del protocollo con id {response.DocumentoOut.IdDocumento}");
            var retVal = response.ToDatiProtocolloLetto();
            this._protocolloLogs.Debug($"Fine adattamento della risposta della lettura del protocollo con id {response.DocumentoOut.IdDocumento}");

            this._protocolloLogs.Debug("Fine metodo LeggiProtocollo");

            return new List<DatiProtocolloLettoResponseType>() { retVal };
        }

        public override void CheckProtocolloLetto(string annoProtocollo, string numeroProtocollo, string idProtocollo, DatiProtocolloLettoResponseType pDatiProtocolloLetto)
        {
            if (String.IsNullOrEmpty(idProtocollo))
            {
                base.CheckProtocolloLetto(annoProtocollo, numeroProtocollo, idProtocollo, pDatiProtocolloLetto);
            }
        }

        public override AllegatoResponseType LeggiAllegato()
        {
            return this.LeggiAllegatoDaLeggiProtocollo();
        }

        public new AllegatoResponseType LeggiAllegatoDaLeggiProtocollo()
        {
            var verticalizzazione = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloJIride>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune);
            var request = new LeggiProtocolloBuilder(verticalizzazione, this.IdProtocollo, this.NumProtocollo, this.AnnoProtocollo, this.Operatore, this.Ruolo).Build();
            var response = new LeggiProtocolloService(this._protocolloLogs, this._protocolloSerializer, this._bindingFactory).Leggi(request);
            var protocolloLetto = response.ToDatiProtocolloLetto();

            foreach (var allegati in protocolloLetto.Allegati)
            {
                if (allegati.IDBase == this.IdAllegato)
                {
                    return allegati;
                }
            }
            return null;
        }

        public override DatiProtocolloLettoResponseType LeggiProtocolloStorico(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            this._protocolloLogs.Debug($"Inizio metodo LeggiProtocollo Storico con idProtocollo: {idProtocollo}, annoProtocollo: {annoProtocollo}, numeroProtocollo: {numeroProtocollo}");

            this._protocolloLogs.Debug("Inizio creazione dei parametri della request");
            var verticalizzazione = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloStorico>(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune);
            var request = new LeggiProtocolloStoricoRequestBuilder(verticalizzazione, idProtocollo, numeroProtocollo, annoProtocollo).Build();
            this._protocolloLogs.Debug("Fine creazione dei parametri della request");

            this._protocolloLogs.Debug("Inizio chiamata al web service per la lettura del protocollo");
            var response = new LeggiProtocolloService(this._protocolloLogs, this._protocolloSerializer, this._bindingFactory).Leggi(request);
            this._protocolloLogs.Debug("Fine chiamata al web service per la lettura del protocollo");

            this._protocolloLogs.Debug($"Inizio adattamento della risposta della lettura del protocollo con id {response.DocumentoOut.IdDocumento}");
            var retVal = response.ToDatiProtocolloLetto();
            this._protocolloLogs.Debug($"Fine adattamento della risposta della lettura del protocollo con id {response.DocumentoOut.IdDocumento}");

            this._protocolloLogs.Debug("Fine metodo LeggiProtocollo");

            return retVal;
        }

        public override AllegatoResponseType LeggiAllegatoStorico()
        {
            this._protocolloLogs.Debug("Inizio lettura allegato storico del protcollo JIride");
            var allegato = base.LeggiAllegatoStoricoDaLeggiProtocolloStorico();
            this._protocolloLogs.Debug("Fine lettura allegato storico del protcollo JIride");
            return allegato;
        }

        #endregion
    }
}