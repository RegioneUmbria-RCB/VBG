using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;

namespace AreaRiservataCore.Shared.enums
{
    public enum OrderByDate
    {
        DataDiscendente,
        DataAscendente
    }

    public static class OrderByDateExtensions
    {
        public static string ToFriendlyString(this OrderByDate orderByDate)
        {
            return orderByDate switch
            {
                OrderByDate.DataDiscendente => "Descending",
                OrderByDate.DataAscendente => "Ascending",
                _ => ""
            };
        }

        public static OrderByDateEnum ToWSEnum(this OrderByDate orderByDate)
        {
            return orderByDate switch
            {
                OrderByDate.DataDiscendente => OrderByDateEnum.Descending,
                OrderByDate.DataAscendente => OrderByDateEnum.Ascending,
                _ => OrderByDateEnum.Descending
            };
        }
    }
}
