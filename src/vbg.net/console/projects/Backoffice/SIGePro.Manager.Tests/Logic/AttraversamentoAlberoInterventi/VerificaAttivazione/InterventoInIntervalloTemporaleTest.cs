using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi;
using Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi.VerificaAttivazione;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace SIGePro.Manager.Tests.Logic.AttraversamentoAlberoInterventi.VerificaAttivazione
{
	[TestClass]
	public class InterventoInIntervalloTemporaleTest
	{
		[TestMethod]
		public void Restituisce_true_se_data_odierna_nell_intervallo_di_attivazione()
		{
			var list = new List<IIntervento>();

			list.Add(new InterventoStub 
			{ 
				DataInizioAttivazione = DateTime.Now.AddHours(-1),
				DataFineAttivazione = DateTime.Now.AddHours(1)
			
			});

			var test = new InterventoAttivo(list);

			var result = test.IsTrue();

			Assert.IsTrue(result);
		}

		[TestMethod]
		public void Restituisce_false_se_data_odierna_antecedente_data_di_attivazione()
		{
			var list = new List<IIntervento>();

			list.Add(new InterventoStub
			{
				DataInizioAttivazione = DateTime.Now.AddHours(1),
				DataFineAttivazione = DateTime.Now.AddHours(2)

			});

			var test = new InterventoAttivo(list);

			var result = test.IsTrue();

			Assert.IsFalse(result);
		}

		[TestMethod]
		public void Restituisce_false_se_data_odierna_seguente_data_di_attivazione()
		{
			var list = new List<IIntervento>();

			list.Add(new InterventoStub
			{
				DataInizioAttivazione = DateTime.Now.AddHours(-2),
				DataFineAttivazione = DateTime.Now.AddHours(-1)

			});

			var test = new InterventoAttivo(list);

			var result = test.IsTrue();

			Assert.IsFalse(result);
		}

		[TestMethod]
		public void Restituisce_true_se_data_odierna_nell_intervallo_di_attivazione_di_un_padre()
		{
			var list = new List<IIntervento>();

			list.Add(new InterventoStub
			{
				DataInizioAttivazione = DateTime.Now.AddHours(-1),
				DataFineAttivazione = DateTime.Now.AddHours(1)
			});
			list.Add(new InterventoStub());
			list.Add(new InterventoStub());

			var test = new InterventoAttivo(list);

			var result = test.IsTrue();

			Assert.IsTrue(result);
		}

		[TestMethod]
		public void Restituisce_false_se_data_odierna_antecedente_data_di_attivazione_di_un_padre()
		{
			var list = new List<IIntervento>();

			list.Add(new InterventoStub
			{
				DataInizioAttivazione = DateTime.Now.AddHours(1),
				DataFineAttivazione = DateTime.Now.AddHours(2)
			});
			list.Add(new InterventoStub());
			list.Add(new InterventoStub());

			var test = new InterventoAttivo(list);

			var result = test.IsTrue();

			Assert.IsFalse(result);
		}

		[TestMethod]
		public void Restituisce_false_se_data_odierna_seguente_data_di_attivazione_di_un_padre()
		{
			var list = new List<IIntervento>();

			list.Add(new InterventoStub
			{
				DataInizioAttivazione = DateTime.Now.AddHours(-2),
				DataFineAttivazione = DateTime.Now.AddHours(-1)
			});
			list.Add(new InterventoStub());
			list.Add(new InterventoStub());

			var test = new InterventoAttivo(list);

			var result = test.IsTrue();

			Assert.IsFalse(result);
		}

		[TestMethod]
		public void Restituisce_true_se_nell_alberatura_nessun_intervento_definisce_data_di_attivazione()
		{
			var list = new List<IIntervento>();

			list.Add(new InterventoStub());
			list.Add(new InterventoStub());
			list.Add(new InterventoStub());
			list.Add(new InterventoStub());

			var test = new InterventoAttivo(list);

			var result = test.IsTrue();

			Assert.IsTrue(result);
		}
	}
}
