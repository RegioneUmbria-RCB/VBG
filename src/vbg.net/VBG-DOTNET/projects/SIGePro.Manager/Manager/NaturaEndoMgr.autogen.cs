using Init.SIGePro.Data;
using PersonalLib2.Data;

namespace Init.SIGePro.Manager
{
    public class NaturaEndoMgr : BaseManager
    {
        public NaturaEndoMgr(DataBase dataBase) : base(dataBase) { }

        public NaturaEndo GetById(string idComune, int codiceNatura)
        {
            var filtro = new NaturaEndo
            {
                IDCOMUNE = idComune,
                CODICENATURA = codiceNatura.ToString()
            };

            return this.db.GetClass(filtro);
        }

    }
}
