using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.AmbitoRicercaIntervento;
using Init.Sigepro.FrontEnd.AppLogic.WsInterventi;
using Init.SIGePro.Manager.DTO.Common;
using Init.SIGePro.Manager.DTO.Endoprocedimenti;
using Init.SIGePro.Manager.DTO.Interventi;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi
{

    internal class WsInterventiRepository : IInterventiRepository, IInterventiV3Service
    {

        private readonly IAliasSoftwareResolver _aliasSoftwareResolver;
        private readonly InterventiServiceCreator _serviceCreator;
        private readonly IAuthenticationDataResolver _authenticationDataResolver;
        private readonly ILog _log = LogManager.GetLogger(typeof(WsInterventiRepository));

        public WsInterventiRepository(IAliasSoftwareResolver aliasSoftwareResolver, InterventiServiceCreator serviceCreator, IAuthenticationDataResolver authenticationDataResolver)
        {
            this._aliasSoftwareResolver = aliasSoftwareResolver;
            this._serviceCreator = serviceCreator;
            this._authenticationDataResolver = authenticationDataResolver;
        }

        /// <summary>
        /// Ottiene la struttura dell'albero a cui l'id nodo passato appartiene
        /// </summary>
        /// <param name="aliasComune">alias comune</param>
        /// <param name="idNodo">Id del nodo</param>
        /// <returns></returns>
        public NodoAlberoInterventiDto GetAlberaturaNodoDaId(string aliasComune, int idNodo)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                return ws.Service.GetAlberaturaDaIdNodo(ws.Token, idNodo);
            }
        }

        /// <summary>
        /// Ottiene la struttura dell'albero a cui l'id nodo passato appartiene
        /// </summary>
        /// <param name="aliasComune">alias comune</param>
        /// <param name="idNodo">Id del nodo</param>
        /// <returns></returns>
        public LinkedList<InterventoDto> GetAlberaturaNodoDaId2(int idNodo)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                var percorso = ws.Service.GetAlberaturaDaIdNodo(ws.Token, idNodo);
                var rval = new LinkedList<InterventoDto>();

                rval.AddFirst(percorso.Elemento);

                var nodiFiglio = percorso.NodiFiglio;

                while ((nodiFiglio?.Count ?? 0) > 0)
                {
                    rval.AddLast(nodiFiglio[0].Elemento);

                    nodiFiglio = nodiFiglio[0].NodiFiglio;
                }

                return rval;
            }
        }

        public InterventoDto GetDettagliIntervento(string aliasComune, int idNodo, IAmbitoRicercaIntervento ambitoRicercaDocumenti, bool leggiNoteEstese)
        {
            return this.GetDettagliIntervento(idNodo, ambitoRicercaDocumenti, leggiNoteEstese);
        }

        public async Task<TitoloInterventoDto> GetTitoloInterventoAsync(int idIntervento)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                return await ws.Service.GetTitoloInterventoAsync(ws.Token, idIntervento);
            }
        }

        public InterventoDto GetDettagliIntervento(int idNodo, IAmbitoRicercaIntervento ambitoRicercaDocumenti, bool leggiNoteEstese)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                var intervento = ws.Service.GetDettagliIntervento(ws.Token, idNodo, ambitoRicercaDocumenti.GetAmbito(), leggiNoteEstese);

                return intervento;
            }
        }


        private IEnumerable<FamigliaEndoprocedimentoDto> SeparaEndoPrincipale(IEnumerable<FamigliaEndoprocedimentoDto> famigliaRoot)
        {
            FamigliaEndoprocedimentoDto nuovaFamiglia = null;
            var listaFamiglie = new List<FamigliaEndoprocedimentoDto>();

            foreach (var famiglia in famigliaRoot)
            {
                var listaTipi = new List<TipoEndoprocedimentoDto>();

                foreach (var tipo in famiglia.TipiEndoprocedimenti)
                {
                    var listaEndo = new List<EndoprocedimentoDto>();

                    foreach (var endo in tipo.Endoprocedimenti)
                    {
                        if (!endo.Principale)
                        {
                            listaEndo.Add(endo);
                            continue;
                        }

                        nuovaFamiglia = new FamigliaEndoprocedimentoDto
                        {
                            Codice = famiglia.Codice,
                            Descrizione = famiglia.Descrizione,
                            TipiEndoprocedimenti = new List<TipoEndoprocedimentoDto>{
                                        new TipoEndoprocedimentoDto{
                                            Codice = tipo.Codice,
                                            Descrizione = tipo.Descrizione,
                                            Endoprocedimenti = new List<EndoprocedimentoDto>(){
                                                new EndoprocedimentoDto{
                                                    Codice = endo.Codice,
                                                    Descrizione = endo.Descrizione,
                                                    Principale = endo.Principale,
                                                    Richiesto = endo.Richiesto
                                                }
                                            }
                                        }
                                    }
                        };
                    }

                    if (listaEndo.Count > 0)
                    {
                        tipo.Endoprocedimenti = listaEndo;
                        listaTipi.Add(tipo);
                    }
                }

                if (listaTipi.Count > 0)
                {
                    famiglia.TipiEndoprocedimenti = listaTipi;
                    listaFamiglie.Add(famiglia);
                }
            }

            if (nuovaFamiglia != null)
                listaFamiglie.Insert(0, nuovaFamiglia);

            return listaFamiglie;
        }

        public NodoAlberoInterventiDto GetAlberoInterventi(string aliasComune, string software)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                return ws.Service.GetStrutturaAlberoInterventi(ws.Token, software);
            }
        }

        public IEnumerable<InterventoDto> GetSottonodi(string aliasComune, string software, int idnodo, IAmbitoRicercaIntervento ambitoRicerca, string codiceComune)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                return ws.Service.GetSottonodiIntervento(ws.Token, software, idnodo, ambitoRicerca.GetAmbito(), codiceComune);
            }
        }

        public async Task<IEnumerable<InterventoDto>> GetSottonodiAsync(int idnodo, IAmbitoRicercaIntervento ambitoRicerca, string codiceComune)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                return await ws.Service.GetSottonodiInterventoAsync(ws.Token, this._aliasSoftwareResolver.Software, idnodo, ambitoRicerca.GetAmbito(), codiceComune);
            }
        }

        //public IEnumerable<InterventoDto> GetSottonodi3(int idnodo, IAmbitoRicercaIntervento ambitoRicerca, string codiceComune)
        //{
        //    using (var ws = this._serviceCreator.CreateClient())
        //    {
        //        return ws.Service.GetSottonodiIntervento(ws.Token, this._aliasSoftwareResolver.Software, idnodo, ambitoRicerca.GetAmbito(), codiceComune);
        //    }
        //}

        public IEnumerable<InterventoDto> GetSottonodiDaIdAteco(string aliasComune, string software, int idNodoPadre, int idAteco, IAmbitoRicercaIntervento ambitoRicerca, string codiceComune)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                return ws.Service.GetSottonodiInterventoDaIdAteco(ws.Token, software, idNodoPadre, idAteco, ambitoRicerca.GetAmbito(), codiceComune);
            }
        }


        public IEnumerable<InterventoBreveDto> RicercaTestuale(string aliasComune, string software, string matchParziale, int matchCount, string modoRicerca, string tipoRicerca, IAmbitoRicercaIntervento ambitoRicerca)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                return ws.Service.RicercaTestualeInterventi(ws.Token, software, matchParziale, matchCount, modoRicerca, tipoRicerca, ambitoRicerca.GetAmbito());
            }
        }

        public IEnumerable<InterventoBreveDto> RicercaTestuale2(string matchParziale, int matchCount, string modoRicerca, string tipoRicerca, IAmbitoRicercaIntervento ambitoRicerca)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                return ws.Service.RicercaTestualeInterventi(ws.Token, this._aliasSoftwareResolver.Software, matchParziale, matchCount, modoRicerca, tipoRicerca, ambitoRicerca.GetAmbito());
            }
        }

        /// <summary>
        /// Dato l'id di un nodo dell'albero restituisce l'intera gerarchia dei relativi nodi padre
        /// </summary>
        /// <param name="aliasComune">alias del comune</param>
        /// <param name="idNodo">id del nodo di cui si vuole risalire la gerarchia</param>
        /// <returns>Lista contenente gli id dei nodi padre del nodo passato</returns>
        public List<int> GetIdNodiPadre(string aliasComune, int idNodo)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                return new List<int>(ws.Service.GetListaIdNodiPadreIntervento(ws.Token, idNodo));
            }
        }

        public int? GetCodiceOggettoCertificatoDiInvioDaIdIntervento(int idIntervento)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                return ws.Service.GetIdCertificatoDiInvioDomandaDaIdIntervento(ws.Token, idIntervento);
            }
        }


        /// <summary>
        /// Ottiene il codice oggetto del file di workflow collegato alla foglia dell'albero o a uno dei suoi
        /// rami padre
        /// </summary>
        /// <param name="idIntervento">Id dell'intervento da cui iniziare la ricerca</param>
        /// <returns>Codice oggetto o null se non esiste un file di workflow collegato all'intervento</returns>
        public int? GetCodiceOggettoWorkflow(int idIntervento)
        {
            if (idIntervento <= 0)
                return null;

            using (var ws = this._serviceCreator.CreateClient())
            {
                return ws.Service.GetCodiceOggettoWorkflowDaCodiceIntervento(ws.Token, idIntervento);
            }
        }

        public NodoConWorkflowDto GetNodoConWorkflowDaIdIntervento(int idIntervento)
        {
            if (idIntervento <= 0)
                return null;

            using (var ws = this._serviceCreator.CreateClient())
            {
                return ws.Service.GetNodoConWorkflowDaIdIntervento(ws.Token, idIntervento);
            }
        }


        public string EstraiDescrizioneEstesa(int idIntervento)
        {
            ClassTree<InterventoDto> strutturaAlbero = this.GetAlberaturaNodoDaId(this._aliasSoftwareResolver.AliasComune, idIntervento);

            var nomeIntervento = new StringBuilder();

            nomeIntervento.Append(strutturaAlbero.Elemento.Descrizione);

            while (strutturaAlbero.NodiFiglio != null && strutturaAlbero.NodiFiglio.Count > 0)
            {
                strutturaAlbero = strutturaAlbero.NodiFiglio[0];

                nomeIntervento.Append(Environment.NewLine);
                nomeIntervento.Append(strutturaAlbero.Elemento.Descrizione);
            }

            return nomeIntervento.ToString();
        }


        public bool EsistonoVociAttivabiliTramiteAreaRiservata(string idComune, string software)
        {
            var nodi = this.GetSottonodi(idComune, software, -1, new AmbitoRicercaAreaRiservata(false), String.Empty);

            return nodi.Any();
        }

        public int? GetidDocumentoRiepilogoDaIdIntervento(int idIntervento)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                return ws.Service.GetIdRiepilogoDomandaDaIdIntervento(ws.Token, idIntervento);
            }
        }

        public RisultatoVerificaAccessoIntervento VerificaAccessoIntervento(int idIntervento, string codiceComune)
        {
            var livello = this._authenticationDataResolver.DatiAutenticazione?.LivelloAutenticazione ?? LivelloAutenticazioneEnum.NonIdentificato;

            if (this._authenticationDataResolver.DatiAutenticazione?.DatiUtente?.UtenteTester ?? false)
            {
                return new RisultatoVerificaAccessoIntervento
                {
                    Risultato = TipoAccessibilitaIntervento.Accessibile,
                    MessaggioErrore = ""
                };
            }

            using (var ws = this._serviceCreator.CreateClient())
            {
                var livelloBo = (LivelloAutenticazioneBOEnum)((int)livello);

                return ws.Service.VerificaAccessoIntervento(ws.Token, livelloBo, idIntervento, codiceComune);
            }
        }

        public async Task<RisultatoVerificaAccessoIntervento> VerificaAccessoInterventoDomandaOnLineAsync(int idIntervento, string codiceComune)
        {
            var livello = this._authenticationDataResolver.DatiAutenticazione?.LivelloAutenticazione ?? LivelloAutenticazioneEnum.NonIdentificato;

            if (this._authenticationDataResolver.DatiAutenticazione?.DatiUtente?.UtenteTester ?? false)
            {
                return new RisultatoVerificaAccessoIntervento
                {
                    Risultato = TipoAccessibilitaIntervento.Accessibile,
                    MessaggioErrore = ""
                };
            }

            using (var ws = this._serviceCreator.CreateClient())
            {
                var livelloBo = (LivelloAutenticazioneBOEnum)((int)livello);

                return await ws.Service.VerificaAccessoInterventoDomandaOnLineAsync(ws.Token, livelloBo, idIntervento, codiceComune);
            }
        }

        public string GetNomeLivelloAutenticazionePerInterventi(int idIntervento)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                var idLivello = ws.Service.GetLivelloDiAccessoIntervento(ws.Token, idIntervento);

                return DecodeLivelloIntervento.FromIdLivello(idLivello);
            }
        }

        public bool InterventoSupportaRedirect(int codiceIntervento)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                return ws.Service.InterventoSupportaRedirect(ws.Token, codiceIntervento);
            }
        }

        public bool HaPresentatoDomandePerIntervento(int idIntervento, string codiceFiscale)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                return ws.Service.HaPresentatoDomandePerIntervento(ws.Token, idIntervento, codiceFiscale);
            }
        }

        public WorkflowV3Dto GetWorkflowDomandaOnLineByIdIntervento(int idIntervento)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                return ws.Service.GetWorkflowDomandaOnLineByIdIntervento(ws.Token, idIntervento);
            }
        }

        public async Task<WorkflowV3Dto> GetWorkflowDomandaOnLineByIdInterventoAsync(int idIntervento)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                return await ws.Service.GetWorkflowDomandaOnLineByIdInterventoAsync(ws.Token, idIntervento);
            }
        }
    }
}
