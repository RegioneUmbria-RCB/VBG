using Init.SIGePro.Data;
using System.ComponentModel;

namespace Init.SIGePro.Manager
{


    [DataObject(true)]
    public partial class SoftwareAttiviMgr
    {
        public SoftwareAttiviList GetSoftwareAttivi(string idComune)
        {
            SoftwareAttivi filtro = new SoftwareAttivi
            {
                Idcomune = idComune
            };

            return new SoftwareAttiviList(this.db.GetClassList(filtro));
        }
    }
}
