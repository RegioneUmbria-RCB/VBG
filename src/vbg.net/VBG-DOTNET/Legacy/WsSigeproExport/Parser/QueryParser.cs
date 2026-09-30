using System;

namespace Parser
{
    /// <summary>
    /// Descrizione di riepilogo per QueryParser.
    /// </summary>
    public class QueryParser
    {
        /// <summary>
        /// E' il metodo che effettua il Parse delle query.
        /// </summary>
        /// <param name="query">Query da parsare</param>
        /// <returns>Ritorna una struttura di tipo <see cref='Query'/></returns>
        public Query Parse(string query)
        {
            Query qs = new Query();

            if (!String.IsNullOrEmpty(query))
            {
                string pQuery = query.ToUpper();

                qs.Type = this.setQueryType(pQuery);

                switch (qs.Type)
                {
                    case queryType.Select:
                        {
                            SelectParser sp = new SelectParser();
                            sp.query = this.replaceChars(pQuery);
                            sp.Parse(qs);
                            break;
                        }
                    case queryType.Insert:
                        {
                            InsertParser ip = new InsertParser();
                            ip.query = this.replaceChars(pQuery);
                            ip.Parse(qs);
                            break;
                        }
                    case queryType.Update:
                        {
                            UpdateParser up = new UpdateParser();
                            up.query = this.replaceChars(pQuery);
                            up.Parse(qs);
                            break;
                        }
                    case queryType.Delete:
                        {
                            DeleteParser dp = new DeleteParser();
                            dp.query = this.replaceChars(pQuery);
                            dp.Parse(qs);
                            break;
                        }
                    default: break;
                }
            }

            return qs;
        }


        protected queryType setQueryType(string pQuery)
        {
            queryType retVal = queryType.Invalid;

            try
            {
                if (!String.IsNullOrEmpty(pQuery))
                {
                    if (pQuery.StartsWith("SELECT"))
                        retVal = queryType.Select;
                    else if (pQuery.StartsWith("UPDATE"))
                        retVal = queryType.Update;
                    else if (pQuery.StartsWith("DELETE"))
                        retVal = queryType.Delete;
                    else if (pQuery.StartsWith("INSERT"))
                        retVal = queryType.Insert;
                }
            }
            catch (System.Exception Ex)
            {
                throw Ex;
            }

            return retVal;
        }

        protected string replaceChars(string pQuery)
        {
            string retVal = pQuery;

            if (!String.IsNullOrEmpty(pQuery))
            {
                retVal = retVal.Replace("\r\n", " ");
            }

            return retVal;
        }

    }
}
