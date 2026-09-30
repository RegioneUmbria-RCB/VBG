namespace PersonalLib2.Data.Providers
{
    public class QueryPaginationRequest
    {
        /// <summary>
        /// Numero di records da restituire
        /// </summary>
        public int PageSize { get; set; } = 10;

        public int CurrentPage { get; set; } = 0;

        public int ComputedOffset => CurrentPage * PageSize;

        public QueryPaginationRequest()
        {
        }

        public QueryPaginationRequest(int pageSize = 10, int startPage = 0)
        {
            this.PageSize = pageSize;
            this.CurrentPage = startPage;
        }
    }
}
