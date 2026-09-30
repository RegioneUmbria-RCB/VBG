using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.DTO.Common;
using Init.SIGePro.Manager.DTO.Endoprocedimenti;
using Init.SIGePro.Manager.Logic.GestioneEndoprocedimenti;
using log4net;
using System.ServiceModel.Activation;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Endoprocedimenti
{


    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WsEndoprocedimenti" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WsEndoprocedimenti.svc or WsEndoprocedimenti.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class WsEndoprocedimenti : WcfServiceBase, IWsEndoprocedimenti
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(WsEndoprocedimenti));

        public WsEndoprocedimenti(IAuthenticationManager authenticationManager, ITransientAuthenticationInfoResolver transientAuthenticationInfoResolver) : base(authenticationManager, transientAuthenticationInfoResolver)
        {
        }

        /*
        public List<EndoBreveFrontoffice> GetEndoprocedimentiPropostiDaCodiceIntervento(string token, int codiceIntervento)
        {
            var authInfo = CheckToken(token);

            using (var db = authInfo.CreateDatabase())
                return new InventarioProcedimentiMgr(db).GetEndoprocedimentiPropostiDaCodiceIntervento(authInfo.IdComune, codiceIntervento).ToList();
        }
        */

        public EndoprocedimentoDto GetEndoprocedimentoById(string token, int codiceEndo, AmbitoRicerca ambitoRicercaDocumenti)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var idComune = authInfo.IdComune;

                var mgr = new InventarioProcedimentiMgr(db);

                var sigEndo = mgr.GetById(authInfo.IdComune, codiceEndo);

                if (sigEndo == null)
                    return new EndoprocedimentoDto();

                var amministrazione = new AmministrazioniMgr(db).GetById(idComune, sigEndo.Amministrazione.GetValueOrDefault(-999));
                var tipoMovimento = new TipiMovimentoMgr(db).GetById(String.IsNullOrEmpty(sigEndo.Tipomovimento) ? "-999" : sigEndo.Tipomovimento, idComune);
                var natura = new NaturaEndoMgr(db).GetById(idComune, sigEndo.Codicenatura.GetValueOrDefault(-1));
                var tempificazione = new TempificazioniMgr(db).GetById(idComune, sigEndo.Tempificazione.GetValueOrDefault(-1));
                var tipoEndo = new TipiEndoMgr(db).GetById(sigEndo.Codicetipo.GetValueOrDefault(-1), idComune);
                var testiEstesi = new TestiEstesiMgr(db).GetByCodiceInventario(idComune, sigEndo.Codiceinventario.Value);
                var allegati = new AllegatiMgr(db).GetByCodiceInventario(idComune, sigEndo.Codiceinventario.Value, ambitoRicercaDocumenti);
                var normative = new InventarioprocLeggiMgr(db).GetByCodiceInventario(idComune, sigEndo.Codiceinventario.Value);
                var oneri = mgr.GetOneriDaCodiceEndo(idComune, codiceEndo);


                var endo = new EndoprocedimentoDto
                {
                    Codice = sigEndo.Codiceinventario.Value,
                    Adempimenti = sigEndo.Adempimenti,
                    Amministrazione = amministrazione == null ? String.Empty : amministrazione.AMMINISTRAZIONE,
                    Applicazione = sigEndo.Campoapplicazione,
                    DatiGenerali = sigEndo.Datigenerali,
                    Descrizione = sigEndo.Procedimento,
                    Movimento = tipoMovimento == null ? String.Empty : tipoMovimento.Movimento,
                    CodiceNatura = natura == null ? (int?)null : Convert.ToInt32(natura.CODICENATURA),
                    Natura = natura == null ? String.Empty : natura.NATURA,
                    BinarioDipendenze = natura == null ? 0 : Convert.ToInt32(natura.BINARIODIPENDENZE),
                    NormativaNazionale = sigEndo.Normativana,
                    NormativaRegionale = sigEndo.Normativare,
                    NormativaUE = sigEndo.Normativaue,
                    Regolamenti = sigEndo.Regolamenti,
                    Tempificazione = tempificazione == null ? String.Empty : tempificazione.Tempificazione,
                    Tipologia = tipoEndo == null ? String.Empty : tipoEndo.Tipo,
                    UltimoAggiornamento = sigEndo.Dataaggiornamento,
                    Normative = normative,
                    Oneri = oneri,
                    TipoTitoloObbligatorio = sigEndo.FlagTipiTitolo.GetValueOrDefault(0) == 1
                };

                var normativeMgr = new NormativeMgr(db);

                for (int i = 0; i < testiEstesi.Count; i++)
                {
                    var el = testiEstesi[i];

                    var normativa = normativeMgr.GetById(idComune, el.Tiponorma.GetValueOrDefault(-999));

                    var te = new TestiEstesiDto
                    {
                        Codice = el.Id.Value,
                        Descrizione = el.Normativa,
                        Normativa = normativa == null ? String.Empty : normativa.Normativa,
                        CodiceOggetto = el.Codiceoggetto,
                        Link = el.Indirizzoweb
                    };

                    endo.TestiEstesi.Add(te);
                }

                for (int i = 0; i < allegati.Count; i++)
                {
                    var el = allegati[i];

                    var al = new AllegatoDto
                    {
                        Codice = el.Id.Value,
                        Descrizione = el.Allegato,
                        Link = el.Indirizzoweb,
                        CodiceOggetto = el.Codiceoggetto,
                        FormatiDownload = el.FoTipodownload
                    };

                    endo.Allegati.Add(al);
                }

                return endo;
            }
        }
        /*
        public List<EndoprocedimentoDto> GetEndoprocedimentiList(string token, List<int> listaId)
        {
            var ai = CheckToken(token);

            using (var db = ai.CreateDatabase())
            {
                var endoSigepro = new InventarioProcedimentiMgr(db).GetEndoprocedimentiList(ai.IdComune, listaId);

                var rVal = new List<EndoprocedimentoDto>();

                endoSigepro.ForEach(sigEndo =>
                {
                    var natura = new NaturaEndoMgr(db).GetById(ai.IdComune, sigEndo.Codicenatura.GetValueOrDefault(-1));

                    rVal.Add(new EndoprocedimentoDto
                    {
                        Codice = sigEndo.Codiceinventario.Value,
                        Adempimenti = sigEndo.Adempimenti,
                        Applicazione = sigEndo.Campoapplicazione,
                        DatiGenerali = sigEndo.Datigenerali,
                        CodiceNatura = natura == null ? (int?)null : Convert.ToInt32(natura.CODICENATURA),
                        Natura = natura == null ? String.Empty : natura.NATURA,
                        BinarioDipendenze = natura == null ? 0 : Convert.ToInt32(natura.BINARIODIPENDENZE),
                        Descrizione = sigEndo.Procedimento,
                        NormativaNazionale = sigEndo.Normativana,
                        NormativaRegionale = sigEndo.Normativare,
                        NormativaUE = sigEndo.Normativaue,
                        Regolamenti = sigEndo.Regolamenti,
                        TipoTitoloObbligatorio = sigEndo.FlagTipiTitolo.GetValueOrDefault(0) == 1
                    });
                });

                return rVal;
            }
        }
        */
        public EndoprocedimentiAreaRiservataDto GetListaEndoDaIdIntervento(string token, string codiceComune, int codiceIntervento, bool utenteTester)
        {
            try
            {
                var ai = this.CheckToken(token);

                using (var db = ai.CreateDatabase())
                {
                    var endoprocedimentiService = new EndoprocedimentiAreaRiservataService(db, ai.IdComune);

                    return endoprocedimentiService.GetListaEndoDaIdInterventoECodiceComune(codiceComune, codiceIntervento, utenteTester);
                }
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore nella chiamata a GetListaEndoDaIdIntervento con token {0} e codiceIntervento {1}:\r\n{2}", token, codiceIntervento, ex.ToString());

                throw;
            }
        }
        /*
        public List<TipiTitoloDto> GetTipiTitoloEndoDaCodiceInventario(string token, int codiceInventario)
        {
            var authInfo = CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var list = new InventarioProcTipiTitoloMgr(db).GetTipiTitoloDaCodiceInventario(authInfo.IdComune, codiceInventario);

                return list.Select(x => x.ToTipiTitoloDto()).ToList();
            }
        }
        */


        public List<TipiTitoloPerCodiceInventario> GetTipiTitoloEndoDaListaCodiciInventario(string token, List<int> listaCodiciInventario)
        {
            var authInfo = this.CheckToken(token);

            var rVal = new List<TipiTitoloPerCodiceInventario>();

            using (var db = authInfo.CreateDatabase())
            {
                foreach (var codiceInventario in listaCodiciInventario)
                {
                    var listaTitoli = new InventarioProcTipiTitoloMgr(db).GetTipiTitoloDaCodiceInventario(authInfo.IdComune, codiceInventario);

                    if (listaTitoli.Count == 0)
                        continue;

                    rVal.Add(new TipiTitoloPerCodiceInventario
                    {
                        CodiceInventario = codiceInventario,
                        TipiTitolo = listaTitoli.Select(x => x.ToTipiTitoloDto()).ToList()
                    });
                }
            }

            return rVal;
        }

        public TipiTitoloDto GetTipoTitoloById(string token, int codiceTipoTitolo)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                return new InventarioProcTipiTitoloMgr(db).GetById(authInfo.IdComune, codiceTipoTitolo).ToTipiTitoloDto();
            }
        }


        public EndoprocedimentoIncompatibileDto[] GetEndoprocedimentiIncompatibili(string token, int[] listaIdEndoAttivati)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var incomp = new InventarioProcedimentiIncompMgr(db, authInfo.IdComune).GetListByListaCodiciEndo(listaIdEndoAttivati);

                return incomp.Select(x => new EndoprocedimentoIncompatibileDto
                {
                    CodiceEndoprocedimento = Convert.ToInt32(x.CODICEINVENTARIO),
                    CodiceEndoprocedimentoIncompatibile = Convert.ToInt32(x.CODICEINCOMPATIBILE)
                }).ToArray();
            }
        }

        public NaturaBaseEndoprocedimentoDto GetNaturaBaseDaidEndoprocedimento(string token, int idEndoprocedimento)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var natura = new InventarioProcedimentiMgr(db).GetNaturaBaseDaIdEndoprocedimento(authInfo.IdComune, idEndoprocedimento);

                return new NaturaBaseEndoprocedimentoDto
                {
                    Natura = natura
                };
            }

        }

        public List<AllegatiPerEndoprocedimentoDto> GetAllegatiEndoprocedimentiAreaRiservata(string token, List<int> codiciEndoSelezionati)
        {
            var authInfo = this.CheckToken(token);
            var ambitoRicercaDocumenti = AmbitoRicerca.AreaRiservata;
            var flagPubblicaAccettati = FiltroRicercaFlagPubblica.Get(ambitoRicercaDocumenti).Split(',').AsEnumerable();

            using (var db = authInfo.CreateDatabase())
            {
                var allegatiMgr = new AllegatiMgr(db);
                var oggettiMgr = new OggettiMgr(db);
                var rVal = new List<AllegatiPerEndoprocedimentoDto>();

                foreach (var codiceInventario in codiciEndoSelezionati)
                {
                    var allegati = allegatiMgr.GetByCodiceInventario(authInfo.IdComune, codiceInventario, ambitoRicercaDocumenti)
                                              .Where(all => flagPubblicaAccettati.Contains(all.Pubblica.GetValueOrDefault(0).ToString()))
                                              .Select(all => new AllegatoPerAreaRiservataDto
                                              {
                                                  Codice = all.Id.Value,
                                                  CodiceOggetto = all.Codiceoggetto,
                                                  Descrizione = all.Allegato,
                                                  FormatiDownload = all.FoTipodownload,
                                                  Link = all.Indirizzoweb,
                                                  DimensioneMassima = all.FoDimensioneMassima.GetValueOrDefault(0),
                                                  EstensioniAmmesse = all.FoEstensioniAmmesse,
                                                  NomeFile = all.Codiceoggetto.HasValue ? oggettiMgr.GetNomeFile(authInfo.IdComune, all.Codiceoggetto.Value) : null,
                                                  NoteFrontend = all.NoteFrontend,
                                                  Ordine = all.Ordine.GetValueOrDefault(0),
                                                  Richiedefirma = all.FoRichiedefirma.GetValueOrDefault(0) == 1,
                                                  Richiesto = all.Richiesto.GetValueOrDefault(0) == 1
                                              });

                    rVal.Add(new AllegatiPerEndoprocedimentoDto
                    {
                        CodiceInventario = codiceInventario,
                        Allegati = allegati.ToList()
                    });
                }

                return rVal;
            }
        }

        public EndoprocedimentoMappatoDto GetEndoprocedimentoDaIdMappatura(string token, string codiceEndoRemoto)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var idComune = authInfo.IdComune;
                var mgr = new InventarioProcedimentiMgr(db);
                var endo = mgr.GetByIdEndoprocedimentoMappato(idComune, codiceEndoRemoto);

                if (endo == null)
                {
                    return null;
                }

                var schede = mgr.FvgGetSchedeDinamicheDaEndoprocedimento(idComune, endo.Codiceinventario.Value).ToList();
                var allegati = new AllegatiMgr(db).GetByCodiceInventario(idComune, endo.Codiceinventario.Value, AmbitoRicerca.AreaRiservata);

                return new EndoprocedimentoMappatoDto
                {
                    Id = endo.Codiceinventario.Value,
                    Descrizione = endo.Procedimento,
                    DataUltimaModifica = endo.Dataaggiornamento,
                    Schede = schede,
                    Allegati = allegati.Where(x => x.Codiceoggetto.HasValue)
                                        .Select(x => new AllegatoDto
                                        {
                                            Codice = x.Id.Value,
                                            Descrizione = x.Allegato,
                                            CodiceOggetto = x.Codiceoggetto,
                                            FormatiDownload = String.Empty,
                                            Link = String.Empty
                                        }).ToList()
                };
            }
        }
    }
}
