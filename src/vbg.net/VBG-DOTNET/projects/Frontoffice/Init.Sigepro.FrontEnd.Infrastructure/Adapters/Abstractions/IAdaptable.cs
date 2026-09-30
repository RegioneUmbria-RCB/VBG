using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Infrastructure.Adapters.Abstractions
{
    public interface IAdaptable<TSrc>
    {
        TDst Adapt<TDst>(IAdapter<TSrc, TDst> adapter);
    }
}
