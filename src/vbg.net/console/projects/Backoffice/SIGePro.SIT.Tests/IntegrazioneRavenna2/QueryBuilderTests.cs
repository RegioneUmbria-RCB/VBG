using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Sit.Ravenna2;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace SIGePro.SIT.Tests.IntegrazioneRavenna2
{
	[TestClass]
	public class QueryBuilderTests
	{
		[TestMethod]
		public void Generazione_query_semplice()
		{
			var q = new QueryBuilder();
			var t = new QueryTable("tabella1");

			q.From(t);
			q.Select(t.GetField("colonna1"));

			var result = q.ToString();
			var expected = "select tabella1.colonna1 from tabella1";

			Assert.AreEqual<string>(expected, result);
		}

		[TestMethod]
		public void Generazione_query_con_condizione_where()
		{
			var q = new QueryBuilder();
			var t = new QueryTable("tabella1");
			var c = t.GetField("colonna1");

			q.From(t);
			q.Select(c);
			q.WhereEqual(c, ":test");

			var result = q.ToString();
			var expected = "select tabella1.colonna1 from tabella1 where 1=1 and tabella1.colonna1 = :test";

			Assert.AreEqual<string>(expected, result);
		}

		[TestMethod]
		public void Generazione_query_con_doppia_condizione_where()
		{
			var q = new QueryBuilder();
			var t = new QueryTable("tabella1");
			var c = t.GetField("colonna1");
			var c2 = t.GetField("colonna2");

			q.From(t);
			q.Select(c);
			q.WhereEqual(c, ":test");
			q.WhereEqual(c2, ":test");

			var result = q.ToString();
			var expected = "select tabella1.colonna1 from tabella1 where 1=1 and tabella1.colonna1 = :test and tabella1.colonna2 = :test";

			Assert.AreEqual<string>(expected, result);
		}

		[TestMethod]
		public void Generazione_query_con_due_tabelle_in_join()
		{
			var q = new QueryBuilder();
			var t = new QueryTable("tabella1");
			var t2 = new QueryTable("tabella2");
			var c = t.GetField("colonna1");
			var c2 = t2.GetField("colonna1");

			q.From(t);
			q.From(t2);
			q.AddJoin(c, c2);
			q.Select(c);
			q.WhereEqual(c, ":test");
			

			var result = q.ToString();
			var expected = "select tabella1.colonna1 from tabella1, tabella2 where 1=1 and tabella1.colonna1 = tabella2.colonna1 and tabella1.colonna1 = :test";

			Assert.AreEqual<string>(expected, result);
		}
	}
}
