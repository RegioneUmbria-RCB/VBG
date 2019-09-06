using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.Ravenna2
{
	class Ravenna2MultipleResultsFound: Ravenna2Result
	{
		public Ravenna2MultipleResultsFound():base(true)
		{
			this.PiuDiUnElementoTrovato = true;
		}
	}
}
