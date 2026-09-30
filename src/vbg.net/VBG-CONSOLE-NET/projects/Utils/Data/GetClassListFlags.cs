using PersonalLib2.Sql;

namespace PersonalLib2.Data
{
    public class GetClassListFlags
    {
        public useForeignEnum UseForeign { get; set; } = PersonalLib2.Sql.useForeignEnum.No;
        public bool SingleRowException { get; set; } = false;

        public GetClassListFlags()
        {

        }

        public GetClassListFlags(useForeignEnum useForeign, bool singleRowException = false)
        {
            this.UseForeign = useForeign;
            this.SingleRowException = singleRowException;
        }
    }
}
