using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Ninject;
using Ninject.Parameters;

namespace Init.Sigepro.FrontEnd.AppLogic.IoC
{
	public static class FoKernelContainer
	{
		private static IKernel _kernel;

		public static IKernel Kernel
		{
			get
			{
				return FoKernelContainer._kernel;
			}
			set
			{
				if (FoKernelContainer._kernel != null)
					throw new NotSupportedException("The static container already has a kernel associated with it!");
				else
					FoKernelContainer._kernel = value;
			}
		}

		public static void Inject(object instance)
		{
			if (FoKernelContainer._kernel == null)
				throw new InvalidOperationException(string.Format("The type {0} requested an injection, but no kernel has been registered for the web application.\r\nPlease ensure that your project defines a NinjectHttpApplication.", (object)instance.GetType()));
			else
				FoKernelContainer._kernel.Inject(instance, new IParameter[0]);

		}
	}
}
