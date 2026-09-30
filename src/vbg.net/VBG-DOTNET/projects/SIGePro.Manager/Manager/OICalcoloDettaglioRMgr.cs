using Init.SIGePro.Data;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.IOC;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Sql;
using System.Collections.Generic;
using System.ComponentModel;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class OICalcoloDettaglioRMgr
    {
        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<OICalcoloDettaglioR> Find(string token, int idTestata)
        {
            var authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

            OICalcoloDettaglioRMgr mgr = new OICalcoloDettaglioRMgr(authInfo.CreateDatabase());

            OICalcoloDettaglioR filtro = new OICalcoloDettaglioR();
            filtro.Idcomune = authInfo.IdComune;
            filtro.FkOicdtId = idTestata;
            filtro.OrderBy = "id asc";

            return mgr.GetList(filtro);
        }


        public OICalcoloDettaglioR Update(OICalcoloDettaglioR cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            this.RicalcolaCubatura(cls);

            return cls;
        }

        private void RicalcolaCubatura(OICalcoloDettaglioR cls)
        {
            OICalcoloDettaglioTMgr tMgr = new OICalcoloDettaglioTMgr(this.db);
            tMgr.RicalcolaCubaturaTotale(cls.Idcomune, cls.FkOicdtId.Value);
        }

        public void Delete(OICalcoloDettaglioR cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);

            this.RicalcolaCubatura(cls);
        }

        public DataClass ChildDataIntegrations(DataClass cls)
        {
            this.RicalcolaCubatura((OICalcoloDettaglioR)cls);

            return cls;
        }



    }
}
