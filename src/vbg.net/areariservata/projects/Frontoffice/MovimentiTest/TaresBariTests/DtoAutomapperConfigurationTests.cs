using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Init.Sigepro.FrontEnd.Bari.TARES;

namespace Init.Sigepro.FrontEnd.AppLogicTests.TaresBariTests
{
	[TestClass]
	public class DtoAutomapperConfigurationTests
	{
		[TestMethod]
		public void La_configurazione_di_automapper_e_valida()
		{
			TaresAutomapperConfiguration.Bootstrap();
		}
	}
}
