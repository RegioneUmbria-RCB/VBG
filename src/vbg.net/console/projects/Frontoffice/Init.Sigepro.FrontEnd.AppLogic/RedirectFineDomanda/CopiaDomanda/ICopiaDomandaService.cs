using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.RedirectFineDomanda.CopiaDomanda
{
    public class AnagraficaDaCopiare : IAnagraficaDaCopiare
    {
        public int Id { get; private set; }
        public int TipoSoggetto { get; private set; }
        public string DescrizioneTipoSoggetto { get; private set; }
        public bool RichiedeAnagraficaCollegata { get; private set; }

        public AnagraficaDaCopiare(int id, int tipoSoggetto, string descrizioneTipoSoggetto, bool richiedeAnagraficaCollegata)
        {
            this.Id = id;
            this.TipoSoggetto = tipoSoggetto;
            this.DescrizioneTipoSoggetto = descrizioneTipoSoggetto;
            this.RichiedeAnagraficaCollegata = richiedeAnagraficaCollegata;
        }
    }

    public class AllegatoDaCopiare
    {
        public int Id;
    }

    public class ElementiDaCopiare
    {
        public IEnumerable<IAnagraficaDaCopiare> Anagrafiche;
        public IEnumerable<AllegatoDaCopiare> Allegati;
    }

    public interface ICopiaDatiDomandaService
    {
        void CopiaDatiDomanda(int idDomandaOrigine, int idDomandaDestinazione, ElementiDaCopiare elementiDaCopiare);
    }
}
