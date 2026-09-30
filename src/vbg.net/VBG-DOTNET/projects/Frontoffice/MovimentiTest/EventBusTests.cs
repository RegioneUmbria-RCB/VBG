using System;
using System.Text;
using System.Collections.Generic;
using System.Linq;
using Xunit;
using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;

namespace MovimentiTest
{
    public class EventBusTests
    {
        public class TestCommand : Command
        { }

        [Fact]
        public void Ad_un_comando_puo_essere_associato_solo_un_handler()
        {
            var bus = new EventsBus();
            bus.RegisterHandler<TestCommand>(x => { var i = 0; i++; });
            Assert.Throws<BusConfigurationException>(() =>
            {
                bus.RegisterHandler<TestCommand>(x => { var i = 0; i++; });
            });
        }
    }
}
