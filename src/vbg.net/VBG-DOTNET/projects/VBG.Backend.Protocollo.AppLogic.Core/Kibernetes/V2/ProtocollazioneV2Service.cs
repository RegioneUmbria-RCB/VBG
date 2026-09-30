using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using ProtocolloKibernetesV2Service;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Kibernetes.V2
{
    public class ProtocollazioneV2Service : IProtocollazioneService
    {
        private readonly ILog _logger;
        private readonly IProtocolloSerializer _serializer;
        private readonly IParametriService _parametriService;
        private readonly string _operatore;
        private readonly ProtocolloClientServiceCreator _protocolloClientServiceCreator;
        private readonly IBindingFactory _bindingFactory;

        public ProtocollazioneV2Service(IParametriService parametriService, ILog logger, IProtocolloSerializer serializer, string operatore, IBindingFactory bindingFactory)
        {
            _logger = logger;
            _serializer = serializer;
            _parametriService = parametriService;
            _operatore = operatore;
            _bindingFactory = bindingFactory;
            this._protocolloClientServiceCreator = new ProtocolloClientServiceCreator(logger, bindingFactory, parametriService.Url);
        }
        public ProtocollazioneResponse Protocolla(DatiProtocolloIn datiProtocollo)
        {
            this._logger.Debug("Inizio protocollazione");

            var response = this.ProtocollaArrivoPartenza(datiProtocollo);

            if (datiProtocollo.HaAllegati())
            {
                var service = new AggiungiAllegatoV2Service(_parametriService, _logger, _serializer, this._bindingFactory);
                var responseAllegati = service.AggiungiAllegati(datiProtocollo.RecuperaAllegati(), response.Numero, Convert.ToInt16(response.Anno));
                if (!responseAllegati.Ok)
                {
                    throw new Exception($"Si sono verificati errori durante l'invio degli allegati: { String.Join(",", responseAllegati.Errori) }");
                }
            }

            if (datiProtocollo.Flusso == "P")
            {
                this._logger.Debug("Inizio Invio mail per protocollazione in uscita");

                try
                {
                    this.InviaMail(new InviaMailRequest
                    {
                        Allegati = datiProtocollo
                                        .RecuperaAllegati()
                                        .Select(x => new DataToImport
                                        {
                                            Buffer = x.OGGETTO,
                                            DescrizioneFile = x.Descrizione,
                                            NomeFile = x.NOMEFILE
                                        })
                                        .ToArray(),
                        Mittente = datiProtocollo
                                    .Mittenti
                                    .Amministrazione
                                    .Where(x => !String.IsNullOrEmpty(x.PROT_UO))
                                    .Select(x => new SoggettoInterno
                                    {
                                        UO = x.PROT_UO,
                                        NomeUtente = this._parametriService.UserName
                                    })
                                    .First(),
                        CorpoMessaggio = datiProtocollo.CorpoMail ?? datiProtocollo.OggettoMail,
                        Destinatari = this.RecuperaDestinatari(datiProtocollo.Destinatari),
                        MittenteEmail = datiProtocollo
                                    .Mittenti
                                    .Amministrazione
                                    .Where(x => !String.IsNullOrEmpty(x.PROT_UO))
                                    .Select(x => x.PEC ?? x.EMAIL)
                                    .First(),
                        OggettoMessaggio = datiProtocollo.OggettoMail,
                        VoceTitolario = datiProtocollo.Classifica
                    });
                }
                catch (Exception ex)
                {
                    this._logger.Warn(ex.Message);
                    response.Warning = ex.Message;
                }

                this._logger.Debug("Fine Invio mail per protocollazione in uscita");
            }

            this._logger.Debug("Fine protocollazione");

            return response;
        }
        private void InviaMail(InviaMailRequest request)
        {
            using (var ws = this._protocolloClientServiceCreator.CreateClient())
            {
                try
                {
                    var authInfo = new AuthInfo
                    {
                        NomeUtente = this._parametriService.UserName,
                        Password = this._parametriService.Password
                    };

                    var dati = new DatiEmailUscita
                    {
                        Mittente = request.Mittente,
                        Destinatari = request.Destinatari,
                        CorpoMessaggio = request.CorpoMessaggio,
                        Oggetto = request.OggettoMessaggio,
                        Allegati = request.Allegati,
                        MittenteEmail = request.MittenteEmail,
                        VoceTitolario = request.VoceTitolario

                    };

                    this._serializer.LogAndValidate("AuthInfoRequest.xml", authInfo);
                    this._serializer.LogAndValidate("DatiEmailUscitaRequest.xml", dati);

                    var response = ws.Service.InviaMail(authInfo, dati);

                    this._serializer.LogAndValidate("InviaMailResponse.xml", response);

                    if (response.Status == StatusCode.Errore)
                    {
                        throw new Exception(response.Messaggio);
                    }

                }
                catch (Exception ex)
                {
                    throw new Exception($"Errore durante la chiamata a InviaMail: {ex.Message}");
                }
            }
        }
        private ProtocollazioneResponse ProtocollaArrivoPartenza(DatiProtocolloIn datiProtocollo)
        {
            switch (datiProtocollo.Flusso)
            {
                case "A":
                    return this.CreaEntrata(datiProtocollo);
                case "P":
                    return this.CreaUscita(datiProtocollo);
                default:
                    throw new NotImplementedException($"E' possibile protocollare solamente in arrivo o in partenza. Previso A o P, ricevuto {datiProtocollo.Flusso}");
            }
        }
        private ProtocollazioneResponse CreaUscita(DatiProtocolloIn datiProtocollo)
        {
            using (var ws = this._protocolloClientServiceCreator.CreateClient())
            {
                try
                {
                    var authInfo = new AuthInfo
                    {
                        NomeUtente = this._parametriService.UserName,
                        Password = this._parametriService.Password
                    };

                    var dati = new DatiProtocolloUscita
                    {
                        Mittente = datiProtocollo
                                    .Mittenti
                                    .Amministrazione
                                    .Where(x => !String.IsNullOrEmpty(x.PROT_UO))
                                    .Select(x => new SoggettoInterno
                                    {
                                        UO = x.PROT_UO,
                                        NomeUtente = this._operatore
                                    })
                                    .First(),
                        Destinatari = this.RecuperaDestinatari(datiProtocollo.Destinatari),
                        Oggetto = datiProtocollo.Oggetto,
                        VoceTitolario = datiProtocollo.Classifica
                    };

                    this._serializer.LogAndValidate("AuthInfoRequest.xml", authInfo);
                    this._serializer.LogAndValidate("DatiProtocolloUscitaRequest.xml", dati);

                    var wsResponse = ws.Service.CreaUscita(authInfo, dati);

                    this._serializer.LogAndValidate("CreaUscitaResponse.xml", wsResponse);

                    return ProtocollazioneResponse.FromResponseInfo(wsResponse);
                }
                catch (Exception ex)
                {
                    this._logger.ErrorFormat("Errore su CreaUscita: {0}", ex);
                    throw;
                }
            }
        }
        private Soggetto[] RecuperaDestinatari(ListaMittDest destinatari)
        {
            try
            {
                List<Soggetto> soggetti = new List<Soggetto>();

                if (destinatari.Anagrafe.Any())
                {
                    soggetti.AddRange(destinatari
                                        .Anagrafe
                                        .Select(x => new Soggetto
                                        {
                                            Esterno = new SoggettoEsterno
                                            {
                                                CAP = x.CAP,
                                                Cellulare = x.TELEFONOCELLULARE,
                                                CodiceFiscale = x.CODICEFISCALE,
                                                Denominazione = x.NOMINATIVO,
                                                Email = x.EMAIL,
                                                Fax = x.FAX,
                                                Indirizzo = x.INDIRIZZO,
                                                Nome = x.NOME,
                                                PartitaIVA = x.PARTITAIVA,
                                                PEC = x.PecProtocollazione,
                                                Telefono = x.TELEFONO
                                            }
                                        }));
                }

                if (destinatari.Amministrazione.Any())
                {
                    soggetti.AddRange(destinatari
                        .Amministrazione
                        .Where(x => String.IsNullOrEmpty(x.PROT_UO))
                        .Select(x => new Soggetto
                        {
                            Esterno = new SoggettoEsterno
                            {
                                CAP = x.CAP,
                                Cellulare = x.TELEFONO1,
                                Denominazione = x.AMMINISTRAZIONE,
                                Email = x.EMAIL,
                                Fax = x.FAX,
                                Indirizzo = x.INDIRIZZO,
                                PartitaIVA = x.PARTITAIVA,
                                PEC = x.PEC,
                                Telefono = x.TELEFONO2
                            }
                        }));

                    soggetti.AddRange(destinatari
                        .Amministrazione
                        .Where(x => !string.IsNullOrEmpty(x.PROT_UO))
                        .Select(x =>
                        {
                            var nomeUtente = _parametriService.UserName;

                            if (_parametriService.UsaRuoloInUscita)
                            {
                                nomeUtente = x.PROT_RUOLO;
                            }

                            return new Soggetto
                            {
                                Interno = new SoggettoInterno
                                {
                                    UO = x.PROT_UO,
                                    NomeUtente = nomeUtente
                                }
                            };
                        }));
                }

                return soggetti.ToArray();
            }
            catch (Exception ex)
            {
                this._logger.ErrorFormat("Errore su RecuperaDestinatari: {0}", ex);
                throw;
            }
        }
        private ProtocollazioneResponse CreaEntrata(DatiProtocolloIn datiProtocollo)
        {
            using (var ws = this._protocolloClientServiceCreator.CreateClient())
            {
                try
                {
                    var authInfo = new AuthInfo
                    {
                        NomeUtente = this._parametriService.UserName,
                        Password = this._parametriService.Password
                    };

                    var dati = new DatiProtocolloEntrata
                    {
                        Mittente = datiProtocollo
                                        .Mittenti
                                        .Anagrafe
                                        .Select(x => new SoggettoEsterno
                                        {
                                            CAP = x.CAP,
                                            Cellulare = x.TELEFONOCELLULARE,
                                            CodiceFiscale = x.CODICEFISCALE,
                                            Denominazione = x.NOMINATIVO,
                                            Email = x.EMAIL,
                                            Fax = x.FAX,
                                            Indirizzo = x.INDIRIZZO,
                                            Nome = x.NOME,
                                            PartitaIVA = x.PARTITAIVA,
                                            PEC = x.PecProtocollazione,
                                            Telefono = x.TELEFONO
                                        })
                                        .First(),
                        Destinatari = datiProtocollo
                                        .Destinatari
                                        .Amministrazione
                                        .Where(x => !String.IsNullOrEmpty(x.PROT_UO))
                                        .Select(x =>
                                        {
                                            var nomeUtente = _parametriService.UserName;

                                            if (_parametriService.UsaRuoloInEntrata)
                                            {
                                                nomeUtente = x.PROT_RUOLO;
                                            }

                                            return new SoggettoInterno
                                            {
                                                UO = x.PROT_UO,
                                                NomeUtente = nomeUtente
                                            };
                                        })
                                        .ToArray(),
                        Oggetto = datiProtocollo.Oggetto,
                        VoceTitolario = datiProtocollo.Classifica
                    };

                    this._serializer.LogAndValidate("AuthInfoRequest.xml", authInfo);
                    this._serializer.LogAndValidate("DatiProtocolloEntrataRequest.xml", dati);

                    var wsResponse = ws.Service.CreaEntrata(authInfo, dati);

                    this._serializer.LogAndValidate("CreaEntrataResponse.xml", wsResponse);

                    if (wsResponse.Errori?.Length > 0)
                    {
                        this._logger.Error("CreaEntrataResponse.xml contiene degli errori.");

                        if (wsResponse.Errori.Contains("Utente non autorizzato"))
                        {
                            this._logger.Error("CreaEntrataResponse -> Utente non autorizzato");
                        }
                    }

                    return ProtocollazioneResponse.FromResponseInfo(wsResponse);
                }
                catch (Exception ex)
                {

                    throw new Exception($"Errore durante la chiamata a CreaEntrata: {ex.Message}");
                }
            }
        }
    }
}
