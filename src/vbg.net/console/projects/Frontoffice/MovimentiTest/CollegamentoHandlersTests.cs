using System;
using System.Text;
using System.Collections.Generic;
using System.Linq;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;

namespace MovimentiTest
{
	[TestClass]
	public class CollegamentoHandlersTests
	{
		public class SampleCommand: Command
		{
		}

		public class SampleEvent : Event
		{
		}

		public class SampleEventHandler : IEventHandler, Handles<SampleCommand>, Handles<SampleEvent>
		{
			public List<Command> Commands = new List<Command>();
			public List<Event> Events = new List<Event>();

			
			public void Handle(SampleCommand message)
			{
				this.Commands.Add(message);
			}

			public void Handle(SampleEvent message)
			{
				this.Events.Add(message);
			}

		}

		[TestMethod]
		public void Quando_un_event_handler_viene_collegato_al_bus_tutti_gli_handlers_vengono_registrati()
		{
			var bus = new EventsBus();
			var handler = new SampleEventHandler();

			EventHandlerConfigurator.Configure()
									.WithHandler( handler )
									.OnBus( bus );

			bus.Send(new SampleCommand());
			bus.Publish(new SampleEvent());

			Assert.AreEqual<int>(1, handler.Commands.Count);
			Assert.IsInstanceOfType( handler.Commands[0], typeof(SampleCommand));

			Assert.AreEqual<int>(1, handler.Events.Count);
			Assert.IsInstanceOfType(handler.Events[0], typeof(SampleEvent));
		}
	}
}
