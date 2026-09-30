namespace Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.ManagedData.MappingDaManagedData
{
    public partial class FvgManagedDataMapper
    {
        public class ItemValue
        {
            public string Valore;
            public string ValoreDecodificato;

            public ItemValue(string valore, string valoredecodificato)
            {
                this.Valore = valore;
                this.ValoreDecodificato = valoredecodificato;
            }
        }
    }
}
