using Init.SIGePro.Data;
using Init.SIGePro.Manager.DTO.Interventi;
using System.Collections.Generic;
using System.Linq;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Interventi
{
    internal class GeneratoreAlberoInterventiDaListaInterventi
    {
        private readonly List<AlberoProc> m_listaInterventi;

        public GeneratoreAlberoInterventiDaListaInterventi(List<AlberoProc> listaInterventi)
        {
            this.m_listaInterventi = listaInterventi;
        }

        public NodoAlberoInterventiDto GeneraAlbero()
        {
            var query = this.CercaSottonodi("");

            var root = new NodoAlberoInterventiDto();

            root.Elemento.Codice = -1;
            root.Elemento.Descrizione = "Root";

            foreach (var el in query)
            {
                root.NodiFiglio = this.PopolaNodiFiglio("");
            }

            return root;
        }

        private List<ClassTree<InterventoDto>> PopolaNodiFiglio(string codicePadre)
        {
            var rVal = new List<ClassTree<InterventoDto>>();

            var query = this.CercaSottonodi(codicePadre);

            foreach (var el in query)
            {
                var item = new NodoAlberoInterventiDto();

                item.Elemento.Codice = el.Sc_id.Value;
                item.Elemento.Descrizione = el.SC_DESCRIZIONE;
                item.Elemento.Note = el.SC_NOTE;

                item.NodiFiglio = this.PopolaNodiFiglio(el.SC_CODICE);

                rVal.Add(item);
            }

            return rVal;
        }


        private IEnumerable<AlberoProc> CercaSottonodi(string codicePadre)
        {
            var query = from AlberoProc ap in this.m_listaInterventi
                        where ap.SC_CODICE.Length == codicePadre.Length + 2 && ap.SC_CODICE.StartsWith(codicePadre)
                        select ap;

            return query;
        }


    }
}
