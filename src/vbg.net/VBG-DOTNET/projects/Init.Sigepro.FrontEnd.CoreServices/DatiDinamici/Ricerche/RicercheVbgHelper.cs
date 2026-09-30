using PersonalLib2.Data;
using PersonalLib2.Data.Providers;
using System.Text;
using System.Text.RegularExpressions;
using VBG.DatiDinamici.Agid.WebControls.Controlli.DatiDinamiciSearch;
using VBG.DatiDinamici.Utils;

namespace Init.Sigepro.FrontEnd.CoreServices.DatiDinamici.Ricerche
{


    internal partial class RicercheVbgHelper
    {
        private record struct ParametroQuery(string Nome, string Valore);

        private readonly IDatabase db;
        private readonly string idComune;

        internal RicercheVbgHelper(IDatabase db, string idComune)
        {
            this.db = db;
            this.idComune = idComune;
        }


        internal GetCompletionListResult ExecuteQuery(ProprietaCampoRicerca pc, string prefixText, bool usaLike, IEnumerable<ValoreFiltroRicerca> filtri, bool ignoraFiltri = false)
        {
            var sb = pc.GetQueryBase();

            var listaParametri = new List<ParametroQuery>();

            if (usaLike)
            {
                sb.AppendFormat(" and ({0} like {1} OR {2} = {3})",
                                    this.db.Specifics.UCaseFunction(pc.CampoRicercaDescrizione),
                                    this.db.QueryParameter("par_" + pc.NomeCampoTesto),
                                    this.db.Specifics.UCaseFunction(pc.CampoRicercaCodice),
                                    this.db.QueryParameter("par_" + pc.NomeCampoValore));

                var valoreRicerca = pc.TipoRicerca == Dyn2TipoRicerca.FullLike ? $"%{prefixText}%" : $"{prefixText}%";

                listaParametri.Add(new($"par_{pc.NomeCampoTesto}", valoreRicerca.ToUpper()));
                listaParametri.Add(new($"par_{pc.NomeCampoValore}", prefixText.ToUpper()));
            }
            else
            {
                sb.Append($" and {pc.CampoRicercaCodice} = {this.db.QueryParameter("par_" + pc.NomeCampoValore)}");

                listaParametri.Add(new($"par_{pc.NomeCampoValore}", prefixText));
            }

            /// Preparo le condizioni where che derivano da altri campi
            if (!String.IsNullOrEmpty(pc.CondizioniWhereAltriCampi) && !ignoraFiltri)
            {
                var nomiCampi = new CondizioneWhereToNomiCampi(pc.CondizioniWhereAltriCampi);
                var filtriAltriCampi = new FiltriRicerca(filtri);
                var whereAltriCampi = pc.CondizioniWhereAltriCampi.ToUpperInvariant();

                foreach (var nomeCampo in nomiCampi.GetNomiCampi())
                {
                    var nomeParametro = "par_" + nomeCampo;
                    var valore = filtriAltriCampi.GetValoreCampo(nomeCampo);

                    whereAltriCampi = whereAltriCampi.Replace("{" + nomeCampo + "}", this.db.QueryParameter(nomeParametro));

                    listaParametri.Add(new(nomeParametro, valore));
                }

                sb.Append($" and {whereAltriCampi}");
            }

            sb.Append($" order by {pc.OrderBy}");

            var sql = sb.ToString();

            if (RegexIdComune().IsMatch(sql))
            {
                sql = RegexIdComune().Replace(sql, "'" + this.idComune + "'");
            }

            try
            {
                void mapperParametri(ICommandParameterFactory mp)
                {
                    foreach (var par in listaParametri)
                    {
                        mp.Add(par.Nome, par.Valore);
                    }
                }

                // Eseguo prima il conteggio dei records trovati
                var sqlCount = $"select count(*) as conta from ({sql}) tmp";

                var totaleRecords = this.db.ExecuteScalar(
                    sqlCount, 0, mapperParametri);

                sql = this.db.Specifics.PaginateQuery(sql, new QueryPaginationRequest(pc.CompletionSetCount, 0));

                var retVal = this.db.ExecuteReader(
                    sql, mapperParametri,
                    dr => new RisultatoRicercaDatiDinamici(
                        value: dr.GetString(pc.NomeCampoValore) ?? "",
                        label: dr.GetString(pc.NomeCampoTesto) ?? ""));

                return new GetCompletionListResult(retVal, totaleRecords);
            }
            catch (Exception ex)
            {
                throw new QueryDatiDinamiciException($"Errore durante l'esecuzione della query per i dati dinamici: {sql}", ex);
            }

        }

        [GeneratedRegex("([@][Ii][Dd][Cc][Oo][Mm][Uu][Nn][Ee])")]
        private static partial Regex RegexIdComune();
    }
}
