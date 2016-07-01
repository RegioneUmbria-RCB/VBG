using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using Init.SIGePro.Authentication;
using Init.SIGePro.DatiDinamici.WebControls;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.RicercheSigepro
{
	internal class RicercheSigeproHelper
	{
		AuthenticationInfo _authInfo;
		ProprietaCampoRicercaReader _proprietaCampoReader;

		internal RicercheSigeproHelper(AuthenticationInfo authInfo, ProprietaCampoRicercaReader proprietaCampoReader)
		{
			this._authInfo = authInfo;
			this._proprietaCampoReader = proprietaCampoReader;
		}


		internal IEnumerable<KeyValuePair<string, string>> ExecuteQuery(string prefixText, bool usaLike, IEnumerable<ValoreFiltroRicerca> filtri, bool ignoraFiltri=false)
		{
			using (var db = this._authInfo.CreateDatabase())
			{
				var pc = this._proprietaCampoReader.GetProprieta();


				StringBuilder sb = BuildQueryBase(pc.CampiSelect, pc.TabelleSelect, pc.CondizioneJoin, pc.CondizioniWhere);



				if (usaLike)
				{
					sb.AppendFormat(" and ({0} like {1} OR {2} = {3})",
										db.Specifics.UCaseFunction(pc.CampoRicercaDescrizione),
										db.Specifics.QueryParameterName("par_" + pc.NomeCampoTesto),
										db.Specifics.UCaseFunction(pc.CampoRicercaCodice),
										db.Specifics.QueryParameterName("par_" + pc.NomeCampoValore));
				}
				else
				{
					sb.AppendFormat(" and {0} = {1}", pc.CampoRicercaCodice, db.Specifics.QueryParameterName("par_" + pc.NomeCampoValore));
				}

                /// Preparo le condizioni where che derivano da altri campi
                if (!String.IsNullOrEmpty(pc.CondizioniWhereAltriCampi) && !ignoraFiltri)
                {
                    var nomiCampi = new CondizioneWhereToNomiCampi(pc.CondizioniWhereAltriCampi);
                    var whereAltriCampi = pc.CondizioniWhereAltriCampi.ToUpperInvariant();
                    var nomeParametro = "";

                    foreach (var campo in nomiCampi.GetNomiCampi())
                    {
                        nomeParametro = db.Specifics.QueryParameterName(campo);
                        whereAltriCampi = whereAltriCampi.Replace("{" + campo + "}", nomeParametro);
                    }

                    sb.AppendFormat(" and {0}", whereAltriCampi);
                }

				sb.AppendFormat(" order by {0} asc", pc.NomeCampoTesto);

				string sql = sb.ToString();

				sql = Regex.Replace(sql, "([@][Ii][Dd][Cc][Oo][Mm][Uu][Nn][Ee])", "'" + this._authInfo.IdComune + "'");

				try
				{
					db.Connection.Open();

					using (var cmd = db.CreateCommand(sql))
					{
						if (usaLike)
						{
							string valoreRicerca = "";
							if (pc.TipoRicerca == 0) // la ricerca è fatta con %valore%
								valoreRicerca = "%" + prefixText + "%";
							else
								valoreRicerca = prefixText + "%";

							cmd.Parameters.Add(db.CreateParameter("par_" + pc.NomeCampoTesto, valoreRicerca.ToUpper()));
							cmd.Parameters.Add(db.CreateParameter("par_" + pc.NomeCampoValore, prefixText.ToUpper())); // La ricerca sul codice è sempre fatta per match esatto
						}
						else
						{
							cmd.Parameters.Add(db.CreateParameter("par_" + pc.NomeCampoValore, prefixText));
						}

                        if (!String.IsNullOrEmpty(pc.CondizioniWhereAltriCampi) && !ignoraFiltri)
                        {
                            var nomiCampi = new CondizioneWhereToNomiCampi(pc.CondizioniWhereAltriCampi);
                            var f = new FiltriRicerca(filtri);

                            foreach (var nomeCampo in nomiCampi.GetNomiCampi())
                            {
                                var valore = f.GetValoreCampo(nomeCampo);

                                cmd.Parameters.Add(db.CreateParameter(nomeCampo, valore));
                            }
                        }

						int idx = 0;
						using (var dr = cmd.ExecuteReader())
						{
							List<KeyValuePair<string, string>> retVal = new List<KeyValuePair<string, string>>();

							while (dr.Read() && idx <= pc.Count)
							{
								string key = dr[pc.NomeCampoValore].ToString();
								string val = dr[pc.NomeCampoTesto].ToString();

								retVal.Add(new KeyValuePair<string, string>(key, val));
								idx++;
							}

							return retVal;
						}
					}
				}
				catch (Exception ex)
				{
					throw ex;
				}
				finally
				{

					if (db != null)
					{
						db.Connection.Close();
						db.Dispose();
					}
				}
			}
		}

        private static StringBuilder BuildQueryBase(string campiSelect, string tabelleSelect, string condizioneJoin, string condizioniWhere)
		{
			StringBuilder sb = new StringBuilder();

			sb.AppendFormat("select {0} from {1} where 1=1 ",
							String.IsNullOrEmpty(campiSelect) ? "*" : campiSelect,
							tabelleSelect);

			if (!String.IsNullOrEmpty(condizioneJoin) || !String.IsNullOrEmpty(condizioniWhere))
			{
				if (!String.IsNullOrEmpty(condizioneJoin))
				{
					sb.AppendFormat(" and {0} ", condizioneJoin);
				}

				if (!String.IsNullOrEmpty(condizioniWhere))
				{
					sb.AppendFormat(" and {0} ", condizioniWhere);
				}
			}
			return sb;
		}

	}
}
