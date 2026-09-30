using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Data;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Core.Elios;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core
{
    public class PROTOCOLLO_ELIOS : ProtocolloBase
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly IBindingFactory _bindingFactory;

        public PROTOCOLLO_ELIOS(IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._bindingFactory = bindingFactory;
        }

        public override void InizializzaProtocolloBase(ResolveDatiProtocollazioneService datiProtocolloService)
        {
            base.InizializzaProtocolloBase(datiProtocolloService);
            base._protocolloSerializer = new EliosSerializer(this._protocolloLogs, this._protocolloValidation);
        }
        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {
            var r = new DatiProtocolloResponseType();

            //protocollazione
            var response = this.Protocolla(protoIn);
            r = response.ToDatiProtocolloRes();
            r.DataProtocollo = DateTime.Now.Date.ToString("dd/MM/yyyy");

            this.AggiungiAllegati(r.IdProtocollo, r.NumeroProtocollo, DateTime.Now.Date, protoIn.RecuperaAllegati());


            //invio della mail
            try
            {
                if (protoIn.Flusso != "A")
                {
                    this.SendMail(Convert.ToInt32(r.AnnoProtocollo), Convert.ToInt32(r.NumeroProtocollo));
                }
            }
            catch (Exception ex)
            {
                r.Warning = $"La mail/pec non è stata inviata a causa di un errore: {ex.Message}";
            }
            return r;
        }

        public override void AggiungiAllegati(string idProtocollo, string numeroProtocollo, DateTime? dataProtocollo, IEnumerable<ProtocolloAllegati> allegati)
        {
            this._protocolloLogs.Debug("Inizio aggiunta allegati");

            //raccolta dei parametri
            var parametri = new ParametriResolver
                            (
                                base.DatiProtocollo.IdComuneAlias,
                                base.DatiProtocollo.Software,
                                base.DatiProtocollo.CodiceComune,
                                base._protocolloSerializer,
                                this._verticalizzazioniFactory,
                                this._protocolloLogs,
                                this._bindingFactory                          
                            )
                            .Resolve();
            this._protocolloSerializer.LogAndValidate("Parametri.xml", parametri);

            var service = new EliosProtocollazioneService(parametri, base._protocolloSerializer, this._protocolloLogs, this._bindingFactory);

            var i = 0;

            foreach (var allegato in allegati)
            {
                this._protocolloLogs.Debug($"Trasmissione {allegato.NOMEFILE} ");
                service.AggiungiAllegato(new AggiungiAllegatoRequest
                {
                    Anno = dataProtocollo.Value.Year,
                    Numero = Convert.ToInt32(numeroProtocollo),
                    NomeFile = allegato.NOMEFILE,
                    Estensione = allegato.Extension,
                    Contenuto = allegato.OGGETTO,
                    Primario = i == 0
                });
                i++;
            }

            this._protocolloLogs.Debug("Fine aggiunta allegati");
        }

        private InsertResponse Protocolla(DatiProtocolloIn protoIn)
        {
            this._protocolloLogs.Debug("Inizio protocollazione");
            base._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.DatiProtocolloInFileName, protoIn);

            //raccolta dei parametri
            var parametri = new ParametriResolver
                            (
                                base.DatiProtocollo.IdComuneAlias,
                                base.DatiProtocollo.Software,
                                base.DatiProtocollo.CodiceComune,
                                base._protocolloSerializer,
                                this._verticalizzazioniFactory,
                                this._protocolloLogs,
                                this._bindingFactory
                            )
                            .Resolve();

            this._protocolloSerializer.LogAndValidate("Parametri.xml", parametri);

            //protocollazione
            var classifica = protoIn.Classifica.Split('.');
            var service = new EliosProtocollazioneService(parametri, base._protocolloSerializer, this._protocolloLogs, this._bindingFactory);
            var response = service
                    .Insert(new InsertRequest
                    {
                        Anagrafiche = this.GetAnagrafiche(protoIn),
                        Oggetto = protoIn.Oggetto,
                        Tipo = this.GetTipo(protoIn.Flusso),
                        Titolario = new Titolario
                        {
                            Categoria = classifica[0],
                            Classe = classifica.Length > 1 ? classifica[1] : null,
                            Sottoclasse = classifica.Length > 2 ? classifica[2] : null
                        },
                        Uffici = this.GetUffici(protoIn)
                    });

            this._protocolloLogs.Debug("Fine protocollazione");

            return response;
        }

        private void SendMail(int AnnoProtocollo, int NumeroProtocollo)
        {
            this._protocolloLogs.Debug("Invio della mail");

            //raccolta dei parametri
            var parametri = new ParametriResolver
                            (
                                base.DatiProtocollo.IdComuneAlias,
                                base.DatiProtocollo.Software,
                                base.DatiProtocollo.CodiceComune,
                                base._protocolloSerializer,
                                this._verticalizzazioniFactory,
                                this._protocolloLogs,
                                this._bindingFactory
                            )
                            .Resolve();
            this._protocolloSerializer.LogAndValidate("Parametri.xml", parametri);

            new EliosProtocollazioneService(parametri, base._protocolloSerializer, this._protocolloLogs, this._bindingFactory).SendMail(new SendMailRequest
            {
                Anno = AnnoProtocollo,
                Numero = NumeroProtocollo
            });

            this._protocolloLogs.Debug("Mail inviata correttamente");
        }
        public override ListaTipiClassificaType GetClassifiche()
        {
            this._protocolloLogs.Debug("Inizio recupero classifiche");

            //risoluzione della verticalizzazione
            var parametri = new ParametriResolver
                            (
                                base.DatiProtocollo.IdComuneAlias,
                                base.DatiProtocollo.Software,
                                base.DatiProtocollo.CodiceComune,
                                base._protocolloSerializer,
                                this._verticalizzazioniFactory,
                                this._protocolloLogs,
                                this._bindingFactory
                            )
                            .Resolve();
            this._protocolloSerializer.LogAndValidate("Parametri.xml", parametri);

            var response = new EliosConfigurazioneService(parametri.UrlConfigurazione, base._protocolloSerializer, this._bindingFactory, this._protocolloLogs)
                                .Configurazione(parametri.Token);

            this._protocolloLogs.Debug("Fine recupero classifiche");

            return response.ToListaTipiClassifica();
        }

        public override List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            this._protocolloLogs.Debug("Inizio lettura protocollo");

            //raccolta dei parametri
            var parametri = new ParametriResolver
                            (
                                base.DatiProtocollo.IdComuneAlias,
                                base.DatiProtocollo.Software,
                                base.DatiProtocollo.CodiceComune,
                                base._protocolloSerializer,
                                this._verticalizzazioniFactory,
                                this._protocolloLogs,
                                this._bindingFactory
                            )
                            .Resolve();
            this._protocolloSerializer.LogAndValidate("Parametri.xml", parametri);

            var response = new EliosProtocollazioneService(parametri, base._protocolloSerializer, this._protocolloLogs, this._bindingFactory).Get(new GetRequest
            {
                Anno = Convert.ToInt32(leggiProtocolloRequest.AnnoProtocollo),
                Numero = Convert.ToInt32(leggiProtocolloRequest.NumeroProtocollo),
                ModalitaRecuperoAllegati = 3
            });

            var retVal = response.ToDatiProtocolloLetto();

            if (response.Fascicolo != null && response.Fascicolo.IdPadre > 0)
            {
                var fascicoloPadre = new EliosFascicolazioneService(parametri, this._protocolloSerializer, this._bindingFactory, this._protocolloLogs).SearchById(response.Fascicolo.IdPadre);
                retVal.NumeroPratica = $"{fascicoloPadre.Fascicoli.First().Fascicolo.NumeroFascicolo}.{retVal.NumeroPratica}";
            }

            this._protocolloLogs.Debug("Fine lettura protocollo");

            return new List<DatiProtocolloLettoResponseType>() { retVal };
        }
        public override DatiFascicoloResponseType Fascicola(Fascicolo fascicolo)
        {
            this._protocolloLogs.Debug("Inizio fascicolazione protocollo");


            //raccolta dei parametri
            var parametri = new ParametriResolver
                            (
                                base.DatiProtocollo.IdComuneAlias,
                                base.DatiProtocollo.Software,
                                base.DatiProtocollo.CodiceComune,
                                base._protocolloSerializer,
                                this._verticalizzazioniFactory,
                                this._protocolloLogs,
                                this._bindingFactory
                            )
                            .Resolve();
            this._protocolloSerializer.LogAndValidate("Parametri.xml", parametri);

            var classifica = fascicolo.Classifica.Split('.');

            var request = new FascicolaRequest
            {
                AnnoProtocollo = Convert.ToInt32(this.AnnoProtocollo),
                NumeroProtocollo = Convert.ToInt32(this.NumProtocollo),
                AnnoFascicolo = fascicolo.AnnoFascicolo.Value,
                LivelloFascicolo = 1,
                Categoria = classifica[0],
                Classe = classifica.Length > 1 ? classifica[1] : null,
                Sottoclasse = classifica.Length > 2 ? classifica[2] : null,
                Descrizione = fascicolo.Oggetto

            };

            //verifico se si tratta di fascicolo o sottofascicolo
            if (!String.IsNullOrEmpty(fascicolo.NumeroFascicolo))
            {

                var gerarchia = fascicolo.NumeroFascicolo.Split('.');
                var nuovo = gerarchia
                                .Where(x => x == "X")
                                .Any();

                request.LivelloFascicolo = gerarchia.Length;

                if (!nuovo)
                {
                    var cercaRequest = new CercaFascicoloRequest
                    {
                        Anno = fascicolo.AnnoFascicolo.Value,
                        Classifica = fascicolo.Classifica,
                        Numero = Convert.ToInt32(gerarchia.Last()),
                        Livello = request.LivelloFascicolo
                    };

                    var cercaResponse = new EliosFascicolazioneService(parametri, this._protocolloSerializer, this._bindingFactory, this._protocolloLogs).Search(cercaRequest);

                    if (cercaResponse.Fascicoli == null)
                    {
                        throw new Exception($"Trovare il fascicolo/sottofascicolo a cui associare il protocollo");
                    }

                    request.Id = cercaResponse.Fascicoli.First().Id;
                }
                else
                {
                    if (gerarchia.Length == 2)
                    {
                        var fascicoloPadre = new EliosFascicolazioneService(parametri, this._protocolloSerializer, this._bindingFactory, this._protocolloLogs).Search(new CercaFascicoloRequest
                        {
                            Anno = fascicolo.AnnoFascicolo.Value,
                            Classifica = fascicolo.Classifica,
                            Numero = Convert.ToInt32(gerarchia[0]),
                            Livello = 1
                        });

                        if (fascicoloPadre.Fascicoli == null)
                        {
                            throw new Exception($"Impossibile creare il nuovo sotto fascicolo: fascicolo padre {gerarchia[0]} inesistente");
                        }

                        request.IdPadre = fascicoloPadre.Fascicoli.First().Id;
                    }
                }
            }

            new EliosProtocollazioneService(parametri, base._protocolloSerializer, this._protocolloLogs, this._bindingFactory).Fascicola(request);

            this._protocolloLogs.Debug("Fine fascicolazione protocollo");

            return new DatiFascicoloResponseType
            {
                AnnoFascicolo = fascicolo.AnnoFascicolo.Value.ToString(),
                NumeroFascicolo = fascicolo.NumeroFascicolo,
                DataFascicolo = DateTime.Now.Date.ToString("dd/MM/yyyy")
            };
        }

        public override ListaFascicoliResponseType GetFascicoli(Fascicolo fascicolo)
        {
            this._protocolloLogs.Debug("Inizio ricerca fascicoli");

            //raccolta dei parametri
            var parametri = new ParametriResolver
                            (
                                base.DatiProtocollo.IdComuneAlias,
                                base.DatiProtocollo.Software,
                                base.DatiProtocollo.CodiceComune,
                                base._protocolloSerializer,
                                this._verticalizzazioniFactory,
                                this._protocolloLogs,
                                this._bindingFactory
                            )
                            .Resolve();

            this._protocolloSerializer.LogAndValidate("Parametri.xml", parametri);

            var gerarchia = String.IsNullOrEmpty(fascicolo.NumeroFascicolo) ? new string[0] : fascicolo.NumeroFascicolo.Split('.');

            var idPadre = (int?)null;
            var primoLivelloResponse = new CercaFascicoloResponse();

            if (gerarchia.Length > 1)
            {
                var primoLivelloRequest = new CercaFascicoloRequest
                {
                    Anno = fascicolo.AnnoFascicolo,
                    Numero = Convert.ToInt32(gerarchia[0]),
                    Classifica = fascicolo.Classifica,
                    Descrizione = fascicolo.Oggetto,
                    Livello = 1
                };

                primoLivelloResponse = new EliosFascicolazioneService(parametri, this._protocolloSerializer, this._bindingFactory, this._protocolloLogs).Search(primoLivelloRequest);

                if (primoLivelloResponse.Fascicoli == null || primoLivelloResponse.Fascicoli.Count() == 0)
                {
                    return new ListaFascicoliResponseType
                    {
                        Fascicolo = new DatiFascType[0]
                    };
                }

                idPadre = primoLivelloResponse.Fascicoli.First().Id;
            }

            var request = new CercaFascicoloRequest
            {
                Anno = fascicolo.AnnoFascicolo,
                Numero = gerarchia.Length > 0 && Int32.TryParse(gerarchia.Last(), out var intNumero) ? intNumero : (int?)null,
                Classifica = fascicolo.Classifica,
                Descrizione = fascicolo.Oggetto,
                IdPadre = idPadre,
                Livello = gerarchia.Length > 0 ? gerarchia.Length : (int?)null
            };

            var response = new EliosFascicolazioneService(parametri, this._protocolloSerializer, this._bindingFactory, this._protocolloLogs).Search(request);

            var retVal = response.ToListaFascicoli();
            if (primoLivelloResponse.Fascicoli != null && primoLivelloResponse.Fascicoli.Any())
            {
                retVal.Fascicolo[0].NumeroFascicolo = $"{primoLivelloResponse.Fascicoli.First().Fascicolo.NumeroFascicolo}.{retVal.Fascicolo[0].NumeroFascicolo}";
            }

            this._protocolloLogs.Debug("Fine ricerca fascicoli");

            return retVal;
        }

        public override DatiFascicoloResponseType CambiaFascicolo(Fascicolo fascicolo)
        {

            try
            {
                this._protocolloLogs.Debug("Inizio cambio fascicoli");

                if (String.IsNullOrEmpty(fascicolo.NumeroFascicolo) || !fascicolo.AnnoFascicolo.HasValue)
                {
                    throw new Exception("Impossibile cambiare il fasicolo senza specificare il numero, la classifica e l'anno del fascicolo da ricercare");
                }

                var parametri = new ParametriResolver
                                (
                                    base.DatiProtocollo.IdComuneAlias,
                                    base.DatiProtocollo.Software,
                                    base.DatiProtocollo.CodiceComune,
                                    base._protocolloSerializer,
                                    this._verticalizzazioniFactory,
                                this._protocolloLogs,
                                this._bindingFactory
                                )
                                .Resolve();

                this._protocolloSerializer.LogAndValidate("Parametri.xml", parametri);

                var gerarchia = fascicolo.NumeroFascicolo.Split('.');

                var request = new CambiaFascicoloRequest
                {
                    Anno = fascicolo.AnnoFascicolo,
                    Numero = Convert.ToInt32(gerarchia.Last()),
                    Classifica = fascicolo.Classifica,
                    Livello = gerarchia.Length,
                    AnnoProtocollo = this.DatiProtocollo.Istanza.DATAPROTOCOLLO.Value.Year,
                    NumeroProtocollo = Convert.ToInt32(this.DatiProtocollo.Istanza.NUMEROPROTOCOLLO),
                };

                new EliosProtocollazioneService(parametri, this._protocolloSerializer, this._protocolloLogs, this._bindingFactory).CambiaFascicolo(request);

                this._protocolloLogs.Debug("Fine cambio fascicoli");

                return new DatiFascicoloResponseType
                {
                    AnnoFascicolo = fascicolo.AnnoFascicolo.HasValue ? fascicolo.AnnoFascicolo.Value.ToString() : null,
                    DataFascicolo = fascicolo.DataFascicolo,
                    NumeroFascicolo = fascicolo.NumeroFascicolo
                };
            }
            catch (Exception ex)
            {
                return new DatiFascicoloResponseType
                {
                    Errore = new ErroreProtocolloType
                    {
                        Descrizione = ex.Message,
                        StackTrace = ex.StackTrace
                    }
                };
            }
        }

        public override DatiProtocolloFascicolatoResponseType IsFascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            this._protocolloLogs.Debug("Inizio verifica fascicolazione");

            var protocollo = this.LeggiProtocollo(new LeggiProtocolloRequest() { IdProtocollo = idProtocollo, AnnoProtocollo = annoProtocollo, NumeroProtocollo = numeroProtocollo });

            this._protocolloLogs.Debug("Fine verifica fascicolazione");

            var singoloProtocollo = protocollo.FirstOrDefault();

            return new DatiProtocolloFascicolatoResponseType
            {
                AnnoFascicolo = singoloProtocollo.AnnoNumeroPratica,
                NumeroFascicolo = singoloProtocollo.NumeroPratica,
                Fascicolato = String.IsNullOrEmpty(singoloProtocollo.NumeroPratica) ? EnumFascicolatoType.no : EnumFascicolatoType.si
            };
        }

        public override AllegatoResponseType LeggiAllegato()
        {
            this._protocolloLogs.Debug("Inizio lettura allegato");

            //raccolta dei parametri
            var parametri = new ParametriResolver
                            (
                                base.DatiProtocollo.IdComuneAlias,
                                base.DatiProtocollo.Software,
                                base.DatiProtocollo.CodiceComune,
                                base._protocolloSerializer,
                                this._verticalizzazioniFactory,
                                this._protocolloLogs,
                                this._bindingFactory
                            )
                            .Resolve();
            this._protocolloSerializer.LogAndValidate("Parametri.xml", parametri);

            var response = new EliosProtocollazioneService(parametri, base._protocolloSerializer, this._protocolloLogs, this._bindingFactory).GetAllegato(new GetAllegatoRequest
            {
                Key = IdAllegato
            });

            this._protocolloLogs.Debug("Fine lettura allegato");

            return response.ToAllOut();
        }

        private IEnumerable<Ufficio> GetUffici(DatiProtocolloIn protoIn)
        {
            var uffici = new List<Ufficio>();
            switch (protoIn.Flusso)
            {
                case "A":
                    {
                        uffici.AddRange(protoIn
                                            .Destinatari
                                            .Amministrazione
                                            .Where(x => !String.IsNullOrEmpty(x.PROT_UO))
                                            .Select(x => new Ufficio
                                            {
                                                Id = Convert.ToInt32(x.PROT_UO),
                                                Tipo = "2", // Tipo ufficio ufficio 1 = "mittente" / 2 = "destinatario" in caso di protocollo interno
                                            }));
                        break;
                    }
                case "P":
                    {
                        uffici.AddRange(protoIn
                                            .Mittenti
                                            .Amministrazione
                                            .Where(x => !String.IsNullOrEmpty(x.PROT_UO))
                                            .Select(x => new Ufficio
                                            {
                                                Id = Convert.ToInt32(x.PROT_UO),
                                                Tipo = "1", // Tipo ufficio ufficio 1 = "mittente" / 2 = "destinatario" in caso di protocollo interno
                                            }));
                        break;
                    }
                case "I":
                    {
                        uffici.AddRange(protoIn
                                            .Mittenti
                                            .Amministrazione
                                            .Where(x => !String.IsNullOrEmpty(x.PROT_UO))
                                            .Select(x => new Ufficio
                                            {
                                                Id = Convert.ToInt32(x.PROT_UO),
                                                Tipo = "1", // Tipo ufficio ufficio 1 = "mittente" / 2 = "destinatario" in caso di protocollo interno
                                            }));

                        uffici.AddRange(protoIn
                                            .Destinatari
                                            .Amministrazione
                                            .Where(x => !String.IsNullOrEmpty(x.PROT_UO))
                                            .Select(x => new Ufficio
                                            {
                                                Id = Convert.ToInt32(x.PROT_UO),
                                                Tipo = "2", // Tipo ufficio ufficio 1 = "mittente" / 2 = "destinatario" in caso di protocollo interno
                                            }));
                        break;
                    }
                default: { throw new NotImplementedException(); }
            }

            return uffici;
        }

        private string GetTipo(string flusso)
        {
            switch (flusso)
            {
                case "A": { return "Arrivo"; }
                case "P": { return "Partenza"; }
                case "I": { return "Interno"; }
                default: { throw new NotImplementedException(); }
            }
        }

        private IEnumerable<Anagrafica> GetAnagrafiche(DatiProtocolloIn protoIn)
        {
            var anagrafiche = new List<Anagrafica>();

            switch (protoIn.Flusso)
            {
                case "A":
                    {
                        if (protoIn.Mittenti.Anagrafe.Count > 0)
                        {
                            anagrafiche.AddRange(protoIn
                                .Mittenti
                                .Anagrafe
                                .Select(x => new Anagrafica
                                {
                                    CodiceFiscale = x.CODICEFISCALE,
                                    Cognome = x.TIPOANAGRAFE == "G" ? null : x.NOMINATIVO,
                                    Email = string.IsNullOrEmpty(x.PecProtocollazione) ? x.EMAIL : x.PecProtocollazione,
                                    Nome = x.TIPOANAGRAFE == "G" ? null : x.NOME,
                                    PartitaIVA = x.PARTITAIVA,
                                    RagioneSociale = x.TIPOANAGRAFE == "G" ? x.NOMINATIVO : null,
                                    TipoPersona = x.TIPOANAGRAFE == "G" ? "Giuridica" : "Fisica",
                                })
                            );
                        }

                        if (protoIn.Mittenti.Amministrazione.Count > 0)
                        {
                            anagrafiche.AddRange(protoIn
                                .Mittenti
                                .Amministrazione
                                .Select(x => new Anagrafica
                                {
                                    Email = string.IsNullOrEmpty(x.PEC) ? x.EMAIL : x.PEC,
                                    PartitaIVA = x.PARTITAIVA,
                                    RagioneSociale = x.AMMINISTRAZIONE,
                                    TipoPersona = "Giuridica",
                                })
                            );
                        }
                        break;
                    }
                case "P":
                    {
                        if (protoIn.Destinatari.Anagrafe.Count > 0)
                        {
                            anagrafiche.AddRange(protoIn
                                .Destinatari
                                .Anagrafe
                                .Select(x => new Anagrafica
                                {
                                    CodiceFiscale = x.CODICEFISCALE,
                                    Cognome = x.TIPOANAGRAFE == "G" ? null : x.NOMINATIVO,
                                    Email = string.IsNullOrEmpty(x.PecProtocollazione) ? x.EMAIL : x.PecProtocollazione,
                                    Nome = x.TIPOANAGRAFE == "G" ? null : x.NOME,
                                    PartitaIVA = x.PARTITAIVA,
                                    RagioneSociale = x.TIPOANAGRAFE == "G" ? x.NOMINATIVO : null,
                                    TipoPersona = x.TIPOANAGRAFE == "G" ? "Giuridica" : "Fisica",
                                })
                            );
                        }

                        if (protoIn.Destinatari.Amministrazione.Count > 0)
                        {
                            anagrafiche.AddRange(protoIn
                                .Destinatari
                                .Amministrazione
                                .Select(x => new Anagrafica
                                {
                                    Email = string.IsNullOrEmpty(x.PEC) ? x.EMAIL : x.PEC,
                                    PartitaIVA = x.PARTITAIVA,
                                    RagioneSociale = x.AMMINISTRAZIONE,
                                    TipoPersona = "Giuridica",
                                })
                            );
                        }
                        break;
                    }
            }

            return anagrafiche;
        }
    }
}
