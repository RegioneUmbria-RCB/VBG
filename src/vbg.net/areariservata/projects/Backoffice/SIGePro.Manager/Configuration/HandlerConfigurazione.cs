using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Manager.WsSigeproSecurity;
using System.Configuration;

namespace Init.SIGePro.Manager.Configuration
{
	public class HandlerConfigurazione 
	{
		ApplicationInfoType[] m_params;

		internal HandlerConfigurazione(ApplicationInfoType[] parametri)
		{
			m_params = parametri;
		}


		protected string GetParam(string nomeParametro)
		{
			return GetParam(nomeParametro, String.Empty);
		}
		protected string GetParam(string nomeParametro, string valoreDefault)
		{
            var cfgVal = ConfigurationManager.AppSettings[nomeParametro];

            if(!String.IsNullOrEmpty(cfgVal))
            {
                return cfgVal;
            }

			ApplicationInfoType val = m_params.SingleOrDefault(x => x.param.ToUpper() == nomeParametro);

			return val == null ? valoreDefault : val.value;
		}
	}
}
