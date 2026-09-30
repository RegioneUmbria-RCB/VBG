
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using System.Data;
using System.ComponentModel;
using Init.SIGePro.Authentication;

using PersonalLib2.Sql;
using Init.Utils.Sorting;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class MailConfigMgr
    {
		public MailConfig GetById(string idcomune, string software)
		{
			MailConfig c = new MailConfig();

			c.Idcomune = idcomune;
			c.Software = software;

			var cls = (MailConfig)db.GetClass(c);

			if (cls == null && software != "TT")
			{
				cls = (MailConfig)db.GetClass(new MailConfig { Idcomune = idcomune, Software = "TT" });
			}

			return cls;
		}
	}
}
				