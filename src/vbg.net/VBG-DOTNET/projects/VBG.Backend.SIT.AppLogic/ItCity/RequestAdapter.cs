namespace VBG.Backend.SIT.AppLogic.ItCity
{
    public class RequestAdapter
    {
        public RequestAdapter()
        {

        }

        public RequestCivici AdattaCivici(string CodVia)
        {
            return new RequestCivici
            {
                Indir = CodVia,
            };
        }
    }
}
