using VBG.Shared.Infrastructure.ServiceModel;
using SIGePro.Manager.VerticalizzazioniBase;
using VBG.Backend.Protocollo.AppLogic.Core.ItalSoft;
using VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.FascicolaProtocollo;
using VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.GetAllegato;
using VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.GetElencoFascicoli;
using VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.GetFascicoliProtocollo;
using VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.GetProtocollo;
using VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.InsertDocumento;
using VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.NotificaMailProtocollo;
using VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.PutProtocollo;
using VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.Token;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.Verticalizzazioni.Core;
using VBG.Backend.Protocollo.Verticalizzazioni.Shared;

namespace VBG.Backend.Protocollo.AppLogic.Core
{
    public class PROTOCOLLO_ITALSOFT : ProtocolloBase
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly IBindingFactory _bindingFactory;

        public PROTOCOLLO_ITALSOFT(IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._bindingFactory = bindingFactory;
        }

        public override void InizializzaProtocolloBase(ResolveDatiProtocollazioneService datiProtocolloService)
        {
            base.InizializzaProtocolloBase(datiProtocolloService);
            base._protocolloSerializer = new ItalSoftSerializer(this._protocolloLogs, this._protocolloValidation);
        }
        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {
            //1. Recupero i parametri
            Parametri parametri = GetPrarametri();

            //2. Precarico gli allegati
            var allegati = this.PrecaricaAllegati(parametri, protoIn.RecuperaAllegati());

            //3. Chiamo il servizio di protocollazione
            var service = new PutProtocolloService(base._protocolloLogs, base._protocolloSerializer, _bindingFactory, parametri.UrlProtocollazione);

            var wsRequest = new PutProtocolloRequest
            {
                AllegatiProtocollo = allegati
                                        .Select(x => new AllegatoProtocollo
                                        {
                                            Id = x.Id,
                                            ImprontaHash = x.ImprontaHash,
                                            Nome = x.NomeFile,
                                            Tipo = x.Tipo,
                                            Estensione = x.Estensione,
                                            PreCaricato = true,
                                            MettiAllaFirma = false
                                        })
                                        .ToList(),
                Classifica = protoIn.Classifica,
                Destinatari = this.GetSoggetti(protoIn.Destinatari),
                Firmatari = this.RecuperaFirmatari(protoIn),
                Mittenti = this.GetSoggetti(protoIn.Mittenti),
                Oggetto = protoIn.Oggetto,
                TipoDocumento = protoIn.TipoDocumento,
                TipoProtocollo = protoIn.Flusso == "I" ? "C" : protoIn.Flusso,
                Token = parametri.Token,
                Ufficio = parametri.CodiceUfficio
            };

            var response = service.PutProtocollo(wsRequest);

            if (!response.Esito.Ok)
            {
                throw new Exception(response.Esito.Descrizione);
            }

            //4. Verifica invio mail
            if (parametri.IndirizziEmailAbilitati && protoIn.Flusso == "P")
            {
                new NotificaMailProtocolloService(base._protocolloLogs, base._protocolloSerializer, _bindingFactory, parametri.UrlProtocollazione)
                    .NotificaMailProtocollo(new NotificaMailProtocolloRequest
                    {
                        Token = parametri.Token,
                        Anno = response.Anno,
                        Numero = response.Numero,
                        Tipo = response.Tipo,
                        Corpo = parametri.CorpoMail,
                        Oggetto = parametri.OggettoMail
                    });
            }

            //5. Annullo il token
            AnnullaToken(parametri);

            //4. Ritorno i dati del protocollo
            return new DatiProtocolloResponseType
            {
                AnnoProtocollo = response.Anno,
                DataProtocollo = $"{response.Data.Substring(6, 2)}/{response.Data.Substring(4, 2)}/{response.Data.Substring(0, 4)}",
                IdProtocollo = $"{response.Id}-{response.Tipo}",
                NumeroProtocollo = response.Numero
            };
        }
        public override List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            //Il metodo leggiprotocollo vuole per forza anche il TIPO che noi non salviamo o che comunque potrei non avere se gli operatori hanno inserito manualmente i riferimenti del protocollo
            //Verrà tentata una lettura del protocollo per tutti e tre i tipi ( A, P, I ) se si ottiene un risultato univoco allora ritorno il protocllo altrimenti errore
            //I protocolli staccati da noi non hanno quel problema perchè come id protocollo salvo anche il tipo e lo recupero da lì

            //1. Recupero i parametri
            var verticalizzazioneItalsoft = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloItalSoft>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune);
            var verticalizzazioneProtocolloAttivo = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloAttivo>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune);

            var parametri = new ParametriService(base._protocolloLogs, this._protocolloSerializer, _bindingFactory, verticalizzazioneItalsoft, verticalizzazioneProtocolloAttivo).Parametri;

            var response = new GetProtocolloService(base._protocolloLogs, base._protocolloSerializer, _bindingFactory, parametri.UrlProtocollazione).GetProtocollo(new GetProtocolloRequest
            {
                Anno = leggiProtocolloRequest.AnnoProtocollo,
                Numero = leggiProtocolloRequest.NumeroProtocollo,
                Token = parametri.Token,
                IdComposto = IdProtocollo
            });

            var fascicolo = this.IsFascicolato(leggiProtocolloRequest.IdProtocollo, leggiProtocolloRequest.AnnoProtocollo, leggiProtocolloRequest.NumeroProtocollo);

            AnnullaToken(parametri);

            return new List<DatiProtocolloLettoResponseType>() {
                new DatiProtocolloLettoResponseType
                {
                    NumeroPratica = fascicolo.Fascicolato == EnumFascicolatoType.si ? $"{fascicolo.NumeroFascicolo} - {fascicolo.Oggetto}" : null,
                    AnnoProtocollo = response.Anno,
                    DataProtocollo = $"{response.Data.Substring(6, 2)}/{response.Data.Substring(4, 2)}/{response.Data.Substring(0, 4)} {response.Ora}",
                    Oggetto = response.Oggetto,
                    Origine = response.Tipo == "C" ? "I" : response.Tipo,
                    InCaricoA = response.Tipo == "A" ? response.Destinatari?.First()?.Codice : response.Mittenti?.First()?.Codice,
                    InCaricoA_Descrizione = response.Tipo == "A" ? response.Destinatari?.First()?.Denominazione : response.Mittenti?.First()?.Denominazione,
                    MittentiDestinatari = response.Tipo == "A"
                                            ? response
                                                .Mittenti?
                                                .Select(x => new MittDestOutType
                                                {
                                                    IdSoggetto = x.Codice,
                                                    CognomeNome = x.Denominazione
                                                })
                                                .ToArray()
                                            : response
                                                .Destinatari?
                                                .Select(x => new MittDestOutType
                                                {
                                                    IdSoggetto = x.Codice,
                                                    CognomeNome = x.Denominazione
                                                })
                                                .ToArray(),
                    NumeroProtocollo = response.Numero,
                    Classifica = $"{response.Classifica.Codice} - {response.Classifica.Descrizione}",
                    Allegati = response
                                .Allegati?
                                .Select(x => new AllegatoResponseType
                                {
                                    IDBase = x.Id,
                                    Serial = x.Nome,
                                    Commento = x.Nome,
                                    TipoFile = Path.GetExtension(x.Nome)
                                })
                                .ToArray()
                }
            };
        }
        public override DatiFascicoloResponseType Fascicola(Fascicolo fascicolo)
        {
            //1. Recupero i parametri
            var verticalizzazioneItalsoft = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloItalSoft>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune);
            var verticalizzazioneProtocolloAttivo = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloAttivo>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune);
            var parametri = new ParametriService(base._protocolloLogs, this._protocolloSerializer, _bindingFactory, verticalizzazioneItalsoft, verticalizzazioneProtocolloAttivo).Parametri;

            //2. Faccio la lettura del protocollo per determinare il tipo protocollo
            var protocollo = new GetProtocolloService(base._protocolloLogs, base._protocolloSerializer, _bindingFactory, parametri.UrlProtocollazione).GetProtocollo(new GetProtocolloRequest
            {
                Anno = this.AnnoProtocollo,
                Numero = this.NumProtocollo,
                Token = parametri.Token,
                IdComposto = this.IdProtocollo
            });

            var response = new FascicolaProtocolloService(base._protocolloLogs, base._protocolloSerializer, _bindingFactory, parametri.UrlFascicolazione).FascicolaProtocollo(new FascicolaProtocolloRequest
            {
                AnnoProtocollo = this.AnnoProtocollo,
                Fascicolo = fascicolo.NumeroFascicolo,
                NumeroProtocollo = this.NumProtocollo,
                SottoFascicolo = "",
                TipoProtocollo = protocollo.Tipo,
                Token = parametri.Token
            });

            AnnullaToken(parametri);

            if (!response.Esito.Ok)
            {
                throw new Exception(response.Esito.Descrizione);
            }

            return new DatiFascicoloResponseType
            {
                NumeroFascicolo = response.CodiceFascicolo
            };
        }
        public override DatiProtocolloFascicolatoResponseType IsFascicolato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            //1. Recupero i parametri
            var verticalizzazioneItalsoft = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloItalSoft>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune);
            var verticalizzazioneProtocolloAttivo = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloAttivo>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune);
            var parametri = new ParametriService(base._protocolloLogs, this._protocolloSerializer, _bindingFactory, verticalizzazioneItalsoft, verticalizzazioneProtocolloAttivo).Parametri;

            //2. Faccio la lettura del protocollo per determinare il tipo protocollo
            var protocollo = new GetProtocolloService(base._protocolloLogs, base._protocolloSerializer, _bindingFactory, parametri.UrlProtocollazione).GetProtocollo(new GetProtocolloRequest
            {
                Anno = annoProtocollo,
                Numero = numeroProtocollo,
                Token = parametri.Token,
                IdComposto = idProtocollo
            });

            var response = new GetFascicoliProtocolloService(base._protocolloLogs, base._protocolloSerializer, _bindingFactory, parametri.UrlFascicolazione).GetFascicoliProtocollo(new GetFascicoliProtocolloRequest
            {
                AnnoProtocollo = annoProtocollo,
                NumeroProtocollo = numeroProtocollo,
                TipoProtocollo = protocollo.Tipo,
                Token = parametri.Token
            });

            AnnullaToken(parametri);

            if (!response.Esito.Ok)
            {
                throw new Exception(response.Esito.Descrizione);
            }

            if (!response.Fascicoli.Any())
            {

                return new DatiProtocolloFascicolatoResponseType
                {
                    Fascicolato = EnumFascicolatoType.no
                };
            }

            var fascicolo = response.Fascicoli.Any(x => x.Principale)
                                ? response.Fascicoli.First(x => x.Principale)
                                : response.Fascicoli.First();

            return new DatiProtocolloFascicolatoResponseType
            {
                Classifica = fascicolo.Titolario,
                Fascicolato = EnumFascicolatoType.si,
                NumeroFascicolo = fascicolo.Codice,
                Oggetto = fascicolo.Descrizione
            };
        }
        public override ListaFascicoliResponseType GetFascicoli(Fascicolo fascicolo)
        {
            //1. Recupero i parametri
            var verticalizzazioneItalsoft = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloItalSoft>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune);
            var verticalizzazioneProtocolloAttivo = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloAttivo>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune);
            var parametri = new ParametriService(base._protocolloLogs, this._protocolloSerializer, _bindingFactory, verticalizzazioneItalsoft, verticalizzazioneProtocolloAttivo).Parametri;

            var response = new GetElencoFascicoliService(base._protocolloLogs, base._protocolloSerializer, _bindingFactory, parametri.UrlFascicolazione).GetElencoFascicoli(new GetElencoFascicoliRequest
            {
                Anno = fascicolo.AnnoFascicolo,
                Classifica = fascicolo.Classifica,
                Numero = fascicolo.NumeroFascicolo,
                Oggetto = fascicolo.Oggetto,
                Token = parametri.Token
            });

            AnnullaToken(parametri);

            if (!response.Esito.Ok)
            {
                throw new Exception(response.Esito.Descrizione);
            }

            return new ListaFascicoliResponseType
            {
                Fascicolo = response
                                .Fascicoli
                                .Select(x => new DatiFascType
                                {
                                    ClassificaFascicolo = x.CodiceClassifica,
                                    DataFascicolo = x.Data,
                                    NumeroFascicolo = x.Codice,
                                    OggettoFascicolo = x.Descrizione
                                })
                                .ToArray()
            };
        }

        private List<Core.ItalSoft.Firmatario> RecuperaFirmatari(DatiProtocolloIn dati)
        {
            if (dati.Flusso == "A")
            {
                return null;
            }

            return dati
                    .Mittenti
                    .Amministrazione?
                    .Where(x => !String.IsNullOrEmpty(x.PROT_UO))?
                    .Select(x => new Core.ItalSoft.Firmatario
                    {
                        CodiceSoggetto = x.PROT_RUOLO,
                        CodiceUfficio = x.PROT_UO

                    })
                    .ToList();
        }
        private Parametri GetPrarametri()
        {
            var verticalizzazioneItalsoft = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloItalSoft>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune);
            var verticalizzazioneProtocolloAttivo = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloAttivo>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune);

            return new ParametriService(base._protocolloLogs, this._protocolloSerializer, _bindingFactory, verticalizzazioneItalsoft, verticalizzazioneProtocolloAttivo).Parametri;

        }
        public override AllegatoResponseType LeggiAllegato()
        {
            //1. Recupero i parametri
            var verticalizzazioneItalsoft = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloItalSoft>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune);
            var verticalizzazioneProtocolloAttivo = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloAttivo>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune);

            var parametri = new ParametriService(base._protocolloLogs, this._protocolloSerializer, _bindingFactory, verticalizzazioneItalsoft, verticalizzazioneProtocolloAttivo).Parametri;

            var response = new GetAllegatoService(base._protocolloLogs, base._protocolloSerializer, _bindingFactory, parametri.UrlProtocollazione).GetAllegato(new GetAllegatoRequest
            {
                Id = IdAllegato,
                Token = parametri.Token
            });

            AnnullaToken(parametri);

            return new AllegatoResponseType
            {
                IDBase = response.Id,
                Serial = response.Nome,
                Commento = response.Nome,
                Image = response.Content,
                TipoFile = Path.GetExtension(response.Nome)
            };
        }
        private List<InsertDocumentoResponse> PrecaricaAllegati(Parametri parametri, IEnumerable<ProtocolloAllegati> allegati)
        {
            if (allegati == null || !allegati.Any())
            {
                return new List<InsertDocumentoResponse>();
            }

            var service = new InsertDocumentoService(base._protocolloLogs, base._protocolloSerializer, _bindingFactory, parametri.UrlProtocollazione);
            var allegatiPrecaricati = new List<InsertDocumentoResponse>();
            foreach (var allegato in allegati)
            {
                allegatiPrecaricati.Add(
                    service.InsertDocumento(new InsertDocumentoRequest
                    {
                        Token = parametri.Token,
                        NomeFile = allegato.NOMEFILE,
                        Content = allegato.OGGETTO,
                        Estensione = allegato.Extension
                    })
                );
            }

            return allegatiPrecaricati;

        }
        private List<Soggetto> GetSoggetti(ListaMittDest elenco)
        {
            if (elenco == null || (!elenco.Anagrafe.Any() && !elenco.Amministrazione.Any()))
            {
                return new List<Soggetto>();
            }

            var soggetti = new List<Soggetto>();

            if (elenco.Anagrafe.Any())
            {
                soggetti.AddRange(elenco
                                    .Anagrafe
                                    .Select(x => new Soggetto
                                    {
                                        CAP = x.CAP,
                                        Citta = x.CITTA,
                                        CodiceFiscale = x.PARTITAIVA ?? x.CODICEFISCALE,
                                        Denominazione = $"{x.NOMINATIVO} {x.NOME}".Trim(),
                                        Email = x.PecProtocollazione,
                                        Indirizzo = x.INDIRIZZO,
                                        Provincia = x.PROVINCIA,
                                        Ufficio = null
                                    }));
            }

            if (elenco.Amministrazione.Any())
            {
                soggetti.AddRange(elenco
                                    .Amministrazione
                                    .Select(x => new Soggetto
                                    {
                                        CAP = x.CAP,
                                        Citta = x.CITTA,
                                        CodiceFiscale = x.PARTITAIVA,
                                        Denominazione = x.AMMINISTRAZIONE,
                                        Email = x.PEC ?? x.EMAIL,
                                        Indirizzo = x.INDIRIZZO,
                                        Provincia = x.PROVINCIA,
                                        Ufficio = x.PROT_UO
                                    }));
            }

            return soggetti;
        }
        private void AnnullaToken(Parametri parametri)
        {
            new TokenService(_protocolloLogs, _protocolloSerializer, _bindingFactory, parametri.UrlProtocollazione).DestroyToken(new DestroyTokenRequest
            {
                DomaniCode = parametri.DomainCode,
                Token = parametri.Token
            });
        }
    }
}
