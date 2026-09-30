using Init.SIGePro.Data;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.IOC;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{
    public partial class CCICalcoliDettaglioTMgr
    {


        #region Classi helper per il calcolo delle superfici

        #endregion


        public static List<CCICalcoliDettaglioT> Find(string token, int idCalcolo)
        {
            var authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

            var mgr = new CCICalcoliDettaglioTMgr(authInfo.CreateDatabase());

            var filtro = new CCICalcoliDettaglioT();
            filtro.Idcomune = authInfo.IdComune;
            filtro.FkCcicId = idCalcolo;

            filtro.OrderBy = "ORDINE ASC";

            return mgr.GetList(filtro);
        }

        private CCICalcoliDettaglioT DataIntegrations(CCICalcoliDettaglioT cls)
        {
            if (cls.Ordine.GetValueOrDefault(int.MinValue) == int.MinValue)
            {
                var where = new List<KeyValuePair<string, object>>{
                                                                    new KeyValuePair<string,object>( "FK_CCIC_ID", cls.FkCcicId )
                                                                 };


                var ordine = this.FindMax("ORDINE", cls.DataTableName, cls.Idcomune, where);

                cls.Ordine = ordine;
            }


            return cls;
        }

        public void AggiornaSU(string idComune, int idTestata)
        {
            var mgr = new CCICalcoliDettaglioRMgr(this.db);
            var filtro = new CCICalcoliDettaglioR();
            filtro.Idcomune = idComune;
            filtro.FkCcicdtId = idTestata;

            var lista = mgr.GetList(filtro);

            var totale = 0.0m;

            lista.ForEach(delegate (CCICalcoliDettaglioR r)
            {
                var tmp = r.Qta.GetValueOrDefault(int.MinValue) * r.Lung.GetValueOrDefault(0.0m) * r.Larg.GetValueOrDefault(0.0m);

                if (tmp > 0.0m)
                    totale += tmp;
            });

            var cls = this.GetById(idComune, idTestata);
            cls.Su = totale;

            this.Update(cls);
        }

        private void EffettuaCancellazioneACascata(CCICalcoliDettaglioT cls)
        {
            var a = new CCICalcoliDettaglioR();
            a.Idcomune = cls.Idcomune;
            a.FkCcicdtId = cls.Id;

            var lCalcoloR = new CCICalcoliDettaglioRMgr(this.db).GetList(a);
            foreach (var calcoloR in lCalcoloR)
            {
                var mgr = new CCICalcoliDettaglioRMgr(this.db);
                mgr.Delete(calcoloR);
            }
        }
    }
}
