using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.DTO.DatiDinamici;
using Init.SIGePro.Manager.DTO.DatiDinamici.OnceOnly;
using Init.SIGePro.Manager.Logic.DatiDinamici;
using Init.SIGePro.Manager.Logic.DatiDinamici.ConfigurazioneSchede.SchedeCollegateACampi;
using Init.SIGePro.Manager.Logic.DatiDinamici.DataAccess.StrutturaModello;
using Init.SIGePro.Manager.Logic.DatiDinamici.RicercheSigepro;
using Init.SIGePro.Manager.Logic.GestioneDecodifiche;
using Init.SIGePro.Manager.Logic.GestioneDomandaOnLine;
using System.ServiceModel.Activation;
using VBG.DatiDinamici.Standard.Utils.CreazioneModelli.Serializables;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.DatiDinamici
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WsDatiDinamici" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WsDatiDinamici.svc or WsDatiDinamici.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public partial class WsDatiDinamici : WcfServiceBase, IWsDatiDinamici
    {
        private readonly IDomandaOnlineService _domandeOnlineService;

        public WsDatiDinamici(IDomandaOnlineService domandeOnlineService, IAuthenticationManager authenticationManager, ITransientAuthenticationInfoResolver transientAuthenticationInfoResolver) : base(authenticationManager, transientAuthenticationInfoResolver)
        {
            this._domandeOnlineService = domandeOnlineService;
        }

        public DecodificaDto[] GetDecodificheAttive(string token, string tabella)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                return new DecodificheService(db, authInfo.IdComune).GetDecodificheAttive(tabella)
                    .Select(x => new DecodificaDto
                    {
                        Chiave = x.Chiave,
                        FlgDisabilitato = x.FlgDisabilitato,
                        Idcomune = x.Idcomune,
                        Raggruppamento = x.Raggruppamento,
                        Tabella = x.Tabella,
                        Valore = x.Valore
                    })
                    .ToArray();
            }
        }

        public ListaModelliDinamiciDomandaDto GetModelliDinamiciDaInterventoEEndo(string token, GetModelliDinamiciDaInterventoEEndoRequest request)
        {
            var authInfo = this.CheckToken(token);

            // Leggo l'albero degli interventi
            var db = authInfo.CreateDatabase();

            var alberoProcMgr = new AlberoProcMgr(db);
            var inventarioProcMgr = new InventarioProcedimentiMgr(db);

            var rVal = new ListaModelliDinamiciDomandaDto();

            if (request.CodiceIntervento <= 0)
            {
                rVal.SchedeIntervento = Enumerable.Empty<SchedaDinamicaInterventoDto>().ToList();
            }
            else
            {
                rVal.SchedeIntervento = alberoProcMgr.GetSchedeDinamicheFoDaIdIntervento(authInfo.IdComune, request.CodiceIntervento);
            }

            rVal.SchedeEndoprocedimenti = inventarioProcMgr.GetSchedeDinamicheDaEndoprocedimentiList(authInfo.IdComune, request.ListaEndo, request.ListaTipiLocalizzazioni, request.IgnoraTipiLocalizzazione);

            return rVal;
        }

        public StrutturaModelloDinamicoSerializzabileDto GetStrutturaModelloDinamico(string token, int idModello)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                return new StrutturModelloDinamicoService(db, authInfo.IdComune).GetStrutturModello(idModello);
            }
        }

        public AutocompleteSearchResultDto GetCompletionListRicerchePlus(string token, int idCampo, string partial, List<ValoreFiltroRicercaDto> filtri)
        {
            var authInfo = this.CheckToken(token);

            var f = filtri.Select(x => new ValoreFiltroRicerca
            {
                nome = x.nome,
                val = x.val
            });

            return new RicercheDatiDinamiciService(authInfo).GetCompletionList(idCampo, partial, f);
        }

        public RisultatoRicercaDatiDinamiciDto InitializeControlRicerchePlus(string token, int idCampo, string valore)
        {
            var authInfo = this.CheckToken(token);

            var val = new RicercheDatiDinamiciService(authInfo).InitializeControl(idCampo, valore);

            return val == null ? null : new RisultatoRicercaDatiDinamiciDto
            {
                Label = val.Label,
                Value = val.Value
            };
        }

        public IstanzeDyn2Dati[] GetDyn2DatiByIdModello(string token, int codiceIstanza, int idModello, int indiceCampo)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                return new IstanzeDyn2DatiMgr(db).GetDyn2DatiByIdModello(authInfo.IdComune, codiceIstanza, idModello, indiceCampo);
            }
        }

        public IstanzeDyn2Dati[] GetDyn2DatiByCodiceIstanza(string token, int codiceIstanza)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                return new IstanzeDyn2DatiMgr(db).GetListByCodiceIstanza(authInfo.IdComune, codiceIstanza);
            }
        }

        public void RecuperaDocumentiIstanzaCollegata(string token, int codiceIstanzaOrigine, int idDomandaDestinazione)
        {
            var authInfo = this.CheckToken(token);

            this._domandeOnlineService.RecuperaDocumentiIstanzaCollegata(codiceIstanzaOrigine, idDomandaDestinazione);
        }

        public FonteDatiOnceOnlyDto[] GetIdentificativiOnceOnlyByIdInterventoEndo(string token, int idIntervento, int[] listaIdEndo)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var mgr = new IstanzeDyn2DatiMgr(db);

                return mgr.GetIdentificativiOnceOnlyByIdInterventoEndo(authInfo.IdComune, idIntervento, listaIdEndo);
            }
        }

        public int? GetIdCampoDaNome(string token, string software, string nomeCampo)
        {
            var authInfo = this.CheckToken(token);
            using (var db = authInfo.CreateDatabase())
            {
                var mgr = new CampiManager(db, authInfo.IdComune);

                return mgr.GetIdCampoDaNome(software, nomeCampo);
            }
        }

        public int? GetIdModelloDaCodice(string token, string software, string codiceModello)
        {
            var authInfo = this.CheckToken(token);
            using (var db = authInfo.CreateDatabase())
            {
                var mgr = new ModelliManager(db, authInfo.IdComune);

                return mgr.GetIdModelloDaCodice(software, codiceModello);
            }
        }

        public bool VerificaEsistenzaModelloDinamico(string token, int idModello)
        {
            var authInfo = this.CheckToken(token);
            using (var db = authInfo.CreateDatabase())
            {
                var mgr = new ModelliManager(db, authInfo.IdComune);

                return mgr.VerificaEsistenzaModelloDinamico(idModello);
            }
        }

        public IEnumerable<int> GetIdSchedeDaMarcareComeNonCompilate(string token, int idModello, IEnumerable<int> listaSchedeDellaDomanda)
        {
            var authInfo = this.CheckToken(token);


            var service = new SchedeCollegateACampiService(new TemporaryAuthInfoResolver(authInfo));
            return service.GetListaSchedeCollegateDaIdModello(idModello, listaSchedeDellaDomanda);
        }
    }
}
