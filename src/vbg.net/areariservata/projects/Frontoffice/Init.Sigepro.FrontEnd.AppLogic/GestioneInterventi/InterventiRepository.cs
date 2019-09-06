using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.AmbitoRicercaIntervento;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.Infrastructure.Caching;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi
{
    internal class WsInterventiRepository : WsAreaRiservataRepositoryBase, IInterventiRepository
    {
        const string SESSION_KEY_ALBERO = "SESSION_KEY_ALBERO_";

        IAliasSoftwareResolver _aliasSoftwareResolver;

        ILog _log = LogManager.GetLogger(typeof(WsInterventiRepository));

        public WsInterventiRepository(IAliasSoftwareResolver aliasSoftwareResolver, AreaRiservataServiceCreator sc)
            : base(sc)
        {
            this._aliasSoftwareResolver = aliasSoftwareResolver;
        }

        /// <summary>
        /// Ottiene la struttura dell'albero a cui l'id nodo passato appartiene
        /// </summary>
        /// <param name="aliasComune">alias comune</param>
        /// <param name="idNodo">Id del nodo</param>
        /// <returns></returns>
        public NodoAlberoInterventiDto GetAlberaturaNodoDaId(string aliasComune, int idNodo)
        {
            using (var ws = _serviceCreator.CreateClient(aliasComune))
            {
                return ws.Service.GetAlberaturaDaIdNodo(ws.Token, idNodo);
            }

        }

        public InterventoDto GetDettagliIntervento(string aliasComune, int idNodo, IAmbitoRicercaIntervento ambitoRicercaDocumenti)
        {
            using (var ws = _serviceCreator.CreateClient(aliasComune))
            {
                var intervento = ws.Service.GetDettagliIntervento(ws.Token, idNodo, ambitoRicercaDocumenti.GetAmbito());

                if (intervento.Endoprocedimenti.Length > 0)
                {
                    intervento.Endoprocedimenti = SeparaEndoPrincipale(intervento.Endoprocedimenti);
                }

                return intervento;
            }
        }

        private FamigliaEndoprocedimentoDto[] SeparaEndoPrincipale(FamigliaEndoprocedimentoDto[] famigliaRoot)
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
                            TipiEndoprocedimenti = new TipoEndoprocedimentoDto[]{
										new TipoEndoprocedimentoDto{
											Codice = tipo.Codice,
											Descrizione = tipo.Descrizione,
											Endoprocedimenti = new EndoprocedimentoDto[]{
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
                        tipo.Endoprocedimenti = listaEndo.ToArray();
                        listaTipi.Add(tipo);
                    }
                }

                if (listaTipi.Count > 0)
                {
                    famiglia.TipiEndoprocedimenti = listaTipi.ToArray();
                    listaFamiglie.Add(famiglia);
                }
            }

            if (nuovaFamiglia != null)
                listaFamiglie.Insert(0, nuovaFamiglia);

            return listaFamiglie.ToArray();
        }

        public NodoAlberoInterventiDto GetAlberoInterventi(string aliasComune, string software)
        {
            var key = SESSION_KEY_ALBERO + software;

            if (!SessionHelper.KeyExists(key))
            {
                using (var ws = _serviceCreator.CreateClient(aliasComune))
                {
                    var value = ws.Service.GetStrutturaAlberoInterventi(ws.Token, software);

                    SessionHelper.AddEntry(key, value);
                }
            }

            return SessionHelper.GetEntry<NodoAlberoInterventiDto>(key);
        }

        public InterventoDto[] GetSottonodi(string aliasComune, string software, int idnodo, IAmbitoRicercaIntervento ambitoRicerca)
        {
            using (var ws = _serviceCreator.CreateClient(aliasComune))
            {
                return ws.Service.GetSottonodiIntervento(ws.Token, software, idnodo, ambitoRicerca.GetAmbito());
            }
        }

        public InterventoDto[] GetSottonodiDaIdAteco(string aliasComune, string software, int idNodoPadre, int idAteco, IAmbitoRicercaIntervento ambitoRicerca )
        {
            using (var ws = _serviceCreator.CreateClient(aliasComune))
            {
                return ws.Service.GetSottonodiInterventoDaIdAteco(ws.Token, software, idNodoPadre, idAteco, ambitoRicerca.GetAmbito());
            }
        }


        public BaseDtoOfInt32String[] RicercaTestuale(string aliasComune, string software, string matchParziale, int matchCount, string modoRicerca, string tipoRicerca, IAmbitoRicercaIntervento ambitoRicerca)
        {
            using (var ws = _serviceCreator.CreateClient(aliasComune))
            {
                return ws.Service.RicercaTestualeInterventi(ws.Token, software, matchParziale, matchCount, modoRicerca, tipoRicerca, ambitoRicerca.GetAmbito());
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
            using (var ws = _serviceCreator.CreateClient(aliasComune))
            {
                return new List<int>(ws.Service.GetListaIdNodiPadreIntervento(ws.Token, idNodo));
            }
        }

        public int? GetCodiceOggettoCertificatoDiInvioDaIdIntervento(int idIntervento)
        {
            using (var ws = _serviceCreator.CreateClient(_aliasSoftwareResolver.AliasComune))
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
            using (var ws = _serviceCreator.CreateClient(_aliasSoftwareResolver.AliasComune))
            {
                return ws.Service.GetCodiceOggettoWorkflowDaCodiceIntervento(ws.Token, idIntervento);
            }
        }


        public string EstraiDescrizioneEstesa(int idIntervento)
        {
            ClassTreeOfInterventoDto strutturaAlbero = this.GetAlberaturaNodoDaId(_aliasSoftwareResolver.AliasComune, idIntervento);

            var nomeIntervento = new StringBuilder();

            nomeIntervento.Append(strutturaAlbero.Elemento.Descrizione);

            while (strutturaAlbero.NodiFiglio != null && strutturaAlbero.NodiFiglio.Length > 0)
            {
                strutturaAlbero = strutturaAlbero.NodiFiglio[0];

                nomeIntervento.Append(Environment.NewLine);
                nomeIntervento.Append(strutturaAlbero.Elemento.Descrizione);
            }

            return nomeIntervento.ToString();
        }


        public bool EsistonoVociAttivabiliTramiteAreaRiservata(string idComune, string software)
        {
            var nodi = this.GetSottonodi(idComune, software, -1, new AmbitoRicercaAreaRiservata(false));

            if (nodi.Length > 0)
                return true;

            return false;
        }

        public int? GetidDocumentoRiepilogoDaIdIntervento(int idIntervento)
        {
            using (var ws = _serviceCreator.CreateClient(_aliasSoftwareResolver.AliasComune))
            {
                return ws.Service.GetIdRiepilogoDomandaDaIdIntervento(ws.Token, idIntervento);
            }
        }

        public RisultatoVerificaAccessoIntervento VerificaAccessoIntervento(int idIntervento, LivelloAutenticazioneEnum livelloAutenticazione, bool utenteTester)
        {
            if (utenteTester)
            {
                return RisultatoVerificaAccessoIntervento.Accessibile;
            }

            using (var ws = _serviceCreator.CreateClient(_aliasSoftwareResolver.AliasComune))
            {
                var livello = (LivelloAutenticazioneBOEnum)((int)livelloAutenticazione);

                return ws.Service.VerificaAccessoIntervento(ws.Token, livello, idIntervento);
            }
        }

        public string GetNomeLivelloAutenticazionePerInterventi(int idIntervento)
        {
            using (var ws = _serviceCreator.CreateClient(_aliasSoftwareResolver.AliasComune))
            {
                var idLivello = ws.Service.GetLivelloDiAccessoIntervento(ws.Token, idIntervento);

                return DecodeLivelloIntervento.FromIdLivello(idLivello);
            }
        }

        public bool InterventoSupportaRedirect(int codiceIntervento)
        {
            using (var ws = _serviceCreator.CreateClient(_aliasSoftwareResolver.AliasComune))
            {
                return ws.Service.InterventoSupportaRedirect(ws.Token, codiceIntervento);
            }
        }

        public bool HaPresentatoDomandePerIntervento(int idIntervento, string codiceFiscale)
        {
            using (var ws = _serviceCreator.CreateClient(_aliasSoftwareResolver.AliasComune))
            {
                return ws.Service.HaPresentatoDomandePerIntervento(ws.Token, idIntervento, codiceFiscale);
            }
        }

    }
}
