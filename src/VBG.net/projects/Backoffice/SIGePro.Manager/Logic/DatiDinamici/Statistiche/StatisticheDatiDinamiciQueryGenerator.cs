using System;
using System.Collections.Generic;
using System.Text;
using PersonalLib2.Data;
using System.Diagnostics;
using Init.SIGePro.DatiDinamici.Statistiche;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.Statistiche
{
	public partial class StatisticheDatiDinamiciQueryGenerator
	{
		string m_idComune;
		DataBase m_database;
		string m_nomeTabella;
		string m_nomeCampoId;
		string m_nomeCampoIdCorrelato;

		int m_idx = 0;

		StringBuilder m_commandText = new StringBuilder();
		List<KeyValuePair<string, object>> m_parameters;

		public StatisticheDatiDinamiciQueryGenerator(string idComune, DataBase database, string nomeTabella, string nomeCampoId, string nomeCampoIdCorrelato)
		{
			m_idComune = idComune;
			m_database = database;
			m_nomeTabella = nomeTabella;
			m_nomeCampoId = nomeCampoId;
			m_nomeCampoIdCorrelato = nomeCampoIdCorrelato;
		}

		public QueryStatisticheDatiDinamici CreaQuery(DsFiltriStatisticheDatiDinamici dsFiltri)
		{
			m_commandText = new StringBuilder();
			m_parameters = new List<KeyValuePair<string, object>>();
			m_idx = 0;

			//foreach ( DsFiltriStatisticheDatiDinamici.DtFiltriRow row in dsFiltri.DtFiltri)
			for (int idx = 0; idx < dsFiltri.DtFiltri.Rows.Count; idx++)
			{
				CreaQueryRiga(dsFiltri.DtFiltri[idx], idx);
			}

			return new QueryStatisticheDatiDinamici(" (" + m_commandText.ToString() + ") ", m_parameters);
		}


		private void CreaQueryRiga(DsFiltriStatisticheDatiDinamici.DtFiltriRow row, int indiceRiga)
		{
			if (row.IdCampo == -1) return;

			bool confrontoNull = TipoConfrontoNull(row.Criterio);
			bool richiedeValore = ConfrontoRichiedeValore(row.Criterio);
			string testoConfronto = EstraiTipoConfronto(row.Criterio);

			bool richiedesubstring = RichiedeSubstring(row.Criterio);
			bool richiedeToNumber = RichiedeToNumber(row.Criterio);

			string nomeCampoValore = m_database.Specifics.UCaseFunction(m_nomeTabella + ".VALORE");

			if (richiedesubstring)
				nomeCampoValore = m_database.Specifics.ToCharFunction(m_database.Specifics.SubstrFunction(nomeCampoValore, 0, 4000));

			if (richiedeToNumber)
			{
				var str = "replace(replace({0}.valore, '.',''),',','.')";
				nomeCampoValore = m_database.Specifics.ToIntegerFunction(String.Format(str, m_nomeTabella));
			}

			string sqlFmtBase = "SELECT " + m_nomeTabella + "." + m_nomeCampoIdCorrelato + " FROM " + m_nomeTabella + " WHERE " + m_nomeTabella + ".idcomune = {{{0}}} AND " + m_nomeTabella + ".FK_D2C_ID = {{{1}}} AND {2} {3} ";

			string sqlCondizioneBase = String.Format(sqlFmtBase, m_idx++, m_idx++, nomeCampoValore, testoConfronto);

			if (richiedeValore)
				sqlCondizioneBase += " {" + (m_idx++) + "}";

			RegistraParametro("parIdcomune", m_idComune);
			RegistraParametro("parIdCampo", row.IdCampo);

			if (richiedeValore)
				RegistraParametro("parValore", row.Valore.ToUpper());


			// Legame con la join
			string sqlPre = "";
			string sqlPost = "";

			if (indiceRiga > 0)
				sqlPre += " " + row.Concatenazione + " ";

			if (row.ParentesiIn)
				sqlPre += "(";

			sqlPre += m_nomeCampoId;

			if (confrontoNull)
				sqlPre += " not ";

			sqlPre += " in ( ";

			sqlPost = " )";

			if (row.ParentesiOut)
				sqlPost += " )";

			m_commandText.Append(" ").Append(sqlPre).Append(sqlCondizioneBase).Append(sqlPost);
		}

		private bool RichiedeToNumber(string operatore)
		{
			var confronto = (TipoConfrontoFiltroEnum)Enum.Parse(typeof(TipoConfrontoFiltroEnum), operatore);

			switch (confronto)
			{

				case TipoConfrontoFiltroEnum.LessThan:
				case TipoConfrontoFiltroEnum.LessThanOrEqual:
				case TipoConfrontoFiltroEnum.GreaterThan:
				case TipoConfrontoFiltroEnum.GreaterThanOrEqual:
					return true;

				case TipoConfrontoFiltroEnum.Equal:
				case TipoConfrontoFiltroEnum.NotEqual:
				case TipoConfrontoFiltroEnum.Like:
				case TipoConfrontoFiltroEnum.Null:
				case TipoConfrontoFiltroEnum.NotNull:
					return false;
			}

			return false;
		}



		private void RegistraParametro(string name, object value)
		{
			m_parameters.Add(new KeyValuePair<string, object>(name + m_parameters.Count, value));
		}

		private bool TipoConfrontoNull(string val)
		{
			TipoConfrontoFiltroEnum confronto = (TipoConfrontoFiltroEnum)Enum.Parse(typeof(TipoConfrontoFiltroEnum), val);

			return confronto == TipoConfrontoFiltroEnum.Null;
		}

		private bool ConfrontoRichiedeValore(string val)
		{
			TipoConfrontoFiltroEnum confronto = (TipoConfrontoFiltroEnum)Enum.Parse(typeof(TipoConfrontoFiltroEnum), val);

			return confronto != TipoConfrontoFiltroEnum.Null && confronto != TipoConfrontoFiltroEnum.NotNull;
		}

		private string EstraiTipoConfronto(string val)
		{
			TipoConfrontoFiltroEnum confronto = (TipoConfrontoFiltroEnum)Enum.Parse(typeof(TipoConfrontoFiltroEnum), val);

			switch (confronto)
			{
				case TipoConfrontoFiltroEnum.Equal:
					return "like";
				case TipoConfrontoFiltroEnum.NotEqual:
					return "not like";
				case TipoConfrontoFiltroEnum.LessThan:
					return "<";
				case TipoConfrontoFiltroEnum.LessThanOrEqual:
					return ">";
				case TipoConfrontoFiltroEnum.GreaterThan:
					return ">";
				case TipoConfrontoFiltroEnum.GreaterThanOrEqual:
					return ">=";
				case TipoConfrontoFiltroEnum.Null:
					return "is not null";
				case TipoConfrontoFiltroEnum.NotNull:
					return "is not null";
				case TipoConfrontoFiltroEnum.Like:
					return "like";
			}

			return "";

		}

		private bool RichiedeSubstring(string val)
		{
			TipoConfrontoFiltroEnum confronto = (TipoConfrontoFiltroEnum)Enum.Parse(typeof(TipoConfrontoFiltroEnum), val);

			switch (confronto)
			{

				case TipoConfrontoFiltroEnum.LessThan:
				case TipoConfrontoFiltroEnum.LessThanOrEqual:
				case TipoConfrontoFiltroEnum.GreaterThan:
				case TipoConfrontoFiltroEnum.GreaterThanOrEqual:
					return true;

				case TipoConfrontoFiltroEnum.Equal:
				case TipoConfrontoFiltroEnum.NotEqual:
				case TipoConfrontoFiltroEnum.Like:
				case TipoConfrontoFiltroEnum.Null:
				case TipoConfrontoFiltroEnum.NotNull:
					return false;
			}

			return false;
		}


	}
}
