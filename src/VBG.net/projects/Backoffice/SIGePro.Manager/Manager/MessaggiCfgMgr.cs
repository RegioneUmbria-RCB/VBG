
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
    public partial class MessaggiCfgMgr
    {
		private List<MessaggiCfg> GetDaSoftwareEContestoInternal(string idComune, string software, string contesto)
		{
			var filtro = new MessaggiCfg
			{
				Idcomune = idComune,
				Software = software,
				Contesto = contesto
			};

			return GetList(filtro);
		}

		public List<MessaggiCfg> GetDaSoftwareEContesto(string idComune, string software, string contesto)
		{
			var list = GetDaSoftwareEContestoInternal(idComune, software, contesto);

			if(list.Count == 0 && software != "TT")
				list = GetDaSoftwareEContestoInternal(idComune, "TT", contesto);

			/*if (list.Count == 0)
			{
				MessaggiCfgBase msgb = new MessaggiCfgBaseMgr(db).GetById(contesto);

				if (msgb == null)
					return list;

				var msg = new MessaggiCfg
				{
					Contesto = msgb.Contesto,
					Corpo = msgb.Corpo,
					FlgInvio = msgb.FlgInvio,
					FlgTipoinvio = msgb.FlgTipoinvio,
					Idcomune = idComune,
					Oggetto = msgb.Oggetto,
					Software = software
				};

				list.Add(msg);
			}*/

			return list;
		}
	}
}
				