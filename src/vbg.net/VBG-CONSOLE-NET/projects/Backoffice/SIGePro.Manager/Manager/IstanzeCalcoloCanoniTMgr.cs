using Init.SIGePro.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{
    public partial class IstanzeCalcoloCanoniTMgr
    {
        public List<IstanzeCalcoloCanoniT> GetList(string idComune, int codiceIstanza)
        {
            IstanzeCalcoloCanoniT filtro = new IstanzeCalcoloCanoniT();
            filtro.Idcomune = idComune;
            filtro.Codiceistanza = codiceIstanza;
            filtro.OrderBy = "DESCRIZIONE";

            return this.db.GetClassList(filtro);
        }

        private void EffettuaCancellazioneACascata(IstanzeCalcoloCanoniT cls)
        {
            IstanzeCalcoloCanoniD filtro = new IstanzeCalcoloCanoniD();
            filtro.Idcomune = cls.Idcomune;
            filtro.FkIdtestata = cls.Id;

            List<IstanzeCalcoloCanoniD> l = new IstanzeCalcoloCanoniDMgr(this.db).GetList(filtro);
            foreach (IstanzeCalcoloCanoniD icc in l)
            {
                IstanzeCalcoloCanoniDMgr mgr = new IstanzeCalcoloCanoniDMgr(this.db);
                mgr.Delete(icc);
            }

            IstanzeCalcoloCanoniO filtroOneri = new IstanzeCalcoloCanoniO();
            filtroOneri.Idcomune = cls.Idcomune;
            filtroOneri.FkIdtestata = cls.Id;

            List<IstanzeCalcoloCanoniO> o = new IstanzeCalcoloCanoniOMgr(this.db).GetList(filtroOneri);
            foreach (IstanzeCalcoloCanoniO icc in o)
            {
                IstanzeCalcoloCanoniOMgr mgr = new IstanzeCalcoloCanoniOMgr(this.db);
                mgr.Delete(icc);
            }
        }
    }
}
