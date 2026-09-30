namespace Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.ManagedData.MappingDaManagedData
{
    public partial class FvgManagedDataMapper
    {
        public class ManagedDataValue
        {
            public int Id { get; set; }
            public string NomeCampo { get; set; }
            public string Valore { get; set; }
            public string ValoreDecodificato { get; set; }
            public int IndiceMolteplicita { get; set; } = 0;
        }
    }
}
