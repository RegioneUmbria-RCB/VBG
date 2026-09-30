using Init.Sigepro.FrontEnd.AppLogic.DataAccess;

namespace PersonalLib2.Data
{
    public static class PersonalLibException
    {
        public static int NextId(this IDatabase db, string idComune, string nomeTabella, string nomeColonna)
        {
            return new SequenceTableService(db, idComune).NextId(nomeTabella, nomeColonna);
        }
    }
}
