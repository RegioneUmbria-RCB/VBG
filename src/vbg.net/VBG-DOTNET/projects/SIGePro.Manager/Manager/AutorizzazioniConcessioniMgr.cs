using Init.SIGePro.Data;
using PersonalLib2.Data;

namespace Init.SIGePro.Manager
{
    public class AutorizzazioniConcessioniMgr : BaseManager
    {
        public AutorizzazioniConcessioniMgr(DataBase dataBase) : base(dataBase) { }

        public void Delete(AutorizzazioniConcessioni p_class)
        {
            this.db.Delete(p_class);
        }
    }
}
