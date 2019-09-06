using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using System.Data;
using Init.Utils.Math;
using Init.SIGePro.Validator;

namespace Init.SIGePro.Manager
{
	public partial class OICalcoloContribRMgr
	{
		public OICalcoloContribR GetByContribTTipoOnereDestinazione(string idComune, int idContribt, int idTipoOnere, int idDestinazione)
		{
			OICalcoloContribR filtro = new OICalcoloContribR();
			filtro.Idcomune  = idComune;
			filtro.FkOicctId = idContribt;
			filtro.FkOtoId = idTipoOnere;
			filtro.FkOdeId = idDestinazione;

			return (OICalcoloContribR)db.GetClass(filtro);
		}

		public OICalcoloContribR Update(OICalcoloContribR cls)
		{
			Validate(cls, AmbitoValidazione.Update);

			db.Update(cls);

			return cls;
		}

		public List<OICalcoloContribR> GetListDaContribT(string idComune, int idContribT)
		{
			OICalcoloContribR filtro = new OICalcoloContribR();
			filtro.Idcomune = idComune;
			filtro.FkOicctId = idContribT;

			return GetList(filtro);
		}

		public void UpdateRiduzioneDaIdDestinazioneTipoOnere(string idComune, int idContribt, int idDestinazione, int idTipoOnere, double riduzione)
		{
			OICalcoloContribR filtro = new OICalcoloContribR();
			filtro.Idcomune = idComune;
			filtro.FkOicctId = idContribt;
			filtro.FkOdeId = idDestinazione;
			filtro.FkOtoId = idTipoOnere;

			OICalcoloContribR cls = (OICalcoloContribR)db.GetClass(filtro);

			if (cls == null)
				throw new ArgumentException("Impossibile trovare OICalcoloContribR per idContribt=" + idContribt + ", IdTipoOnere=" + idTipoOnere + ", idDestinazione=" + idDestinazione);

			cls.Riduzione = riduzione;

			RicalcolaRiduzioniECostoTotale(cls, false);
		}

		private void EffettuaCancellazioneACascata(OICalcoloContribR cls)
		{
			OICalcoloContribRRiduzMgr riduzMgr = new OICalcoloContribRRiduzMgr(db);

			List<OICalcoloContribRRiduz> lst = riduzMgr.GetListaRiduzioniDaContribR(cls);

			foreach (OICalcoloContribRRiduz rid in lst)
				riduzMgr.Delete(rid);

		}

		/// <summary>
		/// Ricalcola il totale della percentuale di riduzione, il totale diduzione e il costo totale dell'onere
		/// </summary>
		/// <remarks>
		/// Effettua anche il salvataggio della riga
		/// </remarks>
		/// <param name="cls"></param>
		public void RicalcolaRiduzioniECostoTotale(OICalcoloContribR cls , bool ricalcolaRiduzione)
		{
			OICalcoloContribRRiduzMgr ridMgr = new OICalcoloContribRRiduzMgr(db);

            cls.Riduzioneperc = cls.Id.GetValueOrDefault(int.MinValue) == int.MinValue ? 0.0f : ridMgr.CalcolaTotaleRiduzioni(cls);

			if(ricalcolaRiduzione)
                cls.Riduzione = Arrotondamento.PerEccesso((cls.Costom.GetValueOrDefault(double.MinValue) / 100) * cls.Riduzioneperc.GetValueOrDefault(double.MinValue), 2);

            cls.Costotot = Arrotondamento.PerEccesso(cls.SuperficieCubatura.GetValueOrDefault(double.MinValue) * (cls.Costom.GetValueOrDefault(double.MinValue) + cls.Riduzione.GetValueOrDefault(double.MinValue)), 2);

            if (cls.Id.GetValueOrDefault(int.MinValue) == int.MinValue)
				this.Insert(cls);
			else
				this.Update(cls);

			OICalcoloContribTMgr tmgr = new OICalcoloContribTMgr(db);
            tmgr.ElaboraBto(tmgr.GetById(cls.Idcomune, cls.FkOicctId.GetValueOrDefault(int.MinValue)));
		}



	}
}
