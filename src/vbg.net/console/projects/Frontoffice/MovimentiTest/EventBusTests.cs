using System;
using System.Text;
using System.Collections.Generic;
using System.Linq;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;

namespace MovimentiTest
{
	[TestClass]
	public class EventBusTests
	{
		public class TestCommand : Command
		{ }

		[TestMethod]
		[ExpectedException(typeof(BusConfigurationException))]
		public void Ad_un_comando_può_essere_associato_solo_un_handler()
		{
			var bus = new EventsBus();
			bus.RegisterHandler<TestCommand>(x => { var i = 0; i++; });
			bus.RegisterHandler<TestCommand>(x => { var i = 0; i++; });
		}
	}
}
