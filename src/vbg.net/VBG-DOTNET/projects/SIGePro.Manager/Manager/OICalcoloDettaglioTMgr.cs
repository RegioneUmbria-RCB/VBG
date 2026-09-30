using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.IOC;
using System.Collections.Generic;
using System.ComponentModel;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class OICalcoloDettaglioTMgr
    {

        public static List<OICalcoloDettaglioT> Find(string token, int idCalcolo)
        {
            AuthenticationInfo authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

            OICalcoloDettaglioTMgr mgr = new OICalcoloDettaglioTMgr(authInfo.CreateDatabase());

            OICalcoloDettaglioT filtro = new OICalcoloDettaglioT();
            filtro.Idcomune = authInfo.IdComune;
            filtro.FkOicId = idCalcolo;
            filtro.OrderBy = "id asc";

            return mgr.GetList(filtro);
        }

        private OICalcoloDettaglioT DataIntegrations(OICalcoloDettaglioT cls)
        {
            if (cls.Ordine.GetValueOrDefault(int.MinValue) == int.MinValue)
            {
                var where = new List<KeyValuePair<string, object>>{
                                                                    new KeyValuePair<string,object>( "FK_OIC_ID", cls.FkOicId )
                                                                 };

                int ordine = this.FindMax("ORDINE", cls.DataTableName, cls.Idcomune, where);

                cls.Ordine = ordine;
            }


            return cls;
        }


        public bool AltezzaRichiesta(string idComune, int idTCalcolo)
        {
            OICalcoloDettaglioT t = this.GetById(idComune, idTCalcolo);
            ODestinazioni dest = new ODestinazioniMgr(this.db).GetById(idComune, t.FkOdeId.GetValueOrDefault(int.MinValue));

            Istanze istanza = new IstanzeMgr(this.db).GetById(idComune, t.Codiceistanza.Value);

            OConfigurazione cfg = new OConfigurazioneMgr(this.db).GetById(idComune, istanza.SOFTWARE);

            return cfg.FkTumUmidMc == dest.FkTumUmid;
        }


        private List<OICalcoloDettaglioR> GetDettagli(OICalcoloDettaglioT cls)
        {
            OICalcoloDettaglioRMgr mgr = new OICalcoloDettaglioRMgr(this.db);

            OICalcoloDettaglioR filtro = new OICalcoloDettaglioR();

            filtro.Idcomune = cls.Idcomune;
            filtro.FkOicdtId = cls.Id;

            return mgr.GetList(filtro);
        }

        private void EffettuaCancellazioneACascata(OICalcoloDettaglioT cls)
        {
            OICalcoloDettaglioRMgr mgr = new OICalcoloDettaglioRMgr(this.db);

            List<OICalcoloDettaglioR> l = this.GetDettagli(cls);

            l.ForEach(delegate (OICalcoloDettaglioR dett) { mgr.Delete(dett); });
        }

        public void RicalcolaCubaturaTotale(string idComune, int id)
        {
            OICalcoloDettaglioT cls = this.GetById(idComune, id);
            List<OICalcoloDettaglioR> l = this.GetDettagli(cls);

            double totale = 0.0d;

            l.ForEach(delegate (OICalcoloDettaglioR dett) { totale += dett.Totale.GetValueOrDefault(double.MinValue); });

            cls.Totale = totale;

            this.Update(cls);
        }



    }
}
