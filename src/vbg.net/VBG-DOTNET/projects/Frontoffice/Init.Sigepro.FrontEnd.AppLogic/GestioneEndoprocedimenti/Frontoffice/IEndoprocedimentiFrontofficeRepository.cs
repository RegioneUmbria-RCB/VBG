using VBG.Frontend.AppLogic.WsAnagraficheService;
using Init.Sigepro.FrontEnd.AppLogic.WsEndoFrontoffice;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.Frontoffice
{
    public interface IEndoprocedimentiFrontofficeRepository
    {
        FamigliaEndoFrontoffice[] GetListaFamiglieFrontoffice(string aliasComune, string software);
        EndoBreveFrontoffice[] GetListaEndoFrontoffice(string aliasComune, string software, int codiceCategoria);
        CategoriaEndoFrontoffice[] GetListaCategorieFrontoffice(string aliasComune, string software, int codiceFamiglia);
        RisultatoCaricamentoGerarchiaEndo CaricaGerarchiaFrontoffice(string aliasComune, int id, LivelloCaricamentoGerarchia livello);
        RisultatoRicercaTestualeEndo RicercaTestualeEndoFrontoffice(string alias, string software, string partial, TipoRicercaEnum tipoRicerca);
    }
}
