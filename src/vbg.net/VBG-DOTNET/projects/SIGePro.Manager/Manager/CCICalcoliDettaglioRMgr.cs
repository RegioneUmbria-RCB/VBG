using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.IOC;
using Init.SIGePro.Manager.Validator;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{
    public partial class CCICalcoliDettaglioRMgr
    {
        public static List<CCICalcoliDettaglioR> Find(string token, int idTestata)
        {
            AuthenticationInfo authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

            CCICalcoliDettaglioRMgr mgr = new CCICalcoliDettaglioRMgr(authInfo.CreateDatabase());

            CCICalcoliDettaglioR filtro = new CCICalcoliDettaglioR();
            filtro.Idcomune = authInfo.IdComune;
            filtro.FkCcicdtId = idTestata;

            filtro.OrderBy = "ID ASC";

            return mgr.GetList(filtro);
        }

        private CCICalcoliDettaglioR ChildInsert(CCICalcoliDettaglioR cls)
        {
            new CCICalcoliDettaglioTMgr(this.db).AggiornaSU(cls.Idcomune, cls.FkCcicdtId.GetValueOrDefault(int.MinValue));

            return cls;
        }

        public CCICalcoliDettaglioR Update(CCICalcoliDettaglioR cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            new CCICalcoliDettaglioTMgr(this.db).AggiornaSU(cls.Idcomune, cls.FkCcicdtId.GetValueOrDefault(int.MinValue));

            return cls;
        }

        public void Delete(CCICalcoliDettaglioR cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);

            new CCICalcoliDettaglioTMgr(this.db).AggiornaSU(cls.Idcomune, cls.FkCcicdtId.GetValueOrDefault(int.MinValue));
        }
    }
}
