//_-----------------------------------------------------------------------
// <copyright file="InvioMovimentoTests.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace MovimentiTest
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Xunit;
	using MovimentiTest.Helpers;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.Commands;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.Events;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class InvioMovimentoTests: MovimentoFrontofficeTestClass
	{
		const string IdComune = "E256";
		const int IdMovimento = 1;

		public override void OnTestInitialize()
		{
			var crea = new CreaMovimento(IdComune, IdMovimento, IdMovimento);

			this._bus.Send(crea);
		}

		[Fact]
		public void Quando_viene_inviato_un_comando_di_tipo_TrasmettiMovimento_viene_generato_un_evento_di_tipo_MovimentoTrasmesso()
		{
			var cmd = new TrasmettiMovimento(IdComune, IdMovimento);

			this._bus.Send(cmd);

			var evento = GetEventiGeneratiNelTest().First();

			Assert.IsType<MovimentoTrasmesso>(evento);
			Assert.Equal(IdComune, ((MovimentoTrasmesso)evento).IdComune);
			Assert.Equal<int>(IdMovimento, ((MovimentoTrasmesso)evento ).IdMovimento);
		}
	}
}
