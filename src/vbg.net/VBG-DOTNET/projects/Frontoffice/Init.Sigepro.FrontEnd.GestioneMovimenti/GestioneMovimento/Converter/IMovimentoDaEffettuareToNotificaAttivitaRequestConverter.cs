using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.Converter
{
    public interface IMovimentoDaEffettuareToNotificaAttivitaRequestConverter
    {
        NotificaAttivitaRequest Convert(int idMovimentoDaEffettuare);
    }

    public class MovimentoDaEffettuareToNotificaAttivitaRequestConverter : IMovimentoDaEffettuareToNotificaAttivitaRequestConverter
    {
        private static class Constants
        {
            public const string NomeElementoSchedeDinamiche = "SCHEDE_MOVIMENTO";
            public const string PrefissoElementiDatiDinamici = "FO_DYN2DATO_";
            public const string NOME_UTENTE_LOGGATO = "METADATO_MOVIMENTO_NOME_UTENTE_LOGGATO";
            public const string CF_UTENTE_LOGGATO = "METADATO_MOVIMENTO_CF_UTENTE_LOGGATO";
        }

        private readonly IMovimentiDaEffettuareRepository _movimentiDaEffettuareRepository;
        private readonly IMovimentiDiOrigineRepository _movimentoDiOrigineRepository;
        private readonly IAuthenticationDataResolver _authenticationDataResolver;

        public MovimentoDaEffettuareToNotificaAttivitaRequestConverter(IMovimentiDaEffettuareRepository movimentiDaEffettuareRepository, IMovimentiDiOrigineRepository movimentoDiOrigineRepository, IAuthenticationDataResolver authenticationDataResolver)
        {
            this._movimentiDaEffettuareRepository = movimentiDaEffettuareRepository;
            this._movimentoDiOrigineRepository = movimentoDiOrigineRepository;
            this._authenticationDataResolver = authenticationDataResolver;
        }

        public NotificaAttivitaRequest Convert(int idMovimentoDaEffettuare)
        {
            var datiMovimento = this._movimentiDaEffettuareRepository.GetById(idMovimentoDaEffettuare);
            var movimentoDiOrigine = this._movimentoDiOrigineRepository.GetById(datiMovimento);

            return new NotificaAttivitaRequest
            {
                rifPraticaDestinatario = new RiferimentiPraticaType
                {
                    idPratica = movimentoDiOrigine.DatiIstanza.CodiceIstanza.ToString()	// codiceistanza 
                },
                datiAttivita = new DettaglioAttivitaType
                {
                    idPratica = movimentoDiOrigine.DatiIstanza.CodiceIstanza.ToString(),	// codice istanza 
                    idAttivita = datiMovimento.Id.ToString(),		// id del movimento da effettuare
                    dataAttivita = DateTime.Now,						// Data in cui viene effettuatoil movimento
                    dataAttivitaSpecified = true,
                    tipoAttivita = new TipoAttivitaType
                    {
                        codice = datiMovimento.IdTipoAttivita.ToString(),			//	tipomovimento
                        descrizione = datiMovimento.NomeAttivita			//	descrizione
                    },
                    esito = true,	// sempre true
                    note = datiMovimento.Note,
                    procedimenti = this.GetProcedimenti(datiMovimento, movimentoDiOrigine),
                    documenti = datiMovimento.Allegati.Select(x =>
                        new DocumentiType
                        {
                            id = x.IdAllegato.ToString(),
                            tipoDocumento = "Altro",
                            documento = x.Descrizione,
                            allegati = new AllegatiType
                            {
                                allegato = x.Note,
                                file = null,
                                id = x.IdAllegato.ToString()
                            }
                        })
                        .Union(datiMovimento.GetRiepiloghiSchedeDinamiche(movimentoDiOrigine.SchedeDinamiche)
                                                .Where(x => x.CodiceOggetto.HasValue)
                                                .Select(x => new DocumentiType
                                                {
                                                    id = x.CodiceOggetto.Value.ToString(),
                                                    tipoDocumento = "Altro",
                                                    documento = "Riepilogo della scheda \"" + x.NomeScheda + "\"",
                                                    allegati = new AllegatiType
                                                    {
                                                        allegato = x.NomeFile,
                                                        file = null,
                                                        id = x.CodiceOggetto.Value.ToString()
                                                    }
                                                }))
                        .ToArray(),
                    documentiDaSostituire = this.GetSostituzioniDocumentali(datiMovimento.SostituzioniDocumentali),
                    altriDati = this.GetSchedeDelMovimento(datiMovimento)
                                        .Union(this.GetValoriDatiDinamiciDelMovimento(datiMovimento))
                                        .Union(this.GetDatiUtenteCheEffettuaMovimento())
                                        .ToArray()
                }

            };
        }

        private SostituzioniDocumentaliType[] GetSostituzioniDocumentali(IEnumerable<SostituzioneDocumentale> sostituzioni)
        {
            return sostituzioni.Select(s => new SostituzioniDocumentaliType
            {
                tipoDocumentoPratica = s.TipoDocumento == TipoSostituzioneDocumentaleEnum.Endo ? SostituzioniDocumentaliTypeTipoDocumentoPratica.DOC_ENDO : SostituzioniDocumentaliTypeTipoDocumentoPratica.DOC_ISTANZA,
                documentoDaSostituire = s.CodiceOggettoOrigine.ToString(),
                nuovoDocumento = new AllegatiType
                {
                    id = s.CodiceOggettoSostitutivo.ToString(),
                    file = new AllegatoBinarioType
                    {
                        fileName = s.NomeFileSostitutivo
                    }
                }
            }).ToArray();
        }

        private ProcedimentoType[] GetProcedimenti(MovimentoDaEffettuare datiMovimento, MovimentoDiOrigine movimentoDiOrigine)
        {
            if (!movimentoDiOrigine.HaProcedimento)
                return null;

            return new ProcedimentoType[]{
                new() {
                    codice = movimentoDiOrigine.CodiceProcedimento,
                    descrizione = movimentoDiOrigine.Procedimento,
                    principale = true,
                    principaleSpecified = true
                }
            };
        }

        private ParametroType[] GetSchedeDelMovimento(MovimentoDaEffettuare datiMovimento)
        {
            return new ParametroType[]{
                new() {
                    nome = Constants.NomeElementoSchedeDinamiche,
                    valore = datiMovimento.RiepiloghiSchedeDinamiche.Select(x => new ValoreParametroType
                    {
                        codice = x.IdScheda.ToString(),
                        descrizione = x.IdScheda.ToString()
                    }).ToArray()
                }
            };
        }


        private ParametroType[] GetValoriDatiDinamiciDelMovimento(MovimentoDaEffettuare datiMovimento)
        {
            return datiMovimento.ValoriSchedeDinamiche
                                .GroupBy(x => x.Id)
                                .Select(grp => new ParametroType
                                {
                                    nome = String.Concat(Constants.PrefissoElementiDatiDinamici, grp.Key.ToString()),
                                    valore = grp.Select(x => new ValoreParametroType
                                    {
                                        codice = x.Valore,
                                        descrizione = x.ValoreDecodificato
                                    }).ToArray()
                                })
                                .ToArray();
        }

        private ParametroType[] GetDatiUtenteCheEffettuaMovimento()
        {
            return [

                this.CreaParametroType(Constants.NOME_UTENTE_LOGGATO, this._authenticationDataResolver.DatiAutenticazione?.FullUserName() ?? "Non definito"),
                this.CreaParametroType(Constants.CF_UTENTE_LOGGATO, this._authenticationDataResolver.DatiAutenticazione?.DatiUtente?.Codicefiscale ?? "Non definito"),
            ];
        }

        private ParametroType CreaParametroType(string chiave, string valore)
        {
            return new ParametroType
            {
                nome = chiave,
                valore = new ValoreParametroType[]
                    {
                        new() {
                            codice = valore,
                            descrizione = valore
                        }
                    }
            };
        }
    }
}
