using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;

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

            return db.GetClassList(filtro).ToList<IstanzeCalcoloCanoniT>();
        }

        private void EffettuaCancellazioneACascata(IstanzeCalcoloCanoniT cls)
        {
            IstanzeCalcoloCanoniD filtro = new IstanzeCalcoloCanoniD();
            filtro.Idcomune = cls.Idcomune;
            filtro.FkIdtestata = cls.Id;

            List<IstanzeCalcoloCanoniD> l = new IstanzeCalcoloCanoniDMgr(db).GetList(filtro);
            foreach (IstanzeCalcoloCanoniD icc in l)
            {
                IstanzeCalcoloCanoniDMgr mgr = new IstanzeCalcoloCanoniDMgr(db);
                mgr.Delete(icc);
            }

            IstanzeCalcoloCanoniO filtroOneri = new IstanzeCalcoloCanoniO();
            filtroOneri.Idcomune = cls.Idcomune;
            filtroOneri.FkIdtestata = cls.Id;

            List<IstanzeCalcoloCanoniO> o = new IstanzeCalcoloCanoniOMgr(db).GetList(filtroOneri);
            foreach (IstanzeCalcoloCanoniO icc in o)
            {
                IstanzeCalcoloCanoniOMgr mgr = new IstanzeCalcoloCanoniOMgr(db);
                mgr.Delete(icc);
            }
        }
    }
}
