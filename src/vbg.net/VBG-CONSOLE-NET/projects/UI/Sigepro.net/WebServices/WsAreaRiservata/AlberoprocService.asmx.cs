using Init.SIGePro.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions.Token;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.DTO.Interventi;
using Init.SIGePro.Verticalizzazioni;
using Init.Utils;
using Sigepro.net.WebServices.WsAreaRiservata.Classes;
using System;
using System.Collections.Generic;
using System.Web.Services;

namespace Sigepro.net.WebServices.WsAreaRiservata
{
    /// <summary>
    /// Summary description for AlberoprocService
    /// </summary>
    [WebService(Namespace = "http://init.sigepro.it")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class AlberoprocService : System.Web.Services.WebService
    {

        [WebMethod]
        public NodoAlberoInterventiDto GetAlberaturaDaIdNodo(string token, int codiceIntervento)
        {
            var authInfo = this.CheckToken(token);

            var listaNodiAlbero = this.RisaliStrutturaAlbero(authInfo, codiceIntervento);

            return NodoAlberoInterventiDto.CreaAlberoSenzaSchedeDinamicheDaListaNodi(listaNodiAlbero);
        }

        private ClassTree<AlberoProc> RisaliStrutturaAlbero(AuthenticationInfo authInfo, int codiceIntervento)
        {
            var mgr = new AlberoProcMgr(authInfo.CreateDatabase());
            var ramoAlbero = mgr.GetById(codiceIntervento, authInfo.IdComune);

            var verticalizzazioneCart = new VerticalizzazioneCart(authInfo.Alias, ramoAlbero.SOFTWARE);

            return mgr.RisaliStrutturaAlbero(authInfo.IdComune, codiceIntervento, verticalizzazioneCart.Attiva);
        }

        [WebMethod]
        public InterventoDto GetDettagliIntervento(string token, int codiceIntervento, AmbitoRicerca ambitoRicerca)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                try
                {
                    db.Connection.Open();

                    var mgr = new AlberoProcMgr(db);
                    var idComune = authInfo.IdComune;

                    var intervento = mgr.GetById(codiceIntervento, idComune);


                    var strutturaAlbero = this.RisaliStrutturaAlbero(authInfo, codiceIntervento);
                    var listaDocumenti = mgr.GetDocumentiDaIdIntervento(idComune, codiceIntervento, ambitoRicerca);
                    var listaSchede = mgr.GetSchedeDinamicheFoDaIdIntervento(idComune, codiceIntervento);
                    var listaOneri = mgr.GetListaOneriDaIdIntervento(idComune, codiceIntervento);
                    var listaEndo = mgr.GetEndoDaIdIntervento(idComune, codiceIntervento, ambitoRicerca);
                    var listaLeggi = mgr.GetListaNormativeDaIdIntervento(idComune, codiceIntervento);
                    var listaFasi = mgr.GetListaFasiAttuativeDaIdIntervento(idComune, codiceIntervento);

                    var rVal = new InterventoDto
                    {
                        Codice = intervento.Sc_id.Value,
                        Descrizione = intervento.SC_DESCRIZIONE,
                        ScCodice = intervento.SC_CODICE,
                        HaNote = !String.IsNullOrEmpty(intervento.SC_NOTE),
                        Note = mgr.GetTestoCompletoNote(idComune, codiceIntervento),
                        Endoprocedimenti = listaEndo,
                        FasiAttuative = listaFasi,
                        Normative = listaLeggi,
                        Oneri = listaOneri
                    };

                    foreach (var schedaIntervento in listaSchede)
                        rVal.SchedeDinamiche.Add(schedaIntervento);

                    return rVal;
                }
                finally
                {
                    db.Connection.Close();
                }
            }
            //var listaOneri = new AlberoProcMgr(authInfo.CreateDatabase()).LeggiDocumentiDaIdNodo(authInfo.IdComune, codiceIntervento);
            //var listaEndo = new AlberoProcMgr(authInfo.CreateDatabase()).LeggiEndoprocedimentiDaIdNodo(authInfo.IdComune, codiceIntervento);

        }

        [WebMethod]
        public NodoAlberoInterventiDto GetStrutturaAlberoInterventi(string token, string software)
        {
            var authInfo = this.CheckToken(token);

            var filtro = new AlberoProc
            {
                Idcomune = authInfo.IdComune,
                SOFTWARE = software,
                OrderBy = "SC_CODICE ASC"
            };

            filtro.OthersWhereClause.Add("sc_attivo = 0");
            filtro.OthersWhereClause.Add("(sc_pubblica = 1 or sc_pubblica = 2)");

            var listaNodi = new AlberoProcMgr(authInfo.CreateDatabase()).GetList(filtro);
            return new GeneratoreAlberoInterventiDaListaInterventi(listaNodi).GeneraAlbero();

        }

        [WebMethod]
        public List<InterventoDto> GetSottonodiIntervento(string token, string software, int idNodo, AmbitoRicerca ambitoRicerca, string codiceComune)
        {
            var authInfo = this.CheckToken(token);

            //var verticalizzazioneCart = new VerticalizzazioneCart(authInfo.Alias, software);

            if (idNodo == -1)
            {
                idNodo = this.RootPredefinita(authInfo.Alias, software);
            }

            return new AlberoProcMgr(authInfo.CreateDatabase()).GetNodiFiglio(authInfo.IdComune, software, idNodo, ambitoRicerca, codiceComune);
        }

        [WebMethod]
        public List<InterventoDto> GetSottonodiInterventoDaIdAteco(string token, string software, int idNodoPadre, int idAteco, AmbitoRicerca ambitoRicerca, string codiceComune)
        {
            var authInfo = this.CheckToken(token);

            var idComune = authInfo.IdComune;

            // var verticalizzazioneCart = new VerticalizzazioneCart(authInfo.Alias, software);

            using (var db = authInfo.CreateDatabase())
            {
                var rVal = new AtecoMgr(db).GetListaInterventiRootDaIdAteco(idComune, idNodoPadre, idAteco, software, ambitoRicerca);

                if (rVal.Count == 0)
                {
                    return new AlberoProcMgr(db).GetNodiFiglio(idComune, software, idNodoPadre, ambitoRicerca, codiceComune);
                }

                return rVal;
            }
        }

        private int RootPredefinita(string alias, string software)
        {
            var idNodoRootNonConfigurata = -1;

            var verticalizzazioneAreaRiservata = new VerticalizzazioneAreaRiservata(alias, software);

            if (verticalizzazioneAreaRiservata.Attiva)
            {
                if (String.IsNullOrEmpty(verticalizzazioneAreaRiservata.AliasBackendEntiTerzi) || verticalizzazioneAreaRiservata.AliasBackendEntiTerzi.ToLower() == alias.ToLower())
                    return verticalizzazioneAreaRiservata.CodiceInterventoEsclusivo.GetValueOrDefault(idNodoRootNonConfigurata);
            }


            return idNodoRootNonConfigurata;
        }

        private AuthenticationInfo CheckToken(string token)
        {
            var authInfo = AuthenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new InvalidTokenException(token);

            return authInfo;
        }
    }
}
