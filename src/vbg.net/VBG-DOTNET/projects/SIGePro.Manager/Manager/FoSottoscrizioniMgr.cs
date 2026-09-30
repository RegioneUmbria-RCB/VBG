
using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.ComponentModel;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class FoSottoscrizioniMgr
    {
        public class DatiSottoscrizione
        {
            public string CodiceFiscale { get; set; }

            private string m_codiceFiscaleSottoscrivente;

            public string CodiceFiscaleSottoscrivente
            {
                get { return this.m_codiceFiscaleSottoscrivente; }
                set { this.m_codiceFiscaleSottoscrivente = value; }
            }

        }

        public void EliminaSottoscrizioniDomanda(string idComune, int idDomanda)
        {
            FoSottoscrizioni filtro = new FoSottoscrizioni();
            filtro.Idcomune = idComune;
            filtro.Codicedomanda = idDomanda;

            List<FoSottoscrizioni> elementi = this.GetList(filtro);

            for (int i = 0; i < elementi.Count; i++)
                this.Delete(elementi[i]);
        }

        public void ModificaSottoscrizioniDomanda(string idComune, int idDomanda, List<DatiSottoscrizione> datiSottoscrizione)
        {
            this.EliminaSottoscrizioniDomanda(idComune, idDomanda);

            for (int i = 0; i < datiSottoscrizione.Count; i++)
            {
                FoSottoscrizioni s = new FoSottoscrizioni();
                s.Idcomune = idComune;
                s.Codicedomanda = idDomanda;
                s.Codicefiscale = datiSottoscrizione[i].CodiceFiscale;
                s.Codicefiscalesottoscrivente = String.IsNullOrEmpty(datiSottoscrizione[i].CodiceFiscaleSottoscrivente) ? datiSottoscrizione[i].CodiceFiscale : datiSottoscrizione[i].CodiceFiscaleSottoscrivente;

                this.Insert(s);
            }
        }

        public void SottoscriviDomanda(string idComune, int idDomanda, string codiceFiscale)
        {
            FoSottoscrizioni filtro = new FoSottoscrizioni();

            filtro.Idcomune = idComune;
            filtro.Codicedomanda = idDomanda;

            List<FoSottoscrizioni> lst = this.GetList(filtro);

            try
            {
                this.db.BeginTransaction();

                for (int i = 0; i < lst.Count; i++)
                {
                    if (lst[i].Codicefiscalesottoscrivente.ToUpper() == codiceFiscale.ToUpper())
                    {
                        lst[i].Datasottoscrizione = DateTime.Now;

                        this.Update(lst[i]);
                    }
                }

                this.db.CommitTransaction();
            }
            catch (Exception)
            {
                this.db.RollbackTransaction();

                throw;
            }
        }

        public List<FoDomande> GetListaDomandeDaSottoscrivere(string idComune, string idComuneAlias, string software, string codiceFiscaleSottoscrivente)
        {
            //         FoDomande foDomande = new FoDomande();
            //         foDomande.SelectColumns = "DISTINCT FO_DOMANDE.*";
            //         foDomande.OthersTables.Add("FO_SOTTOSCRIZIONI");
            //         foDomande.OthersWhereClause.Add("FO_SOTTOSCRIZIONI.IDCOMUNE = FO_DOMANDE.IDCOMUNE");
            //         foDomande.OthersWhereClause.Add("FO_SOTTOSCRIZIONI.CODICEDOMANDA = FO_DOMANDE.ID");
            //         foDomande.OthersWhereClause.Add("FO_SOTTOSCRIZIONI.CODICEFISCALESOTTOSCRIVENTE = '" + codiceFiscaleSottoscrivente + "'");
            //foDomande.OthersWhereClause.Add("FO_DOMANDE.FLG_TRASFERITA = 1");
            //foDomande.OthersWhereClause.Add("FO_DOMANDE.FLG_PRESENTATA = 0");
            //         foDomande.Idcomune = idComune;
            //         foDomande.Software = software;
            //foDomande.UseForeign = useForeignEnum.Yes;

            //var vertFileSystem = new VerticalizzazioneFilesystem(idComuneAlias, software);

            //var listaFoDomande = db.GetClassList(foDomande).ToList<FoDomande>();

            //if (vertFileSystem.Attiva)
            //{
            //	for (int i = 0; i < listaFoDomande.Count; i++)
            //	{
            //		FoDomande domanda = listaFoDomande[i];
            //		if (domanda.Oggetto.OGGETTO == null)
            //		{
            //			domanda.Oggetto = new OggettiMgr(db).GetById( idComune , Convert.ToInt32( domanda.Oggetto.CODICEOGGETTO ));
            //		}
            //	}
            //}

            //return listaFoDomande;

            throw new NotImplementedException();
        }

        public List<FoSottoscrizioni> GetListaSottoscriventi(string idComune, int codicedomanda)
        {
            FoSottoscrizioni foSott = new FoSottoscrizioni();
            //foSott.SelectColumns = "CODICEFISCALESOTTOSCRIVENTE, CODICEDOMANDA, IDCOMUNE";
            foSott.Codicedomanda = codicedomanda;
            foSott.Idcomune = idComune;

            return this.db.GetClassList(foSott).ToList<FoSottoscrizioni>();
        }
    }
}
