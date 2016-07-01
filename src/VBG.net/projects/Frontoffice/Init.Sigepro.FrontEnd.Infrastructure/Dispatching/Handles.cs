using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Infrastructure.Dispatching
{
	public interface Handles<T> where T:Message
	{
		void Handle(T message);		
	}
}
