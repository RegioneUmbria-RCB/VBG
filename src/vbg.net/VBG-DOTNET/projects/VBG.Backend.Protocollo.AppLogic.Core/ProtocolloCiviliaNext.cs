using Init.SIGePro.Data;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext;
using VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Allegati;
using VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Allegati.AggiungiAllegati;
using VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Allegati.GetAllegati;
using VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Allegati.GetAllegato;
using VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Assegnazione;
using VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.CercaPratiche;
using VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Protocollazione;
using VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Protocollazione.AnnullaProtocollo;
using VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Protocollazione.InvioProtocollo;
using VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Protocollazione.Protocolla;
using VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Titolario;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.Verticalizzazioni.Core;
using VBG.Backend.Protocollo.Verticalizzazioni.Shared;

namespace VBG.Backend.Protocollo.AppLogic.Core
{
    public class PROTOCOLLO_CIVILIANEXT : ProtocolloBase
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;

        public PROTOCOLLO_CIVILIANEXT(IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {
            var datiProtocollo = new DatiProtocolloResponseType();

            //recupero parametri
            var par = RecuperaParametri();

            //protocollazione
            var requestProtocollazione = new ProtocolloAdapter(par, protoIn).CreaRequest();
            var responseProtocollazione = new ProtocolloService(par).Protocolla(requestProtocollazione);

            try
            {
                AssegnaAutomaticamenteIlProtocollo(par, responseProtocollazione.Result.IdPratica.Value, protoIn);
            }
            catch (Exception ex)
            {
                datiProtocollo.Warning += $"Si è verificato un errore durante l'assegnazione: {ex.Message} ";
            }


            try
            {
                var allegatiPresenti = protoIn.HaAllegati();
                this._protocolloLogs.Info($"ALLEGATI PRESENTI {allegatiPresenti}");

                if (allegatiPresenti)
                {
                    this._protocolloLogs.Info("CARICAMENTO DEGLI ALLEGATI");
                    //caricamento allegati
                    var requestAllegati = new AggiungiAllegatiAdapter(par.IdRegistro, responseProtocollazione.Result.IdPratica.Value, protoIn.RecuperaAllegati().ToList(), par.MarcaAllegati).CreaRequest();
                    new AllegatiService(par).AggiungiAllegato(requestAllegati);
                }
            }
            catch (Exception ex)
            {
                datiProtocollo.Warning += $"Si è verificato un errore durante la protocollazione dei documenti: {ex.Message}";
            }

            if (protoIn.Flusso == ProtocolloConstants.COD_PARTENZA && par.InviaMail)
            {
                try
                {
                    var invioProtocolloRequest = new InvioProtocolloAdapter(par, protoIn, responseProtocollazione.Result.IdPratica.Value).CreaRequest();
                    var response = new ProtocolloService(par).InvioProtocollo(invioProtocolloRequest);
                }
                catch (Exception ex)
                {
                    datiProtocollo.Messaggio = ex.Message;
                }
            }


            datiProtocollo.AnnoProtocollo = responseProtocollazione.Result.DataRegistrazione.Value.ToString("yyyy");
            datiProtocollo.DataProtocollo = responseProtocollazione.Result.DataRegistrazione.Value.ToString("dd/MM/yyyy");
            datiProtocollo.IdProtocollo = responseProtocollazione.Result.IdPratica.Value.ToString();
            datiProtocollo.NumeroProtocollo = responseProtocollazione.Result.NumeroProtocollo.ToString();


            return datiProtocollo;
        }

        public override List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {

            //recupero parametri
            var par = RecuperaParametri();

            //creazione request
            var request = new CercaPraticheAdapter(par, leggiProtocolloRequest.IdProtocollo, leggiProtocolloRequest.NumeroProtocollo, leggiProtocolloRequest.AnnoProtocollo).Adatta();

            //lettura del protocollo
            var response = new CercaPraticheService(par).CercaPratiche(request);
            var protocolloLetto = response.Result.First();

            //lettura dei riferimenti degli allegati al protocollo
            var requestGetAllegati = new GetAllegatiAdapter(par, protocolloLetto.IdPratica).CreaRequest();
            var responseGetAllegati = new AllegatiService(par).GetAllegati(requestGetAllegati);

            var retVal = new DatiProtocolloLettoResponseType
            {
                Allegati = responseGetAllegati
                            .Result
                            .Select(x => new AllegatoResponseType
                            {
                                Commento = x.Descrizione,
                                IDBase = $"{protocolloLetto.IdPratica}-{x.Id.Value}",
                                Serial = x.NomeFile,
                            })
                            .ToArray(),
                AnnoProtocollo = protocolloLetto.DatiRegistrazioneCorrispondente.DataProtocollo.Value.Year.ToString(),
                Annullato = protocolloLetto.DatiAnnullamento.Annullata ? Shared.Enums.IsAnnullato.si.ToString() : Shared.Enums.IsAnnullato.no.ToString(),
                Classifica = protocolloLetto.Titolario,
                Classifica_Descrizione = protocolloLetto.Titolario,
                DataAnnullamento = protocolloLetto.DatiAnnullamento?.DataAnnullamento?.ToString("dd/MM/yyyy"),
                DataProtocollo = protocolloLetto.DatiRegistrazioneCorrispondente.DataProtocollo.Value.ToString("dd/MM/yyyy"),
                IdProtocollo = protocolloLetto.IdPratica.ToString(),
                MittentiDestinatari = protocolloLetto
                                        .ListaCorrispondenti
                                        .Select(x => new MittDestOutType
                                        {
                                            CognomeNome = String.IsNullOrEmpty(x.Denominazione) ? $"{x.Cognome} {x.Nome}" : x.Denominazione
                                        })
                                        .ToArray(),
                //InCaricoA_Descrizione = this.RecuperaAmministrazioneDaCodiceOrganigramma(idProtocollo.Split(Convert.ToChar("-"))[1] ),
                MotivoAnnullamento = protocolloLetto.DatiAnnullamento?.MotivoAnnullamento,
                NumeroProtocollo = protocolloLetto.DatiRegistrazioneCorrispondente.NumeroProtocollo.Value.ToString(),
                Oggetto = protocolloLetto.Oggetto,
                Origine = DecodificaFlussoProtocollo(protocolloLetto.DatiRegistrazioneCorrispondente.TipoProtocollo),
            };

            return new List<DatiProtocolloLettoResponseType>() { retVal };
        }

        public override AllegatoResponseType LeggiAllegato()
        {
            //recupero parametri
            var par = RecuperaParametri();

            var riferimenti = IdAllegato.Split(Convert.ToChar("-"));
            var idPratica = long.Parse(riferimenti[0]);
            var idAllegato = long.Parse(riferimenti[1]);

            var requestGetAllegato = new GetAllegatoAdapter(par, idPratica).CreaRequest(idAllegato);
            var responseGetAllegato = new AllegatiService(par).DownloadAllegato(requestGetAllegato);

            return new AllegatoResponseType
            {
                IDBase = IdAllegato,
                Image = Convert.FromBase64String(responseGetAllegato.Result.File),
                Commento = responseGetAllegato.Result.Descrizione,
                ContentType = responseGetAllegato.Result.MimeType,
                Serial = responseGetAllegato.Result.NomeFile,
            };
        }

        public override void AnnullaProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo, string motivoAnnullamento, string noteAnnullamento)
        {
            //recupero parametri
            var par = RecuperaParametri();

            var request = new AnnullaProtocolloAdapter(par, long.Parse(idProtocollo)).CreaRequest(motivoAnnullamento);
            new ProtocolloService(par).AnnullaProtocollo(request);
        }

        public override DatiProtocolloAnnullatoResponseType IsAnnullato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            var protocollo = LeggiProtocollo(new LeggiProtocolloRequest() { IdProtocollo = idProtocollo, AnnoProtocollo = annoProtocollo, NumeroProtocollo = numeroProtocollo });
            Enum.TryParse<EnumAnnullatoType>(protocollo.FirstOrDefault().Annullato, out var protAnnullato);

            return new DatiProtocolloAnnullatoResponseType
            {
                Annullato = protAnnullato,
                MotivoAnnullamento = protocollo.FirstOrDefault().MotivoAnnullamento,
            };
        }

        public override void AggiungiAllegati(string idProtocollo, string numeroProtocollo, DateTime? dataProtocollo, IEnumerable<ProtocolloAllegati> allegati)
        {
            var par = RecuperaParametri();

            var requestAllegati = new AggiungiAllegatiAdapter(par.IdRegistro, long.Parse(idProtocollo), allegati.ToList(), par.MarcaAllegati).CreaRequest();
            new AllegatiService(par).AggiungiAllegato(requestAllegati);
        }


        public override ListaTipiClassificaType GetClassifiche()
        {
            try
            {
                //recupero parametri
                var par = RecuperaParametri();

                var request = new EstraiTitolarioRequest();

                var response = new EstraiTitolarioService(par).EstraiTitolario(request);

                var retVal = new List<ListaTipiClassificaClassifica>();

                var titoli = response
                                .Titoli
                                .Where(x => !x.Eliminato);

                foreach (var titolo in titoli)
                {
                    var classi = titolo
                                    .Classi
                                    .Where(x => !x.Eliminato);

                    retVal.Add(new ListaTipiClassificaClassifica
                    {
                        Codice = titolo.Codice,
                        Descrizione = $"{titolo.Codice} - {titolo.Descrizione}"
                    });

                    foreach (var classe in classi)
                    {
                        retVal.Add(new ListaTipiClassificaClassifica
                        {
                            Codice = classe.Codice,
                            Descrizione = $"{classe.Codice} - {classe.Descrizione}"
                        });

                        var sottoclassi = classe
                                            .SottoClassi
                                            .Where(c => !c.Eliminato)
                                            .Select(f => new ListaTipiClassificaClassifica
                                            {
                                                Codice = f.Codice,
                                                Descrizione = $"{f.Codice} - {f.Descrizione}",
                                            })
                                            .ToArray();
                        if (sottoclassi.Count() > 0)
                        {
                            retVal.AddRange(sottoclassi);
                        }
                    }
                }

                if (retVal.Count() == 0)
                {
                    throw new Exception("Non è stato trovato nessun titolario in corso di validità");
                }

                return new ListaTipiClassificaType
                {
                    Classifica = retVal.ToArray(),
                    Errore = null
                };
            }
            catch (Exception ex)
            {
                return new ListaTipiClassificaType
                {
                    Errore = new ErroreProtocolloType
                    {
                        Descrizione = ex.Message,
                        StackTrace = ex.StackTrace
                    }
                };
            }
        }

        private void AssegnaAutomaticamenteIlProtocollo(ParametriRegoleInfo parametri, long idPratica, DatiProtocolloIn datiProtocollo)
        {
            var request = new AssegnaPraticaAdapter(parametri, idPratica, datiProtocollo).Adatta();
            new AssegnaPraticaService(parametri).AssegnaPratica(request);
        }

        private ParametriRegoleInfo RecuperaParametri()
        {
            return new ParametriRegoleInfoAdapter(_protocolloLogs, _protocolloSerializer, DatiProtocollo.Token, DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune, Operatore, this._verticalizzazioniFactory).Adatta();
        }

        private string DecodificaFlussoProtocollo(string tipoProtocollo)
        {
            switch (tipoProtocollo.ToUpper())
            {
                case "INGRESSO":
                    {
                        return ProtocolloConstants.COD_ARRIVO;
                    }
                case "USCITA":
                    {
                        return ProtocolloConstants.COD_PARTENZA;
                    }
                case "INTERNO":
                    {
                        return ProtocolloConstants.COD_INTERNO;
                    }
            }
            return null;
        }
    }
}
