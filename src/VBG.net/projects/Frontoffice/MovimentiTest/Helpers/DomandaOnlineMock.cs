using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;

namespace MovimentiTest.Helpers
{
	public class DomandaOnlineMock : DomandaOnline
	{
		public class EventObserverMock : IEventObserver
		{
		}

		public DomandaOnlineMock(PresentazioneIstanzaDataKey dataKey, PresentazioneIstanzaDbV2 db, bool presentata):base(dataKey, db , presentata)
		{

		}

		protected override IEventObserver CreateEventObserver()
		{
			return new EventObserverMock();
		}
	}
}
