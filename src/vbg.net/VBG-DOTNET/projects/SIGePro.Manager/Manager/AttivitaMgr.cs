using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.IOC;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.ComponentModel;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public class AttivitaMgr : BaseManager
    {
        public AttivitaMgr(DataBase dataBase) : base(dataBase) { }

        public Attivita GetById(String pCODICEISTAT, String pIDCOMUNE)
        {
            Attivita retVal = new Attivita();

            retVal.CodiceIstat = pCODICEISTAT;
            retVal.IDCOMUNE = pIDCOMUNE;

            return this.db.GetClass(retVal);
        }

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<Attivita> Find(string token, string codiceSettore)
        {
            AuthenticationInfo authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

            Attivita filtro = new Attivita();
            filtro.IDCOMUNE = authInfo.IdComune;
            filtro.CODICESETTORE = codiceSettore;

            return authInfo.CreateDatabase().GetClassList(filtro).ToList<Attivita>();
        }

        public void Delete(Attivita p_class)
        {
            this.db.Delete(p_class);
        }

        public Attivita Insert(Attivita p_class)
        {
            this.db.Insert(p_class);
            return p_class;
        }

        public Attivita Update(Attivita p_class)
        {
            this.db.Update(p_class);
            return p_class;
        }
    }
}