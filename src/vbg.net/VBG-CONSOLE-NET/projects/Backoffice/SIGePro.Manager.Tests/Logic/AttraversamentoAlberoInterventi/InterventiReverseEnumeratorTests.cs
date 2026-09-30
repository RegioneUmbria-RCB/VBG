using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace SIGePro.Manager.Tests.Logic.AttraversamentoAlberoInterventi
{
	[TestClass]
	public class InterventiReverseEnumeratorTests
	{
		[TestMethod]
		public void Data_un_alberatura_la_attraversa_a_salmone_risalendo_dalle_foglie_al_nodo_padre()
		{
			var listaInterventi = new List<IIntervento>();

			listaInterventi.Add(new InterventoStub { Id = 1 });
			listaInterventi.Add(new InterventoStub { Id = 2 });
			listaInterventi.Add(new InterventoStub { Id = 3 });

			var enumerator = new InterventiReverseEnumerator<IIntervento>(listaInterventi);

			var nuovaLista = new List<IIntervento>();

			while (enumerator.MoveNext())
			{
				var i = enumerator.Current;

				nuovaLista.Add(i);
			}

			Assert.AreEqual<int>(3, nuovaLista.Count);
			Assert.AreEqual<int>(3, nuovaLista[0].Id);
			Assert.AreEqual<int>(2, nuovaLista[1].Id);
			Assert.AreEqual<int>(1, nuovaLista[2].Id);
		}

	}
}
