using System;
using System.Collections.Specialized;

namespace Parser
{
    /// <summary>
    /// E' una classe non istanziabile al di fuori del progetto e serve a Parsare le query di tipo SELECT
    /// </summary>
    internal class SelectParser : BaseParser
    {
        /// <summary>
        /// Esegue il parser della query precedentemente impostata tramite la proprietà pubblica "query".
        /// </summary>
        /// <param name="qs">
        /// Struttura di tipo <see cref='Query'/> alla quale aggiungere i vari elementi 
        /// estrapolati dal parse della query
        /// </param>
        public void Parse(Query qs)
        {
            try
            {
                string pCols = String.Empty;
                string pWhere = String.Empty;

                StringCollection tabs = new StringCollection();

                int posStart = 7;
                int posFrom = this._query.IndexOf(" FROM ");

                #region 1. Estrapolo le colonne della select
                StringCollection cols = new StringCollection();
                string pSelCols = this._query.Substring(posStart, posFrom - posStart).Trim();

                string colAlias = "";

                for (this.currentIndex = 0; this.currentIndex < pSelCols.Length; this.currentIndex++)
                {
                    char curChar = this.CurrentChar(pSelCols);

                    switch (curChar.ToString())
                    {
                        case "'":
                            {
                                if (this.isInComment())
                                    this.apiceCounter -= 1;
                                else
                                    this.apiceCounter += 1;

                                colAlias += curChar.ToString();

                                break;
                            }
                        case "(":
                            {
                                if (!this.isInComment())
                                    this.functionCounter += 1;

                                colAlias += curChar.ToString();

                                break;
                            }
                        case ")":
                            {
                                if (!this.isInComment())
                                    this.functionCounter -= 1;

                                colAlias += curChar.ToString();

                                break;
                            }
                        case " ":
                            {
                                colAlias += curChar.ToString();
                                break;
                            }

                        case ",":
                            {
                                if (!this.isInComment() && !this.isInFunction())
                                {
                                    cols.Add(this.getColumnAlias(colAlias));
                                    colAlias = String.Empty;
                                }
                                else
                                {
                                    colAlias += curChar.ToString();
                                }
                                break;
                            }

                        default:
                            {
                                colAlias += curChar.ToString();
                                break;
                            }
                    }
                }

                if (colAlias.Length > 0)
                {
                    cols.Add(this.getColumnAlias(colAlias));
                    colAlias = String.Empty;
                }

                this.addCols(qs, cols);
                #endregion

                #region 2. Estrapolo le tabelle dalla from
                string pFrom = this._query.Substring(posFrom + 6);

                qs.Tables = this.ParseTables(pFrom);
                #endregion

                #region 3. Estrapolo la condizione where
                qs.Where = this.ParseWhere(pFrom);
                #endregion
            }
            catch (System.Exception Ex)
            {
                throw Ex;
            }
        }


        /// <summary>
        /// Estrapola le tabelle della SELECT
        /// </summary>
        /// <param name="query">Query da parsare</param>
        /// <returns>Una collection di stringhe in cui ogni elemento è il nome della tabella</returns>
        protected StringCollection ParseTables(string query)
        {
            StringCollection retVal = new StringCollection();

            try
            {
                string pQuery = query;
                string tabAlias = String.Empty;

                this.currentIndex = -1;

                foreach (char c in pQuery)
                {
                    this.currentIndex += 1;

                    switch (c.ToString())
                    {
                        case "(":
                            {
                                this.functionCounter += 1;
                                tabAlias += c.ToString();
                                break;
                            }
                        case ")":
                            {
                                this.functionCounter -= 1;
                                tabAlias += c.ToString();
                                break;
                            }
                        case ",":
                            {
                                if (!this.isInFunction())
                                {
                                    retVal.Add(this.getTableAlias(tabAlias));
                                    tabAlias = String.Empty;
                                }
                                else
                                {
                                    tabAlias += c.ToString();
                                }
                                break;
                            }
                        case " ":
                            {
                                if (!this.isInFunction())
                                {
                                    string pWhere = this.ReadChars(pQuery, 7);
                                    if (pWhere == " WHERE ")
                                    {
                                        //	currentIndex += 7;

                                        if (!String.IsNullOrEmpty(tabAlias))
                                            retVal.Add(this.getTableAlias(tabAlias));

                                        return retVal;
                                    }
                                    else
                                    {
                                        tabAlias += c.ToString();
                                    }
                                }
                                else
                                {
                                    tabAlias += c.ToString();
                                }

                                break;
                            }
                        default:
                            {
                                tabAlias += c.ToString();
                                break;
                            }
                    }
                }
            }
            catch (System.Exception Ex)
            {
                throw Ex;
            }

            return retVal;
        }



        /// <summary>
        /// 
        /// </summary>
        /// <param name="query"></param>
        /// <returns></returns>
        protected string ParseWhere(string query)
        {
            if (query.Length >= this.currentIndex + 7)
                return (query.Substring(this.currentIndex + 7));

            return null;
        }
    }
}
